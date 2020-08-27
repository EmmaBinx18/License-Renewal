package com.bbd.licenscerenewal.models;

import com.bbd.licenscerenewal.services.OnCreate;
import com.bbd.licenscerenewal.services.OnUpdate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class VehicleType{
    @NotNull(groups = OnUpdate.class,message = "Vehicle type ID null on update")
    @Null(groups = OnCreate.class, message = "Vehiicle type ID not null on create")
    private int vehicleTypeId;
    @Null(message = "Name cannot be null")
    private String name;

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId){
        this.vehicleTypeId = vehicleTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}