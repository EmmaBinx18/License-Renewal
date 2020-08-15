package com.bbd.licenscerenewal.service;


import com.bbd.licenscerenewal.models.LicenseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LicenseStatusRepo implements IRepository<LicenseStatus>{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Autowired
    LicenseStatusRepo licenseStatusRepo;

    @Override
    public LicenseStatus update(LicenseStatus toUpdate) {
        return null;
    }

    @Override
    public LicenseStatus delete(int id) {
        return null;
    }

    @Override
    public LicenseStatus add(LicenseStatus toAdd) {
        return null;
    }

    @Override
    public List<LicenseStatus> convertResultSet(ResultSet toConvert) throws SQLException {
        List<LicenseStatus> licenseStatuses = new ArrayList<LicenseStatus>();

        while(toConvert.next()){
            LicenseStatus licenseStatus = new LicenseStatus();
            licenseStatus.setLicenseStatusId(toConvert.getInt(1));
            licenseStatus.setName(toConvert.getString(2));
            licenseStatuses.add(licenseStatus);
        }

        return licenseStatuses;
    }

    @Override
    public LicenseStatus getById(int id) {
        return null;
    }
}
