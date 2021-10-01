package com.rmit.sept.books.Repositories;

import java.util.Date;

import javax.transaction.Transactional;

import com.rmit.sept.books.model.Book;
import com.rmit.sept.books.model.BookStatus;
import com.rmit.sept.books.model.Quality;
import com.rmit.sept.books.model.User;

import org.springframework.data.jpa.repository.Modifying;
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

        @Query(value = "SELECT * FROM Books WHERE price BETWEEN low AND high", nativeQuery = true)
        public Iterable<Book> findByPrice(@Param("price") double low, @Param("price") double high);

        @Query(value = "SELECT * FROM Books WHERE create_At BETWEEN start AND end", nativeQuery = true)
        public Iterable<Book> findByDate(Date start, Date end);

        // update a book's details
        @Transactional
        @Modifying
        @Query(value = "UPDATE Book s SET s.title = :title, s.authorName = :authorName, s.price = :price, s.category = :category, s.isbn = :isbn, s.quantity = :quantity, s.quality = :quality, s.bookStatus = :bookStatus, s.rating = :rating, s.ratingNo = :ratingNo WHERE s.id = :id", nativeQuery = true)
        public void updatebook(@Param("title") String title, @Param("authorName") String authorName,
                        @Param("price") double price, @Param("category") String category, @Param("isbn") int isbn,
                        @Param("quantity") int quantity, @Param("quality") Quality quality,
                        @Param("bookStatus") BookStatus bookStatus, @Param("rating") double rating,
                        @Param("ratingNo") int ratingNo, @Param("id") Long id);

        // returns true if a book with the given parameters exists
        @Query(value = "SELECT COUNT(*)>0 FROM Books WHERE seller_id = :seller AND LOWER(book_title) = :title AND LOWER(author_name) = :authorName AND LOWER(category) = :category AND isbn = :isbn AND quality_id = :quality", nativeQuery = true)
        public boolean bookExists(@Param("seller") User seller, @Param("title") String title,
                        @Param("authorName") String authorName, @Param("category") String category,
                        @Param("isbn") int isbn, @Param("quality") Quality quality);

        // returns book with the given parameters exists
        @Query(value = "SELECT FROM Books WHERE seller_id = :seller AND LOWER(book_title) = :title AND LOWER(author_name) = :authorName AND LOWER(category) = :category AND isbn = :isbn AND quality_id = :quality", nativeQuery = true)
        public Book findWithParams(@Param("seller") Long seller, @Param("title") String title,
                        @Param("authorName") String authorName, @Param("category") String category,
                        @Param("isbn") int isbn, @Param("quality") Quality quality);

        @Query(value = "SELECT * FROM Books WHERE s.book_id = :id", nativeQuery = true)
        Iterable<Book> findByBookId(@Param("id") Long id);

        @Override
        Iterable<Book> findAll();
}
