package com.bbd.licenscerenewal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bbd.licenscerenewal.models.Representative;
import com.bbd.licenscerenewal.services.RepresentativeRepo;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class RepresentativeController {

    @Autowired
    RepresentativeRepo representativeRepo;

    @GetMapping("/representative")
    public <T> ResponseEntity<List<Representative>> getAllVehicles(@RequestParam(required = false) Map<String,T> allParams){
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
    public ResponseEntity<Representative> insertRepresentative(@RequestBody Representative representative){
        Representative result = representativeRepo.add(representative);
        return new ResponseEntity<> (result, HttpStatus.CREATED);
    }

    @PatchMapping("/representative/{id}")
    public <T> ResponseEntity<Representative> patchRepresentative(@PathVariable int id, @RequestBody Map<String,T> value){
        Set<Map.Entry<String,T>> values = value.entrySet();
        Representative result = representativeRepo.patchRepresentative(id, values);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}