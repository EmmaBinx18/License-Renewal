package com.bbd.licenscerenewal.services;

import com.bbd.licenscerenewal.models.Address;
import com.bbd.licenscerenewal.utils.logging.LogSQL;
import com.bbd.licenscerenewal.utils.logging.LogType;
import com.bbd.licenscerenewal.utils.logging.Logger;
import org.apache.commons.logging.Log;
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

    public Address update(Address toUpdate) {
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

            logger.log("UPDATE TABLE Address SET AddressLine1 = ?,AddressLine2 = ?AddressLine3 = ? ,PostalCode = ?,AddressTypeId = ?  WHERE AddressId = ?", LogType.QUERY);
            logger.log(toUpdate, LogType.PARAMETERS);

            update.executeUpdate();

            logger.log("",LogType.COMPLETED);

            return toUpdate;
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);

            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return null;
    }

    public Address delete(int id) {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement select  = conn.prepareStatement("SELECT * FROM Address WHERE AddressId = ? ");
            select.setInt(1, id);

            logger.log("SELECT * FROM Address WHERE AddressId = ?",LogType.QUERY);

            PreparedStatement delete = conn.prepareStatement("DELETE FROM Address WHERE AddressId = ?");
            delete.setInt(1, id);

            logger.log("DELETE FROM Address WHERE AddressId = ?",LogType.QUERY);

            logger.log(id, LogType.PARAMETERS);

            ResultSet rs = select.executeQuery();
            delete.executeQuery();

            Address address = convertResultSet(rs).get(0);

            logger.log(address, LogType.RESPONSE);
            logger.log(address, LogType.COMPLETED);

            return address;
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);

            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return null;
    }

    @Override
    public Address add(Address toAdd) {
        Connection conn = null;
        try {
            conn = databaseService.getConnection();
            return insert(toAdd,conn);
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return null;
    }

    public Address insert(Address toAdd, Connection conn) throws SQLException {
        CallableStatement sp = conn.prepareCall("{CALL pCreateAddress(?,?,?,?,?)}");
        sp.setString(1, toAdd.getAddressLine1());
        sp.setString(2, toAdd.getAddressLine2());
        sp.setString(3, toAdd.getAddressLine3());
        sp.setString(4, toAdd.getPostalCode());
        sp.setInt(5,toAdd.getAddressTypeId());

        logger.log("{CALL pCreateAddress(?,?,?,?,?)}", LogType.QUERY);
        logger.log(toAdd, LogType.PARAMETERS);

        ResultSet rs = sp.executeQuery();

        Address address = convertResultSet(rs).get(0);

        logger.log(address, LogType.RESPONSE);
        logger.log("",LogType.COMPLETED);
        return address;
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
    public Address getById(int id) {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM Address WHERE AddressId = ?");
            get.setInt(1, id);
            ResultSet rs = get.executeQuery();

            logger.log("SELECT * FROM Address WHERE AddressId = ?", LogType.QUERY);
            logger.log(id, LogType.PARAMETERS);

            Address address = convertResultSet(rs).get(0);

            logger.log(address, LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);

            return address;
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return null;
    }

}
