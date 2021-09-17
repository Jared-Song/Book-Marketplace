package com.rmit.sept.bk_loginservices.Repositories;

import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import com.rmit.sept.bk_loginservices.model.Business;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;


@Repository
public interface BusinessRepository extends CrudRepository<Business, Long> {

    @Query(value = "SELECT s FROM Business s WHERE s.created_At BETWEEN start AND end", nativeQuery = true)
    public Iterable<Business> findByDate(Date start, Date end);

    @Override
    Iterable<Business> findAll();
}
