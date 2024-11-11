package zad1;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.*;
import java.sql.*;

public class Books extends HttpServlet {
	String url;
    String query;
    String usr;
    String pass;
    String driverName;
    Connection con;

	public void init() throws ServletException {
			this.url = "jdbc:sqlserver://KUBUS;trustServerCertificate=true";
			this.query = "SELECT * FROM books";
			this.usr = "user_one";
			this.pass = "aaaa";
			this.driverName= "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			
			try {
				Class.forName(driverName);
				con = DriverManager.getConnection(url, usr, pass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter resOut = response.getWriter();
		resOut.println("<h1>Books</h1>");
		resOut.println("<form>");
		resOut.println("<label>");
		resOut.println("<p>Name:</p>");
		resOut.println("<input type=\"text\" name=\"searched\" />");
		resOut.println("</label>");
		resOut.println("<br />");
		resOut.println("<input type=\"submit\" value=\"Search by title\" />");
		resOut.println("</form>");

		String searched = request.getParameter("searched");

		try {
			Statement statement = con.createStatement();
			
			String query = "SELECT * FROM books";
			if (searched != null) {
				query = "SELECT id, title, author FROM books WHERE title LIKE " + searched;
			}
			
			ResultSet results = statement.executeQuery(query);
			resOut.println("<ol>");
			while (results.next()) {
				int id = results.getInt("id");
				String title = results.getString("title");
				String author = results.getString("author");
				resOut.println("<li><a href=\"Book?id=" + id + "\">" + title + ", " + author + "</li>");
			}
			resOut.println("</ol>");
			results.close();
			statement.close();
		} catch (Exception exc) {
			resOut.println(exc.getMessage());
		} finally {
			try {
				con.close();
			} catch (Exception exc) {}
		}

		resOut.close();
	}
}