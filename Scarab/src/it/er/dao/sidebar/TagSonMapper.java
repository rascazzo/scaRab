package it.er.dao.sidebar;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TagSonMapper implements RowMapper<SBTagSon>{

	@Override
	public SBTagSon mapRow(ResultSet arg0, int arg1) throws SQLException {
		SBTagSon sbs = new SBTagSon(arg0.getInt("idtagnameFK"),arg0.getInt("idtagsonFK"));
		return sbs;
	}

}
