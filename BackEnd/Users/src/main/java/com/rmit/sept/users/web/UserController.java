package com.rmit.sept.users.web;

import javax.validation.Valid;

import com.rmit.sept.users.UsersApplication;
import com.rmit.sept.users.model.User;
import com.rmit.sept.users.services.MapValidationErrorService;
import com.rmit.sept.users.services.UserService;
import com.rmit.sept.users.validator.UserValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/users")
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UsersApplication.class);
    
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    // register a new user account
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        LOGGER.trace("Registering a new user");
        // Validate passwords match
        userValidator.validate(user, result);
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            LOGGER.warn("The user's credentials are invalid and the user cannot be registered");
            return errorMap;
        }

        User newUser = userService.saveUser(user);
        LOGGER.trace("The new user has been successfully registered and saved");
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    // retrieve all users
    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        LOGGER.trace("Finding all users");
        return userService.findAllUsers();
    }

    // retrieve a user with a specific id
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        LOGGER.trace("Finding user with ID " + userId);
        User user = userService.findById(userId);
        if (user != null) {
            LOGGER.trace("Found user with ID  " + userId);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            LOGGER.warn("User with ID " + userId + " was not found");
            return new ResponseEntity<String>("User with ID " + userId + " was not found", HttpStatus.NOT_FOUND);
        }

    }

    // delete a user with a specific id
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        LOGGER.trace("Finding user with ID " + userId);
        User user = userService.findById(userId);

        if (user != null) {
            userService.deleteUserById(userId);
            LOGGER.trace("Deleted user with ID " + userId);
            return new ResponseEntity<String>("User with ID " + userId + " was deleted", HttpStatus.OK);
        } else {
            LOGGER.warn("User with ID " + userId + " was not found");
            return new ResponseEntity<String>("User with ID " + userId + " was not found", HttpStatus.NOT_FOUND);
        }

    }

}
