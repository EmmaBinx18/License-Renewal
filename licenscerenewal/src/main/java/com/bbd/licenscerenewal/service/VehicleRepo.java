package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
            insert.setInt(5, toAdd.getVehicleTypeId());

            insert.executeUpdate();
            toAdd.setVehicleId(insert.getGeneratedKeys().getInt(0));
            databaseService.ReleaseConnection(conn);
            return toAdd;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Vehicle> convertResultSet(ResultSet toConvert) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<Vehicle>();

        while(toConvert.next()){
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleId(toConvert.getInt(0));
            vehicle.setRegistrationNumber(toConvert.getString(1));
            vehicle.setVin(toConvert.getString(2));
            vehicle.setMake(toConvert.getString(3));
            vehicle.setModel(toConvert.getString(4));
            vehicle.setOdometer(toConvert.getInt(5));
            vehicle.setVehicleTypeId(toConvert.getInt(6));
            vehicles.add(vehicle);
        }

        return vehicles;
    }

    @Override
    public Vehicle getById(int id) {
        try {
            Connection conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM Vehicle WHERE VehicleId = ?");
            get.setInt(1, id);

            ResultSet rs = get.executeQuery();
            Vehicle vehicle = convertResultSet(rs).get(0);
            databaseService.ReleaseConnection(conn);
            return vehicle;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


    public Vehicle getByRegistrationNumber(String registrationNumber) {
        try {
            Connection conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM Vehicle WHERE RegistrationNumber = ?");
            get.setString(1, registrationNumber);

            ResultSet rs = get.executeQuery();
            Vehicle vehicle = convertResultSet(rs).get(0);
            databaseService.ReleaseConnection(conn);
            return vehicle;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


    public List<Vehicle> getByMake(String make) {
        try {
            Connection conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM Vehicle WHERE Make = ?");
            get.setString(1, make);

            ResultSet rs = get.executeQuery();
            List<Vehicle> vehicles = convertResultSet(rs);
            databaseService.ReleaseConnection(conn);
            return vehicles;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
