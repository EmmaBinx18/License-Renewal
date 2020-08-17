package com.bbd.licenscerenewal.services;

import com.bbd.licenscerenewal.models.IdentificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class IdentificationTypeRepo {

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    public List<IdentificationType> getIdentificationTypes() {
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

            return identificationTypes;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return new ArrayList<>();
    }
}