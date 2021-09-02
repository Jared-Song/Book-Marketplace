package com.rmit.sept.bk_loginservices.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailAndUsernameAlreadyExistsException extends RuntimeException {

    public EmailAndUsernameAlreadyExistsException(String message) {
        super(message);
    }
}