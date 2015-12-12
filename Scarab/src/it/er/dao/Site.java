package it.er.dao;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "site")
public class Site {
	
	private String idsite;
	private String title;
	private String subtitle;
	private String domain;
	private String xsltpath;
	private String adminxsltpath;
	private String webcontentpath;
	private String mainlang;
	private boolean mixedlang;
	
	public Site(){}

	public Site(String domain){
		this.domain = domain;
	}
	
	@XmlElement
	public String getIdsite() {
		return idsite;
	}

	public void setIdsite(String idsite) {
		this.idsite = idsite;
	}

	@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@XmlElement
	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	@XmlElement
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	@XmlElement
	public String getXsltpath() {
		return xsltpath;
	}

	public void setXsltpath(String xsltpath) {
		this.xsltpath = xsltpath;
	}
	@XmlElement
	public String getAdminxsltpath() {
		return adminxsltpath;
	}

	public void setAdminxsltpath(String adminxsltpath) {
		this.adminxsltpath = adminxsltpath;
	}
	@XmlElement
	public String getWebcontentpath() {
		return webcontentpath;
	}

	public void setWebcontentpath(String webcontentpath) {
		this.webcontentpath = webcontentpath;
	}
	@XmlElement
	public String getMainlang() {
		return mainlang;
	}

	public void setMainlang(String mainlang) {
		this.mainlang = mainlang;
	}
	@XmlElement
	public boolean isMixedlang() {
		return mixedlang;
	}

	public void setMixedlang(boolean mixedlang) {
		this.mixedlang = mixedlang;
	}

	
	
	
}
