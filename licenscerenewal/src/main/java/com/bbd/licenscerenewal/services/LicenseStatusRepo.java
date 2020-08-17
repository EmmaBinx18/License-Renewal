package com.bbd.licenscerenewal.services;


import com.bbd.licenscerenewal.models.LicenseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class LicenseStatusRepo {

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    public List<LicenseStatus> getLicenseStatuses() {
        Connection conn = null;
        try{
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM LicenseStatus");
            ResultSet rs = get.executeQuery();
            
            List<LicenseStatus> licenseStatuses = new ArrayList<>();
            while(rs.next()){
                LicenseStatus licenseStatus = new LicenseStatus();
                licenseStatus.setLicenseStatusId(rs.getInt(1));
                licenseStatus.setName(rs.getString(2));
                licenseStatuses.add(licenseStatus);
            }

            return licenseStatuses;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return new ArrayList<>();
    }
}
