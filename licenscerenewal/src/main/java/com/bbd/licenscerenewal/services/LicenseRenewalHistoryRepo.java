package com.bbd.licenscerenewal.services;

import com.bbd.licenscerenewal.models.License;
import com.bbd.licenscerenewal.models.NullObjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bbd.licenscerenewal.models.LicenseRenewalHistory;


@Service
public class LicenseRenewalHistoryRepo {

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Autowired
    NullObjects nullObjects;

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

    public LicenseRenewalHistory getLatest(int id) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            CallableStatement sp = conn.prepareCall("{CALL pGetLatestLicenseAction(?)}");
            sp.setInt(1, id);

            ResultSet rs = sp.executeQuery();
            List<LicenseRenewalHistory> list = convertResultSet(rs);
            return list.isEmpty() ?   nullObjects.getLicenseRenewalHistory():list.get(0);
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    public List<LicenseRenewalHistory> getByLicenseId(int id) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            CallableStatement sp = conn.prepareCall("{CALL pGetLicenseRenwalHistory(?)}");
            sp.setInt(1, id);

            ResultSet rs = sp.executeQuery();
            return convertResultSet(rs);
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }
}
