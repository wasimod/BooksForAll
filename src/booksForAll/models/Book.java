package booksForAll.models;

import java.util.Date;

public class Book {
	private String isbn;
	private String title;
	private String description;
	private String author;
	private Date publishDate;
	private double price;
	
	public Book(String isbn,String title,String description,String author,Date publishDate,double price) {
		this.isbn=isbn;
		this.title=title;
		this.description=description;
		this.author=author;
		this.publishDate=publishDate;
		this.price=price;
	}

	public String getIsbn() {
		return isbn;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public Date getPublishDate() {
		return publishDate;
	}
	
	public double getPrice() {
		return price;
	}
}
