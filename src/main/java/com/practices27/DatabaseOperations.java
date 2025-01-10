package com.practices27;




import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/DatabaseOperations")
public class DatabaseOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response); // Common method for handling logic
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response); // Common method for handling logic
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        HttpSession session = request.getSession(false);
        String name = "";
        String mobile = "";
        String operation = "";

        // Initialize ObjectMapper for parsing JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(request.getInputStream());

        // Check if the "operation" field exists and is not null
        if (jsonNode.has("operation") && !jsonNode.get("operation").isNull()) {
            operation = jsonNode.get("operation").asText();  // safely extract the value as text
        }

        // Check if the "name" field exists and is not null
        if (jsonNode.has("name") && !jsonNode.get("name").isNull()) {
            name = jsonNode.get("name").asText();  // safely extract the value as text
        }

        // Check if the "mobile" field exists and is not null
        if (jsonNode.has("mobile") && !jsonNode.get("mobile").isNull()) {
            mobile = jsonNode.get("mobile").asText();  // safely extract the value as text
        }

      
       
       
        if (session == null) {
            request.setAttribute("error", "Session expired. Please log in again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        int recordId =0;
        String id= request.getParameter("id");
        try {
            if (id != null) {
                recordId = Integer.parseInt(id);
                
            }
        } catch (NumberFormatException e) {
          
            request.setAttribute("error", "Invalid ID format.");
            request.getRequestDispatcher("home.jsp").include(request, response);
            return;
        }
        int uid = (int) session.getAttribute("userId");
        
        if (operation == null || operation.trim().isEmpty()) {
            request.setAttribute("error", "No operation specified.");
            request.getRequestDispatcher("home.jsp").include(request, response);
            return;
        }
     
       try {
    	   // this go to Operation type for check the operation 
    	   OperationType opType = OperationType.fromString(operation);
    	   //it execute handleOperation for check operation and execute specific operation
    	   handleOperation(opType, recordId, request, response,name, mobile, uid);
    	   
       }catch(Exception e) {
    	   request.setAttribute("error", "Invalid operation specified.");
           request.getRequestDispatcher("home.jsp").include(request, response);
       }
       
       
    }

    
    public void handleOperation(OperationType opType,int id, HttpServletRequest request, HttpServletResponse response,String name, String mobile, int uid)
            throws ServletException, IOException {
    	
    	switch(opType) {
    	case INSERT 		-> handleInsert(request, response, name, mobile, uid);
    	case UPDATE 		-> handleUpdate(request, response,id, name, mobile, uid);
    	case DELETE 		-> handleDelete(id, request, response, uid);
    	case DELETETRASH 	-> handleDeleteTrash(request, response,id, uid);
    	case RESTOREID 		-> handleRestoreId(request, response, id, uid);
    	
    	
    	case RETRIEVE    	-> handleRetrive(request, response, uid);
    	case SHOWTRASH 		-> handleRetriveTrash(request, response, uid);
    	
    	default -> throw new IllegalArgumentException("Unsupported operation");
    	}
    }
    
    private void handleRetrive(HttpServletRequest request, HttpServletResponse response, int uid)throws ServletException, IOException  {
   	 	Operations.Retrive(response, uid);
        
   }
    private void handleRetriveTrash(HttpServletRequest request, HttpServletResponse response, int uid)throws ServletException, IOException  {
   	 	Operations.showTrash(response, uid);
       
   }
    
    private void handleInsert(HttpServletRequest request, HttpServletResponse response, String name, String mobile, int uid) throws ServletException, IOException {
   
        Operations.insert(response, name, mobile, uid);
    }

    
    
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response,int id, String name, String mobile, int uid)throws ServletException, IOException  {
       Operations.update(response, id,name, mobile, uid);
        
    }
    
 
    
    
    private void handleDelete(int id, HttpServletRequest request, HttpServletResponse response, int uid)throws ServletException, IOException  {
    				Operations.Delete(id,response, uid);
 
    }
    private void handleDeleteTrash(HttpServletRequest request, HttpServletResponse response,int id, int uid)throws ServletException, IOException  {
    	Operations.deletetrash(id,response, uid);
        
    }
  
    
   
    private void handleRestoreId(HttpServletRequest request, HttpServletResponse response,int id, int uid)throws ServletException, IOException  {
    
      Operations.RestoreUsingId(response,id, uid);
        
    }
   
    


	
	private enum OperationType{
		INSERT, UPDATE, DELETE, RETRIEVE, SHOWTRASH, DELETETRASH, 
		 RESTOREID;
		public static OperationType fromString(String op) {
			
			return switch(op.toLowerCase()) {
			case "insert", "i" 			-> INSERT;
			case "update", "u" 			-> UPDATE;
			
			case "delete", "d" 			-> DELETE;
			case "retrieve", "r" 		-> RETRIEVE;
			case "restoreid", "ri"		-> RESTOREID;
			
			case "showtrash", "st" 		-> SHOWTRASH;
			case "deletetrash", "dt"	-> DELETETRASH;
		
			
			
			
			default -> throw new IllegalArgumentException("Unexpected value: " + op.toLowerCase());
			
			};
			
		}
	}
}




