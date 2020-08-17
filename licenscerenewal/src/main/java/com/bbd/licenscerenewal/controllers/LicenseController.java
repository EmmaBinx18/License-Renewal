package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.models.License;
import com.bbd.licenscerenewal.models.LicenseRenewalHistory;
import com.bbd.licenscerenewal.models.LicenseStatus;
import com.bbd.licenscerenewal.models.LicenseType;
import com.bbd.licenscerenewal.services.LicenseRenewalHistoryRepo;
import com.bbd.licenscerenewal.services.LicenseRepo;
import com.bbd.licenscerenewal.services.LicenseStatusRepo;
import com.bbd.licenscerenewal.services.LicenseTypeRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    LicenseRenewalHistoryRepo licenseRenwalHistoryRepo;

    @GetMapping("/licenses")
    @ResponseBody
    public <T> List<License> getAllLicenses(@RequestParam(required = false) Map<String,T> allParams) {
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

    @GetMapping("/licenses/statuses")
    public List<LicenseStatus> getLicenseStatuses() {
        return licenseStatusRepo.getLicenseStatuses();
    }

    @GetMapping("/licenses/types")
    public List<LicenseType> getLicenseTypes() {
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

    // @PostMapping("/licenses/renewal/{id}")
    // @ResponseBody
    // public License renewLicense(@PathVariable int id) {
    //     return licenseRepo.getById(id);
    // }

    @GetMapping("/licenses/renewal/{id}/history")
    @ResponseBody
    public LicenseRenewalHistory getRenewalHistory(@PathVariable int id) {
        return licenseRenwalHistoryRepo.getById(id);
    }
}
