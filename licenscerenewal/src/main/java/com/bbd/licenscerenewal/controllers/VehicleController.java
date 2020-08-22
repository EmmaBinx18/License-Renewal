package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.models.Vehicle;
import com.bbd.licenscerenewal.models.VehicleType;
import com.bbd.licenscerenewal.services.VehicleRepo;
import com.bbd.licenscerenewal.services.VehicleTypeRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
class VehicleController {

    @Autowired
    VehicleRepo vehicleRepo;

    @Autowired
    VehicleTypeRepo vehicleTypeRepo;

    @GetMapping("/vehicles")
    public <T> List<Vehicle> getAllVehicles(@RequestParam(required = false) Map<String,T> allParams){
        Set<Map.Entry<String,T>> params = allParams.entrySet();
        if(params.isEmpty()){
            return vehicleRepo.getAll();
        }
        else{
            return vehicleRepo.getByQueryParams(params);
        }
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> getById(@PathVariable int id) {
        Vehicle result = vehicleRepo.getById(id);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/vehicles/types")
    public ResponseEntity<List<VehicleType>> getVehicleTypes(){
        List<VehicleType> result = vehicleTypeRepo.getVehicleTypes();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/vehicles")
    public ResponseEntity<Vehicle> insert(@RequestBody Vehicle vehicle){
        Vehicle result = vehicleRepo.add(vehicle);
        return new ResponseEntity<> (result, HttpStatus.CREATED);
    }

}
