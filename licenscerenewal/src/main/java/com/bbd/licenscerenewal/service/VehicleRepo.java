package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Service
public class VehicleRepo implements IRepository<Vehicle>{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Override
    public Vehicle update(Vehicle toUpdate) {
        try {
            Connection conn = databaseService.getConnection();
            PreparedStatement update = conn.prepareStatement("UPDATE TABLE Vehicle SET RegistrationNumber = ?,VIN = ?,Make = ? ,Model = ?, Odo = ?, VehicleTypeId = ? WHERE VehicleId = ?");
            update.setString(1, toUpdate.getRegistrationNumber());
            update.setString(2, toUpdate.getVin());
            update.setString(3, toUpdate.getMake());
            update.setString(4, toUpdate.getModel());
            update.setInt(5, toUpdate.getOdometer());
            update.setInt(6, toUpdate.getVehicleTypeId());
            update.setInt(7, toUpdate.getVehicleId());

            update.executeUpdate();
            databaseService.ReleaseConnection(conn);
            return toUpdate;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Vehicle delete(int id) {
        try {
            Connection conn  = databaseService.getConnection();
            PreparedStatement select  = conn.prepareStatement("SELECT * FROM Vehicle WHERE VehicleId = ? ");
            select.setInt(1, id);

            PreparedStatement delete = conn.prepareStatement("DELETE FROM Vehicle WHERE VehicleId = ?");
            delete.setInt(1, id);

            ResultSet rs = select.executeQuery();
            delete.executeQuery();
            databaseService.ReleaseConnection(conn);
            return convertResultSet(rs).get(0);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Vehicle add(Vehicle toAdd) {
        try {
            Connection conn  = databaseService.getConnection();
            PreparedStatement insert  = conn.prepareStatement("INSERT INTO Vehicle VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, toAdd.getRegistrationNumber());
            insert.setString(2, toAdd.getVin());
            insert.setString(3, toAdd.getMake());
            insert.setString(4, toAdd.getModel());
            insert.setInt(5, toAdd.getOdometer());
            insert.setInt(6, toAdd.getVehicleTypeId());

            insert.executeUpdate();
            toAdd.setVehicleId(insert.getGeneratedKeys().getInt(1));
            databaseService.ReleaseConnection(conn);
            return toAdd;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Vehicle> convertResultSet(ResultSet toConvert) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();

        while(toConvert.next()){
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleId(toConvert.getInt(1));
            vehicle.setRegistrationNumber(toConvert.getString(2));
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
        try {
            Connection conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM Vehicle");
            ResultSet rs = get.executeQuery();
            databaseService.ReleaseConnection(conn);
            return convertResultSet(rs);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Vehicle getById(int id) {
        try {
            Connection conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM Vehicle WHERE VehicleId = ?");
            get.setInt(1, id);

            ResultSet rs = get.executeQuery();
            databaseService.ReleaseConnection(conn);
            return convertResultSet(rs).get(0);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public <T> List<Vehicle> getByQueryParams(Set<Map.Entry<String,T>> params) {
        try {
            Connection conn  = databaseService.getConnection();
            String query = "SELECT * FROM Vehicle WHERE 1=1";
            params.forEach(param -> {
                if(param.getKey().equals("registrationNumber")){
                    query += " AND RegistrationNumber = ?";
                }
                else if(param.getKey().equals("make")){
                    query += " AND Make = ?";
                }
                else if(param.getKey().equals("vin")){
                    query += " AND VIN = ?";
                }
            });

            PreparedStatement get  = conn.prepareStatement(query);
            int index = 1;
            params.forEach(param -> {
                if(param.getValue() instanceof String){
                    get.setString(index, param.getValue());
                }
                else{
                    get.setInt(index, param.getValue());
                }
                
                index += 1;
            });

            ResultSet rs = get.executeQuery();
            databaseService.ReleaseConnection(conn);
            return convertResultSet(rs);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return new ArrayList<>();
    }
}
