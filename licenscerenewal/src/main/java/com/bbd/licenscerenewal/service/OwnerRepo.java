package com.bbd.licenscerenewal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Service
public class OwnerRepo implements IRepository<OwnerRepo>{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Autowired
    AddressRepo addressRepo;

    @Override
    public OwnerRepo update(OwnerRepo toUpdate) {
        return null;
    }

    @Override
    public OwnerRepo delete(OwnerRepo toDelete) {
        return null;
    }

    @Override
    public OwnerRepo add(OwnerRepo toAdd) {
        return null;
    }

    @Override
    public List<OwnerRepo> convertResultSet(ResultSet toConvert) throws SQLException {
        return null;
    }

    @Override
    public OwnerRepo get(int Id) {
        return null;
    }
}
