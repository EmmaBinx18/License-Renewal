package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.Renewal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Service
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

    @Override
    public Renewal get(int Id) {
        return null;
    }
}
