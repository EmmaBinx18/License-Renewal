package com.bbd.licenscerenewal.models;

public class Owner{
    private int OwnerId;
    private String idNumber;
    private String idType;
    private String countryOfIssue;
    private String organisationName;
    private String surname;
    private String initials;
    private String firstName;
    private String middleName;
    private String emailAddress;
    private String homeTel;
    private String workTel;
    private String cellphoneNumber;
    private String faxNumber;
    private Address postalAddress;
    private Address streetAddress;
    private String chosenAddress;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber){
        this.idNumber = idNumber;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType){
        this.idType = idType;
    }

    public String getCountryOfIssue() {
        return countryOfIssue;
    }

    public void setCountryOfIssue(String countryOfIssue){
        this.countryOfIssue = countryOfIssue;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName){
        this.organisationName = organisationName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName){
        this.middleName = middleName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

    public String getHomeTel() {
        return homeTel;
    }

    public void setHomeTel(String homeTel){
        this.homeTel = homeTel;
    }

    public String getWorkTel() {
        return workTel;
    }

    public void setWorkTel(String workTel){
        this.workTel = workTel;
    }

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public void setCellphoneNumber(String cellphoneNumber){
        this.cellphoneNumber = cellphoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber){
        this.faxNumber = faxNumber;
    }

    public Address getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(Address postalAddress){
        this.postalAddress = postalAddress;
    }

    public Address getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(Address streetAddress){
        this.streetAddress = streetAddress;
    }

    public String getChosenAddress() {
        return chosenAddress;
    }

    public void setChosenAddress(String chosenAddress){
        this.chosenAddress = chosenAddress;
    }

    public int getOwnerId() { return OwnerId; }

    public void setOwnerId(int ownerId) { OwnerId = ownerId; }
}