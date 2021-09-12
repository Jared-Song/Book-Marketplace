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

        if (bookId == null) {
            throw new BookException("Book with ID " + bookId + " does not exist");
        }

        return book;
    }

    public void deleteBookById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new BookException("Book with ID " + bookId + " does not exist");
        }
        bookRepository.delete(book);
    }

    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        boolean bookExists = bookRepository.bookExists(book.getSellerId(), book.getTitle(), book.getAuthorFirstName(),
                book.getAuthorLastName(), book.getCategory(), book.getISBN());

        if (bookExists) {
            return null;
        } else {
            try {
                book.setId(book.getId());
                return bookRepository.save(book);
            } catch (Exception e) {
                throw new BookException("Book Save Error");
            }
        }
    }

    public Iterable<Book> getAllByBookId(Long bookId) {
        return bookRepository.findByBookId(bookId);
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

    public Iterable<Book> getAllByISBN(int isbn) {
        return bookRepository.findByisbn(isbn);
    }

    public Iterable<Book> getAllByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    public Iterable<Book> findByPrice(float low, float high) {
        return bookRepository.findByPrice(low, high);
    }

    public Iterable<Book> getByDate(Date start, Date end) {
        return bookRepository.findByDate(start, end);
    }
}
