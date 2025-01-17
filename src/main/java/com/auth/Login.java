package com.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		
		 	ServletContext context = getServletContext();
		    String driver = context.getInitParameter("driver");
		    String dburl = context.getInitParameter("dburl");
		    String dbuserName = context.getInitParameter("dbuserName");
		    String dbpassword = context.getInitParameter("dbpassword");
		    
		    // getting inputs from the server or login.jsp
		    String email = request.getParameter("email");
		    String password = request.getParameter("password");
		    
		    try {
		    	Class.forName(driver);
				Connection con = DriverManager.getConnection(dburl, dbuserName, dbpassword);
				PreparedStatement ps = con.prepareStatement("select * from authentcate where email=?");
				ps.setString(1, email);
				ResultSet data = ps.executeQuery();
				
				if(data.next()) {
					 int userId = data.getInt("id");
					 HttpSession uid = request.getSession();
					 uid.setAttribute("userId", userId);
					String dPassword = data.getString("password");
					if(!password.equals(dPassword)) {
						request.setAttribute("wrong-pass", "Incorrect Password");
						request.getRequestDispatcher("login.jsp").include(request, response);
					}else {
						HttpSession emailSession = request.getSession();
				         emailSession.setAttribute("userEmail", email);
						response.sendRedirect("home.jsp");
					}
				}else {
					request.setAttribute("wrong-email", "Invalid email or password.");
					request.getRequestDispatcher("login.jsp").include(request, response);
				}
				
				con.close();
				ps.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
