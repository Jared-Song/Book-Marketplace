package com.rmit.sept.requests.Controllers;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import com.rmit.sept.requests.model.Book;
import com.rmit.sept.requests.model.Quality;
import com.rmit.sept.requests.model.ServiceType;
import com.rmit.sept.requests.model.User;
import com.rmit.sept.requests.model.Request;
import com.rmit.sept.requests.model.RequestType;
import com.rmit.sept.requests.security.JwtTokenProvider;
import com.rmit.sept.requests.services.MapValidationErrorService;
import com.rmit.sept.requests.services.RequestService;
import com.rmit.sept.requests.web.RequestController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class RequestsControllerTest {
    MockMvc mockMvc;

    @Autowired
    RequestController requestController;

    @MockBean
    RequestService requestService;

    @MockBean
    private MapValidationErrorService mapValidationErrorService;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @MockBean
    private AuthenticationManager authenticationManager;

    private List<User> users;
    private List<Book> books;
    private List<Request> requests;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(this.requestController).build(); // builds an instance of the request controller

        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("johndoe@gmail.com");
        user1.setUsername("JohnDoe");
        user1.setFullName("John Doe");
        user1.setPassword("password");
        user1.setAddress("1 John Street, Doeland");

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
        book2.setSellerId(1L);
        book2.setISBN(1234567);
        book2.setQuantity(5);
        book2.setCategory("Book Category 2");
        book2.setQuality(Quality.USED);
        book2.setPrice(89.99);
        book2.setRatingNo(1);
        book2.setRatingTotal(1);
        book2.setServiceType(ServiceType.PRINT_ON_DEMAND);

        Request request1 = new Request();
        request1.setId(1L);
        request1.setObjectId(1L);
        request1.setRequest("New Book");
        request1.setRequestType(RequestType.NEW_BOOK_LISTING);
        request1.setUser(user1);
        book1.setRequest(request1);

        Request request2 = new Request();
        request2.setId(2L);
        request2.setObjectId(2L);
        request2.setRequest("New Book");
        request2.setRequestType(RequestType.NEW_BOOK_LISTING);
        request2.setUser(user1);
        book1.setRequest(request2);

        users = new ArrayList<>();
        books = new ArrayList<>();
        requests = new ArrayList<>();
        users.add(user1);
        requests.add(request1);
        requests.add(request2);
        books.add(book1);
        books.add(book2);
    }

    @Test
    @DisplayName("Test findAllRequests") // test for getting all requests
    void testFindAllRequests() throws Exception {
        // Mocking service
        when(requestService.findAllRequests()).thenReturn(requests);
        mockMvc.perform(get("/api/requests/all").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].objectId", is(1)))
                .andExpect(jsonPath("$[0].request", is("New Book"))).andExpect(jsonPath("$[0].requestType", is(RequestType.NEW_BOOK_LISTING.toString())))
                .andExpect(jsonPath("$[0].user.id", is(1)))

                .andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].objectId", is(2)))
                .andExpect(jsonPath("$[1].request", is("New Book"))).andExpect(jsonPath("$[1].requestType", is(RequestType.NEW_BOOK_LISTING.toString())))
                .andExpect(jsonPath("$[1].user.id", is(1)));

    }

    @Test
    @DisplayName("Test getRequest success") // test for getting a request successfully
    void testGetRequestSuccess() throws Exception {
        // Mocking service
        when(requestService.findById(1L)).thenReturn(requests.get(0));
        mockMvc.perform(get("/api/requests/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.objectId", is(1)))
                .andExpect(jsonPath("$.request", is("New Book")))
                .andExpect(jsonPath("$.requestType", is(RequestType.NEW_BOOK_LISTING.toString())))
                .andExpect(jsonPath("$.user.id", is(1)));        
    }

    @Test
    @DisplayName("Test getRequest not found") // test for getting a request that doesn't exist
    void testGetRequestNotFound() throws Exception {
        // Mocking service
        when(requestService.findById(0L)).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/requests/0")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Request with ID 0 was not found", response.getContentAsString());
    }

    @Test
    @DisplayName("Test addNewRequest success") // test for adding a new request successfully
    void testAddNewRequestSuccess() throws Exception {
        // Mocking service
        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class)))
                .thenReturn(null);
        when(requestService.saveRequest(ArgumentMatchers.any(Request.class))).thenReturn(requests.get(0));
        String inputJson = "{\n" + " \"userId\":\"1\",\n" + "\"objectId\":\"1\",\n"
                + " \"requestType\":\"1\"\n" + "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/requests/new").accept(MediaType.APPLICATION_JSON)
                .content(inputJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    @DisplayName("Test addNewRequest null user error") // test for adding a new request without a user
    void testAddNewRequestNullUserError() throws Exception {
        // Mocking service
        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class)))
                .thenReturn(null);
        when(requestService.saveRequest(ArgumentMatchers.any(Request.class))).thenReturn(requests.get(0));
        String inputJson = "{\n" + "\"objectId\":\"1\",\n"
                + " \"requestType\":\"1\"\n" + "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/requests/new").accept(MediaType.APPLICATION_JSON)
                .content(inputJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
        assertEquals("Unable to add the new request, User id not given!.", response.getContentAsString());
    }

    @Test
    @DisplayName("Test addNewRequest duplicate error") // test for adding a duplicate new request
    void testAddNewRequestDuplicateError() throws Exception {
        // Mocking service
        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class)))
                .thenReturn(null);
                when(requestService.saveRequest(ArgumentMatchers.any(Request.class))).thenReturn(null);
                String inputJson = "{\n" + " \"userId\":\"1\",\n" + "\"objectId\":\"1\",\n"
                        + " \"requestType\":\"1\"\n" + "}";
                RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/requests/new").accept(MediaType.APPLICATION_JSON)
                        .content(inputJson).contentType(MediaType.APPLICATION_JSON);
        
                MvcResult result = mockMvc.perform(requestBuilder).andReturn();
                MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
        assertEquals("Unable to add the new request, a copy of the request already exists.", response.getContentAsString());
    }

    @Test
    @DisplayName("Test addNewRequest badRequest") // test for adding a new request with invalid information
    void testAddNewRequestBadRequest() throws Exception {
        // Mocking service
        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class)))
                .thenReturn(null);

        String inputJson = "null";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/requests/new").accept(MediaType.APPLICATION_JSON)
                .content(inputJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Test deleteRequest success") // test for successfully deleting a request
    void testDeleteRequestSuccess() throws Exception {
        // Mocking service
        when(requestService.findById(1L)).thenReturn(requests.get(0));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/requests/1");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Request with ID 1 was deleted", response.getContentAsString());
    }

    @Test
    @DisplayName("Test deleteRequest not found") // test for deleting a request that doesn't exist
    void testDeleteRequestNotFound() throws Exception {
        // Mocking service
        when(requestService.findById(0L)).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/requests/0");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Request with ID 0 was not found", response.getContentAsString());
    }
}
