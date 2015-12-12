package it.er.account;


import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.PlatformTransactionManager;


public interface IAccount {
	//public int incVisite(int Id,int visita);
	public AccountBreve loadAccountbreve(String userId);
	//public Account loadAccount();
	public AccountBreve loginWithRole(String email, String passMR1, String role) throws SQLException;
	public AccountBreve login(String email, String passMR1) throws SQLException,EmptyResultDataAccessException;
	//public String getHostLetele();
	//public String getTitleLetele();
	public int saveSuperRoleAfterSuperAccout(Role role) throws SQLException;
	public int saveAccount(Account account,String password) throws SQLException;
	public int saveRoleAfterAccout(Role role) throws SQLException;
	public PlatformTransactionManager getTransactionManager();
}
