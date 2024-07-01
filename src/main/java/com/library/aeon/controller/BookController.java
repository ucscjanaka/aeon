package com.library.aeon.controller;


import com.library.aeon.dto.BookDto;
import com.library.aeon.model.Book;
import com.library.aeon.service.BookService;
import io.micrometer.common.lang.NonNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Book", description = "Library management APIs")
@RestController
@RequestMapping("/api")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    /**
     * Registers a new book.
     *
     * @param bookDto The BookDto object containing book details.
     * @return The registered BookDto object.
     */
    @Operation(summary = "Register a new Book", tags = {"books", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = BookDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/books")
    public ResponseEntity<BookDto> registerBook(@RequestBody BookDto bookDto) {

        try {
            BookDto newBook = bookService.registerBook(bookDto);
            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Retrieves all books.
     *
     * @return A list of all books.
     */
    @Operation(summary = "Retrieve all Books", tags = {"books", "get", "filter"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Book.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", description = "There are no books", content = {
                    @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        try {

            List<Book> books = bookService.getAllBooks();

            if (books.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(books, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     * Borrows a book by ISBN number and user ID.
     *
     * @param isbnNumber The ISBN number of the book to borrow.
     * @param userId     The ID of the user borrowing the book.
     * @return The Book object representing the borrowed book.
     */
    @Operation(summary = "Borrows a book by ISBN number and user ID",
            description = "Borrows a book by ISBN number and user ID.The response is Book object with id, title, isbnNumber and isbnNumber and borrowDate.",
            tags = {"books", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Book.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/books/{isbnNumber}/{userId}")
    public ResponseEntity<Book> borrowBook(@PathVariable @NonNull String isbnNumber, @PathVariable @NotNull int userId) {

        Book book = bookService.borrowBook(isbnNumber, userId);

        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    /**
     * Returns a book by its ID.
     *
     * @param bookId The ID of the book to return.
     * @return The Book object representing the returned book.
     */

    @Operation(summary = "Returns a book by its ID", description = "Returns a book by its ID ", tags = {"books", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = Book.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})

    @PostMapping("/books/{bookId}")
    public ResponseEntity<Book> returnBook(@PathVariable  int bookId) {

        Book book = bookService.returnBook(bookId);

        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
