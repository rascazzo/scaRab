package it.er.object;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "footer")
public class Footer {

	
	private String mainContact;
	
	private String overMainTitle;
	
	public Footer(){}

	@XmlElement
	public String getMainContact() {
		return mainContact;
	}

	public void setMainContact(String mainContact) {
		this.mainContact = mainContact;
	}

	@XmlElement
	public String getOnmainTitle() {
		return overMainTitle;
	}

	public void setOnmainTitle(String overMainTitle) {
		this.overMainTitle = overMainTitle;
	}
	
	
}
