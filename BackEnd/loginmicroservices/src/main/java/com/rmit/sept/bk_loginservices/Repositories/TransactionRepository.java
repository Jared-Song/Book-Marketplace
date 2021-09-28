package com.rmit.sept.bk_loginservices.Repositories;

import java.util.Date;

import javax.transaction.Transactional;

import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.Transaction;
import com.rmit.sept.bk_loginservices.model.TransactionStatus;
import com.rmit.sept.bk_loginservices.model.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    public Iterable<Transaction> findByBuyerID(User buyerID);

    public Iterable<Transaction> findByBookID(Book bookID);

    public Iterable<Transaction> findByPrice(double price);

    Transaction getById(Long id);

    @Query(value = "SELECT s FROM Transaction s WHERE s.created_At BETWEEN start AND end", nativeQuery = true)
    public Iterable<Transaction> findByDate(Date start, Date end);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Transaction s SET s.status = :status WHERE s.id = :id", nativeQuery = true)
    public void updateTransactionStatus(@Param("status") TransactionStatus status, @Param("id") Long id);

    @Override
    Iterable<Transaction> findAll();
}
