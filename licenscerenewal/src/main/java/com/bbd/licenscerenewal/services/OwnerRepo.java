package com.bbd.licenscerenewal.services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bbd.licenscerenewal.models.NullObjects;
import com.bbd.licenscerenewal.models.Owner;
import com.bbd.licenscerenewal.models.Representative;
import com.bbd.licenscerenewal.utils.logging.LogRequest;
import com.bbd.licenscerenewal.utils.logging.LogSQL;
import com.bbd.licenscerenewal.utils.logging.LogType;
import com.bbd.licenscerenewal.utils.logging.Logger;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OwnerRepo implements IRepository<Owner>{

    private Logger logger = new Logger(new LogSQL());

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    NullObjects nullObjects;

    public final Dictionary<String,String> getParams = new Hashtable();
    public final Dictionary<String,String> putParams = new Hashtable();

    public OwnerRepo() {
        getParams.put("idType"," AND IdentificationTypeId = ?");
        getParams.put("idNumber", " AND IdNumber = ?");
        getParams.put("countryOfIssue", " AND ForeignIdCountry = ?");
        getParams.put("surname", " AND Surname = ?");
        getParams.put("organisationName", " AND OrganisationName = ?");
        getParams.put("firstName", " AND FirstName = ?");
        getParams.put("emailAddress", " AND EmailAddress = ?");
        getParams.put("cellphoneNumber", " AND CellphoneNumber = ?");
        getParams.put("representativeId", " AND RepresentativeId = ?");

        putParams.put("idType", " IdentificationTypeId = ?,");
        putParams.put("idNumber", " IdNumber = ?,");
        putParams.put("countryOfIssue", " ForeignIdCountry = ?,");
        putParams.put("surname", " Surname = ?,");
        putParams.put("organisationName", " OrganisationName = ?,");
        putParams.put("initials", " Initials = ?,");
        putParams.put("firstName", " FirstName = ?,");
        putParams.put("middleNames", " MiddleNames = ?,");
        putParams.put("emailAddress", " EmailAddress = ?,");
        putParams.put("homeTel", " HomeTel = ?,");
        putParams.put("workTel", " WorkTel = ?,");
        putParams.put("cellphoneNumber", " CellphoneNumber = ?,");
        putParams.put("faxNumber", " FaxNumber = ?,");
        putParams.put("postalAddressId", " PostalAddressId = ?,");
        putParams.put("streetAddressId", " StreetAddressId = ?,");
        putParams.put("chosenAddress", " PrefferedAddressType = ?,");
        putParams.put("representativeId", " RepresentativeId = ?,");
    }

    @Override
    public Owner add(Owner toAdd) throws SQLException {
        Connection conn = null;
        try {
            conn = databaseService.getConnection();
            CallableStatement sp = conn.prepareCall("{CALL pCreateOwner(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            sp.setInt(1, toAdd.getIdType());
            sp.setString(2,toAdd.getIdNumber());
            sp.setString(3,toAdd.getCountryOfIssue());
            sp.setString(4,toAdd.getSurname());
            sp.setString(5,toAdd.getOrganisationName());
            sp.setString(6,toAdd.getInitials());
            sp.setString(7,toAdd.getFirstName());
            sp.setString(8,toAdd.getMiddleNames());
            sp.setString(9,toAdd.getEmailAddress());
            sp.setString(10, toAdd.getHomeTel());
            sp.setString(11, toAdd.getWorkTel());
            sp.setString(12, toAdd.getCellphoneNumber());
            sp.setString(13, toAdd.getFaxNumber());
            sp.setInt(14,toAdd.getPostalAddressId());
            sp.setInt(15,toAdd.getStreetAddressId());
            sp.setInt(16,toAdd.getChosenAddress());
            sp.setInt(17,toAdd.getRepresentativeId());

            logger.log("{CALL pCreateOwner(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", LogType.QUERY);
            logger.log(toAdd,LogType.PARAMETERS);

            ResultSet rs = sp.executeQuery();
            List<Owner> list = convertResultSet(rs);
            logger.log(list,LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);
            return list.isEmpty() ?   nullObjects.getOwner():list.get(0);
        } catch (SQLException exception) {
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }

    }

    public <T> Owner patchOwner(int id, Set<Map.Entry<String,T>> values) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            String query = "UPDATE TABLE Owner SET";
            for (Map.Entry<String,T> value: values) {
                query += putParams.get(value.getKey());
            }
            logger.log("UPDATE TABLE Owner SET", LogType.QUERY);
            logger.log(id,LogType.PARAMETERS);
            logger.log(values,LogType.PARAMETERS);
            PreparedStatement update  = conn.prepareStatement(query);
            int index = 1;
            for (Map.Entry<String,T> value: values) {
                update.setObject(index,value.getValue());
            }
            
            update.executeUpdate();
            return getById(id);
        } catch (SQLException exception) {
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    @Override
    public List<Owner> convertResultSet(ResultSet toConvert) throws SQLException {
        List<Owner> owners = new ArrayList<>();

        while(toConvert.next())
        {
            Owner temp = new Owner();
            temp.setOwnerId(toConvert.getInt(1));
            temp.setIdNumber(toConvert.getString(2));
            temp.setIdType(toConvert.getInt(3));
            temp.setCountryOfIssue(toConvert.getString(4));
            temp.setSurname(toConvert.getString(5));
            temp.setOrganisationName(toConvert.getString(6));
            temp.setInitials(toConvert.getString(7));
            temp.setFirstName(toConvert.getString(8));
            temp.setMiddleNames(toConvert.getString(9));
            temp.setEmailAddress(toConvert.getString(10));
            temp.setHomeTel(toConvert.getString(11));
            temp.setWorkTel(toConvert.getString(12));
            temp.setCellphoneNumber(toConvert.getString(13));
            temp.setFaxNumber(toConvert.getString(14));
            temp.setPostalAddressId(toConvert.getInt(15));
            temp.setStreetAddressId(toConvert.getInt(16));
            temp.setChosenAddress(toConvert.getInt(17));
            temp.setRepresentativeId(toConvert.getInt(18));

            owners.add(temp);
        }
        return owners;
    }

    public List<Owner> getAll() throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM Owner");
            ResultSet rs = get.executeQuery();
            return convertResultSet(rs);
        } catch (SQLException exception) {
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    @Override
    public Owner getById(int id) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            CallableStatement sp = conn.prepareCall("{CALL pGetOwner(?)}");
            sp.setInt(1, id);
            logger.log("{CALL pGetOwner(?)}",LogType.QUERY);
            logger.log(id,LogType.PARAMETERS);
            ResultSet rs = sp.executeQuery();
            List<Owner> list = convertResultSet(rs);
            logger.log(list,LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);
            return list.isEmpty() ?   nullObjects.getOwner():list.get(0);

        } catch (SQLException exception) {
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    public <T> List<Owner> getByQueryParams(Set<Map.Entry<String,T>> params) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            String query = "SELECT * FROM Owner WHERE 1=1 ";
            for (Map.Entry<String,T> param: params) {
                query += getParams.get(param.getKey());
            }
            logger.log(query,LogType.QUERY);
            logger.log(params,LogType.PARAMETERS);
            PreparedStatement get  = conn.prepareStatement(query);
            int index = 1;
            for (Map.Entry<String,T> param: params) {
                get.setObject(index,param.getValue());
            }
            
            ResultSet rs = get.executeQuery();
            List<Owner> list = convertResultSet(rs);
            logger.log(list,LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);
            return list;
        } catch (SQLException exception) {
            logger.log("{Error SQL}" + exception.getMessage(),LogType.ERROR);
            throw exception;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }
}
