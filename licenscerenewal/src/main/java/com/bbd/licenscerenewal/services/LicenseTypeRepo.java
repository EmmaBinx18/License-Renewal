package com.bbd.licenscerenewal.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bbd.licenscerenewal.models.LicenseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LicenseTypeRepo{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    public List<LicenseType> getLicenseTypes() {
        Connection conn = null;
        try{
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM LicenseType");
            ResultSet rs = get.executeQuery();
            
            List<LicenseType> licenseTypes = new ArrayList<>();
            while(rs.next()){
                LicenseType licenseType = new LicenseType();
                licenseType.setLicenseTypeId(rs.getInt(1));
                licenseType.setName(rs.getString(2));
                licenseTypes.add(licenseType);
            }

            return licenseTypes;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return new ArrayList<>();
    }
}
