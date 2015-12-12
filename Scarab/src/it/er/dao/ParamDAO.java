package it.er.dao;

import it.er.basic.Basic;
import it.er.dao.ParamRowMapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("param")
public class ParamDAO extends Basic implements IParamDAO,it.er.dinamic.RepresentedHappyService{
	@Autowired
	private JdbcTemplate jdbcTemplate = null;
	
	private static Logger log = LogManager.getLogger(ParamDAO.class);
	
	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	protected JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}
	
	public void close(){
		log.info("Destroy paramDao bean");
		this.jdbcTemplate = null;
	}
	
	public void start(){
		log.info("Init paramDao bean");
	}
	
	@Override
	public Param getParam(String name) throws SQLException{
		String sql = "SELECT * FROM param WHERE name=?";
		RowMapper<Param> rm = new ParamRowMapper();
		Param p = (Param) getJdbcTemplate().queryForObject(sql, new Object[]{name}, rm);
		return p;
	}

	@Override
	public int create(Object i) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int read(Object i, Object o) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int read(Object i, Object o, int start, int limit)
			throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Object o) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Object o) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Param> getParamList(String name) throws SQLException {
		String sql = "SELECT * FROM param WHERE name=?";
		RowMapper<Param> rm = new ParamRowMapper();
		List<Param> p = getJdbcTemplate().query(sql, new String[]{name}, rm);
		return p;
		
	}

	

}
