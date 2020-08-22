package com.bbd.licenscerenewal.models;

import com.bbd.licenscerenewal.services.OnCreate;
import com.bbd.licenscerenewal.services.OnUpdate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class OwnerType {
    @NotNull(groups = OnUpdate.class,message = "Owner type ID null on update")
    @Null(groups = OnCreate.class,message = "Owner type ID not null on update")
    private int ownerTypeId;
    @NotNull(message = "Name cannot be null")
    private String name;

    public int getOwnerTypeId() {
        return ownerTypeId;
    }

    public void setOwnerTypeId(int ownerTypeId) {
        this.ownerTypeId = ownerTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}