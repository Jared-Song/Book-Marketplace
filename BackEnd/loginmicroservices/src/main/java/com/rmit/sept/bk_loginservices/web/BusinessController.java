package com.rmit.sept.bk_loginservices.web;

import javax.validation.Valid;

import com.rmit.sept.bk_loginservices.model.Business;
import com.rmit.sept.bk_loginservices.services.BusinessService;
import com.rmit.sept.bk_loginservices.services.MapValidationErrorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/business")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllBusinesses() {
        Iterable<Business> business = businessService.findAllBusinesses();
        System.out.println(business);
        if(!business.iterator().hasNext()){
            return new ResponseEntity<String>("No businesses found", HttpStatus.OK);
        }
        return new ResponseEntity<Iterable<Business>>(business, HttpStatus.OK);
    }

    @GetMapping(path = "/{Id}")
    public ResponseEntity<?> getBusinessById(@PathVariable Long Id) {
        Business business = businessService.getById(Id);
        if(business == null){
            return new ResponseEntity<String>("Business with ID '" + Id + "' does not exist", HttpStatus.OK);
        }
        return new ResponseEntity<Business>(business, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{Id}")
    public ResponseEntity<?> deleteBusiness(@PathVariable Long Id) {
        Business business = businessService.getById(Id);
        if(business == null){
            return new ResponseEntity<String>("Business with ID '" + Id + "' does not exist", HttpStatus.OK);
        }
        businessService.deleteBusinessById(Id);
        return new ResponseEntity<String>("Business with ID " + Id + " was deleted", HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createNewBusiness(@Valid @RequestBody Business business, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null)
            return errorMap;

        
        Business newBusiness = businessService.saveBusiness(business);
        return new ResponseEntity<Business>(newBusiness, HttpStatus.CREATED);
    }
}