package com.bbd.licenscerenewal.services;

import com.bbd.licenscerenewal.utils.logging.LogRequest;
import com.bbd.licenscerenewal.utils.logging.LogType;
import com.bbd.licenscerenewal.utils.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bbd.licenscerenewal.models.OwnerType;

@Service
public class OwnerTypeRepo {
    private Logger logger = new Logger(new LogRequest());

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    public List<OwnerType> getOwnerTypes() throws SQLException {
        Connection conn = null;
        try{
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM OwnerType");
            logger.log("SELECT * FROM OwnerType", LogType.QUERY);
            ResultSet rs = get.executeQuery();
            
            List<OwnerType> ownerTypes = new ArrayList<>();
            while(rs.next()){
                OwnerType ownerType = new OwnerType();
                ownerType.setOwnerTypeId(rs.getInt(1));
                ownerType.setName(rs.getString(2));
                ownerTypes.add(ownerType);
            }
            logger.log(ownerTypes,LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);
            return ownerTypes;
        } catch (SQLException exception) {
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }
}