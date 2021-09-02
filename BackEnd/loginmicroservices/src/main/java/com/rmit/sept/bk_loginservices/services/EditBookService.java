package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.BookRepository;
import com.rmit.sept.bk_loginservices.exceptions.BookException;
import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.BookForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditBookService {
    @Autowired
    private BookRepository bookRepository;

    public Book updateBook(BookForm bookForm, Book book) {

        Long sellerId = bookForm.getSellerId();
        if (sellerId == null) {
            sellerId = book.getSellerId();
        }

        String title = bookForm.getTitle();
        if (title == null) {
            title = book.getTitle();
        }

        String authorFirstName = bookForm.getAuthorFirstName();
        if (title == null) {
            title = book.getAuthorFirstName();
        }

        String authorLastName = bookForm.getAuthorLastName();
        if (title == null) {
            title = book.getAuthorLastName();
        }

        int isbn = bookForm.getISBN();
        if (isbn == 0) {
            isbn = book.getISBN();
        }

        double price = bookForm.getPrice();
        if (price == 0.0) {
            price = book.getPrice();
        }

        int quantity = bookForm.getQuantity();
        if (quantity == 0) {
            quantity = book.getQuantity();
        }

        String imageURL = bookForm.getImageURL();
        if (imageURL == null) {
            imageURL = book.getImageURL();
        }

        try {
            bookRepository.updatebook(sellerId, title, authorFirstName, authorLastName, isbn, price, quantity, imageURL,
                    book.getId());
        } catch (Exception e) {
        }
        return bookRepository.findById(book.getId()).orElse(null);

    }
}
