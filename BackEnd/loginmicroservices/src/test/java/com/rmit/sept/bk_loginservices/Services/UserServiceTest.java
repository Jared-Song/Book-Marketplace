// package com.rmit.sept.bk_loginservices.Services;

// import static org.mockito.BDDMockito.given;

// import java.util.ArrayList;
// import java.util.List;

// import com.rmit.sept.bk_loginservices.Repositories.UserRepository;
// import com.rmit.sept.bk_loginservices.model.User;
// import com.rmit.sept.bk_loginservices.services.UserService;

// import org.junit.Assert;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.context.junit4.SpringRunner;

// @SpringBootTest
// @ActiveProfiles("test")
// @RunWith(SpringRunner.class)
// public class UserServiceTest {
//     @Autowired
//     private UserService userService;

//     @MockBean
//     private UserRepository userRepository;

//     private List<User> users;
//     private User user1, user2;

//     @BeforeEach
//     public void setup() {
//         user1 = new User();
//         user1.setId(1L);
//         user1.setEmail("johndoe@gmail.com");
//         user1.setUsername("JohnDoe");
//         user1.setFullName("John Doe");
//         user1.setPassword("password");
//         user1.setAddress("1 John Street, Doeland");

//         user2 = new User();
//         user2.setId(2L);
//         user2.setEmail("johndoe2@gmail.com");
//         user2.setUsername("JohnDoe2");
//         user2.setFullName("John Doe");
//         user2.setPassword("password");
//         user2.setAddress("2 John Street, Doeland");

//         users = new ArrayList<>();
//         users.add(user1);
//         users.add(user2);
//     }

//     @Test
//     @DisplayName("Test findByUsername success") // test for finding a user by username successfully
//     public void testFindByUsernameSuccess() throws Exception {
//         String username = user1.getUsername();
//         given(userRepository.findByUsername(username)).willReturn(user1);
//         User user = userService.findByUsername(username);
//         Assert.assertEquals(user.getEmail(), "johndoe@gmail.com");
//         Assert.assertEquals(user.getUsername(), "JohnDoe");
//         Assert.assertEquals(user.getFullName(), "John Doe");
//         Assert.assertEquals(user.getPassword(), "password");
//         Assert.assertEquals(user.getAddress(), "1 John Street, Doeland");
//     }

//     @Test
//     @DisplayName("Test findByUsername fail") // test for finding a user by username which doesn't exist
//     public void testFindByUsernameFail() throws Exception {
//         String username = "null";
//         given(userRepository.findByUsername(username)).willReturn(null);
//         User user = userService.findByUsername(username);
//         Assert.assertNull(user);
//     }
// }