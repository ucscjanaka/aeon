package com.library.aeon.service;


import com.library.aeon.model.Book;
import com.library.aeon.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

//    @Test
//    void testRegisterBook() {
//        // Prepare test data
//        BookDto bookDto = new BookDto();
//        bookDto.setTitle("Test Book");
//
//        Book book = new Book();
//        book.setTitle("Test Book");
//
//        BookDto mappedBookDto = new BookDto();
//        mappedBookDto.setTitle("Test Book");
//        mappedBookDto.setId(1L);
//
//        // Mock behavior
//        when(modelMapper.map(bookDto, Book.class)).thenReturn(book);
//        when(bookRepository.save(book)).thenReturn(book);
//        when(modelMapper.map(book, BookDto.class)).thenReturn(mappedBookDto);
//
//        // Perform the call
//        BookDto result = bookService.registerBook(bookDto);
//
//        // Assertions
//        assertNotNull(result);
//        assertEquals(mappedBookDto.getId(), result.getId());
//        assertEquals(mappedBookDto.getTitle(), result.getTitle());
//    }


    @Test
    void testGetAllBooks() {
        // Prepare test data
        List<Book> books = Arrays.asList(
                new Book(1L, "Book 1", "Author 1", "123456", null, null, 0),
                new Book(2L, "Book 2", "Author 2", "789012", null, null, 0)
        );

        // Mock behavior
        when(bookRepository.findAll()).thenReturn(books);

        // Perform the call
        List<Book> result = bookService.getAllBooks();

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testBorrowBook() {
        // Prepare test data
        String isbnNumber = "123456";
        int userId = 1;

        Book bookToBorrow = new Book(1L, "Test Book", "Author", isbnNumber, null, null, 0);
        List<Book> books = Collections.singletonList(bookToBorrow);

        // Mock behavior
        when(bookRepository.findByIsbnNumber(isbnNumber)).thenReturn(books);
        when(bookRepository.save(any(Book.class))).thenReturn(bookToBorrow);

        // Perform the call
        Book result = bookService.borrowBook(isbnNumber, userId);

        // Assertions
        assertNotNull(result);
        assertEquals(userId, result.getBorrowerId());
        assertNotNull(result.getBorrowDate());
    }

    @Test
    void testReturnBook() {
        // Prepare test data
        long bookId = 1L;
        Book bookToReturn = new Book(bookId, "Test Book", "Author", "123456", new Date(), null, 0);

        // Mock behavior
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookToReturn));
        when(bookRepository.save(any(Book.class))).thenReturn(bookToReturn);

        // Perform the call
        Book result = bookService.returnBook((int) bookId);

        // Assertions
        assertNotNull(result);
        assertNotNull(result.getReturnDate());
    }
}
