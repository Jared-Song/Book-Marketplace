package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.UserRepository;
import com.rmit.sept.bk_loginservices.exceptions.UserException;
import com.rmit.sept.bk_loginservices.exceptions.UsernameAlreadyExistsException;
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
            return userRepository.save(newUser);

        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
        }

    }

    public User updateUser(UserForm userForm, User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        String username = userForm.getUsername();
        if (username == null) {
            username = user.getUsername();
        }

        String password = userForm.getPassword();
        if (password == null) {
            password = user.getPassword();
        } else {
            password = bCryptPasswordEncoder.encode(password);
        }

        String email = userForm.getEmail();
        if (email == null) {
            email = user.getAddress();
        }

        String fullName = userForm.getFullName();
        if (fullName == null) {
            fullName = user.getAddress();
        }

        String address = userForm.getAddress();
        if (address == null) {
            address = user.getAddress();
        }
        try {
            userRepository.updateUser(username, password, email, fullName, address, user.getId());
        } catch (Exception e) {
        }
        User updateUser = userRepository.findById(user.getId()).orElse(null);

        if (existingUser.getUsername().equals(userForm.getUsername())) {
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
