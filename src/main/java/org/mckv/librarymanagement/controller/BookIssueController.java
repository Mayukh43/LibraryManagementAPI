package org.mckv.librarymanagement.controller;

import java.util.List;

import org.mckv.librarymanagement.entity.BookIssue;
import org.mckv.librarymanagement.services.BookIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/issues")
public class BookIssueController {

    @Autowired
    BookIssueService bookIssueService;

    // Issue a book to a student
    @PostMapping("/{studentId}/{bookId}")
    public ResponseEntity<BookIssue> issueBook(
            @PathVariable int studentId,
            @PathVariable int bookId) {

        BookIssue bookIssue = bookIssueService.issueBook(studentId, bookId);

        return new ResponseEntity<>(bookIssue, HttpStatus.CREATED);
    }

    // Get all book issue records
    @GetMapping
    public List<BookIssue> getAllIssues() {
        return bookIssueService.getAllIssues();
    }

    // Get issues belonging to a particular student
    @GetMapping("/student/{studentId}")
    public List<BookIssue> getIssuesByStudent(
            @PathVariable int studentId) {

        return bookIssueService.getIssuesByStudent(studentId);
    }

    // Return an issued book
    @PutMapping("/return/{issueId}")
    public BookIssue returnBook(@PathVariable int issueId) {
        return bookIssueService.returnBook(issueId);
    }

    // Get overdue books
    @GetMapping("/overdue")
    public List<BookIssue> getOverdueIssues() {
        return bookIssueService.getOverdueIssues();
    }
}
