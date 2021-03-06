package com.bbd.licenscerenewal.services;

import com.bbd.licenscerenewal.models.VehicleType;
import com.bbd.licenscerenewal.utils.logging.LogRequest;
import com.bbd.licenscerenewal.utils.logging.LogSQL;
import com.bbd.licenscerenewal.utils.logging.LogType;
import com.bbd.licenscerenewal.utils.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class VehicleTypeRepo{

    Logger logger= new Logger(new LogSQL());

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    public List<VehicleType> getVehicleTypes() throws SQLException {
        Connection conn = null;
        try{
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM VehicleType");
            logger.log("SELECT * FROM VehicleType", LogType.QUERY);
            ResultSet rs = get.executeQuery();
            
            List<VehicleType> vehicleTypes = new ArrayList<>();
            while(rs.next()){
                VehicleType vehicleType = new VehicleType();
                vehicleType.setVehicleTypeId(rs.getInt(1));
                vehicleType.setName(rs.getString(2));
                vehicleTypes.add(vehicleType);
            }
            logger.log(vehicleTypes,LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);
            return vehicleTypes;
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }
}
