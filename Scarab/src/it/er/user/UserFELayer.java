package it.er.user;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.er.account.AccountBreve;
import it.er.service.LoginAction;
import it.er.service.UserPreferences;
import it.er.service.UserSessionService;
import it.er.util.*;

@Controller
@RequestMapping("/userfe")
public class UserFELayer implements UserSessionService{

	private Logger log = LogManager.getLogger(UserFELayer.class);
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public String login(@RequestParam("loginusername") String username,
			@RequestParam("loginpassword") String password,
			HttpServletRequest req) throws CustomException{
		String r = "{\"success\":";
		AccountBreve a = loginAction.requestScope(username, password);
		if (a != null){
			try {
				userPreferences.scopeSession(a,req);
				
				r = r+ "true}";
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				r =  r +"false}";
			}
		} else
			r =  r +"false}";
		return r;
	}
	
	@RequestMapping(value = "/logged", method = RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public String logged(HttpServletRequest s) throws CustomException{
		String r = "{\"success\":";
		try {
			if (loggedVerify(s))
				r = r+ "true,\"name\":\""+userPreferences.getLogged().getAccount().getUsername()+"\"}";
			else
				r =  r +"false}";
		} catch (Exception e) {
			throw new CustomException(e);
		}
		return r;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public String logout(HttpServletRequest req) throws CustomException{
		String r = "{\"success\":";
			try {
				userPreferences.logout(req);
			} catch (Exception e){
				r = r+ "false}";
			}
			r = r+ "true}";
		return r;
	}
	
	private boolean loggedVerify(HttpServletRequest s)throws Exception{
		if (s.getSession() != null && userPreferences.loggedScopeSession(s))
			return true;
		else
			return false;
	}
	

	@Autowired
	private LoginAction loginAction;
	
	private UserPreferences userPreferences;
    
	public LoginAction getLoginAction() {
		return loginAction;
	}

	public void setLoginAction(LoginAction loginAction) {
		this.loginAction = loginAction;
	}

	public UserPreferences getUserPreferences() {
		return userPreferences;
	}
	@Autowired
	public void setUserPreferences(UserPreferences userPreferences) {
		this.userPreferences = userPreferences;
	}


	
}
