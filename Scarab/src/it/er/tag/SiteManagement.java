package it.er.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import it.er.basic.Basic;
import it.er.dao.Site;

@Component("siteManagement")
public class SiteManagement extends Basic implements InitializingBean,SiteAccess{
	
	private static final Logger logger = LogManager.getLogger(SiteManagement.class);
	
	private Map<String,Site> currentSites;
	
	public SiteManagement(){
		super();
	}


	public Map<String, Site> getCurrentSites() {
		return currentSites;
	}

	public void setCurrentSites(Map<String, Site> currentSites) {
		this.currentSites = currentSites;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			currentSites = new HashMap<String, Site>();
			List<Site> s = this.site.readAllSites();
			Iterator<Site> i = s.iterator();
			while (i.hasNext()){
				Site c = i.next();
				currentSites.put(c.getIdsite(), c);
			}
		} catch (Exception e){
			logger.error(e);
		}
	}


	@Override
	public Site getCurrentSite(HttpServletRequest req) throws Exception {
		Iterator<String> s = currentSites.keySet().iterator();
		Site c = null;
		while (s.hasNext()){
			c = currentSites.get(s.next());
			if (c.getDomain().equals(req.getServerName())){
				break;
			}
			c = null;
		}
		return c;
	}


	@Override
	public Site getCurrentSite(String idSite) throws Exception {
		Iterator<String> s = currentSites.keySet().iterator();
		Site c = null;
		while (s.hasNext()){
			c = currentSites.get(s.next());
			if (c.getIdsite().equals(idSite)){
				break;
			}
			c = null;
		}
		return c;
	}

	private List<Site> currentSiteList;
	
	@Override
	public List<Site> getAllSite() throws Exception {
		if (currentSiteList == null || currentSiteList.size() == 0){
			currentSiteList = new ArrayList<Site>();
			Iterator<String> i = this.currentSites.keySet().iterator();
			while (i.hasNext()){
				currentSiteList.add(currentSites.get(i.next()));
			}
		}
		return currentSiteList;
	}


	@Override
	public Object containsAndGetObject(Map<Site, ? extends Object> map, Site s)
			throws Exception {
		if (map != null){
			Iterator<Site> i = map.keySet().iterator();
			String domain = null;
			Site st = null;
			while (i.hasNext()){
				st = i.next();
				if (st.getDomain().equals(s.getDomain())){
					domain = st.getDomain();
					break;
				}
			}
			if (domain != null){
				return map.get(st);
			} else {
				return null;
			}
		} else 
			return null;
	}
	
}
