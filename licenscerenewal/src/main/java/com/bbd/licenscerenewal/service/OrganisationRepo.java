package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Service
public class OrganisationRepo implements IRepository<Organisation>{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Override
    public Organisation update(Organisation toUpdate) {
        return null;
    }

    @Override
    public Organisation delete(int toDelete) {
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

    @Override
    public Organisation getById(int Id) {
        return null;
    }
}
