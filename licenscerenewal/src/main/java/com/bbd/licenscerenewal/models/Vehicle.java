package com.bbd.licenscerenewal.models;

public class Vehicle{
    private int vehicleId;
    private String registerNumber;
    private String vin;
    private String make;
    private String model;
    private int odometer;
    private int vehicleTypeId;

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId){
        this.vehicleId = vehicleId;
    }

    public String getRegisterNumber(){
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber){
        this.registerNumber = registerNumber;
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

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId){
        this.vehicleTypeId = vehicleTypeId;
    }
}