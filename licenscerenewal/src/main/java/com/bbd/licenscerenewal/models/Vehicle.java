package com.bbd.licenscerenewal.models;

public class Vehicle{
    private String registrationNumber;
    private String vin;
    private String make;
    private String model;
    private int odometer;
    private String licenseType;

    public String getRegistrationNumber(){
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber){
        this.registrationNumber = registrationNumber;
    }

    public String getVin(){
        return vin;
    }

    public void setVin(String vin){
        this.vin = vin;
    }

    public String getMake(){
        return make;
    }

    public void setMake(String make){
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model){
        this.model = model;
    }

    public int getOdometer(){
        return odometer;
    }

    public void setOdometer(int odometer){
        this.odometer = odometer;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType){
        this.licenseType = licenseType;
    }
}