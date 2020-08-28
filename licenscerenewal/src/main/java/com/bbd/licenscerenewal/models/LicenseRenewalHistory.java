package com.bbd.licenscerenewal.models;

import com.bbd.licenscerenewal.services.OnCreate;
import com.bbd.licenscerenewal.services.OnUpdate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.Date;

public class LicenseRenewalHistory{
    @Null(groups = OnCreate.class, message = "License renewal history ID not null on create")
    @NotNull(groups = OnUpdate.class, message = "License renewal history ID not null on create")
    private int licenseRenewalHistoryId;
    @Min(value = 0,message = "License ID less than zero")
    @NotNull(message = "License ID cannot be null")
    private int licenseId;
    @NotNull(message = "Renewal date cannot be null")
    private Date renewalDate;
    @Min(value = 0,message = "Fee cannot be less than zero")
    private double fee;
    private int renewalActionId;

    public int getLicenseRenewalHistoryId() {
        return licenseRenewalHistoryId;
    }

    public void setLicenseRenewalHistoryId(int licenseRenewalHistoryId) {
        this.licenseRenewalHistoryId = licenseRenewalHistoryId;
    }

    public int getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(int licenseId) {
        this.licenseId = licenseId;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getRenewalActionId() {
        return renewalActionId;
    }

    public void setRenewalActionId(int renewalActionId) {
        this.renewalActionId = renewalActionId;
    }
}