package it.er.dao.sidebar;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TagnameAttrMapper implements RowMapper<SBTagnameAttr>{

	@Override
	public SBTagnameAttr mapRow(ResultSet arg0, int arg1) throws SQLException {
		SBTagnameAttr sbta = new SBTagnameAttr(arg0.getInt("idtagname"),
				arg0.getInt("idattribut"));
		return sbta;
	}

}
