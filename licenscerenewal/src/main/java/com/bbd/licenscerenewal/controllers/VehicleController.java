package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.models.Vehicle;
import com.bbd.licenscerenewal.models.VehicleType;
import com.bbd.licenscerenewal.service.VehicleRepo;
import com.bbd.licenscerenewal.service.VehicleTypeRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles") 
class VehicleController {

    @Autowired
    VehicleRepo vehicleRepo;

    @Autowired
    VehicleTypeRepo vehicleTypeRepo;

    @GetMapping("/")
    public List<Vehicle> getAllLicenses()
    {
        return vehicleRepo.getAll();
    }

    @GetMapping("/{id}")
    public Vehicle getById(@PathVariable int id) {
        return vehicleRepo.getById(id);
    }

    @GetMapping("/")
    public Vehicle getByRegistrationNumber(@RequestParam("registrationNumber") String registrationNumber) {
        return vehicleRepo.getByRegistrationNumber(registrationNumber);
    }

    @GetMapping("/")
    public List<Vehicle> getByMake(@RequestParam("make") String make) {
        return vehicleRepo.getByMake(make);
    }

    @GetMapping("/")
    public Vehicle getByVin(@RequestParam("vin") String vin) {
        return vehicleRepo.getByVin(vin);
    }

    @GetMapping("/types")
    public List<VehicleType> getVehicleTypes()
    {
        return vehicleTypeRepo.getVehicleTypes();
    }

    @PostMapping("/")
    @ResponseBody
    public Vehicle insert(@RequestBody Vehicle vehicle){
        return vehicleRepo.add(vehicle);
    }

    @PutMapping("/")
    @ResponseBody
    public Vehicle update(@RequestBody Vehicle vehicle){
        return vehicleRepo.update(vehicle);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Vehicle delete(@PathVariable int id) {
        return vehicleRepo.delete(id);
    }
}
