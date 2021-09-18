package com.rmit.sept.bk_loginservices.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.rmit.sept.bk_loginservices.Repositories.UserRepository;

import com.rmit.sept.bk_loginservices.model.User;
import com.rmit.sept.bk_loginservices.services.UserService;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @BeforeAll
    public void setUp() {
        User user = new User();
        user.setEmail("johndoe@gmail.com");
        user.setUsername("JohnDoe");
        user.setConfirmPassword("");
        user.setFullName("John Doe");
        user.setPassword("password");
        user.setAddress("1 John Street, Doeland");

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
    }

    // testing for empty user repository
    @Test
    @Rollback(true)
    public void should_find_no_users_if_userRepository_is_empty() {
        Iterable<User> users = userService.findAllUsers();
        assertThat(users).isEmpty();
    }

    // test to find a user by username
    @Test
    @Rollback(true)
    public void find_user_by_username() {
        // String username = "JohnDoe";
        // User found = userService.findByUsername(username);

        // assertThat(found.getUsername()).isEqualTo(username);

        // Iterable<User> users = userService.findAllUsers();
        // assertThat(users).isEmpty();

        // User findUser = userService.findByUsername("JohnDoe");
        // assertNotNull(findUser);

        // User findUser = userService.findByUsername(user.getUsername());
        // assertNull(findUser);

        // assertThat(findUser).hasFieldOrPropertyWithValue("email",
        // "johndoe@gmail.com");
        // assertThat(findUser).hasFieldOrPropertyWithValue("username", "JohnDoe");
        // assertThat(findUser).hasFieldOrPropertyWithValue("fullName", "John Doe");
        // assertThat(findUser).hasFieldOrPropertyWithValue("password", "password");
        // assertThat(findUser).hasFieldOrPropertyWithValue("address", "1 John Street,
        // Doeland");
    }

}
