package com.rmit.sept.books.web;

import javax.validation.Valid;

import com.rmit.sept.books.BookApplication;
import com.rmit.sept.books.model.Book;
import com.rmit.sept.books.model.BookImage;
import com.rmit.sept.books.model.User;
import com.rmit.sept.books.services.BookService;
import com.rmit.sept.books.services.MapValidationErrorService;
import com.rmit.sept.books.services.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/books")
public class BookController {
    private static final Logger LOGGER = LogManager.getLogger(BookApplication.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    // get all the books in the catalogue
    @GetMapping(path = "/all")
    public Iterable<Book> getAllBooks() {
        LOGGER.trace("Finding all books");
        return bookService.findAllBooks();
    }

    // get a book from the catalogue with a specific id
    @GetMapping(path = "/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Long bookId) {
        LOGGER.trace("Finding book with ID " + bookId);
        Book book = bookService.findById(bookId);

        if (book != null) {
            LOGGER.trace("Found book with ID  " + bookId);
            return new ResponseEntity<Book>(book, HttpStatus.OK);
        } else {
            LOGGER.warn("Book with ID " + bookId + " was not found");
            return new ResponseEntity<String>("Book with ID " + bookId + " was not found", HttpStatus.NOT_FOUND);
        }
    }

    // delete a book in the catalogue with a specific id
    @DeleteMapping(path = "/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        LOGGER.trace("Finding book with ID " + bookId);
        Book book = bookService.findById(bookId);
        if (book != null) {
            bookService.deleteBookById(bookId);
            LOGGER.trace("Deleted book with ID " + bookId);
            return new ResponseEntity<String>("Book with ID " + bookId + " was deleted", HttpStatus.OK);
        } else {
            LOGGER.warn("Book with ID " + bookId + " was not found");
            return new ResponseEntity<String>("Book with ID " + bookId + " was not found", HttpStatus.NOT_FOUND);
        }

    }

    // add a new book to the catalogue
    @PostMapping("/new")
    public ResponseEntity<?> addNewBook(@Valid @RequestBody Book book, BindingResult result) {
        LOGGER.trace("Adding a new book");
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            LOGGER.warn("The book's information is invalid and the new book cannot be added");
            return errorMap;
        }

        if (book.getSellerId() == null) {
            LOGGER.warn(
                    "The book's information is invalid and the new book cannot be added - the seller id is not given");
            return new ResponseEntity<String>("Unable to add the new book, User id not given!.",
                    HttpStatus.NOT_ACCEPTABLE);
        }

        User user = userService.findById(book.getSellerId());
        if (user == null) {
            LOGGER.warn(
                    "The book's information is invalid and the new book cannot be added - the seller does not exist");
            return new ResponseEntity<String>("Unable to add the new book, User to tie to not found!.",
                    HttpStatus.NOT_FOUND);
        }

        book.setSeller(user);
        Book newBook = bookService.saveBook(book);
        if (newBook != null) {
            LOGGER.trace("The new book has been successfully added");
            return new ResponseEntity<Book>(newBook, HttpStatus.OK);
        } else {
            LOGGER.warn("The new book could not be added as a copy of it already exists");
            return new ResponseEntity<String>("Unable to add the new book, a copy of the book already exists.",
                    HttpStatus.CONFLICT);
        }
    }

    // get all the books in the catalogue from a seller
    @GetMapping(path = "/sellerId/{sellerId}")
    public Iterable<Book> getAllBooksBySeller(@PathVariable Long sellerId) {
        LOGGER.trace("Finding all books that are sold by the seller with ID " + sellerId);
        return bookService.getAllBySeller(userService.findById(sellerId));
    }

    // add an image to a book
    @PostMapping(path = "/addImage/{bookId}")
    public ResponseEntity<?> addBookImage(@Valid @RequestBody BookImage bookImage, @PathVariable Long bookId) {
        LOGGER.trace("Finding book with ID " + bookId);
        Book book = bookService.findById(bookId);

        if (book != null) {
            LOGGER.trace("Adding image to book with ID " + bookId);
            BookImage savedBookImage = bookService.addBookImage(bookImage, book);
            if (savedBookImage != null) {
                LOGGER.trace("Image was added to book with ID " + bookId);
                return new ResponseEntity<String>("Image was added to book with ID " + bookId, HttpStatus.OK);
            } else {
                LOGGER.trace("Image was invalid and unable to be added to book with ID " + bookId);
                return new ResponseEntity<String>("Image was unabled to be added to book with ID " + bookId + " as image is invalid", HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            LOGGER.warn("Book with ID " + bookId + " was not found");
            return new ResponseEntity<String>("Unable to add image to book, Book not found!", HttpStatus.NOT_FOUND);
        }
    }
}
