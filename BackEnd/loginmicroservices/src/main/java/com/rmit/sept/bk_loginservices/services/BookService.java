package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.BookRepository;
import com.rmit.sept.bk_loginservices.exceptions.BookException;
import com.rmit.sept.bk_loginservices.model.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    // find a book in the repository with the given id
    public Book findById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);

        if (book == null) {
            throw new BookException("Book with ID " + bookId + " does not exist");
        }

        return book;
    }

    // delete a book in the repository with the given id
    public void deleteBookById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        try {
            bookRepository.delete(book);
        } catch (IllegalArgumentException e) {
            throw new BookException("Book with ID " + bookId + " does not exist");
        }
    }

    // retrieve all the books in the repository
    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    // save a book into the repository
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
}
