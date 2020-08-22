package com.bbd.licenscerenewal.models;

import com.bbd.licenscerenewal.services.OnCreate;
import com.bbd.licenscerenewal.services.OnUpdate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class LicenseType{
    @NotNull(groups = OnUpdate.class, message = "License type ID null on update")
    @Null(groups = OnCreate.class, message = "License type ID not null on create")
    private int licenseTypeId;
    @NotNull(message = "Name cannot be null")
    private String name;

    public int getLicenseTypeId() {
        return licenseTypeId;
    }

    public void setLicenseTypeId(int licenseTypeId){
        this.licenseTypeId = licenseTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}