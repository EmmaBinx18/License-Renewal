package com.bbd.licenscerenewal.models;

import com.bbd.licenscerenewal.services.OnCreate;
import com.bbd.licenscerenewal.services.OnUpdate;

import javax.validation.constraints.*;

public class Organisation{
    @NotNull(groups = OnUpdate.class, message = "ID null on Update")
    @Null(groups = OnCreate.class,message = "ID not null on create")
    private int id;
    @NotNull(message = "ID type cannot be null")
    private int idType;
    @Pattern(regexp =  "(((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229))(( |-)(\\d{4})( |-)(\\d{3})|(\\d{7}))"
            ,message = "Invalid ID number")
    private String idNumber;
    @NotNull(message = "Country of issue cannot be null")
    private String countryOfIssue;
    @NotNull(message = "Surname cannot be null")
    private String surname;
    @NotNull(message = "Initials cannot be null")
    private String initials;
    @NotNull(message = "Owner type cannot be null")
    private int ownerType;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType){
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

    public int getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(int ownerType){
        this.ownerType = ownerType;
    }
}