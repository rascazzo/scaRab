package it.er.basic;


import it.er.dinamic.CreaMovEntity;
import it.er.util.CustomException;
import it.er.util.SingletonLookup;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;


public abstract class HappyDaoSrvLayer {

	/*	
	protected boolean getIAccount(CreaMovEntity cme,Logger log){
		boolean r = false;
		if (account==null)
		try{
			account = (IAccount) cme.retriveEntity(AccountDAO.class);
			if (account==null){
				cme.createEntity(AccountDAO.class);
				account	= (IAccount) cme.retriveEntity(AccountDAO.class);
			}
			r = true;
		} catch (CustomException e) {
			log.warn(LayerBasic.getNoretrivedao(),new CustomException(LayerBasic.getNoretrivedao()));
			account = null;
			r = false;
		}
		return r;
	}
	
	protected boolean getHost(HttpServletRequest req,CreaMovEntity cme,String host,Logger log){
		boolean r = false;
		if (cme!=null && account!=null)
		try {
			host = this.getExistDomain(req,cme);
			r = true;
		} catch (SQLException e) {
			log.warn("no existdomain",new CustomException(LayerBasic.getNoretrivedao()));
			host = null;
			r = false;
		}
		return r;
	}
	
	*/
	
	/*
	 * return null se servername not equal to domain in dao
	 *
	protected String getExistDomain(HttpServletRequest req,CreaMovEntity c) throws SQLException{
		String serverName = getDomainFromServerName(req);
		List<String> domainsInDao = account.getHosts();
		Iterator<String> i =  domainsInDao.iterator(); 
		while (i.hasNext()){
			if (i.next().equals(serverName))
				return serverName;
		}
		return null;
	}
	*/
	protected static String getDomainFromServerName(HttpServletRequest req){
		return req.getServerName();
	}
	
		
	
	
	
}
