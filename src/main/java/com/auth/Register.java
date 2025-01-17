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


public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
	    ServletContext context = getServletContext();
	    String driver = context.getInitParameter("driver");
	    String dburl = context.getInitParameter("dburl");
	    String dbuserName = context.getInitParameter("dbuserName");
	    String dbpassword = context.getInitParameter("dbpassword");
	    
	    //getting user info from registration form
	    String fname = request.getParameter("fname");
	    String lname = request.getParameter("lname");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    
		
	    try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(dburl, dbuserName, dbpassword);
			
			PreparedStatement validate = con.prepareStatement("select first from authentcate where email=?");
			validate.setString(1, email);
			ResultSet data = validate.executeQuery();
			if(data.next()) {
				request.setAttribute("exist", "This email is already registered. Please log in or use a different email address.");
				request.getRequestDispatcher("index.jsp").include(request, response);
			}
			else {
				
				PreparedStatement inserting = con.prepareStatement("insert into authentcate (first, last, email, password) values(?, ?, ?, ?)");
				inserting.setString(1, fname);
				inserting.setString(2, lname);
				inserting.setString(3, email);
				inserting.setString(4, password);
				inserting.executeUpdate();
				
				request.getRequestDispatcher("login.jsp").include(request, response);
			}
			validate.close();
			con.close();
			
			
		} catch (ClassNotFoundException | SQLException e) {
		
			e.printStackTrace();
		}
	}

}
