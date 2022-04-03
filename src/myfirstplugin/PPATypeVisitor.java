package myfirstplugin;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PPABindingsUtil;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import java.sql.Connection;


public class PPATypeVisitor extends ASTVisitor {

	private PrintStream printer;
	private CompilationUnit cu;
	ArrayList<MethodInvokedModel> InvokedMethods;
	public ProjectDetailsModel projectDetailsModel;
	public String javaFileAsString;
	
	// Internal Purpose
	public MethodInvokedModel methodInvoked;
	
	// Comment and Method Declaration
	public ArrayList<CommentModel> commentModelList;
	public ArrayList<MethodInfoModel> methodInfoList;
	
	// Import Statement
	public ArrayList<ImportStatementModel> importStatementList;

	// DB Connection
	Connection con = null; 
	DBHandler _DBHandler = new DBHandler();
	
	public PPATypeVisitor(CompilationUnit compunit, ProjectDetailsModel projectDetailsModel,String javaFileAsString, Connection conn) {
		super();
		this.cu = compunit;
		this.projectDetailsModel = projectDetailsModel;
		this.projectDetailsModel.InvokedMethods = new ArrayList<MethodInvokedModel>();
		this.javaFileAsString = javaFileAsString;
		
		InvokedMethods = new ArrayList<MethodInvokedModel>();
		
		// Comment and Method Declaration
		this.commentModelList = new ArrayList<CommentModel>();
		this.methodInfoList = new ArrayList<MethodInfoModel>();
		
		// Import Statement
		this.importStatementList = new ArrayList<ImportStatementModel>();
		
		//DB Connection
		this.con = conn;
		
		// Comment handler
		this.CommentList();
	}
	
	public void CommentList(){		
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(this.javaFileAsString.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
 
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		CommentModel cmntModel = new CommentModel();

		
		for (Comment comment : (List<Comment>) cu.getCommentList()) {
			CommentVisitor cmntVisitor = new CommentVisitor(cu, this.javaFileAsString);
			comment.accept(cmntVisitor);
			
			cmntModel.Comment = CommentVisitor.cmntModel.Comment;
			cmntModel.StartLine = CommentVisitor.cmntModel.StartLine;
			cmntModel.EndLine = CommentVisitor.cmntModel.EndLine;
			cmntModel.CommentType = CommentVisitor.cmntModel.CommentType;
			cmntModel.ProjectName = projectDetailsModel.ProjectName;
			cmntModel.FilePath = projectDetailsModel.FilePath;

			this.commentModelList.add(cmntModel);
			
			cmntVisitor = null;
		}

	}
	
	

	@Override
	public boolean visit(ImportDeclaration node) {
		ImportStatementModel istModel = new ImportStatementModel();
		
		istModel.ProjectName = projectDetailsModel.ProjectName;
		istModel.FilePath = projectDetailsModel.FilePath;
		istModel.Statement = node.getName().toString();

		this.importStatementList.add(istModel);
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		
		// Method declaration and comment		
		MethodInfoModel methodInfoModel = new MethodInfoModel();
		methodInfoModel.Name = node.getName().toString();
		methodInfoModel.StartLine = this.cu.getLineNumber(node.getName().getStartPosition());
		this.methodInfoList.add(methodInfoModel);
		
		  
//		int methodStartLine = this.cu.getLineNumber(node.getName().getStartPosition());
//		String methodName = node.getName().toString();
//		System.out.println("Method Name: "+methodName+"	| Start: "+methodStartLine);
//		validationHelper.validMethodPriorComment(commentModelList, methodName, methodStartLine);
		
		Block methodBody = node.getBody();
		if(methodBody == null){
			return super.visit(node);
		}
		
		
		// TODO Auto-generated method stub
		methodInvoked = new MethodInvokedModel();
		methodInvoked.MethodContainsInvokedMethod = node.getName().toString();
		methodInvoked.InvokedMethods = new ArrayList<String>(); 
		

		
	    methodBody.accept(new ASTVisitor() {
			@Override
			public boolean visit(MethodInvocation node) {
				
				
				IBinding binding = null;
				binding = node.resolveMethodBinding();
				
				//Added Later	
				IMethodBinding mBinding = (IMethodBinding) binding;
				
				
				String fullyQualifiedMethod = mBinding.getDeclaringClass().getQualifiedName()+ "."+ mBinding.getName()+"()";
												
				methodInvoked.InvokedMethods.add(fullyQualifiedMethod);
				
				return true;
			}
		});
	    
		return super.visit(node);
	}
	
	@Override
	public void endVisit(MethodDeclaration node) {
	    InvokedMethods.add(methodInvoked);	
		// TODO Auto-generated method stub
		super.endVisit(node);
	}
	
	@Override
	public void endVisit(CompilationUnit node) {
		
		
		// Method declaration and comment
		ValidationHelper validationHelper = new ValidationHelper();
		
		ArrayList<CommentModel> filteredCmntModelList = new ArrayList<CommentModel>();  
		filteredCmntModelList = validationHelper.validMethodPriorComment(commentModelList, this.methodInfoList, this.javaFileAsString);
		
//		for(CommentModel cmntModel : filteredCmntModelList){
//			System.out.println("MethodName: 	"+ cmntModel.DeclaredMethodName);
//			System.out.println("Comment: 	"+ cmntModel.Comment);
//		}
		
		//API calling Sequences
		this.projectDetailsModel.InvokedMethods.addAll(InvokedMethods);
	
		
		//Database Handler
		DBHandler dbHandler = new DBHandler();

		try{
			dbHandler.InsertAPICallSequence(this.projectDetailsModel, this.con);
			dbHandler.InsertMethodandComment(filteredCmntModelList, this.con);
			dbHandler.InsertImportStatement(importStatementList, this.con);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		
		// CSV Handler
//		CSVRW csvWriter = new CSVRW();
//		try {
//			csvWriter.WriteContentToCSV(this.projectDetailsModel);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		super.endVisit(node);
	}

	
}