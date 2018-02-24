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
 * Servlet implementation class Approve
 */
@WebServlet("/approve")
public class Approve extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Approve() {
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
	 * Approve Review.
	 * <p>
	 * <b>Used methods:</b>
	 * <br/>
	 * <dd>{@link #get(Review)} - to get Review data from the database if he exist.</dd>
	 * <br/>
	 * <dd>{@link #updateReviewApproved(Review)} - update the review approve to true.</dd>
	 * @param request Http request
	 * @param response Http response
	 * @throws ServletException
	 * @throws IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new GsonBuilder().create();
		// Convert JSON object from request input to Review object
		Review review = gson.fromJson(request.getReader(), Review.class);
		// Connect to database
		// Get the Review from Database (if exists)
		Review reviewFromDb = get(review);
		// Prepare a JSON to be forwarded to a new servlet or returned in the response
		PrintWriter out = response.getWriter();
		response.setContentType("application/json; charset=UTF-8");
		String data;
		// Write Review data to the response of type JSON
		if (reviewFromDb != null) {
			
			reviewFromDb.setApproved(true);
			String jsonReview = gson.toJson(reviewFromDb, Review.class);
			data = "{"
				+ 		"\"status\": \"success\","
				+ 		"\"route\": \"messages\","
				+ 		"\"notification\": {"
				+ 			"\"selector\": \".login-notification\","
				+ 			"\"message\": \"Review Approved successfully\""
				+ 		"},"
				+ 		"\"review\": "
				+			jsonReview
				;

			request.setAttribute("data", data + ",");

			request.setAttribute("review", reviewFromDb);
			request.getRequestDispatcher("/messages").forward(request, response);
			data += "}";
			updateReviewApproved(reviewFromDb);
		// Write "failure" status to the response
		} else {
			data = "{"
				+ 		"\"status\": \"danger\","
				+ 		"\"route\": \"login\","
				+ 		"\"notification\": {"
				+ 			"\"selector\": \".login-notification\","
				+ 			"\"message\": \"Incorrect review id\""
				+ 		"}"
				+ 	"}"
				;
		}

		out.println(data);
		out.close();
	}
	/**
	 * A method to get the Review data.
	 * If the Review doesn't exist return null.
	 * @param user {@link booksForAll.models.Review} object that contain the review model.
	 * @return {@link booksForAll.models.Review} object that contain required Review's 
	 * data in case he exist, else null.
	 */
	private Review get (Review review) {
		try {
			Connection connection = Globals.database.getConnection();
			PreparedStatement statement = connection.prepareStatement(Globals.SELECT_REVIEW_BY_ID);
			
			statement.setInt(1, review.getId());
			
			ResultSet resultSet = statement.executeQuery();
			
			// The Review exists in our system, get his data
			if (resultSet.next()) {
				Review reviewFromDb = new Review();
				reviewFromDb.setId(resultSet.getInt("ID"));
				reviewFromDb.setUserName(resultSet.getString("USERNAME"));			
				reviewFromDb.setBookIsbn(resultSet.getString("BOOK_ISBN"));
				reviewFromDb.setText(resultSet.getString("TEXT"));
				reviewFromDb.setWriteDate(resultSet.getDate("WRITE_DATE"));
				reviewFromDb.setApproved(resultSet.getBoolean("APPROVED"));
				
				statement.close();
				connection.close();
				return reviewFromDb;
			// He is not existing, return null
			} else {
				statement.close();
				connection.close();
				return null;
			}

		} catch (SQLException e) {
			System.out.println("An error has occured while trying to connect to database!");
			return null;
		}
	}
	
	/**
	 * A method to update the Review APPROVED to true.
	 */
	private void updateReviewApproved (Review review) {
		boolean approve = true;
		
		try {
			Connection connection = Globals.database.getConnection();
			PreparedStatement statement = connection.prepareStatement(Globals.UPDATE_REVIEW_APPROVED);
			
			statement.setBoolean(1, approve);
			statement.setInt(2, review.getId());
			statement.executeUpdate();
			
			connection.commit();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("An error has occured while trying to execute the query!");
		}
	}
}
