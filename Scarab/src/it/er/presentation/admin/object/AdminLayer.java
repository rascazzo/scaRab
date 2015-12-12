package it.er.presentation.admin.object;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import it.er.layerintercept.IdentifiedIntercept;
import it.er.layerintercept.OverX;
import it.er.manage.BaseManage.BaseManeError;
import it.er.object.Logged;
import it.er.presentation.admin.BaseAdminContent;
import it.er.service.UserPreferences;
import it.er.tag.SiteAccess;
import it.er.transform.Ofelia;
import it.er.util.CustomException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


@XmlRootElement(name = "adminlayer")
public class AdminLayer extends BaseAdminContent implements Ofelia,IdentifiedIntercept{

	private static final Logger log = LogManager.getLogger(AdminLayer.class);
	
	private SiteAccess siteManagement;
	
	private HeadHTML heads;
	
	private MenuHTML adminMenu;
	
	private List<MenuElementMount> adminMenus;
	
	private MainHTML content;
	
	public AdminLayer(){
		super();
		this.logged = new Logged();
		this.logged.setNew(true);
		this.logged.setCurrLang("en");
	}
	
	public AdminLayer(UserPreferences u,Class <? extends BaseAdminContent> restservice,HttpServletRequest req, boolean requiredAuth) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, CustomException{
		super();
		this.setLogged((Logged) (restservice.getMethod("invokeServerIdentities", new Class[]{UserPreferences.class,HttpServletRequest.class,boolean.class}).invoke(this, new Object[]{u,req,requiredAuth})));
	}
	
	@XmlElement
	public HeadHTML getHeads() {
		return heads;
	}

	public void setHeads(HeadHTML heads) {
		this.heads = heads;
	}
	
	
	@XmlElement
	public MenuHTML getAdminMenu() {
		return adminMenu;
	}

	public void setAdminMenu(MenuHTML adminMenu) {
		this.adminMenu = adminMenu;
	}

	@Override
	public ByteArrayOutputStream writeTo(Object target, Class<?> type)
			throws IOException {
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			JAXBContext ctx = JAXBContext.newInstance(type);
			ctx.createMarshaller().marshal(target, os);
			} catch (JAXBException ex) {
				throw new RuntimeException(ex);
			}
		return os;
	}

	@Override
	public void invokeClientIdentities(HttpServletRequest req, OverX over) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Logged invokeServerIdentities(UserPreferences u, HttpServletRequest req, boolean requiredAuth)
			throws CustomException {
		Logged l = null;
		try{
			l = u.getLogged();		
		} catch (Exception e){
			if (l == null){
				l = new Logged();
				l.setNew(true);
				try {
					l.setCurrLang(siteManagement.getCurrentSite(req).getMainlang());
				} catch (Exception e1){
					log.error("Error in set Session current Default Logout site Lang");
				}
			}
		} 
		if (u.getLogged().isNew() && requiredAuth){
			throw (CustomException) new CustomException(BaseManeError.LOGIN_REQUIRED.getDescription());

		}
		return l;
	}

	@XmlElementWrapper(name="mainAdminnav")
	public  List<MenuElementMount> getAdminMenus() {
		return adminMenus;
	}

	public void setAdminMenus(List<MenuElementMount> adminMenus) {
		this.adminMenus = adminMenus;
	}
	
	
	@XmlElement(name = "mainpanel")
	@Override
	public MainHTML getContent() {
		return content;
	}

	public void setContent(MainHTML content) {
		this.content = content;
	}

	@Autowired
	public void setSiteManagement(SiteAccess siteManagement) {
		this.siteManagement = siteManagement;
	}

	
	

}
