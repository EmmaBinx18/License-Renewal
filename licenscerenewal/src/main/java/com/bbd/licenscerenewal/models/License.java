package com.bbd.licenscerenewal.models;

import java.sql.Date;

public class License{
    private String licenseNumber;
    private Owner owner;
    private Date firstIssueDate;
    private Date expiryDate;
    private Vehicle vehicle;
    private String licenseStatus;
    private String licenseType;

    public String getLicenseNumber(){
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber){
        this.licenseNumber = licenseNumber;
    }

    public Owner getOwner(){
        return owner;
    }

    public void setOwner(Owner owner){
        this.owner = owner;
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

    public Vehicle getVehicle(){
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    public String getLicenseStatus(){
        return licenseStatus;
    }

    public void setLicenseStatus(String licenseStatus){
        this.licenseStatus = licenseStatus;
    }

    public String getLicenseType(){
        return licenseType;
    }

    public void setLicenseType(String licenseType){
        this.licenseType = licenseType;
    }
}