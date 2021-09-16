// package com.rmit.sept.bk_loginservices.web;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.assertj.core.api.Assertions.assertThat;

// import com.rmit.sept.bk_loginservices.Repositories.UserRepository;
// import com.rmit.sept.bk_loginservices.model.User;

// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.context.junit4.SpringRunner;

// @RunWith(SpringRunner.class)
// @DataJpaTest
// public class UserControllerTest {

//     @Autowired
//     private UserRepository userRepository;

//     // testing for empty user repository 
//     @Test
//     public void should_find_no_users_if_userRepository_is_empty() {
//         Iterable<User> users = userRepository.findAll();
//         assertThat(users).isEmpty();
//     }

//     @Test
//     public void should_store_a_user() {
//         User user = new User();
//         user.setEmail("johndoe@gmail.com");
//         user.setUsername("JohnDoe");
//         user.setFullName("John Doe");
//         user.setPassword("password");
//         user.setAddress("1 John Street, Doeland");

//         User savedUser = userRepository.save(user);

//         assertThat(savedUser).hasFieldOrPropertyWithValue("email", "johndoe@gmail.com");
//         assertThat(savedUser).hasFieldOrPropertyWithValue("username", "JohnDoe");
//         assertThat(savedUser).hasFieldOrPropertyWithValue("fullName", "John Doe");
//         assertThat(savedUser).hasFieldOrPropertyWithValue("password", "password");
//         assertThat(savedUser).hasFieldOrPropertyWithValue("address", "1 John Street, Doeland");
//     }

//     // @Test
//     // public void testLogin() {
//     // LoginRequest loginRequest = new LoginRequest();
//     // loginRequest.setUsername("TestUsername");
//     // loginRequest.setPassword("passwordA");

//     // ResponseEntity<?> wrongPassword =
//     // userController.authenticateUser(loginRequest, null);
//     // }
// }
