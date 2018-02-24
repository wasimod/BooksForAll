package booksForAll.models;

public class Purchase {
	private int id;
	private String userName;
	private String bookIsbn;

	public Purchase() {}
	
	public Purchase(String userName, String bookIsbn) {
		this.id = 0;
		this.userName = userName;
		this.bookIsbn = bookIsbn;
	}
	
	public Purchase(int id, String userName, String bookIsbn) {
		this.id = id;
		this.userName = userName;
		this.bookIsbn = bookIsbn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id=id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName=userName;
	}
	
	public String getBookIsbn() {
		return bookIsbn;
	}

	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn=bookIsbn;
	}
}
