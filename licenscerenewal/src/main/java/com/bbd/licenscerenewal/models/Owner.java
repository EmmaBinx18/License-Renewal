package com.bbd.licenscerenewal.models;

public class Owner{
    private int ownerId;
    private int idType;
    private String idNumber;
    private String countryOfIssue;
    private String organisationName;
    private String surname;
    private String initials;
    private String firstName;
    private String middleNames;
    private String emailAddress;
    private String homeTel;
    private String workTel;
    private String cellphoneNumber;
    private String faxNumber;
    private int postalAddressId;
    private int streetAddressId;
    private int chosenAddress;
    private int representativeId;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber){
        this.idNumber = idNumber;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType){
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

    public String getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(String middleNames){
        this.middleNames = middleNames;
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

    public int getPostalAddressId() {
        return postalAddressId;
    }

    public void setPostalAddressId(int postalAddressId){
        this.postalAddressId = postalAddressId;
    }

    public int getStreetAddressId() {
        return streetAddressId;
    }

    public void setStreetAddressId(int streetAddressId){
        this.streetAddressId = streetAddressId;
    }

    public int getChosenAddress() {
        return chosenAddress;
    }

    public void setChosenAddress(int chosenAddress){
        this.chosenAddress = chosenAddress;
    }

    public int getOwnerId() { 
        return ownerId; 
    }

    public void setOwnerId(int ownerId) { 
        this.ownerId = ownerId; 
    }

    public int getRepresentativeId() {
        return representativeId;
    }

    public void setRepresentativeId(int representativeId) {
        this.representativeId = representativeId;
    }
}