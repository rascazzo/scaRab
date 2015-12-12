package it.er.account;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AccountBreveRowMapper implements RowMapper<AccountBreve>{

	@Override
	public AccountBreve mapRow(ResultSet rs, int numRow) throws SQLException {
		AccountBreve acc = new AccountBreve();
		acc.setUser_id(rs.getString("user_id"));
		acc.setUsername(rs.getString("username"));
		acc.seteMail(rs.getString("eMail"));
		acc.setRole(rs.getString("r.role"));
		acc.setPrivacy(rs.getBoolean("privacy"));
		return acc;
	}
	
}
