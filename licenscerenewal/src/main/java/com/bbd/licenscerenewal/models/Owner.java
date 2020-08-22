package com.bbd.licenscerenewal.models;

import javax.validation.constraints.*;

public class Owner{
    @Min(0)
    private int ownerId;
    @Pattern(regexp =  "(((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229))(( |-)(\\d{4})( |-)(\\d{3})|(\\d{7}))", message = "Invalid ID")
    private String idNumber;
    @Min(0)
    private int idType;
    @Size(min = 0, max = 25, message = "Invalid Country of issue")
    private String countryOfIssue;
    private String organisationName;
    @NotNull(message = "Surname cannot be null")
    private String surname;
    @NotNull(message = "Initials cannot be null")
    private String initials;
    @NotNull(message = "First name cannot be null")
    private String firstName;
    @NotNull(message = "Middle name cannot be null")
    private String middleName;
    @Email(message = "Invalid email address")
    private String emailAddress;
    @Size(min = 10, max = 15,message = "Invalid home number")
    private String homeTel;
    @Size(min = 10, max = 15,message = "Invalid work number")
    private String workTel;
    @Size(min = 10, max = 15,message = "Invalid cellphone number")
    @NotNull(message = "Cellphone number cannot be null")
    private String cellphoneNumber;
    @Size(min = 10, max = 15 ,message = "Invalid fax number")
    private String faxNumber;
    private Address postalAddress;
    private Address streetAddress;
    @NotNull(message = "Chosen address cannot be null")
    private int chosenAddress;
    private int organisationId;

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

    public int getOrganisationId() { return organisationId; }

    public void setOrganisationId(int organisationId) { this.organisationId = organisationId; }
}