package com.rmit.sept.bk_loginservices.web;

import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.User;
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
    @GetMapping("/search/title/{title}")
    public Iterable<Book> getByTitle(@PathVariable String title) {
        return browsingService.findAllByTitle(title.toLowerCase());
    }

    // retrieve books in the catalogue with a specific author's name
    @GetMapping("/search/authorName/{authorName}")
    public Iterable<Book> getByAuthorFirstName(@PathVariable String authorName) {
        return browsingService.findAllByAuthorName(authorName.toLowerCase());
    }

    // retrieve books in the catalogue with a specific seller's id
    @GetMapping("/search/sellerId/{sellerId}")
    public Iterable<Book> getBySellerId(@PathVariable User sellerId) { //TODO: change User to int, and have it find the User variable
        System.out.println("asdasdasdasdasdasd" + sellerId);
        return browsingService.findAllBySellerId(sellerId);
    }

    // retrieve books in the catalogue with a specific genre
    @GetMapping("/search/genre/{genre}")
    public Iterable<Book> getByGenre(@PathVariable String genre) {
        return browsingService.findAllByGenre(genre.toLowerCase());
    }

    // retrieve books in the catalogue with a specific isbn
    @GetMapping("/search/isbn/{isbn}")
    public Iterable<Book> getByISBN(@PathVariable int isbn) {
        return browsingService.findAllByISBN(isbn);
    }

    // retrieve books in the catalogue that are new
    @GetMapping("/search/new")
    public Iterable<Book> getNewBooks() {
        return browsingService.findAllNewBooks();
    }

    // retrieve books in the catalogue that are used
    @GetMapping("/search/used")
    public Iterable<Book> getUsedBooks() {
        return browsingService.findAllUsedBooks();
    }
}
