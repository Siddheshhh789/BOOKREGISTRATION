package com.idiot.servlet;

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
	private static final String query="select BOOKNAME,BOOKEDITION,BOOKPRICE from BOOKDATA  where id=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get printWriter
		PrintWriter pw=res.getWriter();
		//set content type
		res.setContentType("text/html");
		//get the id for edit
		int id=Integer.parseInt(req.getParameter("id"));
		
				
		//load jdbc driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnf) {
			// TODO Auto-generated catch block
			cnf.printStackTrace();
		}
		//generate the connection
		try(Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","root");
				PreparedStatement ps=con.prepareStatement(query)){
			ps.setInt(1,id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			pw.println("<form action='editurl?id="+id+"' method='post'>");
			pw.println("<table align='center'>");
			pw.println("<tr>");
			pw.println("<td>BOOK NAME</td>");
			pw.println("<td><input type='text'name='bookName'value='"+rs.getString(1)+"'></td>)");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>BOOK EDITION</td>");
			pw.println("<td><input type='text'name='bookEdition'value='"+rs.getString(2)+"'></td>)");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>BOOK PRICE</td>");
			pw.println("<td><input type='text'name='bookPrice'value='"+rs.getString(3)+"'></td>)");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td><input type='submit' value='edit'></td>");
			pw.println("<td><input type='reset' value='cancel'></td>");
			
			
			
			pw.println("</tr>");
			pw.println("</table>");
			pw.println("</form>");
		}
		catch(SQLException se) {
			se.printStackTrace();
			pw.println("<h1>"+se.getMessage()+"/h2");
		}
		catch(Exception e) {
			e.printStackTrace();
			pw.println("<h1>"+e.getMessage()+"/h2");
		}
		pw.println("<a href='home.html'>HOME</a>");

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	doGet(req,res);
}
	}



