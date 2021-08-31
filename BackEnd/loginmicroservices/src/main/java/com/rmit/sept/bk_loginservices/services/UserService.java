package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.UserRepository;
import com.rmit.sept.bk_loginservices.exceptions.UserException;
import com.rmit.sept.bk_loginservices.exceptions.UsernameAlreadyExistsException;
import com.rmit.sept.bk_loginservices.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser) {

        /*
         * newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
         * //Username has to be unique (exception) // Make sure that password and
         * confirmPassword match // We don't persist or show the confirmPassword return
         * userRepository.save(newUser);
         */
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            // Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);

        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
        }

    }

    @GetMapping("/user")
    public ResponseEntity getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable("id") long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null){
            return new ResponseEntity<>(userRepository.findById(id), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/user")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/published")
    public ResponseEntity<User> getByUsername(@RequestParam(required = false) String username) {
        return new ResponseEntity<>(userRepository.findByUsername(username), HttpStatus.OK);
    }

    public User findByUsername(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UserException("User with username '" + username + "'does not exist");
        }

        return user;
    }

    public User findById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new UserException("User with ID " + userId + " does not exist");
        }

        return user;
    }

    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserException("User with ID " + userId + " does not exist");
        }

        userRepository.delete(user);
    }

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UserException("Cannot delete user with username '" + username + "'. This user does not exist");
        }

        userRepository.delete(user);
    }



}
