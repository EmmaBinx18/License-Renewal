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

import com.bbd.licenscerenewal.models.Representative;

import com.bbd.licenscerenewal.utils.logging.LogSQL;
import com.bbd.licenscerenewal.utils.logging.LogType;
import com.bbd.licenscerenewal.utils.logging.Logger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RepresentativeRepo implements IRepository<Representative> {

    Logger logger = new Logger(new LogSQL());

    @Autowired
    @Qualifier("DatabasePool")
    IDataBasePool databaseService;

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
            logger.log("{CALL pCreateRepresentative(?,?,?,?,?,?)}",LogType.QUERY);
            sp.setInt(1, toAdd.getIdType());
            sp.setString(2, toAdd.getIdNumber());
            sp.setString(3, toAdd.getCountryOfIssue());
            sp.setString(4, toAdd.getSurname());
            sp.setString(5, toAdd.getInitials());
            sp.setInt(6, toAdd.getOwnerTypeId());

            ResultSet rs = sp.executeQuery();

            Representative representative = convertResultSet(rs).get(0);
            logger.log(representative,LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);
            return representative;
        } catch (SQLException throwables) {
            logger.log("(Failed Running Query) " + throwables.getMessage(), LogType.ERROR);
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
            logger.log("UPDATE TABLE Representative SET",LogType.QUERY);
            for (Map.Entry<String,T> value: values) {
                query += putParams.get(value.getKey());
            }
            
            PreparedStatement update  = conn.prepareStatement(query);
            int index = 1;
            for (Map.Entry<String,T> value: values) {
                update.setObject(index,value.getValue());
            }

            logger.log(values,LogType.PARAMETERS);
            
            update.executeUpdate();
            return getById(id);
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
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
            logger.log("SELECT * FROM Representative", LogType.QUERY);
            ResultSet rs = get.executeQuery();
            List<Representative> representatives = convertResultSet(rs);
            logger.log(representatives, LogType.RESPONSE);
            logger.log("", LogType.COMPLETED);
            return representatives;
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
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
            logger.log("SELECT RepresentativeId,IdentificationTypeId,IdNumber,ForeignIdCountry,Surname, Initials,OwnerTypeId FROM Representative WHERE RepresentativeId = ?",LogType.QUERY);
            get.setInt(1, id);

            logger.log(id,LogType.PARAMETERS);

            ResultSet rs = get.executeQuery();

            Representative representative = convertResultSet(rs).get(0);
            logger.log(representative,LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);
            return representative;
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
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

            logger.log("SELECT * FROM Representative WHERE 1=1 ",LogType.QUERY);
            for (Map.Entry<String,T> param: params) {
                query += getParams.get(param.getKey());
            }
            
            PreparedStatement get  = conn.prepareStatement(query);
            int index = 1;
            for (Map.Entry<String,T> param: params) {
                get.setObject(index,param.getValue());
            }
            logger.log(params,LogType.PARAMETERS);
            
            ResultSet rs = get.executeQuery();
            List<Representative> representatives = convertResultSet(rs);
            logger.log(representatives,LogType.RESPONSE);
            logger.log("",LogType.COMPLETED);
            return representatives;
        } catch (SQLException throwable) {
            logger.log("(Failed Running Query) " + throwable.getMessage(), LogType.ERROR);
            throwable.printStackTrace();
            throw throwable;
        } finally {
            databaseService.releaseConnection(conn);
        }
    }
    
}