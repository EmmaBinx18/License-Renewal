package com.bbd.licenscerenewal.models;

import java.sql.Date;

public class LicenseRenewalHistory{
    private int licenseRenewalHistoryId;
    private int licenseId;
    private Date renewalDate;
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