package com.bbd.licenscerenewal.models;

public class Organisation{
    private String idType;
    private String idNumber;
    private String countryOfIssue;
    private String surname;
    private String initials;
    private String ownerType;

    public int getIdType() {
        return idType;
    }

    public void setIdType(String idType){
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber){
        this.idNumber = idNumber;
    }

    public String getCountryOfIssue() {
        return countryOfIssue;
    }

    public void setCountryOfIssue(String countryOfIssue){
        this.countryOfIssue = countryOfIssue;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials){
        this.initials = initials;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType){
        this.ownerType = ownerType;
    }
}