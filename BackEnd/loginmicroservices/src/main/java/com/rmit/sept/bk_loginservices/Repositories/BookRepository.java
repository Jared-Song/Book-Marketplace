package com.rmit.sept.bk_loginservices.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.BookStatus;
import com.rmit.sept.bk_loginservices.model.Quality;
import com.rmit.sept.bk_loginservices.model.User;
import com.rmit.sept.bk_loginservices.model.BookImage;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

        // find books with a specific title
        @Query("SELECT s FROM Book s WHERE LOWER(s.title) LIKE %:title%")
        public Iterable<Book> findByTitle(@Param("title") String title);

        // find books with a specific author's name
        @Query("SELECT s FROM Book s WHERE LOWER(s.authorName) LIKE %:authorName%")
        public Iterable<Book> findByAuthorName(@Param("authorName") String authorName);

        // find books with a specific genre
        @Query("SELECT s FROM Book s WHERE LOWER(s.genre) LIKE %:genre%")
        public Iterable<Book> findByGenre(@Param("genre") String genre);

        // find books with a specific seller's id
        @Query(value = "SELECT * FROM Book WHERE seller_Id = :sellerId", nativeQuery = true)
        public Iterable<Book> findBySellerId(@Param("sellerId") User sellerId);

        // find books with a specific isbn
        @Query(value = "SELECT * FROM Book WHERE (isbn REGEXP :isbn)", nativeQuery = true)
        public Iterable<Book> findByisbn(@Param("isbn") int isbn);

        // find all new books
        @Query("SELECT s FROM Book s WHERE s.quality LIKE 'NEW'")
        public Iterable<Book> findAllNew();

        // find all used books
        @Query("SELECT s FROM Book s WHERE s.quality LIKE 'USED'")
        public Iterable<Book> findAllUsed();

        @Transactional
        @Modifying
        @Query(value = "UPDATE Book s SET s.sellerId = :sellerId, s.title = :title, s.authorName = :authorName, s.isbn = :isbn, s.price = :price, s.quantity = :quantity, s.imageURL = :imageURL WHERE s.id = :id")
        public void updatebook(@Param("sellerId") User sellerId, @Param("title") String title,
                @Param("authorName") String authorName,
                @Param("isbn") int isbn, @Param("price") double price, @Param("quantity") int quantity,
                @Param("imageURL") String imageURL, @Param("id") Long id);

        @Query("SELECT COUNT(*)>0 FROM Book s WHERE s.sellerId = :sellerId AND s.title = :title AND s.authorName = :authorName AND s.isbn = :isbn")
        boolean bookExists(@Param("sellerId") User sellerId, @Param("title") String title,
                        @Param("authorName") String authorName,
                        @Param("isbn") int isbn);

        @Query(value = "SELECT * FROM Book WHERE price BETWEEN low AND high", nativeQuery = true)
        public Iterable<Book> findByPrice(@Param("price") double low, @Param("price") double high);

        @Query(value = "SELECT * FROM Book WHERE createdAt BETWEEN start AND end", nativeQuery = true)
        public Iterable<Book> findByDate(Date start, Date end);
        // find all books and sort them by highest price first
        @Query(value = "SELECT * FROM Book ORDER BY PRICE ASC", nativeQuery = true)
        public Iterable<Book> sortByHighestPrice();

        // find all books and sort them by lowest price first
        @Query(value = "SELECT * FROM Book ORDER BY PRICE DESC", nativeQuery = true)
        public Iterable<Book> sortByLowestPrice();

        // find all books and sort them alphabetically by title
        @Query(value = "SELECT * FROM Book ORDER BY TITLE ASC", nativeQuery = true)
        public Iterable<Book> sortByAlphabet();

        // @Query(value = "SELECT * FROM Book WHERE createdAt BETWEEN start AND end",
        // nativeQuery = true)
        // public Iterable<Book> findByDate(Date start, Date end);

        // update a book's details
        @Transactional
        @Modifying
        @Query(value = "UPDATE Book s SET s.sellerId = :sellerId, s.title = :title, s.authorName = :authorName, s.price = :price, s.genre = :genre, s.isbn = :isbn, s.quantity = :quantity, s.imageURL = :imageURL, s.quality = :quality, s.bookStatus = :bookStatus WHERE s.id = :id")
        public void updatebook(@Param("sellerId") User sellerId, @Param("title") String title,
                        @Param("authorName") String authorName, @Param("price") double price,
                        @Param("genre") String genre, @Param("isbn") int isbn, @Param("quantity") int quantity,
                        @Param("imageURL") List<BookImage> imageURL, @Param("quality") Quality quality, 
                        @Param("bookStatus") BookStatus bookStatus, @Param("id") Long id);

        // returns true if a book with the given parameters exists
        @Query("SELECT COUNT(*)>0 FROM Book s WHERE s.sellerId = :sellerId AND LOWER(s.title) = :title AND LOWER(s.authorName) = :authorName AND LOWER(s.genre) = :genre AND s.isbn = :isbn AND s.quality = :quality")
        boolean bookExists(@Param("sellerId") User sellerId, @Param("title") String title,
                        @Param("authorName") String authorName, @Param("genre") String genre,
                        @Param("isbn") int isbn, @Param("quality") Quality quality);

        
        @Query(value = "SELECT * FROM Book WHERE s.book_id = :id", nativeQuery = true)                
        Iterable<Book> findByBookId(@Param("id") Long id);

        @Override
        Iterable<Book> findAll();
}
