package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.Renewal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RenewalRepo implements IRepository<Renewal>{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Override
    public Renewal update(Renewal toUpdate) {
        return null;
    }

    @Override
    public Renewal delete(Renewal toDelete) {
        return null;
    }

    @Override
    public Renewal add(Renewal toAdd) {
        return null;
    }

    @Override
    public List<Renewal> convertResultSet(ResultSet toConvert) throws SQLException {
        return null;
    }
}
