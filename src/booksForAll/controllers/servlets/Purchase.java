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

/**
 * Servlet implementation class Purchase
 */
@WebServlet("/purchase")
public class Purchase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Purchase() {
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
	 * Handles an HTTP request.
	 * Add new Purchased book to the database.
	 * <p>
	 * <b>Used methods:</b>
	 * <br/>
	 * <dd>{@link #insert(Purchase)} - Insert the new Purchase to the database.</dd>
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
		booksForAll.models.Purchase purchase = gson.fromJson(request.getReader(), booksForAll.models.Purchase.class);
		// Prepare a JSON to be forwarded to a new servlet or returned in the response
		PrintWriter out = response.getWriter();
		response.setContentType("application/json; charset=UTF-8");
		String data;
		if (insert(purchase)) {
			// Write purchase data to the response of type JSON
			String jsonPurchase = gson.toJson(purchase, booksForAll.models.Purchase.class);
			data = "{"
				+ 		"\"status\": \"success\","
				+ 		"\"route\": \"messages\","
				+ 		"\"notification\": {"
				+ 			"\"selector\": \".register-notification\","
				+ 			"\"message\": \"Registered successfully\""
				+ 		"},"
				+ 		"\"purchase\": "
				+			 jsonPurchase
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
	private boolean insert (booksForAll.models.Purchase purchase) {
		int rows = 0;
		
		try {
			Connection connection = Globals.database.getConnection();
			PreparedStatement statement = connection.prepareStatement(Globals.INSERT_PURCHASE);
			
			statement.setString(1, purchase.getUserName());
			statement.setString(2, purchase.getBookIsbn());
			
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
