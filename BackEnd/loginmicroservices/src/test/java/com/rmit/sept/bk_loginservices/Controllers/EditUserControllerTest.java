package com.rmit.sept.bk_loginservices.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import com.rmit.sept.bk_loginservices.model.User;
import com.rmit.sept.bk_loginservices.model.UserForm;
import com.rmit.sept.bk_loginservices.services.MapValidationErrorService;
import com.rmit.sept.bk_loginservices.services.UserService;
import com.rmit.sept.bk_loginservices.validator.UserValidator;
import com.rmit.sept.bk_loginservices.web.EditUserController;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class EditUserControllerTest {
    MockMvc mockMvc;

    @Autowired
    EditUserController editUserController;

    @MockBean
    UserService userService;

    @MockBean
    private UserValidator userValidator;

    @MockBean
    private MapValidationErrorService mapValidationErrorService;

    private List<User> users;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(this.editUserController).build(); // builds an instance of the edit user controller

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
    @DisplayName("Test editUser success") // test for editing a user successfully
    void testEditUserSuccess() throws Exception {
        // Mocking service
        when(userService.findById(1L)).thenReturn(users.get(0));
        when(userService.updateUser(ArgumentMatchers.any(UserForm.class), ArgumentMatchers.any(User.class)))
                .thenReturn(users.get(0));

        String inputJson = "{\n" +
        "    \"username\":\"newUsername\",\n" +
        "    \"password\":\"newPassword\"\n" +
        "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/editUser/1")
                .accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Successfully updated user details", response.getContentAsString());
    }

    @Test
    @DisplayName("Test editUser username taken") // test for trying to change a username to one that is already taken
    void testEditUserUsernameTaken() throws Exception {
        // Mocking service
        when(userService.findById(1L)).thenReturn(users.get(0));
        when(userService.updateUser(ArgumentMatchers.any(UserForm.class), ArgumentMatchers.any(User.class)))
                .thenReturn(null);

        String inputJson = "{\n" +
        "    \"username\":\"JohnDoe2\"\n" +
        "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/editUser/1")
                .accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
        assertEquals("Unable to save details, Username 'JohnDoe2' already taken", response.getContentAsString());
    }

    @Test
    @DisplayName("Test editUser not found") // test for editing a user that doesn't exist
    void testEditUserNotFound() throws Exception {
        // Mocking service
        when(userService.findById(3L)).thenReturn(null);

        String inputJson = "{\n" +
        "    \"username\":\"newUsername\",\n" +
        "    \"password\":\"newPassword\"\n" +
        "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/editUser/3")
                .accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
        assertEquals("User with ID 3 was not found", response.getContentAsString());
    }

    @Test
    @DisplayName("Test editPassword success") // test for changing a user's password successfully
    void testEditPasswordSuccess() throws Exception {
        // Mocking service
        when(userService.findById(1L)).thenReturn(users.get(0));
        when(userService.updateUserPassword(ArgumentMatchers.any(UserForm.class), ArgumentMatchers.any(User.class)))
                .thenReturn(users.get(0));

        String inputJson = "{\n" +
        "    \"password\":\"newPassword\"\n" +
        "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/editUser/password/1")
                .accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Successfully updated password", response.getContentAsString());
    }

    @Test
    @DisplayName("Test editPassword invalid password") // test for changing a user's password to an invalid password
    void testEditPasswordInvalid() throws Exception {
        // Mocking service
        when(userService.findById(1L)).thenReturn(users.get(0));
        when(userService.updateUserPassword(ArgumentMatchers.any(UserForm.class), ArgumentMatchers.any(User.class)))
                .thenReturn(null);

        String inputJson = "{\n" +
        "    \"password\":\"newPassword\"\n" +
        "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/editUser/password/1")
                .accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
        assertEquals("Unable to update invalid password", response.getContentAsString());
    }

    @Test
    @DisplayName("Test editPassword user not found") // test for changing a user's password when the user doesn't exist
    void testEditPasswordUserNotFound() throws Exception {
        // Mocking service
        when(userService.findById(3L)).thenReturn(null);

        String inputJson = "{\n" +
        "    \"password\":\"newPassword\"\n" +
        "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/editUser/3")
                .accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
        assertEquals("User with ID 3 was not found", response.getContentAsString());
    }

}