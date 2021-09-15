package com.rmit.sept.bk_loginservices.web;

import com.rmit.sept.bk_loginservices.model.User;
import com.rmit.sept.bk_loginservices.model.UserForm;
import com.rmit.sept.bk_loginservices.services.UserService;

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

    @Autowired
    private UserService userService;

    @PostMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<?> updateUser(@RequestBody UserForm userForm, @PathVariable Long userId) {
        // first try to find the user that is to be updated in the database
        User user = userService.findById(userId);
        if (user != null) { // if the user exists
            User updateUser = userService.updateUser(userForm, user); // update the user
            if (updateUser != null) { // if the returned user isn't null
                return new ResponseEntity<String>("Successfully updated user details", HttpStatus.OK);
            } else { // if the returned user is null, an error has occurred
                return new ResponseEntity<String>(
                        "Unable to save details, Username '" + userForm.getUsername() + "' already taken",
                        HttpStatus.ACCEPTED);
            }
        } else { // if the user doesn't exist
            return new ResponseEntity<String>("User with ID " + userId + " was not found", HttpStatus.ACCEPTED);
        }
    }

}