package com.rmit.sept.bk_loginservices.services;

import java.util.Date;

import com.rmit.sept.bk_loginservices.Repositories.BookRepository;
import com.rmit.sept.bk_loginservices.exceptions.BookException;
import com.rmit.sept.bk_loginservices.model.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book findById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);

        if (book == null) {
            throw new BookException("Book with ID " + bookId + " does not exist");
        }

        return book;
    }

    public void deleteBookById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        try {
            bookRepository.delete(book);
        } catch (IllegalArgumentException e) {
            throw new BookException("Book with ID " + bookId + " does not exist");
        }
    }

    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        boolean bookExists = bookRepository.bookExists(book.getSellerId(), book.getTitle().toLowerCase(),
                book.getAuthorName().toLowerCase(), book.getCategory().toLowerCase(), book.getISBN(),
                book.getQuality());

        if (bookExists) {
            return null;
        } else {
            try {
                book.setId(book.getId());
                return bookRepository.save(book);
            } catch (IllegalArgumentException e) {
                throw new BookException("Book Save Error");
            }
        }
    }

    public Iterable<Book> findAllByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Iterable<Book> findAllByAuthorName(String authorName) {
        return bookRepository.findByAuthorName(authorName);
    }

    public Iterable<Book> findAllBySellerId(Long sellerId) {
        return bookRepository.findBySellerId(sellerId);
    }

    public Iterable<Book> findAllByISBN(int isbn) {
        return bookRepository.findByisbn(isbn);
    }

    public Iterable<Book> findAllByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    public Iterable<Book> findAllNewBooks() {
        return bookRepository.findAllNew();
    }

    public Iterable<Book> findAllUsedBooks() {
        return bookRepository.findAllUsed();
    }

    public Iterable<Book> findByPrice(float low, float high) {
        return bookRepository.findByPrice(low, high);
    }

    public Iterable<Book> findByDate(Date start, Date end) {
        return bookRepository.findByDate(start, end);
    }
}
