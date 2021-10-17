package com.rmit.sept.browsing.Services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rmit.sept.browsing.Repositories.BookRepository;
import com.rmit.sept.browsing.model.Book;
import com.rmit.sept.browsing.model.BookImage;
import com.rmit.sept.browsing.model.Quality;
import com.rmit.sept.browsing.model.ServiceType;
import com.rmit.sept.browsing.model.User;
import com.rmit.sept.browsing.services.BrowsingService;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class BrowsingServiceTest {
    @Autowired
    private BrowsingService browsingService;

    @MockBean
    private BookRepository bookRepository;

    private List<User> users;
    private List<Book> books;
    private List<Book> books2;
    private List<Book> newBooks;
    private List<Book> usedBooks;

    private List<BookImage> book1Images;
    private List<BookImage> book2Images;

    @BeforeEach
    public void setup() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("johndoe@gmail.com");
        user1.setUsername("JohnDoe");
        user1.setFullName("John Doe");
        user1.setPassword("password");
        user1.setAddress("1 John Street, Doeland");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("johndoe2@gmail.com");
        user2.setUsername("JohnDoe2");
        user2.setFullName("John Doe");
        user2.setPassword("password");
        user2.setAddress("2 John Street, Doeland");

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book Title");
        book1.setAuthorName("Author Name");
        book1.setSellerId(1L);
        book1.setISBN(123456);
        book1.setQuantity(10);
        book1.setCategory("Book Category");
        book1.setQuality(Quality.NEW);
        book1.setPrice(99.99);
        book1.setRatingNo(0);
        book1.setRatingTotal(0);
        book1.setServiceType(ServiceType.E_BOOK);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book Title 2");
        book2.setAuthorName("Author Name 2");
        book2.setSellerId(2L);
        book2.setISBN(1234567);
        book2.setQuantity(5);
        book2.setCategory("Book Category 2");
        book2.setQuality(Quality.USED);
        book2.setPrice(89.99);
        book2.setRatingNo(1);
        book2.setRatingTotal(1);
        book2.setServiceType(ServiceType.PRINT_ON_DEMAND);

        BookImage bookImage1 = new BookImage();
        bookImage1.setId(1L);
        bookImage1.setBook(book1);
        bookImage1.setImageNumber(1);
        bookImage1.setUrl("url");
        book1Images = new ArrayList<>();
        book1Images.add(bookImage1);

        BookImage bookImage2 = new BookImage();
        bookImage2.setId(2L);
        bookImage2.setBook(book2);
        bookImage2.setImageNumber(1);
        bookImage2.setUrl("url");
        book2Images = new ArrayList<>();
        book2Images.add(bookImage2);

        users = new ArrayList<>();
        books = new ArrayList<>();
        books2 = new ArrayList<>();
        newBooks = new ArrayList<>();
        usedBooks = new ArrayList<>();

        users.add(user1);
        users.add(user2);
        book1.setImageURL(book1Images);
        book1.setImageFront("url");
        book2.setImageURL(book2Images);
        book2.setImageFront("url");

        books.add(book1);
        books.add(book2);
        books2.add(book2);
        books2.add(book1);
        newBooks.add(book1);
        usedBooks.add(book2);
    }

    @Test
    @DisplayName("Test findByTitle") // test for finding by title
    public void testFindByTitle() throws Exception {
        given(bookRepository.findByTitle("title")).willReturn(books);
        Iterable<Book> books = browsingService.findAllByTitle("title");
        Assert.assertNotNull(books);
    }

    @Test
    @DisplayName("Test findByAuthorName") // test for finding by author name
    public void testFindByAuthorName() throws Exception {
        given(bookRepository.findByAuthorName("author")).willReturn(books);
        Iterable<Book> books = browsingService.findAllByTitle("author");
        Assert.assertNotNull(books);
    }

    @Test
    @DisplayName("Test findByisbn") // test for finding by isbn
    public void testFindByisbn() throws Exception {
        given(bookRepository.findByisbn("123456")).willReturn(books);
        Iterable<Book> books = browsingService.findAllByISBN(123456);
        Assert.assertNotNull(books);
    }

    @Test
    @DisplayName("Test findByCategory") // test for finding by category
    public void testFindByCategory() throws Exception {
        given(bookRepository.findByCategory("book")).willReturn(books);
        Iterable<Book> books = browsingService.findAllByTitle("book");
        Assert.assertNotNull(books);
    }

    @Test
    @DisplayName("Test findAllNewBooks") // test for finding all new books
    public void testFindAllNewBooks() throws Exception {
        given(bookRepository.findByQuality(Quality.NEW)).willReturn(newBooks);
        Iterable<Book> books = browsingService.findAllNewBooks();
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book book = iter.next();
            assertThat(book.getQuality()).isEqualTo(Quality.NEW);
        }
    }

    @Test
    @DisplayName("Test findAllUsedBooks") // test for finding all used books
    public void testFindAllUsedBooks() throws Exception {
        given(bookRepository.findByQuality(Quality.USED)).willReturn(usedBooks);
        Iterable<Book> books = browsingService.findAllUsedBooks();
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book book = iter.next();
            assertThat(book.getQuality()).isEqualTo(Quality.USED);
        }
    }

    @Test
    @DisplayName("Test sortByHighestPrice") // test for sorting by highest price
    public void testSortByHighestPrice() throws Exception {
        given(bookRepository.sortByHighestPrice()).willReturn(books);
        Iterable<Book> books = browsingService.sortByHighestPrice();
        Iterator<Book> iter = books.iterator();
        double maxPrice = Integer.MAX_VALUE;
        while (iter.hasNext()) {
            Book book = iter.next();
            double price = book.getPrice();
            assertThat(price <= maxPrice).isTrue();
            maxPrice = price;
        }
    }

    @Test
    @DisplayName("Test sortByLowestPrice") // test for sorting by lowest price
    public void testSortByLowestPrice() throws Exception {
        given(bookRepository.sortByHighestPrice()).willReturn(books2);
        Iterable<Book> books = browsingService.sortByLowestPrice();
        Iterator<Book> iter = books.iterator();
        double minPrice = Integer.MIN_VALUE;
        while (iter.hasNext()) {
            Book book = iter.next();
            double price = book.getPrice();
            assertThat(price >= minPrice).isTrue();
            minPrice = price;
        }
    }

    @Test
    @DisplayName("Test sortByAlphabet") // test for sorting by alphabetical title order
    public void testSortByAlphabet() throws Exception {
        given(bookRepository.sortByAlphabet()).willReturn(books);
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

    @Test
    @DisplayName("Test sortByNewestRelease") // testing for browsing by books sorted by newest release
    public void testSortByNewestRelease() {
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

    @Test
    @DisplayName("Test sortByHighestRating") // testing for browsing by books sorted by highest rating
    public void testSortByHighestRating() {
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