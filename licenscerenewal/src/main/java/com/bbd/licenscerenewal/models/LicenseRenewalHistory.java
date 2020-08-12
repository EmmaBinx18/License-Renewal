package com.bbd.licenscerenewal.models;

import java.sql.Date;

public class LicenseRenewalHistory{
    private License license;
    private Date renewalDate;
    private Float fee;

    public License getLicense(){
        return license;
    }

    public void setLicense(License license){
        this.license = license;
    }

    public Date getRenewalDate(){
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate){
        this.renewalDate = renewalDate;
    }

    public Float getFee(){
        return fee;
    }

    public void setFee(Float fee){
        this.fee = fee;
    }
}