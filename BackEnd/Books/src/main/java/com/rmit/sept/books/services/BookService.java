package com.rmit.sept.books.services;

import java.util.Arrays;
import java.util.Date;

import com.rmit.sept.books.Repositories.BookRepository;
import com.rmit.sept.books.Repositories.RequestRepository;
import com.rmit.sept.books.exceptions.BookException;
import com.rmit.sept.books.model.Book;
import com.rmit.sept.books.model.BookImage;
import com.rmit.sept.books.model.BookStatus;
import com.rmit.sept.books.model.Request;
import com.rmit.sept.books.model.RequestType;
import com.rmit.sept.books.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            //TODO: fix delete book request, book image and book transaction
             if (book != null) { // if the book exists and was pending approval, delete the request
                // requestRepository.deletePendingBookRequest(bookId, RequestType.NEW_BOOK_LISTING);
                
            }
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
            try {
                book.setBookStatus(BookStatus.PENDING_APPROVAL);
                book.setImageURL(Arrays.asList(new BookImage(1l, "lmao", 1))); //TODO: implement proper book images
                Request newBookRequest = new Request(); // make a new request to approve the new listing
                newBookRequest.setUser(book.getSeller());
                book.setRating(Book.INITIAL_RATING);
                book.setRatingNo(Book.INITIAL_NUM_RATINGS);
                bookRepository.save(book);

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

    public Iterable<Book> getAllByISBN(int isbn) {
        return bookRepository.findByisbn(Integer.toString(isbn));
    }

    public Iterable<Book> findByPrice(float low, float high) {
        return bookRepository.findByPrice(low, high);
    }

    public Iterable<Book> getByDate(Date start, Date end) {
        return bookRepository.findByDate(start, end);
    }

    // find all books in the repository with a given seller's id
    public Iterable<Book> getAllBySeller(User seller) {
        return bookRepository.findBySeller(seller);
    }

    public long findRepositorySize() {
        return bookRepository.count();
    }
}