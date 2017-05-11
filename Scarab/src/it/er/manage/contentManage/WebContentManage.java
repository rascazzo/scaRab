package it.er.manage.contentManage;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.er.account.IAccount;
import it.er.basic.Basic;
import it.er.dao.IParamDAO;
import it.er.dao.ISiteDAO;
import it.er.dao.SiteDAO;
import it.er.dao.Site;
import it.er.dao.Text;
import it.er.dao.TextContent;
import it.er.lang.LanguageAccess;
import it.er.manage.BaseManage;
import it.er.object.Header;
import it.er.object.Layer;
import it.er.object.Metatag;
import it.er.object.NovitaContainer;
import it.er.object.NovitaPos;
import it.er.object.Presentation;
import it.er.object.SiteMapXML;
import it.er.service.SideBarBean;
import it.er.service.SideBarX;
import it.er.service.UserPreferences;
import it.er.tag.SiteAccess;
import it.er.util.CustomException;
import it.er.util.SingletonLookup;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("webContentManage")
public class WebContentManage extends BaseManage{


	private static Logger log = LogManager.getLogger(WebContentManage.class);

	
	private IParamDAO param;
	
	private SiteAccess siteManagement;
	
	private IAccount account;
	
	private UserPreferences userPreferences;
	
	private SideBarX sidebar = null;
	
	public SideBarX getSidebar() {
		return sidebar;
	}
	
	public void setSidebar(SideBarX sidebar) {
		this.sidebar = sidebar;
	}
	
	public UserPreferences getUserPreferences() {
		return userPreferences;
	}
	@Autowired
	public void setUserPreferences(UserPreferences userPreferences) {
		this.userPreferences = userPreferences;
	}

	public IParamDAO getParam() {
		return param;
	}
	
	
	@Autowired
	public void setParam(IParamDAO param) {
		this.param = param;
	}
	
	public SiteAccess getSiteManagement() {
		return siteManagement;
	}
	@Autowired
	public void setSiteManagement(SiteAccess siteManagement) {
		this.siteManagement = siteManagement;
	}

	public IAccount getAccount() {
		return account;
	}

	public SiteDAO getSite() {
		return site;
	}
	
	
	private TextContent novita;
	
	private LanguageAccess genericScarabLang;
	
	public LanguageAccess getGenericScarabLang() {
		return genericScarabLang;
	}
	@Autowired
	public void setGenericScarabLang(LanguageAccess genericScarabLang) {
		this.genericScarabLang = genericScarabLang;
	}
	
	private int getIdRandom(List<?> list){
		double r = Math.random();
		int p = list.size();
		int re = (int)(r*p);
		return re;
	}
	
	public Layer getHom(HttpServletRequest req,
			ServletContext cx, HttpServletResponse res,String lang){
		
		checkLang(userPreferences.getLogged(), lang);
		sidebar = SingletonLookup.getSideBarX(cx);
		
		String tit = "";
		Site s = null;
		try {
			 s = siteManagement.getCurrentSite(req);
			 tit = s.getTitle();
			 log.info(s!=null?"site :"+s.getIdsite():"no site");
		} catch (Exception e){
			log.error(e);
		}
		
		Layer home = WebContentManage.createAuthLayer(userPreferences, req, false);
		home.setTitle(tit);
		home.setNavigation("home");
		 
		
		param = SingletonLookup.getParamDAO(cx);
		
		/* lo uso per tutti i componenti (tagname) home*/
		int limitNovitaHome = 0;
		try {
			limitNovitaHome= Integer.parseInt(param.getParam(getLimitNovHom()).getValue());
		} catch (NumberFormatException e) {
			log.error("Unable to parse "+getLimitNovHom(),e.getCause());
		} catch (SQLException e) {
			log.error("Unable to load "+getLimitNovHom(),e.getCause());
		}	
		
		NovitaContainer novConteiner = new NovitaContainer();
		novConteiner.setArchivio(false);
		novConteiner.setHome(true);
		/*NovitaDal*/
	
		novita  = SingletonLookup.getNoSqlTextDAO(cx);
		try {
			Deque<Text> novList = novita.getTextListShort(limitNovitaHome, 0, "home",null,lang);
			int sizeQueue = novList.size();
			int pos = 0;
			List<Text> lnp = new LinkedList<Text>();
			while (pos<sizeQueue){
				pos ++;
				Text np = new Text();
				//np.setPos(pos);
				np = novList.poll();
				lnp.add(np);
			}
			novConteiner.setArticle(lnp);
		} catch (Exception e) {
			log.error("Unable to select Novita List",e.getCause());
		}
		
		home.getContent().setText(novConteiner);
		
		
		/*Image ;meta */
		try {
			List<Metatag> lm = novita.readMetatagByTitle(null, "home", lang);
			if (lm != null && lm.size() > 0){
				this.addURLToMetaKey(lm.get(0), "home", null, s, sidebar, lang, "content", req.getQueryString());
				home.setMetatag(lm.get(0));
			}	
		} catch (Exception e1) {
			log.error("Unable to read Metatag List",e1.getCause());
		}
	
		if (s != null )
			try {
				home.setSidebar(sidebar.getSidebar(s));
			} catch (Exception e) {
				log.warn("Not able to get Sidebar",e);
			}
		else
			log.info("no site");
		setTextsAndLabels(home, genericScarabLang ,lang);
		novita = null;
		account = null;
		param = null;
		
		return home;
	}
	
	public Presentation getPresentation(HttpServletRequest req,
			ServletContext cx, HttpServletResponse res,String lang){
		
		checkLang(userPreferences.getLogged(), lang);
		//account = SingletonLookup.getAccountDAO(cx);
				String host = "";
				String tit = "";
				try {
					 if (this.site == null){
						 ISiteDAO site = SingletonLookup.getSiteDAO(cx);
					 }
					 host = getSite().readSite(req.getServerName()).getDomain();
					 tit = getSite().readSite(req.getServerName()).getTitle();
				
				} catch (Exception e){
					log.error(e);
				}
		Presentation welcome = new Presentation();
		welcome.setNavigation("welcome");
		welcome.setTitle(tit);
		Header header = new Header();
		header.setDomain(host);
		
		/*Metatag*/
		novita  = SingletonLookup.getNoSqlTextDAO(cx);
		param = SingletonLookup.getParamDAO(cx);
		try {
			List<Metatag> lm = novita.readMetatagByTitle(null, "welcome", lang);
			if (lm != null && lm.size() > 0){
				//this.addURLToMetaKey(lm.get(0), "welcome", null, , sidebar, lang, "content", null);
				welcome.setMetatag(lm.get(0));
			}
		} catch (Exception e1) {
			log.error("Unable to read Metatag List",e1.getCause());
		}
	
		welcome.setHeader(header);
		
		return welcome;
	}
	
	public SiteMapXML getSitemap(HttpServletRequest req,ServletContext cx){
		SiteMapXML mapsite = new SiteMapXML();
		
		//account = SingletonLookup.getAccountDAO(cx);
		String host = "";//account.getHostLetele();
		
		sidebar = SingletonLookup.getSideBarX(cx);
		try {
			 
			 if (this.site == null){
				 ISiteDAO site = SingletonLookup.getSiteDAO(cx);
			 }
			 host = getSite().readSite(req.getServerName()).getDomain();
		
		
			//mapsite.getUrl().add(new object.URLSiteMap("http://"+host+getResolverrestcontentns()+"/content/welcome",
			//		sidebar.getLastDateModSidebarxml(), "monthly", "0.8"));
			//mapsite.getUrl().add(new object.URLSiteMap("http://"+host+getResolverrestcontentns()+"/content/home",
			//		sidebar.getLastDateModSidebarxml(), "weekly", "1"));
			Map<String,Map<String,String>> sideBarMapValue = sidebar.getSideBarMapValue();
			Map<String,String> secondLiv = null;
			Set<String> pr = sideBarMapValue.keySet();
			Iterator<String> i = pr.iterator(); 
			Map<String, Map<String, String>> otherAttr = sidebar.getSideBarMapOtherAttr();
			while(i.hasNext()){
				String kye1 = i.next();
				secondLiv = sideBarMapValue.get(kye1);
				Set<String> sec = secondLiv.keySet();
				Iterator<String> i2 = sec.iterator();
				
				while(i2.hasNext()){
					String i2s = i2.next();
					if (otherAttr.get(kye1).containsKey("type")){
						if (otherAttr.get(kye1).get("type").equals("staticlink"))
							if (otherAttr.get(kye1).containsKey("lastmod") && otherAttr.get(kye1).containsKey("changefreq") && otherAttr.get(kye1).containsKey("priority"))
							mapsite.getUrl().add(new it.er.object.URLSiteMap(SideBarBean.resolvStaticLink(host, param.getParam(Basic.getParamadminername()).getValue(), "/"+kye1),
										//sidebar.getLastDateModSidebarxml(), otherAttr.get(kye1).get("changefreq") , otherAttr.get(kye1).get("priority")));
									otherAttr.get(kye1).get("lastmod"), otherAttr.get(kye1).get("changefreq") , otherAttr.get(kye1).get("priority")));
					} else
						if (otherAttr.get(i2s).containsKey("lastmod") && otherAttr.get(i2s).containsKey("changefreq") && otherAttr.get(i2s).containsKey("priority"))
						mapsite.getUrl().add(new it.er.object.URLSiteMap(sidebar.resolvLink(host, param.getParam(Basic.getParamadminername()).getValue(), "/"+i2s),
								//sidebar.getLastDateModSidebarxml(), otherAttr.get(kye1).get("changefreq") , otherAttr.get(kye1).get("priority")));
								otherAttr.get(i2s).get("lastmod"), otherAttr.get(i2s).get("changefreq") , otherAttr.get(i2s).get("priority")));
				}
			}
		} catch (Exception e){
			log.error(e);
			mapsite = null;
		}
		account = null;
		return mapsite;
	}
	
	public Layer getStaticContent(HttpServletRequest req,
			ServletContext cx, HttpServletResponse res,String lang,
			String contentstatic){
		
		checkLang(userPreferences.getLogged(), lang);
		
		String tit = "";
		Site s = null;
		try {
			 s = siteManagement.getCurrentSite(req);
			 tit = s.getTitle();
		} catch (Exception e){
			log.error(e);
		}
		Layer stk = WebContentManage.createAuthLayer(userPreferences, req, false);
		stk.setTitle(tit);
		stk.setNavigation(contentstatic);
		
		
		param = SingletonLookup.getParamDAO(cx);
		
		
		/*Image ;meta */
		novita  = SingletonLookup.getNoSqlTextDAO(cx);
		try {
			List<Metatag> lm = novita.readMetatagByTitle(null, contentstatic, lang);
			if (lm != null && lm.size() > 0){
				this.addURLToMetaKey(lm.get(0), "custom", null, s , sidebar, lang, "content", req.getQueryString());
				stk.setMetatag(lm.get(0));
			}
		} catch (Exception e1) {
			log.error("Unable to read Metatag List",e1.getCause());
		}
	
		sidebar = SingletonLookup.getSideBarX(cx);
		if (s != null )
			try {
				stk.setSidebar(sidebar.getSidebar(s));
			} catch (Exception e) {
				log.warn(e.getMessage(),e);
			}
		
		setTextsAndLabels(stk, genericScarabLang ,lang);
		return stk;
	}
	
	
	
	private static Layer createAuthLayer(UserPreferences userPreferences, HttpServletRequest req,boolean auth){
		Layer al = null;
		try {
			al = new Layer(userPreferences,Layer.class,req,auth);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e ) {
			log.info("User not in session #"+userPreferences.getLogged().isNew()+" error",e);
		} catch (CustomException e) {
			log.error("Error in Create Layer. Possible not Auth", e);
		} catch (Exception e){
			log.error("Error in Create Layer", e);
		}
		
		
		return al;
	}
	
	
}
