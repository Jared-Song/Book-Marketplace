package com.rmit.sept.books.web;

import com.rmit.sept.books.model.Book;
import com.rmit.sept.books.model.BookForm;
import com.rmit.sept.books.services.BookService;
import com.rmit.sept.books.services.EditBookService;

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

    @Autowired
    private BookService bookService;

    @Autowired
    private EditBookService editBookService;

    // edit a book's details
    @PostMapping("/{Id}")
    public ResponseEntity<?> editBook(@RequestBody BookForm bookForm, @PathVariable Long Id) {
        Book book = bookService.findById(Id);
        if (book != null) {
            Book updateBook = editBookService.updateBook(bookForm, book);
            if (updateBook != null) {
                return new ResponseEntity<String>("Successfully updated book details", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Unable to save details for book, a copy of the book already exists.",
                        HttpStatus.CONFLICT);
            }

        } else {
            return new ResponseEntity<String>("Book with ID " + Id + " was not found", HttpStatus.NOT_FOUND);
        }
    }

}