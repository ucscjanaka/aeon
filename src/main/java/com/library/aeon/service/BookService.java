package com.library.aeon.service;


import com.library.aeon.dto.BookDto;
import com.library.aeon.model.Book;
import com.library.aeon.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing books.
 */
@Service
public class BookService {


    private final ModelMapper modelMapper = new ModelMapper();
    Logger logger = LoggerFactory.getLogger(BookService.class);
    @Autowired
    private BookRepository bookRepository;

    /**
     * Registers a new book.
     *
     * @param bookDto The BookDto object containing book details.
     * @return The registered BookDto object.
     */
    public BookDto registerBook(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        return modelMapper.map(bookRepository.save(book), BookDto.class);
    }

    /**
     * Retrieves all books.
     *
     * @return A list of all books.
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Borrows a book based on the ISBN number and user ID.
     *
     * @param isbnNumber The ISBN number of the book to borrow.
     * @param userId     The ID of the user borrowing the book.
     * @return The Book object representing the borrowed book.
     * @throws BookNotFoundException If no book is found with the given ISBN number.
     */
    public Book borrowBook(String isbnNumber, int userId) {
        // First check whether the book is available in the library using isbn number
        List<Book> books = bookRepository.findByIsbnNumber(isbnNumber);
        if (books.isEmpty()) {
            throw new com.aeon.library.exceptions.BookNotFoundException(isbnNumber);
        }

        // If the book exists, then check the stocks
        Optional<Book> availableBook = books.stream()
                .filter(book -> (book.getReturnDate() == null || book.getBorrowDate() == null)
                        || (book.getReturnDate() != null))
                .findFirst();

        Book book = availableBook.orElse(null);

        if (book != null) {
            book.setBorrowDate(new Date());
            book.setBorrowerId(userId);
        }

        return bookRepository.save(book);
    }

    /**
     * Returns a book based on the book ID.
     *
     * @param bookId The ID of the book to return.
     * @return The Book object representing the returned book.
     * @throws BookNotFoundException If no book is found with the given ID.
     */
    public Book returnBook(int bookId) {
        // Get the book details from the DB using bookId to update the return date.
        Book book = bookRepository.findById((long) bookId)
                .orElseThrow(() -> new com.aeon.library.exceptions.BookNotFoundException(String.valueOf(bookId)));

        // Set the return date
        book.setReturnDate(new Date());
        return bookRepository.save(book);
    }
}
