package it.er.service;


import it.er.dao.Site;
import it.er.object.SideBar;
import it.er.util.CustomException;

import java.util.Map;

public interface SideBarX {
	public SideBar getSidebar(Site s) throws Exception;
	public SideBar getSidebar();
	public Map<String, Map<String, String>> getSideBarMapValue(Site s);
	public Map<String, Integer> getSideBarMapOrder(Site s);
	public Map<String, Map<String, String>> getSideBarMapOtherAttr(Site s);
	public Map<String, Map<String, String>> getSideBarMapValue();
	public Map<String, Integer> getSideBarMapOrder();
	public Map<String, Map<String, String>> getSideBarMapOtherAttr();
	public String resolvLink(String servername,String plus,String method);
	public String resolvLink(String plus,String method);
	public boolean reloadAllSidebar() throws CustomException;
	public Map<Site, Map<String, Map<String, Map<String,String>>>> getSideBarMapValueSite();
	public Map<Site, Map<String, Integer>> getSideBarMapOrderSite();
	public Map<Site, Map<String, Map<String, String>>> getSideBarMapOtherAttrSite();
	public String resolveHrefByTagName(Site s,String contentType,String nodename,String lang,String id,String query); 
}
