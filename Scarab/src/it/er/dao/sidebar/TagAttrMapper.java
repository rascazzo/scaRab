package it.er.dao.sidebar;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TagAttrMapper implements RowMapper<SBTagAttr>{

	@Override
	public SBTagAttr mapRow(ResultSet arg0, int arg1) throws SQLException {
		SBTagAttr sba = new SBTagAttr(arg0.getInt("id"));
		sba.setName(arg0.getString("name"));
		sba.setValue(arg0.getString("value"));
		return sba;
	}

}
