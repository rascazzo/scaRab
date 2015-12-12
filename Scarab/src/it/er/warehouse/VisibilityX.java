package it.er.warehouse;

import java.sql.SQLException;

public interface VisibilityX {

	public int insertTracklanding(Visit v) throws SQLException;
	public int insertWalking(Visit v) throws SQLException;
}
