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

import com.bbd.licenscerenewal.utils.logging.LogRequest;
import com.bbd.licenscerenewal.utils.logging.LogType;
import com.bbd.licenscerenewal.utils.logging.Logger;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
class LicenseController {

    Logger logger = new Logger(new LogRequest());

    @Autowired
    LicenseRepo licenseRepo;

    @Autowired
    LicenseStatusRepo licenseStatusRepo;

    @Autowired
    LicenseTypeRepo licenseTypeRepo;

    @Autowired
    LicenseRenewalHistoryRepo licenseRenwalHistoryRepo;

    @GetMapping(value = "/licenses", headers = "X-API-VERSION=1")
    public ResponseEntity<Map<String, Object>> getAllLicensesPaged(@RequestParam int page, @RequestParam(defaultValue = "100") int size, HttpServletRequest request) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException {

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

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

    @GetMapping(value = "/licenses/query", headers = "X-API-VERSION=1")
    public <T> ResponseEntity<List<License>> getAllLicenses(@RequestParam Map<String,T> allParams, HttpServletRequest request) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException {

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        Set<Map.Entry<String,T>> params = allParams.entrySet();
        List<License> licenses = licenseRepo.getByQueryParams(params);
        return new ResponseEntity<>(licenses, HttpStatus.OK);
    }

    @GetMapping(value = "/licenses/{id}", headers = "X-API-VERSION=1")
    public ResponseEntity<License> getById(@PathVariable int id, HttpServletRequest request) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        License result = licenseRepo.getById(id);
        if(result.getOwnerId() == -1)
        {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/licenses/statuses", headers = "X-API-VERSION=1")
    public ResponseEntity<List<LicenseStatus>> getLicenseStatuses(HttpServletRequest request) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        List<LicenseStatus> result =licenseStatusRepo.getLicenseStatuses();
        if(result.size() >0)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/licenses/types", headers = "X-API-VERSION=1")
    public ResponseEntity<List<LicenseType>> getLicenseTypes(HttpServletRequest request) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        List<LicenseType> result = licenseTypeRepo.getLicenseTypes();
        if(result.size() >0)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/licenses", headers = "X-API-VERSION=1")
    @Validated(OnCreate.class)
    public ResponseEntity<License> insert(@Valid @RequestBody License license,HttpServletRequest request) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        License result = licenseRepo.add(license);
        if(result.getLicenseId() == -1){
            return new ResponseEntity<>(result, HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/licenses/{id}", headers = "X-API-VERSION=1")
    public <T> ResponseEntity<License> patchLicense(@PathVariable int id, @RequestBody Map<String,T> value,HttpServletRequest request) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        Set<Map.Entry<String,T>> values = value.entrySet();
        License result = licenseRepo.patchLicense(id, values);
        if(result.getLicenseId() == -1){
            return new ResponseEntity<>(result, HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/licenses/{id}", headers = "X-API-VERSION=1")
    public ResponseEntity<License> delete(@PathVariable int id,HttpServletRequest request) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        License result = licenseRepo.delete(id);
        if(result.getLicenseId()==-1){
            return new ResponseEntity<>(result, HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/licenses/{id}/renew", headers = "X-API-VERSION=1")
    public ResponseEntity<Map<String, Object>> renewLicense(@PathVariable int id,HttpServletRequest request) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        License license = licenseRepo.renew(id);
        LicenseRenewalHistory history = licenseRenwalHistoryRepo.getLatest(id);

        Map<String, Object> response = new HashMap<>();
        response.put("license", license);
        response.put("renewal", history);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/licenses/{id}/history", headers = "X-API-VERSION=1")
    public ResponseEntity<List<LicenseRenewalHistory>> getRenewalHistory(@PathVariable int id,HttpServletRequest request) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        List<LicenseRenewalHistory> result = licenseRenwalHistoryRepo.getByLicenseId(id);
        if(result.size() > 0){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }
}
