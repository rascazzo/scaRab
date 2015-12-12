package it.er.tag;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

public class TagRowMapper implements RowMapper<Tag>{

	@Override
	public Tag mapRow(ResultSet rs, int numRow) throws SQLException {
		Tag t = new Tag(rs.getInt("id"));
		t.setActive(rs.getBoolean("active"));
		t.setTagname(rs.getString("name"));
		t.setTextvalue(rs.getString("textvalue"));
		return t;
	}

}
