package myfirstplugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DBHandler {
	
	
    public Connection connect() {
    	
        // SQLite connection string  
        String url = AppConfig.SQLITE_FILE_CON_STR;  
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        Connection conn = null;  
        try { 
            conn = DriverManager.getConnection(url);  
        } catch (SQLException e) {  
            System.out.println(e);  
        }  
        return conn;  
    } 
	
    public void InsertAPICallSequence(ProjectDetailsModel projectDetails, Connection conn){ 
    	    	    	
        String sql = "INSERT INTO api_call_sequence(project_name, file_path, declared_method, invoked_method, date_created" 
        	+") VALUES(?,?,?,?,?)";
        
        PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  

    	
		for (MethodInvokedModel mim : projectDetails.InvokedMethods){
			
			if(mim == null){continue;}
			
			for (String invokedMethods : mim.InvokedMethods){
				   try{ 
//					    Connection conn = this.connect();
//			            PreparedStatement pstmt = conn.prepareStatement(sql);  
			            pstmt.setString(1, projectDetails.ProjectName);  
			            pstmt.setString(2, projectDetails.FilePath);
			            pstmt.setString(3, mim.MethodContainsInvokedMethod);
			            pstmt.setString(4, invokedMethods);
			            
			            String timeStamp = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
			            pstmt.setString(5, timeStamp);
			            
			            pstmt.addBatch();
			   
			        } catch (SQLException e) {
			            System.out.println(e.getMessage());
			        } 
			}
		}
		
		
		try {
			pstmt.executeBatch();
			pstmt.close();		

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
    
    public void InsertMethodandComment(ArrayList<CommentModel> commentListModel, Connection conn){ 
    	
        String sql = "INSERT INTO method_comment_details(project_name, file_path, method_name, comment, comment_type, date_created" 
        	+") VALUES(?,?,?,?,?,?)";  
        
        PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  


			for (CommentModel cmntModel : commentListModel){
				
				if(cmntModel == null){continue;}

				   try{
//			            PreparedStatement pstmt = conn.prepareStatement(sql);  
			            pstmt.setString(1, cmntModel.ProjectName);  
			            pstmt.setString(2, cmntModel.FilePath);
			            pstmt.setString(3, cmntModel.DeclaredMethodName);
			            pstmt.setString(4, cmntModel.Comment);
			            pstmt.setString(5, cmntModel.CommentType);
			            
			            String timeStamp = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
			            pstmt.setString(6, timeStamp);

			            pstmt.addBatch();

			        } catch (SQLException e) {  
			            System.out.println(e.getMessage());
			        }
			}
			
			
			try {
				pstmt.executeBatch();
				pstmt.close();		

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    } 
    
    public void InsertImportStatement(ArrayList<ImportStatementModel> istModelList, Connection conn){ 
    	
        String sql = "INSERT INTO import_statement(project_name, file_path, statement, date_created" 
        	+") VALUES(?,?,?,?)";  
        
        PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  


			for (ImportStatementModel istModel : istModelList){
				
				if(istModel == null){continue;}
				
				   try{
			            pstmt.setString(1, istModel.ProjectName);  
			            pstmt.setString(2, istModel.FilePath);
			            pstmt.setString(3, istModel.Statement);
			            
			            String timeStamp = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
			            pstmt.setString(4, timeStamp);

			            pstmt.addBatch();

			        } catch (SQLException e) {  
			            System.out.println(e.getMessage());
			        }
			}
			
			
			
			try {
				pstmt.executeBatch();
				pstmt.close();		

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    } 

}
