package com.rmit.sept.login.Controllers;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import com.rmit.sept.login.model.User;
import com.rmit.sept.login.security.JwtTokenProvider;
import com.rmit.sept.login.services.MapValidationErrorService;
import com.rmit.sept.login.services.UserService;
import com.rmit.sept.login.web.LoginController;

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
public class LoginControllerTest {
    MockMvc mockMvc;

    @Autowired
    LoginController userController;

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
    @DisplayName("Test login success") // test for logging in successfully
    void testLoginSuccess() throws Exception {
        // Mocking service
        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class)))
                .thenReturn(null);

        String inputJson = "{\n" + "    \"username\":\"JohnDoe\",\n" + "    \"password\":\"password\"\n" + "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users/login")
                .accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andExpect(jsonPath("success", is(true))).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("Test login failure") // test for failing to log in with incorrect user information
    void testLoginFailure() throws Exception {
        // Mocking service
        String inputJson = "{\n" + "    \"username\":\"WrongUsername\",\n" + "    \"password\":\"password\"\n" + "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users/login")
                .accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(jsonPath("token", is("Bearer null"))).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    
}
