package com.rmit.sept.bk_loginservices.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.bk_loginservices.model.Book;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    @Query(value = "SELECT s FROM Book s WHERE s.bookId = ?1", nativeQuery = true)
    public Iterable<Book> findByBookId(@Param("bookId") Long bookId);

    public Iterable<Book> findByTitle(String title);

    public Iterable<Book> findByAuthorFirstName(String firstName);

    public Iterable<Book> findByAuthorLastName(String lastName);

    public Iterable<Book> findBySellerId(Long sellerId);
    
    public Iterable<Book> findByisbn(int isbn);

    @Query(value = "SELECT s FROM Book s WHERE s.price BETWEEN low AND high", nativeQuery = true)
    public Iterable<Book> findByPrice(float low, float high);

    @Query(value = "SELECT s FROM Book s WHERE s.createdAt BETWEEN start AND end", nativeQuery = true)
    public Iterable<Book> findByDate(Date start, Date end);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Book s SET s.custId = :sellerId WHERE s.id = :id", nativeQuery = true)
    public void updateBook(@Param("sellerId") Long sellerId, @Param("id") Long id);

    @Override
    Iterable<Book> findAll();
}
