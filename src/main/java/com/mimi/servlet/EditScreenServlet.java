package com.mimi.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {

	
	private static final String query="SELECT * FROM BOOKDATA  where id=? ";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter 
		 PrintWriter pw= res.getWriter(); // ctrl+shift+o
		 //set content type
		 res.setContentType("text/html"); 
		 //GET THE ID OF THE RECORD
//		 int id= Integer.parseInt(req.getParameter("id"));
		 int id= Integer.parseInt(req.getParameter("id").trim());

		 //LOAD jdbc driver
		 try{
			Class.forName("com.mysql.cj.jdbc.Driver"); 
		 }catch (ClassNotFoundException e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
		//generate the connection
		 try(Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","mimi019mimi");
				PreparedStatement ps = con.prepareStatement(query); ){
			ps.setInt(1,id);
			ResultSet rs= ps.executeQuery();
			rs.next();
			
			pw.println("<form action='editurl?id="+id+"' method='post'>");

			pw.println("<table align='center'>");
			pw.println("<tr>");
			pw.println("<td>Book Name</td>");
			pw.println("<td> <input type='text' name='bookName' value='"+rs.getString(2)+"'></td>");
			pw.println("</tr>");
			
			pw.println("<tr>");
			pw.println("<td>Book Edition</td>");
			pw.println("<td> <input type='text' name='bookEdition' value='"+rs.getString(3)+"'></td>");
			pw.println("</tr>");
			
			pw.println("<tr>");
			pw.println("<td>Book Price</td>");
			pw.println("<td> <input type='text' name='bookPrice' value='"+rs.getFloat(4)+"'></td>");
			pw.println("</tr>");
			
			pw.println("<tr>");
			pw.println("<td><input type='submit' value='Edit' ></td>");
			pw.println("<td><input type='reset' value='Cancel' ></td>");
			pw.println("</tr>");
			
			
			pw.println("</table>");
			pw.println("</form>");
			
		 }catch(SQLException se) {
			 se.printStackTrace();
			 pw.println("<h1>"+se.getMessage()+"</h2>");
		 }catch(Exception e) {
			 e.printStackTrace();
			 pw.println("<h1>"+e.getMessage()+"</h2>");

		 }
		 pw.println("<a href='home.html'>Home</a>");

		 
	}
	@Override //ctrl+espace
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
		
		
		
	}
	
	
	
	
}
