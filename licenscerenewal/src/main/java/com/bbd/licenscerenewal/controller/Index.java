package com.bbd.licenscerenewal.controller;

import com.bbd.licenscerenewal.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {
    @Autowired
     DatabaseService DBS; //makes instance of DB stuff for us

    @GetMapping("/test")
    String test()
    {
        return "test";
    }



}
