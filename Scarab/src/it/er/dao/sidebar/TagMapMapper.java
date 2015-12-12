package it.er.dao.sidebar;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TagMapMapper implements RowMapper<SBTagmap> {

	@Override
	public SBTagmap mapRow(ResultSet arg0, int arg1) throws SQLException {
		SBTagmap sbm = new SBTagmap(arg0.getInt("id"), arg0.getString("branch"));
		sbm.setIdtagname(arg0.getInt("idtagnameFK"));
		return sbm;
	}

}
