package com.bbd.licenscerenewal.services;

import com.bbd.licenscerenewal.models.Address;
import com.bbd.licenscerenewal.models.LicenseRenewalHistory;
import com.bbd.licenscerenewal.models.NullObjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressRepo implements IRepository<Address>{

    Logger logger = new Logger(new LogSQL());

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Autowired
    NullObjects nullObjects;

    public Address update(Address toUpdate) throws SQLException {
        Connection conn = null;
        try {
            conn = databaseService.getConnection();
            PreparedStatement update = conn.prepareStatement("UPDATE TABLE Address SET AddressLine1 = ?,AddressLine2 = ?AddressLine3 = ? ,PostalCode = ?,AddressTypeId = ?  WHERE AddressId = ?");
            update.setString(1, toUpdate.getAddressLine1());
            update.setString(2, toUpdate.getAddressLine2());
            update.setString(3, toUpdate.getAddressLine3());
            update.setString(4, toUpdate.getPostalCode());
            update.setInt(5,toUpdate.getAddressTypeId());
            update.setInt(6, toUpdate.getAddressId());

            update.executeUpdate();

            logger.log("UPDATE TABLE Address SET AddressLine1 = ?,AddressLine2 = ?AddressLine3 = ? ,PostalCode = ?,AddressTypeId = ?  WHERE AddressId = ?", LogType.QUERY);

            logger.log(toUpdate, LogType.PARAMETERS);

            logger.log("",LogType.COMPLETED);

            return toUpdate;
        } catch (SQLException exception) { 
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    public Address delete(int id) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement select  = conn.prepareStatement("SELECT * FROM Address WHERE AddressId = ? ");
            select.setInt(1, id);

            PreparedStatement delete = conn.prepareStatement("DELETE FROM Address WHERE AddressId = ?");
            delete.setInt(1, id);

            ResultSet rs = select.executeQuery();
            delete.executeQuery();
            List<Address> list = convertResultSet(rs);

            logger.log("DELETE FROM Address WHERE AddressId = ?", LogType.QUERY);

            logger.log(id, LogType.PARAMETERS);

            logger.log(list,LogType.RESPONSE);

            logger.log("",LogType.COMPLETED);

            return list.isEmpty() ?   nullObjects.getAddress():list.get(0);
        } catch (SQLException throwable) {
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    @Override
    public Address add(Address toAdd) throws SQLException {
        Connection conn = null;
        try {
            conn = databaseService.getConnection();
            return insert(toAdd,conn);
        } catch (SQLException exception) {
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    public Address insert(Address toAdd, Connection conn) throws SQLException {
        CallableStatement sp = conn.prepareCall("{CALL pCreateAddress(?,?,?,?,?)}");
        sp.setString(1, toAdd.getAddressLine1());
        sp.setString(2, toAdd.getAddressLine2());
        sp.setString(3, toAdd.getAddressLine3());
        sp.setString(4, toAdd.getPostalCode());
        sp.setInt(5,toAdd.getAddressTypeId());

        ResultSet rs = sp.executeQuery();
        List<Address> list = convertResultSet(rs);

        logger.log("{CALL pCreateAddress(?,?,?,?,?)}", LogType.QUERY);

        logger.log(toAdd, LogType.PARAMETERS);

        logger.log(list,LogType.RESPONSE);

        logger.log("",LogType.COMPLETED);

        return list.isEmpty() ?   nullObjects.getAddress():list.get(0);
    }

    @Override
    public List<Address> convertResultSet(ResultSet toConvert) throws SQLException {
        List<Address> addresses = new ArrayList<>();

        while(toConvert.next()){
            Address address = new Address();
            address.setAddressId(toConvert.getInt(1));
            address.setAddressLine1(toConvert.getString(2));
            address.setAddressLine2(toConvert.getString(3));
            address.setAddressLine3(toConvert.getString(4));
            address.setAddressTypeId(toConvert.getInt(5));
            address.setPostalCode(toConvert.getString(6));

            addresses.add(address);
        }

        return addresses;
    }

    @Override
    public Address getById(int id) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM Address WHERE AddressId = ?");
            get.setInt(1, id);
            ResultSet rs = get.executeQuery();

            List<Address> list = convertResultSet(rs);

            Logger logger = new Logger(new LogSQL());

            logger.log("SELECT * FROM Address WHERE AddressId = ?", LogType.QUERY);
            
            logger.log(id, LogType.PARAMETERS);
            
            logger.log(list,LogType.RESPONSE);
            
            logger.log("",LogType.COMPLETED);

            return list.isEmpty() ?   nullObjects.getAddress():list.get(0);
        } catch (SQLException exception) {
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

}
