package com.rmit.sept.users.services;

import com.rmit.sept.users.Repositories.BusinessRepository;
import com.rmit.sept.users.Repositories.UserRepository;
import com.rmit.sept.users.exceptions.AbnAlreadyExistsException;
import com.rmit.sept.users.model.Business;
import com.rmit.sept.users.model.Role;
import com.rmit.sept.users.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private UserRepository userRepository;

    // returns business by id
    public Business getById(Long Id) {
        User user = userRepository.getById(Id);
        Business business = user.getBusiness();
        return business;
    }
    
    public Business saveBusiness(Business business) {
       
        //checks to make sure the user id is valid
        User user = userRepository.getById(business.getUser().getId());
        if (user != null){
            //Check to make sure abn is unique
            business.setABN(business.getABN());
            user.setRole(Role.USER_BUSINESS);
            user.setBusiness(business);
            business.setUser(user);
            try {
                userRepository.save(user);
                return businessRepository.save(business);
            } catch (Exception e){
                throw new AbnAlreadyExistsException("ABN '" + business.getABN() + "' already exists");
            }
        } else {
            return null;
        }
        
    }

    //delete business by the id
    public void deleteBusinessById(Long Id) {
        Business business = businessRepository.findById(Id).orElse(null);
        User user = userRepository.getById(Id);
        user.setBusiness(null);
        businessRepository.delete(business);
    }
    
    public Iterable<Business> findAllBusinesses() {
        return businessRepository.findAll();
    }
}
