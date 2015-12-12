package it.er.account;

import it.er.basic.Basic;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

@Repository("account")
public class AccountDAO extends Basic implements IAccount,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8056690442426672327L;

	private JdbcTemplate jdbcTemplate = null;
	
	private static Logger log = LogManager.getLogger(AccountDAO.class);
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}
	
	public void close(){
		log.info("Destroy acasatuaDao bean");
		this.jdbcTemplate = null;
	}
	
	public void start(){
		log.info("Init acasatuaDao bean");
	}
	
	

	
	@Override
	public AccountBreve loadAccountbreve(String userId) {
		AccountBreve acc = null;
		String sql = "Select * From user Where user_id=?";
		RowMapper<AccountBreve> rm = new AccountBreveRowMapper();
		try {
			acc = getJdbcTemplate().queryForObject(sql, new Object[]{userId}, rm);
		} catch (EmptyResultDataAccessException e){
			log.warn("Some try to select an object user that there in not here");
		}
		return acc;
	}

	/*
	@Override
	public Account loadAccount() {
		String sql = "Select u.*,r.ruolo From utente u inner join ruolo r on " +
				"u.IdUtente=r.utenteId Where u.IdUtente=?";
		
		return null;
	}
	*/
	
	

	

	
	
	@Override
	public AccountBreve login(String email, String passMR1) throws SQLException,EmptyResultDataAccessException{
		String sql = "Select u.*,r.role from user u inner join role r on " +
				"u.user_id=r.user_id where username=? AND password=?";
		RowMapper<AccountBreve> rm = new AccountBreveRowMapper();
		AccountBreve a = getJdbcTemplate().queryForObject(sql, new Object[]{email,passMR1}, rm);
		log.info(sql+"{"+email+"}");
		return a;
	}

	
	@Override
	public int saveSuperRoleAfterSuperAccout(Role role) throws SQLException {
		String sql = "INSERT into role (user_id,role)"
					+ " Values (?,?)";
		int r =  getJdbcTemplate().update(sql, role.getUtenteId(),
				role.getRuolo());
		return r;
	}

	@Override
	public AccountBreve loginWithRole(String email, String passMR1, String role)
			throws SQLException {
		String sql = "Select u.*,r.role from user u inner join role r on " +
				"u.user_id=r.user_id where username=? AND password=? AND r.role = ?";
			RowMapper<AccountBreve> rm = new AccountBreveRowMapper();
			List<AccountBreve> a = getJdbcTemplate().queryForList(sql, AccountBreve.class, new Object[]{email,passMR1,role});
			log.info(sql+"{"+email+"}");
		if (a != null && a.size() > 1){
			throw new SQLException("error DB");
		} else if (a.size() == 1)
			return a.get(0);
		return null;
	}

	
	@Override
	public int saveAccount(Account account,String password) throws SQLException {
		String sql = "INSERT into user (user_id,email,username,create_date,password,privacy) "
				+ "Values(?,?,?,now(),?,?)";
		int u =  getJdbcTemplate().update(sql, account.getUser_id(), 
										account.geteMail(),
										account.getUsername(),
										password,
										account.isPrivacy());
		return u;
	}
	
	@Override
	public int saveRoleAfterAccout(Role role) throws SQLException {
		String sql = "INSERT into role (user_id,role)"
					+ " Values (?,?)";
		int r =  getJdbcTemplate().update(sql, role.getUtenteId(),
				role.getRuolo());
		return r;
	}

}
