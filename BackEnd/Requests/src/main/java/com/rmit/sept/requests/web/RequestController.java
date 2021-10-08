package com.rmit.sept.requests.web;

import javax.validation.Valid;

import com.rmit.sept.requests.model.Request;
import com.rmit.sept.requests.model.User;
import com.rmit.sept.requests.services.MapValidationErrorService;
import com.rmit.sept.requests.services.RequestService;
import com.rmit.sept.requests.services.UserService;

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
    private UserService userService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    // approve requests
    @PostMapping(path = "/approve/{requestId}")
    public ResponseEntity<?> approveRequest(@PathVariable Long requestId) {
        Request request = requestService.findById(requestId);

        if (request != null) {
            requestService.approveRequest(requestId);
            requestService.deleteRequestById(requestId);
            return new ResponseEntity<String>("Request with ID " + requestId + " was approved", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Request with ID " + requestId + " was not found", HttpStatus.NOT_FOUND);
        }
    }

    // get all the requests in the repository
    @GetMapping(path = "/all")
    public Iterable<Request> getAllRequests() {
        return requestService.findAllRequests();
    }

    // delete a request with a specific id
    @DeleteMapping(path = "/{requestId}")
    public ResponseEntity<?> deleteRequest(@PathVariable Long requestId) {
        Request request = requestService.findById(requestId);

        if (request != null) {
            requestService.deleteRequestById(requestId);
            return new ResponseEntity<String>("Request with ID " + requestId + " was deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Request with ID " + requestId + " was not found", HttpStatus.NOT_FOUND);
        }
    }

    /// add a new request
    @PostMapping("/new")
    public ResponseEntity<?> addNewRequest(@Valid @RequestBody Request request, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null)
            return errorMap;
        if (request.getUserId() == null)
            return new ResponseEntity<String>("Unable to add the new request, User id not given!.",
                    HttpStatus.NOT_ACCEPTABLE);
        User user = userService.findById(request.getUserId());
        if (user == null)
            return new ResponseEntity<String>("Unable to add the new request, User to tie to not found!.",
                    HttpStatus.NOT_FOUND);
        request.setUser(user);
        Request newRequest = requestService.saveRequest(request);
        if (newRequest != null) {
            return new ResponseEntity<Request>(newRequest, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Unable to add the new request, a copy of the request already exists.",
                    HttpStatus.CONFLICT);
        }
    }

}