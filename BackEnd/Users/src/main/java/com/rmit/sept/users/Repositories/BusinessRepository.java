package com.rmit.sept.users.Repositories;

import java.util.Date;

import com.rmit.sept.users.model.Business;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends CrudRepository<Business, Long> {

    @Query(value = "SELECT s FROM Business s WHERE s.created_At BETWEEN start AND end", nativeQuery = true)
    public Iterable<Business> findByDate(Date start, Date end);

    @Override
    Iterable<Business> findAll();
}
