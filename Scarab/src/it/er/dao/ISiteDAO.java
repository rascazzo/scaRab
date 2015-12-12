package it.er.dao;

import it.er.account.Account;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

public interface ISiteDAO {

	public int saveSite(Site site) throws SQLException;
	public int saveSuperAccount(Account account,String password) throws SQLException;
	public JdbcTemplate getJdbcTemplate();
	public PlatformTransactionManager getTransactionManager();
	public Site readSite(String domain) throws SQLException;
	public Site readSiteFromId(String id) throws SQLException;
	public List<Site> readAllSites() throws SQLException;
}
