package com.bbd.licenscerenewal.services;

import com.bbd.licenscerenewal.models.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class AddressRepo implements IRepository<Address>{

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


            update.executeUpdate();
            return toUpdate;
        } catch (SQLException throwable) {
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

            PreparedStatement delete = conn.prepareStatement("DELETE FROM Address WHERE AddressId = ?");
            delete.setInt(1, id);

            ResultSet rs = select.executeQuery();
            delete.executeQuery();
            return convertResultSet(rs).get(0);
        } catch (SQLException throwable) {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return null;
    }

    public Address insert(Address toAdd, Connection conn) throws SQLException {
        PreparedStatement insert = conn.prepareStatement("EXEC pCreateAddress ?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        insert.setString(1, toAdd.getAddressLine1());
        insert.setString(2, toAdd.getAddressLine2());
        insert.setString(3, toAdd.getAddressLine3());
        insert.setString(4, toAdd.getPostalCode());
        insert.setInt(5,toAdd.getAddressTypeId());

        insert.executeUpdate();
        toAdd.setAddressId(insert.getGeneratedKeys().getInt(1));

        return toAdd;
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

            return convertResultSet(rs).get(0);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return null;
    }

}
