package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.LicenseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LicenseTypeRepo implements IRepository<LicenseType>{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Autowired
    LicenseTypeRepo licenseTypeRepo;


    @Override
    public LicenseType update(LicenseType toUpdate) {
        return null;
    }

    @Override
    public LicenseType delete(int id) {
        return null;
    }

    @Override
    public LicenseType add(LicenseType toAdd) {
        return null;
    }

    @Override
    public List<LicenseType> convertResultSet(ResultSet toConvert) throws SQLException {
        List<LicenseType> licenseTypes = new ArrayList<LicenseType>();

        while(toConvert.next()){
            LicenseType licenseType = new LicenseType();
            licenseType.setLicenseTypeId(toConvert.getInt(1));
            licenseType.setName(toConvert.getString(2));
            licenseTypes.add(licenseType);
        }

        return licenseTypes;
    }

    @Override
    public LicenseType getById(int id) {
        return null;
    }
}
