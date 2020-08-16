package com.bbd.licenscerenewal.service;

import com.bbd.licenscerenewal.models.Address;
import com.bbd.licenscerenewal.models.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class OwnerRepo implements IRepository<Owner>{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    OrganisationRepo organisationRepo;

    @Override
    public Owner update(Owner toUpdate) {
         try {
             Connection conn = databaseService.getConnection();
             PreparedStatement update = conn.prepareStatement("UPDATE [dbo].[Owner]\n" +
                     "   SET [IdentificationTypeId] = ?\n" +
                     "      ,[IdNumber] =? \n" +
                     "      ,[ForeignIdCountry] = ?\n" +
                     "      ,[SurnameOrganisationName] = ?\n" +
                     "      ,[Initials] = ?\n" +
                     "      ,[FirstName] = ?\n" +
                     "      ,[MiddleNames] = ?\n" +
                     "      ,[EmailAddress] = ?\n" +
                     "      ,[HomeTel] = ?\n" +
                     "      ,[WorkTel] = ?\n" +
                     "      ,[CellphoneNumber] = ?\n" +
                     "      ,[FaxNumber] = ?\n" +
                     "      ,[PostalAddressId] = ?\n" +
                     "      ,[StreetAddressId] = ?\n" +
                     "      ,[PrefferedAddressType] = ?\n" +
                     "      ,[OrganisationId] = ?\n" +
                     " WHERE OwnerId = ?");

             update.setInt(1, toUpdate.getIdType());
             update.setString(2,toUpdate.getIdNumber());
             update.setString(3,toUpdate.getCountryOfIssue());
             update.setString(4,toUpdate.getSurname());
             update.setString(5,toUpdate.getInitials());
             update.setString(6,toUpdate.getFirstName());
             update.setString(7,toUpdate.getMiddleName());
             update.setString(8,toUpdate.getEmailAddress());
             update.setString(9, toUpdate.getHomeTel());
             update.setString(10, toUpdate.getWorkTel());
             update.setString(11, toUpdate.getCellphoneNumber());
             update.setString(12, toUpdate.getFaxNumber());
             update.setInt(13,toUpdate.getPostalAddress().getAddressId());
             update.setInt(14,toUpdate.getStreetAddress().getAddressId());
             update.setInt(15,toUpdate.getChosenAddress());
             update.setInt(16,toUpdate.getOrganisationId());


             update.executeUpdate();
             databaseService.releaseConnection(conn);
             return toUpdate;
         } catch (SQLException throwable) {
             throwable.printStackTrace();
         }
        return null;
    }

    @Override
    public Owner delete(int id) {
        Connection conn = null;
         try {
             conn  = databaseService.getConnection();
             Owner removed = getById(id);

             PreparedStatement delete = conn.prepareStatement("DELETE FROM Owner WHERE OwnerId = ?");
             delete.setInt(1, id);
             delete.executeQuery();

             return removed;
         } catch (SQLException throwable) {
             throwable.printStackTrace();
         }finally{
             databaseService.releaseConnection(conn);
         }
        return null;
    }


    public Owner addOwnerAndAddresses(Owner toAdd) {
        Connection conn = null;
         try {
             conn = databaseService.getConnection();
             conn.setAutoCommit(false);
             Address postalAddress = addressRepo.insert(toAdd.getPostalAddress(),conn);
             Address streetAddress = addressRepo.insert(toAdd.getStreetAddress(),conn);
             toAdd = addOnlyOwner(toAdd,conn);
             conn.commit();


             toAdd.setStreetAddress(streetAddress);
             toAdd.setPostalAddress(postalAddress);

             return toAdd;
         } catch (SQLException throwable) {
             throwable.printStackTrace();
         }finally{
             databaseService.releaseConnection(conn);
         }
        return null;
    }
    @Override
    public Owner add(Owner toAdd){
        Connection conn = null;
        try {
            conn = databaseService.getConnection();
            return addOnlyOwner(toAdd,conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            databaseService.releaseConnection(conn);
        }
        return null;
    }

    private Owner addOnlyOwner(Owner toAdd,Connection conn){

        try {
            PreparedStatement insert = conn.prepareStatement("EXEC pCreateOwner ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?");
            insert.setInt(1, toAdd.getIdType());
            insert.setString(2,toAdd.getIdNumber());
            insert.setString(3,toAdd.getCountryOfIssue());
            insert.setString(4,toAdd.getSurname());
            insert.setString(5,toAdd.getInitials());
            insert.setString(6,toAdd.getFirstName());
            insert.setString(7,toAdd.getMiddleName());
            insert.setString(8,toAdd.getEmailAddress());
            insert.setString(9, toAdd.getHomeTel());
            insert.setString(10, toAdd.getWorkTel());
            insert.setString(11, toAdd.getCellphoneNumber());
            insert.setString(12, toAdd.getFaxNumber());
            insert.setInt(13,toAdd.getPostalAddress().getAddressId());
            insert.setInt(14,toAdd.getStreetAddress().getAddressId());
            insert.setInt(15,toAdd.getChosenAddress());
            insert.setInt(16,toAdd.getOrganisationId());
            insert.executeUpdate();

            toAdd.setOwnerId(insert.getGeneratedKeys().getInt(0));
            return toAdd;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Owner> convertResultSet(ResultSet toConvert) throws SQLException {
        List<Owner> owners = new ArrayList<Owner>();

        while(toConvert.next())
        {

            Owner temp = new Owner();
            temp.setOwnerId(toConvert.getInt(1));
            temp.setIdNumber(toConvert.getString(2));
            temp.setIdType(toConvert.getInt(3));
            temp.setCountryOfIssue(toConvert.getString(4));
            temp.setOrganisationName(toConvert.getString(5));
            temp.setSurname(toConvert.getString(6));
            temp.setInitials(toConvert.getString(7));
            temp.setFirstName(toConvert.getString(8));
            temp.setMiddleName(toConvert.getString(9));
            temp.setEmailAddress(toConvert.getString(10));
            temp.setHomeTel(toConvert.getString(11));
            temp.setWorkTel(toConvert.getString(12));
            temp.setCellphoneNumber(toConvert.getString(13));
            temp.setFaxNumber(toConvert.getString(14));
            temp.setPostalAddress(addressRepo.getById(toConvert.getInt(15)));
            temp.setStreetAddress(addressRepo.getById(toConvert.getInt(16)));
            temp.setChosenAddress(toConvert.getInt(17));
            temp.setOrganisationId(toConvert.getInt(18));

            owners.add(temp);
        }
        return owners;
    }

    @Override
    public Owner getById(int id) {
        Connection conn = null;
         try {
             conn  = databaseService.getConnection();
             PreparedStatement get  = conn.prepareStatement("SELECT OwnerId,IdNumber,IdentificationTypeId,ForeignIdCountry,SurnameOrganisationName, SurnameOrganisationName,Initials,\n" +
                     "FirstName,MiddleNames,EmailAddress,HomeTel,WorkTel,CellphoneNumber,FaxNumber,PostalAddressId,StreetAddressId, PrefferedAddressType,OrganisationId FROM Owner\n" +
                     "WHERE  OwnerId = ?");
             get.setInt(1, id);

             ResultSet rs = get.executeQuery();
             Owner owner = convertResultSet(rs).get(0);

             return owner;

         } catch (SQLException throwable) {
             throwable.printStackTrace();
         }finally{
             databaseService.releaseConnection(conn);
         }
        return null;
    }
}
