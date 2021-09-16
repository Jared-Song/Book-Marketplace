package com.rmit.sept.bk_loginservices.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.Quality;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

        // find books with a specific title
        @Query("SELECT s FROM Book s WHERE LOWER(s.title) LIKE %:title%")
        public Iterable<Book> findByTitle(@Param("title") String title);

        // find books with a specific author's name
        @Query("SELECT s FROM Book s WHERE LOWER(s.authorName) LIKE %:authorName%")
        public Iterable<Book> findByAuthorName(@Param("authorName") String authorName);

        // find books with a specific category
        @Query("SELECT s FROM Book s WHERE LOWER(s.category) LIKE %:category%")
        public Iterable<Book> findByCategory(@Param("category") String category);

        // find books with a specific seller's id
        @Query(value = "SELECT * FROM Book WHERE seller_Id = :sellerId", nativeQuery = true)
        public Iterable<Book> findBySellerId(@Param("sellerId") Long sellerId);

        // find books with a specific isbn
        @Query(value = "SELECT * FROM Book WHERE (isbn REGEXP :isbn)", nativeQuery = true)
        public Iterable<Book> findByisbn(@Param("isbn") int isbn);

        // find all new books
        @Query("SELECT s FROM Book s WHERE s.quality LIKE 'NEW'")
        public Iterable<Book> findAllNew();

        // find all used books
        @Query("SELECT s FROM Book s WHERE s.quality LIKE 'USED'")
        public Iterable<Book> findAllUsed();

        // @Query(value = "SELECT * FROM Book WHERE price BETWEEN low AND high", nativeQuery = true)
        // public Iterable<Book> findByPrice(@Param("price") double low, @Param("price") double high);

        // @Query(value = "SELECT * FROM Book WHERE createdAt BETWEEN start AND end", nativeQuery = true)
        // public Iterable<Book> findByDate(Date start, Date end);

        // update a book's details
        @Transactional
        @Modifying
        @Query(value = "UPDATE Book s SET s.sellerId = :sellerId, s.title = :title, s.authorName = :authorName, s.price = :price, s.category = :category, s.isbn = :isbn, s.quantity = :quantity, s.imageURL = :imageURL, s.quality = :quality WHERE s.id = :id")
        public void updatebook(@Param("sellerId") Long sellerId, @Param("title") String title,
                        @Param("authorName") String authorName, @Param("price") double price,
                        @Param("category") String category, @Param("isbn") int isbn, @Param("quantity") int quantity,
                        @Param("imageURL") String imageURL, @Param("quality") Quality quality, @Param("id") Long id);

        // returns true if a book with the given parameters exists
        @Query("SELECT COUNT(*)>0 FROM Book s WHERE s.sellerId = :sellerId AND LOWER(s.title) = :title AND LOWER(s.authorName) = :authorName AND LOWER(s.category) = :category AND s.isbn = :isbn AND s.quality = :quality")
        boolean bookExists(@Param("sellerId") Long sellerId, @Param("title") String title,
                        @Param("authorName") String authorName, @Param("category") String category,
                        @Param("isbn") int isbn, @Param("quality") Quality quality);

        @Override
        Iterable<Book> findAll();
}
