package org.mckv.librarymanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
    private int bookid;

    @NotBlank(message = "Book title cannot be blank")
    @Size(min = 2, max = 100, message = "Book title should be between 2 to 100 characters")
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @NotBlank(message = "Author name cannot be blank")
    @Size(min = 2, max = 100, message = "Author name should be between 2 to 100 characters")
    @Column(name = "author", length = 100, nullable = false)
    private String author;

    @NotBlank(message = "ISBN cannot be blank")
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @NotBlank(message = "Book category cannot be blank")
    @Column(name = "category", nullable = false)
    private String category;

    @NotNull(message = "Total copies cannot be null")
    @PositiveOrZero(message = "Total copies cannot be negative")
    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies;

    @NotNull(message = "Available copies cannot be null")
    @PositiveOrZero(message = "Available copies cannot be negative")
    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies;
	
    @Column(name = "active", nullable = false)
    private boolean active = true;
    
    public Book() {
    	
    }

 // Parameterized constructor
    
    public Book(
    		@NotBlank(message = "Book title cannot be blank") @Size(min = 2, max = 100, message = "Book title should be between 2 to 100 characters") String title,
    		@NotBlank(message = "Author name cannot be blank") @Size(min = 2, max = 100, message = "Author name should be between 2 to 100 characters") String author,
    		@NotBlank(message = "ISBN cannot be blank") String isbn,
    		@NotBlank(message = "Book category cannot be blank") String category,
    		@NotNull(message = "Total copies cannot be null") @PositiveOrZero(message = "Total copies cannot be negative") Integer totalCopies,
    		@NotNull(message = "Available copies cannot be null") @PositiveOrZero(message = "Available copies cannot be negative") Integer availableCopies) {
    	super();
    	this.title = title;
    	this.author = author;
    	this.isbn = isbn;
    	this.category = category;
    	this.totalCopies = totalCopies;
    	this.availableCopies = availableCopies;
    }

	public int getBookid() {
		return bookid;
	}

	

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getTotalCopies() {
		return totalCopies;
	}

	public void setTotalCopies(Integer totalCopies) {
		this.totalCopies = totalCopies;
	}

	public Integer getAvailableCopies() {
		return availableCopies;
	}

	public void setAvailableCopies(Integer availableCopies) {
		this.availableCopies = availableCopies;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Book [bookid=" + bookid + ", title=" + title + ", author=" + author + ", isbn=" + isbn + ", category="
				+ category + ", totalCopies=" + totalCopies + ", availableCopies=" + availableCopies + ", active="
				+ active + "]";
	}
    
 

	
}
