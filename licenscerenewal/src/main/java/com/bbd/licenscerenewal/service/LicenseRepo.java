package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LicenseRepo implements IRepository<License> {

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Override
    public License update(License toUpdate) {
        return null;
    }

    @Override
    public License delete(License toDelete) {
        return null;
    }

    @Override
    public License add(License toAdd) {
        return null;
    }

    @Override
    public List<License> convertResultSet(ResultSet toConvert) throws SQLException {
        return null;
    }
}
