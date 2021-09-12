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

        @Query("SELECT s FROM Book s WHERE s.title LIKE %:title%")
        public Iterable<Book> findByTitle(@Param("title") String title);

        @Query("SELECT s FROM Book s WHERE s.authorFirstName LIKE %:authorFirstName%")
        public Iterable<Book> findByAuthorFirstName(@Param("authorFirstName")String authorFirstName);

        @Query("SELECT s FROM Book s WHERE s.authorLastName LIKE %:authorLastName%")
        public Iterable<Book> findByAuthorLastName(@Param("authorLastName") String authorLastName);

        @Query(value = "SELECT s FROM Book s WHERE s.sellerId = ?1", nativeQuery = true)
        public Iterable<Book> findBySellerId(@Param("sellerId") Long sellerId);

        @Query("SELECT s FROM Book s WHERE s.isbn LIKE %:isbn%")
        public Iterable<Book> findByisbn(@Param("isbn") int isbn);

        @Query(value = "SELECT s FROM Book s WHERE s.price BETWEEN low AND high", nativeQuery = true)
        public Iterable<Book> findByPrice(@Param("price") double low, @Param("price") double high);

        @Query(value = "SELECT s FROM Book s WHERE s.createdAt BETWEEN start AND end", nativeQuery = true)
        public Iterable<Book> findByDate(Date start, Date end);

        @Transactional
        @Modifying
        @Query(value = "UPDATE Book s SET s.sellerId = :sellerId, s.title = :title, s.authorFirstName = :authorFirstName, s.authorLastName = :authorLastName, s.isbn = :isbn, s.price = :price, s.quantity = :quantity, s.imageURL = :imageURL WHERE s.id = :id")
        public void updatebook(@Param("sellerId") Long sellerId, @Param("title") String title,
                        @Param("authorFirstName") String authorFirstName,
                        @Param("authorLastName") String authorLastName, @Param("isbn") int isbn,
                        @Param("price") double price, @Param("quantity") int quantity,
                        @Param("imageURL") String imageURL, @Param("id") Long id);

        @Query("SELECT COUNT(*)>0 FROM Book s WHERE s.sellerId = :sellerId AND s.title = :title AND s.authorFirstName = :authorFirstName AND s.authorLastName = :authorLastName AND s.isbn = :isbn")
        boolean bookExists(@Param("sellerId") Long sellerId, @Param("title") String title,
                        @Param("authorFirstName") String authorFirstName,
                        @Param("authorLastName") String authorLastName, @Param("isbn") int isbn);

        @Override
        Iterable<Book> findAll();
}
