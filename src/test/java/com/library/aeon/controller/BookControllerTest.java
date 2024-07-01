package com.library.aeon.controller;


import com.library.aeon.dto.BookDto;
import com.library.aeon.model.Book;
import com.library.aeon.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Arrays;
import java.util.Date;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Test
    void testRegisterBook() {
        // Prepare test data
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test Book");

        BookDto mockedResponse = new BookDto();
        mockedResponse.setTitle("Test Book");
        mockedResponse.setId(1L);

        // Mock behavior
        when(bookService.registerBook(bookDto)).thenReturn(mockedResponse);

        // Perform the call
        ResponseEntity<BookDto> response = bookController.registerBook(bookDto);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockedResponse, response.getBody());
    }

    @Test
    void testGetAllBooks() {
        // Prepare test data
        List<Book> books = Arrays.asList(new Book(1L, "Test Book 1", "Author 1", "123456", null, null, 0),
                new Book(2L, "Test Book 2", "Author 2", "789012", null, null, 0));

        // Mock behavior
        when(bookService.getAllBooks()).thenReturn(books);

        // Perform the call
        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books.size(), Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void testBorrowBook() {
        // Prepare test data
        String isbnNumber = "123456";
        int userId = 1;
        Book borrowedBook = new Book(1L, "Test Book", "Author", isbnNumber, null, new Date(), userId);

        // Mock behavior
        when(bookService.borrowBook(isbnNumber, userId)).thenReturn(borrowedBook);

        // Perform the call
        ResponseEntity<Book> response = bookController.borrowBook(isbnNumber, userId);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(borrowedBook, response.getBody());
    }

    @Test
    void testReturnBook() {
        // Prepare test data
        int bookId = 1;
        Book returnedBook = new Book(1L, "Test Book", "Author", "123456", new Date(), null, 0);

        // Mock behavior
        when(bookService.returnBook(bookId)).thenReturn(returnedBook);

        // Perform the call
        ResponseEntity<Book> response = bookController.returnBook(bookId);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(returnedBook, response.getBody());
    }


}
