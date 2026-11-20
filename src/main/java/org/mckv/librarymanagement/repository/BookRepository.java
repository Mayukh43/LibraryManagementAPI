package org.mckv.librarymanagement.repository;

import java.util.List;

import org.mckv.librarymanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	
	 // Find books by category
    List<Book> findByCategory(String category);

    // Check whether an ISBN already exists
    boolean existsByIsbn(String isbn);
    
    List<Book> findByActiveTrue();

    List<Book> findByCategoryAndActiveTrue(String category);

}
