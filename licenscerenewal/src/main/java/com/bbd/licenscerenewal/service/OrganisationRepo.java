package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrganisationRepo implements IRepository<Organisation>{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Override
    public Organisation update(Organisation toUpdate) {
        return null;
    }

    @Override
    public Organisation delete(Organisation toDelete) {
        return null;
    }

    @Override
    public Organisation add(Organisation toAdd) {
        return null;
    }

    @Override
    public List<Organisation> convertResultSet(ResultSet toConvert) throws SQLException {
        return null;
    }
}
