package com.bbd.licenscerenewal.models;

import com.bbd.licenscerenewal.services.OnCreate;
import com.bbd.licenscerenewal.services.OnUpdate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.Date;


public class License{
    @Min(0)
    @NotNull(groups = OnUpdate.class, message = "License ID not set on update")
    @Null(groups = OnCreate.class,message =  "License ID set on create")
    private int licenseId;
    private String licenseNumber;
    @Min(value = 0, message = "Owner ID less than zero")
    private int ownerId;
    private Date firstIssueDate;
    private Date expiryDate;
    @Min(value = 0, message = "Vehicle ID less than zero")
    private int vehicleId;
    @Min(value = 0, message = "license status ID less than zero")
    private int licenseStatusId;
    @Min(value = 0, message = "License type ID less than zero")
    private int licenseTypeId;

    public int getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(int licenseId) {
        this.licenseId = licenseId;
    }

    public String getLicenseNumber(){
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber){
        this.licenseNumber = licenseNumber;
    }

    public int getOwnerId(){
        return ownerId;
    }

    public void setOwnerId(int ownerId){
        this.ownerId = ownerId;
    }

    public Date getFirstIssueDate(){
        return firstIssueDate;
    }

    public void setFirstIssueDate(Date firstIssueDate){
        this.firstIssueDate = firstIssueDate;
    }

    public Date getExpiryDate(){
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate){
        this.expiryDate = expiryDate;
    }

    public int getVehicleId(){
        return vehicleId;
    }

    public void setVehicleId(int vehicleId){
        this.vehicleId = vehicleId;
    }

    public int getLicenseStatusId(){
        return licenseStatusId;
    }

    public void setLicenseStatusId(int licenseStatusId){
        this.licenseStatusId = licenseStatusId;
    }

    public int getLicenseTypeId(){
        return licenseTypeId;
    }

    public void setLicenseTypeId(int licenseTypeId){
        this.licenseTypeId = licenseTypeId;
    }
}