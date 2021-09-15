package com.rmit.sept.bk_loginservices.services;

import org.springframework.stereotype.Service;

import com.rmit.sept.bk_loginservices.Repositories.BusinessRepository;
import com.rmit.sept.bk_loginservices.Repositories.UserRepository;
import com.rmit.sept.bk_loginservices.exceptions.BusinessException;
import com.rmit.sept.bk_loginservices.model.Business;
import com.rmit.sept.bk_loginservices.model.User;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private UserRepository userRepository;

    public Business getById(Long Id) {
        Business business = businessRepository.findById(Id).orElse(null);
        return business;
    }

    
    public Business saveBusiness(Business business) {
        User user = userRepository.getById(business.getId());
        
        user.setBusiness(business);
        business.setUser(user);
        System.out.println(user.getBusiness());
        System.out.println(business.getUser());
        userRepository.save(user);
        return businessRepository.save(business);
    }

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
