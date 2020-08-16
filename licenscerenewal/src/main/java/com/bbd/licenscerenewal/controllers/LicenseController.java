package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.models.License;
import com.bbd.licenscerenewal.models.LicenseStatus;
import com.bbd.licenscerenewal.models.LicenseType;
import com.bbd.licenscerenewal.service.LicenseRepo;
import com.bbd.licenscerenewal.service.LicenseStatusRepo;
import com.bbd.licenscerenewal.service.LicenseTypeRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
class LicenseController {

    @Autowired
    LicenseRepo licenseRepo;

    @Autowired
    LicenseStatusRepo licenseStatusRepo;

    @Autowired
    LicenseTypeRepo licenseTypeRepo;

    @GetMapping("/licenses")
    @ResponseBody
    public <T> List<License> getAllLicenses(@RequestParam(required = false) Map<String,T> allParams)
    {
        Set<Map.Entry<String,T>> params = allParams.entrySet();
        if(params.isEmpty()){
            return licenseRepo.getAll();
        }
        else{
            return licenseRepo.getByQueryParams(params);
        }
    }

    @GetMapping("/licenses/{id}")
    @ResponseBody
    public License getById(@PathVariable int id) {
        return licenseRepo.getById(id);
    }

    // @GetMapping("/licenses")
    // @ResponseBody
    // public List<License> getByOwner(@RequestParam(required = false) int owner) {
    //     return licenseRepo.getByOwner(owner);
    // }

    // @GetMapping("/licenses")
    // @ResponseBody
    // public License getByVehicle(@RequestParam int vehicle) {
    //     return licenseRepo.getByVehicle(vehicle);
    // }

    // @GetMapping("/licenses")
    // @ResponseBody
    // public List<License> getByExpiryDate(@RequestParam Date expiryDate) {
    //     return licenseRepo.getByExpiryDate(expiryDate);
    // }

    // @GetMapping("/licenses")
    // @ResponseBody
    // public List<License> getByFirstIssueDate(@RequestParam Date firstIssueDate) {
    //     return licenseRepo.getByFirstIssueDate(firstIssueDate);
    // }

    // @GetMapping("/licenses")
    // @ResponseBody
    // public List<License> getByType(@RequestParam int type) {
    //     return licenseRepo.getByType(type);
    // }

    // @GetMapping("/licenses")
    // @ResponseBody
    // public List<License> getByStatus(@RequestParam int status) {
    //     return licenseRepo.getByStatus(status);
    // }

    @GetMapping("/licenses/statuses")
    public List<LicenseStatus> getLicenseStatuses()
    {
        return licenseStatusRepo.getLicenseStatuses();
    }

    @GetMapping("/licenses/types")
    public List<LicenseType> getLicenseTypes()
    {
        return licenseTypeRepo.getLicenseTypes();
    }

    @PostMapping("/licenses")
    @ResponseBody
    public License insert(@RequestBody License license){
        return licenseRepo.add(license);
    }

    @PutMapping("/licenses")
    @ResponseBody
    public License update(@RequestBody License license){
        return licenseRepo.update(license);
    }

    @DeleteMapping("/licenses/{id}")
    @ResponseBody
    public License delete(@PathVariable int id) {
        return licenseRepo.delete(id);
    }
}
