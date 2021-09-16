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
public class BrowsingController {
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
        System.out.println("asdasdasdasdasdasd" + sellerId);
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
}
