package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Service
public class VehicleRepo implements IRepository<Vehicle>{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Override
    public Vehicle update(Vehicle toUpdate) {
        return null;
    }

    @Override
    public Vehicle delete(Vehicle toDelete) {
        return null;
    }

    @Override
    public Vehicle add(Vehicle toAdd) {
        return null;
    }

    @Override
    public List<Vehicle> convertResultSet(ResultSet toConvert) throws SQLException {
        return null;
    }

    @Override
    public Vehicle get(int Id) {
        return null;
    }
}
