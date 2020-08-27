package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.models.Vehicle;
import com.bbd.licenscerenewal.models.VehicleType;
import com.bbd.licenscerenewal.services.OnCreate;
import com.bbd.licenscerenewal.services.VehicleRepo;
import com.bbd.licenscerenewal.services.VehicleTypeRepo;

import com.bbd.licenscerenewal.utils.logging.LogRequest;
import com.bbd.licenscerenewal.utils.logging.LogType;
import com.bbd.licenscerenewal.utils.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
class VehicleController {

    Logger logger = new Logger(new LogRequest());

    @Autowired
    VehicleRepo vehicleRepo;

    @Autowired
    VehicleTypeRepo vehicleTypeRepo;

    @GetMapping(value = "/vehicles", headers = "X-API-VERSION=1")
    public ResponseEntity<Map<String, Object>> getAllVehiclesPaged(@RequestParam int page, @RequestParam(defaultValue = "100") int size, HttpServletRequest request) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException {

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        Pageable paging = PageRequest.of(page, size);
        Page<List<Vehicle>> vehicles = vehicleRepo.getAllPaged(paging);

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

    @GetMapping(value = "/vehicles/query", headers = "X-API-VERSION=1")
    public <T> ResponseEntity<List<Vehicle>> getAllVehicles(@RequestParam Map<String,T> allParams, HttpServletRequest request) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        Set<Map.Entry<String,T>> params = allParams.entrySet();
        List<Vehicle> vehicles = vehicleRepo.getByQueryParams(params);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @GetMapping(value = "/vehicles/{id}", headers = "X-API-VERSION=1")
    public ResponseEntity<Vehicle> getById(@PathVariable int id, HttpServletRequest request) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException {

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        Vehicle result = vehicleRepo.getById(id);
        if(result.getVehicleId() == -1){
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/vehicles/types", headers = "X-API-VERSION=1")
    public ResponseEntity<List<VehicleType>> getVehicleTypes(HttpServletRequest request) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        List<VehicleType> result = vehicleTypeRepo.getVehicleTypes();
        if(result.isEmpty()){
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/vehicles", headers = "X-API-VERSION=1")
    @Validated(OnCreate.class)
    public ResponseEntity<Vehicle> insert(@Valid @RequestBody Vehicle vehicle,HttpServletRequest request) throws SQLException, SQLTimeoutException, RuntimeException, HttpClientErrorException, HttpServerErrorException{

        logger.log(request.getRemoteAddr(), LogType.REQUEST);

        Vehicle result = vehicleRepo.add(vehicle);
        if(result.getVehicleId() == -1)
        {
            return new ResponseEntity<> (result, HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<> (result, HttpStatus.CREATED);
    }

}
