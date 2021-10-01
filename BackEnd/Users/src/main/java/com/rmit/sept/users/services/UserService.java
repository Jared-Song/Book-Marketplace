package com.rmit.sept.users.services;

import com.rmit.sept.users.Repositories.BusinessRepository;
import com.rmit.sept.users.Repositories.RequestRepository;
import com.rmit.sept.users.Repositories.UserRepository;
import com.rmit.sept.users.exceptions.AbnAlreadyExistsException;
import com.rmit.sept.users.exceptions.UserException;
import com.rmit.sept.users.exceptions.UsernameAlreadyExistsException;
import com.rmit.sept.users.model.Business;
import com.rmit.sept.users.model.Request;
import com.rmit.sept.users.model.RequestType;
import com.rmit.sept.users.model.Role;
import com.rmit.sept.users.model.User;
import com.rmit.sept.users.model.UserForm;
import com.rmit.sept.users.model.UserStatus;

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
    private RequestRepository requestRepository;

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
            newUser.setUserStatus(UserStatus.ENABLED);
            newUser.setRatingTotal(User.INITIAL_RATING);
            newUser.setRatingNo(User.INITIAL_NUM_RATINGS);
            newUser.setRole(Role.USER_NORMAL);
            // try to save user without business
            newUser.setBusiness(null);
            newUser.setRole(Role.USER_NORMAL);
            userRepository.save(newUser);

        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
        }
        try {
            // test if business can save
            newUser.setBusiness(business);
            if (business != null) {
                newUser.setRole(Role.USER_BUSINESS);
                newUser.setUserStatus(UserStatus.PENDING_REGISTRATION);
                Request newBusinessRequest = new Request();
                newBusinessRequest.setUser(newUser);
                newBusinessRequest.setRequest("New business user");
                newBusinessRequest.setRequestType(RequestType.NEW_BUSINESS_USER);
                requestRepository.save(newBusinessRequest);
            }
            return userRepository.save(newUser);
        } catch (Exception e) {
            System.out.println(e);
            userRepository.delete(userRepository.findById(newUser.getId()).orElse(null));
            throw new AbnAlreadyExistsException("ABN '" + business.getABN() + "' already exists");
        }
    }

    public User updateUser(UserForm userForm, User user) {
        boolean usernameExists = userRepository.usernameExists(userForm.getUsername());
        if (usernameExists && !user.getUsername().equals(userForm.getUsername())) { // username exists and is being used
                                                                                    // by someone else
            throw new UsernameAlreadyExistsException("Username '" + userForm.getUsername() + "' already exists");
        } else {
            // if user form is empty, fill the field with info from the user in the db,
            // otherwise use the form's info
            String email = (userForm.getEmail() == null) ? user.getEmail() : userForm.getEmail();
            String username = (userForm.getUsername() == null) ? user.getUsername() : userForm.getUsername();
            String fullName = (userForm.getFullName() == null) ? user.getFullName() : userForm.getFullName();

            // if user form is empty, fill the field with info from the user in the db,
            // otherwise use the form's info and encrypt it
            String address = (userForm.getAddress() == null) ? user.getAddress() : userForm.getAddress();

            Business business = user.getBusiness();
            if (userForm.getBusiness() != null && business != null) {
                Business newBusiness = userForm.getBusiness();
                int abn = (newBusiness.getABN() == 0) ? business.getABN() : newBusiness.getABN();
                String companyName = (newBusiness.getCompanyName() == null) ? business.getCompanyName()
                        : newBusiness.getCompanyName();
                business.setABN(abn);
                business.setCompanyName(companyName);
            }

            try {
                userRepository.updateUser(email, username, fullName, address, user.getId());
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

    public User updateUserPassword(UserForm userForm, User user) {
        // if user form is empty, fill the field with info from the user in the db,
        // otherwise use the form's info and encrypt it
        if (userForm.getPassword().isEmpty()) {
            return null;
        } else {
            String password = (userForm.getPassword() == null) ? user.getPassword()
                    : bCryptPasswordEncoder.encode(userForm.getPassword());
            try {
                userRepository.updateUserPassword(password, user.getId());
            } catch (Exception e) {
                throw new UserException("User with ID " + user.getId() + " was unable to be updated");
            }
            return user;
        }
    }

    public User setUserStatus(UserForm userForm, User user) {
        // if user form is empty, fill the field with info from the user in the db
        UserStatus status = (userForm.getUserStatus() == null) ? user.getUserStatus() : userForm.getUserStatus();
        try {
            userRepository.setUserStatus(status, user.getId());
        } catch (Exception e) {
            throw new UserException("User with ID " + user.getId() + " was unable to be updated");
        }
        return user;
    }

    public User setUserRole(UserForm userForm, User user) {
        // if user form is empty, fill the field with info from the user in the db
        Role role = (userForm.getRole() == null) ? user.getRole() : userForm.getRole();
        try {
            userRepository.setUserRole(role, user.getId());
        } catch (Exception e) {
            throw new UserException("User with ID " + user.getId() + " was unable to be updated");
        }
        return user;
    }

    // retrieve a user with a specific username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
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
            requestRepository.deleteByUserId(user.getId()); // delete any requests from the user
            userRepository.delete(user);
        } catch (IllegalArgumentException e) {
            throw new UserException("User with ID " + userId + " does not exist");
        }
    }

    // retrieve all users
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public long findRepositorySize() {
        return userRepository.count();
    }
}
