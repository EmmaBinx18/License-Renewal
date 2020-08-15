package com.bbd.licenscerenewal.models;

public class Address{
    private int AddressId;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String postalCode;

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1){
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2){
        this.addressLine2 = addressLine2;
    }

    public int getAddressId() { return AddressId; }

    public void setAddressId(int addressId) { AddressId = addressId; }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3){
        this.addressLine3 = addressLine3;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
    }


}