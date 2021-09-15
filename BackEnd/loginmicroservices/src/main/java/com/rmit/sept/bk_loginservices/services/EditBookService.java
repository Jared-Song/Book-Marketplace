package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.BookRepository;
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
        Book existingBook = bookRepository.findById(book.getId()).orElse(null);

        Long sellerId = bookForm.getSellerId();
        if (sellerId == null) {
            sellerId = book.getSellerId();
        }

        String title = bookForm.getTitle();
        if (title == null) {
            title = book.getTitle();
        }

        String authorName = bookForm.getAuthorName();
        if (authorName == null) {
            authorName = book.getAuthorName();
        }

        double price = bookForm.getPrice();
        if (price == 0.0) {
            price = book.getPrice();
        }

        String category = bookForm.getCategory();
        if (category == null) {
            category = book.getCategory();
        }

        int isbn = bookForm.getISBN();
        if (isbn == 0) {
            isbn = book.getISBN();
        }

        int quantity = bookForm.getQuantity();
        if (quantity == 0) {
            quantity = book.getQuantity();
        }

        String imageURL = bookForm.getImageURL();
        if (imageURL == null) {
            imageURL = book.getImageURL();
        }

        Quality quality = bookForm.getQuality();
        if (quality == null) {
            quality = book.getQuality();
        }

        boolean bookExists = bookRepository.bookExists(sellerId, title, authorName, category, isbn, quality);
        Book updateBook = bookRepository.findById(book.getId()).orElse(null);

        if (existingBook.getSellerId() == sellerId && existingBook.getTitle().equals(title)
                && existingBook.getAuthorName().equals(authorName) && existingBook.getISBN() == isbn
                && existingBook.getQuality().equals(quality)) {

            try {
                bookRepository.updatebook(sellerId, title, authorName, price, category, isbn, quantity, imageURL,
                        quality, book.getId());
            } catch (Exception e) {
            }
            return updateBook;
        } else if (!bookExists) {
            System.out.println("BOOK EXISTS");
            try {
                bookRepository.updatebook(sellerId, title, authorName, price, category, isbn, quantity, imageURL,
                        quality, book.getId());
            } catch (Exception e) {
            }
            return updateBook;
        } else {
            return null;
        }
    }
}
