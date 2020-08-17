package com.bbd.licenscerenewal.services;

import com.bbd.licenscerenewal.models.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;
@Service
public class OrganisationRepo implements IRepository<Organisation>{

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Override
    public Organisation update(Organisation toUpdate) {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement add  = conn.prepareStatement("UPDATE [dbo].[Organisation]\n" +
                    "   SET [IdentificationTypeId] = ?\n" +
                    "      ,[IdNumber] = ?\n" +
                    "      ,[ForeignIdCountry] = ?\n" +
                    "      ,[Surname] = ?\n" +
                    "      ,[Initials] = ?\n" +
                    "      ,[OwnerTypeId] = ?\n" +
                    " WHERE OrganisationId = ?");
            add.setInt(1, toUpdate.getIdType());
            add.setString(2,toUpdate.getIdNumber());
            add.setString(3,toUpdate.getCountryOfIssue());
            add.setString(4,toUpdate.getSurname());
            add.setString(5, toUpdate.getInitials());
            add.setInt(6,toUpdate.getOwnerType());
            add.setInt(7,toUpdate.getId());

            ResultSet rs = add.executeQuery();
            return convertResultSet(rs).get(0);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return null;
    }

    @Override
    public Organisation delete(int id) {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            Organisation organisation = getById(id);
            PreparedStatement delete  = conn.prepareStatement("DELETE FROM Organisation WHERE OrganisationId = ?");
            delete.setInt(1, id);

            ResultSet rs = delete.executeQuery();

            return organisation;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return null;
    }

    @Override
    public Organisation add(Organisation toAdd) {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement add  = conn.prepareStatement("EXEC pCreateOrganisation ?,?,?,?,?,?");
            add.setInt(1, toAdd.getIdType());
            add.setString(2,toAdd.getIdNumber());
            add.setString(3,toAdd.getCountryOfIssue());
            add.setString(4,toAdd.getSurname());
            add.setString(5, toAdd.getInitials());
            add.setInt(6,toAdd.getOwnerType());

            ResultSet rs = add.executeQuery();
            return convertResultSet(rs).get(0);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return null;
    }

    @Override
    public List<Organisation> convertResultSet(ResultSet toConvert) throws SQLException {
        List<Organisation> owners = new ArrayList<>();

        while(toConvert.next())
        {
            Organisation temp = new Organisation();
            temp.setId(toConvert.getInt(1));
            temp.setIdType(toConvert.getInt(2));
            temp.setIdNumber(toConvert.getString(3));
            temp.setCountryOfIssue(toConvert.getString(4));
            temp.setSurname(toConvert.getString(5));
            temp.setInitials(toConvert.getString(6));
            temp.setOwnerType(toConvert.getInt(7));

            owners.add(temp);
        }

        return owners;
    }

    @Override
    public Organisation getById(int id) {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM Organisation WHERE OrganisationId = ?");
            get.setInt(1, id);

            ResultSet rs = get.executeQuery();
            return convertResultSet(rs).get(0);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            databaseService.releaseConnection(conn);
        }
        return null;
    }
}
