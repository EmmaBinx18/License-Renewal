package com.bbd.licenscerenewal.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bbd.licenscerenewal.models.IdentificationType;
import com.bbd.licenscerenewal.utils.logging.LogSQL;
import com.bbd.licenscerenewal.utils.logging.LogType;
import com.bbd.licenscerenewal.utils.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class IdentificationTypeRepo {

    Logger logger = new Logger(new LogSQL());

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    public List<IdentificationType> getIdentificationTypes() throws SQLException {
        Connection conn = null;
        try{
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM IdentificationType");
            ResultSet rs = get.executeQuery();
            databaseService.releaseConnection(conn);
            
            List<IdentificationType> identificationTypes = new ArrayList<>();
            while(rs.next()){
                IdentificationType identificationType = new IdentificationType();
                identificationType.setIdentificationTypeId(rs.getInt(1));
                identificationType.setName(rs.getString(2));
                identificationTypes.add(identificationType);
            }

            logger.log("SELECT * FROM IdentificationType", LogType.QUERY);

            logger.log(identificationTypes,LogType.RESPONSE);
            
            logger.log("",LogType.COMPLETED);

            return identificationTypes;
        } catch (SQLException exception) {
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }
}