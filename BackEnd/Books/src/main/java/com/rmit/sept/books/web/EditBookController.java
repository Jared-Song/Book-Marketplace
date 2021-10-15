package com.rmit.sept.books.web;

import com.rmit.sept.books.BookApplication;
import com.rmit.sept.books.model.Book;
import com.rmit.sept.books.model.BookForm;
import com.rmit.sept.books.services.BookService;
import com.rmit.sept.books.services.EditBookService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/editBook")
public class EditBookController {
    private static final Logger LOGGER = LogManager.getLogger(BookApplication.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private EditBookService editBookService;

    // edit a book's details
    @PostMapping("/{Id}")
    public ResponseEntity<?> editBook(@RequestBody BookForm bookForm, @PathVariable Long Id) {
        LOGGER.info("Finding book with ID " + Id);
        Book book = bookService.findById(Id);
        if (book != null) {
            if (bookForm.getSeller() == null && bookForm.getSellerId() == null) {
                LOGGER.warn(
                        "The book's information is invalid and the new book cannot be added - the seller does not exist");
                return new ResponseEntity<String>("Unable to add the new book details, User to tie to not found!.",
                        HttpStatus.NOT_FOUND);
            }
            Book updateBook = editBookService.updateBook(bookForm, book);
            if (updateBook != null) {
                LOGGER.info("The details have been successfully updated for the book with ID " + Id);
                return new ResponseEntity<String>("Successfully updated book details", HttpStatus.OK);
            } else {
                LOGGER.warn(
                        "The edited book details could not be saved as another book with the same details already exists");
                return new ResponseEntity<String>("Unable to save details for book, a copy of the book already exists.",
                        HttpStatus.CONFLICT);
            }

        } else {
            LOGGER.warn("Book with ID " + Id + " was not found");
            return new ResponseEntity<String>("Book with ID " + Id + " was not found", HttpStatus.NOT_FOUND);
        }
    }

}