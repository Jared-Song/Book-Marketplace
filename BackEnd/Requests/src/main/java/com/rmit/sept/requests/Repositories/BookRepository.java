package com.rmit.sept.requests.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.requests.model.Book;
import com.rmit.sept.requests.model.BookStatus;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

        // set a book's status to available
        @Transactional
        @Modifying
        @Query(value = "UPDATE Book s SET s.bookStatus = :bookStatus WHERE s.id = :id", nativeQuery = true)
        public void setBookStatus(@Param("bookStatus") BookStatus bookStatus, @Param("id") Long id);
}
