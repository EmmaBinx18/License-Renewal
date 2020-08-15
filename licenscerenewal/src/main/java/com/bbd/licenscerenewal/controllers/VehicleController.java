package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.models.Vehicle;
import com.bbd.licenscerenewal.service.VehicleRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class VehicleController {

    @Autowired
    VehicleRepo vehicleRepo;

    @GetMapping("/vehicle")
    public List<Vehicle> getAllLicenses()
    {
        return vehicleRepo.getAll();
    }

    // @GetMapping("/employees/{id}")
    // Employee one(@PathVariable Long id) {

    //     return repository.findById(id)
    //     .orElseThrow(() -> new EmployeeNotFoundException(id));
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
