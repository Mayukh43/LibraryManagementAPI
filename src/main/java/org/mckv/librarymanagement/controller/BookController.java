package org.mckv.librarymanagement.controller;

import java.util.List;

import org.mckv.librarymanagement.entity.Book;
import org.mckv.librarymanagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")

public class BookController {
	
	@Autowired
    BookService bookService;

    // Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Get book by ID
    @GetMapping("/{id}")
    public Book getBook(@PathVariable int id) {
        return bookService.getBookById(id);
    }

    // Create a new book
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // Update book
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable int id, @Valid @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    // Delete book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Find books by category
    @GetMapping("/category/{category}")
    public List<Book> getBooksByCategory(@PathVariable String category) {
        return bookService.getBooksByCategory(category);
    }


}
