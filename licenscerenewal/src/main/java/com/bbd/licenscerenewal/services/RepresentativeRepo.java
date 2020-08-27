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
import com.bbd.licenscerenewal.models.Representative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RepresentativeRepo implements IRepository<Representative> {

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

    @Autowired
    NullObjects nullObjects;

    public final Dictionary<String,String> getParams = new Hashtable();
    public final Dictionary<String,String> putParams = new Hashtable();

    public RepresentativeRepo() {
        getParams.put("idType"," AND IdentificationTypeId = ?");
        getParams.put("idNumber"," AND IdNumber = ?");
        getParams.put("countryOfIssue"," AND ForeignIdCountry = ?");
        getParams.put("surname"," AND Surname = ?");
        getParams.put("initials"," AND Initials = ?");
        getParams.put("ownerTypeId"," AND OwnerTypeId = ?");
        
        putParams.put("idType", " IdentificationTypeId = ?,");
        putParams.put("idNumber", " IdNumber = ?,");
        putParams.put("countryOfIssue", " ForeignIdCountry = ?,");
        putParams.put("surname", " Surname = ?,");
        putParams.put("initials", " Initials = ?,");
        putParams.put("ownerTypeId", " OwnerTypeId = ?,");
    }

    @Override
    public Representative add(Representative toAdd) throws SQLException {
        Connection conn = null;
        try {
            conn = databaseService.getConnection();
            CallableStatement sp = conn.prepareCall("{CALL pCreateRepresentative(?,?,?,?,?,?)}");
            sp.setInt(1, toAdd.getIdType());
            sp.setString(2, toAdd.getIdNumber());
            sp.setString(3, toAdd.getCountryOfIssue());
            sp.setString(4, toAdd.getSurname());
            sp.setString(5, toAdd.getInitials());
            sp.setInt(6, toAdd.getOwnerTypeId());

            ResultSet rs = sp.executeQuery();
            return convertResultSet(rs).size() > 0 ? convertResultSet(rs).get(0) : nullObjects.getRepresentative();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    public <T> Representative patchRepresentative(int id, Set<Map.Entry<String,T>> values) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            String query = "UPDATE TABLE Representative SET";
            for (Map.Entry<String,T> value: values) {
                query += putParams.get(value.getKey());
            }
            
            PreparedStatement update  = conn.prepareStatement(query);
            int index = 1;
            for (Map.Entry<String,T> value: values) {
                update.setObject(index,value.getValue());
            }
            
            update.executeUpdate();
            return getById(id);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    @Override
    public List<Representative> convertResultSet(ResultSet toConvert) throws SQLException {
        List<Representative> representatives = new ArrayList<>();

        while(toConvert.next())
        {
            Representative temp = new Representative();
            temp.setRepresentativeId(toConvert.getInt(1));
            temp.setIdType(toConvert.getInt(2));
            temp.setIdNumber(toConvert.getString(3));
            temp.setCountryOfIssue(toConvert.getString(4));
            temp.setSurname(toConvert.getString(5));
            temp.setInitials(toConvert.getString(6));
            temp.setOwnerTypeId(toConvert.getInt(7));

            representatives.add(temp);
        }
        return representatives;
    }

    public List<Representative> getAll() throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT * FROM Representative");
            ResultSet rs = get.executeQuery();
            return convertResultSet(rs);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    @Override
    public Representative getById(int id) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            PreparedStatement get  = conn.prepareStatement("SELECT RepresentativeId,IdentificationTypeId,IdNumber,ForeignIdCountry,Surname, Initials,OwnerTypeId FROM Representative WHERE RepresentativeId = ?");
            get.setInt(1, id);

            ResultSet rs = get.executeQuery();
            return convertResultSet(rs).get(0);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }

    public <T> List<Representative> getByQueryParams(Set<Map.Entry<String,T>> params) throws SQLException {
        Connection conn = null;
        try {
            conn  = databaseService.getConnection();
            String query = "SELECT * FROM Representative WHERE 1=1 ";
            for (Map.Entry<String,T> param: params) {
                query += getParams.get(param.getKey());
            }
            
            PreparedStatement get  = conn.prepareStatement(query);
            int index = 1;
            for (Map.Entry<String,T> param: params) {
                get.setObject(index,param.getValue());
            }
            
            ResultSet rs = get.executeQuery();
            return convertResultSet(rs);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }
    
}