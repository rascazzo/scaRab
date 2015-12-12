package it.er.jdbcTemplatestack;

import org.springframework.jdbc.core.JdbcTemplate;

public interface DtOperation {
	public JdbcTemplate getJdbcMyTemplate(String s);
}
