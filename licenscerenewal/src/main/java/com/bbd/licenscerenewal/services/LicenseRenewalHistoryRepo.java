package com.bbd.licenscerenewal.services;

import com.bbd.licenscerenewal.models.NullObjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bbd.licenscerenewal.models.LicenseRenewalHistory;


@Service
public class LicenseRenewalHistoryRepo implements IRepository<LicenseRenewalHistory> {

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Autowired
    NullObjects nullObjects;

    @Override
    public LicenseRenewalHistory add(LicenseRenewalHistory toAdd) throws SQLException {
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
            throw  throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    public LicenseRenewalHistory updateRenewalAction(int id, int action) throws SQLException {
        Connection conn = null;
        try {
            conn = databaseService.getConnection();
            PreparedStatement update = conn.prepareStatement("UPDATE TABLE LicenseRenewalHistory SET RenewalActionId = ? WHERE LicenseRenewalHistoryId = ?");
            update.setInt(1, action);
            update.setInt(2, id);

            update.executeUpdate();

            PreparedStatement select = conn.prepareStatement("SELECT * FROM LicenseRenewalHistory WHERE icenseRenewalHistoryId = ?");
            select.setInt(1, id);

            ResultSet rs = select.executeQuery();
            return convertResultSet(rs).size() > 0 ? nullObjects.getLicenseRenewalHistory() : convertResultSet(rs).get(0);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
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
    public LicenseRenewalHistory getById(int id) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM LicenseRenewalHistory WHERE LicenseRenewalHistoryId = ?");
            get.setInt(1, id);

            ResultSet rs = get.executeQuery();
            return convertResultSet(rs).size() > 0? nullObjects.getLicenseRenewalHistory() : convertResultSet(rs).get(0);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    public List<LicenseRenewalHistory> getByLicenseId(int id) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM LicenseRenewalHistory WHERE LicenseId = ?");
            get.setInt(1, id);

            ResultSet rs = get.executeQuery();
            return convertResultSet(rs);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    // public LicenseRenewalHistory getLatestHistory(int id) {
    //     List<LicenseRenewalHistory> histories = getByLicenseId(id);
    //     return histories.get(histories.size() - 1);
    // }
}
