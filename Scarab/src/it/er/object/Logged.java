package it.er.object;

import it.er.account.AccountBreve;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "logged")
public class Logged {

	private boolean isNew;
	
	private String name;
	
	private AccountBreve account;
	
	private String role;
	
	private String currLang;
	
	public Logged(){}

	@XmlElement
	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	@XmlElement
	public AccountBreve getAccount() {
		return account;
	}

	public void setAccount(AccountBreve account) {
		this.account = account;
	}
	@XmlElement
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	@XmlElement
	public String getCurrLang() {
		return currLang;
	}

	public void setCurrLang(String currLang) {
		this.currLang = currLang;
	}
	
	
}
