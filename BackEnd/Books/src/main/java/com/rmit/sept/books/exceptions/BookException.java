package com.rmit.sept.books.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookException extends RuntimeException {

    public BookException(String message) {
        super(message);
    }
}
