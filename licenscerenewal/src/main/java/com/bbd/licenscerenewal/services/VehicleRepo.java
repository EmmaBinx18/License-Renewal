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
import com.bbd.licenscerenewal.utils.logging.LogRequest;
import com.bbd.licenscerenewal.utils.logging.LogType;
import com.bbd.licenscerenewal.utils.logging.Logger;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@Service
public class VehicleRepo implements IRepository<Vehicle>{

    private Logger logger = new Logger(new LogRequest());

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
            logger.log("{CALL pCreateVehicle(?,?,?,?,?,?)}", LogType.QUERY);
            logger.log(toAdd,LogType.PARAMETERS);

            ResultSet rs = sp.executeQuery();
            List<Vehicle> list = convertResultSet(rs);
            logger.log(list,LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);
            return list.isEmpty() ?   nullObjects.getVehicle():list.get(0);
        } catch (SQLException exception) {
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
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
            logger.log("SELECT * FROM Vehicle",LogType.QUERY);
            ResultSet rs = get.executeQuery();
            List<Vehicle> list = convertResultSet(rs);
            logger.log(list,LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);
            return list;
        } catch (SQLException exception) {
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    public Page<List<Vehicle>> getAllPaged(Pageable pageable) throws SQLException {
        try {
            List<Vehicle> vehicles = getAll(); 
            int start = (int) pageable.getOffset();
            int end = ((start + pageable.getPageSize()) > vehicles.size() ? vehicles.size() : (start + pageable.getPageSize()));
            return new PageImpl(vehicles.subList(start, end), pageable, vehicles.size());
        } catch (SQLException exception) {
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
        }
    }

    @Override
    public Vehicle getById(int id) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            CallableStatement sp = conn.prepareCall("{CALL pGetVehicle(?)}");
            sp.setInt(1, id);
            logger.log("{CALL pGetVehicle(?)}",LogType.QUERY);
            logger.log(id,LogType.PARAMETERS);

            ResultSet rs = sp.executeQuery();
            List<Vehicle> list = convertResultSet(rs);
            logger.log(list, LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);
            return list.isEmpty() ?   nullObjects.getVehicle():list.get(0);
        } catch (SQLException exception) {
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
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
            logger.log("SELECT * FROM Vehicle WHERE 1=1", LogType.QUERY);
            logger.log(params,LogType.PARAMETERS);
            PreparedStatement get  = conn.prepareStatement(query);
            int index = 1;
            for (Map.Entry<String,T> param: params) {
                get.setObject(index,param.getValue());
            }
            
            ResultSet rs = get.executeQuery();
            List<Vehicle> list = convertResultSet(rs);
            logger.log(list,LogType.RESPONSE);
            logger.log("", LogType.COMPLETED);
            return list;
        } catch (SQLException exception) {
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }
}
