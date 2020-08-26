package com.bbd.licenscerenewal.models;

public class Representative {
    private int representativeId;
    private int idType;
    private String idNumber;
    private String countryOfIssue;
    private String surname;
    private String initials;
    private int ownerTypeId;

    public int getRepresentativeId() {
        return representativeId;
    }

    public void setRepresentativeId(int representativeId) {
        this.representativeId = representativeId;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCountryOfIssue() {
        return countryOfIssue;
    }

    public void setCountryOfIssue(String countryOfIssue) {
        this.countryOfIssue = countryOfIssue;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public int getOwnerTypeId() {
        return ownerTypeId;
    }

    public void setOwnerTypeId(int ownerTypeId) {
        this.ownerTypeId = ownerTypeId;
    }
}