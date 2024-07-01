package com.library.aeon.repository;


import com.library.aeon.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByIsbnNumber(String isbnNumnber);

}