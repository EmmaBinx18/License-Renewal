package com.bbd.licenscerenewal.service;


import com.bbd.licenscerenewal.models.LicenseStatus;
import com.bbd.licenscerenewal.models.OwnerType;

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
public class OwnerTypeRepo {

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    public List<OwnerType> getOwnerTypes() {
        try{
            Connection conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM OwnerType");
            ResultSet rs = get.executeQuery();
            databaseService.ReleaseConnection(conn);
            
            List<OwnerType> ownerTypes = new ArrayList<>();
            while(rs.next()){
                OwnerType ownerType = new OwnerType();
                ownerType.setOwnerTypeId(rs.getInt(1));
                ownerType.setName(rs.getString(2));
                ownerTypes.add(ownerType);
            }

            return ownerTypes;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return new ArrayList<>();
    }
}