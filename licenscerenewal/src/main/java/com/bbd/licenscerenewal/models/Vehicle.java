package com.bbd.licenscerenewal.models;

import com.bbd.licenscerenewal.services.OnCreate;
import com.bbd.licenscerenewal.services.OnUpdate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class Vehicle{
    @Null(groups = OnCreate.class, message = "Vehicle Id set on create")
    @NotNull(groups = OnUpdate.class, message = "Vehicle Id not set on update")
    private int vehicleId;
    @Size(min = 0 , max = 8,message = "Invalid registration number")
    private String registrationNumber;
    @Size(min = 0,max = 17, message = "Invalid VIN number")
    private String vin;
    @NotNull(message = "Make cannot be null")
    private String make;
    @NotNull(message = "Model cannot be null")
    private String model;
    @Min(value = 0,message = "Invalid odometer less than zero")
    @NotNull(message = "Odometer cannot be null")
    private int odometer;
    @NotNull(message = "Vehicle Type ID cannot be null")
    private int vehicleTypeId;

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId){
        this.vehicleId = vehicleId;
    }

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

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId){
        this.vehicleTypeId = vehicleTypeId;
    }
}