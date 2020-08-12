package com.bbd.licenscerenewal.service;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
@Qualifier("DatabasePool")
public class DatabaseService implements IDataBasePool {
    private String ConnectionString;
    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    //put method here for the DB stuff

}
