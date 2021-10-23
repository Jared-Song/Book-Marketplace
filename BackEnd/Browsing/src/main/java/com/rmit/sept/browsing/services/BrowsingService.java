package com.rmit.sept.browsing.services;

import java.util.Iterator;

import com.rmit.sept.browsing.Repositories.BookRepository;
import com.rmit.sept.browsing.model.Book;
import com.rmit.sept.browsing.model.BookStatus;
import com.rmit.sept.browsing.model.Quality;
import com.rmit.sept.browsing.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrowsingService {
    @Autowired
    private BookRepository bookRepository;

    // find all books in the repository with a given title
    public Iterable<Book> findAllByTitle(String title) {
        Iterable<Book> books = bookRepository.findByTitle(title);
        return filterByAvailable(books);
    }

    // find all books in the repository with a given author's name
    public Iterable<Book> findAllByAuthorName(String authorName) {
        Iterable<Book> books = bookRepository.findByAuthorName(authorName);
        return filterByAvailable(books);
    }

    // find all books in the repository with a given seller's id
    public Iterable<Book> findAllBySeller(User seller) {
        Iterable<Book> books = bookRepository.findBySeller(seller);
        return filterByAvailable(books);
    }

    // find all books in the repository with a given isbn
    public Iterable<Book> findAllByISBN(int isbn) {
        Iterable<Book> books = bookRepository.findByisbn(Integer.toString(isbn));
        return filterByAvailable(books);
    }

    // find all books in the repository with a given category
    public Iterable<Book> findAllByCategory(String category) {
        Iterable<Book> books = bookRepository.findByCategory(category);
        return filterByAvailable(books);
    }

    // find all books in the repository that are new
    public Iterable<Book> findAllNewBooks() {
        Iterable<Book> books = bookRepository.findByQuality(Quality.NEW);
        return filterByAvailable(books);
    }

    // find all books in the repository that are used
    public Iterable<Book> findAllUsedBooks() {
        Iterable<Book> books = bookRepository.findByQuality(Quality.USED);
        return filterByAvailable(books);
    }

    // find all books and sort them by highest price first
    public Iterable<Book> sortByHighestPrice() {
        Iterable<Book> books = bookRepository.sortByHighestPrice();
        return filterByAvailable(books);
    }

    // find all books and sort them by lowest price first
    public Iterable<Book> sortByLowestPrice() {
        Iterable<Book> books = bookRepository.sortByLowestPrice();
        return filterByAvailable(books);
    }

    // find all books and sort them alphabetically
    public Iterable<Book> sortByAlphabet() {
        Iterable<Book> books = bookRepository.sortByAlphabet();
        return filterByAvailable(books);
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

    // filter a collection of books by available only
    public Iterable<Book> filterByAvailable(Iterable<Book> books) {
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book book = iter.next();
            if (book.getBookStatus() != BookStatus.AVAILABLE) {
                iter.remove();
            }
        }
        return books;
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
        return filterByAvailable(books);
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
        return filterByAvailable(books);
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
        return filterByAvailable(books);
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
        return filterByAvailable(books);
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
        return filterByAvailable(books);
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
        return filterByAvailable(books);
    }
}
