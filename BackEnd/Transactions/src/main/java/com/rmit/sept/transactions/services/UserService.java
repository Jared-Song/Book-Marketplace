package com.rmit.sept.transactions.services;

import com.rmit.sept.transactions.Repositories.UserRepository;
import com.rmit.sept.transactions.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // retrieve a user with a specific id
    public User findById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user;
    }
}
