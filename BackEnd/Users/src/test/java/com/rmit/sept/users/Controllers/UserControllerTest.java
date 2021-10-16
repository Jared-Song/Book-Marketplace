package com.rmit.sept.users.Controllers;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import com.rmit.sept.users.model.User;
import com.rmit.sept.users.security.JwtTokenProvider;
import com.rmit.sept.users.services.MapValidationErrorService;
import com.rmit.sept.users.services.UserService;
import com.rmit.sept.users.web.UserController;

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
public class UserControllerTest {
    MockMvc mockMvc;

    @Autowired
    UserController userController;

    @MockBean
    UserService userService;

    @MockBean
    private MapValidationErrorService mapValidationErrorService;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @MockBean
    private AuthenticationManager authenticationManager;

    private List<User> users;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(this.userController).build(); // builds an instance of the user controller

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

        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
    }

    @Test
    @DisplayName("Test findAllUsers") // test for getting all users
    void testFindAllUsers() throws Exception {
        // Mocking service
        when(userService.findAllUsers()).thenReturn(users);
        mockMvc.perform(get("/api/users/all").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].email", is("johndoe@gmail.com")))
                .andExpect(jsonPath("$[0].username", is("JohnDoe")))
                .andExpect(jsonPath("$[0].fullName", is("John Doe")))
                .andExpect(jsonPath("$[0].password", is("password")))
                .andExpect(jsonPath("$[0].address", is("1 John Street, Doeland")))

                .andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].email", is("johndoe2@gmail.com")))
                .andExpect(jsonPath("$[1].username", is("JohnDoe2")))
                .andExpect(jsonPath("$[1].fullName", is("John Doe")))
                .andExpect(jsonPath("$[1].password", is("password")))
                .andExpect(jsonPath("$[1].address", is("2 John Street, Doeland")));

    }

    @Test
    @DisplayName("Test getUser success") // test for getting a user successfully
    void testGetUserSuccess() throws Exception {
        // Mocking service
        when(userService.findById(1L)).thenReturn(users.get(0));

        mockMvc.perform(get("/api/users/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1))).andExpect(jsonPath("email", is("johndoe@gmail.com")))
                .andExpect(jsonPath("username", is("JohnDoe"))).andExpect(jsonPath("fullName", is("John Doe")))
                .andExpect(jsonPath("password", is("password")))
                .andExpect(jsonPath("address", is("1 John Street, Doeland")));
    }

    @Test
    @DisplayName("Test getUser not found") // test for getting a user that doesn't exist
    void testGetUserNotFound() throws Exception {
        // Mocking service
        when(userService.findById(3L)).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users/3")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("User with ID 3 was not found", response.getContentAsString());

    }

    @Test
    @DisplayName("Test registerUser success") // test for registering a user successfully
    void testRegisterUserSuccess() throws Exception {
        // Mocking service
        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class)))
                .thenReturn(null);
        when(userService.saveUser(ArgumentMatchers.any(User.class))).thenReturn(users.get(0));

        String inputJson = "{\n" + "    \"username\":\"TestUsername\",\n" + "    \"password\":\"password\"\n" + "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users/register")
                .accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    @DisplayName("Test registerUser badRequest") // test for registering a user with invalid credentials
    void testRegisterUserBadRequest() throws Exception {
        // Mocking service
        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class)))
                .thenReturn(null);
        when(userService.saveUser(ArgumentMatchers.any(User.class))).thenReturn(users.get(0));

        String inputJson = "null";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users/register")
                .accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Test deleteUser success") // test for successfully deleting a user
    void testDeleteUserSuccess() throws Exception {
        // Mocking service
        when(userService.findById(1L)).thenReturn(users.get(0));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/users/1");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("User with ID 1 was deleted", response.getContentAsString());
    }

    @Test
    @DisplayName("Test deleteUser not found") // test for deleting a user that doesn't exist
    void testDeleteUserNotFound() throws Exception {
        // Mocking service
        when(userService.findById(3L)).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/users/3");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("User with ID 3 was not found", response.getContentAsString());
    }
}
