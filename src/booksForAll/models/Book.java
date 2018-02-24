package booksForAll.models;

import java.util.Date;

public class Book {
	private String isbn;
	private String title;
	private String description;
	private String author;
	private Date publishDate;
	private double price;
	
	public Book() {}
	
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
	
	public void setIsbn(String isbn) {
		this.isbn=isbn;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title=title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description=description;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author=author;
	}
	
	public Date getPublishDate() {
		return publishDate;
	}
	
	public void setPublishDate(Date publishDate) {
		this.publishDate=publishDate;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price=price;
	}
	
}
