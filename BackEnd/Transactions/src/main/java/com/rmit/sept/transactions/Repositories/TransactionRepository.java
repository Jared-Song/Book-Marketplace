package com.rmit.sept.transactions.Repositories;

import com.rmit.sept.transactions.model.Book;
import com.rmit.sept.transactions.model.Transaction;
import com.rmit.sept.transactions.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    public Iterable<Transaction> findByBuyer(User buyer);

    public Iterable<Transaction> findByBook(Book book);

    public Iterable<Transaction> findByBookIn(Iterable<Book> books);

    public Iterable<Transaction> findByPrice(double price);

    Transaction getById(Long id);

    @Override
    Iterable<Transaction> findAll();
}
