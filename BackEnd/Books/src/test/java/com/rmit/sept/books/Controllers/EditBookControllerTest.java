// package com.rmit.sept.books.Controllers;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

// import java.util.ArrayList;
// import java.util.List;

// import com.rmit.sept.books.model.Book;
// import com.rmit.sept.books.model.BookForm;
// import com.rmit.sept.books.model.Quality;
// import com.rmit.sept.books.model.ServiceType;
// import com.rmit.sept.books.model.User;
// import com.rmit.sept.books.services.BookService;
// import com.rmit.sept.books.services.EditBookService;
// import com.rmit.sept.books.services.UserService;
// import com.rmit.sept.books.web.EditBookController;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.runner.RunWith;
// import org.mockito.ArgumentMatchers;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.mock.web.MockHttpServletResponse;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;
// import org.springframework.test.web.servlet.RequestBuilder;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// @SpringBootTest
// @ActiveProfiles("test")
// @RunWith(SpringRunner.class)
// public class EditBookControllerTest {
//         MockMvc mockMvc;

//         @Autowired
//         EditBookController editBookController;

//         @MockBean
//         UserService userService;

//         @MockBean
//         BookService bookService;

//         @MockBean
//         EditBookService editBookService;

//         private List<User> users;
//         private List<Book> books;

//         @BeforeEach
//         public void setup() {
//                 this.mockMvc = standaloneSetup(this.editBookController).build(); // builds an instance of the editBook
//                                                                                  // controller

//                 User user1 = new User();
//                 user1.setId(1L);
//                 user1.setEmail("johndoe@gmail.com");
//                 user1.setUsername("JohnDoe");
//                 user1.setFullName("John Doe");
//                 user1.setPassword("password");
//                 user1.setAddress("1 John Street, Doeland");

//                 Book book1 = new Book();
//                 book1.setId(1L);
//                 book1.setTitle("Book Title");
//                 book1.setAuthorName("Author Name");
//                 book1.setSellerId(1L);
//                 book1.setISBN(123456);
//                 book1.setQuantity(10);
//                 book1.setCategory("Book Category");
//                 book1.setQuality(Quality.NEW);
//                 book1.setPrice(99.99);
//                 book1.setRatingNo(0);
//                 book1.setRatingTotal(0);
//                 book1.setServiceType(ServiceType.E_BOOK);

//                 Book book2 = new Book();
//                 book2.setId(2L);
//                 book2.setTitle("Book Title 2");
//                 book2.setAuthorName("Author Name 2");
//                 book2.setSellerId(1L);
//                 book2.setISBN(1234567);
//                 book2.setQuantity(5);
//                 book2.setCategory("Book Category 2");
//                 book2.setQuality(Quality.USED);
//                 book2.setPrice(89.99);
//                 book2.setRatingNo(1);
//                 book2.setRatingTotal(1);
//                 book2.setServiceType(ServiceType.PRINT_ON_DEMAND);

//                 users = new ArrayList<>();
//                 books = new ArrayList<>();
//                 users.add(user1);
//                 books.add(book1);
//                 books.add(book2);
//         }

//         @Test
//         @DisplayName("Test editBook success") // test for editing a book successfully
//         void testEditBookSuccess() throws Exception {
//                 // Mocking service
//                 when(bookService.findById(1L)).thenReturn(books.get(0));
//                 when(editBookService.updateBook(ArgumentMatchers.any(BookForm.class), ArgumentMatchers.any(Book.class)))
//                                 .thenReturn(books.get(0));

//                 String inputJson = "{\n" + "\"sellerId\":\"1\",\n" + "\"quality\":\"NEW\"\n" + "}";
//                 RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/editBook/1")
//                                 .accept(MediaType.APPLICATION_JSON).content(inputJson)
//                                 .contentType(MediaType.APPLICATION_JSON);

//                 MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//                 MockHttpServletResponse response = result.getResponse();

//                 assertEquals(HttpStatus.OK.value(), response.getStatus());
//                 assertEquals("Successfully updated book details", response.getContentAsString());
//         }

//         @Test
//         @DisplayName("Test editBook null seller error") // test for editing a book so it is sold by an invalid seller
//         void testEditBookNullSellerError() throws Exception {
//                 // Mocking service
//                 when(bookService.findById(1L)).thenReturn(books.get(0));
//                 String inputJson = "{\n" + "\"quality\":\"NEW\"\n" + "}";
//                 RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/editBook/1")
//                                 .accept(MediaType.APPLICATION_JSON).content(inputJson)
//                                 .contentType(MediaType.APPLICATION_JSON);

//                 MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//                 MockHttpServletResponse response = result.getResponse();

//                 assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
//                 assertEquals("Unable to add the new book details, User to tie to not found!.",
//                                 response.getContentAsString());
//         }

//         @Test
//         @DisplayName("Test editBook duplicate error") // test for editing a book so it becomes a duplicate copy of
//                                                       // another book
//         void testEditBookDuplicateError() throws Exception {
//                 // Mocking service

//                 when(bookService.findById(1L)).thenReturn(books.get(0));
//                 when(editBookService.updateBook(ArgumentMatchers.any(BookForm.class), ArgumentMatchers.any(Book.class)))
//                                 .thenReturn(null);
//                 String inputJson = "{\n" + " \"title\":\"Book Title\",\n" + "\"authorName\":\"Author Name\",\n"
//                                 + " \"sellerId\":\"1\",\n" + " \"category\":\"Book Category\",\n"
//                                 + " \"isbn\":\"123456\",\n" + " \"quantity\":\"10\",\n" + " \"quality\":\"0\",\n"
//                                 + " \"bookStatus\":\"0\",\n" + " \"serviceType\":\"E_BOOK\",\n"
//                                 + " \"price\":\"99.99\"\n" + "}";
//                 RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/editBook/1")
//                                 .accept(MediaType.APPLICATION_JSON).content(inputJson)
//                                 .contentType(MediaType.APPLICATION_JSON);

//                 MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//                 MockHttpServletResponse response = result.getResponse();

//                 assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
//                 assertEquals("Unable to save details for book, a copy of the book already exists.",
//                                 response.getContentAsString());
//         }

//         @Test
//         @DisplayName("Test editBook not found") // test for editing a book that doesn't exist
//         void testEditBookNotFound() throws Exception {
//                 // Mocking service
//                 when(userService.findById(3L)).thenReturn(null);

//                 String inputJson = "{\n" + "\"sellerId\":\"1\",\n" + "\"quality\":\"NEW\"\n" + "}";
//                 RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/editBook/3")
//                                 .accept(MediaType.APPLICATION_JSON).content(inputJson)
//                                 .contentType(MediaType.APPLICATION_JSON);

//                 MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//                 MockHttpServletResponse response = result.getResponse();

//                 assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
//                 assertEquals("Book with ID 3 was not found", response.getContentAsString());
//         }
// }