package com.rmit.sept.bk_loginservices.web;

import javax.validation.Valid;

import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.services.BookService;
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
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping(path = "/all")
    public Iterable<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping(path = "/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Long bookId) {
        Book book = bookService.findById(bookId);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBookById(bookId);
        return new ResponseEntity<String>("Book with ID " + bookId + " was deleted", HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<?> addNewBook(@Valid @RequestBody Book book, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null)
            return errorMap;

        Book newBook = bookService.saveBook(book);
        if (newBook != null) {
            return new ResponseEntity<Book>(newBook, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<String>("Unable to save details for book, a copy of the book already exists.",
                    HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/search/title/{title}")
    public Iterable<Book> searchByTitle(@PathVariable String title) {
        return bookService.getAllByTitle(title.toLowerCase());
    }

    @GetMapping("/search/authorFirstName/{authorFirstName}")
    public Iterable<Book> searchByAuthorFirstName(@PathVariable String authorFirstName) {
        return bookService.getAllByAuthorFirstName(authorFirstName.toLowerCase());
    }

    @GetMapping("/search/authorLastName/{authorLastName}")
    public Iterable<Book> searchByAuthorLastName(@PathVariable String authorLastName) {
        return bookService.getAllByAuthorLastName(authorLastName.toLowerCase());
    }

    @GetMapping("/search/sellerId/{sellerId}")
    public Iterable<Book> searchBySellerId(@PathVariable Long sellerId) {
        System.out.println("asdasdasdasdasdasd" + sellerId);
        return bookService.getAllBySellerId(sellerId);
    }

    @GetMapping("/search/category/{category}")
    public Iterable<Book> searchByCategory(@PathVariable String category) {
        return bookService.getAllByCategory(category.toLowerCase());
    }

    @GetMapping("/search/isbn/{isbn}")
    public Iterable<Book> searchByISBN(@PathVariable int isbn) {
        return bookService.getAllByISBN(isbn);
    }
}
