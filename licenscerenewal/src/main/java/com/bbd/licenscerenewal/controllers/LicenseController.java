package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.models.License;
import com.bbd.licenscerenewal.service.LicenseRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/license")
class LicenseController {

    @Autowired
    LicenseRepo licenseRepo;

    @GetMapping(path = "/", produces = "application/json")
    public List<License> getAllLicenses()
    {
        return licenseRepo.getAll();
    }

    // @PostMapping("/license")
    // License postNewLicense(@RequestBody License newLicense){
    //     return null;
    // }

    // @PatchMapping("/license")
    // License patchLicense(@RequestBody License license){
    //     return null;
    // }


    // @GetMapping("/address/{id}")
    // Address getAddress(@PathVariable("id") int id){
    //     return addressRepo.getById(id);
    // }


    //API ENPOINTS TO CREATE
    //Get license ready for renewal based on dates passed
    //Get license based on license number
    //Get license by expiry date
    //Get license by date issued
    //Get license by owner
    //Get licenses for organisation
    //Get license based on vehicle parameters
    //Get licenses based on status
    //Get licenses based on type
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
