package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleTypeRepo implements IRepository<VehicleType>{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Autowired
    VehicleTypeRepo vehicleTypesRepo;


    @Override
    public VehicleType update(VehicleType toUpdate) {
        return null;
    }

    @Override
    public VehicleType delete(int id) {
        return null;
    }

    @Override
    public VehicleType add(VehicleType toAdd) {
        return null;
    }

    @Override
    public List<VehicleType> convertResultSet(ResultSet toConvert) throws SQLException {
        List<VehicleType> vehicleTypes = new ArrayList<VehicleType>();

        while(toConvert.next()){
            VehicleType vehicleType = new VehicleType();
            vehicleType.setVehicleTypeId(toConvert.getInt(1));
            vehicleType.setName(toConvert.getString(2));
            vehicleTypes.add(vehicleType);
        }

        return vehicleTypes;
    }

    @Override
    public VehicleType getById(int id) {
        return null;
    }
}
