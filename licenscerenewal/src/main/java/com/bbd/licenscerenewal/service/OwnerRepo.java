package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class OwnerRepo implements IRepository<Owner>{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    OrganisationRepo organisationRepo;

    @Override
    public Owner update(Owner toUpdate) {
        // try {
        //     Connection conn = databaseService.getConnection();
        //     PreparedStatement update = conn.prepareStatement("");
        //     update.setString(1, "put shit here");


        //     update.executeUpdate();
        //     databaseService.ReleaseConnection(conn);
        //     return toUpdate;
        // } catch (SQLException throwable) {
        //     throwable.printStackTrace();
        // }
        return null;
    }

    @Override
    public Owner delete(int id) {
        // try {
        //     Connection conn  = databaseService.getConnection();
        //     PreparedStatement select  = conn.prepareStatement("");
        //     select.setInt(1, id);

        //     PreparedStatement delete = conn.prepareStatement("");
        //     delete.setInt(1, id);

        //     ResultSet rs = select.executeQuery();
        //     delete.executeQuery();
        //     databaseService.ReleaseConnection(conn);
        //     return convertResultSet(rs).get(0);
        // } catch (SQLException throwable) {
        //     throwable.printStackTrace();
        // }
        return null;
    }

    @Override
    public Owner add(Owner toAdd) {
        // try {
        //     Connection conn = databaseService.getConnection();
        //     PreparedStatement insert = conn.prepareStatement("", Statement.RETURN_GENERATED_KEYS);
        //     insert.setString(1, "Put shit here");


        //     insert.executeUpdate();
        //     toAdd.setOwnerId(insert.getGeneratedKeys().getInt(0));
        //     databaseService.ReleaseConnection(conn);
        //     return toAdd;
        // } catch (SQLException throwable) {
        //     throwable.printStackTrace();
        // }
        return null;
    }

    @Override
    public List<Owner> convertResultSet(ResultSet toConvert) throws SQLException {
        List<Owner> owners = new ArrayList<Owner>();
        while(toConvert.next())
        {
            Owner temp = new Owner();
            temp.setOwnerId(toConvert.getInt(1));
            temp.setIdNumber(toConvert.getString(2));
            temp.setIdType(toConvert.getString(3));
            temp.setCountryOfIssue(toConvert.getString(4));
            temp.setOrganisationName(toConvert.getString(5));
            temp.setSurname(toConvert.getString(6));
            temp.setInitials(toConvert.getString(7));
            temp.setFirstName(toConvert.getString(8));
            temp.setMiddleName(toConvert.getString(9));
            temp.setEmailAddress(toConvert.getString(10));
            temp.setHomeTel(toConvert.getString(11));
            temp.setWorkTel(toConvert.getString(12));
            temp.setCellphoneNumber(toConvert.getString(13));
            temp.setFaxNumber(toConvert.getString(14));
            temp.setPostalAddress(addressRepo.getById(toConvert.getInt(15)));
            temp.setStreetAddress(addressRepo.getById(toConvert.getInt(16)));
            temp.setChosenAddress(toConvert.getString(17));

            owners.add(temp);
        }
        return owners;
    }

    @Override
    public Owner getById(int id) {
        // try {
        //     Connection conn  = databaseService.getConnection();
        //     PreparedStatement get  = conn.prepareStatement("");
        //     get.setInt(1, id);

        //     ResultSet rs = get.executeQuery();
        //     Owner owner = convertResultSet(rs).get(0);
        //     databaseService.ReleaseConnection(conn);
        //     return owner;

        // } catch (SQLException throwable) {
        //     throwable.printStackTrace();
        // }
        return null;
    }
}
