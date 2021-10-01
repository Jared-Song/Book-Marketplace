package com.rmit.sept.books.services;
import com.rmit.sept.books.Repositories.UserRepository;
import com.rmit.sept.books.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // retrieve a user with a specific username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // retrieve a user with a specific id
    public User findById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user;
    }

}
