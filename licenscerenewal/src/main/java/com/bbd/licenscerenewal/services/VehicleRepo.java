package com.bbd.licenscerenewal.services;

import com.bbd.licenscerenewal.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class VehicleRepo implements IRepository<Vehicle>{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    public final Dictionary<String,String> getParams = new Hashtable();

    public VehicleRepo() {
        getParams.put("registerNumber"," AND RegisterNumber = ?");
        getParams.put("model", " AND Model = ?");
        getParams.put("make", " AND Make = ?");
        getParams.put("vin", " AND VIN = ?");
        getParams.put("odometer", " AND Odo = ?");
        getParams.put("vehicleTypeId", " AND VehicleTypeId = ?");
    }

    @Override
    public Vehicle add(Vehicle toAdd) {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement insert  = conn.prepareStatement("INSERT INTO Vehicle VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, toAdd.getRegisterNumber());
            insert.setString(2, toAdd.getVin());
            insert.setString(3, toAdd.getMake());
            insert.setString(4, toAdd.getModel());
            insert.setInt(5, toAdd.getOdometer());
            insert.setInt(6, toAdd.getVehicleTypeId());

            insert.executeUpdate();
            toAdd.setVehicleId(insert.getGeneratedKeys().getInt(1));

            return toAdd;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return null;
    }

    @Override
    public List<Vehicle> convertResultSet(ResultSet toConvert) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();

        while(toConvert.next()){
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleId(toConvert.getInt(1));
            vehicle.setRegisterNumber(toConvert.getString(2));
            vehicle.setVin(toConvert.getString(3));
            vehicle.setMake(toConvert.getString(4));
            vehicle.setModel(toConvert.getString(5));
            vehicle.setOdometer(toConvert.getInt(6));
            vehicle.setVehicleTypeId(toConvert.getInt(7));
            vehicles.add(vehicle);
        }

        return vehicles;
    }

    public List<Vehicle> getAll() {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM Vehicle");
            ResultSet rs = get.executeQuery();
            return convertResultSet(rs);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return new ArrayList<>();
    }

    @Override
    public Vehicle getById(int id) {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM Vehicle WHERE VehicleId = ?");
            get.setInt(1, id);

            ResultSet rs = get.executeQuery();
            return convertResultSet(rs).get(0);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return null;
    }

    public <T> List<Vehicle> getByQueryParams(Set<Map.Entry<String,T>> params) {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            String query = "SELECT * FROM Vehicle WHERE 1=1 ";
            for (Map.Entry<String,T> param: params) {
                query += getParams.get(param.getKey());
            }
            
            PreparedStatement get  = conn.prepareStatement(query);
            int index = 1;
            for (Map.Entry<String,T> param: params) {
                get.setObject(index,param.getValue());
            }
            
            ResultSet rs = get.executeQuery();
            return convertResultSet(rs);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return new ArrayList<>();
    }
}
