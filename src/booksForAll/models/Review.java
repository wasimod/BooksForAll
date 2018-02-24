package booksForAll.models;

import java.sql.Date;

public class Review {
	private int id;
	private String userName;
	private String bookIsbn;
	private String text;
	private Date writeDate;
	private boolean approved;
	
	public Review () {}
	
	public Review(String userName, String bookIsbn,String text,Date writeDate) {
		this.id = 0;
		this.userName = userName;
		this.bookIsbn = bookIsbn;
		this.text=text;
		this.writeDate=writeDate;
		this.approved=false;
	}
	
	public Review(int id, String userName, String bookIsbn,String text,Date writeDate,boolean approved) {
		this.id = id;
		this.userName = userName;
		this.bookIsbn = bookIsbn;
		this.text=text;
		this.writeDate=writeDate;
		this.approved=approved;
	}

	public int getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getBookIsbn() {
		return bookIsbn;
	}
	
	public String getText() {
		return text;
	}
	
	public Date getWriteDate() {
		return writeDate;
	}
	
	public boolean getApproved() {
		return approved;
	}
	
	public void setApproved(boolean approved) {
		this.approved=approved;
	}
}
