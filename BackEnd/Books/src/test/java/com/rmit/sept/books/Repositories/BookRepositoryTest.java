package com.rmit.sept.books.Repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.transaction.Transactional;

import com.rmit.sept.books.model.Book;
import com.rmit.sept.books.model.BookStatus;
import com.rmit.sept.books.model.Quality;
import com.rmit.sept.books.model.Request;
import com.rmit.sept.books.model.ServiceType;
import com.rmit.sept.books.model.User;

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

    // testing for empty book repository
    @Test
    @Rollback(true)
    public void should_find_no_books_if_bookRepository_is_empty() {
        Iterable<Book> books = bookRepository.findAll();
        if (books.iterator().hasNext()) {
            assertThat(books).isNotEmpty();
        } else {
            assertThat(books).isEmpty();
        }
    }

    // test to find a book by id
    @Test
    @Rollback(true)
    public void find_book_by_id() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book Title");
        book1.setAuthorName("Author Name");
        book1.setISBN(123456);
        book1.setQuantity(10);
        book1.setCategory("Book Category");
        book1.setQuality(Quality.NEW);
        book1.setPrice(99.99);
        book1.setRatingNo(0);
        book1.setRatingTotal(0);
        book1.setServiceType(ServiceType.E_BOOK);
        bookRepository.save(book1);

        Book findBook = bookRepository.findById(1L).orElse(null);
        assertNotNull(findBook);
        assertThat(findBook).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(findBook).hasFieldOrPropertyWithValue("title", "Book Title");
        assertThat(findBook).hasFieldOrPropertyWithValue("authorName", "Author Name");
        assertThat(findBook).hasFieldOrPropertyWithValue("isbn", 123456);
        assertThat(findBook).hasFieldOrPropertyWithValue("quantity", 10);
        assertThat(findBook).hasFieldOrPropertyWithValue("category", "Book Category");
        assertThat(findBook).hasFieldOrPropertyWithValue("quality", Quality.NEW);
        assertThat(findBook).hasFieldOrPropertyWithValue("price", 99.99);
        assertThat(findBook).hasFieldOrPropertyWithValue("ratingNo", 0);
        assertThat(findBook).hasFieldOrPropertyWithValue("ratingTotal", 0);
        assertThat(findBook).hasFieldOrPropertyWithValue("serviceType", ServiceType.E_BOOK);
    }

    // test to delete a book
    @Test
    @Rollback(true)
    public void delete_book() {
        Book book1 = new Book();
        book1.setId(0L);
        book1.setTitle("Book Title");
        book1.setAuthorName("Author Name");
        book1.setISBN(123456);
        book1.setQuantity(10);
        book1.setCategory("Book Category");
        book1.setQuality(Quality.NEW);
        book1.setPrice(99.99);
        book1.setRatingNo(0);
        book1.setRatingTotal(0);
        book1.setServiceType(ServiceType.E_BOOK);
        bookRepository.save(book1);
        bookRepository.delete(book1);
        Book findBook = bookRepository.findById(0L).orElse(null);
        assertNull(findBook);
    }

    // test to save a new book
    @Test
    @Rollback(true)
    public void save_new_book() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book Title");
        book1.setAuthorName("Author Name");
        book1.setISBN(123456);
        book1.setQuantity(10);
        book1.setCategory("Book Category");
        book1.setQuality(Quality.NEW);
        book1.setPrice(99.99);
        book1.setRatingNo(0);
        book1.setRatingTotal(0);
        book1.setServiceType(ServiceType.E_BOOK);
        bookRepository.save(book1);

        Book findBook = bookRepository.findById(1L).orElse(null);
        assertNotNull(findBook);
    }

    // test to see if a book exists with valid params
    @Test
    @Rollback(true)
    public void book_exists_returns_true_if_book_exists() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book Title");
        book1.setAuthorName("Author Name");
        book1.setISBN(123456);
        book1.setQuantity(10);
        book1.setCategory("Book Category");
        book1.setQuality(Quality.NEW);
        book1.setPrice(99.99);
        book1.setRatingNo(0);
        book1.setRatingTotal(0);
        book1.setServiceType(ServiceType.E_BOOK);
        book1.setBookStatus(BookStatus.AVAILABLE);
        User seller = new User();
        seller.setId(1L);
        book1.setSeller(seller);
        Request request = new Request();
        request.setId(1L);
        book1.setRequest(request);
        bookRepository.save(book1);

        boolean newbookExists = bookRepository.bookExists(seller, "Book Title".toLowerCase(),
                "Author Name".toLowerCase(), "Book Category".toLowerCase(), 123456, Quality.NEW);
        assertEquals(true, newbookExists);
    }

    // test to see if a book exists with invalid params
    @Test
    @Rollback(true)
    public void book_exists_returns_false_if_book_does_not_exist() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book Title");
        book1.setAuthorName("Author Name");
        book1.setISBN(123456);
        book1.setQuantity(10);
        book1.setCategory("Book Category");
        book1.setQuality(Quality.NEW);
        book1.setPrice(99.99);
        book1.setRatingNo(0);
        book1.setRatingTotal(0);
        book1.setServiceType(ServiceType.E_BOOK);
        book1.setBookStatus(BookStatus.AVAILABLE);
        User seller = new User();
        seller.setId(1L);
        book1.setSeller(seller);
        Request request = new Request();
        request.setId(1L);
        book1.setRequest(request);
        bookRepository.save(book1);

        boolean newbookExists = bookRepository.bookExists(seller, "Wrong Book Title".toLowerCase(),
                "Author Name".toLowerCase(), "Book Category".toLowerCase(), 123456, Quality.NEW);
        assertEquals(false, newbookExists);
    }

    // test to find a book with valid params
    @Test
    @Rollback(true)
    public void find_with_params_returns_correct_book_if_book_exists() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book Title");
        book1.setAuthorName("Author Name");
        book1.setISBN(123456);
        book1.setQuantity(10);
        book1.setCategory("Book Category");
        book1.setQuality(Quality.NEW);
        book1.setPrice(99.99);
        book1.setRatingNo(0);
        book1.setRatingTotal(0);
        book1.setServiceType(ServiceType.E_BOOK);
        book1.setBookStatus(BookStatus.AVAILABLE);
        User seller = new User();
        seller.setId(1L);
        book1.setSeller(seller);
        Request request = new Request();
        request.setId(1L);
        book1.setRequest(request);
        bookRepository.save(book1);

        Book findBook = bookRepository.findWithParams(seller, "Book Title".toLowerCase(), "Author Name".toLowerCase(),
                "Book Category".toLowerCase(), 123456, Quality.NEW);
        assertEquals(book1.getTitle(), findBook.getTitle());
        assertEquals(book1.getAuthorName(), findBook.getAuthorName());
        assertEquals(book1.getISBN(), findBook.getISBN());
        assertEquals(book1.getQuantity(), findBook.getQuantity());
        assertEquals(book1.getCategory(), findBook.getCategory());
        assertEquals(book1.getQuality(), findBook.getQuality());
        assertEquals(book1.getPrice(), findBook.getPrice());
        assertEquals(book1.getRatingNo(), findBook.getRatingNo());
        assertEquals(book1.getRatingTotal(), findBook.getRatingTotal());
        assertEquals(book1.getServiceType(), findBook.getServiceType());
        assertEquals(book1.getBookStatus(), findBook.getBookStatus());
        assertEquals(book1.getSeller().getId(), findBook.getSeller().getId());
    }

    // test to find a book with valid params
    @Test
    @Rollback(true)
    public void find_with_params_returns_null_book_if_book_does_not_exist() {
        User seller = new User();
        seller.setId(1L);

        Book findBook = bookRepository.findWithParams(seller, "Book Title".toLowerCase(), "Author Name".toLowerCase(),
                "Book Category".toLowerCase(), 123456, Quality.NEW);
        assertNull(findBook);

    }
}
