package it.er.dao;

import it.er.dao.Param;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ParamRowMapper implements RowMapper<Param>{

	@Override
	public Param mapRow(ResultSet rs, int numRow) throws SQLException {
		Param p = new Param();
		p.setName(rs.getString("name"));
		p.setType(rs.getString("type"));
		p.setValue(rs.getString("value"));
		return p;
	}

}
