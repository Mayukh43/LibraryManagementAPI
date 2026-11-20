package org.mckv.librarymanagement.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.mckv.librarymanagement.entity.Book;
import org.mckv.librarymanagement.exceptions.DuplicateResourceException;
import org.mckv.librarymanagement.exceptions.InvalidOperationException;
import org.mckv.librarymanagement.exceptions.ResourceNotFoundException;
import org.mckv.librarymanagement.repository.BookIssueRepository;
import org.mckv.librarymanagement.repository.BookRepository;
import org.mckv.librarymanagement.repository.UniversalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class BookService {
	
	private final BookIssueRepository bookIssueRepository;
	private final UniversalRepository universalRepository;
    private final BookRepository bookRepository;
    

    // Constructor injection
    public BookService(UniversalRepository universalRepository,
            BookRepository bookRepository,
            BookIssueRepository bookIssueRepository) {

this.universalRepository = universalRepository;
this.bookRepository = bookRepository;
this.bookIssueRepository = bookIssueRepository;
}

    // Add a new book
    public Book addBook(Book book) {

    	if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new DuplicateResourceException(
                    "A book with this ISBN already exists");
        }

        if (book.getAvailableCopies() > book.getTotalCopies()) {
            throw new InvalidOperationException(
                    "Available copies cannot exceed total copies");
        }

        book.setActive(true);

        return universalRepository.save(book);
    }

    // Get all books
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
    		return bookRepository.findByActiveTrue();
    }

    // Get book by ID
    @Transactional(readOnly = true)
    public Book getBookById(int bookid) {

    	 Book book = universalRepository.findById(Book.class, bookid);

    	    if (book == null || !book.isActive()) {
    	        throw new ResourceNotFoundException(
    	                "Book not found with ID: " + bookid);
    	    }

    	    return book;
    }

    // Update book
    public Book updateBook(int bookid, Book updatedBook) {

    	Book existingBook = getBookById(bookid);

        // Check duplicate ISBN
        if (!existingBook.getIsbn().equals(updatedBook.getIsbn())
                && bookRepository.existsByIsbn(updatedBook.getIsbn())) {

            throw new DuplicateResourceException(
                    "A book with this ISBN already exists");
        }

        // Count copies currently issued
        long issuedCopies = bookIssueRepository
                .countByBookBookidAndStatus(bookid, "ISSUED");

        // New total copies cannot be less than issued copies
        if (updatedBook.getTotalCopies() < issuedCopies) {

            throw new InvalidOperationException(
                    "Total copies cannot be less than the number of currently issued copies");
        }

        // Update book details
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setTotalCopies(updatedBook.getTotalCopies());

        // Calculate available copies correctly
        existingBook.setAvailableCopies(
                updatedBook.getTotalCopies() - (int) issuedCopies);

        return universalRepository.save(existingBook);
    }

    // Delete book
    public void deleteBook(int bookid) {
    	Book book = getBookById(bookid);

        long activeIssues = bookIssueRepository
                .countByBookBookidAndStatus(bookid, "ISSUED");

        if (activeIssues > 0) {
            throw new InvalidOperationException(
                    "Cannot delete this book because it has active issues");
        }

        // Soft delete
        book.setActive(false);

        universalRepository.save(book);
    }

    // Get books by category
    @Transactional(readOnly = true)
    public List<Book> getBooksByCategory(String category) {
    		return bookRepository.findByCategoryAndActiveTrue(category);
    }

}
