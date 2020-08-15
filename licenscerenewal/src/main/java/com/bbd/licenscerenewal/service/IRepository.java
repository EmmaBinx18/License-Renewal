package com.bbd.licenscerenewal.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IRepository<T> {
    public T update(T toUpdate);
    public T delete(T toDelete);
    public T add(T toAdd);
    public List<T> convertResultSet(ResultSet toConvert) throws SQLException;
    public T get(int Id);
}
