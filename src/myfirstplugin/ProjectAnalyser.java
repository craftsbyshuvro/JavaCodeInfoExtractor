package myfirstplugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;

import ca.mcgill.cs.swevo.ppa.PPAOptions;
import ca.mcgill.cs.swevo.ppa.ui.PPAUtil;
 

public class ProjectAnalyser {
	DirectoryExplorer _directoryExplorer = new DirectoryExplorer();
	CSVRW _csvWrite = new CSVRW();
	AppConfig _appConfig = new AppConfig();
	Connection conn = null;

	
	public ProjectAnalyser() throws IOException{
		DBHandler _dbhandler = new DBHandler();
		this.conn = _dbhandler.connect();
		
		if(_appConfig.DELETE_AND_CREATE_OUTPUT_FILE_ACS){
			_csvWrite.CreateCSVConditionally(new File(_appConfig.CSV_OUTPUT_LOCATION_ACS));
		}
	}
	
	
	public void AnalyzeProjects(ArrayList<String> listOfProjects) throws IOException{
		for(String projectPath: listOfProjects){
//			System.out.println("========PROJECT PATH :	"+projectPath+"=====");
			ExtractCodeInfo(projectPath);
		}
	}
	
	
	// read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
 
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
 
		reader.close();
		return fileData.toString();
	}
	
		
	public void ExtractCodeInfo(String projectPath) throws IOException{
		List<String> JavaFilesFromProject = _directoryExplorer.GetAllJavaFiles(projectPath);
		ProjectDetailsModel projectDetails = new ProjectDetailsModel();
		
		PPAOptions ppaOption = new PPAOptions();
		
		for(String javaFile: JavaFilesFromProject){
			
			projectDetails.ProjectName = projectPath.substring(projectPath.lastIndexOf('\\') + 1).trim();
			projectDetails.FilePath = javaFile;
			
			System.out.println("========FILE :	"+projectDetails.FilePath+"=====");
			
			String javaFileAsString = readFileToString(javaFile);
		    File javaFileToAnalyze = new File(javaFile);
		    
		    try{
			    CompilationUnit cu = PPAUtil.getCU(javaFileToAnalyze, ppaOption);
			    
			    PPATypeVisitor visitor = new PPATypeVisitor(cu, projectDetails, javaFileAsString, this.conn);
			    
			    cu.accept(visitor);
			    
			    visitor = null;
		    }
		    catch(Exception ex){
		    	ex.printStackTrace();
		    }
		    finally{
		    	
		    }
		}
		
		System.out.println("====ALLEND===");
	}
	
}
