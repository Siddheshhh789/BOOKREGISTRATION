package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final String query="insert into BOOKDATA(BOOKNAME,BOOKEDITION,BOOKPRICE)VALUES (?,?,?)";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get printWriter
		PrintWriter pw=res.getWriter();
		//set content type
		res.setContentType("text/html");
		//get the book content
		String bookName=req.getParameter("bookName");
		String bookEdition =req.getParameter("bookEdition");
		float bookPrice=Float.parseFloat(req.getParameter("bookPrice"));
				
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
			ps.setString(1, bookName);
			ps.setString(2, bookEdition);
			ps.setFloat(3, bookPrice);
			int count=ps.executeUpdate();
			if(count==1) {
				pw.println("<h2> Record update successfully</h2>");
			}
			else {
				pw.println("<h2> not registered</h2>");
			}
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
		pw.println("<br>");
		pw.println("<a href='bookList'> BOOK LIST </a>");
		
	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	doGet(req,res);
}
	}
