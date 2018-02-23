package booksForAll.models;

public class Like {
	private int id;
	private String userName;
	private String bookIsbn;

	public Like(int id, String userName, String bookIsbn) {
		this.id = id;
		this.userName = userName;
		this.bookIsbn = bookIsbn;
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
}
