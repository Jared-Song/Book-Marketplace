package com.rmit.sept.users.web;

import javax.validation.Valid;

import com.rmit.sept.users.model.User;
import com.rmit.sept.users.services.MapValidationErrorService;
import com.rmit.sept.users.services.UserService;
import com.rmit.sept.users.validator.UserValidator;

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

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    // register a new user account
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        // Validate passwords match
        userValidator.validate(user, result);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null)
            return errorMap;

        User newUser = userService.saveUser(user);

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    // retrieve all users
    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userService.findAllUsers();
    }

    // retrieve a user with a specific id
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        User user = userService.findById(userId);
        if (user != null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("User with ID " + userId + " was not found", HttpStatus.NOT_FOUND);
        }

    }

    // delete a user with a specific id
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        User user = userService.findById(userId);

        if (user != null) {
            userService.deleteUserById(userId);
            return new ResponseEntity<String>("User with ID " + userId + " was deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("User with ID " + userId + " was not found", HttpStatus.NOT_FOUND);
        }

    }

}
