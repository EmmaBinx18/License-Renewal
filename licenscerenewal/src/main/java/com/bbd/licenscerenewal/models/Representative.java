package com.bbd.licenscerenewal.models;

import com.bbd.licenscerenewal.services.OnCreate;
import com.bbd.licenscerenewal.services.OnUpdate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Representative {
    @NotNull(groups = OnUpdate.class)
    @Null(groups = OnCreate.class)
    private int representativeId;
    @NotNull(message = "ID type cannot be null")
    private int idType;
    @Pattern(regexp =  "(((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229))(( |-)(\\d{4})( |-)(\\d{3})|(\\d{7}))", message = "Invalid ID")
    private String idNumber;
    @NotNull(message = "Country of issue cannot be null")
    @Size(max = 25,message = "Country of issue is too long")
    private String countryOfIssue;
    @NotNull(message = "Surname cannot be null")
    @Size(max=100,message = "Surname too long")
    private String surname;
    @NotNull(message = "Initials cannot be null")
    @Size(max = 3, message = "Initials too long")
    private String initials;
    @NotNull(message = "Owner type ID cannot be null")
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