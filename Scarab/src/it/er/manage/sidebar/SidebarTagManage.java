package it.er.manage.sidebar;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import it.er.dao.Site;
import it.er.dao.sidebar.SBTagOrder;
import it.er.manage.BaseManage;
import it.er.object.Attribute;
import it.er.service.SidebarAccess;
import it.er.service.UserPreferences;
import it.er.tag.SiteAccess;
import it.er.tag.Tag;
import it.er.tag.XTag;
import it.er.util.CustomException;

@Component("sidebartagmanage")
public class SidebarTagManage extends BaseManage implements SidebarAccess{

	private static final Logger log = LogManager.getLogger(SidebarTagManage.class);
	
	private XTag tagDao;
	
	public XTag getTagDao() {
		return tagDao;
	}
	@Autowired
	public void setTagDao(XTag tagDao) {
		this.tagDao = tagDao;
	}
	
	public UserPreferences getUserPreferences() {
		return userPreferences;
	}
	@Autowired
	public void setUserPreferences(UserPreferences userPreferences) {
		this.userPreferences = userPreferences;
	}
	
	private UserPreferences userPreferences;
	
	
	private SiteAccess siteManagement;
	
	public SiteAccess getSiteManagement() {
		return siteManagement;
	}
	
	@Autowired
	public void setSiteManagement(SiteAccess siteManagement) {
		this.siteManagement = siteManagement;
	}
	
	@Override
	public int[] insertTgn(HttpServletRequest req,
			HttpServletResponse res, String tagname, String tagvalue, String numorder,
				String sitename,String tgtypeFirst, String tgtypeSecond, String tgtypeGrp,
				String tagnamerel,String lang) throws CustomException{
		int[] r = null;
		try {
			Map<String,String> tag = new HashMap<String, String>();
			tag.put(tagname, tagvalue);
			Map<String,Integer> ord = new HashMap<String, Integer>();
			ord.put(tagname, Integer.parseInt(numorder));
			Site s = site.readSiteFromId(sitename);
			if (tgtypeFirst.equals("on")){
					r = tagDao.insertTags(tag, null,ord,lang, s);
				} else if (tgtypeSecond.equals("on")){
					r = tagDao.insertTags(tag, tagnamerel,ord,lang, s);
				}
			
		} catch (NumberFormatException e){
			log.error(e.getMessage(),e);
			throw new CustomException(e);
		} catch (Exception e){
			log.error(e.getMessage(),e);
			throw new CustomException(e);
		}
		return r;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Tag> getAllRootTag(Site s) throws CustomException{
		List<Tag> lfath = null;
		try {
			Map<Site,List<Tag>> map = tagDao.getAllRootTag();
			
			lfath = (List<Tag>) siteManagement.containsAndGetObject(map, siteManagement.getCurrentSite(s.getIdsite()));
		} catch (Exception e) {
			log.error("error in create object, site, father",e);
			throw new CustomException(e);
		}
		return lfath;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Tag> getTagsSon(Site s,String tagnamerel) throws CustomException{
		List<Tag> lson = null;
		try {
			Map<Site,List<Tag>> map = tagDao.getTags(tagnamerel, null);
			
			lson = (List<Tag>) siteManagement.containsAndGetObject(map, siteManagement.getCurrentSite(s.getIdsite()));
		} catch (Exception e) {
			log.error("error in create object, site, son",e);
			throw new CustomException(e);
		}
		return lson;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Attribute> getAttributes(Site s,String tagname) throws CustomException {
		List<Attribute> lattr = null;
		try {
			Map<Site,List<Attribute>> map =  tagDao.getAttributes(tagname);
			lattr = (List<Attribute>) siteManagement.containsAndGetObject(map, siteManagement.getCurrentSite(s.getIdsite()));
		} catch (Exception e) {
			log.error("error in create object, site, attr",e);
			throw new CustomException(e);
		}
		return lattr;
	}
	@Override
	public int[] insertAttributes(List<Attribute> l, String tagname, Site s)
			throws CustomException {
		int[] r = new int[l.size()];
		try{
			r = tagDao.insertAttributes(l, tagname, s);
		} catch (SQLException e){
			log.error("error in create object, attr",e);
			r = null;
			throw new CustomException(e);
		} catch (Exception e) {
			log.error("error in create object, attr",e);
			r = null;
			throw new CustomException(e);
		}
		
		return r;
	}
}
