package it.er.layerintercept;

import it.er.object.Logged;
import it.er.service.UserPreferences;
import it.er.util.CustomException;

import javax.servlet.http.HttpServletRequest;

public interface IdentifiedIntercept {

	public Logged invokeServerIdentities(UserPreferences u,HttpServletRequest req,boolean requiredAuth) throws CustomException;
	
	public void invokeClientIdentities(HttpServletRequest req,OverX over) throws CustomException;
}
