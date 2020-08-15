package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class LicenseRepo implements IRepository<License> {

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Override
    public License update(License toUpdate) {
        try {
            Connection conn = databaseService.getConnection();
            PreparedStatement update = conn.prepareStatement("UPDATE TABLE License SET LicenseNumber = ?,OwnerId = ?,FirstIssueDate = ? ,ExpiryDate = ?, VehicleId = ?, LicenseStatusId = ?, LicenseTypeId = ? WHERE LicenseId = ?");
            update.setString(1, toUpdate.getLicenseNumber());
            update.setInt(2, toUpdate.getOwnerId());
            update.setDate(3, toUpdate.getFirstIssueDate());
            update.setDate(4, toUpdate.getExpiryDate());
            update.setInt(5, toUpdate.getVehicleId());
            update.setInt(6, toUpdate.getLicenseStatusId());
            update.setInt(7, toUpdate.getLicenseTypeId());

            update.executeUpdate();
            databaseService.ReleaseConnection(conn);
            return toUpdate;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public License delete(int id) {
        try {
            Connection conn  = databaseService.getConnection();
            PreparedStatement select  = conn.prepareStatement("SELECT * FROM License WHERE LicenseId = ? ");
            select.setInt(1, id);

            PreparedStatement delete = conn.prepareStatement("DELETE FROM License WHERE LicenseId = ?");
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
    public License add(License toAdd) {
        try {
            Connection conn  = databaseService.getConnection();
            PreparedStatement insert  = conn.prepareStatement("INSERT INTO License VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, toAdd.getLicenseNumber());
            insert.setInt(2, toAdd.getOwnerId());
            insert.setDate(3, toAdd.getFirstIssueDate());
            insert.setDate(4, toAdd.getExpiryDate());
            insert.setInt(5, toAdd.getVehicleId());
            insert.setInt(6, toAdd.getLicenseStatusId());
            insert.setInt(7, toAdd.getLicenseTypeId());

            insert.executeUpdate();
            toAdd.setLicenseId(insert.getGeneratedKeys().getInt(1));
            databaseService.ReleaseConnection(conn);
            return toAdd;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public List<License> convertResultSet(ResultSet toConvert) throws SQLException {
        List<License> licenses = new ArrayList<>();

        while(toConvert.next()){
            License license = new License();
            license.setLicenseId(toConvert.getInt(1));
            license.setLicenseNumber(toConvert.getString(2));
            license.setFirstIssueDate(toConvert.getDate(3));
            license.setExpiryDate(toConvert.getDate(4));
            license.setVehicleId(toConvert.getInt(5));
            license.setLicenseStatusId(toConvert.getInt(6));
            license.setLicenseTypeId(toConvert.getInt(7));
            licenses.add(license);
        }

        return licenses;
    }

    public List<License> getAll() {
        try {
            Connection conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM License");
            ResultSet rs = get.executeQuery();
            databaseService.ReleaseConnection(conn);
            return convertResultSet(rs);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public License getById(int id) {
        try {
            Connection conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM License WHERE LicenseId = ?");
            get.setInt(1, id);

            ResultSet rs = get.executeQuery();
            databaseService.ReleaseConnection(conn);
            return convertResultSet(rs).get(0);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
