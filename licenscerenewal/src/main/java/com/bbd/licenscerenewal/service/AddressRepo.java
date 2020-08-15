package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class AddressRepo implements IRepository<Address>{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Override
    public Address update(Address toUpdate) {
        try {
            Connection conn = databaseService.getConnection();
            PreparedStatement update = conn.prepareStatement("UPDATE TABLE Address SET AddressLine1 = ?,AddressLine2 = ?AddressLine3 = ? ,PostalCode = ? WHERE AddressId = ?");
            update.setString(1, toUpdate.getAddressLine1());
            update.setString(2, toUpdate.getAddressLine2());
            update.setString(3, toUpdate.getAddressLine3());
            update.setString(4, toUpdate.getPostalCode());
            update.setInt(5, toUpdate.getAddressId());

            update.executeUpdate();
            databaseService.ReleaseConnection(conn);
            return toUpdate;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Address delete(int id) {
        try {
            Connection conn  = databaseService.getConnection();
            PreparedStatement select  = conn.prepareStatement("SELECT * FROM Address WHERE AddressID = ? ");
            select.setInt(1, id);

            PreparedStatement delete = conn.prepareStatement("DELETE FROM Address WHERE AddressID = ?");
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
    public Address add(Address toAdd) {
        try {
            Connection conn = databaseService.getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO Address VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, toAdd.getAddressLine1());
            insert.setString(2, toAdd.getAddressLine2());
            insert.setString(3, toAdd.getAddressLine3());
            insert.setString(4, toAdd.getPostalCode());

            insert.executeUpdate();
            toAdd.setAddressId(insert.getGeneratedKeys().getInt(0));
            databaseService.ReleaseConnection(conn);
            return toAdd;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public List<Address> convertResultSet(ResultSet toConvert) throws SQLException {
        List<Address> addresses = new ArrayList<Address>();

        while(toConvert.next()){
        Address temp = new Address();
        temp.setAddressId(toConvert.getInt(1));
        temp.setAddressLine1(toConvert.getString(2));
        temp.setAddressLine2(toConvert.getString(3));
        temp.setAddressLine3(toConvert.getString(4));
        temp.setPostalCode(toConvert.getString(5));

        addresses.add(temp);
        }

        return addresses;
    }

    @Override
    public Address get(int id) {
        try {
            Connection conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM Address WHERE AddressId = ?");
            get.setInt(1, id);

            ResultSet rs = get.executeQuery();
            Address address = convertResultSet(rs).get(0);
            databaseService.ReleaseConnection(conn);
            return address;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
