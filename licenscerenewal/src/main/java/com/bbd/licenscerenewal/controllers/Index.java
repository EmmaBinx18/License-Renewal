package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.models.Address;
import com.bbd.licenscerenewal.models.License;
import com.bbd.licenscerenewal.models.Renewal;
import com.bbd.licenscerenewal.service.AddressRepo;
import com.bbd.licenscerenewal.service.DatabaseService;
import com.bbd.licenscerenewal.service.IDataBasePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
class LicenseRenewalController {


    @Autowired
    AddressRepo addressRepo;

    @GetMapping("/license")
    List<License> getAllLicenses()
    {
        return null;
    }

    @PostMapping("/license")
    License postNewLicense(@RequestBody License newLicense){
        return null;
    }

    @PatchMapping("/license")
    License patchLicense(@RequestBody License license){
        return null;
    }


    @GetMapping("/address")
    Address getAddress(int Id){
        return addressRepo.get(Id);
    }

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
