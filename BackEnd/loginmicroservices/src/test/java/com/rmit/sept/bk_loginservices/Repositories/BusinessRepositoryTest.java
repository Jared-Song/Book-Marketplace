package com.rmit.sept.bk_loginservices.Repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rmit.sept.bk_loginservices.model.Business;
import com.rmit.sept.bk_loginservices.model.User;

import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BusinessRepositoryTest {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private UserRepository userRepository;

    User USER_1 = new User("johndoe@gmail.com", "JohnDoe", "John Doe", "password", "1 John Street, Doeland");
    Business BUSINESS_1 = new Business(1l, 123, "testCompany", USER_1);
    User USER_2 = new User("johndoe@gmail.com", "JohnDoe2", "John Doe", "password", "1 John Street, Doeland");
    Business BUSINESS_2 = new Business(2l, 1234, "testCompany", USER_2);
    User USER_3 = new User("johndoe@gmail.com", "JohnDoe3", "John Doe", "password", "1 John Street, Doeland");
    Business BUSINESS_3 = new Business(3l, 321, "testCompany", USER_3);

    @Before
    public void saveUser(){
        USER_1.setBusiness(BUSINESS_1);
        userRepository.save(USER_1);
        USER_2.setBusiness(BUSINESS_2);
        userRepository.save(USER_2);
        USER_3.setBusiness(BUSINESS_3);
        userRepository.save(USER_3);
    }

    @Test
    public void findNoBusinesses_success() {
        List<Business> businesses = new ArrayList<Business>();
        assertEquals(businesses, businessRepository.findAll());
    }

    @Test
    public void saveBusiness_success() {
        Business savedBusiness = businessRepository.save(BUSINESS_1);
        assertThat(savedBusiness).hasFieldOrPropertyWithValue("id", 1l);
        assertThat(savedBusiness).hasFieldOrPropertyWithValue("ABN", 123);
        assertThat(savedBusiness).hasFieldOrPropertyWithValue("companyName", "testCompany");
    }

    @Test
    public void getAllBusinesses_success() {
        List<Business> businesses = new ArrayList<Business>(Arrays.asList(BUSINESS_1, BUSINESS_2, BUSINESS_3));
        businessRepository.save(BUSINESS_1);
        businessRepository.save(BUSINESS_2);
        businessRepository.save(BUSINESS_3);

        assertEquals(businesses, businessRepository.findAll());
    }

}
    