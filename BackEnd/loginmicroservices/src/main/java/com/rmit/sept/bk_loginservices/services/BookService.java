package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.BookRepository;
import com.rmit.sept.bk_loginservices.Repositories.RequestRepository;
import com.rmit.sept.bk_loginservices.exceptions.BookException;
import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.BookImage;
import com.rmit.sept.bk_loginservices.model.BookStatus;
import com.rmit.sept.bk_loginservices.model.Quality;
import com.rmit.sept.bk_loginservices.model.Request;
import com.rmit.sept.bk_loginservices.model.RequestType;

import com.rmit.sept.bk_loginservices.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RequestRepository requestRepository;

    // find a book in the repository with the given id
    public Book findById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        return book;
    }

    // delete a book in the repository with the given id
    public void deleteBookById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        try {
            bookRepository.delete(book);
            if (book != null) { // if the book exists and was pending approval, delete the request
                requestRepository.deletePendingBookRequest(bookId, RequestType.NEW_BOOK_LISTING);
            }
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
        // boolean bookExists = bookRepository.bookExists(book.getSellerId(), book.getTitle().toLowerCase(),
        //         book.getAuthorName().toLowerCase(), book.getCategory().toLowerCase(), book.getISBN(),
        //         book.getQuality());

        // if (bookExists) {
        //     return null;
        // } else {
            try {
                book.setBookStatus(BookStatus.PENDING_APPROVAL);
                book.setImageURL(Arrays.asList(new BookImage())); //TODO: implement proper book images
                bookRepository.save(book);

                Request newBookRequest = new Request(); // make a new request to approve the new listing
                newBookRequest.setUser(book.getSeller());
                newBookRequest.setRequestType(RequestType.NEW_BOOK_LISTING);
                newBookRequest.setRequest(String.format("%s would like to put %s on the market, TODO: OVERRIDE 'USER' AND 'BOOK' .toString() METHODS ", book.getSeller().getUsername(), book.getTitle()));
                requestRepository.save(newBookRequest);

                return book;
            } catch (IllegalArgumentException e) {
                throw new BookException("Book Save Error");
            }
        }
    // }

    public Iterable<Book> getAllByBookId(Long bookId) {
        return bookRepository.findByBookId(bookId);
    }

    public Iterable<Book> getAllByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Iterable<Book> getAllByAuthorName(String name) {
        return bookRepository.findByAuthorName(name);
    }

    public Iterable<Book> getAllBySellerId(User sellerId) {
        return bookRepository.findBySellerId(sellerId);
    }

    public Iterable<Book> getAllByISBN(int isbn) {
        return bookRepository.findByisbn(isbn);
    }

    public Iterable<Book> findByPrice(float low, float high) {
        return bookRepository.findByPrice(low, high);
    }

    public Iterable<Book> getByDate(Date start, Date end) {
        return bookRepository.findByDate(start, end);
    }

}
