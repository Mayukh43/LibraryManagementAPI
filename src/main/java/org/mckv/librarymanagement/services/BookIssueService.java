package org.mckv.librarymanagement.services;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.mckv.librarymanagement.entity.Book;
import org.mckv.librarymanagement.entity.BookIssue;
import org.mckv.librarymanagement.entity.Student;
import org.mckv.librarymanagement.exceptions.InvalidOperationException;
import org.mckv.librarymanagement.exceptions.ResourceNotFoundException;
import org.mckv.librarymanagement.repository.BookIssueRepository;
import org.mckv.librarymanagement.repository.UniversalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookIssueService {

	private final UniversalRepository universalRepository;
    private final BookIssueRepository bookIssueRepository;

    public BookIssueService(UniversalRepository universalRepository,
                            BookIssueRepository bookIssueRepository) {
        this.universalRepository = universalRepository;
        this.bookIssueRepository = bookIssueRepository;
    }

    // Issue a book to a student
    public BookIssue issueBook(int studentId, int bookId) {

        Student student = universalRepository.findById(
                Student.class, studentId);

        if (student == null) {
            throw new ResourceNotFoundException(
                    "Student not found with ID: " + studentId);
        }

        Book book = universalRepository.findById(
                Book.class, bookId);

        if (book == null || !book.isActive()) {
            throw new ResourceNotFoundException(
                    "Book not found with ID: " + bookId);
        }

        if (book.getAvailableCopies() <= 0) {
            throw new InvalidOperationException(
                    "No copies available for this book");
        }

        long activeIssues = bookIssueRepository
                .countByStudentStudentIdAndStatus(studentId, "ISSUED");

        if (activeIssues >= 3) {
            throw new InvalidOperationException(
                    "A student cannot have more than 3 active book issues");
        }

        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = issueDate.plusDays(14);

        BookIssue bookIssue = new BookIssue(
                student, book, issueDate, dueDate, null, "ISSUED");

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        universalRepository.save(book);

        return universalRepository.save(bookIssue);
    }

    // Get all book issue records
    @Transactional(readOnly = true)
    public List<BookIssue> getAllIssues() {
        return universalRepository.findAll(BookIssue.class);
    }

    // Get all issues belonging to a particular student
    @Transactional(readOnly = true)
    public List<BookIssue> getIssuesByStudent(int studentId) {

        Student student = universalRepository.findById(
                Student.class, studentId);

        if (student == null) {
            throw new ResourceNotFoundException(
                    "Student not found with ID: " + studentId);
        }

        return bookIssueRepository.findByStudentStudentId(studentId);
    }

    // Return an issued book
    public BookIssue returnBook(int issueId) {

        BookIssue bookIssue = universalRepository.findById(
                BookIssue.class, issueId);

        if (bookIssue == null) {
            throw new ResourceNotFoundException(
                    "Book issue not found with ID: " + issueId);
        }

        if ("RETURNED".equals(bookIssue.getStatus())) {
            throw new InvalidOperationException(
                    "This book has already been returned");
        }

        Book book = bookIssue.getBook();

        if (book.getAvailableCopies() >= book.getTotalCopies()) {
            throw new InvalidOperationException(
                    "Book availability is inconsistent; cannot increase copies");
        }

        bookIssue.setReturnDate(LocalDate.now());
        bookIssue.setStatus("RETURNED");

        book.setAvailableCopies(book.getAvailableCopies() + 1);

        universalRepository.save(book);

        return universalRepository.save(bookIssue);
    }

    // Get overdue books
    @Transactional(readOnly = true)
    public List<BookIssue> getOverdueIssues() {

        return bookIssueRepository.findByDueDateBeforeAndStatus(
                LocalDate.now(), "ISSUED");
    }
}