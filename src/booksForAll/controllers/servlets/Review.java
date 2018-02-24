package booksForAll.controllers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import booksForAll.globals.Globals;
import booksForAll.models.User;


/**
 * Servlet implementation class Review
 */
@WebServlet("/Review")
public class Review extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Review() {
        super();
        // TODO Auto-generated constructor stub
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
	 * Handles an HTTP request.
	 * Register a new User to the database.
	 * <p>
	 * <b>Used methods:</b>
	 * <br/>
	 * <dd>{@link #insert(User)} - Insert the new user to the database.</dd>
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
		booksForAll.models.Review review = gson.fromJson(request.getReader(), booksForAll.models.Review.class);
		// Prepare a JSON to be forwarded to a new servlet or returned in the response
		PrintWriter out = response.getWriter();
		response.setContentType("application/json; charset=UTF-8");
		String data;
		if (insert(review)) {
			// Write review data to the response of type JSON
			String jsonReview = gson.toJson(review, booksForAll.models.Review.class);
			data = "{"
				+ 		"\"status\": \"success\","
				+ 		"\"route\": \"messages\","
				+ 		"\"notification\": {"
				+ 			"\"selector\": \".register-notification\","
				+ 			"\"message\": \"Registered successfully\""
				+ 		"},"
				+ 		"\"review\": "
				+			 jsonReview
				;
	
			request.setAttribute("data", data + ",");
			data += "}";
		} else {
			data = "{"
				+ 		"\"status\": \"danger\","
				+ 		"\"route\": \"messages\","
				+ 		"\"notification\": {"
				+ 			"\"selector\": \".register-notification\","
				+ 			"\"message\": \"Something went wrong, please try again later\""
				+ 		"}"
				+	"}"
				;
		}
		out.println(data);
		out.close();
	}
	 
	/**
	 * A method to insert the new review to the database.
	 * @param review {@link booksForAll.models.review} object that contain the new review data.
	 * @return True in case the review inserted successfully, False otherwise.
	 */
	private boolean insert (booksForAll.models.Review review) {
		int rows = 0;
		
		try {
			Connection connection = Globals.database.getConnection();
			PreparedStatement statement = connection.prepareStatement(Globals.INSERT_REVIEW);
			
			statement.setString(1, review.getUserName());
			statement.setString(2, review.getBookIsbn());
			statement.setString(3, review.getText());
			statement.setDate(4, review.getWriteDate());
			statement.setBoolean(5,review.getApproved());
			
			rows = statement.executeUpdate();
			connection.commit();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("An error has occured while trying to execute the query!");
		}
		
		return rows > 0;
	}
}
