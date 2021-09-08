package com.rmit.sept.bk_loginservices.web;

import com.rmit.sept.bk_loginservices.Repositories.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{Id}")
    @ResponseBody
    public ResponseEntity<?> updateUser(@RequestBody UserForm userForm, @PathVariable Long Id) {
        User user = userRepository.findById(Id).orElse(null);
        if (user != null) {
            User updateUser = userService.updateUser(userForm, user);
            if (updateUser != null) {
                return new ResponseEntity<String>("Successfully updated user details", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Unable to save details, Username '" + userForm.getUsername() + "' already taken", HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<String>("User with ID " + Id + " was not found", HttpStatus.NOT_FOUND);
        }
    }

}