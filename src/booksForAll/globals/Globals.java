package booksForAll.globals;

import javax.naming.Context;

import booksForAll.globals.Database;

public class Globals {
	public static final String dbName = "BooksForAllDB";
	public static Context context = null;
	public static Database database;

	// Public variables and statements for SQL queries
	/**
	 * Users Table predefined statement.
	 */
	public static final String SELECT_ALL_USERS = "SELECT * FROM USERS",
//			SELECT_USER_BY_USERNAME = "SELECT * FROM USERS WHERE USERNAME=?",
//			SELECT_USER_BY_NICKNAME = "SELECT * FROM USERS WHERE NICKNAME=?",
//			SELECT_USER_BY_USERNAME_OR_NICKNAME = "SELECT * FROM USERS WHERE USERNAME=? OR NICKNAME=?",
			SELECT_USER_BY_USERNAME_AND_PASSWORD = "SELECT * FROM USERS WHERE USERNAME=? AND PASSWORD=?",
			INSERT_USER = "INSERT INTO USERS (USERNAME, PASSWORD, IS_ADMIN, NICKNAME, EMAIL, TELEPHONE, ADDRESS, DESCRIPTION, PHOTO_URL, STATUS) VALUES (?,?,?,?,?,?,?,?,?,?)",
			//UPDATE_USER_DETAILS = "UPDATE USERS SET USERNAME=?, PASSWORD=?, NICKNAME=?, DESCRIPTION=?, PHOTOURL=?, STATUS=?, LASTSEEN=? WHERE USERNAME=?",
			UPDATE_USER_STATUS = "UPDATE USERS SET STATUS=?, WHERE NICKNAME=?",
			LOGOFF_USERS = "UPDATE USERS SET STATUS=? WHERE STATUS=?",
			DELETE_USER = "DELETE FROM USERS WHERE USERNAME=?";

	/**
	 * REVIEW Table predefined statement.
	 */
	public static final String INSERT_REVIEW = "INSERT INTO REVIEWS (USERNAME, BOOK_ISBN, TEXT, WRITE_DATE, APPROVED) VALUES (?,?,?,?,?)";
	
	/**
	 * PURCHASE Table predefined statement.
	 */
	public static final String INSERT_PURCHASE = "INSERT INTO PURCHASES (USERNAME, BOOK_ISBN) VALUES (?,?)";
	
	/**
	 * LIKE Table predefined statement.
	 */
	public static final String INSERT_LIKE = "INSERT INTO LIKES (USERNAME, BOOK_ISBN) VALUES (?,?)",
			DELETE_LIKE = "DELETE FROM LIKES WHERE USERNAME=? AND BOOK_ISBN=?";
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Messages Table predefined statement.
	 */
	public static final String SELECT_ALL_MESSAGES = "SELECT * FROM MESSAGES",
			SELECT_MESSAGE_BY_SENDER = "SELECT * FROM MESSAGES WHERE SENDER=?",
			SELECT_MESSAGE_BY_RECEIVER = "SELECT * FROM MESSAGES WHERE RECEIVER=?",
			SELECT_TEN_CHANNEL_MESSAGES = "SELECT * FROM MESSAGES WHERE PARENT_ID=0 AND RECEIVER=? ORDER BY LAST_UPDATE DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY",
			SELECT_TEN_DIRECT_MESSAGES = "SELECT * FROM MESSAGES WHERE PARENT_ID=0 AND ((RECEIVER=? AND SENDER=?) OR (RECEIVER=? AND SENDER=?)) ORDER BY LAST_UPDATE DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY",
			SELECT_TEN_THREAD_MESSAGES = "SELECT * FROM MESSAGES WHERE PARENT_ID=? ORDER BY LAST_UPDATE DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY",
			SELECT_LAST_THREAD_MESSAGE = "SELECT * FROM MESSAGES WHERE PARENT_ID=? ORDER BY SENT_TIME DESC FETCH NEXT 1 ROWS ONLY",
			SELECT_MESSAGE_REPLIES = "SELECT * FROM MESSAGES WHERE PARENT_ID=? ORDER BY LAST_UPDATE DESC",
			SELECT_MESSAGE_BY_SENDER_AND_RECEIVER = "SELECT * FROM MESSAGES WHERE SENDER=? AND RECEIVER=?",
			INSERT_MESSAGE = "INSERT INTO MESSAGES (PARENT_ID, SENDER, RECEIVER, TEXT, LAST_UPDATE, SENT_TIME) VALUES (?,?,?,?,?,?)",
			UPDATE_MESSAGE_LAST_UPDATE_TIME = "UPDATE MESSAGES SET LAST_UPDATE=? WHERE ID=?";

	/**
	 * Channels Table predefined statement.
	 */
	public static final String SELECT_ALL_CHANNELS = "SELECT * FROM CHANNELS",
			SELECT_CHANNEL_BY_NAME = "SELECT * FROM CHANNELS WHERE NAME=?",
			INSERT_CHANNEL = "INSERT INTO CHANNELS (NAME, DESCRIPTION, CREATED_BY, CREATED_TIME) VALUES (?,?,?,?)",
			UPDATE_CHANNEL_DESCRIPTION = "UPDATE CHANNELS SET DESCRIPTION=? WHERE NAME=?",
			UPDATE_CHANNEL_NAME = "UPDATE CHANNELS SET NAME=? WHERE NAME=? AND CREATED_BY=?",
			DELETE_CHANNEL = "DELETE FROM CHANNELS WHERE NAME=? AND CREATED_BY=?";

	/**
	 * Subscriptions Table predefined statement.
	 */
	public static final String SELECT_SUBSCRIPTON_BY_USER = "SELECT * FROM SUBSCRIPTIONS WHERE NICKNAME=?",
			SELECT_SUBSCRIPTON_BY_CHANNEL = "SELECT * FROM SUBSCRIPTIONS WHERE CHANNEL=?",
			SELECT_ALL_SUBSCRIPTIONS = "SELECT * FROM SUBSCRIPTIONS",
			DELETE_SUBSCRIPTON = "DELETE FROM SUBSCRIPTIONS WHERE NICKNAME=? AND CHANNEL=?",
			INSERT_SUBSCRIPTON = "INSERT INTO SUBSCRIPTIONS (NICKNAME, CHANNEL) VALUES (?,?)";

}
