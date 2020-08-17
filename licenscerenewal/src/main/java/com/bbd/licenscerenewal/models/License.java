package com.bbd.licenscerenewal.models;

import java.sql.Date;

public class License{
    private int licenseId;
    private String licenseNumber;
    private int ownerId;
    private Date firstIssueDate;
    private Date expiryDate;
    private int vehicleId;
    private int licenseStatusId;
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