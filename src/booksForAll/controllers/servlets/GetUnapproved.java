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
import booksForAll.models.Review;

/**
 * Servlet implementation class GetUnapproved
 */
@WebServlet("/getunapproved")
public class GetUnapproved extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUnapproved() {
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
	 * Gets all unapproved reviews from the database and send it to the client.
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
		response.setContentType("application/json; charset=UTF-8");
		
		String data = "[";
		
		try {
			Connection connection = Globals.database.getConnection();
			PreparedStatement statement = connection.prepareStatement(Globals.SELECT_REVIEWS_BY_APPROVED);
			
			statement.setBoolean(1, false);
			
			ResultSet resultSet = statement.executeQuery();
			
			int i = 1;
			resultSet.last();
			int rows = resultSet.getRow();
			resultSet.beforeFirst();
			
			while (resultSet.next()) {
				Review tempReview = new Review();
				tempReview.setId(resultSet.getInt("ID"));
				tempReview.setUserName(resultSet.getString("USERNAME"));
				tempReview.setBookIsbn(resultSet.getString("BOOK_ISBN"));			
				tempReview.setText(resultSet.getString("TEXT"));
				tempReview.setWriteDate(resultSet.getDate("WRITE_DATE"));
				tempReview.setApproved(resultSet.getBoolean("APPROVED"));

				// Retrieve sender data
			
				String jsonReview = gson.toJson(tempReview, Review.class);
				data += i++ < rows ? jsonReview + "," : jsonReview;
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("An unknown error has occurred while trying to retrieve Unapproved Reviews from the database.");
		}
		
		data += "]";

		PrintWriter out = response.getWriter();
		response.setContentType("application/json; charset=UTF-8");
		out.println(data);
		out.close();
	}
}
