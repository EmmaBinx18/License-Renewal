package com.bbd.licenscerenewal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Service
public class LicenseRenewalHistoryRepo implements IRepository<LicenseRenewalHistoryRepo> {

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Override
    public LicenseRenewalHistoryRepo update(LicenseRenewalHistoryRepo toUpdate) {
        return null;
    }

    @Override
    public LicenseRenewalHistoryRepo delete(LicenseRenewalHistoryRepo toDelete) {
        return null;
    }

    @Override
    public LicenseRenewalHistoryRepo add(LicenseRenewalHistoryRepo toAdd) {
        return null;
    }

    @Override
    public List<LicenseRenewalHistoryRepo> convertResultSet(ResultSet toConvert) throws SQLException {
        return null;
    }

    @Override
    public LicenseRenewalHistoryRepo get(int Id) {
        return null;
    }
}
