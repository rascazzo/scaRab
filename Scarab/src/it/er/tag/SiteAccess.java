package it.er.tag;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import it.er.dao.Site;

public interface SiteAccess {

	public Site getCurrentSite(HttpServletRequest req) throws Exception;
	
	public Site getCurrentSite(String idSite) throws Exception;
	
	public List<Site> getAllSite() throws Exception;
	
	public Object containsAndGetObject(Map<Site, ? extends Object> map, Site s ) throws Exception;
	
	public boolean reloadAllSites();
}
