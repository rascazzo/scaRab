package it.er.tag;

import it.er.object.Branch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BranchRowMapper implements RowMapper<Branch>{

	/* non lo usiamo */
	@Override
	public Branch mapRow(ResultSet rs, int numRow) throws SQLException {
		Branch b = new Branch();
		b.setId(rs.getInt("id)"));
		b.setIdtagname(rs.getInt("idtagnameFK"));
		return b;
	}

}
