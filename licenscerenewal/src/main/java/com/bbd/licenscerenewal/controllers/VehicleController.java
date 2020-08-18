package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.models.Vehicle;
import com.bbd.licenscerenewal.models.VehicleType;
import com.bbd.licenscerenewal.services.VehicleRepo;
import com.bbd.licenscerenewal.services.VehicleTypeRepo;

import org.springframework.beans.factory.annotation.Autowired;
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
    public <T> List<Vehicle> getAllVehicles(@RequestParam(required = false) Map<String,T> allParams)
    {
        Set<Map.Entry<String,T>> params = allParams.entrySet();
        if(params.isEmpty()){
            return vehicleRepo.getAll();
        }
        else{
            return vehicleRepo.getByQueryParams(params);
        }
    }

    @GetMapping("/vehicles/{id}")
    public Vehicle getById(@PathVariable int id) {
        return vehicleRepo.getById(id);
    }

    @GetMapping("/vehicles/types")
    public List<VehicleType> getVehicleTypes()
    {
        return vehicleTypeRepo.getVehicleTypes();
    }

    @PostMapping("/vehicles")
    public Vehicle insert(@RequestBody Vehicle vehicle){
        return vehicleRepo.add(vehicle);
    }

    @PutMapping("/vehicles")
    public Vehicle update(@RequestBody Vehicle vehicle){
        return vehicleRepo.update(vehicle);
    }

    @DeleteMapping("/vehicles/{id}")
    public Vehicle delete(@PathVariable int id) {
        return vehicleRepo.delete(id);
    }

}
