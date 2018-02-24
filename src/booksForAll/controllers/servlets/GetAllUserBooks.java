package booksForAll.controllers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import booksForAll.globals.Globals;
import booksForAll.models.Book;
import booksForAll.models.User;

/**
 * Servlet implementation class GetAllUserBooks
 */
@WebServlet("/getalluserbooks")
public class GetAllUserBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllUserBooks() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}

	/**
	 * 	
	 * Handles an HTTP request.
	 * Gets all purchased books for a user from the database and send it to the client.
	 * <p>
	 * <b>Used methods:</b>
	 * <br/>
	 * @param request Http request
	 * @param response Http response
	 * @throws ServletException
	 * @throws IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *
	 */
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new GsonBuilder().create();
		// Convert JSON object from request input to Book object
		User user = gson.fromJson(request.getReader(), User.class);
		// Prepare a JSON to be forwarded to a new servlet or returned in the response
		response.setContentType("application/json; charset=UTF-8");
		
		String data = "[";
		
		try {
			Connection connection = Globals.database.getConnection();
			PreparedStatement statement = connection.prepareStatement(Globals.SELECT_PURCHASES_BY_USERNAME);
			PreparedStatement statement2;
			statement.setString(1, user.getUserName());

			ResultSet resultSet = statement.executeQuery();
			ResultSet resultSet2;
			
			int i = 1;
			resultSet.last();
			int rows = resultSet.getRow();
			resultSet.beforeFirst();
			
			while (resultSet.next()) {
				Book tempBook = new Book();
				tempBook.setIsbn(resultSet.getString("BOOK_ISBN"));

				statement2 = connection.prepareStatement(Globals.SELECT_BOOK_BY_ISBN);
				statement2.setString(1, tempBook.getIsbn());
				resultSet2 = statement2.executeQuery();
				
				// The book exists in our system, get his data
				if (resultSet2.next()) {
					tempBook.setTitle(resultSet2.getString("TITLE"));
					tempBook.setDescription(resultSet2.getString("DESCRIPTION"));
					tempBook.setAuthor(resultSet2.getString("AUTHOR"));
					tempBook.setPublishDate(resultSet2.getDate("PUBLISH_DATE"));
					tempBook.setPrice(resultSet2.getDouble("PRICE"));										
					statement.close();
				// He is not existing, return null
				} else {
					statement.close();
					
				}
				
				// Retrieve sender data		
				String jsonBook = gson.toJson(tempBook, Book.class);
				data += i++ < rows ? jsonBook + "," : jsonBook;
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("An unknown error has occurred while trying to retrieve Approved Reviews for a Book from the database.");
		}
		
		data += "]";

		PrintWriter out = response.getWriter();
		response.setContentType("application/json; charset=UTF-8");
		out.println(data);
		out.close();
	}
}
