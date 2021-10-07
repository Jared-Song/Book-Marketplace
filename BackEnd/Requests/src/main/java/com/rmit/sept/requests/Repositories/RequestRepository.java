package com.rmit.sept.requests.Repositories;

import com.rmit.sept.requests.model.Request;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    @Override
    Iterable<Request> findAll();
}
