package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.LicenseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class LicenseStatusRepo{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Autowired
    LicenseStatusRepo licenseStatusRepo;

    public List<LicenseStatus> getLicenseStatuses() {
        List<LicenseStatus> licenseStatuses = new ArrayList<LicenseStatus>();

        while(toConvert.next()){
            LicenseStatus licenseStatus = new LicenseStatus();
            licenseStatus.setLicenseStatusId(toConvert.getInt(1));
            licenseStatus.setName(toConvert.getString(2));
            licenseStatuses.add(licenseStatus);
        }

        return licenseStatuses;
    }
}
