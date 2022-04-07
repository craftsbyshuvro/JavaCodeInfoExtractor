package myfirstplugin;

import java.io.File;
import java.util.ArrayList;

public class ValidationHelper {
	
	public ArrayList<CommentModel> filteredCommentModel = new ArrayList<CommentModel>();
	
	public ArrayList<CommentModel> validMethodPriorComment(ArrayList<CommentModel> pCommentModelList, ArrayList<MethodInfoModel> pMethodInfoModel, String pJavaFileAsString){
		
		String[] javaMultiLineFile =  pJavaFileAsString.split("\n");
		
		for(MethodInfoModel methodInfo :  pMethodInfoModel){
			CommentModel tempCmntModel = new CommentModel();
			boolean hasPriorComment = false;
			for(CommentModel cmntModel :  pCommentModelList){
				tempCmntModel = cmntModel;
				
				int LinetoCompare = GetLineNoWithContent(methodInfo.StartLine - 1, javaMultiLineFile);
				
				if(LinetoCompare >= cmntModel.StartLine && LinetoCompare <= cmntModel.EndLine){
					tempCmntModel.DeclaredMethodName = methodInfo.Name;
					tempCmntModel.FilePath = methodInfo.FilePath;
					tempCmntModel.ProjectName = methodInfo.ProjectName;
					filteredCommentModel.add(tempCmntModel);
					hasPriorComment = true;
				}
			}
			
			if(hasPriorComment){
				continue;
			}

			tempCmntModel = new CommentModel();
			tempCmntModel.DeclaredMethodName = methodInfo.Name;
			tempCmntModel.FilePath = methodInfo.FilePath;
			tempCmntModel.ProjectName = methodInfo.ProjectName;
			filteredCommentModel.add(tempCmntModel);
		} 	
		return filteredCommentModel;
	}
	
	private int GetLineNoWithContent(int pStartLineNo, String[] pJavaMultiLineFile){
		int LineNoToSearch = pStartLineNo - 1;
		if(pJavaMultiLineFile[LineNoToSearch].trim().length() < 1){
			int LineNoToReturn = GetLineNoWithContent(LineNoToSearch, pJavaMultiLineFile);
			return LineNoToReturn;
		}
		
		return LineNoToSearch + 1;
	}
}
