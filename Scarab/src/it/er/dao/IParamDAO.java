package it.er.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public interface IParamDAO {
	public Param getParam(String name) throws SQLException;
	public List<Param> getParamList(String name) throws SQLException;
	public void setJdbcTemplate(JdbcTemplate jdbc);
}
