package myfirstplugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVRW {
	AppConfig _appConfig = new AppConfig();
	
	
	public void WriteContentToCSV(ProjectDetailsModel projectDetails) throws Exception {
		
		FileWriter csvwriter = new FileWriter(_appConfig.CSV_OUTPUT_LOCATION_ACS, true);
		
		for (MethodInvokedModel mim : projectDetails.InvokedMethods){
			for (String invokedMethods : mim.InvokedMethods){
				csvwriter.append(projectDetails.ProjectName);
		        csvwriter.append(",");
				csvwriter.append(projectDetails.FilePath);
		        csvwriter.append(",");
				
		        csvwriter.append(mim.MethodContainsInvokedMethod);
		        csvwriter.append(",");

		        csvwriter.append(invokedMethods.replace(',', '|'));
		        csvwriter.append("\n");
			}
		}
		
        csvwriter.close();
	} 
	
	public void CreateCSVConditionally(File f) throws IOException {	
				
		if(f.exists()){
    	   File csvFile = new File(_appConfig.CSV_OUTPUT_LOCATION_ACS); 
    	   csvFile.delete();
		}
		
		    f.createNewFile();
			FileWriter csvwriter = new FileWriter(_appConfig.CSV_OUTPUT_LOCATION_ACS, true);
			csvwriter.append("ProjectName");
	        csvwriter.append(",");
			csvwriter.append("FilePath");
	        csvwriter.append(",");
			csvwriter.append("MethodContainsInvokedMethod");
	        csvwriter.append(",");
	        csvwriter.append("InvokedMethods");
	        csvwriter.append("\n");
            csvwriter.close();
	}
}
