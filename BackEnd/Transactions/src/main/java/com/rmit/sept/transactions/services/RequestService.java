package com.rmit.sept.transactions.services;

import com.rmit.sept.transactions.Repositories.RequestRepository;
import com.rmit.sept.transactions.exceptions.RequestException;
import com.rmit.sept.transactions.model.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;

    // save a new request into the repository, returning null if the request already exists
    public Request saveRequest(Request request) {
            try {
                request.setId(request.getId());
                return requestRepository.save(request);
            } catch (Exception e) {
                throw new RequestException("Request already exists");
            }
    }
}
