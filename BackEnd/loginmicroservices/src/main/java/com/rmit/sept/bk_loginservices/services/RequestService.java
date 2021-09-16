package com.rmit.sept.bk_loginservices.services;

import org.springframework.stereotype.Service;
import com.rmit.sept.bk_loginservices.Repositories.RequestRepository;
import com.rmit.sept.bk_loginservices.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import com.rmit.sept.bk_loginservices.exceptions.RequestException;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;

    public Request findById(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        return request;
    }

    public Request saveRequest(Request request) {
        try {
            request.setId(request.getId());
            return requestRepository.save(request);
        } catch (Exception e) {
            throw new RequestException("Request already exists");
        }
    }

    public void deleteRequestById(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        try {
            requestRepository.delete(request);
        } catch (IllegalArgumentException e) {
            throw new RequestException("Request with ID " + requestId + " does not exist");
        }
    }

    public Iterable<Request> findAllRequests() {
        return requestRepository.findAll();
    }
}
