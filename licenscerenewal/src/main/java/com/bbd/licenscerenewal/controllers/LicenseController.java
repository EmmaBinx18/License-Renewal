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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.sql.Date;

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
    public ResponseEntity<License> getById(@PathVariable int id) {
        License result = licenseRepo.getById(id);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/licenses/statuses")
    public ResponseEntity<List<LicenseStatus>> getLicenseStatuses() {
        List<LicenseStatus> result =licenseStatusRepo.getLicenseStatuses();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/licenses/types")
    public ResponseEntity<List<LicenseType>> getLicenseTypes() {
        List<LicenseType> result = licenseTypeRepo.getLicenseTypes();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/licenses")
    public ResponseEntity<License> insert(@RequestBody License license){
        License result = licenseRepo.add(license);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping("/licenses/{id}/expiryDate")
    public ResponseEntity<License> updateExpiryDate(@PathVariable int id, @RequestBody Date expiryDate){
        License result = licenseRepo.updateExpiryDate(id, expiryDate);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/licenses/{id}/status")
    public ResponseEntity<License> updateStatus(@PathVariable int id, @RequestBody int status){
        License result = licenseRepo.updateStatus(id, status);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/licenses/{id}/type")
    public ResponseEntity<License> updateType(@PathVariable int id, @RequestBody int type){
        License result = licenseRepo.updateType(id, type);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/licenses/{id}")
    public ResponseEntity<License> delete(@PathVariable int id) {
        License result = licenseRepo.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/licenses/{id}/renew")
    @ResponseBody
    public ResponseEntity<License> renewLicense(@PathVariable int id) {
        Calendar currenttime = Calendar.getInstance();
        Date date = new Date((currenttime.getTime()).getTime());

        License license = licenseRepo.renew(id);
        LicenseRenewalHistory history = new LicenseRenewalHistory();
        history.setLicenseId(license.getLicenseId());
        history.setRenewalDate(date);
        history.setFee(200.00);
        history.setRenewalActionId(1);
        licenseRenwalHistoryRepo.add(history);

        return new ResponseEntity<>(license, HttpStatus.OK);
    }

    @GetMapping("/licenses/{id}/history")
    public ResponseEntity<List<LicenseRenewalHistory>> getRenewalHistory(@PathVariable int id) {
        List<LicenseRenewalHistory> result = licenseRenwalHistoryRepo.getByLicenseId(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/licenses/{id}/history")
    public ResponseEntity<LicenseRenewalHistory> updateRenewalAction(@PathVariable int id, @RequestBody int action){
        LicenseRenewalHistory result = licenseRenwalHistoryRepo.updateRenewalAction(id, action);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
