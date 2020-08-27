package com.bbd.licenscerenewal.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.CallableStatement;

import com.bbd.licenscerenewal.models.NullObjects;
import com.bbd.licenscerenewal.models.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bbd.licenscerenewal.models.UserDB;
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
    @Qualifier("DatabasePool")
		IDataBasePool databaseService;
		
	@Autowired
		private PasswordEncoder bcryptEncoder;

	@Autowired
	private NullObjects nullObjects;

		public List<UserDB> convertResultSet(ResultSet toConvert) throws SQLException {
			List<UserDB> users = new ArrayList<>();

			while(toConvert.next()){
					UserDB user = new UserDB();
					user.setUsername(toConvert.getString(1));
					user.setPassword(toConvert.getString(2));

					users.add(user);
			}

			return users;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {
			UserDB user = getUser(username);
			if (user != null) {
				return new User(user.getUsername(), user.getPassword(),
						new ArrayList<>());
			} else {
				throw new UsernameNotFoundException("User not found with username: " + username);
			}
		} catch (SQLException exception) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		} 
			
	}

	public String add(UserDB user) throws SQLException {

		Connection conn = null;
		try {
				conn  = databaseService.getConnection();
				CallableStatement sp = conn.prepareCall("{?= CALL pCreateUser(?,?)}");
				sp.registerOutParameter(1, java.sql.Types.VARCHAR);
				sp.setString(2, user.getUsername());
				sp.setString(3, bcryptEncoder.encode(user.getPassword()));
			
				sp.execute();
				return sp.getString(1);
		} catch (SQLException exception) {
				exception.printStackTrace();
				throw exception;
		} finally {
				databaseService.releaseConnection(conn);
		}

	}

	public UserDB getUser(String username) throws SQLException {

		Connection conn = null;
		try {
				conn  = databaseService.getConnection();
				CallableStatement sp = conn.prepareCall("{CALL pGetUser(?)}");
				sp.setString(1, username);

				ResultSet rs = sp.executeQuery();

			List<UserDB> list = convertResultSet(rs);
			return list.isEmpty() ?   null : list.get(0);
		} catch (SQLException exception) {
				exception.printStackTrace();
				throw exception;
		} finally {
				databaseService.releaseConnection(conn);
		}
	}

}