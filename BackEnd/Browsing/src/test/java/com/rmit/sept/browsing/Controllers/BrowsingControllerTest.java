package com.rmit.sept.browsing.Controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import com.rmit.sept.browsing.model.Book;
import com.rmit.sept.browsing.model.BookImage;
import com.rmit.sept.browsing.model.Quality;
import com.rmit.sept.browsing.model.ServiceType;
import com.rmit.sept.browsing.model.User;
import com.rmit.sept.browsing.security.JwtTokenProvider;
import com.rmit.sept.browsing.services.BrowsingService;
import com.rmit.sept.browsing.web.BrowsingController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class BrowsingControllerTest {
        MockMvc mockMvc;

        @Autowired
        BrowsingController browsingController;

        @MockBean
        BrowsingService browsingService;

        @MockBean
        private JwtTokenProvider tokenProvider;

        @MockBean
        private AuthenticationManager authenticationManager;

        private List<User> users;
        private List<Book> books;
        private List<Book> books2;
        private List<Book> newBooks;
        private List<Book> usedBooks;

        private List<BookImage> book1Images;
        private List<BookImage> book2Images;

        @BeforeEach
        public void setup() {
                this.mockMvc = standaloneSetup(this.browsingController).build(); // builds an instance of the book
                                                                                 // controller

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
        @DisplayName("Test getByTitle") // test for getting by book title
        void testGetByTitle() throws Exception {
                // Mocking service
                when(browsingService.findAllByTitle("title")).thenReturn(books);
                mockMvc.perform(get("/api/browse/title/title").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()).andExpect(jsonPath("$[0].title", is("Book Title")))
                                .andExpect(jsonPath("$[1].title", is("Book Title 2")));
        }

        @Test
        @DisplayName("Test getByAuthorName") // test for getting by author name
        void testGetByAuthorName() throws Exception {
                // Mocking service
                when(browsingService.findAllByAuthorName("author")).thenReturn(books);
                mockMvc.perform(get("/api/browse/authorName/author").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()).andExpect(jsonPath("$[0].authorName", is("Author Name")))
                                .andExpect(jsonPath("$[1].authorName", is("Author Name 2")));
        }

        @Test
        @DisplayName("Test getBySeller") // test for getting by seller
        void testGetBySeller() throws Exception {
                // Mocking service
                when(browsingService.findAllBySeller(ArgumentMatchers.any(User.class))).thenReturn(newBooks);
                mockMvc.perform(get("/api/browse/sellerId/1").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].quality", is(Quality.NEW.toString())));
        }

        @Test
        @DisplayName("Test getByCategory") // test for getting by category
        void testGetByCategory() throws Exception {
                // Mocking service
                when(browsingService.findAllByCategory("category")).thenReturn(books);
                mockMvc.perform(get("/api/browse/category/category").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()).andExpect(jsonPath("$[0].category", is("Book Category")))
                                .andExpect(jsonPath("$[1].category", is("Book Category 2")));
        }

        @Test
        @DisplayName("Test getByISBN") // test for getting by isbn
        void testGetByISBN() throws Exception {
                // Mocking service
                when(browsingService.findAllByISBN(123)).thenReturn(books);
                mockMvc.perform(get("/api/browse/isbn/123").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()).andExpect(jsonPath("$[0].isbn", is(123456)))
                                .andExpect(jsonPath("$[1].isbn", is(1234567)));
        }

        @Test
        @DisplayName("Test getNewBooks") // test for getting all new books
        void testGetNewBooks() throws Exception {
                // Mocking service
                when(browsingService.findAllNewBooks()).thenReturn(books);
                mockMvc.perform(get("/api/browse/new").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].quality", is(Quality.NEW.toString())));
        }

        @Test
        @DisplayName("Test getUsedBooks") // test for getting all used books
        void testGetUsedBooks() throws Exception {
                // Mocking service
                when(browsingService.findAllUsedBooks()).thenReturn(usedBooks);
                mockMvc.perform(get("/api/browse/used").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].quality", is(Quality.USED.toString())));
        }

        @Test
        @DisplayName("Test sortByPriceHighToLow") // test for getting books sorted from highest to lowest price
        void testSortByPriceHighToLow() throws Exception {
                // Mocking service
                when(browsingService.sortByHighestPrice()).thenReturn(books);
                mockMvc.perform(get("/api/browse/price/high").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()).andExpect(jsonPath("$[0].price", is(99.99)))
                                .andExpect(jsonPath("$[1].price", is(89.99)));
        }

        @Test
        @DisplayName("Test sortByPriceLowToHigh") // test for getting all sorted from lowest to highest price
        void testSortByPriceLowToHigh() throws Exception {
                // Mocking service
                when(browsingService.sortByLowestPrice()).thenReturn(books2);
                mockMvc.perform(get("/api/browse/price/low").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()).andExpect(jsonPath("$[0].price", is(89.99)))
                                .andExpect(jsonPath("$[1].price", is(99.99)));
        }

        @Test
        @DisplayName("Test sortByAlphabet") // test for getting all sorted in alphabetical title order
        void testSortByAlphabet() throws Exception {
                // Mocking service
                when(browsingService.sortByAlphabet()).thenReturn(books);
                mockMvc.perform(get("/api/browse/alphabet").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0].title", is("Book Title")))
                                .andExpect(jsonPath("$[1].title", is("Book Title 2")));
        }

        @Test
        @DisplayName("Test bestSellers") // test for getting all sorted by highest rating
        void testBestSellers() throws Exception {
                // Mocking service
                when(browsingService.sortByHighestRating(2)).thenReturn(books2);
                mockMvc.perform(get("/api/browse/bestSellers/2").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0].ratingTotal", is(1)))
                                .andExpect(jsonPath("$[1].ratingTotal", is(0)));
        }

        @Test
        @DisplayName("Test random") // test for getting two randpo
        void testRandom() throws Exception {
                // Mocking service
                when(browsingService.random(2)).thenReturn(books2);
                mockMvc.perform(get("/api/browse/random/2").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0].id", is(2))).andExpect(jsonPath("$[1].id", is(1)));
        }
}
