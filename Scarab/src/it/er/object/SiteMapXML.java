package it.er.object;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="urlset",namespace="http://www.sitemaps.org/schemas/sitemap/0.9")
public class SiteMapXML {

	
	
	private List<URLSiteMap> url = new LinkedList<URLSiteMap>();
	

	@XmlElement
	public List<URLSiteMap> getUrl() {
		return url;
	}

	public void setUrl(List<URLSiteMap> url) {
		this.url = url;
	}

	
	
	
	
}
