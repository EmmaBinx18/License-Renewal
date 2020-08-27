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

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;

import com.bbd.licenscerenewal.utils.logging.LogRequest;
import com.bbd.licenscerenewal.utils.logging.LogType;
import com.bbd.licenscerenewal.utils.logging.Logger;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Calendar;
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

    @GetMapping("/licenses")
    public <T> ResponseEntity<List<License>> getAllLicenses(@RequestParam(required = false) Map<String,T> allParams, HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException {

        logger.log(request.getRemoteAddr(), LogType.DEFAULT);

        Set<Map.Entry<String,T>> params = allParams.entrySet();
        if(params.isEmpty()){
            List<License> licenses = licenseRepo.getAll();
            return new ResponseEntity<>(licenses, HttpStatus.OK);
        }
        else{
            List<License> licenses = licenseRepo.getByQueryParams(params);
            return new ResponseEntity<>(licenses, HttpStatus.OK);
        }
    }

    // @GetMapping("/licenses")
    // public ResponseEntity<Map<String, Object>> getAllLicensesPaged(@RequestParam int page, @RequestParam(defaultValue = "100") int size) {
    //     try {
    //         Pageable paging = PageRequest.of(page, size);
    //         Page<List<License>> vehicles = licenseRepo.getAllPaged(paging);

    //         if (vehicles.getContent().isEmpty()) {
    //             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //         }

    //         Map<String, Object> response = new HashMap<>();
    //         response.put("vehicles", vehicles.getContent());
    //         response.put("currentPage", vehicles.getNumber());
    //         response.put("totalItems", vehicles.getTotalElements());
    //         response.put("totalPages", vehicles.getTotalPages());

    //         return new ResponseEntity<>(response, HttpStatus.OK);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @GetMapping("/licenses/{id}")
    public ResponseEntity<License> getById(@PathVariable int id,  HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.DEFAULT);

        License result = licenseRepo.getById(id);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/licenses/statuses")
    public ResponseEntity<List<LicenseStatus>> getLicenseStatuses(HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.DEFAULT);

        List<LicenseStatus> result =licenseStatusRepo.getLicenseStatuses();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/licenses/types")
    public ResponseEntity<List<LicenseType>> getLicenseTypes(HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.DEFAULT);

        List<LicenseType> result = licenseTypeRepo.getLicenseTypes();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/licenses")
    @Validated(OnCreate.class)
    public ResponseEntity<License> insert(@Valid @RequestBody License license,HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.DEFAULT);

        License result = licenseRepo.add(license);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping("/licenses/{id}")
    public <T> ResponseEntity<License> patchLicense(@PathVariable int id, @RequestBody Map<String,T> value,HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.DEFAULT);

        Set<Map.Entry<String,T>> values = value.entrySet();
        License result = licenseRepo.patchLicense(id, values);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/licenses/{id}")
    public ResponseEntity<License> delete(@PathVariable int id,HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.DEFAULT);

        License result = licenseRepo.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/licenses/{id}/renew")
    public ResponseEntity<Map<String, Object>> renewLicense(@PathVariable int id,HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.DEFAULT);

        License license = licenseRepo.renew(id);
        // LicenseRenewalHistory history = licenseRenwalHistoryRepo.getLatestHistory(id);

        Map<String, Object> response = new HashMap<>();
        response.put("license", license);
        // response.put("renewal", history);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/licenses/{id}/history")
    public ResponseEntity<List<LicenseRenewalHistory>> getRenewalHistory(@PathVariable int id, HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.DEFAULT);

        List<LicenseRenewalHistory> result = licenseRenwalHistoryRepo.getByLicenseId(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/licenses/{id}/history")
    public ResponseEntity<LicenseRenewalHistory> updateRenewalAction(@PathVariable int id, @RequestBody int action, HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.DEFAULT);

        LicenseRenewalHistory result = licenseRenwalHistoryRepo.updateRenewalAction(id, action);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
