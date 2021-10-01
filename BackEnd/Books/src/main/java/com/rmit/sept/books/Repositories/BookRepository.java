package com.rmit.sept.books.Repositories;

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

        // find books with a specific seller's id
        @Query("SELECT s FROM Book s WHERE s.seller = :seller")
        public Iterable<Book> findBySeller(@Param("seller") User seller);
  
        // update a book's details
        @Transactional
        @Modifying
        @Query("UPDATE Book s SET s.title = :title, s.authorName = :authorName, s.price = :price, s.category = :category, s.isbn = :isbn, s.quantity = :quantity, s.quality = :quality, s.bookStatus = :bookStatus WHERE s.id = :id")
        public void updatebook(@Param("title") String title, @Param("authorName") String authorName,
                        @Param("price") double price, @Param("category") String category, @Param("isbn") int isbn,
                        @Param("quantity") int quantity, @Param("quality") Quality quality,
                        @Param("bookStatus") BookStatus bookStatus, @Param("id") Long id);

        // returns true if a book with the given parameters exists
        @Query("SELECT COUNT(*)>0 FROM Book s WHERE s.seller = :seller AND LOWER(title) = :title AND LOWER(authorName) = :authorName AND LOWER(s.category) = :category AND s.isbn = :isbn AND s.quality = :quality")
        public boolean bookExists(@Param("seller") User seller, @Param("title") String title,
                        @Param("authorName") String authorName, @Param("category") String category,
                        @Param("isbn") int isbn, @Param("quality") Quality quality);
        
        // returns book with the given parameters exists
        @Query("SELECT s FROM Book s WHERE s.seller = :seller AND LOWER(title) = :title AND LOWER(authorName) = :authorName AND LOWER(s.category) = :category AND s.isbn = :isbn AND s.quality = :quality")
        public Book findWithParams(@Param("seller") User seller, @Param("title") String title,
                        @Param("authorName") String authorName, @Param("category") String category,
                        @Param("isbn") int isbn, @Param("quality") Quality quality);

        @Override
        Iterable<Book> findAll();
}
