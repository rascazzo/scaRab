package it.er.service;

import it.er.util.CustomException;

import javax.servlet.http.HttpServletRequest;


public interface UserSessionService {

	public String login(String username, String passwords,HttpServletRequest req) throws CustomException;
	
	public String logged(HttpServletRequest s) throws CustomException;


}
