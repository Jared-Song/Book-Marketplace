package com.rmit.sept.bk_loginservices.web;

import javax.validation.Valid;

import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.User;
import com.rmit.sept.bk_loginservices.services.BookService;
import com.rmit.sept.bk_loginservices.services.UserService;
import com.rmit.sept.bk_loginservices.services.MapValidationErrorService;

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

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    // get all the books in the catalogue
    @GetMapping(path = "/all")
    public Iterable<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    // get a book from the catalogue with a specific id
    @GetMapping(path = "/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Long bookId) {
        Book book = bookService.findById(bookId);

        if (book != null) {
            return new ResponseEntity<Book>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Book with ID " + bookId + " was not found", HttpStatus.ACCEPTED);
        }
    }

    // delete a book in the catalogue with a specific id
    @DeleteMapping(path = "/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        Book book = bookService.findById(bookId);

        if (book != null) {
            bookService.deleteBookById(bookId);
            return new ResponseEntity<String>("Book with ID " + bookId + " was deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Book with ID " + bookId + " was not found", HttpStatus.ACCEPTED);
        }

    }

    // add a new book to the catalogue
    @PostMapping("/new")
    public ResponseEntity<?> addNewBook(@Valid @RequestBody Book book, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null)
            return errorMap;

        if (book.getSellerId() == null) return new ResponseEntity<String>("Unable to add the new book, User id not given!.", HttpStatus.ACCEPTED); 
        User user = userService.findById(book.getSellerId());
        if (user == null) return new ResponseEntity<String>("Unable to add the new book, User to tie to not found!.", HttpStatus.ACCEPTED);
        book.setSeller(user);
        Book newBook = bookService.saveBook(book);
        if (newBook != null) {
            return new ResponseEntity<Book>(newBook, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<String>("Unable to add the new book, a copy of the book already exists.", HttpStatus.ACCEPTED);
        }
    }
}
