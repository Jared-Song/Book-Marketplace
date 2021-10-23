package com.rmit.sept.books.services;

import java.util.ArrayList;
import java.util.List;

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
            bookImageRepository.deleteByBook(book);
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
        boolean defaultImage = false;
        BookImage defaultBookImage = new BookImage();
        List<BookImage> bookImages = new ArrayList<BookImage>();
        try {
            book.setBookStatus(BookStatus.PENDING_APPROVAL);
            if (book.getImageURL().isEmpty()) {
                defaultImage = true;
                defaultBookImage.setBook(book);
                defaultBookImage.setUrl("noURL");
                defaultBookImage.setImageNumber(0);
            } else {
                bookImages = book.getImageURL();
            }

            Request newBookRequest = new Request(); // make a new request to approve the new listing
            newBookRequest.setUser(book.getSeller());
            book.setRatingTotal(Book.INITIAL_RATING);
            book.setRatingNo(Book.INITIAL_NUM_RATINGS);
            newBookRequest.setRequestType(RequestType.NEW_BOOK_LISTING);
            newBookRequest.setRequest(String.format("%s would like to put %s on the market",
                    book.getSeller().getUsername(), book.getTitle()));
            Request savedRequest = requestRepository.save(newBookRequest);
            book.setRequest(savedRequest);
            book = bookRepository.save(book);
            if (defaultImage) {
                bookImageRepository.save(defaultBookImage);
            } else {
                int count = 0;
                for (BookImage image : bookImages) {
                    image.setBook(book);
                    image.setImageNumber(count);
                    bookImageRepository.save(image);
                    count++;
                }
            }
            return book;
        } catch (IllegalArgumentException e) {
            throw new BookException("Book Save Error");
        }
    }

    // adds an image to a book
    public BookImage addBookImage(BookImage bookImage, Book book) {
        try {
            List<BookImage> images = book.getImageURL();
            bookImage.setImageNumber(images.size());
            if (images.get(0).getUrl().equals("noURL")) {
                bookImage.setImageNumber(0);
                bookImageRepository.deleteById(images.get(0).getId());
                images.remove(0);
            }
            images.add(bookImage);
            book.setImageURL(images);
            bookImage.setBook(book);
            return bookImageRepository.save(bookImage);
        } catch (Exception e) {

        }
        return null;
    }

    // find all books in the repository with a given seller's id
    public Iterable<Book> getAllBySeller(User seller) {
        return bookRepository.findBySeller(seller);
    }
}
