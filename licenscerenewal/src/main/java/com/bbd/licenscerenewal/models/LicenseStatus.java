package com.bbd.licenscerenewal.models;

import com.bbd.licenscerenewal.services.OnCreate;
import com.bbd.licenscerenewal.services.OnUpdate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class LicenseStatus{
    @NotNull(groups = OnUpdate.class , message = "License status ID null on update")
    @Null(groups = OnCreate.class , message = "License status ID not null on create")
    private int licenseStatusId;
    @NotNull(message = "Name cannot be null")
    private String name;

    public int getLicenseStatusId() {
        return licenseStatusId;
    }

    public void setLicenseStatusId(int licenseStatusId){
        this.licenseStatusId = licenseStatusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}