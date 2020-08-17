package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class VehicleTypeRepo{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    public List<VehicleType> getVehicleTypes() {
        Connection conn = null;
        try{
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM VehicleType");
            ResultSet rs = get.executeQuery();
            
            List<VehicleType> vehicleTypes = new ArrayList<>();
            while(rs.next()){
                VehicleType vehicleType = new VehicleType();
                vehicleType.setVehicleTypeId(rs.getInt(1));
                vehicleType.setName(rs.getString(2));
                vehicleTypes.add(vehicleType);
            }
            return vehicleTypes;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return new ArrayList<>();
    }
}
