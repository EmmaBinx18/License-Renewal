package com.bbd.licenscerenewal.models;

import org.springframework.stereotype.Service;

@Service
public class NullObjects {
    private Address address;
    private License license;
    private LicenseRenewalHistory licenseRenewalHistory;
    private LicenseStatus licenseStatus;
    private LicenseType licenseType;
    private Owner owner;
    private OwnerType ownerType;
    private Representative representative;
    private Vehicle vehicle;
    private VehicleType vehicleType;

    public NullObjects(){
        address = new Address();
        address.setAddressId(-1);
        address.setPostalCode("");
        address.setAddressTypeId(-1);
        address.setAddressLine1("");
        address.setAddressLine2("");
        address.setAddressLine3("");

        license = new License();
        license.setLicenseId(-1);
        license.setExpiryDate(null);
        license.setFirstIssueDate(null);
        license.setLicenseNumber("");
        license.setOwnerId(-1);

        licenseRenewalHistory = new LicenseRenewalHistory();
        licenseRenewalHistory.setFee(-1.00);
        licenseRenewalHistory.setLicenseId(-1);
        licenseRenewalHistory.setLicenseRenewalHistoryId(-1);
        licenseRenewalHistory.setRenewalActionId(-1);
        licenseRenewalHistory.setRenewalDate(null);

        licenseStatus = new LicenseStatus();
        licenseStatus.setLicenseStatusId(-1);
        licenseStatus.setName("");

        licenseType = new LicenseType();
        licenseType.setLicenseTypeId(-1);
        licenseType.setName("");

        owner = new Owner();
        owner.setOwnerId(-1);
        owner.setFaxNumber("");
        owner.setCellphoneNumber("");
        owner.setHomeTel("");
        owner.setEmailAddress("");
        owner.setSurname("");
        owner.setOrganisationName("");
        owner.setInitials("");
        owner.setIdType(-1);
        owner.setMiddleNames("");
        owner.setRepresentativeId(-1);
        owner.setPostalAddressId(-1);
        owner.setStreetAddressId(-1);

        ownerType = new OwnerType();
        ownerType.setName("");
        ownerType.setOwnerTypeId(-1);

        representative = new Representative();
        representative.setCountryOfIssue("");
        representative.setIdNumber("");
        representative.setIdType(-1);
        representative.setInitials("");
        representative.setOwnerTypeId(-1);
        representative.setSurname("");
        representative.setRepresentativeId(-1);

        vehicle = new Vehicle();
        vehicle.setVehicleId(-1);
        vehicle.setMake("");
        vehicle.setModel("");
        vehicle.setVin("");
        vehicle.setRegisterNumber("");
        vehicle.setOdometer(-1);
        vehicle.setVehicleTypeId(-1);

        vehicleType = new VehicleType();
        vehicleType.setName("");
        vehicleType.setVehicleTypeId(-1);
    }

    public Address getAddress() {
        return address;
    }

    public License getLicense() {
        return license;
    }

    public LicenseRenewalHistory getLicenseRenewalHistory() {
        return licenseRenewalHistory;
    }

    public LicenseStatus getLicenseStatus() {
        return licenseStatus;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public Owner getOwner() {
        return owner;
    }

    public OwnerType getOwnerType() {
        return ownerType;
    }

    public Representative getRepresentative() {
        return representative;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}
