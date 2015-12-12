package it.er.service;


import it.er.account.AccountBreve;
import it.er.basic.Basic;
import it.er.object.Logged;
import it.er.tag.SiteAccess;
import it.er.util.CustomException;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Authenticator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CleanupFailureDataAccessException;


public class UserPreferencesBean extends Basic implements UserPreferences,Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -887250773819076800L;

	private static Logger log = LogManager.getLogger(UserPreferencesBean.class);
	
	private SiteAccess siteManagement;
	
	private Logged loggedAccount;
		
	public void start(){
		log.info("Init userpreference");
		loggedAccount = new Logged();
		loggedAccount.setNew(true);
		loggedAccount.setCurrLang("en");
	}
	
	public void close(){
		log.info("Stop userpreference");
	}

	@Override
	public void scopeSession(AccountBreve a,HttpServletRequest s) {

		s.setAttribute("loggeduser", a);
		loggedAccount.setAccount(a);
		loggedAccount.setNew(false);
		loggedAccount.setRole(a.getRole());
		try {
			loggedAccount.setCurrLang(siteManagement.getCurrentSite(s).getMainlang());
		} catch (Exception e){
			log.error("Error in set Session current Default site Lang");
		}
		s.getSession().setMaxInactiveInterval(1800);
		if (log.isDebugEnabled())
			log.debug("user"+a.getUser_id()+" in session");
	}

	@Override
	public boolean loggedScopeSession(HttpServletRequest s) throws Exception{
		Enumeration<String> names = s.getAttributeNames();
		if (s.getSession().isNew()){
			return false;
		}
		/*
		if (s.getSession().getCreationTime() (Calendar.getInstance().getTime().getTime()){
			s.getSession().invalidate();
			return false;
		}*/
		if (loggedAccount == null || (loggedAccount != null && loggedAccount.isNew())){
			return false;
		} 
		if (loggedAccount != null && !loggedAccount.isNew()){
			return true;
		}
        while (names.hasMoreElements()) {
            String name = names.nextElement(); 
            if (name.equals("loggeduser")){ 
	            	AccountBreve tmp =  (AccountBreve) s.getAttribute("loggeduser");
	            	
	            	return true;
            }
        }
		return false;
	}

	
	public Logged getLoggedAccount() {
		return loggedAccount;
	}

	public void setLoggedAccount(Logged loggedAccount) {
		this.loggedAccount = loggedAccount;
	}

	@Override
	public void logout(HttpServletRequest s) throws CustomException {
		s.getSession().invalidate();
		
		loggedAccount.setNew(true);
		loggedAccount.setAccount(null);
		try {
			loggedAccount.setCurrLang(siteManagement.getCurrentSite(s).getMainlang());
		} catch (Exception e){
			log.error("Error in set Session current Default Logout site Lang");
		}
	}

	@Override
	public Logged getLogged() {
		return this.loggedAccount;
	}

	public SiteAccess getSiteManagement() {
		return siteManagement;
	}
	
	@Autowired
	public void setSiteManagement(SiteAccess siteManagement) {
		this.siteManagement = siteManagement;
	}
	
}
