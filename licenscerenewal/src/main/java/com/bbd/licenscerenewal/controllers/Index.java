package com.bbd.licenscerenewal.controller;

import com.bbd.licenscerenewal.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {
    @Autowired
    DatabaseService DBS;

    @GetMapping("/test")
    String test()
    {
        return "test";
    }

    //API ENPOINTS TO CREATE

    //Get all Licenses
    //Get license ready for renewal based on dates passed
    //Get license based on license number
    //Get license by expiry date
    //Get license by date issued
    //Get license by owner
    //Get licenses for organisation
    //Get license based on vehicle parameters
    //Get licenses based on status
    //Get licenses based on type
    //Post new license
    //Update license and everything that comes with that

    //Get all vehicles
    //Get vehicle based on owner
    //Get vehicle based on license 
    //Get vehicle based on vin
    //Get vehicle based on make
    //Get vehicle by type
    //Post register vehicle ???? NOT SURE ABOUT THIS

    //Get license renewal history based on license
    //Get all renewal dates for license
    //Get fee for license renewal
    //Post pay for license

}
