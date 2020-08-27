package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.models.Address;
import com.bbd.licenscerenewal.models.IdentificationType;
import com.bbd.licenscerenewal.models.Owner;
import com.bbd.licenscerenewal.models.OwnerType;
import com.bbd.licenscerenewal.models.Representative;
import com.bbd.licenscerenewal.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class OwnerController {
    
    @Autowired
    OwnerRepo ownerRepo;

    @Autowired
    OwnerTypeRepo ownerTypeRepo;

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    RepresentativeRepo representativeRepo;

    @Autowired
    IdentificationTypeRepo identificationTypeRepo;

    @GetMapping(value = "/owners", headers = "X-API-VERSION=1")
    public ResponseEntity<Map<String, Object>> getAllLicensesPaged(@RequestParam int page, @RequestParam(defaultValue = "100") int size) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException {
        Pageable paging = PageRequest.of(page, size);
        Page<List<Owner>> owners = ownerRepo.getAllPaged(paging);

        if (owners.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("owners", owners.getContent());
        response.put("currentPage", owners.getNumber());
        response.put("totalItems", owners.getTotalElements());
        response.put("totalPages", owners.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/owners/query", headers = "X-API-VERSION=1")
    public <T> ResponseEntity<List<Owner>> getAllLicenses(@RequestParam Map<String,T> allParams) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException {
        Set<Map.Entry<String,T>> params = allParams.entrySet();
        List<Owner> owners = ownerRepo.getByQueryParams(params);
            return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    @GetMapping(value = "/owners/{id}", headers = "X-API-VERSION=1")
    public ResponseEntity<Owner> getById(@PathVariable int id)  throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{
        Owner result = ownerRepo.getById(id);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/owners/{id}/addresses", headers = "X-API-VERSION=1")
    public ResponseEntity<Map<String, Object>> getAddresses(@PathVariable int id)  throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{
        Owner owner = ownerRepo.getById(id);
        Address postalAddress = addressRepo.getById(owner.getPostalAddressId());
        Address streetAddress = addressRepo.getById(owner.getStreetAddressId());

        Map<String, Object> response = new HashMap<>();
        response.put("postalAddress", postalAddress);
        response.put("streetAddress", streetAddress);
        response.put("prefferedAddress", owner.getChosenAddress());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/owners/{id}/representative", headers = "X-API-VERSION=1")
    public ResponseEntity<Representative> getRepresentative(@PathVariable int id)  throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{
        Representative result = representativeRepo.getById(id);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/owners/types", headers = "X-API-VERSION=1")
    public ResponseEntity<List<OwnerType>> getOwnerTypes()  throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{
        List<OwnerType> result = ownerTypeRepo.getOwnerTypes();
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping(value = "/owners/{id}", headers = "X-API-VERSION=1")
    public <T> ResponseEntity<Owner> patchOwner(@PathVariable int id, @RequestBody Map<String,T> value) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{
        Set<Map.Entry<String,T>> values = value.entrySet();
        Owner result = ownerRepo.patchOwner(id, values);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/owners", headers = "X-API-VERSION=1")
    @Validated(OnCreate.class)
    public ResponseEntity<Owner> insert(@Valid @RequestBody Owner owner) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{
        Owner result = ownerRepo.add(owner);
        return new ResponseEntity<> (result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/identificationTypes", headers = "X-API-VERSION=1")
    public ResponseEntity<List<IdentificationType>> getIdentificationTypes()  throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{
        List<IdentificationType> identificationTypes = identificationTypeRepo.getIdentificationTypes();
        return new ResponseEntity<>(identificationTypes, HttpStatus.OK);
    }
}