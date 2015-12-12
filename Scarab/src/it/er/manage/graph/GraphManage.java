package it.er.manage.graph;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.er.dao.IParamDAO;
import it.er.dao.Site;
import it.er.manage.BaseManage;
import it.er.manage.contentManage.WebContentManage;
import it.er.object.GraphLayer;
import it.er.object.content.GraphContent;
import it.er.object.content.Palette16;
import it.er.object.content.PaletteBox;
import it.er.object.content.ScSvgWrap;
import it.er.service.SideBarX;
import it.er.service.UserPreferences;
import it.er.tag.SiteAccess;
import it.er.util.CustomException;
import it.er.util.SingletonLookup;

@Component("graphContentManage")
public class GraphManage extends BaseManage{


	private static Logger log = LogManager.getLogger(GraphManage.class);

	private IParamDAO param;
	
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
	
	private SiteAccess siteManagment;
	
	public SiteAccess getSiteManagment() {
		return siteManagment;
	}
	@Autowired
	public void setSiteManagment(SiteAccess siteManagment) {
		this.siteManagment = siteManagment;
	}
	
	public GraphContent getPalette16(HttpServletRequest req,
			HttpServletResponse res,
			ServletContext cx,
			String width,
			String height,
			String type){
		GraphContent gc = new GraphContent(new LinkedList<ScSvgWrap>());
		switch (type) {
		case "8":
			Palette16.makePalette((List<ScSvgWrap>)gc.getSvgContainer());
			break;
		case "mono":
			Palette16.makePaletteMono((List<ScSvgWrap>)gc.getSvgContainer());
			break;
		case "thirdless":
			Palette16.makePalette16((List<ScSvgWrap>)gc.getSvgContainer());
			break;
		default:
			break;
		}
			
		
		Iterator<?> i = gc.getSvgContainer().iterator();
		while (i.hasNext()){
			ScSvgWrap w = (ScSvgWrap) i.next();
			PaletteBox p = w.getBox();
			p.setWidth(width);
			p.setHeight(height);
			p.setX(0);
			p.setY(0);
			p.setStroke("#D7DAE9");
			p.setStrokeWidth("1");
		}
		return gc;
	}
	
	public GraphLayer getGraphStaticContent(HttpServletRequest req,
			ServletContext cx, HttpServletResponse res,
			String lang,
			String contentstatic){
		
		checkLang(userPreferences.getLogged(), lang);
		//account = SingletonLookup.getAccountDAO(cx);
		String host = "";//account.getHostLetele();
		String tit = "";//account.getTitleLetele();
		Site s = null;
		try {
			 s = siteManagment.getCurrentSite(req);
			 host = s.getDomain();
			 tit = s.getTitle();
		
		} catch (Exception e){
			log.error(e);
		}
		GraphLayer stk = GraphManage.createAuthGraphLayer(userPreferences, req, false);
		stk.setTitle(tit);
		stk.setNavigation(contentstatic);
		//stk.setContextTag("static@"+contentstatic);
		
		//over = SingletonLookup.getOverX(cx);
		//navMenu = SingletonLookup.getNavMenuX(cx);
		
		param = SingletonLookup.getParamDAO(cx);
		
		/*
		Contatti c = new Contatti();
		String email = null;
		try {
			email = account.loadAccountbreve().geteMail();
		} catch (Exception e){
			log.error("Errror in load account breve",e.getCause());
			return null;
		}
		c.getContatti().add(email);
		stk.getContent().setContatti(c);
		*/
		/*Image ;meta */
		String logoPath = null;
		
		try {
			logoPath= param.getParam(getLogoImagePath()).getValue();
		} catch (SQLException e) {
			log.error(e);
		}
	
		if (logoPath!=null)
			stk.getMetatag().setImage("http://" + host + "/" + logoPath);
		sidebar = SingletonLookup.getSideBarX(cx);
		if (s != null )
			try {
				stk.setSidebar(sidebar.getSidebar(s));
			} catch (Exception e) {
				log.warn(e.getMessage(),e);
			}
		param = null;
		/*
		 * palette
		 */
		stk.getContent().add(this.getPalette16(req, res, cx, "100%", "5","8"));
		stk.getContent().add(this.getPalette16(req, res, cx, "100%", "5","mono"));
		stk.getContent().add(this.getPalette16(req, res, cx, "100%", "5", "thirdless"));
		return stk;
	}
	
	private static GraphLayer createAuthGraphLayer(UserPreferences userPreferences, HttpServletRequest req,boolean auth){
		GraphLayer al = null;
		try {
			al = new GraphLayer(userPreferences,GraphLayer.class,req,auth);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e ) {
			log.info("User not in session #"+userPreferences.getLogged().isNew()+" error",e);
		} catch (CustomException e) {
			log.error("Error in Create GraphLayer. Possible not Auth", e);
		} catch (Exception e){
			log.error("Error in Create GraphLayer", e);
		}
		
		
		return al;
	}
}
