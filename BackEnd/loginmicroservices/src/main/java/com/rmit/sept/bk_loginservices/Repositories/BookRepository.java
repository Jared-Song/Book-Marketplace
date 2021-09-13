package com.rmit.sept.bk_loginservices.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.bk_loginservices.model.Book;

import com.rmit.sept.bk_loginservices.model.User;
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

    public Iterable<Book> findByAuthorFullName(String fullName);

    public Iterable<Book> findBySellerId(User sellerId);

    public Iterable<Book> findByisbn(int isbn);

    @Query(value = "SELECT s FROM Book s WHERE s.price BETWEEN low AND high", nativeQuery = true)
    public Iterable<Book> findByPrice(float low, float high);

    @Query(value = "SELECT s FROM Book s WHERE s.createdAt BETWEEN start AND end", nativeQuery = true)
    public Iterable<Book> findByDate(Date start, Date end);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Book s SET s.sellerId = :sellerId, s.title = :title, s.authorFullName = :authorFullName, s.isbn = :isbn, s.price = :price, s.quantity = :quantity, s.imageURL = :imageURL WHERE s.id = :id")
    public void updatebook(@Param("sellerId") User sellerId, @Param("title") String title,
            @Param("authorFullName") String authorFullName,
            @Param("isbn") int isbn, @Param("price") double price, @Param("quantity") int quantity,
            @Param("imageURL") String imageURL, @Param("id") Long id);

    @Query("SELECT COUNT(*)>0 FROM Book s WHERE s.sellerId = :sellerId AND s.title = :title AND s.authorFullName = :authorFullName AND s.isbn = :isbn")
    boolean bookExists(@Param("sellerId") User sellerId, @Param("title") String title,
                       @Param("authorFullName") String authorFullName,
                       @Param("isbn") int isbn);

    @Override
    Iterable<Book> findAll();
}
