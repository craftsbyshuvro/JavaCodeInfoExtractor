package myfirstplugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DirectoryExplorer {
		
	AppConfig _appConfig = new AppConfig();

	
	public void ExploreDirectory() throws IOException{
		ArrayList<String> listOfProjects =  this.getSubDirectories();
		
		ProjectAnalyser _projectAnalyzer = new ProjectAnalyser();
		_projectAnalyzer.AnalyzeProjects(listOfProjects);
	}
	 
	
	public ArrayList<String> getSubDirectories(){
	    ArrayList<String> listOfProjects = new ArrayList<String>(); 
		for (File f : new File(_appConfig.PROJECTS_DIRECTORY).listFiles()) {
		    if (f.isDirectory()) {
		    	listOfProjects.add(f.toString());
		    }        
		}
		return listOfProjects;
	}
	
	
	public List<String> GetAllJavaFiles(String directoryName) {
	    ArrayList<String> result = new ArrayList<String>(); 
        List<File> resultList = new ArrayList<File>();
        resultList = listf(directoryName);
        
        for(File file : resultList) {
        	String filePath = file.getAbsolutePath();
        	if(filePath.endsWith(".java")) {
        		result.add(filePath);
        	}
        }
        
        return result;
	}

    public List<File> listf(String directoryName) {

        File directory = new File(directoryName);
        List<File> resultList = new ArrayList<File>();

        // get all the files from a directory
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
//                System.out.println(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                resultList.addAll(listf(file.getAbsolutePath()));
            }
        }

        return resultList;
    } 
}
