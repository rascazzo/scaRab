package it.er.dao.sidebar;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TagNameMapper implements RowMapper<SBTagname>{

	@Override
	public SBTagname mapRow(ResultSet arg0, int arg1) throws SQLException {
		SBTagname sbt = new SBTagname(arg0.getInt("id"));
		sbt.setActive(arg0.getBoolean("active"));
		sbt.setName(arg0.getString("name"));
		sbt.setTagSiteId(arg0.getString("tag_siteid"));
		sbt.setTextValue(arg0.getString("textvalue"));
		return sbt;
	}

}
