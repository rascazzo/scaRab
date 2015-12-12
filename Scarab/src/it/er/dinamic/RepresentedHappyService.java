package it.er.dinamic;

import java.sql.SQLException;

public interface RepresentedHappyService {
	public int create(Object i) throws SQLException;

	public int read(Object i, Object o) throws SQLException;

	public int read(Object i, Object o, int start, int limit)
			throws SQLException;

	public int update(Object o) throws SQLException;

	public int delete(Object o) throws SQLException;
}
