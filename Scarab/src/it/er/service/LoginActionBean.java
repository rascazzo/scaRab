package it.er.service;


import it.er.account.AccountBreve;
import it.er.account.IAccount;
import it.er.basic.Basic;
import it.er.util.CustomException;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

public class LoginActionBean extends Basic implements LoginAction{

	private Logger log = LogManager.getLogger(LoginActionBean.class);
	
		
	@Autowired
	private IAccount accountDao;
	
	public void start(){
		log.info("Init possible login");
	}
	
	public void close(){
		log.info("Stop possible login");
	}

	@Override	
	public AccountBreve requestScope(String username, String password) throws CustomException{
		if (log.isDebugEnabled())
			log.debug("come to login... #"+username);
		String md5pass = "";
		String md5User = "";
		AccountBreve ab = null;
		try {
			md5pass = it.er.util.MD5.getHash(password);
			md5User = it.er.util.MD5.getHash(username);
		} catch (NoSuchAlgorithmException e){
			log.error("Unable to passMd5", e);
			throw new CustomException(e);
		}
		try {
			ab = getAccountDao().login(md5User, md5pass);
		} catch (EmptyResultDataAccessException e) {
			log.info("Unknow user");
			ab = null;
		} catch (SQLException e) {
			log.error("Error in retrive user");
			ab = null;
			throw new CustomException(e);
		}
		return ab;
	}
	
	public IAccount getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(IAccount accountDao) {
		this.accountDao = accountDao;
	}
}
