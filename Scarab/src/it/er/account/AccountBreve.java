package it.er.account;
import it.er.dao.Esternal;

import java.io.Serializable;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "accountBreve")
public class AccountBreve implements Esternal,Serializable,HttpSessionBindingListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7864016751689645618L;
	private String user_id;
 	private String eMail;
 	private String username;
 	private String password;
 	private String role;
	private Boolean privacy;
	
	public AccountBreve(){
		
	}

	@XmlElement
	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	@XmlElement
	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	
	@XmlElement	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void annulla(){
		this.user_id = null;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
		
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		this.annulla();
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Boolean privacy) {
		this.privacy = privacy;
	}
	
	
}
