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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import booksForAll.globals.Globals;
import booksForAll.models.User;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
		// Convert JSON object from request input to User object
		User user = gson.fromJson(request.getReader(), User.class);
		// Prepare a JSON to be forwarded to a new servlet or returned in the response
		PrintWriter out = response.getWriter();
		response.setContentType("application/json; charset=UTF-8");
		String data;
		if (insert(user)) {
			// Write user data to the response of type JSON
			HttpSession session = request.getSession();
			request.setAttribute("user", user);
			session.setAttribute("user", user);
			request.setAttribute("httpSession", session);
			String jsonUser = gson.toJson(user, User.class);
			data = "{"
				+ 		"\"status\": \"success\","
				+ 		"\"route\": \"messages\","
				+ 		"\"notification\": {"
				+ 			"\"selector\": \".register-notification\","
				+ 			"\"message\": \"Registered successfully\""
				+ 		"},"
				+ 		"\"user\": "
				+			 jsonUser
				;
	
			request.setAttribute("data", data + ",");
			session.setAttribute("data", data + ",");
			request.getRequestDispatcher("/messages").forward(request, response);
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
	 * A method to insert the new user to the database.
	 * @param user {@link booksForAll.models.User} object that contain the new user data.
	 * @return True in case the user inserted successfully, False otherwise.
	 */
	private boolean insert (User user) {
		int rows = 0;
		
		try {
			Connection connection = Globals.database.getConnection();
			PreparedStatement statement = connection.prepareStatement(Globals.INSERT_USER);
			
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getPassword());
			statement.setBoolean(3, user.getIsAdmin());
			statement.setString(4, user.getNickName());
			statement.setString(5,user.getEmail());
			statement.setString(6,user.getTelephone());
			statement.setString(7,user.getAddress());
			statement.setString(8, user.getDescription());
			statement.setString(9, user.getPhotoUrl());
			statement.setBoolean(10, user.getStatus());
			
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
