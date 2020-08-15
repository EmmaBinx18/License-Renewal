package com.bbd.licenscerenewal.controller;

import com.bbd.licenscerenewal.service.IDataBasePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class Index {

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool DBS; //makes instance of DB stuff for us

    @GetMapping("/test")
    String test()
    {

        try {
            return DBS.getConnection().toString();
        } catch (SQLException e) {
             e.printStackTrace(); //if you get here then there are no more connections available
            return "We got fucked";
        }

    }



}
