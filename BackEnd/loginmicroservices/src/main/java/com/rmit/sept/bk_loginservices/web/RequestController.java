package com.rmit.sept.bk_loginservices.web;

import javax.validation.Valid;

import com.rmit.sept.bk_loginservices.model.Request;
import com.rmit.sept.bk_loginservices.services.RequestService;
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
@RequestMapping("/api/requests")
public class RequestController {
    @Autowired
    private RequestService requestService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;


    @GetMapping(path = "/all")
    public Iterable<Request> getAllRequests() {
        return requestService.findAllRequests();
    }

    @DeleteMapping(path = "/{requestId}")
    public ResponseEntity<?> deleteRequest(@PathVariable Long requestId) {
        requestService.deleteRequestById(requestId);
        return new ResponseEntity<String>("Request with ID " + requestId + " was deleted", HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<?> addNewBook(@Valid @RequestBody Request request, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null)
            return errorMap;

        Request newRequest = requestService.saveRequest(request);
        if (newRequest != null) {
            return new ResponseEntity<Request>(newRequest, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Unable to save details for book, a copy of the book already exists.",
                    HttpStatus.ACCEPTED);
        }
    }


}