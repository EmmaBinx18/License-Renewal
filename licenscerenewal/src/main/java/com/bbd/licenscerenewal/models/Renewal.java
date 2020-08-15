package com.bbd.licenscerenewal.models;

public class Renewal{
    private Owner owner;
    private Organisation organisation;
    private Vehicle vehicle;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner){
        this.owner = owner;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation){
        this.organisation = organisation;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
    }
}