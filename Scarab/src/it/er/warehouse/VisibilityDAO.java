package it.er.warehouse;

import it.er.basic.Basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class VisibilityDAO extends Basic implements VisibilityX{

private JdbcTemplate jdbcTemplate = null;
	
	private static Logger log = LogManager.getLogger(VisibilityDAO.class);
	
		
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}
	
	public void close(){
		log.info("Destroy visibilityDao bean");
		this.jdbcTemplate = null;
	}
	
	public void start(){
		log.info("Init visibilityDao bean");
	}
	
	

	private Integer insertVisita(Visit v) throws SQLException{
		Integer idvisit = null;
		String sql = "insert into visit (objectid,objectype,objectname,tagname,nobject) "+
				"values ("+v.getObjectid()+",'"+v.getObjectype()+"','"+v.getObjectname()+"','"+v.getTagname()+"',"+v.getNobject()+")";
		Connection c = getJdbcTemplate().getDataSource().getConnection();
		Statement s = c.prepareStatement(sql);
		
		
		try {
			int result = s.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			if (result>0){
				ResultSet rs = s.getGeneratedKeys();
				while (rs.next())
					idvisit = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			s.close();
			c.close();
		}
		
		return idvisit;
	}
	
	private Integer verifyVisit(Visit v) throws SQLException{
		String sql = "Select * from visit where nobject=? and tagname=?";
		RowMapper<Visit> rm = new VisitRowMapper();
		List<Visit> r = getJdbcTemplate().query(sql, new Object[]{v.getNobject(),v.getTagname()},rm);
		if (r.size()==0){
			sql = "Select * from visit where objectid=? and tagname=?";
			r = getJdbcTemplate().query(sql, new Object[]{v.getObjectid(),v.getTagname()},rm);
		}
		if (r.size()==0)
			return this.insertVisita(v);
		else 
			return r.get(0).getIdvisit();
	}
	
	@Override
	public int insertTracklanding(Visit v) throws SQLException {
		Integer id = this.verifyVisit(v);
		String sql = "INSERT INTO tracklanding (time,idvisit) values "+
				"(now(),"+id+")";
		int r = getJdbcTemplate().update(sql);
		return r;
	}

	private Integer insertWalking() throws SQLException{
		Integer idwalking = null;
		String sql = "insert into walking(time) "+
				"values (now())";
		Connection c = getJdbcTemplate().getDataSource().getConnection();
		Statement s = c.createStatement();
		
		int result = s.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		if (result>0){
			ResultSet rs = s.getGeneratedKeys();
			
			while (rs.next())
				idwalking = rs.getInt(1);
			
			
		}
		
		return idwalking;
	}
	
	@Override
	public int insertWalking(Visit v) throws SQLException {
		Integer id = this.verifyVisit(v);
		Integer idwalk = this.insertWalking();
		String sql = "Insert into walk_visit (idvisit,idwalking) values (?,?)";
		int r = getJdbcTemplate().update(sql, new Object[]{id,idwalk});
		return r;
	}

}
