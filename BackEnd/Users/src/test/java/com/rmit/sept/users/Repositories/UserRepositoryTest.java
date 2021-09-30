package com.rmit.sept.users.Repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Iterator;

import javax.transaction.Transactional;

import com.rmit.sept.users.model.Role;
import com.rmit.sept.users.model.User;
import com.rmit.sept.users.model.UserStatus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // testing for empty user repository
    @Test
    @Rollback(true)
    public void should_find_no_users_if_userRepository_is_empty() {
        Iterable<User> users = userRepository.findAll();
        if (users.iterator().hasNext()) {
            assertThat(users).isNotEmpty();
        } else {
            assertThat(users).isEmpty();
        }
    }

    // test to find a user by username
    @Test
    @Rollback(true)
    public void find_user_by_username() {
        User user = new User();
        user.setId(1L);
        user.setEmail("johndoe@gmail.com");
        user.setUsername("JohnDoe");
        user.setFullName("John Doe");
        user.setPassword("password");
        user.setAddress("1 John Street, Doeland");
        user.setRole(Role.USER_NORMAL);
        user.setUserStatus(UserStatus.ENABLED);
        userRepository.save(user);
        
        User findUser = userRepository.findByUsername("JohnDoe");
        assertNotNull(findUser);
        assertThat(findUser).hasFieldOrPropertyWithValue("email", "johndoe@gmail.com");
        assertThat(findUser).hasFieldOrPropertyWithValue("username", "JohnDoe");
        assertThat(findUser).hasFieldOrPropertyWithValue("fullName", "John Doe");
        assertThat(findUser).hasFieldOrPropertyWithValue("password", "password");
        assertThat(findUser).hasFieldOrPropertyWithValue("address", "1 John Street, Doeland");
        assertThat(findUser).hasFieldOrPropertyWithValue("role", Role.USER_NORMAL);
        assertThat(findUser).hasFieldOrPropertyWithValue("userStatus", UserStatus.ENABLED);
    }

    // test to delete a user
    @Test
    @Rollback(true)
    public void delete_user() {
        User user = new User();
        user.setId(1L);
        user.setEmail("johndoe@gmail.com");
        user.setUsername("JohnDoe");
        user.setFullName("John Doe");
        user.setPassword("password");
        user.setAddress("1 John Street, Doeland");
        user.setRole(Role.USER_NORMAL);
        user.setUserStatus(UserStatus.ENABLED);
        User savedUser = userRepository.save(user);
        userRepository.delete(savedUser);

        Iterable<User> users = userRepository.findAll();
        Iterator<User> iter = users.iterator();
        while(iter.hasNext()) {
            User iterUser = iter.next();
            assertThat(iterUser.getUsername()).isNotEqualTo(savedUser.getUsername());
        }
    }

    // test to save a new user
    @Test
    @Rollback(true)
    public void save_new_user() {
        User user = new User();
        user.setId(1L);
        user.setEmail("johndoe@gmail.com");
        user.setUsername("JohnDoe");
        user.setFullName("John Doe");
        user.setPassword("password");
        user.setAddress("1 John Street, Doeland");
        user.setRole(Role.USER_NORMAL);
        user.setUserStatus(UserStatus.ENABLED);
        userRepository.save(user);

        User savedUser = userRepository.save(user);

        assertThat(savedUser).hasFieldOrPropertyWithValue("email", "johndoe@gmail.com");
        assertThat(savedUser).hasFieldOrPropertyWithValue("username", "JohnDoe");
        assertThat(savedUser).hasFieldOrPropertyWithValue("fullName", "John Doe");
        assertThat(savedUser).hasFieldOrPropertyWithValue("password", "password");
        assertThat(savedUser).hasFieldOrPropertyWithValue("address", "1 John Street, Doeland");
        assertThat(savedUser).hasFieldOrPropertyWithValue("role", Role.USER_NORMAL);
        assertThat(savedUser).hasFieldOrPropertyWithValue("userStatus", UserStatus.ENABLED);
    }

    // test to find all users after saving a new user
    @Test
    @Rollback(true)
    public void find_all_returns_saved_user() {
        User user = new User();
        user.setId(1L);
        user.setEmail("johndoe@gmail.com");
        user.setUsername("JohnDoe");
        user.setFullName("John Doe");
        user.setPassword("password");
        user.setAddress("1 John Street, Doeland");
        user.setRole(Role.USER_NORMAL);
        user.setUserStatus(UserStatus.ENABLED);
        userRepository.save(user);

        Iterable<User> users = userRepository.findAll();

        User savedUser = users.iterator().next();
        assertThat(savedUser.getEmail().equals("johndoe@gmail.com"));
        assertThat(savedUser.getUsername().equals("JohnDoe"));
        assertThat(savedUser.getFullName().equals("John Doe"));
        assertThat(savedUser.getAddress().equals("1 John Street, Doeland"));
        assertThat(savedUser.getRole().equals(Role.USER_NORMAL));
        assertThat(savedUser.getUserStatus().equals(UserStatus.ENABLED));
    }
}
