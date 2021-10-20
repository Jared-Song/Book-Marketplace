package com.rmit.sept.reviews.services;

import com.rmit.sept.reviews.Repositories.BookRepository;
import com.rmit.sept.reviews.model.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    // find a book in the repository with the given id
    public Book findById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        return book;
    }
}
