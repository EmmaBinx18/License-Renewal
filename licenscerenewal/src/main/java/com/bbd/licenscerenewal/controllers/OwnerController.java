package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.models.Address;
import com.bbd.licenscerenewal.models.IdentificationType;
import com.bbd.licenscerenewal.models.Owner;
import com.bbd.licenscerenewal.models.OwnerType;
import com.bbd.licenscerenewal.models.Representative;
import com.bbd.licenscerenewal.services.*;

import com.bbd.licenscerenewal.utils.logging.LogRequest;
import com.bbd.licenscerenewal.utils.logging.LogType;
import com.bbd.licenscerenewal.utils.logging.Logger;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class OwnerController {

    Logger logger = new Logger(new LogRequest());

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
    public <T> ResponseEntity<List<Owner>> getAllVehicles(@RequestParam(required = false) Map<String,T> allParams, HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException {

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

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

    @GetMapping(value = "/owners/{id}", headers = "X-API-VERSION=1")
    public ResponseEntity<Owner> getById(@PathVariable int id, HttpServletRequest request)  throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        Owner result = ownerRepo.getById(id);
        if(result.getOwnerId() == -1){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/owners/{id}/addresses", headers = "X-API-VERSION=1")
    public ResponseEntity<Map<String, Object>> getAddresses(@PathVariable int id, HttpServletRequest request)  throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

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
    public ResponseEntity<Representative> getRepresentative(@PathVariable int id, HttpServletRequest request)  throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        Representative result = representativeRepo.getById(id);
        if(result.getRepresentativeId() == -1){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/owners/types", headers = "X-API-VERSION=1")
    public ResponseEntity<List<OwnerType>> getOwnerTypes(HttpServletRequest request)  throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        List<OwnerType> result = ownerTypeRepo.getOwnerTypes();
        if(result.size() >0){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @PatchMapping(value = "/owners/{id}", headers = "X-API-VERSION=1")
    public <T> ResponseEntity<Owner> patchOwner(@PathVariable int id, @RequestBody Map<String,T> value, HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        Set<Map.Entry<String,T>> values = value.entrySet();
        Owner result = ownerRepo.patchOwner(id, values);
        if(result.getOwnerId() == -1)
        {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/owners", headers = "X-API-VERSION=1")
    @Validated(OnCreate.class)
    public ResponseEntity<Owner> insert(@Valid @RequestBody Owner owner, HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        Owner result = ownerRepo.add(owner);
        if(result.getOwnerId() ==-1){
            return new ResponseEntity<> (result, HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<> (result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/identificationTypes", headers = "X-API-VERSION=1")
    public ResponseEntity<List<IdentificationType>> getIdentificationTypes(HttpServletRequest request)  throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        List<IdentificationType> identificationTypes = identificationTypeRepo.getIdentificationTypes();
        if(identificationTypes.size() > 0 )
        {
            return new ResponseEntity<>(identificationTypes, HttpStatus.OK);
        }
        return new ResponseEntity<>(identificationTypes, HttpStatus.NOT_MODIFIED);
    }
}