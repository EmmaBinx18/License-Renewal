package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.IdentificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class IdentificationTypeRepo {

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    public List<IdentificationType> getIdentificationTypes() {
        try{
            Connection conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM IdentificationType");
            ResultSet rs = get.executeQuery();
            databaseService.ReleaseConnection(conn);
            
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
        }
        return new ArrayList<>();
    }
}