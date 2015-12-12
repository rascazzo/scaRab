package it.er.dinamic;

import it.er.dao.IParamDAO;

import java.sql.SQLException;

public interface ResolvPath {
	public String dinamicPath(String file, String nameParam, IParamDAO param) throws SQLException;	
}
