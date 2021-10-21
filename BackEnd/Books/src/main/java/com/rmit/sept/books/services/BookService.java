package com.rmit.sept.books.services;

import com.rmit.sept.books.Repositories.BookImageRepository;
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

    @Autowired
    private BookImageRepository bookImageRepository;

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
                BookImage bookImage = new BookImage();
                bookImage.setBook(book);
                bookImage.setUrl("please add new url");
                bookImage.setImageNumber(0);

                Request newBookRequest = new Request(); // make a new request to approve the new listing
                newBookRequest.setUser(book.getSeller());
                book.setRatingTotal(Book.INITIAL_RATING);
                book.setRatingNo(Book.INITIAL_NUM_RATINGS);
                newBookRequest.setRequestType(RequestType.NEW_BOOK_LISTING);
                newBookRequest.setRequest(String.format("%s would like to put %s on the market", book.getSeller().getUsername(), book.getTitle()));
                Request savedRequest = requestRepository.save(newBookRequest);
                book.setRequest(savedRequest);
                book = bookRepository.save(book);
                bookImageRepository.save(bookImage);
                return book;
            } catch (IllegalArgumentException e) {
                throw new BookException("Book Save Error");
            }
        }
    // }

    // find all books in the repository with a given seller's id
    public Iterable<Book> getAllBySeller(User seller) {
        return bookRepository.findBySeller(seller);
    }
}
