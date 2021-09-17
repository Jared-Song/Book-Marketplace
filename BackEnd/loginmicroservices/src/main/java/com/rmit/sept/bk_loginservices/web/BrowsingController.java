package com.rmit.sept.bk_loginservices.web;

import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.services.BrowsingService;

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
    @Autowired
    private BrowsingService browsingService;

    // retrieve books in the catalogue with a specific title
    @GetMapping("/title/{title}")
    public Iterable<Book> getByTitle(@PathVariable String title) {
        return browsingService.findAllByTitle(title.toLowerCase());
    }

    // retrieve books in the catalogue with a specific author's name
    @GetMapping("/authorName/{authorName}")
    public Iterable<Book> getByAuthorFirstName(@PathVariable String authorName) {
        return browsingService.findAllByAuthorName(authorName.toLowerCase());
    }

    // retrieve books in the catalogue with a specific seller's id
    @GetMapping("/sellerId/{sellerId}")
    public Iterable<Book> getBySellerId(@PathVariable Long sellerId) {
        return browsingService.findAllBySellerId(sellerId);
    }

    // retrieve books in the catalogue with a specific category
    @GetMapping("/category/{category}")
    public Iterable<Book> getByCategory(@PathVariable String category) {
        return browsingService.findAllByCategory(category.toLowerCase());
    }

    // retrieve books in the catalogue with a specific isbn
    @GetMapping("/isbn/{isbn}")
    public Iterable<Book> getByISBN(@PathVariable int isbn) {
        return browsingService.findAllByISBN(isbn);
    }

    // retrieve books in the catalogue that are new
    @GetMapping("/new")
    public Iterable<Book> getNewBooks() {
        return browsingService.findAllNewBooks();
    }

    // retrieve books in the catalogue that are used
    @GetMapping("/used")
    public Iterable<Book> getUsedBooks() {
        return browsingService.findAllUsedBooks();
    }

    // retrieve all books from highest to lowest price
    @GetMapping("/price/high")
    public Iterable<Book> sortByPriceHighToLow() {
        return browsingService.sortByHighestPrice();
    }

    // retrieve all books from lowest to highest price
    @GetMapping("/price/low")
    public Iterable<Book> sortByPriceLowToHigh() {
        return browsingService.sortByLowestPrice();
    }

    // retrieve all books in aphabetical order
    @GetMapping("/alphabet")
    public Iterable<Book> sortByAlphabet() {
        return browsingService.sortByAlphabet();
    }

    // retrieve a given number of books by most recently created
    @GetMapping("/newReleases/{size}")
    public Iterable<Book> newReleases(@PathVariable int size) {
        return browsingService.sortByNewestRelease(size);
    }

    // retrieve a given number of books with the highest ratings
    @GetMapping("/bestSellers/{size}")
    public Iterable<Book> bestSellers(@PathVariable int size) {
        return browsingService.sortByHighestRating(size);
    }

    // retrieve a given number of random books
    @GetMapping("/random/{size}")
    public Iterable<Book> random(@PathVariable int size) {
        return browsingService.random(size);
    }

    // search by title and sort by lowest to highest price
    @GetMapping("/sort/price/low/title/{title}")
    public Iterable<Book> getByTitleAndSortByPriceLowToHigh(@PathVariable String title) {
        Iterable<Book> books = browsingService.sortByLowestPrice();
        return browsingService.filterByTitle(books, title);
    }

    // search by title and sort by highest to lowest price
    @GetMapping("/sort/price/high/title/{title}")
    public Iterable<Book> getByTitleAndSortByPriceHighToLow(@PathVariable String title) {
        Iterable<Book> books = browsingService.sortByHighestPrice();
        return browsingService.filterByTitle(books, title);
    }

    // search by title and sort by alphabet
    @GetMapping("/sort/alphabet/title/{title}")
    public Iterable<Book> getByTitleAndSortByAlphabet(@PathVariable String title) {
        Iterable<Book> books = browsingService.sortByAlphabet();
        return browsingService.filterByTitle(books, title);
    }

    // search by title and new books
    @GetMapping("/filter/new/title/{title}")
    public Iterable<Book> getByTitleAndFilterNew(@PathVariable String title) {
        Iterable<Book> books = browsingService.findAllByTitle(title);
        return browsingService.filterByNewBooks(books);
    }

    // search by title and used books
    @GetMapping("/filter/used/title/{title}")
    public Iterable<Book> getByTitleAndFilterUsed(@PathVariable String title) {
        Iterable<Book> books = browsingService.findAllByTitle(title);
        return browsingService.filterByUsedBooks(books);
    }

    // search by author name and sort by lowest to highest price
    @GetMapping("/sort/price/low/authorName/{authorName}")
    public Iterable<Book> getByAuthorNameAndSortByPriceLowToHigh(@PathVariable String authorName) {
        Iterable<Book> books = browsingService.sortByLowestPrice();
        return books = browsingService.filterByAuthorName(books, authorName);
    }

    // search by author name and sort by highest to lowest price
    @GetMapping("/sort/price/high/authorName/{authorName}")
    public Iterable<Book> getByAuthorNameAndSortByPriceHighToLow(@PathVariable String authorName) {
        Iterable<Book> books = browsingService.sortByHighestPrice();
        return books = browsingService.filterByAuthorName(books, authorName);
    }

    // search by author name and sort by alphabet
    @GetMapping("/sort/alphabet/authorName/{authorName}")
    public Iterable<Book> getByAuthorNameAndSortByAlphabet(@PathVariable String authorName) {
        Iterable<Book> books = browsingService.sortByAlphabet();
        return books = browsingService.filterByAuthorName(books, authorName);
    }

    // search by author name and new books
    @GetMapping("/filter/new/authorName/{authorName}")
    public Iterable<Book> getByAuthorNameAndFilterNew(@PathVariable String authorName) {
        Iterable<Book> books = browsingService.findAllByAuthorName(authorName);
        return books = browsingService.filterByNewBooks(books);
    }

    // search by author name and used books
    @GetMapping("/filter/used/authorName/{authorName}")
    public Iterable<Book> getByAuthorNameAndFilterUsed(@PathVariable String authorName) {
        Iterable<Book> books = browsingService.findAllByAuthorName(authorName);
        return books = browsingService.filterByUsedBooks(books);
    }

    // search by category and sort by lowest to highest price
    @GetMapping("/sort/price/low/category/{category}")
    public Iterable<Book> getByCategoryAndSortByPriceLowToHigh(@PathVariable String category) {
        Iterable<Book> books = browsingService.sortByLowestPrice();
        return books = browsingService.filterByCategory(books, category);
    }

    // search by category and sort by highest to lowest price
    @GetMapping("/sort/price/high/category/{category}")
    public Iterable<Book> getByCategoryAndSortByPriceHighToLow(@PathVariable String category) {
        Iterable<Book> books = browsingService.sortByHighestPrice();
        return books = browsingService.filterByCategory(books, category);
    }

    // search by category and sort by alphabet
    @GetMapping("/sort/alphabet/category/{category}")
    public Iterable<Book> getByCategoryAndSortByAlphabet(@PathVariable String category) {
        Iterable<Book> books = browsingService.sortByAlphabet();
        return books = browsingService.filterByCategory(books, category);
    }

    // search by category and new books
    @GetMapping("/filter/new/category/{category}")
    public Iterable<Book> getByCategoryAndFilterNew(@PathVariable String category) {
        Iterable<Book> books = browsingService.findAllByCategory(category);
        return books = browsingService.filterByNewBooks(books);
    }

    // search by category and used books
    @GetMapping("/filter/used/category/{category}")
    public Iterable<Book> getByCategoryAndFilterUsed(@PathVariable String category) {
        Iterable<Book> books = browsingService.findAllByCategory(category);
        return books = browsingService.filterByUsedBooks(books);
    }



     // search by isbn and sort by lowest to highest price
     @GetMapping("/sort/price/low/isbn/{isbn}")
     public Iterable<Book> getByISBNAndSortByPriceLowToHigh(@PathVariable int isbn) {
         Iterable<Book> books = browsingService.sortByLowestPrice();
         return books = browsingService.filterByIsbn(books, isbn);
     }
 
     // search by isbn and sort by highest to lowest price
     @GetMapping("/sort/price/high/isbn/{isbn}")
     public Iterable<Book> getByISBNAndSortByPriceHighToLow(@PathVariable int isbn) {
         Iterable<Book> books = browsingService.sortByHighestPrice();
         return books = browsingService.filterByIsbn(books, isbn);
     }
 
     // search by isbn and sort by alphabet
     @GetMapping("/sort/alphabet/isbn/{isbn}")
     public Iterable<Book> getByISBNAndSortByAlphabet(@PathVariable int isbn) {
         Iterable<Book> books = browsingService.sortByAlphabet();
         return books = browsingService.filterByIsbn(books, isbn);
     }
 
     // search by isbn and new books
     @GetMapping("/filter/new/isbn/{isbn}")
     public Iterable<Book> getByISBNAndFilterNew(@PathVariable int isbn) {
         Iterable<Book> books = browsingService.findAllByISBN(isbn);
         return books = browsingService.filterByNewBooks(books);
     }
 
     // search by isbn and used books
     @GetMapping("/filter/used/isbn/{isbn}")
     public Iterable<Book> getByISBNAndFilterUsed(@PathVariable int isbn) {
         Iterable<Book> books = browsingService.findAllByISBN(isbn);
         return books = browsingService.filterByUsedBooks(books);
     }
}
