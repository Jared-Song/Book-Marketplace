package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.UserRepository;
import com.rmit.sept.bk_loginservices.exceptions.UserException;
import com.rmit.sept.bk_loginservices.exceptions.UsernameAlreadyExistsException;
import com.rmit.sept.bk_loginservices.model.Role;
import com.rmit.sept.bk_loginservices.model.Status;
import com.rmit.sept.bk_loginservices.model.User;
import com.rmit.sept.bk_loginservices.model.UserForm;
import com.rmit.sept.bk_loginservices.model.Business;
import com.rmit.sept.bk_loginservices.Repositories.BusinessRepository;
import com.rmit.sept.bk_loginservices.exceptions.AbnAlreadyExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser) {

        Business business = newUser.getBusiness();
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
            newUser.setRating(User.INITIAL_RATING);
            newUser.setRatingNo(User.INITIAL_NUM_RATINGS);
            //try to save user without business
            newUser.setBusiness(null);
            userRepository.save(newUser); 

        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
        }
        try {
            //test if business can save
            newUser.setBusiness(business);
            return userRepository.save(newUser); 
        } catch (Exception e){
            userRepository.delete(userRepository.findById(newUser.getId()).orElse(null));
            throw new AbnAlreadyExistsException("ABN '" + business.getABN() + "' already exists");
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
            Status status = (userForm.getStatus() == null) ? user.getStatus() : userForm.getStatus();

            double rating = (userForm.getRating() == 0) ? user.getRating() : userForm.getRating();
            int ratingNo = (userForm.getRatingNo() == 0) ? user.getRatingNo() : userForm.getRatingNo();

            Business business = user.getBusiness();
            if(userForm.getBusiness() != null && business != null){
                Business newBusiness = userForm.getBusiness();
                int abn = (newBusiness.getABN() == 0) ? business.getABN() : newBusiness.getABN();
                String companyName = (newBusiness.getCompanyName() == null) ? business.getCompanyName() : newBusiness.getCompanyName();
                business.setABN(abn);
                business.setCompanyName(companyName);
            } 

            try {
                userRepository.updateUser(email, username, fullName, password, address, role, status, rating, ratingNo, 
                        user.getId());
                
            } catch (Exception e) {
                throw new UserException("User with ID " + user.getId() + " was unable to be updated");
            }
            if (business != null) {
                try {
                    businessRepository.save(business);
                } catch (Exception e) {
                    throw new AbnAlreadyExistsException("ABN '" + business.getABN() + "' already exists");
                }
            }
            return user;
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
