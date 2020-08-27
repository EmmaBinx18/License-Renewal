package com.bbd.licenscerenewal.services;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bbd.licenscerenewal.models.LicenseStatus;
import com.bbd.licenscerenewal.utils.logging.LogSQL;
import com.bbd.licenscerenewal.utils.logging.LogType;
import com.bbd.licenscerenewal.utils.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LicenseStatusRepo {

    Logger logger = new Logger(new LogSQL());

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    public List<LicenseStatus> getLicenseStatuses() throws SQLException {
        Connection conn = null;
        try{
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM LicenseStatus");
            logger.log("SELECT * FROM LicenseStatus", LogType.QUERY);
            ResultSet rs = get.executeQuery();
            
            List<LicenseStatus> licenseStatuses = new ArrayList<>();
            while(rs.next()){
                LicenseStatus licenseStatus = new LicenseStatus();
                licenseStatus.setLicenseStatusId(rs.getInt(1));
                licenseStatus.setName(rs.getString(2));
                licenseStatuses.add(licenseStatus);
            }

            logger.log(licenseStatuses, LogType.RESPONSE);
            logger.log("", LogType.COMPLETED);

            return licenseStatuses;
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }
}
