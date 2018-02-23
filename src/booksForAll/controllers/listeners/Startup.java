package booksForAll.controllers.listeners;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import booksForAll.globals.Database;
import booksForAll.globals.Globals;

/**
 * Application Lifecycle Listener implementation class Startup
 *
 */
@WebListener
public class Startup implements ServletContextListener {

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		Database.setDatabase(servletContext);

		try {
			Connection connection = Globals.database.getConnection();
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("CREATE TABLE USERS (" + 
					"USERNAME 		VARCHAR(10) NOT NULL PRIMARY KEY," + 
					"PASSWORD 		VARCHAR(8) NOT NULL," + 
					"IS_ADMIN		BOOLEAN	NOT NULL,	" + 
					"NICKNAME 		VARCHAR(20) UNIQUE," + 
					"EMAIL			VARCHAR(50) NOT NULL," + 
					"TELEPHONE		VARCHAR(10) NOT NULL," + 
					"ADDRESS			VARCHAR(300) NOT NULL," + 
					"DESCRIPTION 	VARCHAR(50)," + 
					"PHOTO_URL 		VARCHAR(150)," + 
					"STATUS 			BOOLEAN NOT NULL" + 
				")"
			);

			statement.executeUpdate("CREATE TABLE BOOKS (" + 
					"ISBN 			VARCHAR(30) PRIMARY KEY," + 
					"TITLE			VARCHAR(150) NOT NULL," + 
					"DESCRIPTION		VARCHAR(500) NOT NULL," + 
					"AUTHOR			VARCHAR(50) NOT NULL," + 
					"PUBLISH_DATE 	TIMESTAMP NOT NULL," + 
					"PRICE			DOUBLE NOT NULL DEFAULT 0.0" + 
				")"
			);

			statement.executeUpdate("CREATE TABLE LIKES (" + 
					"ID			INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY," + 
					"USERNAME	VARCHAR(10) NOT NULL REFERENCES USERS(USERNAME) ON DELETE CASCADE," + 
					"BOOK_ISBN 	VARCHAR(30) NOT NULL REFERENCES BOOKS(ISBN) ON DELETE CASCADE" + 
				")"
			);

			statement.executeUpdate("CREATE TABLE REVIEWS (" + 
					"ID			INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY," + 
					"USERNAME	VARCHAR(10) NOT NULL REFERENCES USERS(USERNAME) ON DELETE CASCADE," + 
					"BOOK_ISBN 	VARCHAR(30) NOT NULL REFERENCES BOOKS(ISBN) ON DELETE CASCADE," + 
					"TEXT 		VARCHAR(500) NOT NULL," + 
					"WRITE_DATE	TIMESTAMP NOT NULL," + 
					"APPROVED 	BOOLEAN NOT NULL" + 
				")"
			);

			connection.commit();
			statement.close();
			connection.close();

			System.out.println("The database was created successfully, and you're now connected to it.");

		} catch (SQLException e) {
			if (e.getSQLState().equals("X0Y32")) {
				System.out.println("The database is already existing, you're now connected to it.");
				
			} else {
				System.out.println("An unknown error has occured while trying to create the database.");
			}
		}
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		logoffAllUsers();
		Globals.database.shutdown(servletContext);
	}

	private void logoffAllUsers() {
		Timestamp last_seen = new Timestamp(System.currentTimeMillis());
		try {
			Connection connection = Globals.database.getConnection();
			PreparedStatement statement = connection.prepareStatement(Globals.LOGOFF_USERS);

			statement.setString(1, "away");
			statement.setTimestamp(2, last_seen);
			statement.setString(3, "active");

			statement.executeUpdate();
			connection.commit();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("An error has occured while trying to execute the query!");
		}
	}
}
