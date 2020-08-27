package com.bbd.licenscerenewal.services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bbd.licenscerenewal.models.NullObjects;
import com.bbd.licenscerenewal.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@Service
public class VehicleRepo implements IRepository<Vehicle>{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Autowired
    NullObjects nullObjects;

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
    public Vehicle add(Vehicle toAdd) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            CallableStatement sp = conn.prepareCall("{CALL pCreateVehicle(?,?,?,?,?,?)}");
            sp.setString(1, toAdd.getRegisterNumber());
            sp.setString(2, toAdd.getVin());
            sp.setString(3, toAdd.getMake());
            sp.setString(4, toAdd.getModel());
            sp.setInt(5, toAdd.getOdometer());
            sp.setInt(6, toAdd.getVehicleTypeId());

            ResultSet rs = sp.executeQuery();
            return convertResultSet(rs).size() > 0 ? convertResultSet(rs).get(0) : nullObjects.getVehicle();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
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

    public List<Vehicle> getAll() throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM Vehicle");
            ResultSet rs = get.executeQuery();
            return convertResultSet(rs);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    public Page<List<Vehicle>> getAllPaged(Pageable pageable) throws SQLException {
        List<Vehicle> vehicles = getAll(); 
        int start = (int) pageable.getOffset();
        int end = ((start + pageable.getPageSize()) > vehicles.size() ? vehicles.size() : (start + pageable.getPageSize()));
        return new PageImpl(vehicles.subList(start, end), pageable, vehicles.size());
    }

    @Override
    public Vehicle getById(int id) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM Vehicle WHERE VehicleId = ?");
            get.setInt(1, id);

            ResultSet rs = get.executeQuery();

            return convertResultSet(rs).size() > 0 ? convertResultSet(rs).get(0) : nullObjects.getVehicle();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    public <T> List<Vehicle> getByQueryParams(Set<Map.Entry<String,T>> params) throws SQLException {
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
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }
}
