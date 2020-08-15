package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleTypeRepo{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    public List<VehicleType> getVehicleTypes() {
        try{
            Connection conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM VehicleType");
            ResultSet rs = get.executeQuery();
            databaseService.ReleaseConnection(conn);
            
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
        }
        return new ArrayList<>();
    }
}
