package com.rmit.sept.bk_loginservices.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Date;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Book findByBookId(Long bookId);

    public Iterable<Book> findByTitle(String title);

    public Iterable<Book> findByAuthorFirstName(String firstName);

    public Iterable<Book> findByAuthorLastName(String lastName);

    public Iterable<Book> findBySeller(String sellerName);

    public Iterable<Book> findBySellerId(Long sellerId);
    
    public Iterable<Book> findByISBN(int isbn);

    public Iterable<Book> findByPrice(float low, float high);

    public Iterable<Book> findByDate(Date start, Date end);

    public void updateBook(Long bookId);

    @Override
    Iterable<Book> findAll();
}
