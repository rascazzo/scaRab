package it.er.dao;

import it.er.account.Account;
import it.er.basic.Basic;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

@Repository("site")
public class SiteDAO implements ISiteDAO,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7766017102398203358L;

	@Autowired
	private JdbcTemplate jdbcTemplate = null;
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	private static Logger log = LogManager.getLogger(SiteDAO.class);
	
	public SiteDAO(){}
	
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}
	
	@Override
	public int saveSite(Site site) throws SQLException {
		String sql = "INSERT into site (idsite,domain,adminxsltpath,mainlang,subtitle"
				+ ",title,webcontentpath,xsltpath) "
				+ "Values(?,?,?,?,?,?,?,?)";
		return getJdbcTemplate().update(sql, site.getIdsite(),
										site.getDomain(),
										site.getAdminxsltpath(),
										site.getMainlang(),
										site.getSubtitle(),
										site.getTitle(),
										site.getWebcontentpath(),
										site.getXsltpath());
		
	}
	
	@Override
	public int saveSuperAccount(Account account,String password) throws SQLException {
		String sql = "INSERT into user (user_id,email,username,mainsuperusersite,create_date,password) "
				+ "Values(?,?,?,?,now(),?)";
		int u =  getJdbcTemplate().update(sql, account.getUser_id(), 
										account.geteMail(),
										account.getUsername(),
										account.getMainsuperusersite(),
										password);
		return u;
	}


	@Override
	public Site readSite(String domain) throws SQLException {
		String sql = "SELECT * FROM site WHERE domain=?";
		RowMapper<Site> row = new SiteRowMapper();
		Site s = getJdbcTemplate().queryForObject(sql, row, new Object[]{domain});
		return s;
	}

	@Override
	public List<Site> readAllSites() throws SQLException {
		String sql = "SELECT * FROM site";
		List<Site> l = null;
		RowMapper<Site> row = new SiteRowMapper();
		l = getJdbcTemplate().query(sql, row);
		return l;
	}

	@Override
	public Site readSiteFromId(String id) throws SQLException {
		String sql = "SELECT * FROM site WHERE idsite=?";
		RowMapper<Site> row = new SiteRowMapper();
		Site s = getJdbcTemplate().queryForObject(sql, row, new Object[]{id});
		return s;
	}


}
