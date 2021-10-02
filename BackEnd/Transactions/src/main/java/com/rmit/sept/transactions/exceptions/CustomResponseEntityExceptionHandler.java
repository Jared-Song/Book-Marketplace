package com.rmit.sept.transactions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleNotFound(NotFoundException ex, WebRequest request){
        NotFoundResponse exceptionResponse = new NotFoundResponse(ex.getMessage());
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleNotAcceptable(NotAcceptableException ex, WebRequest request){
        NotAcceptableResponse exceptionResponse = new NotAcceptableResponse(ex.getMessage());
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleConflict(ConflictException ex, WebRequest request){
        ConflictResponse exceptionResponse = new ConflictResponse(ex.getMessage());
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.CONFLICT);
    }
}
