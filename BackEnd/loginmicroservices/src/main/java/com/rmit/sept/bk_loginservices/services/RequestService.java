package com.rmit.sept.bk_loginservices.services;

import org.springframework.stereotype.Service;

import com.rmit.sept.bk_loginservices.Repositories.BookRepository;
import com.rmit.sept.bk_loginservices.Repositories.RequestRepository;
import com.rmit.sept.bk_loginservices.Repositories.UserRepository;
import com.rmit.sept.bk_loginservices.model.BookStatus;
import com.rmit.sept.bk_loginservices.model.Request;
import com.rmit.sept.bk_loginservices.model.RequestType;
import com.rmit.sept.bk_loginservices.model.Role;
import com.rmit.sept.bk_loginservices.model.UserStatus;

import org.springframework.beans.factory.annotation.Autowired;
import com.rmit.sept.bk_loginservices.exceptions.RequestException;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    // retrieve a request from the repository with a given id
    public Request findById(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        return request;
    }

    // save a new request into the repository, returning null if the request already exists
    public Request saveRequest(Request request) {
        // boolean requestExists = requestRepository.requestExists(request.getId(), request.getRequestType());

        // if (requestExists) {
        //     return null;
        // } else {
            try {
                request.setId(request.getId());
                return requestRepository.save(request);
            } catch (Exception e) {
                throw new RequestException("Request already exists");
            }
        // }
    }

    // delete a request from the repository with a given id
    public void deleteRequestById(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        try {
            requestRepository.delete(request);
        } catch (IllegalArgumentException e) {
            throw new RequestException("Request with ID " + requestId + " does not exist");
        }
    }

    // approve a request with given id
    public Request approveRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        Long objectId = request.getId();

        if (request != null) {
            // approving a new business user
            if (request.getRequestType() == RequestType.NEW_BUSINESS_USER) {
                userRepository.setUserStatus(UserStatus.ENABLED, objectId);

                // approving a new book listing
            } else if (request.getRequestType() == RequestType.NEW_BOOK_LISTING) {
                bookRepository.setBookStatus(BookStatus.AVAILABLE, objectId);

                // approving a regular user changing to a business user
            } else if (request.getRequestType() == RequestType.TO_BUSINESS_USER) {
                userRepository.setUserRole(Role.USER_BUSINESS, objectId);
                userRepository.setUserStatus(UserStatus.ENABLED, objectId);

                // approving a business user changing to a regular user
            } else if (request.getRequestType() == RequestType.TO_REG_USER) {
                userRepository.setUserRole(Role.USER_NORMAL, objectId);
                userRepository.setUserStatus(UserStatus.ENABLED, objectId);
            }
        }
        return request;
    }

    // retrieve all requests in the repository
    public Iterable<Request> findAllRequests() {
        return requestRepository.findAll();
    }
}
