package it.er.tag;

import it.er.object.Attribute;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AttributeRowMapper implements RowMapper<Attribute> {

	@Override
	public Attribute mapRow(ResultSet rs, int numRow) throws SQLException {
		Attribute a = new Attribute();
		a.setId(rs.getInt("id"));
		a.setName(rs.getString("name"));
		a.setValue(rs.getString("value"));
		return a;
	}

}
