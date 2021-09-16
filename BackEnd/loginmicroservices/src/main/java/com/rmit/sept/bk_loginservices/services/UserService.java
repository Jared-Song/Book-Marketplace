package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.UserRepository;
import com.rmit.sept.bk_loginservices.exceptions.UserException;
import com.rmit.sept.bk_loginservices.exceptions.UsernameAlreadyExistsException;
import com.rmit.sept.bk_loginservices.model.Role;
import com.rmit.sept.bk_loginservices.model.UserStatus;
import com.rmit.sept.bk_loginservices.model.User;
import com.rmit.sept.bk_loginservices.model.UserForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
            newUser.setUserStatus(UserStatus.ENABLED);
            newUser.setRole(Role.USER_NORMAL);
            newUser.setRating(User.INITIAL_RATING);
            newUser.setRatingNo(User.INITIAL_NUM_RATINGS);
            return userRepository.save(newUser);

        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
        }

    }

    public User updateUser(UserForm userForm, User user) {
        boolean usernameExists = userRepository.usernameExists(userForm.getUsername());
        if (usernameExists && !user.getUsername().equals(userForm.getUsername())) { // username exists and is being used
                                                                                    // by someone else
            return null;
        } else {
            // if user form is empty, fill the field with info from the user in the db,
            // otherwise use the form's info
            String email = (userForm.getEmail() == null) ? user.getEmail() : userForm.getEmail();
            String username = (userForm.getUsername() == null) ? user.getUsername() : userForm.getUsername();
            String fullName = (userForm.getFullName() == null) ? user.getFullName() : userForm.getFullName();

            // if user form is empty, fill the field with info from the user in the db,
            // otherwise use the form's info and encrypt it
            String password = (userForm.getPassword() == null) ? user.getPassword()
                    : bCryptPasswordEncoder.encode(userForm.getPassword());

            String address = (userForm.getAddress() == null) ? user.getAddress() : userForm.getAddress();

            Role role = (userForm.getRole() == null) ? user.getRole() : userForm.getRole();
            UserStatus userSatus = (userForm.getUserStatus() == null) ? user.getUserStatus() : userForm.getUserStatus();

            double rating = (userForm.getRating() == 0) ? user.getRating() : userForm.getRating();
            int ratingNo = (userForm.getRatingNo() == 0) ? user.getRatingNo() : userForm.getRatingNo();

            try {
                userRepository.updateUser(email, username, fullName, password, address, role, userSatus, rating,
                        ratingNo, user.getId());
            } catch (Exception e) {
                throw new UserException("User with ID " + user.getId() + " was unable to be updated");
            }
            return user;
        }

    }

    // retrieve a user with a specific username
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

    // retrieve a user with a specific id
    public User findById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user;
    }

    // delete a user with a specific id
    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        try {
            userRepository.delete(user);
        } catch (IllegalArgumentException e) {
            throw new UserException("User with ID " + userId + " does not exist");
        }
    }

    // retrieve all users
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

}
