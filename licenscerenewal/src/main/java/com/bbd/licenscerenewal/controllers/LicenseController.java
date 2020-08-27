package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.models.License;
import com.bbd.licenscerenewal.models.LicenseRenewalHistory;
import com.bbd.licenscerenewal.models.LicenseStatus;
import com.bbd.licenscerenewal.models.LicenseType;
import com.bbd.licenscerenewal.services.LicenseRenewalHistoryRepo;
import com.bbd.licenscerenewal.services.LicenseRepo;
import com.bbd.licenscerenewal.services.LicenseStatusRepo;
import com.bbd.licenscerenewal.services.LicenseTypeRepo;
import com.bbd.licenscerenewal.services.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import javax.validation.Valid;
import java.util.HashMap;
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
    public ResponseEntity<Map<String, Object>> getAllLicensesPaged(@RequestParam int page, @RequestParam(defaultValue = "100") int size) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException {
        Pageable paging = PageRequest.of(page, size);
        Page<List<License>> vehicles = licenseRepo.getAllPaged(paging);

        if (vehicles.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("vehicles", vehicles.getContent());
        response.put("currentPage", vehicles.getNumber());
        response.put("totalItems", vehicles.getTotalElements());
        response.put("totalPages", vehicles.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/licenses/query")
    public <T> ResponseEntity<List<License>> getAllLicenses(@RequestParam Map<String,T> allParams) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException {
        Set<Map.Entry<String,T>> params = allParams.entrySet();
        List<License> licenses = licenseRepo.getByQueryParams(params);
        return new ResponseEntity<>(licenses, HttpStatus.OK);
    }

    @GetMapping("/licenses/{id}")
    public ResponseEntity<License> getById(@PathVariable int id) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{
        License result = licenseRepo.getById(id);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/licenses/statuses")
    public ResponseEntity<List<LicenseStatus>> getLicenseStatuses() throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{
        List<LicenseStatus> result =licenseStatusRepo.getLicenseStatuses();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/licenses/types")
    public ResponseEntity<List<LicenseType>> getLicenseTypes() throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{
        List<LicenseType> result = licenseTypeRepo.getLicenseTypes();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/licenses")
    @Validated(OnCreate.class)
    public ResponseEntity<License> insert(@Valid @RequestBody License license) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{
        License result = licenseRepo.add(license);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping("/licenses/{id}")
    public <T> ResponseEntity<License> patchLicense(@PathVariable int id, @RequestBody Map<String,T> value) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{
        Set<Map.Entry<String,T>> values = value.entrySet();
        License result = licenseRepo.patchLicense(id, values);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/licenses/{id}")
    public ResponseEntity<License> delete(@PathVariable int id) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{
        License result = licenseRepo.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/licenses/{id}/renew")
    public ResponseEntity<Map<String, Object>> renewLicense(@PathVariable int id) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{
        License license = licenseRepo.renew(id);
        LicenseRenewalHistory history = licenseRenwalHistoryRepo.getLatest(id);

        Map<String, Object> response = new HashMap<>();
        response.put("license", license);
        response.put("renewal", history);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/licenses/{id}/history")
    public ResponseEntity<List<LicenseRenewalHistory>> getRenewalHistory(@PathVariable int id) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{
        List<LicenseRenewalHistory> result = licenseRenwalHistoryRepo.getByLicenseId(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
