package com.rmit.sept.bk_loginservices.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.services.EditBookService;
import com.rmit.sept.bk_loginservices.services.MapValidationErrorService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/editBook")
public class EditBookController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private EditBookService editBookService;

    @PostMapping("")
    public ResponseEntity<?> addNewBook(@Valid @RequestBody Book book, BindingResult result) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null)
            return errorMap;

        Book editedBook = editBookService.saveOrUpdateBook(book);
        return new ResponseEntity<Book>(editedBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long Id) {
        editBookService.deleteBookById(Id);

        return new ResponseEntity<String>("Book with ID " + Id + " was deleted", HttpStatus.OK);
    }

}