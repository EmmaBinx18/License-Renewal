package com.bbd.licenscerenewal.services;

import com.bbd.licenscerenewal.utils.logging.LogSQL;
import com.bbd.licenscerenewal.utils.logging.LogType;
import com.bbd.licenscerenewal.utils.logging.Logger;
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

    Logger logger = new Logger(new LogSQL());

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Override
    public LicenseRenewalHistory add(LicenseRenewalHistory toAdd) {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement insert  = conn.prepareStatement("INSERT INTO LicenseRenewalHistory VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            logger.log("INSERT INTO LicenseRenewalHistory VALUES(?,?,?,?)", LogType.QUERY);
            insert.setInt(1, toAdd.getLicenseId());
            insert.setDate(2, toAdd.getRenewalDate());
            insert.setDouble(3, toAdd.getFee());
            insert.setInt(4, toAdd.getRenewalActionId());

            logger.log(toAdd,LogType.PARAMETERS);

            insert.executeUpdate();
            toAdd.setLicenseRenewalHistoryId(insert.getGeneratedKeys().getInt(1));

            logger.log("",LogType.COMPLETED);

            return toAdd;
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return null;
    }

    public LicenseRenewalHistory updateRenewalAction(int id, int action) {
        Connection conn = null;
        try {
            conn = databaseService.getConnection();
            PreparedStatement update = conn.prepareStatement("UPDATE TABLE LicenseRenewalHistory SET RenewalActionId = ? WHERE LicenseRenewalHistoryId = ?");
            logger.log("UPDATE TABLE LicenseRenewalHistory SET RenewalActionId = ? WHERE LicenseRenewalHistoryId = ?", LogType.QUERY);
            update.setInt(1, action);
            update.setInt(2, id);

            logger.log(id,LogType.PARAMETERS);
            logger.log(action,LogType.PARAMETERS);

            update.executeUpdate();

            PreparedStatement select = conn.prepareStatement("SELECT * FROM LicenseRenewalHistory WHERE LicenseRenewalHistoryId = ?");
            logger.log("SELECT * FROM LicenseRenewalHistory WHERE LicenseRenewalHistoryId = ?", LogType.QUERY);
            select.setInt(1, id);

            ResultSet rs = select.executeQuery();
            LicenseRenewalHistory licenseRenewalHistory = convertResultSet(rs).get(0);

            logger.log(licenseRenewalHistory,LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);

            return licenseRenewalHistory;
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
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
            logger.log("SELECT * FROM LicenseRenewalHistory WHERE LicenseRenewalHistoryId = ?",LogType.QUERY);
            get.setInt(1, id);
            logger.log(id,LogType.PARAMETERS);
            ResultSet rs = get.executeQuery();

            LicenseRenewalHistory licenseRenewalHistory = convertResultSet(rs).get(0);
            logger.log(licenseRenewalHistory, LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);
            return licenseRenewalHistory;
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
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

            logger.log("SELECT * FROM LicenseRenewalHistory WHERE LicenseId = ?", LogType.QUERY);
            logger.log(id, LogType.PARAMETERS);

            ResultSet rs = get.executeQuery();

            List<LicenseRenewalHistory> licenseRenewalHistories = convertResultSet(rs);
            logger.log(licenseRenewalHistories, LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);
            return licenseRenewalHistories;
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return new ArrayList<>();
    }

    // public LicenseRenewalHistory getLatestHistory(int id) {
    //     List<LicenseRenewalHistory> histories = getByLicenseId(id);
    //     return histories.get(histories.size() - 1);
    // }
}
