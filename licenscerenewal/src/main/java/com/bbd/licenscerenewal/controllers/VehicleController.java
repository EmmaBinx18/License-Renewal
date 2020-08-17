package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.models.Vehicle;
import com.bbd.licenscerenewal.models.VehicleType;
import com.bbd.licenscerenewal.service.VehicleRepo;
import com.bbd.licenscerenewal.service.VehicleTypeRepo;

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
    @ResponseBody
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
    @ResponseBody
    public Vehicle insert(@RequestBody Vehicle vehicle){
        return vehicleRepo.add(vehicle);
    }

    @PutMapping("/vehicles")
    @ResponseBody
    public Vehicle update(@RequestBody Vehicle vehicle){
        return vehicleRepo.update(vehicle);
    }

    @DeleteMapping("/vehicles/{id}")
    @ResponseBody
    public Vehicle delete(@PathVariable int id) {
        return vehicleRepo.delete(id);
    }

}
