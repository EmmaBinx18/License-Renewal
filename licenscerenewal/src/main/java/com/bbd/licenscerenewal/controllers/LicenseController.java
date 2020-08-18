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
import org.springframework.http.ResponseEntity;
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
    public <T> ResponseEntity<List<License>> getAllLicenses(@RequestParam(required = false) Map<String,T> allParams) {
        Set<Map.Entry<String,T>> params = allParams.entrySet();
        if(params.isEmpty()){
            return ResponseEntity.ok().body(licenseRepo.getAll());
        }
        else{
            return ResponseEntity.ok().body(licenseRepo.getByQueryParams(params));
        }
    }

    @GetMapping("/licenses/{id}")
    public ResponseEntity<License> getById(@PathVariable int id) {
        return ResponseEntity.ok().body(licenseRepo.getById(id));
    }

    @GetMapping("/licenses/statuses")
    public ResponseEntity<List<LicenseStatus>> getLicenseStatuses() {
        return ResponseEntity.ok().body(licenseStatusRepo.getLicenseStatuses());
    }

    @GetMapping("/licenses/types")
    public ResponseEntity<List<LicenseType>> getLicenseTypes() {
        return ResponseEntity.ok().body(licenseTypeRepo.getLicenseTypes());
    }

    @PostMapping("/licenses")
    public ResponseEntity<License> insert(@RequestBody License license){
        return ResponseEntity.created(null).body(licenseRepo.add(license));
    }

    @PutMapping("/licenses")
    public ResponseEntity<License> update(@RequestBody License license){
        return ResponseEntity.ok().body(licenseRepo.update(license));
    }

    @DeleteMapping("/licenses/{id}")
    public ResponseEntity<License> delete(@PathVariable int id) {
        return ResponseEntity.ok().body(licenseRepo.delete(id));
    }

    // @PostMapping("/licenses/{id}/renew")
    // @ResponseBody
    // public License renewLicense(@PathVariable int id) {
    //     return licenseRepo.getById(id);
    // }

    @GetMapping("/licenses/{id}/history")
    public ResponseEntity<LicenseRenewalHistory> getRenewalHistory(@PathVariable int id) {
        return ResponseEntity.ok().body(licenseRenwalHistoryRepo.getById(id));
    }
}
