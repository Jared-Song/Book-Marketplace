package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.BookRepository;
import com.rmit.sept.bk_loginservices.model.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
    public Iterable<Book> findAllBySellerId(Long sellerId) {
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

    // find all books and sort them by highest price first
    public Iterable<Book> sortByHighestPrice() {
        return bookRepository.sortByHighestPrice();
    }

    // find all books and sort them by lowest price first
    public Iterable<Book> sortByLowestPrice() {
        return bookRepository.sortByLowestPrice();
    }

    // find all books and sort them alphabetically
    public Iterable<Book> sortByAlphabet() {
        return bookRepository.sortByAlphabet();
    }

    // public Iterable<Book> findByDate(Date start, Date end) {
    //     return bookRepository.findByDate(start, end);
    // }
}
