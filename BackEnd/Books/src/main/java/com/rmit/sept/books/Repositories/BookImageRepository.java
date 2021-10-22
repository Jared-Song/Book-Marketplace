package com.rmit.sept.books.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.books.model.Book;
import com.rmit.sept.books.model.BookImage;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookImageRepository extends CrudRepository<BookImage, Long> {

    // delete all images with a given book
    @Transactional
    @Modifying
    @Query("DELETE FROM BookImage s WHERE s.book = :book")
    public void deleteByBook(@Param("book") Book book);

    @Override
    Iterable<BookImage> findAll();
}
