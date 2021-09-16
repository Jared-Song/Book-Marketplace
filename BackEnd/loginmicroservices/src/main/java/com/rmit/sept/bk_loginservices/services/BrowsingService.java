package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.BookRepository;
import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.User;

import org.springframework.beans.factory.annotation.Autowired;

public class BrowsingService {
    @Autowired
    private BookRepository bookRepository;

    // find all books in the repository with a given title
    public Iterable<Book> findAllByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    // find all books in the repository with a given author's name
    public Iterable<Book> findAllByAuthorName(String authorName) {
        return bookRepository.findByAuthorName(authorName);
    }

    // find all books in the repository with a given seller's id
    public Iterable<Book> findAllBySellerId(User sellerId) {
        return bookRepository.findBySellerId(sellerId);
    }

    // find all books in the repository with a given isbn
    public Iterable<Book> findAllByISBN(int isbn) {
        return bookRepository.findByisbn(isbn);
    }

    // find all books in the repository with a given category
    public Iterable<Book> findAllByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    // find all books in the repository that are new
    public Iterable<Book> findAllNewBooks() {
        return bookRepository.findAllNew();
    }

    // find all books in the repository that are used
    public Iterable<Book> findAllUsedBooks() {
        return bookRepository.findAllUsed();
    }

    // public Iterable<Book> findByPrice(float low, float high) {
    //     return bookRepository.findByPrice(low, high);
    // }

    // public Iterable<Book> findByDate(Date start, Date end) {
    //     return bookRepository.findByDate(start, end);
    // }
}
