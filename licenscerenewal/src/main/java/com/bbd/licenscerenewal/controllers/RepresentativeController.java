package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.services.OnCreate;
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

import com.bbd.licenscerenewal.models.Representative;
import com.bbd.licenscerenewal.services.RepresentativeRepo;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class RepresentativeController {

    Logger logger = new Logger(new LogRequest());

    @Autowired
    RepresentativeRepo representativeRepo;

    @GetMapping("/representative")
    public <T> ResponseEntity<List<Representative>> getAllVehicles(@RequestParam(required = false) Map<String,T> allParams, HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException {

        logger.log(request.getRemoteAddr(), LogType.DEFAULT);

        Set<Map.Entry<String,T>> params = allParams.entrySet();
        if(params.isEmpty()){
            List<Representative> representatives = representativeRepo.getAll();
            return new ResponseEntity<>(representatives, HttpStatus.OK);
        }
        else{
            List<Representative> representatives = representativeRepo.getByQueryParams(params);
            return new ResponseEntity<>(representatives, HttpStatus.OK);
        }
    }

    @PostMapping("/representative")
    @Validated(OnCreate.class)
    public ResponseEntity<Representative> insertRepresentative(@Valid @RequestBody Representative representative, HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.DEFAULT);

        Representative result = representativeRepo.add(representative);
        return new ResponseEntity<> (result, HttpStatus.CREATED);
    }

    @PatchMapping("/representative/{id}")
    public <T> ResponseEntity<Representative> patchRepresentative(@PathVariable int id, @RequestBody Map<String,T> value, HttpServletRequest request) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.DEFAULT);

        Set<Map.Entry<String,T>> values = value.entrySet();
        Representative result = representativeRepo.patchRepresentative(id, values);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}