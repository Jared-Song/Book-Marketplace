package com.rmit.sept.bk_loginservices.Repositories;

import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import com.rmit.sept.bk_loginservices.model.Transaction;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    public Iterable<Transaction> findByBuyerID(Long buyerID);

    public Iterable<Transaction> findBySellerID(Long sellerID);

    public Iterable<Transaction> findByBookID(Long bookID);

    public Iterable<Transaction> findByPrice(double price);

    Transaction getById(Long id);

    @Query(value = "SELECT s FROM Transaction s WHERE s.createdAt BETWEEN start AND end", nativeQuery = true)
    public Iterable<Transaction> findByDate(Date start, Date end);

    @Override
    Iterable<Transaction> findAll();
}
