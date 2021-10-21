// package com.rmit.sept.books.Controllers;

// import static org.hamcrest.Matchers.is;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

// import java.util.ArrayList;
// import java.util.List;

// import com.rmit.sept.books.model.Book;
// import com.rmit.sept.books.model.Quality;
// import com.rmit.sept.books.model.ServiceType;
// import com.rmit.sept.books.model.User;
// import com.rmit.sept.books.security.JwtTokenProvider;
// import com.rmit.sept.books.services.BookService;
// import com.rmit.sept.books.services.MapValidationErrorService;
// import com.rmit.sept.books.web.BookController;

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
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;
// import org.springframework.test.web.servlet.RequestBuilder;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.validation.BindingResult;

// @SpringBootTest
// @ActiveProfiles("test")
// @RunWith(SpringRunner.class)
// public class BookControllerTest {
//     MockMvc mockMvc;

//     @Autowired
//     BookController bookController;

//     @MockBean
//     BookService bookService;

//     @MockBean
//     private MapValidationErrorService mapValidationErrorService;

//     @MockBean
//     private JwtTokenProvider tokenProvider;

//     @MockBean
//     private AuthenticationManager authenticationManager;

//     private List<User> users;
//     private List<Book> books;

//     @BeforeEach
//     public void setup() {
//         this.mockMvc = standaloneSetup(this.bookController).build(); // builds an instance of the book controller

//         User user1 = new User();
//         user1.setId(1L);
//         user1.setEmail("johndoe@gmail.com");
//         user1.setUsername("JohnDoe");
//         user1.setFullName("John Doe");
//         user1.setPassword("password");
//         user1.setAddress("1 John Street, Doeland");

//         Book book1 = new Book();
//         book1.setId(1L);
//         book1.setTitle("Book Title");
//         book1.setAuthorName("Author Name");
//         book1.setSellerId(1L);
//         book1.setISBN(123456);
//         book1.setQuantity(10);
//         book1.setCategory("Book Category");
//         book1.setQuality(Quality.NEW);
//         book1.setPrice(99.99);
//         book1.setRatingNo(0);
//         book1.setRatingTotal(0);
//         book1.setServiceType(ServiceType.E_BOOK);

//         Book book2 = new Book();
//         book2.setId(2L);
//         book2.setTitle("Book Title 2");
//         book2.setAuthorName("Author Name 2");
//         book2.setSellerId(1L);
//         book2.setISBN(1234567);
//         book2.setQuantity(5);
//         book2.setCategory("Book Category 2");
//         book2.setQuality(Quality.USED);
//         book2.setPrice(89.99);
//         book2.setRatingNo(1);
//         book2.setRatingTotal(1);
//         book2.setServiceType(ServiceType.PRINT_ON_DEMAND);

//         users = new ArrayList<>();
//         books = new ArrayList<>();
//         users.add(user1);
//         books.add(book1);
//         books.add(book2);
//     }

//     @Test
//     @DisplayName("Test findAllBooks") // test for getting all books
//     void testFindAllBooks() throws Exception {
//         // Mocking service
//         when(bookService.findAllBooks()).thenReturn(books);
//         mockMvc.perform(get("/api/books/all").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//                 .andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].title", is("Book Title")))
//                 .andExpect(jsonPath("$[0].authorName", is("Author Name"))).andExpect(jsonPath("$[0].sellerId", is(1)))
//                 .andExpect(jsonPath("$[0].isbn", is(123456))).andExpect(jsonPath("$[0].quantity", is(10)))
//                 .andExpect(jsonPath("$[0].category", is("Book Category")))
//                 .andExpect(jsonPath("$[0].quality", is(Quality.NEW.toString())))
//                 .andExpect(jsonPath("$[0].price", is(99.99))).andExpect(jsonPath("$[0].ratingNo", is(0)))
//                 .andExpect(jsonPath("$[0].ratingTotal", is(0)))
//                 .andExpect(jsonPath("$[0].serviceType", is(ServiceType.E_BOOK.toString())))

//                 .andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].title", is("Book Title 2")))
//                 .andExpect(jsonPath("$[1].authorName", is("Author Name 2"))).andExpect(jsonPath("$[1].sellerId", is(1)))
//                 .andExpect(jsonPath("$[1].isbn", is(1234567))).andExpect(jsonPath("$[1].quantity", is(5)))
//                 .andExpect(jsonPath("$[1].category", is("Book Category 2")))
//                 .andExpect(jsonPath("$[1].quality", is(Quality.USED.toString())))
//                 .andExpect(jsonPath("$[1].price", is(89.99))).andExpect(jsonPath("$[1].ratingNo", is(1)))
//                 .andExpect(jsonPath("$[1].ratingTotal", is(1)))
//                 .andExpect(jsonPath("$[1].serviceType", is(ServiceType.PRINT_ON_DEMAND.toString())));

//     }

//     @Test
//     @DisplayName("Test getBook success") // test for getting a book successfully
//     void testGetBookSuccess() throws Exception {
//         // Mocking service
//         when(bookService.findById(1L)).thenReturn(books.get(0));

//         mockMvc.perform(get("/api/books/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//                 .andExpect(jsonPath("id", is(1))).andExpect(jsonPath("title", is("Book Title")))
//                 .andExpect(jsonPath("authorName", is("Author Name"))).andExpect(jsonPath("sellerId", is(1)))
//                 .andExpect(jsonPath("isbn", is(123456))).andExpect(jsonPath("quantity", is(10)))
//                 .andExpect(jsonPath("category", is("Book Category")))
//                 .andExpect(jsonPath("quality", is(Quality.NEW.toString()))).andExpect(jsonPath("price", is(99.99)))
//                 .andExpect(jsonPath("ratingNo", is(0))).andExpect(jsonPath("ratingTotal", is(0)))
//                 .andExpect(jsonPath("serviceType", is(ServiceType.E_BOOK.toString())));
//     }

//     @Test
//     @DisplayName("Test getBook not found") // test for getting a book that doesn't exist
//     void testGetBookNotFound() throws Exception {
//         // Mocking service
//         when(bookService.findById(3L)).thenReturn(null);

//         RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/books/3")
//                 .contentType(MediaType.APPLICATION_JSON);
//         MvcResult result = mockMvc.perform(requestBuilder).andReturn();

//         MockHttpServletResponse response = result.getResponse();

//         assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
//         assertEquals("Book with ID 3 was not found", response.getContentAsString());

//     }

//     @Test
//     @DisplayName("Test addNewBook success") // test for adding a new book successfully
//     void testAddNewBookSuccess() throws Exception {
//         // Mocking service
//         when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class)))
//                 .thenReturn(null);
//         when(bookService.saveBook(ArgumentMatchers.any(Book.class))).thenReturn(books.get(0));
//         String inputJson = "{\n" + " \"title\":\"Title\",\n" + "\"authorName\":\"Author Name\",\n"
//                 + " \"sellerId\":\"1\",\n" + " \"category\":\"Category\",\n" + " \"isbn\":\"12345678\",\n"
//                 + " \"quantity\":\"99\",\n" + " \"quality\":\"0\",\n" + " \"bookStatus\":\"0\",\n"
//                 + " \"serviceType\":\"PRINT_ON_DEMAND\",\n" + " \"price\":\"99.99\"\n" + "}";
//         RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/books/new").accept(MediaType.APPLICATION_JSON)
//                 .content(inputJson).contentType(MediaType.APPLICATION_JSON);

//         MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//         MockHttpServletResponse response = result.getResponse();

//         assertEquals(HttpStatus.OK.value(), response.getStatus());
//     }

//     @Test
//     @DisplayName("Test addNewBook null seller error") // test for adding a new book without a seller
//     void testAddNewBookNullSellerError() throws Exception {
//         // Mocking service
//         when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class)))
//                 .thenReturn(null);
//         when(bookService.saveBook(ArgumentMatchers.any(Book.class))).thenReturn(null);
//         String inputJson = "{\n" + " \"title\":\"Book Title\",\n" + "\"authorName\":\"Author Name\",\n"
//                 + " \"category\":\"Book Category\",\n" + " \"isbn\":\"123456\",\n" + " \"quantity\":\"10\",\n"
//                 + " \"quality\":\"0\",\n" + " \"bookStatus\":\"0\",\n" + " \"serviceType\":\"E_BOOK\",\n"
//                 + " \"price\":\"99.99\"\n" + "}";
//         RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/books/new").accept(MediaType.APPLICATION_JSON)
//                 .content(inputJson).contentType(MediaType.APPLICATION_JSON);

//         MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//         MockHttpServletResponse response = result.getResponse();

//         assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
//         assertEquals("Unable to add the new book, User id not given!.", response.getContentAsString());
//     }

//     @Test
//     @DisplayName("Test addNewBook duplicate error") // test for adding a duplicate new book
//     void testAddNewBookDuplicateError() throws Exception {
//         // Mocking service
//         when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class)))
//                 .thenReturn(null);
//         when(bookService.saveBook(ArgumentMatchers.any(Book.class))).thenReturn(null);
//         String inputJson = "{\n" + " \"title\":\"Book Title\",\n" + "\"authorName\":\"Author Name\",\n"
//                 + " \"sellerId\":\"1\",\n" + " \"category\":\"Book Category\",\n" + " \"isbn\":\"123456\",\n"
//                 + " \"quantity\":\"10\",\n" + " \"quality\":\"0\",\n" + " \"bookStatus\":\"0\",\n"
//                 + " \"serviceType\":\"E_BOOK\",\n" + " \"price\":\"99.99\"\n" + "}";
//         RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/books/new").accept(MediaType.APPLICATION_JSON)
//                 .content(inputJson).contentType(MediaType.APPLICATION_JSON);

//         MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//         MockHttpServletResponse response = result.getResponse();

//         assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
//         assertEquals("Unable to add the new book, a copy of the book already exists.", response.getContentAsString());
//     }

//     @Test
//     @DisplayName("Test addNewBook badRequest") // test for adding a new book with invalid information
//     void testAddNewBookBadRequest() throws Exception {
//         // Mocking service
//         when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class)))
//                 .thenReturn(null);

//         String inputJson = "null";
//         RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/books/new").accept(MediaType.APPLICATION_JSON)
//                 .content(inputJson).contentType(MediaType.APPLICATION_JSON);

//         MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//         MockHttpServletResponse response = result.getResponse();

//         assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
//     }

//     @Test
//     @DisplayName("Test deleteBook success") // test for successfully deleting a book
//     void testDeleteBookSuccess() throws Exception {
//         // Mocking service
//         when(bookService.findById(1L)).thenReturn(books.get(0));

//         RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/books/1");

//         MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//         MockHttpServletResponse response = result.getResponse();

//         assertEquals(HttpStatus.OK.value(), response.getStatus());
//         assertEquals("Book with ID 1 was deleted", response.getContentAsString());
//     }

//     @Test
//     @DisplayName("Test deleteBook not found") // test for deleting a book that doesn't exist
//     void testDeleteBookNotFound() throws Exception {
//         // Mocking service
//         when(bookService.findById(3L)).thenReturn(null);

//         RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/books/3");

//         MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//         MockHttpServletResponse response = result.getResponse();

//         assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
//         assertEquals("Book with ID 3 was not found", response.getContentAsString());
//     }
// }
