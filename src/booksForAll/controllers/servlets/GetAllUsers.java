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
 * Servlet implementation class GetAllUsers
 */
@WebServlet("/GetAllUsers")
public class GetAllUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllUsers() {
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
	 * 	
	 * Handles an HTTP request.
	 * Gets all Users Detailsfrom the database and send it to the client.
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
			PreparedStatement statement = connection.prepareStatement(Globals.SELECT_ALL_USERS);
			
			ResultSet resultSet = statement.executeQuery();
			
			int i = 1;
			resultSet.last();
			int rows = resultSet.getRow();
			resultSet.beforeFirst();
			
			while (resultSet.next()) {
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
				// Retrieve sender data
			
				String jsonUser = gson.toJson(tempUser, User.class);
				data += i++ < rows ? jsonUser + "," : jsonUser;
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("An unknown error has occurred while trying to retrieve Users details from the database.");
		}
		
		data += "]";

		PrintWriter out = response.getWriter();
		response.setContentType("application/json; charset=UTF-8");
		out.println(data);
		out.close();
	}
}
