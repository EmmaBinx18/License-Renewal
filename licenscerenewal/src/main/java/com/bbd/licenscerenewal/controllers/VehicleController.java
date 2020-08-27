package com.bbd.licenscerenewal.controllers;

import com.bbd.licenscerenewal.models.Vehicle;
import com.bbd.licenscerenewal.models.VehicleType;
import com.bbd.licenscerenewal.services.OnCreate;
import com.bbd.licenscerenewal.services.OnUpdate;
import com.bbd.licenscerenewal.services.VehicleRepo;
import com.bbd.licenscerenewal.services.VehicleTypeRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
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
    public <T> ResponseEntity<List<Vehicle>> getAllVehicles(@RequestParam(required = false) Map<String,T> allParams) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException {
        Set<Map.Entry<String,T>> params = allParams.entrySet();
        if(params.isEmpty()){
            List<Vehicle> vehicles = vehicleRepo.getAll();
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        }
        else{
            List<Vehicle> vehicles = vehicleRepo.getByQueryParams(params);
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        }
    }

    // @GetMapping("/vehicles")
    // public ResponseEntity<Map<String, Object>> getAllVehiclesPaged(@RequestParam int page, @RequestParam(defaultValue = "100") int size) {
    //     try {
    //         Pageable paging = PageRequest.of(page, size);
    //         Page<List<Vehicle>> vehicles = vehicleRepo.getAllPaged(paging);

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

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> getById(@PathVariable int id) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException {
        Vehicle result = vehicleRepo.getById(id);
        if(result == null){
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/vehicles/types")
    public ResponseEntity<List<VehicleType>> getVehicleTypes() throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{
        List<VehicleType> result = vehicleTypeRepo.getVehicleTypes();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/vehicles")
    @Validated(OnCreate.class)
    public ResponseEntity<Vehicle> insert(@Valid @RequestBody Vehicle vehicle) throws SQLException, SQLTimeoutException,RuntimeException, HttpClientErrorException, HttpServerErrorException{
        Vehicle result = vehicleRepo.add(vehicle);
        return new ResponseEntity<> (result, HttpStatus.CREATED);
    }

}
