package com.rmit.sept.bk_loginservices.Repositories;

import org.springframework.stereotype.Repository;

import com.rmit.sept.bk_loginservices.model.Business;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;


@Repository
public interface BusinessRepository extends CrudRepository<Business, Long> {

    @Query(value = "SELECT s FROM Business s WHERE s.created_At BETWEEN start AND end", nativeQuery = true)
    public Iterable<Business> findByDate(Date start, Date end);

    @Override
    Iterable<Business> findAll();
}
