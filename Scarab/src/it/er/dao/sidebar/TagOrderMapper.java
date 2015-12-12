package it.er.dao.sidebar;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TagOrderMapper implements RowMapper<SBTagOrder>{

	@Override
	public SBTagOrder mapRow(ResultSet arg0, int arg1) throws SQLException {
		SBTagOrder sbto = new SBTagOrder(arg0.getInt("idtagname"));
		sbto.setId(arg0.getInt("id"));
		sbto.setNumorder(arg0.getInt("numorder"));
		return sbto;
	}

	
}
