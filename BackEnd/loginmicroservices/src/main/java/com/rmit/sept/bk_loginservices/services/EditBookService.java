package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.BookRepository;
import com.rmit.sept.bk_loginservices.exceptions.BookException;
import com.rmit.sept.bk_loginservices.model.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditBookService {
    @Autowired
    private BookRepository bookRepository;

    public void editBook(Long id, Long sellerId) {
        bookRepository.updateBook(sellerId, id);
    }

    public Book saveOrUpdateBook(Book book) {
        try {
            book.setId(book.getId());
            return bookRepository.save(book);
        } catch (Exception e) {
            throw new BookException("Book with ID '" + book.getId() + "' already exists.");
        }
    }
    
    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book == null) {
            throw new BookException("Cannot find book with ID '" + id + "'. This book does not exist");
        }

        bookRepository.delete(book);
    }
}
