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

    Logger logger = new Logger(new LogSQL());

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    public List<LicenseType> getLicenseTypes() throws SQLException {
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

            logger.log("SELECT * FROM LicenseType", LogType.QUERY);

            logger.log(licenseTypes,LogType.RESPONSE);

            logger.log("",LogType.COMPLETED);

            return licenseTypes;
        } catch (SQLException exception) {
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }
}
