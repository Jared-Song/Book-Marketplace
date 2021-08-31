package com.rmit.sept.bk_loginservices.services;

import java.util.Date;

import com.rmit.sept.bk_loginservices.Repositories.BookRepository;
import com.rmit.sept.bk_loginservices.model.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrowsingService {
    @Autowired
    private BookRepository bookRepository;

    public Book getBookById(Long id) {
        return bookRepository.getById(id);
    }

    public Iterable<Book> getAllByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Iterable<Book> getAllByAuthorFirstName(String firstName) {
        return bookRepository.findByAuthorFirstName(firstName);
    }

    public Iterable<Book> getAllByAuthorLastName(String lastName) {
        return bookRepository.findByAuthorFirstName(lastName);
    }

    public Iterable<Book> getAllBySellerId(Long sellerId) {
        return bookRepository.findBySellerId(sellerId);
    }

    public Iterable<Book> findByPrice(float price) {
        return bookRepository.findByPrice(price);
    }

    public Iterable<Book> getByDate(Date date) {
        return bookRepository.findByCreatedAt(date);
    }


}
