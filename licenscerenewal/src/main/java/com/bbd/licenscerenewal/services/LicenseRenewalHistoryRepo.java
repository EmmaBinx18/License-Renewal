package com.bbd.licenscerenewal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

import com.bbd.licenscerenewal.models.LicenseRenewalHistory;
@Service
public class LicenseRenewalHistoryRepo implements IRepository<LicenseRenewalHistory> {

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Override
    public LicenseRenewalHistory update(LicenseRenewalHistory toUpdate) {
        Connection conn = null;
        try {
            conn = databaseService.getConnection();
            PreparedStatement update = conn.prepareStatement("UPDATE TABLE LicenseRenewalHistory SET LicenseId = ?,RenewalDate = ?,Fee = ?,RenewalActionId = ? WHERE LicenseRenewalHistoryId = ?");
            update.setInt(1, toUpdate.getLicenseId());
            update.setDate(2, toUpdate.getRenewalDate());
            update.setDouble(3, toUpdate.getFee());
            update.setInt(4, toUpdate.getRenewalActionId());
            update.setInt(5, toUpdate.getLicenseRenewalHistoryId());

            update.executeUpdate();
            return toUpdate;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return null;
    }

    @Override
    public LicenseRenewalHistory delete(int id) {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement select  = conn.prepareStatement("SELECT * FROM LicenseRenewalHistory WHERE LicenseRenewalHistoryId = ? ");
            select.setInt(1, id);

            PreparedStatement delete = conn.prepareStatement("DELETE FROM LicenseRenewalHistory WHERE LicenseRenewalHistoryId = ?");
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
    public LicenseRenewalHistory add(LicenseRenewalHistory toAdd) {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement insert  = conn.prepareStatement("INSERT INTO LicenseRenewalHistory VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            insert.setInt(1, toAdd.getLicenseId());
            insert.setDate(2, toAdd.getRenewalDate());
            insert.setDouble(3, toAdd.getFee());
            insert.setInt(4, toAdd.getRenewalActionId());

            insert.executeUpdate();
            toAdd.setLicenseRenewalHistoryId(insert.getGeneratedKeys().getInt(1));

            return toAdd;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return null;
    }

    @Override
    public List<LicenseRenewalHistory> convertResultSet(ResultSet toConvert) throws SQLException {
        List<LicenseRenewalHistory> histories = new ArrayList<>();

        while(toConvert.next()){
            LicenseRenewalHistory history = new LicenseRenewalHistory();
            history.setLicenseRenewalHistoryId(toConvert.getInt(1));
            history.setLicenseId(toConvert.getInt(2));
            history.setRenewalDate(toConvert.getDate(3));
            history.setFee(toConvert.getFloat(4));
            history.setRenewalActionId(toConvert.getInt(5));

            histories.add(history);
        }

        return histories;
    }

    @Override
    public LicenseRenewalHistory getById(int id) {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM LicenseRenewalHistory WHERE LicenseRenewalHistoryId = ?");
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

    public List<LicenseRenewalHistory> getByLicenseId(int id) {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM LicenseRenewalHistory WHERE LicenseId = ?");
            get.setInt(1, id);
            ResultSet rs = get.executeQuery();

            return convertResultSet(rs);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return new ArrayList<>();
    }
}
