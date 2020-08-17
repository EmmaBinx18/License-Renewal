package com.bbd.licenscerenewal.service;


import com.bbd.licenscerenewal.models.OwnerType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class OwnerTypeRepo {

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    public List<OwnerType> getOwnerTypes() {
        Connection conn = null;
        try{
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM OwnerType");
            ResultSet rs = get.executeQuery();
            
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
        } finally {
            databaseService.releaseConnection(conn);
        }
        return new ArrayList<>();
    }
}