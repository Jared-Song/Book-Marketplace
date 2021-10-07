package com.rmit.sept.books.services;

import com.rmit.sept.books.Repositories.BookRepository;
import com.rmit.sept.books.Repositories.UserRepository;
import com.rmit.sept.books.exceptions.BookException;
import com.rmit.sept.books.model.Book;
import com.rmit.sept.books.model.BookForm;
import com.rmit.sept.books.model.BookStatus;
import com.rmit.sept.books.model.Quality;
import com.rmit.sept.books.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditBookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public Book updateBook(BookForm bookForm, Book book) {
        // for all fields, if the bookform fields are filled out then use the bookform's
        // values, otherwise use the values from the book in the repository
        User seller = (bookForm.getSeller() == null) ? book.getSeller() : bookForm.getSeller();
        Long sellerId = (bookForm.getSellerId() == null) ? book.getSellerId() : bookForm.getSellerId();

        if (seller == null) { // if the seller id is given isntead of the seller
            if (sellerId != null) {
                seller = userRepository.getById(sellerId);
            } else { // seller id and seller were not given
                return null;
            }
        }
        String title = (bookForm.getTitle() == null) ? book.getTitle() : bookForm.getTitle();
        String authorName = (bookForm.getAuthorName() == null) ? book.getAuthorName() : bookForm.getAuthorName();
        double price = (bookForm.getPrice() == 0) ? book.getPrice() : bookForm.getPrice();
        String category = (bookForm.getCategory() == null) ? book.getCategory() : bookForm.getCategory();
        int isbn = (bookForm.getISBN() == 0) ? book.getISBN() : bookForm.getISBN();
        int quantity = (bookForm.getQuantity() == 0) ? book.getQuantity() : bookForm.getQuantity();
        Quality quality = (bookForm.getQuality() == null) ? book.getQuality() : bookForm.getQuality();
        BookStatus bookStatus = (bookForm.getBookStatus() == null) ? book.getBookStatus() : bookForm.getBookStatus();

        boolean newbookExists = bookRepository.bookExists(seller, title.toLowerCase(), authorName.toLowerCase(),
                category.toLowerCase(), isbn, quality);
        Book existingBook = bookRepository.findWithParams(seller, title.toLowerCase(), authorName.toLowerCase(),
                category.toLowerCase(), isbn, quality);

        // if the book exists and it's not the current book being updated
        if (newbookExists && existingBook.getId() != book.getId()) {
            return null;
        } else { // the updated book details are valid, update it in the repository
            try {
                bookRepository.updatebook(title, authorName, price, category, isbn, quantity, quality, bookStatus,
                        book.getId());
            } catch (Exception e) {
                throw new BookException("Book with ID " + book.getId() + " was unable to be updated");
            }
        }
        return book;
    }
}
