package com.aeon.library.exceptions;

/**
 * Exception thrown when a book is not found.
 */
public class BookNotFoundException extends RuntimeException {

    /**
     * Constructs a new BookNotFoundException with the specified detail message.
     *
     * @param message the detail message (which is appended to "Book not found")
     */
    public BookNotFoundException(String message) {
        super("Book not found: " + message);
    }

}
