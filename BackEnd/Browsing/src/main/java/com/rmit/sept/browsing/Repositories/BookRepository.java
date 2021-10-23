package com.rmit.sept.browsing.Repositories;

import com.rmit.sept.browsing.model.Book;
import com.rmit.sept.browsing.model.Quality;
import com.rmit.sept.browsing.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

        // find all by quality
        public Iterable<Book> findByQuality(Quality qualitty);

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
        @Query(value = "SELECT * FROM Books WHERE status_id = 'AVAILABLE' ORDER BY CREATE_AT DESC LIMIT :size", nativeQuery = true)
        public Iterable<Book> sortByNewestRelease(int size);

        // find a given number of books with the highest ratings
        @Query(value = "SELECT * FROM Books WHERE status_id = 'AVAILABLE' ORDER BY RATING_TOTAL/RATING_NO DESC LIMIT :size", nativeQuery = true)
        public Iterable<Book> sortByHighestRating(int size);

        // find a given number of random books
        @Query(value = "SELECT * FROM Books WHERE status_id = 'AVAILABLE' ORDER BY RANDOM() LIMIT :size", nativeQuery = true)
        public Iterable<Book> random(@Param("size") int size);

        @Override
        Iterable<Book> findAll();
}
