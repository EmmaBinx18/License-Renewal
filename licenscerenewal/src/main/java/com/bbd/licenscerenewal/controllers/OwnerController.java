package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.models.Address;
import com.bbd.licenscerenewal.models.IdentificationType;
import com.bbd.licenscerenewal.models.Owner;
import com.bbd.licenscerenewal.models.OwnerType;
import com.bbd.licenscerenewal.models.Representative;
import com.bbd.licenscerenewal.services.*;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/owners")
    public <T> ResponseEntity<List<Owner>> getAllVehicles(@RequestParam(required = false) Map<String,T> allParams) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException {
        Set<Map.Entry<String,T>> params = allParams.entrySet();
        if(params.isEmpty()){
            List<Owner> owners = ownerRepo.getAll();
            return new ResponseEntity<>(owners, HttpStatus.OK);
        }
        else{
            List<Owner> owners = ownerRepo.getByQueryParams(params);
            return new ResponseEntity<>(owners, HttpStatus.OK);
        }
    }

    @GetMapping("/owners/{id}")
    public ResponseEntity<Owner> getById(@PathVariable int id)  throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{
        Owner result = ownerRepo.getById(id);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/owners/{id}/addresses")
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

    @GetMapping("/owners/{id}/representative")
    public ResponseEntity<Representative> getRepresentative(@PathVariable int id)  throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{
        Representative result = representativeRepo.getById(id);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/owners/types")
    public ResponseEntity<List<OwnerType>> getOwnerTypes()  throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{
        List<OwnerType> result = ownerTypeRepo.getOwnerTypes();
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/owners/{id}")
    public <T> ResponseEntity<Owner> patchOwner(@PathVariable int id, @RequestBody Map<String,T> value) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{
        Set<Map.Entry<String,T>> values = value.entrySet();
        Owner result = ownerRepo.patchOwner(id, values);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/owners")
    @Validated(OnCreate.class)
    public ResponseEntity<Owner> insert(@Valid @RequestBody Owner owner) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{
        Owner result = ownerRepo.add(owner);
        return new ResponseEntity<> (result, HttpStatus.CREATED);
    }

    @GetMapping("/identificationTypes")
    public ResponseEntity<List<IdentificationType>> getIdentificationTypes()  throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{
        List<IdentificationType> identificationTypes = identificationTypeRepo.getIdentificationTypes();
        return new ResponseEntity<>(identificationTypes, HttpStatus.OK);
    }
}