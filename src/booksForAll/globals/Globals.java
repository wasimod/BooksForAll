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
			SELECT_USER_BY_USERNAME = "SELECT * FROM USERS WHERE USERNAME=?",
			SELECT_USER_BY_NICKNAME = "SELECT * FROM USERS WHERE NICKNAME=?",
			SELECT_USER_BY_USERNAME_AND_PASSWORD = "SELECT * FROM USERS WHERE USERNAME=? AND PASSWORD=?",
			INSERT_USER = "INSERT INTO USERS (USERNAME, PASSWORD, IS_ADMIN, NICKNAME, EMAIL, TELEPHONE, ADDRESS, DESCRIPTION, PHOTO_URL, STATUS) VALUES (?,?,?,?,?,?,?,?,?,?)",
			UPDATE_USER_STATUS = "UPDATE USERS SET STATUS=? WHERE USERNAME=?",
			LOGOFF_USERS = "UPDATE USERS SET STATUS=? WHERE STATUS=?",
			DELETE_USER = "DELETE FROM USERS WHERE USERNAME=?";

	/**
	 * REVIEW Table predefined statement.
	 */
	public static final String INSERT_REVIEW = "INSERT INTO REVIEWS (USERNAME, BOOK_ISBN, TEXT, WRITE_DATE, APPROVED) VALUES (?,?,?,?,?)",
			SELECT_REVIEW_BY_ID = "SELECT * FROM REVIEWS WHERE APPROVED=?",
			SELECT_REVIEWS_BY_APPROVED = "SELECT * FROM REVIEWS WHERE ID=?",
			SELECT_REVIEWS_FOR_BOOK = "SELECT * FROM REVIEWS WHERE BOOK_ISBN=? AND APPROVED=?",
			UPDATE_REVIEW_APPROVED = "UPDATE REVIEWS SET APPROVED=? WHERE ID=?";
	
	/**
	 * PURCHASE Table predefined statement.
	 */
	public static final String INSERT_PURCHASE = "INSERT INTO PURCHASES (USERNAME, BOOK_ISBN) VALUES (?,?)",
			SELECT_PURCHASES_BY_USERNAME = "SELECT * FROM PURCHASES WHERE USERNAME=?";
	
	/**
	 * LIKE Table predefined statement.
	 */
	public static final String INSERT_LIKE = "INSERT INTO LIKES (USERNAME, BOOK_ISBN) VALUES (?,?)",
			DELETE_LIKE = "DELETE FROM LIKES WHERE USERNAME=? AND BOOK_ISBN=?",
			SELECT_LIKES_FOR_BOOK = "SELECT * FROM LIKES WHERE BOOK_ISBN=?";
	
	/**
	 * BOOK Table predefined statement.
	 */
	public static final String SELECT_BOOK_BY_ISBN = "SELECT * FROM BOOKS WHERE ISBN=?";
}
