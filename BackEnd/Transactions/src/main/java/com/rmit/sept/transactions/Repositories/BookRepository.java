package com.rmit.sept.transactions.Repositories;

import com.rmit.sept.transactions.model.Book;
import com.rmit.sept.transactions.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

        Book getById(Long id);

        Iterable<Book> findBySeller(User seller);

        @Override
        Iterable<Book> findAll();
}
