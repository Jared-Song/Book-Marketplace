package com.rmit.sept.books.Services;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import com.rmit.sept.books.Repositories.BookRepository;
import com.rmit.sept.books.model.Book;
import com.rmit.sept.books.model.Quality;
import com.rmit.sept.books.model.ServiceType;
import com.rmit.sept.books.model.User;
import com.rmit.sept.books.services.BookService;

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
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    private List<Book> books;
    private Book book1, book2;

    @BeforeEach
    public void setup() {
        book1 = new Book();
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

        book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book Title 2");
        book2.setAuthorName("Author Name 2");
        book2.setSellerId(1L);
        book2.setISBN(1234567);
        book2.setQuantity(5);
        book2.setCategory("Book Category 2");
        book2.setQuality(Quality.USED);
        book2.setPrice(89.99);
        book2.setRatingNo(1);
        book2.setRatingTotal(1);
        book2.setServiceType(ServiceType.PRINT_ON_DEMAND);

        books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
    }

    @Test
    @DisplayName("Test findAllBooks success") // test for finding all books
    public void testFindAllBooksSuccess() throws Exception {
        given(bookRepository.findAll()).willReturn(books);
        Iterable<Book> allBooks = bookService.findAllBooks();
        Assert.assertNotNull(allBooks);
    }

    @Test
    @DisplayName("Test findBySeller success") // test for finding all books by seller successfully
    public void testFindBySellerSuccess() throws Exception {
        User user = new User();
        given(bookRepository.findBySeller(user)).willReturn(books);
        Iterable<Book> allBooks = bookService.getAllBySeller(user);
        Assert.assertNotNull(allBooks);
    }

    @Test
    @DisplayName("Test findBySeller fail") // test for finding all books by seller which doesn't exist
    public void testFindBySellerFail() throws Exception {
        given(bookRepository.findBySeller(null)).willReturn(null);
        Iterable<Book> allBooks = bookService.getAllBySeller(null);
        Assert.assertNull(allBooks);
    }
}