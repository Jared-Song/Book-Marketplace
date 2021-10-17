package com.rmit.sept.browsing.Repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Iterator;

import javax.transaction.Transactional;

import com.rmit.sept.browsing.model.Book;
import com.rmit.sept.browsing.model.Quality;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    // testing for browsing when books have a given title
    @Test
    @Rollback(true)
    public void test_if_books_exist_with_title() {
        Iterable<Book> books = bookRepository.findByTitle("peril");
        assertThat(books).isNotEmpty();
    }

    // testing for browsing when no books have a given title
    @Test
    @Rollback(true)
    public void test_empty_result_if_no_books_with_title() {
        Iterable<Book> books = bookRepository.findByTitle("TestTitle");
        assertThat(books).isEmpty();
    }

    // testing for browsing when books have a given author's name
    @Test
    @Rollback(true)
    public void test_if_books_exist_with_author_name() {
        Iterable<Book> books = bookRepository.findByAuthorName("dave");
        assertThat(books).isNotEmpty();
    }

    // testing for browsing when no books have a given author's name
    @Test
    @Rollback(true)
    public void test_empty_result_if_no_books_with_author_name() {
        Iterable<Book> books = bookRepository.findByAuthorName("authorName");
        assertThat(books).isEmpty();
    }

    // testing for browsing when books have a given category
    @Test
    @Rollback(true)
    public void test_if_books_exist_with_category() {
        Iterable<Book> books = bookRepository.findByCategory("business");
        assertThat(books).isNotEmpty();
    }

    // testing for browsing when no books have a given category
    @Test
    @Rollback(true)
    public void test_empty_result_if_no_books_with_category() {
        Iterable<Book> books = bookRepository.findByCategory("TestCategory");
        assertThat(books).isEmpty();
    }

    // testing for browsing when books have a given isbn
    @Test
    @Rollback(true)
    public void test_if_books_exist_with_isbn() {
        Iterable<Book> books = bookRepository.findByisbn("198218291");
        assertThat(books).isNotEmpty();
    }

    // testing for browsing when no books have a given isbn
    @Test
    @Rollback(true)
    public void test_empty_result_if_no_books_with_isbn() {
        Iterable<Book> books = bookRepository.findByisbn("falseisbn");
        assertThat(books).isEmpty();
    }

    // testing for browsing when books have a given quality - new
    @Test
    @Rollback(true)
    public void test_if_books_exist_with_quality_new() {
        Iterable<Book> books = bookRepository.findByQuality(Quality.NEW);
        assertThat(books).isNotEmpty();
    }

    // testing for browsing when books have a given quality - used
    @Test
    @Rollback(true)
    public void test_if_books_exist_with_quality_used() {
        Iterable<Book> books = bookRepository.findByQuality(Quality.USED);
        assertThat(books).isNotEmpty();
    }

    // testing for browsing by books sorted by highest price
    @Test
    @Rollback(true)
    public void test_sort_by_highest_price() {
        Iterable<Book> books = bookRepository.sortByHighestPrice();
        Iterator<Book> iter = books.iterator();
        double maxPrice = Integer.MAX_VALUE;
        while (iter.hasNext()) {
            Book book = iter.next();
            double price = book.getPrice();
            assertThat(price <= maxPrice).isTrue();
            maxPrice = price;
        }
    }

    // testing for browsing by books sorted by lowest price
    @Test
    @Rollback(true)
    public void test_sort_by_lowest_price() {
        Iterable<Book> books = bookRepository.sortByLowestPrice();
        Iterator<Book> iter = books.iterator();
        double minPrice = Integer.MIN_VALUE;
        while (iter.hasNext()) {
            Book book = iter.next();
            double price = book.getPrice();
            assertThat(price >= minPrice).isTrue();
            minPrice = price;
        }
    }

    // testing for browsing by books sorted by alphabetical title order
    @Test
    @Rollback(true)
    public void test_sort_by_alphabet() {
        Iterable<Book> books = bookRepository.sortByAlphabet();
        Iterator<Book> iter = books.iterator();
        String prevTitle = "a";
        while (iter.hasNext()) {
            Book book = iter.next();
            String title = book.getTitle();
            assertThat(prevTitle.compareTo(title)).isGreaterThan(-1);
            title = prevTitle;
        }
    }

    // testing for browsing by books sorted by newest release
    @Test
    @Rollback(true)
    public void test_sort_by_newest_release() {
        Iterable<Book> books = bookRepository.sortByNewestRelease(10);
        Iterator<Book> iter = books.iterator();
        Date newestDate = new Date();
        while (iter.hasNext()) {
            Book book = iter.next();
            Date date = book.getcreated_At();
            assertThat(newestDate.compareTo(date)).isGreaterThan(-1);
            newestDate = date;
        }
    }

    // testing for browsing by books sorted by highest rating
    @Test
    @Rollback(true)
    public void test_sort_by_highest_rating() {
        Iterable<Book> books = bookRepository.sortByHighestRating(10);
        Iterator<Book> iter = books.iterator();
        double maxRating = 5.0;
        while (iter.hasNext()) {
            Book book = iter.next();
            double rating = book.getRatingTotal()/book.getRatingNo();
            assertThat(maxRating >= rating).isTrue();
            maxRating = rating;
        }
    }
}
