package it.er.object;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="header")
public class Header {
	
	private String domain;
	
	private String mainTitle;
	
	private String mainSubTitle;
	
	
	public Header(){}
	@XmlElement
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	@XmlElement
	public String getMainTitle() {
		return mainTitle;
	}
	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}
	@XmlElement
	public String getMainSubTitle() {
		return mainSubTitle;
	}
	public void setMainSubTitle(String mainSubTitle) {
		this.mainSubTitle = mainSubTitle;
	}
	
	
}
