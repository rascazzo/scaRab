package it.er.warehouse;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VisitRowMapper implements RowMapper<Visit>{

	@Override
	public Visit mapRow(ResultSet rs, int numR) throws SQLException {
		Visit v = new Visit();
		v.setIdvisit(rs.getInt("idvisit"));
		v.setNobject(rs.getBoolean("nobject"));
		v.setObjectid(rs.getInt("objectid"));
		v.setObjectname(rs.getString("objectname"));
		v.setObjectype(rs.getString("objectype"));
		v.setTagname(rs.getString("tagname"));
		return v;
	}

}
