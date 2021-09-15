package com.rmit.sept.bk_loginservices.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.BookForm;
import com.rmit.sept.bk_loginservices.services.BookService;
import com.rmit.sept.bk_loginservices.services.EditBookService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/editBook")
public class EditBookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private EditBookService editBookService;

    @PostMapping("/{Id}")
    public ResponseEntity<?> editBook(@RequestBody BookForm bookForm, @PathVariable Long Id) {
        Book book = bookService.findById(Id);
        if (book != null) {
            Book updateBook = editBookService.updateBook(bookForm, book);
            if (updateBook != null) {
                return new ResponseEntity<String>("Successfully updated book details", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Unable to save details for book, a copy of the book already exists.",
                        HttpStatus.ACCEPTED);
            }

        } else {
            return new ResponseEntity<String>("Book with ID " + Id + " was not found", HttpStatus.ACCEPTED);
        }
    }

}