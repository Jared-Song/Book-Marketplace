package com.rmit.sept.transactions.Repositories;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.rmit.sept.transactions.model.Book;
import com.rmit.sept.transactions.model.Transaction;
import com.rmit.sept.transactions.model.TransactionStatus;
import com.rmit.sept.transactions.model.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    public Iterable<Transaction> findByBuyer(User buyer);

    public Iterable<Transaction> findByBook(Book book);

    public Iterable<Transaction> findByBookIn(Iterable<Book> books);

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
