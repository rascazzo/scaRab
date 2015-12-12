package it.er.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SiteRowMapper implements RowMapper<Site>{

	@Override
	public Site mapRow(ResultSet rs, int num) throws SQLException {
		Site s = new Site(rs.getString("domain"));
		s.setIdsite(rs.getString("idsite"));
		s.setAdminxsltpath(rs.getString("adminxsltpath"));
		s.setMainlang(rs.getString("mainlang"));
		s.setMixedlang(rs.getBoolean("mixedlang"));
		s.setSubtitle(rs.getString("subtitle"));
		s.setTitle(rs.getString("title"));
		s.setWebcontentpath(rs.getString("webcontentpath"));
		s.setXsltpath(rs.getString("xsltpath"));
		return s;
	}

}
