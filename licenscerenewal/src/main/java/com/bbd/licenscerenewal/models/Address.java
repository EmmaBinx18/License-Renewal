package com.bbd.licenscerenewal.models;

import com.bbd.licenscerenewal.services.OnCreate;
import com.bbd.licenscerenewal.services.OnUpdate;

import javax.validation.constraints.*;

public class Address{
    @NotNull(groups = OnUpdate.class)
    @Null(groups= OnCreate.class)
    private int addressId;
    @NotNull(message = "Address line 1 null")
    @Size(max = 100, message = "Address line 1 length too large")
    private String addressLine1;
    @NotNull(message = "Address line 1 null")
    @Size(max = 100, message = "Address line 1 length too large")
    private String addressLine2;
    @NotNull(message = "Address line 1 null")
    @Size(max = 100, message = "Address line 1 length too large")
    private String addressLine3;
    @Max(value = 9999,message = "Postal code too large")
    @Min(value = 0001,message = "Postal code too small")
    private String postalCode;
    private int addressTypeId;

    public int getAddressId() { 
        return addressId; 
    }

    public void setAddressId(int addressId) { 
        this.addressId = addressId; 
    }

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

    public int getAddressTypeId() { return addressTypeId; }

    public void setAddressTypeId(int addressTypeId) { this.addressTypeId = addressTypeId; }
}