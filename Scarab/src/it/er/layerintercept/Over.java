package it.er.layerintercept;


import it.er.account.AccountBreve;
import it.er.account.IAccount;
import it.er.basic.Basic;
import it.er.object.Logged;
import it.er.service.UserPreferences;
import it.er.util.SingletonLookup;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


@Path("/over")
public class Over extends Basic implements OverX{
	
	private IAccount account = null;
	
	private UserPreferences userPreferences;
	
	public UserPreferences getUserPreferences() {
		return userPreferences;
	}
	@Autowired
	public void setUserPreferences(UserPreferences userPreferences) {
		this.userPreferences = userPreferences;
	}
	
	private static Logger log = LogManager.getLogger(Over.class);
	
	public void close(){
		log.info("Destroy webpageStyOver bean");
		this.account = null;
	}
	
	public void start(){
		log.info("Init webpageStyOver bean");
	}
	
	
	
	
	@Override
	public Logged userVerify(HttpServletRequest req, HttpServletResponse res, 
								ServletContext cx) {
		boolean flag = false;
		account = SingletonLookup.getAccountDAO(cx);
			AccountBreve pxplatform = null;
			
			
			//Cookie mycookie = null;
			//Cookie[] cookies = req.getCookies();
			/*
			if (cookies!=null){
				for (int i=0;i<cookies.length;i++)
					if(cookies[i].getName().equals(getVisitCookieName()))
						if (cookies[i].getValue().equals("sadgnbswb315vwefsj3lgume5lpazm21")){
							mycookie = cookies[i];
							req.getSession().setAttribute("user", mycookie.getValue());
							break;
							
						}
				for (int i=0;i<cookies.length;i++)
					if(cookies[i].getName().equals("ericaAd"))				
						if (cookies[i].getValue().equals("admin")){
							mycookie = cookies[i];
							req.getSession().setAttribute("admin", mycookie.getValue());
							break;
						}
			} 
			*/
			int visita = 0;
			Logged logged = new Logged();
			AccountBreve a = new AccountBreve();
				if (req.getSession().getAttribute("loggeduser")==null){
					
						//int id = 0;
						//if (pxplatform.getUser_id()!=-1){
							//id = pxplatform.getUser_id();
									/*
										req.getSession().setAttribute("user", b.getVisite());
										mycookie = new Cookie(getVisitCookieName(),"sadgnbswb315vwefsj3lgume5lpazm21");
										mycookie.setPath("/");
										mycookie.setMaxAge(1800);
										res.addCookie(mycookie);
										flag = true;
										*/
										
										
										
												logged.setNew(true);
												log.info("user in visita");
											
										
									
						/**
						 * impostazione browser identity
						 */
						/*}
						else {
							log.warn("errore lettura account");
							flag = false;
						}*/
				} else {
					try {
						boolean f = userPreferences.loggedScopeSession(req);
						if (f){
							logged.setAccount(a);
							logged.setName(a.getUsername());
							logged.setNew(false);
						} else {
							log.warn("WARN in load user on over");
						}
						
					} catch (Exception e) {
						log.error("Unable to verify user", e.getCause());
					}
				}
				return logged;
	
	}
}
