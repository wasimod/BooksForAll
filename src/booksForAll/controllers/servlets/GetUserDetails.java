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
import booksForAll.models.User;

/**
 * Servlet implementation class GetUserDetails
 */
@WebServlet("/getuserdetails")
public class GetUserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserDetails() {
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
	 * Gets User Details by nickname from the database and send it to the client.
	 * <p>
	 * <b>Used methods:</b>
	 * <br/>
	 * <dd>{@link #getUserData(User)} - to get user data from the database.</dd>
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
		User user = gson.fromJson(request.getReader(), User.class);
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String data;
		
		String jsonUser = gson.toJson(getUserData(user), User.class);
		data = 	"{"
			 + 		"\"user\": "
			 + 			jsonUser + ","
	 		 + 		"\"messages\": []"
			 + 	"}"
			 ;
		
		out.println(data);
		out.close();
	}
	/**
	 * A method to get user data by his nickname.
	 * @param user {@link booksForAll.models.User} object that contain the user nickname.
	 * @return {@link booksForAll.models.User} object that contain required User's data.
	 */
	private User getUserData (User user) {
		
		try {
			Connection connection = Globals.database.getConnection();
			PreparedStatement statement = connection.prepareStatement(Globals.SELECT_USER_BY_NICKNAME);
			
			statement.setString(1, user.getNickName());
			
			ResultSet resultSet = statement.executeQuery();
			// The user exists in our system, get his data
			if (resultSet.next()) {
				User tempUser = new User();
				tempUser.setUserName(resultSet.getString("USERNAME"));
				tempUser.setPassword(resultSet.getString("PASSWORD"));
				tempUser.setIsAdmin(resultSet.getBoolean("IS_ADMIN"));
				tempUser.setNickName(resultSet.getString("NICKNAME"));
				tempUser.setDescription(resultSet.getString("DESCRIPTION"));
				tempUser.setStatus(resultSet.getBoolean("STATUS"));
				tempUser.setAddress(resultSet.getString("ADDRESS"));
				tempUser.setEmail(resultSet.getString("EMAIL"));
				tempUser.setTelephone(resultSet.getString("TELEPHONE"));
				tempUser.setPhotoUrl(resultSet.getString("PHOTO_URL"));
				
				statement.close();
				connection.close();
				return tempUser;
			// He is not existing, return null
			} else {
				statement.close();
				connection.close();
				return null;
			}
			
		} catch (SQLException e) {
			System.out.println("An error has occured while trying to execute the query!");
			return null;
		}
	}
}
