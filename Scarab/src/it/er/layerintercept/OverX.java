package it.er.layerintercept;

import it.er.object.Logged;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OverX {

	
	public Logged userVerify(HttpServletRequest req,
			HttpServletResponse res,
			ServletContext cx);
}
