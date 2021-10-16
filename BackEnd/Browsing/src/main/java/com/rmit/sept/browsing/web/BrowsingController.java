package com.rmit.sept.browsing.web;

import com.rmit.sept.browsing.BrowsingApplication;
import com.rmit.sept.browsing.model.Book;
import com.rmit.sept.browsing.services.BrowsingService;
import com.rmit.sept.browsing.services.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/browse")
public class BrowsingController { // browsing all available books
    private static final Logger LOGGER = LogManager.getLogger(BrowsingApplication.class);

    @Autowired
    private BrowsingService browsingService;

    @Autowired
    private UserService userService;

    // retrieve books in the catalogue with a specific title
    @GetMapping("/title/{title}")
    public Iterable<Book> getByTitle(@PathVariable String title) {
        LOGGER.trace("Finding all books containing the title '" + title + "'");
        return browsingService.findAllByTitle(title.toLowerCase());
    }

    // retrieve books in the catalogue with a specific author's name
    @GetMapping("/authorName/{authorName}")
    public Iterable<Book> getByAuthorFirstName(@PathVariable String authorName) {
        LOGGER.trace("Finding all books written by author's with the name '" + authorName + "'");
        return browsingService.findAllByAuthorName(authorName.toLowerCase());
    }

    // retrieve books in the catalogue with a specific seller's id
    @GetMapping("/sellerId/{sellerId}")
    public Iterable<Book> getBySeller(@PathVariable Long sellerId) {
        LOGGER.trace("Finding all books sold by users with the ID " + sellerId);
        return browsingService.findAllBySeller(userService.findById(sellerId));
    }

    // retrieve books in the catalogue with a specific category
    @GetMapping("/category/{category}")
    public Iterable<Book> getByCategory(@PathVariable String category) {
        LOGGER.trace("Finding all books in the category '" + category + "'");
        return browsingService.findAllByCategory(category.toLowerCase());
    }

    // retrieve books in the catalogue with a specific isbn
    @GetMapping("/isbn/{isbn}")
    public Iterable<Book> getByISBN(@PathVariable int isbn) {
        LOGGER.trace("Finding all books containing the following ISBN '" + isbn + "'");
        return browsingService.findAllByISBN(isbn);
    }

    // retrieve books in the catalogue that are new
    @GetMapping("/new")
    public Iterable<Book> getNewBooks() {
        LOGGER.trace("Finding all books that have the quality NEW");
        return browsingService.findAllNewBooks();
    }

    // retrieve books in the catalogue that are used
    @GetMapping("/used")
    public Iterable<Book> getUsedBooks() {
        LOGGER.trace("Finding all books that have the quality USED");
        return browsingService.findAllUsedBooks();
    }

    // retrieve all books from highest to lowest price
    @GetMapping("/price/high")
    public Iterable<Book> sortByPriceHighToLow() {
        LOGGER.trace("Finding all books sorted from highest to lowest price");
        return browsingService.sortByHighestPrice();
    }

    // retrieve all books from lowest to highest price
    @GetMapping("/price/low")
    public Iterable<Book> sortByPriceLowToHigh() {
        LOGGER.trace("Finding all books sorted from lowest to highest price");
        return browsingService.sortByLowestPrice();
    }

    // retrieve all books in aphabetical order
    @GetMapping("/alphabet")
    public Iterable<Book> sortByAlphabet() {
        LOGGER.trace("Finding all books sorted by alphabetical title order");
        return browsingService.sortByAlphabet();
    }

    // retrieve a given number of books by most recently created
    @GetMapping("/newReleases/{size}")
    public Iterable<Book> newReleases(@PathVariable int size) {
        LOGGER.trace("Finding " + size + " books sorted by most recently added");
        return browsingService.sortByNewestRelease(size);
    }

    // retrieve a given number of books with the highest ratings
    @GetMapping("/bestSellers/{size}")
    public Iterable<Book> bestSellers(@PathVariable int size) {
        LOGGER.trace("Finding " + size + " books sorted by highest rating");
        return browsingService.sortByHighestRating(size);
    }

    // retrieve a given number of random books
    @GetMapping("/random/{size}")
    public Iterable<Book> random(@PathVariable int size) {
        LOGGER.trace("Finding " + size + " random books");
        return browsingService.random(size);
    }

    // search by title and sort by lowest to highest price
    @GetMapping("/sort/price/low/title/{title}")
    public Iterable<Book> getByTitleAndSortByPriceLowToHigh(@PathVariable String title) {
        LOGGER.trace("Finding all books sorted from lowest to highest price");
        Iterable<Book> books = browsingService.sortByLowestPrice();
        LOGGER.trace("Finding all books containing the title '" + title + "'");
        return browsingService.filterByTitle(books, title);
    }

    // search by title and sort by highest to lowest price
    @GetMapping("/sort/price/high/title/{title}")
    public Iterable<Book> getByTitleAndSortByPriceHighToLow(@PathVariable String title) {
        LOGGER.trace("Finding all books sorted from highest to lowest price");
        Iterable<Book> books = browsingService.sortByHighestPrice();
        LOGGER.trace("Finding all books containing the title '" + title + "'");
        return browsingService.filterByTitle(books, title);
    }

    // search by title and sort by alphabet
    @GetMapping("/sort/alphabet/title/{title}")
    public Iterable<Book> getByTitleAndSortByAlphabet(@PathVariable String title) {
        LOGGER.trace("Finding all books sorted by alphabetical title order");
        Iterable<Book> books = browsingService.sortByAlphabet();
        LOGGER.trace("Finding all books containing the title '" + title + "'");
        return browsingService.filterByTitle(books, title);
    }

    // search by title and new books
    @GetMapping("/filter/new/title/{title}")
    public Iterable<Book> getByTitleAndFilterNew(@PathVariable String title) {
        LOGGER.trace("Finding all books containing the title '" + title + "'");
        Iterable<Book> books = browsingService.findAllByTitle(title);
        LOGGER.trace("Finding all books that have the quality NEW");
        return browsingService.filterByNewBooks(books);
    }

    // search by title and used books
    @GetMapping("/filter/used/title/{title}")
    public Iterable<Book> getByTitleAndFilterUsed(@PathVariable String title) {
        LOGGER.trace("Finding all books containing the title '" + title + "'");
        Iterable<Book> books = browsingService.findAllByTitle(title);
        LOGGER.trace("Finding all books that have the quality USED");
        return browsingService.filterByUsedBooks(books);
    }

    // search by author name and sort by lowest to highest price
    @GetMapping("/sort/price/low/authorName/{authorName}")
    public Iterable<Book> getByAuthorNameAndSortByPriceLowToHigh(@PathVariable String authorName) {
        LOGGER.trace("Finding all books sorted from lowest to highest price");
        Iterable<Book> books = browsingService.sortByLowestPrice();
        LOGGER.trace("Finding all books written by author's with the name '" + authorName + "'");
        return books = browsingService.filterByAuthorName(books, authorName);
    }

    // search by author name and sort by highest to lowest price
    @GetMapping("/sort/price/high/authorName/{authorName}")
    public Iterable<Book> getByAuthorNameAndSortByPriceHighToLow(@PathVariable String authorName) {
        LOGGER.trace("Finding all books sorted from highest to lowest price");
        Iterable<Book> books = browsingService.sortByHighestPrice();
        LOGGER.trace("Finding all books written by author's with the name '" + authorName + "'");
        return books = browsingService.filterByAuthorName(books, authorName);
    }

    // search by author name and sort by alphabet
    @GetMapping("/sort/alphabet/authorName/{authorName}")
    public Iterable<Book> getByAuthorNameAndSortByAlphabet(@PathVariable String authorName) {
        Iterable<Book> books = browsingService.sortByAlphabet();
        LOGGER.trace("Finding all books written by author's with the name '" + authorName + "'");
        return books = browsingService.filterByAuthorName(books, authorName);
    }

    // search by author name and new books
    @GetMapping("/filter/new/authorName/{authorName}")
    public Iterable<Book> getByAuthorNameAndFilterNew(@PathVariable String authorName) {
        LOGGER.trace("Finding all books written by author's with the name '" + authorName + "'");
        Iterable<Book> books = browsingService.findAllByAuthorName(authorName);
        LOGGER.trace("Finding all books that have the quality NEW");
        return books = browsingService.filterByNewBooks(books);
    }

    // search by author name and used books
    @GetMapping("/filter/used/authorName/{authorName}")
    public Iterable<Book> getByAuthorNameAndFilterUsed(@PathVariable String authorName) {
        LOGGER.trace("Finding all books written by author's with the name '" + authorName + "'");
        Iterable<Book> books = browsingService.findAllByAuthorName(authorName);
        LOGGER.trace("Finding all books that have the quality USED");
        return books = browsingService.filterByUsedBooks(books);
    }

    // search by category and sort by lowest to highest price
    @GetMapping("/sort/price/low/category/{category}")
    public Iterable<Book> getByCategoryAndSortByPriceLowToHigh(@PathVariable String category) {
        LOGGER.trace("Finding all books sorted from lowest to highest price");
        Iterable<Book> books = browsingService.sortByLowestPrice();
        LOGGER.trace("Finding all books in the category '" + category + "'");
        return books = browsingService.filterByCategory(books, category);
    }

    // search by category and sort by highest to lowest price
    @GetMapping("/sort/price/high/category/{category}")
    public Iterable<Book> getByCategoryAndSortByPriceHighToLow(@PathVariable String category) {
        LOGGER.trace("Finding all books sorted from highest to lowest price");
        Iterable<Book> books = browsingService.sortByHighestPrice();
        LOGGER.trace("Finding all books in the category '" + category + "'");
        return books = browsingService.filterByCategory(books, category);
    }

    // search by category and sort by alphabet
    @GetMapping("/sort/alphabet/category/{category}")
    public Iterable<Book> getByCategoryAndSortByAlphabet(@PathVariable String category) {
        LOGGER.trace("Finding all books sorted by alphabetical title order");
        Iterable<Book> books = browsingService.sortByAlphabet();
        LOGGER.trace("Finding all books in the category '" + category + "'");
        return books = browsingService.filterByCategory(books, category);
    }

    // search by category and new books
    @GetMapping("/filter/new/category/{category}")
    public Iterable<Book> getByCategoryAndFilterNew(@PathVariable String category) {
        LOGGER.trace("Finding all books in the category '" + category + "'");
        Iterable<Book> books = browsingService.findAllByCategory(category);
        LOGGER.trace("Finding all books that have the quality NEW");
        return books = browsingService.filterByNewBooks(books);
    }

    // search by category and used books
    @GetMapping("/filter/used/category/{category}")
    public Iterable<Book> getByCategoryAndFilterUsed(@PathVariable String category) {
        LOGGER.trace("Finding all books in the category '" + category + "'");
        Iterable<Book> books = browsingService.findAllByCategory(category);
        LOGGER.trace("Finding all books that have the quality USED");
        return books = browsingService.filterByUsedBooks(books);
    }

    // search by isbn and sort by lowest to highest price
    @GetMapping("/sort/price/low/isbn/{isbn}")
    public Iterable<Book> getByISBNAndSortByPriceLowToHigh(@PathVariable int isbn) {
        LOGGER.trace("Finding all books sorted from lowest to highest price");
        Iterable<Book> books = browsingService.sortByLowestPrice();
        LOGGER.trace("Finding all books containing the following ISBN '" + isbn + "'");
        return books = browsingService.filterByIsbn(books, isbn);
    }

    // search by isbn and sort by highest to lowest price
    @GetMapping("/sort/price/high/isbn/{isbn}")
    public Iterable<Book> getByISBNAndSortByPriceHighToLow(@PathVariable int isbn) {
        LOGGER.trace("Finding all books sorted from highest to lowest price");
        Iterable<Book> books = browsingService.sortByHighestPrice();
        LOGGER.trace("Finding all books containing the following ISBN '" + isbn + "'");
        return books = browsingService.filterByIsbn(books, isbn);
    }

    // search by isbn and sort by alphabet
    @GetMapping("/sort/alphabet/isbn/{isbn}")
    public Iterable<Book> getByISBNAndSortByAlphabet(@PathVariable int isbn) {
        LOGGER.trace("Finding all books sorted by alphabetical title order");
        Iterable<Book> books = browsingService.sortByAlphabet();
        LOGGER.trace("Finding all books containing the following ISBN '" + isbn + "'");
        return books = browsingService.filterByIsbn(books, isbn);
    }

    // search by isbn and new books
    @GetMapping("/filter/new/isbn/{isbn}")
    public Iterable<Book> getByISBNAndFilterNew(@PathVariable int isbn) {
        LOGGER.trace("Finding all books containing the following ISBN '" + isbn + "'");
        Iterable<Book> books = browsingService.findAllByISBN(isbn);
        LOGGER.trace("Finding all books that have the quality NEW");
        return books = browsingService.filterByNewBooks(books);
    }

    // search by isbn and used books
    @GetMapping("/filter/used/isbn/{isbn}")
    public Iterable<Book> getByISBNAndFilterUsed(@PathVariable int isbn) {
        LOGGER.trace("Finding all books containing the following ISBN '" + isbn + "'");
        Iterable<Book> books = browsingService.findAllByISBN(isbn);
        LOGGER.trace("Finding all books that have the quality USED");
        return books = browsingService.filterByUsedBooks(books);
    }
}
