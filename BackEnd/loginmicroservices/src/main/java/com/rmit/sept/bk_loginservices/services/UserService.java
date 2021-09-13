package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.UserRepository;
import com.rmit.sept.bk_loginservices.exceptions.UserException;
import com.rmit.sept.bk_loginservices.exceptions.UsernameAlreadyExistsException;
import com.rmit.sept.bk_loginservices.model.Role;
import com.rmit.sept.bk_loginservices.model.Status;
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
            newUser.setStatus(Status.ENABLED);
            newUser.setRole(Role.USER_NORMAL);
            return userRepository.save(newUser);

        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
        }

    }

    public User updateUser(UserForm userForm, User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        boolean usernameExists = userRepository.usernameExists(userForm.getUsername());

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
        Status status = (userForm.getStatus() == null) ? user.getStatus() : userForm.getStatus();

        double rating = (userForm.getRating() == 0) ? user.getRating() : userForm.getRating();
        int ratingNo = (userForm.getRatingNo() == 0) ? user.getRatingNo() : userForm.getRatingNo();

        try {
            userRepository.updateUser(email, username, fullName, password, address, role, status, rating, ratingNo,
                    user.getId());
        } catch (Exception e) {
        }
        User updateUser = userRepository.findById(user.getId()).orElse(null);

        if (existingUser.getUsername().equals(username)) {
            return updateUser;
        } else if (!usernameExists) {
            return updateUser;
        } else {
            return null;
        }
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
