package it.er.layerintercept;


import it.er.basic.Basic;
import it.er.object.Logged;
import it.er.service.UserPreferences;
import it.er.util.CustomException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractIdentified extends Basic {

	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	protected ServletContext context;
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}
	
	
	public AbstractIdentified(){}
	
	public AbstractIdentified(HttpServletRequest request, HttpServletResponse response, ServletContext context){
		this.request = request;
		this.response = response;
		this.context = context;
	}
	
}
