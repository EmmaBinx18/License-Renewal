package com.bbd.licenscerenewal.service;

public interface IRepository<T> {
    public T update(T toUpdate);
    public T delete(T toDelete);
    public T add(T toAdd);
}
