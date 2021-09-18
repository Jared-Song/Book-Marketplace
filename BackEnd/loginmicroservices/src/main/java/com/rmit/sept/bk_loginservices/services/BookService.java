package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.BookRepository;
import com.rmit.sept.bk_loginservices.Repositories.RequestRepository;
import com.rmit.sept.bk_loginservices.exceptions.BookException;
import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.BookStatus;
import com.rmit.sept.bk_loginservices.model.Request;
import com.rmit.sept.bk_loginservices.model.RequestType;

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
        boolean bookExists = bookRepository.bookExists(book.getSellerId(), book.getTitle().toLowerCase(),
                book.getAuthorName().toLowerCase(), book.getCategory().toLowerCase(), book.getISBN(),
                book.getQuality());

        if (bookExists) {
            return null;
        } else {
            try {
                book.setId(book.getId());
                book.setBookStatus(BookStatus.PENDING_APPROVAL);
                book.setRating(Book.INITIAL_RATING);
                book.setRatingNo(Book.INITIAL_NUM_RATINGS);
                bookRepository.save(book);

                Request newBookRequest = new Request(); // make a new request to approve the new listing
                newBookRequest.setObjectId(book.getId());
                newBookRequest.setRequestType(RequestType.NEW_BOOK_LISTING);
                requestRepository.save(newBookRequest);

                return book;
            } catch (IllegalArgumentException e) {
                throw new BookException("Book Save Error");
            }
        }
    }

    // find all books in the repository with a given seller's id
    public Iterable<Book> findAllBySellerId(Long sellerId) {
        return bookRepository.findBySellerId(sellerId);
    }
}
