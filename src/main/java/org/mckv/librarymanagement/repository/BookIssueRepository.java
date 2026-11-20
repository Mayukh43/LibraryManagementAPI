package org.mckv.librarymanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.mckv.librarymanagement.entity.BookIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookIssueRepository extends JpaRepository<BookIssue, Integer> {
	
	List<BookIssue> findByStudentStudentId(int studentId);

    List<BookIssue> findByStatus(String status);

    List<BookIssue> findByDueDateBeforeAndStatus(LocalDate date, String status);

    long countByStudentStudentIdAndStatus(int studentId, String status);
    
    long countByBookBookidAndStatus(int bookid, String status);
    
    long countByStudentStudentId(int studentId);

}
