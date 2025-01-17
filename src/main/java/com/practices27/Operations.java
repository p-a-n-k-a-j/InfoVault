package com.practices27;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Operations {
    static  Connection con=null;
    
    static {
    	 String driver ="org.postgresql.Driver";
    	 String jdbcURL = "jdbc:postgresql://dpg-cu12dilsvqrc73em3770-a.oregon-postgres.render.com:5432/employeedb_ycsd";
         String username = "employeedb_ycsd_user";
         String password = "4PLj8JCFSHicv7uIlC9QTQtZMCzF1ZDk";
    
         try {
        	 Class.forName(driver);
        	 con = DriverManager.getConnection(jdbcURL, username, password);
		} catch ( ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    }
    public static void insert(HttpServletResponse response, String name, String mob, int uid) throws IOException {
        try (PrintWriter out = response.getWriter(); 
             PreparedStatement ps = con.prepareStatement("INSERT INTO emp (name, mobile, user_id) VALUES (?, ?, ?)")) {

            // Verify the connection
            if (con == null) {
                throw new SQLException("Database connection is null.");
            }

            // Log the data being inserted
        

            // Set the parameters for the PreparedStatement
            ps.setString(1, name);
            ps.setString(2, mob);
            ps.setInt(3, uid);

            // Execute the query
            int rowsAffected = ps.executeUpdate();

            // Check if the insert was successful
            if (rowsAffected > 0) {
                // Send success response
                response.setStatus(HttpServletResponse.SC_OK); // Ensure the status is OK
                out.write("{\"success\": true}");
               
            } else {
                // Record not inserted, return an error response
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Return 400 status if insertion fails
                out.write("{\"success\": false, \"error\": \"Failed to insert data.\"}");
                
            }

        } catch (SQLException e) {
            // Handle SQL exception and return specific error message
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Return 500 for DB errors
            response.getWriter().write("{\"success\": false, \"error\": \"Database error: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            // Handle any other exceptions
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Return 500 for generic errors
            response.getWriter().write("{\"success\": false, \"error\": \"An unexpected error occurred: " + e.getMessage() + "\"}");
        }
    }

    
    public static void update(HttpServletResponse response, int id, String name, String mob, int uid) throws IOException {
    		response.setContentType("application/json");
    		response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            

            PreparedStatement ps = con.prepareStatement("UPDATE emp SET name = ?, mobile = ? WHERE id = ? AND user_id = ?");
            ps.setString(1, name);
            ps.setString(2, mob);
            ps.setInt(3, id);
            ps.setInt(4, uid);

            int rowAffected = ps.executeUpdate();
           

            if (rowAffected > 0) {
                // Send success response
                response.setStatus(HttpServletResponse.SC_OK); // Ensure the status is OK
                out.write("{\"success\": true}");
               
            } else {
                // Record not inserted, return an error response
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Return 400 status if insertion fails
                out.write("{\"success\": false, \"error\": \"Failed to update data.\"}");
                
            }

        } catch (SQLException e) {
            // Handle SQL exception and return specific error message
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Return 500 for DB errors
            response.getWriter().write("{\"success\": false, \"error\": \"Database error: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            // Handle any other exceptions
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Return 500 for generic errors
            response.getWriter().write("{\"success\": false, \"error\": \"An unexpected error occurred: " + e.getMessage() + "\"}");
        }
        
    }

	
	public static void Delete(int id, HttpServletResponse response, int uid) throws IOException {
	    // This can be removed if not needed elsewhere
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    try (PrintWriter out = response.getWriter()) {
	        // Check if the record exists
	        PreparedStatement ps = con.prepareStatement("SELECT * FROM emp WHERE id = ? AND user_id = ?");
	        ps.setInt(1, id);
	        ps.setInt(2, uid);
	        ResultSet dataSet = ps.executeQuery();

	        if (dataSet.next()) {
	            // Retrieve details for trash insertion
	            String ename = dataSet.getString("name");
	            String mob = dataSet.getString("mobile");

	            // Insert into trash table
	            insertTrash(id, ename, mob, uid);

	            // Delete record from the main table
	            PreparedStatement dt = con.prepareStatement("DELETE FROM emp WHERE id = ? AND user_id = ?");
	            dt.setInt(1, id);
	            dt.setInt(2, uid);
	            int rowEffect = dt.executeUpdate();

	            if (rowEffect > 0) {
	                // Send success response
	                out.write("{\"success\": true}");
	                 // Record deleted successfully
	            } else {
	                // Record not found
	                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	                out.write("{\"success\": false, \"error\": \"Record not found.\"}");
	            }
	        } else {
	            // Record does not exist
	            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	            out.write("{\"success\": false, \"error\": \"Record not found.\"}");
	        }
	    } catch (Exception e) {
	        // Handle internal server error
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("{\"success\": false, \"error\": \"Error deleting record.\"}");
	        e.printStackTrace();
	    }

	}

	public static void Retrive(HttpServletResponse response, int uid) {

	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    try (PrintWriter out = response.getWriter()) {
	        PreparedStatement get = con.prepareStatement("SELECT * FROM emp WHERE user_id = ?");
	        get.setInt(1, uid);
	        ResultSet rs = get.executeQuery();
	        List<Map<String, String>> dataList = new ArrayList<>();
	        while(rs.next()) {
	        	Map<String, String> row = new HashMap<String, String>();
	        	row.put("id", String.valueOf(rs.getInt("id")));
	        	row.put("name", String.valueOf(rs.getString("name")));
	        	row.put("mobile", String.valueOf(rs.getString("mobile")));
	        	 dataList.add(row);
	        }
	        // If data is available, convert to JSON
	        if (!dataList.isEmpty()) {
	            ObjectMapper objectMapper = new ObjectMapper();
	            String json = objectMapper.writeValueAsString(dataList);
	            out.write(json);  // Send the JSON response
	        } else {
	            // Return an empty JSON array if no data found
	            out.write("[]");
	        }
	        
	    }catch (Exception e) {
	        e.printStackTrace();
	        try {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("{\"error\":\"Error fetching data from the database!\"}");
	        } catch (IOException ioException) {
	            ioException.printStackTrace();
	        }
	    }
         
	}
	// this method is private because its a expensive method that no body know about that
	private static void insertTrash(int id, String name, String mobile, int uid) {
		try {
			PreparedStatement intrash = con.prepareStatement("insert into emp_trash (id, name, mobile, user_id) values(?, ?, ?, ?)");
			intrash.setInt(1, id);
			intrash.setString(2, name);
			intrash.setString(3, mobile);
			intrash.setInt(4, uid);
			intrash.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static  void RestoreUsingId(HttpServletResponse response, int did , int uid) throws IOException {
			
		try (PrintWriter out = response.getWriter()){//this statement for fetching data from emp_trash table
		
			PreparedStatement get = con.prepareStatement("select * from emp_trash where id =? AND user_id=? ");
			get.setInt(1, did);
			get.setInt(2, uid);
			ResultSet restored =get.executeQuery();
			if(restored.next()) {
			
				
				int rid = restored.getInt("id");
				String rname = restored.getString("name");
				String rmob = restored.getString("mobile");
				
				PreparedStatement restoredInEmp = con.prepareStatement("insert into emp ( name, mobile, user_id) values( ?, ?, ?)");
				
				restoredInEmp.setString(1, rname);
				restoredInEmp.setString(2, rmob);
				restoredInEmp.setInt(3, uid);
				int Affected = restoredInEmp.executeUpdate();
				if(Affected >0) {
		                // Send success response
		                out.write("{\"success\": true}");
		                 // Record deleted successfully
		            } else {
		                // Record not found
		                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		                out.write("{\"success\": false, \"error\": \"Record not found.\"}");
		            }
				
				// this code for deleted restored data from trash
			
				PreparedStatement deleteTrash =con.prepareStatement(" delete from emp_trash where id=? AND user_id =?");
				deleteTrash.setInt(1, rid);
				deleteTrash.setInt(2, uid);
				
				deleteTrash.executeUpdate();
			}
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("{\"error\":\"Error fetching data from the database!\"}");
	        } catch (IOException ioException) {
	            ioException.printStackTrace();
	        }
		}
		
	
		
	}

	// this code for show trash data the user
	public static void showTrash(HttpServletResponse response, int uid) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try (PrintWriter out = response.getWriter()){
			
			PreparedStatement get = con.prepareStatement("select * from emp_trash where user_id=?");
			get.setInt(1, uid);
			ResultSet rs = get.executeQuery();
			
			List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
			while(rs.next()) {
				Map<String, String> row = new HashMap<String, String>();
				row.put("id", String.valueOf(rs.getInt("id")));
				row.put("name", rs.getString("name"));
				row.put("mobile", rs.getString("mobile"));
				dataList.add(row);

			}
			
			if(!dataList.isEmpty()) {
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(dataList);
				out.write(json);
			}else {
				out.write("{\"message\":\"No data available\"}");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			try {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("{\"error\":\"Error fetching data from the database!\"}");
	        } catch (IOException ioException) {
	            ioException.printStackTrace();
	        }
		}
		
	}
	
	// I think someOne want to delete trash data so for that I make this method. using this user easily delete trash data
	
	public static void deletetrash(int id, HttpServletResponse response, int uid) {
			
		try(PrintWriter out = response.getWriter()) {
			
			
			PreparedStatement dt = con.prepareStatement("delete from emp_trash where id =? and user_id=?");
			
			
			dt.setInt(1, id);	
			dt.setInt(2, uid);
			int rowAffect=dt.executeUpdate();
			
			if(rowAffect>0) {
				out.write("{\"success\": true}");
			}else {
                // Record not found
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.write("{\"success\": false, \"error\": \"Record not found.\"}");
            }

		}catch(Exception e) {
			e.printStackTrace();
			try {
				 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	             response.getWriter().write("{\"success\": false, \"error\": \"Record not found.\"}");
	        } catch (IOException ioException) {
	            ioException.printStackTrace();
	        }
			
		}
		
	}


}
