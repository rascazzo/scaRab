package it.er.service;

import it.er.account.AccountBreve;
import it.er.object.Logged;
import it.er.util.CustomException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public interface UserPreferences {

	public void scopeSession(AccountBreve a,HttpServletRequest s);
	
	public boolean loggedScopeSession(HttpServletRequest s) throws Exception;
	
	public void logout(HttpServletRequest s) throws CustomException;
	
	public Logged getLogged();
}
