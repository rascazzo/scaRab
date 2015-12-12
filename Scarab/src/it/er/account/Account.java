package it.er.account;


import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.er.dao.Esternal;


@XmlRootElement(name = "account")
public class Account implements Esternal{
	private String user_id;
 	private String eMail;
 	private String username;
 	private String password;
 	private Date create_date;
 	private Date modify_date;
 	private String mainsuperusersite;
 	private Boolean privacy;
		
	public Account(){
		
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
	@XmlElement
	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	@XmlElement
	public Date getModify_date() {
		return modify_date;
	}

	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@XmlElement
	public String getMainsuperusersite() {
		return mainsuperusersite;
	}

	public void setMainsuperusersite(String mainsuperusersite) {
		this.mainsuperusersite = mainsuperusersite;
	}

	@Override
	public void annulla() {
		this.user_id = null;
	}

	public Boolean isPrivacy() {
		return privacy;
	}

	public void setPrivacy(Boolean privacy) {
		this.privacy = privacy;
	}

	
	
	
}
