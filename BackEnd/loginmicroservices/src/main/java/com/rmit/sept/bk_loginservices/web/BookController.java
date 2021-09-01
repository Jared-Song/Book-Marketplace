package com.rmit.sept.bk_loginservices.web;

import javax.validation.Valid;

import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.BookForm;
import com.rmit.sept.bk_loginservices.services.BookService;
import com.rmit.sept.bk_loginservices.services.EditBookService;
import com.rmit.sept.bk_loginservices.services.MapValidationErrorService;

import org.apache.catalina.connector.Response;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private EditBookService editBookService;

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

        return new ResponseEntity<Book>(newBook, HttpStatus.CREATED);
    }

}
