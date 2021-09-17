package com.rmit.sept.bk_loginservices.services;

import java.util.Iterator;
import java.util.List;

import com.rmit.sept.bk_loginservices.Repositories.BookRepository;
import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.Quality;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class BrowsingService {
    @Autowired
    private BookRepository bookRepository;

    // find all books in the repository with a given title
    public Iterable<Book> findAllByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    // find all books in the repository with a given author's name
    public Iterable<Book> findAllByAuthorName(String authorName) {
        return bookRepository.findByAuthorName(authorName);
    }

    // find all books in the repository with a given seller's id
    public Iterable<Book> findAllBySellerId(Long sellerId) {
        return bookRepository.findBySellerId(sellerId);
    }

    // find all books in the repository with a given isbn
    public Iterable<Book> findAllByISBN(int isbn) {
        return bookRepository.findByisbn(isbn);
    }

    // find all books in the repository with a given category
    public Iterable<Book> findAllByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    // find all books in the repository that are new
    public Iterable<Book> findAllNewBooks() {
        return bookRepository.findAllNew();
    }

    // find all books in the repository that are used
    public Iterable<Book> findAllUsedBooks() {
        return bookRepository.findAllUsed();
    }

    // find all books and sort them by highest price first
    public Iterable<Book> sortByHighestPrice() {
        return bookRepository.sortByHighestPrice();
    }

    // find all books and sort them by lowest price first
    public Iterable<Book> sortByLowestPrice() {
        return bookRepository.sortByLowestPrice();
    }

    // find all books and sort them alphabetically
    public Iterable<Book> sortByAlphabet() {
        return bookRepository.sortByAlphabet();
    }

    // retrieve a given number of books by most recently created
    public Iterable<Book> sortByNewestRelease(int size) {
        return bookRepository.sortByNewestRelease(size);
    }

    // retrieve a given number of books with the highest ratings
    public Iterable<Book> sortByHighestRating(int size) {
        return bookRepository.sortByHighestRating(size);
    }

    // retrieve a given number of random books
    public Iterable<Book> random(int size) {
        return bookRepository.random(size);
    }

    // filter a collection of books by new books only
    public Iterable<Book> filterByNewBooks(Iterable<Book> books) {
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book book = iter.next();
            if (book.getQuality() != Quality.NEW) {
                iter.remove();
            }
        }
        return books;
    }

    // filter a collection of books by used books only
    public Iterable<Book> filterByUsedBooks(Iterable<Book> books) {
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book book = iter.next();
            if (book.getQuality() != Quality.USED) {
                iter.remove();
            }
        }
        return books;
    }

    // filter a collection of books by title
    public Iterable<Book> filterByTitle(Iterable<Book> books, String title) {
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book book = iter.next();
            String bookTitle = book.getTitle().toLowerCase();
            String queryTitle = title.toLowerCase();
            if (!bookTitle.equals(queryTitle) && !bookTitle.contains(queryTitle)) {
                iter.remove();
            }
        }
        return books;
    }

    // filter a collection of books by author name
    public Iterable<Book> filterByAuthorName(Iterable<Book> books, String authorName) {
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book book = iter.next();
            String bookAuthorName = book.getAuthorName().toLowerCase();
            String queryAuthorName = authorName.toLowerCase();
            if (!bookAuthorName.equals(queryAuthorName) && !bookAuthorName.contains(queryAuthorName)) {
                iter.remove();
            }
        }
        return books;
    }

    // filter a collection of books by category
    public Iterable<Book> filterByCategory(Iterable<Book> books, String category) {
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book book = iter.next();
            String bookCategory = book.getCategory().toLowerCase();
            String queryCategory = category.toLowerCase();
            if (!bookCategory.equals(queryCategory) && !bookCategory.contains(queryCategory)) {
                iter.remove();
            }
        }
        return books;
    }

    // filter a collection of books by isbn
    public Iterable<Book> filterByIsbn(Iterable<Book> books, int isbn) {
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book book = iter.next();
            if (book.getISBN() != isbn) {
                iter.remove();
            }
        }
        return books;
    }
}
