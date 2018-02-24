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

import booksForAll.globals.Globals;
import booksForAll.models.User;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
     * @see HttpServlet#HttpServlet()
     */
	public Logout() {
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
	 * Log the user out and update his status to false.
	 * <p>
	 * <b>Used methods:</b>
	 * <br/>
	 * <dd>{@link #updateUserStatus(HttpSession)} - update the user status to false (Offline).</dd>
	 * @param request Http request
	 * @param response Http response
	 * @throws ServletException
	 * @throws IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/JSON; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String data;
		data = "{"
			+ 		"\"status\": \"success\","
			+ 		"\"route\": \"login\","
			+ 		"\"notification\": {"
			+ 			"\"selector\": \".logout-notification\","
			+ 			"\"message\": \"Logged out successfully\""
			+ 		"}"
			+  "}"
			;
		HttpSession session = request.getSession();
		updateUserStatus(session);
		session.invalidate();
		out.println(data);
		out.close();
	}
	/**
	 * Updates the user's status to false (offline) when he logs out.
	 * <p>
	 * @param session The current HTTP session.
	 */
	private void updateUserStatus (HttpSession session) {
		User user = (User)session.getAttribute("user");		
		boolean status = false;
		
		try {
			Connection connection = Globals.database.getConnection();
			PreparedStatement statement = connection.prepareStatement(Globals.UPDATE_USER_STATUS);
			
			statement.setBoolean(1, status);
			statement.setString(2, user.getUserName());
			
			statement.executeUpdate();
			connection.commit();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("An error has occured while trying to execute the query!");
		}
	}
}
