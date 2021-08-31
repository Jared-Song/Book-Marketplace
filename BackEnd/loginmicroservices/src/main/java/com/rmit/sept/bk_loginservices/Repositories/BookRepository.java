package com.rmit.sept.bk_loginservices.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Date;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Book getById(Long bookId);

    public Iterable<Book> findByTitle(String title);

    public Iterable<Book> findByAuthorFirstName(String firstName);

    public Iterable<Book> findByAuthorLastName(String lastName);

    public Iterable<Book> findBySellerId(Long sellerId);

    public Iterable<Book> findByPrice(float price);

    public Iterable<Book> findByCreatedAt(Date start);

    @Override
    Iterable<Book> findAll();
}
