package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.BookRepository;
import com.rmit.sept.bk_loginservices.exceptions.BookException;
import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.BookForm;
import com.rmit.sept.bk_loginservices.model.Quality;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditBookService {
    @Autowired
    private BookRepository bookRepository;

    public Book updateBook(BookForm bookForm, Book book) {

        // for all fields, if the bookform fields are filled out then use the bookform's
        // values, otherwise use the values from the book in the repository
        Long sellerId = (bookForm.getSellerId() == null) ? book.getSellerId() : bookForm.getSellerId();
        String title = (bookForm.getTitle() == null) ? book.getTitle() : bookForm.getTitle();
        String authorName = (bookForm.getAuthorName() == null) ? book.getAuthorName() : bookForm.getAuthorName();
        double price = (bookForm.getPrice() == 0) ? book.getPrice() : bookForm.getPrice();
        String category = (bookForm.getCategory() == null) ? book.getCategory() : bookForm.getCategory();
        int isbn = (bookForm.getISBN() == 0) ? book.getISBN() : bookForm.getISBN();
        int quantity = (bookForm.getQuantity() == 0) ? book.getQuantity() : bookForm.getQuantity();
        String imageURL = (bookForm.getImageURL() == null) ? book.getImageURL() : bookForm.getImageURL();
        Quality quality = (bookForm.getQuality() == null) ? book.getQuality() : bookForm.getQuality();

        // check to see if a copy of the updated book already exists in the repository
        boolean newbookExists = bookRepository.bookExists(sellerId, title, authorName, category, isbn, quality);
        if (newbookExists) {
            return null;
        } else { // the updated book details are valid, update it in the repository
            try {
                bookRepository.updatebook(sellerId, title, authorName, price, category, isbn, quantity, imageURL,
                        quality, book.getId());
            } catch (Exception e) {
                throw new BookException("Book with ID " + book.getId() + " was unable to be updated");
            }
            return book;
        }

    }
}
