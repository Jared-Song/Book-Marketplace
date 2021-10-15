package com.rmit.sept.users.web;

import com.rmit.sept.users.UsersApplication;
import com.rmit.sept.users.model.User;
import com.rmit.sept.users.model.UserForm;
import com.rmit.sept.users.services.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/editUser")
public class EditUserController {
    private static final Logger LOGGER = LogManager.getLogger(UsersApplication.class);

    @Autowired
    private UserService userService;

    @PostMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<?> updateUser(@RequestBody UserForm userForm, @PathVariable Long userId) {
        LOGGER.info("Finding user with ID " + userId);
        // first try to find the user that is to be updated in the database
        User user = userService.findById(userId);
        if (user != null) { // if the user exists
            LOGGER.info("Updating details for user with ID " + userId);
            User updateUser = userService.updateUser(userForm, user); // update the user
            if (updateUser != null) { // if the returned user isn't null
                LOGGER.info("Successfully updated details for user with ID " + userId);
                return new ResponseEntity<String>("Successfully updated user details", HttpStatus.OK);
            } else { // if the returned user is null, an error has occurred
                LOGGER.warn("Unable to save details for User with ID " + userId);
                return new ResponseEntity<String>(
                        "Unable to save details, Username '" + userForm.getUsername() + "' already taken",
                        HttpStatus.CONFLICT);
            }
        } else { // if the user doesn't exist
            LOGGER.warn("User with ID " + userId + " was not found");
            return new ResponseEntity<String>("User with ID " + userId + " was not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/password/{userId}")
    @ResponseBody
    public ResponseEntity<?> updateUserPassword(@RequestBody UserForm userForm, @PathVariable Long userId) {
        LOGGER.info("Finding user with ID " + userId);
        // first try to find the user that is to be updated in the database
        User user = userService.findById(userId);
        if (user != null) { // if the user exists
            LOGGER.info("Updating password for user with ID " + userId);
            User updateUser = userService.updateUserPassword(userForm, user); // update the user's password and returns
                                                                              // the user
            if (updateUser != null) { // if the returned user isn't null
                LOGGER.info("Successfully updated password for user with ID " + userId);
                return new ResponseEntity<String>("Successfully updated password", HttpStatus.OK);
            } else { // if the returned user is null, an error has occurred
                LOGGER.warn("Unable to update password for user with ID " + userId);
                return new ResponseEntity<String>("Unable to update invalid password", HttpStatus.NOT_ACCEPTABLE);
            }
        } else { // if the user doesn't exist
            LOGGER.warn("User with ID " + userId + " was not found");
            return new ResponseEntity<String>("User with ID " + userId + " was not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/userStatus/{userId}")
    @ResponseBody
    public ResponseEntity<?> setUserStatus(@RequestBody UserForm userForm, @PathVariable Long userId) {
        LOGGER.info("Finding user with ID " + userId);
        // first try to find the user that is to be updated in the database
        User user = userService.findById(userId);
        if (user != null) { // if the user exists
            LOGGER.info("Updating user status for user with ID " + userId);
            User updateUser = userService.setUserStatus(userForm, user); // update the user's status and returns
                                                                         // the user
            if (updateUser != null) { // if the returned user isn't null
                LOGGER.info("Successfully updated user status for user with ID " + userId);
                return new ResponseEntity<String>("Successfully updated user status", HttpStatus.OK);
            } else { // if the returned user is null, an error has occurred
                LOGGER.warn("Unable to update user status for user with ID " + userId);
                return new ResponseEntity<String>("Unable to update invalid user status", HttpStatus.NOT_ACCEPTABLE);
            }
        } else { // if the user doesn't exist
            LOGGER.warn("User with ID " + userId + " was not found");
            return new ResponseEntity<String>("User with ID " + userId + " was not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/role/{userId}")
    @ResponseBody
    public ResponseEntity<?> setRole(@RequestBody UserForm userForm, @PathVariable Long userId) {
        LOGGER.info("Finding user with ID " + userId);
        // first try to find the user that is to be updated in the database
        User user = userService.findById(userId);
        if (user != null) { // if the user exists
            LOGGER.info("Updating user role for user with ID " + userId);
            User updateUser = userService.setUserRole(userForm, user); // update the user's status and returns
                                                                       // the user
            if (updateUser != null) { // if the returned user isn't null
            LOGGER.info("Successfully updated user role for user with ID " + userId);
                return new ResponseEntity<String>("Successfully updated user role", HttpStatus.OK);
            } else { // if the returned user is null, an error has occurred
                LOGGER.warn("Unable to update user role for user with ID " + userId);
                return new ResponseEntity<String>("Unable to update invalid user role", HttpStatus.NOT_ACCEPTABLE);
            }
        } else { // if the user doesn't exist
            LOGGER.warn("User with ID " + userId + " was not found");
            return new ResponseEntity<String>("User with ID " + userId + " was not found", HttpStatus.NOT_FOUND);
        }
    }

}