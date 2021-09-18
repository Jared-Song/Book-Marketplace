package com.rmit.sept.bk_loginservices.Repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.rmit.sept.bk_loginservices.Repositories.UserRepository;
import com.rmit.sept.bk_loginservices.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // testing for empty user repository
    @Test
    @Rollback(true)
    public void should_find_no_users_if_userRepository_is_empty() {
        Iterable<User> users = userRepository.findAll();
        assertThat(users).isEmpty();
    }

    // test to find a user by username
    @Test
    @Rollback(true)
    public void find_user_by_username() {
        User user = new User();
        user.setEmail("johndoe@gmail.com");
        user.setUsername("JohnDoe");
        user.setFullName("John Doe");
        user.setPassword("password");
        user.setAddress("1 John Street, Doeland");
        userRepository.save(user);

        User findUser = userRepository.findByUsername("JohnDoe");
        assertNotNull(findUser);
        assertThat(findUser).hasFieldOrPropertyWithValue("email", "johndoe@gmail.com");
        assertThat(findUser).hasFieldOrPropertyWithValue("username", "JohnDoe");
        assertThat(findUser).hasFieldOrPropertyWithValue("fullName", "John Doe");
        assertThat(findUser).hasFieldOrPropertyWithValue("password", "password");
        assertThat(findUser).hasFieldOrPropertyWithValue("address", "1 John Street, Doeland");
    }

    // test to delete a user
    @Test
    @Rollback(true)
    public void delete_user() {
        User user = new User();
        user.setEmail("johndoe@gmail.com");
        user.setUsername("JohnDoe");
        user.setFullName("John Doe");
        user.setPassword("password");
        user.setAddress("1 John Street, Doeland");

        User savedUser = userRepository.save(user);
        userRepository.delete(savedUser);
        Iterable<User> users = userRepository.findAll();
        assertThat(users).isEmpty();
    }

    // test to save a new user
    @Test
    @Rollback(true)
    public void save_new_user() {
        User user = new User();
        user.setEmail("johndoe@gmail.com");
        user.setUsername("JohnDoe");
        user.setFullName("John Doe");
        user.setPassword("password");
        user.setAddress("1 John Street, Doeland");

        User savedUser = userRepository.save(user);

        assertThat(savedUser).hasFieldOrPropertyWithValue("email", "johndoe@gmail.com");
        assertThat(savedUser).hasFieldOrPropertyWithValue("username", "JohnDoe");
        assertThat(savedUser).hasFieldOrPropertyWithValue("fullName", "John Doe");
        assertThat(savedUser).hasFieldOrPropertyWithValue("password", "password");
        assertThat(savedUser).hasFieldOrPropertyWithValue("address", "1 John Street, Doeland");
    }

    // test to find all users after saving a new user
    @Test
    @Rollback(true)
    public void find_all_returns_saved_user() {
        User user = new User();
        user.setEmail("johndoe@gmail.com");
        user.setUsername("JohnDoe");
        user.setFullName("John Doe");
        user.setPassword("password");
        user.setAddress("1 John Street, Doeland");
        userRepository.save(user);

        Iterable<User> users = userRepository.findAll();

        User savedUser = users.iterator().next();
        assertThat(savedUser.getEmail().equals("johndoe@gmail.com"));
        assertThat(savedUser.getUsername().equals("JohnDoe"));
        assertThat(savedUser.getFullName().equals("John Doe"));
        assertThat(savedUser.getAddress().equals("1 John Street, Doeland"));
    }
}
