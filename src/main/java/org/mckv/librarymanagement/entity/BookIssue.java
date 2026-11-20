package org.mckv.librarymanagement.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "book_issues")
public class BookIssue {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "issue_id")
	    private int issueId;

	    @NotNull(message = "Student is required")
	    @ManyToOne
	    @JoinColumn(name = "student_id", nullable = false)
	    private Student student;

	    @NotNull(message = "Book is required")
	    @ManyToOne
	    @JoinColumn(name = "book_id", nullable = false)
	    private Book book;

	    @NotNull(message = "Issue date is required")
	    @Column(name = "issue_date", nullable = false)
	    private LocalDate issueDate;

	    @NotNull(message = "Due date is required")
	    @Column(name = "due_date", nullable = false)
	    private LocalDate dueDate;

	    @Column(name = "return_date")
	    private LocalDate returnDate;

	    @Column(name = "status", nullable = false, length = 20)
	    private String status = "ISSUED";

	    public BookIssue() {
	    }
	    
	    // Parameterized constructor
	    
		public BookIssue(@NotNull(message = "Student is required") Student student,
				@NotNull(message = "Book is required") Book book,
				@NotNull(message = "Issue date is required") LocalDate issueDate,
				@NotNull(message = "Due date is required") LocalDate dueDate, LocalDate returnDate, String status) {
			super();
			this.student = student;
			this.book = book;
			this.issueDate = issueDate;
			this.dueDate = dueDate;
			this.returnDate = returnDate;
			this.status = status;
		}

		public int getIssueId() {
			return issueId;
		}

		public void setIssueId(int issueId) {
			this.issueId = issueId;
		}

		public Student getStudent() {
			return student;
		}

		public void setStudent(Student student) {
			this.student = student;
		}

		public Book getBook() {
			return book;
		}

		public void setBook(Book book) {
			this.book = book;
		}

		public LocalDate getIssueDate() {
			return issueDate;
		}

		public void setIssueDate(LocalDate issueDate) {
			this.issueDate = issueDate;
		}

		public LocalDate getDueDate() {
			return dueDate;
		}

		public void setDueDate(LocalDate dueDate) {
			this.dueDate = dueDate;
		}

		public LocalDate getReturnDate() {
			return returnDate;
		}

		public void setReturnDate(LocalDate returnDate) {
			this.returnDate = returnDate;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		@Override
		public String toString() {
			return "BookIssue [issueId=" + issueId + ", student=" + student + ", book=" + book + ", issueDate="
					+ issueDate + ", dueDate=" + dueDate + ", returnDate=" + returnDate + ", status=" + status + "]";
		}
    
	    
	        
	
}
