package com.rmit.sept.bk_loginservices.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.BookStatus;
import com.rmit.sept.bk_loginservices.model.Quality;
import com.rmit.sept.bk_loginservices.model.User;

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
        @Query(value = "SELECT s FROM Book s WHERE s.seller = :seller")
        public Iterable<Book> findBySeller(@Param("seller") User seller);

        // find books with a specific isbn
        @Query(value = "SELECT * FROM Books WHERE CAST(isbn AS TEXT) LIKE :isbn%", nativeQuery = true)
        public Iterable<Book> findByisbn(@Param("isbn") String isbn);

        // find all new books
        @Query("SELECT s FROM Book s WHERE s.quality LIKE 'NEW'")
        public Iterable<Book> findAllNew();

        // find all used books
        @Query("SELECT s FROM Book s WHERE s.quality LIKE 'USED'")
        public Iterable<Book> findAllUsed();

        @Transactional
        @Modifying
        @Query(value = "UPDATE Book s SET s.seller = :seller, s.title = :title, s.authorName = :authorName, s.isbn = :isbn, s.price = :price, s.quantity = :quantity, s.imageURL = :imageURL WHERE s.id = :id")
        public void updatebook(@Param("seller") User seller, @Param("title") String title,
                @Param("authorName") String authorName,
                @Param("isbn") int isbn, @Param("price") double price, @Param("quantity") int quantity,
                @Param("imageURL") String imageURL, @Param("id") Long id);

        @Query("SELECT COUNT(*)>0 FROM Book s WHERE s.seller = :seller AND s.title = :title AND s.authorName = :authorName AND s.isbn = :isbn")
        boolean bookExists(@Param("seller") User seller, @Param("title") String title,
                        @Param("authorName") String authorName,
                        @Param("isbn") int isbn);

        @Query(value = "SELECT * FROM Books WHERE price BETWEEN low AND high", nativeQuery = true)
        public Iterable<Book> findByPrice(@Param("price") double low, @Param("price") double high);

        @Query(value = "SELECT * FROM Books WHERE create_At BETWEEN start AND end", nativeQuery = true)
        public Iterable<Book> findByDate(Date start, Date end);

        // find all books and sort them by highest price first
        @Query(value = "SELECT * FROM Books ORDER BY PRICE DESC", nativeQuery = true)
        public Iterable<Book> sortByHighestPrice();

        // find all books and sort them by lowest price first
        @Query(value = "SELECT * FROM Books ORDER BY PRICE ASC", nativeQuery = true)
        public Iterable<Book> sortByLowestPrice();

        // find all books and sort them alphabetically by title
        @Query(value = "SELECT * FROM Books ORDER BY BOOK_TITLE ASC", nativeQuery = true)
        public Iterable<Book> sortByAlphabet();

        // find a given number of books by most recently created
        @Query(value = "SELECT * FROM Books ORDER BY CREATE_AT DESC LIMIT :size", nativeQuery = true)
        public Iterable<Book> sortByNewestRelease(int size);

        // find a given number of books with the highest ratings
        @Query(value = "SELECT * FROM Books ORDER BY RATING DESC LIMIT :size", nativeQuery = true)
        public Iterable<Book> sortByHighestRating(int size);

        // find a given number of random books
        @Query(value = "SELECT * FROM Books ORDER BY RANDOM() LIMIT :size", nativeQuery = true)
        public Iterable<Book> random(@Param("size") int size);

        // set a book's status to available
        @Transactional
        @Modifying
        @Query(value = "UPDATE Book s SET s.bookStatus = :bookStatus WHERE s.id = :id", nativeQuery = true)
        public void setBookStatus(@Param("bookStatus") BookStatus bookStatus, @Param("id") Long id);

        // @Query(value = "SELECT * FROM Book WHERE createdAt BETWEEN start AND end",
        // nativeQuery = true)
        // public Iterable<Book> findByDate(Date start, Date end);

        // update a book's details
        @Transactional
        @Modifying
        @Query(value = "UPDATE Book s SET s.seller = :seller, s.title = :title, s.authorName = :authorName, s.price = :price, s.category = :category, s.isbn = :isbn, s.quantity = :quantity, s.imageURL = :imageURL, s.quality = :quality, s.bookStatus = :bookStatus, s.rating = :rating, s.ratingNo = :ratingNo WHERE s.id = :id", nativeQuery = true)
        public void updatebook(@Param("seller") User seller, @Param("title") String title,
                        @Param("authorName") String authorName, @Param("price") double price,
                        @Param("category") String category, @Param("isbn") int isbn, @Param("quantity") int quantity,
                        @Param("imageURL") String imageURL, @Param("quality") Quality quality,
                        @Param("bookStatus") BookStatus bookStatus, @Param("rating") double rating,
                        @Param("ratingNo") int ratingNo, @Param("id") Long id);

        // returns true if a book with the given parameters exists
        @Query(value = "SELECT COUNT(*)>0 FROM Book s WHERE s.seller = :seller AND LOWER(s.title) = :title AND LOWER(s.authorName) = :authorName AND LOWER(s.category) = :category AND s.isbn = :isbn AND s.quality = :quality", nativeQuery = true)
        public boolean bookExists(@Param("seller") Long seller, @Param("title") String title,
                        @Param("authorName") String authorName, @Param("category") String category,
                        @Param("isbn") int isbn, @Param("quality") Quality quality);

        // returns book with the given parameters exists
        @Query(value = "SELECT s FROM Book s WHERE s.seller = :seller AND LOWER(s.title) = :title AND LOWER(s.authorName) = :authorName AND LOWER(s.category) = :category AND s.isbn = :isbn AND s.quality = :quality", nativeQuery = true)
        public Book findWithParams(@Param("seller") Long seller, @Param("title") String title,
                        @Param("authorName") String authorName, @Param("category") String category,
                        @Param("isbn") int isbn, @Param("quality") Quality quality);

        
        @Query(value = "SELECT * FROM Books WHERE s.book_id = :id", nativeQuery = true)                
        Iterable<Book> findByBookId(@Param("id") Long id);

        @Override
        Iterable<Book> findAll();
}
