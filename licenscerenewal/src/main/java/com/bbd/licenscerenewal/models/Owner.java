package com.bbd.licenscerenewal.models;

import com.bbd.licenscerenewal.services.OnCreate;
import com.bbd.licenscerenewal.services.OnUpdate;

import javax.validation.constraints.*;

public class Owner{
    @NotNull(groups = OnUpdate.class)
    @Null(groups = OnCreate.class)
    private int ownerId;
    @Pattern(regexp =  "(((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229))(( |-)(\\d{4})( |-)(\\d{3})|(\\d{7}))", message = "Invalid ID")
    private String idNumber;
    @Min(value = 0,message = "ID type cannot be negative")
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
    private String middleNames;
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
    @NotNull(message = "Postal address ID cannot be null")
    private int postalAddressId;
    @NotNull(message = "Street address ID cannot be null")
    private int streetAddressId;
    @NotNull(message = "Chosen address cannot be null")
    private int chosenAddress;
    @Min(value = 0,message = "Representative ID cannot be null")
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