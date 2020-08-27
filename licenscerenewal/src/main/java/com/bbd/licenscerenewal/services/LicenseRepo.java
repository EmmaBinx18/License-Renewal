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

import com.bbd.licenscerenewal.models.License;
import com.bbd.licenscerenewal.utils.logging.LogSQL;
import com.bbd.licenscerenewal.utils.logging.LogType;
import com.bbd.licenscerenewal.utils.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@Service
public class LicenseRepo implements IRepository<License> {

    Logger logger = new Logger(new LogSQL());

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    public final Dictionary<String,String> getParams = new Hashtable();
    public final Dictionary<String,String> putParams = new Hashtable();

    public LicenseRepo() {
        getParams.put("owner"," AND OwnerId = ?");
        getParams.put("vehicle", " AND VehicleId = ?");
        getParams.put("expiryDate", " AND ExpiryDate =?");
        getParams.put("status", " AND LicenseStatusId =?");
        getParams.put("type", " AND LicenseTypeId =?");

        putParams.put("licenseNumber", " LicenseNumber = ?,");
        putParams.put("ownerId", " OwnerId = ?,");
        putParams.put("firstIssueDate", " FirstIssueDate = ?,");
        putParams.put("expiryDate", " ExpiryDate = ?,");
        putParams.put("vehicleId", " VehicleId = ?,");
        putParams.put("licenseStatusId", " LicenseStatusId = ?,");
        putParams.put("licenseTypeId", " LicenseTypeId = ?,");
    }

    public <T> License patchLicense(int id, Set<Map.Entry<String,T>> values) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            String query = "UPDATE TABLE License SET";

            logger.log("UPDATE TABLE License SET", LogType.QUERY);

            for (Map.Entry<String,T> value: values) {
                query += putParams.get(value.getKey());
            }
            
            PreparedStatement update  = conn.prepareStatement(query);
            int index = 1;
            for (Map.Entry<String,T> value: values) {
                update.setObject(index,value.getValue());
            }
            logger.log(values, LogType.PARAMETERS);
            
            update.executeUpdate();
            return getById(id);
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    public License renew(int id) throws SQLException {
        Connection conn = null;
        try {
            conn = databaseService.getConnection();
            CallableStatement sp = conn.prepareCall("{CALL pRenewLicense(?)}");
            sp.setInt(1, id);

            ResultSet rs = sp.executeQuery();
            return convertResultSet(rs).get(0);
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }

    }

    public License delete(int id) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();

            PreparedStatement update = conn.prepareStatement("UPDATE TABLE License SET LicenseStatusId = ? WHERE LicenseId = ?");
            update.setInt(1, 4);
            update.setInt(2, id);

            logger.log("UPDATE TABLE License SET LicenseStatusId = ? WHERE LicenseId = ?",LogType.QUERY);
            logger.log(id,LogType.PARAMETERS);

            update.executeUpdate();
            return getById(id);
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }

    }

    @Override
    public License add(License toAdd) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            CallableStatement sp = conn.prepareCall("{CALL pCreateLicense(?,?,?,?,?,?,?)}");
            sp.setString(1, toAdd.getLicenseNumber());
            sp.setInt(2, toAdd.getOwnerId());
            sp.setDate(3, toAdd.getFirstIssueDate());
            sp.setDate(4, toAdd.getExpiryDate());
            sp.setInt(5, toAdd.getVehicleId());
            sp.setInt(6, toAdd.getLicenseStatusId());
            sp.setInt(7, toAdd.getLicenseTypeId());

            logger.log("{CALL pCreateLicense(?,?,?,?,?,?,?)}",LogType.QUERY);
            logger.log(toAdd,LogType.PARAMETERS);

            ResultSet rs = sp.executeQuery();

            License license = convertResultSet(rs).get(0);
            logger.log(license,LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);

            return license;
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }

    }

    @Override
    public List<License> convertResultSet(ResultSet toConvert) throws SQLException {
        List<License> licenses = new ArrayList<>();
        
        while(toConvert.next()){
            License license = new License();
            license.setLicenseId(toConvert.getInt(1));
            license.setLicenseNumber(toConvert.getString(2));
            license.setOwnerId(toConvert.getInt(3));
            license.setFirstIssueDate(toConvert.getDate(4));
            license.setExpiryDate(toConvert.getDate(5));
            license.setVehicleId(toConvert.getInt(6));
            license.setLicenseStatusId(toConvert.getInt(7));
            license.setLicenseTypeId(toConvert.getInt(8));
            licenses.add(license);
        }

        return licenses;
    }

    public List<License> getAll() throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM License");
            logger.log("SELECT * FROM License",LogType.QUERY);
            ResultSet rs = get.executeQuery();

            List<License> licenses = convertResultSet(rs);
            logger.log(licenses,LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);
            return licenses;
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }

    }

    public Page<List<License>> getAllPaged(Pageable pageable) throws SQLException {
        List<License> vehicles = getAll(); 
        int start = (int) pageable.getOffset();
        int end = ((start + pageable.getPageSize()) > vehicles.size() ? vehicles.size() : (start + pageable.getPageSize()));
        return new PageImpl(vehicles.subList(start, end), pageable, vehicles.size());
    }

    @Override
    public License getById(int id) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM License WHERE LicenseId = ?");
            logger.log("SELECT * FROM License WHERE LicenseId = ?",LogType.QUERY);
            get.setInt(1, id);

            ResultSet rs = get.executeQuery();

            License license =  convertResultSet(rs).get(0);

            logger.log(license,LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);

            return license;
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }

    }

    public <T> List<License> getByQueryParams(Set<Map.Entry<String,T>> params) throws SQLException {
        Connection conn = null;
        try {
                conn  = databaseService.getConnection();
                String query = "SELECT * FROM License WHERE 1=1 ";

                logger.log("SELECT * FROM License WHERE 1=1 ",LogType.QUERY);
                for (Map.Entry<String,T> param: params) {
                    query += getParams.get(param.getKey());
                }

                PreparedStatement get  = conn.prepareStatement(query);
                int index = 1;
                for (Map.Entry<String,T> param: params) {
                    get.setObject(index,param.getValue());
                }

                logger.log(params,LogType.PARAMETERS);
                ResultSet rs = get.executeQuery();
                List<License> licenses = convertResultSet(rs);

                logger.log(licenses,LogType.RESPONSE);
                logger.log("",LogType.RESPONSE);

                return licenses;
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }

    }
}
