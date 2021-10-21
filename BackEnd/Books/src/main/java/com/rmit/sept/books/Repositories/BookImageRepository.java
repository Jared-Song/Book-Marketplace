package com.rmit.sept.books.Repositories;

import com.rmit.sept.books.model.BookImage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookImageRepository extends CrudRepository<BookImage, Long> {

    @Override
    Iterable<BookImage> findAll();
}
