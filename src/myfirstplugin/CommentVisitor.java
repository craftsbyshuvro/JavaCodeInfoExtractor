package myfirstplugin;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.LineComment;

public class CommentVisitor extends ASTVisitor {
	CompilationUnit cu;
	public String source;
	public static CommentModel cmntModel = new CommentModel();
 
	public CommentVisitor(CompilationUnit cu, String source) {
		super();
		this.cu = cu;
		this.source = source;
	}
	
	public boolean visit(LineComment node) {
		int start = node.getStartPosition();
		int end = start + node.getLength();
		String comment = source.substring(start, end);
		
		
		// Setting values in object
		cmntModel.StartLine = this.cu.getLineNumber(start);
		cmntModel.EndLine = this.cu.getLineNumber(end);
		cmntModel.Comment = comment;
		cmntModel.CommentType = "Line";


//		System.out.println(comment+"	Start: "+this.cu.getLineNumber(start)+" | END: "+this.cu.getLineNumber(end));
		return true;
	}
 
	public boolean visit(BlockComment node) {
		int start = node.getStartPosition();
		int end = start + node.getLength();
		String comment = source.substring(start, end);
		
		// Setting values in object
		cmntModel.StartLine = this.cu.getLineNumber(start);
		cmntModel.EndLine = this.cu.getLineNumber(end);
		cmntModel.Comment = comment;
		cmntModel.CommentType = "Block";
		
//		System.out.println(comment+"	Start: "+this.cu.getLineNumber(start)+" | END: "+this.cu.getLineNumber(end));
		return true;
	}

	@Override
	public boolean visit(Javadoc node) {

		if(node.isDocComment()){
			int start = node.getStartPosition();
			int end = start + node.getLength();
			String comment = source.substring(start, end);
			
			// Setting values in object
			cmntModel.StartLine = this.cu.getLineNumber(start);
			cmntModel.EndLine = this.cu.getLineNumber(end);
			cmntModel.Comment = comment;
			cmntModel.CommentType = "Javadoc";
			
//			System.out.println(comment+"	Start: "+this.cu.getLineNumber(start)+" | END: "+this.cu.getLineNumber(end));
		} 
		
		return super.visit(node);
	}
}
