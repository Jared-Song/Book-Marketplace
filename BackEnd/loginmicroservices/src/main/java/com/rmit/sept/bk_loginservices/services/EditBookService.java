package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.BookRepository;
import com.rmit.sept.bk_loginservices.exceptions.BookException;
import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.BookForm;
import com.rmit.sept.bk_loginservices.model.BookStatus;
import com.rmit.sept.bk_loginservices.model.Quality;
import com.rmit.sept.bk_loginservices.model.BookImage;

import com.rmit.sept.bk_loginservices.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditBookService {
    @Autowired
    private BookRepository bookRepository;

    public Book updateBook(BookForm bookForm, Book book) {
        // for all fields, if the bookform fields are filled out then use the bookform's
        // values, otherwise use the values from the book in the repository
        User seller = (bookForm.getSellerId() == null) ? book.getSeller() : bookForm.getSellerId();
        String title = (bookForm.getTitle() == null) ? book.getTitle() : bookForm.getTitle();
        String authorName = (bookForm.getAuthorName() == null) ? book.getAuthorName() : bookForm.getAuthorName();
        double price = (bookForm.getPrice() == 0) ? book.getPrice() : bookForm.getPrice();
        String category = (bookForm.getCategory() == null) ? book.getCategory() : bookForm.getCategory();
        int isbn = (bookForm.getISBN() == 0) ? book.getISBN() : bookForm.getISBN();
        int quantity = (bookForm.getQuantity() == 0) ? book.getQuantity() : bookForm.getQuantity();
        List<BookImage> imageURL = (bookForm.getImageURL() == null) ? book.getImageURL() : bookForm.getImageURL();
        Quality quality = (bookForm.getQuality() == null) ? book.getQuality() : bookForm.getQuality();
        BookStatus bookStatus = (bookForm.getBookStatus() == null) ? book.getBookStatus() : bookForm.getBookStatus();
        double rating = (bookForm.getRating() == 0) ? book.getRating() : bookForm.getRating();
        int ratingNo = (bookForm.getRatingNo() == 0) ? book.getRatingNo() : bookForm.getRatingNo();

        // check to see if a copy of the updated book already exists in the repository
        boolean newbookExists = bookRepository.bookExists(seller.getId(), title.toLowerCase(), authorName.toLowerCase(),
                category.toLowerCase(), isbn, quality);
        Book existingBook = bookRepository.findWithParams(seller.getId(), title.toLowerCase(), authorName.toLowerCase(),
                category.toLowerCase(), isbn, quality);

        // if the book exists and it's not the current book being updated
        if (newbookExists && existingBook.getId() != book.getId()) {
            return null;
        } else { // the updated book details are valid, update it in the repository
            System.out.println("rating: " + rating);
            try {
                bookRepository.updatebook(seller, title, authorName, price, category, isbn, quantity, imageURL.get(0).getUrl(),
                        quality, bookStatus, rating, ratingNo, book.getId());
            } catch (Exception e) {
                throw new BookException("Book with ID " + book.getId() + " was unable to be updated");
            }
        }
        return book;
    }
}
