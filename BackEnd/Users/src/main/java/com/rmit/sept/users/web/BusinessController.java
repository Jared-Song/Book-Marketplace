package com.rmit.sept.users.web;

import javax.validation.Valid;

import com.rmit.sept.users.model.Business;
import com.rmit.sept.users.services.BusinessService;
import com.rmit.sept.users.services.MapValidationErrorService;

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
import org.springframework.web.bind.annotation.RestController;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/business")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    // GET api/business/all returns all businesses in the system
    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllBusinesses() {
        Iterable<Business> business = businessService.findAllBusinesses();
        System.out.println(business);

        //check to see if any businesses were found
        if(!business.iterator().hasNext()){
            return new ResponseEntity<String>("No businesses found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Iterable<Business>>(business, HttpStatus.OK);
    }

    // GET api/business/{id} returns a business within the system by ID
    @GetMapping(path = "/{Id}")
    public ResponseEntity<?> getBusinessById(@PathVariable Long Id) {
        Business business = businessService.getById(Id);
        //Check to see if the business exists
        if(business == null){
            return new ResponseEntity<String>("Business with ID '" + Id + "' does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Business>(business, HttpStatus.OK);
    }

    // DELETE api/business/{id} deletes a business within the system by ID
    @DeleteMapping(path = "/{Id}")
    public ResponseEntity<?> deleteBusiness(@PathVariable Long Id) {
        Business business = businessService.getById(Id);
        //Check to see if the business exists
        if(business == null){
            return new ResponseEntity<String>("Business with ID '" + Id + "' does not exist", HttpStatus.NOT_FOUND);
        }
        businessService.deleteBusinessById(Id);
        return new ResponseEntity<String>("Business with ID " + Id + " was deleted", HttpStatus.OK);
    }

    // create a new business and links it to a given user
    @PostMapping("/new")
    public ResponseEntity<?> createNewBusiness(@Valid @RequestBody Business business, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null)
            return errorMap;

        
        Business newBusiness = businessService.saveBusiness(business);
        if (newBusiness == null){
            return new ResponseEntity<String>("User with ID '" + business.getUser().getId() + "' does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Business>(newBusiness, HttpStatus.CREATED);
    }
}