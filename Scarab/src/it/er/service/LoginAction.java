package it.er.service;


import it.er.account.AccountBreve;
import it.er.util.CustomException;


public interface LoginAction {
	
	public AccountBreve requestScope(String username, String password) throws CustomException ;
}
