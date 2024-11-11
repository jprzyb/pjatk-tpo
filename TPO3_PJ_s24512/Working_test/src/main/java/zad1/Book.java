package zad1;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.*;
import java.sql.*;

public class Book extends HttpServlet {
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

		PrintWriter responseOut = response.getWriter();
		String bookId = request.getParameter("searched");
		responseOut.println("<h1>For searched id "+bookId +" result is: </h1>");

			Statement statement = null;
			try {
				statement = con.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String query = "SELECT * FROM books WHERE id=" + bookId;
			ResultSet res = null;
			
			try {
				res = statement.executeQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			boolean hasResult;
			try {
				hasResult = res.next();
				if (hasResult) {
					String id = res.getString("id");
					String title = res.getString("title");
					String author = res.getString("author");
					responseOut.println("<h1>" + title + "</h1>");
					responseOut.println("<h2>" + author + "</h2>");
					responseOut.println("<h3>" + id + "</h3>");
				} else {
					responseOut.println("<h1>Book not found</h1>");
				}
				
				res.close();
				statement.close();
				responseOut.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
}