package it.er.util;

import it.er.account.AccountDAO;
import it.er.account.IAccount;
import it.er.basic.Basic;
import it.er.dao.IParamDAO;
import it.er.dao.ISiteDAO;
import it.er.dao.ParamDAO;
import it.er.dao.SiteDAO;
import it.er.dao.TextContent;
import it.er.dao.TextContentNoSqlDAO;
import it.er.lang.LanguageAccess;
import it.er.lang.LanguageGenericManageBean;
import it.er.layerintercept.Over;
import it.er.layerintercept.OverX;
import it.er.manage.admin.AdminContentManage;
import it.er.manage.contentManage.SdManage;
import it.er.manage.contentManage.WebContentManage;
import it.er.manage.graph.GraphManage;
import it.er.manage.sidebar.SidebarTagManage;
import it.er.service.SideBarBean;
import it.er.service.SideBarX;
import it.er.service.SidebarAccess;
import it.er.service.SidebarControl;
import it.er.service.UserPreferences;
import it.er.service.UserPreferencesBean;
import it.er.service.UserSessionService;
import it.er.tag.SiteAccess;
import it.er.tag.SiteManagement;
import it.er.tag.TagDAOBean;
import it.er.tag.XTag;
import it.er.transform.TransformGenerator;
import it.er.user.UserFELayer;

import javax.servlet.ServletContext;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



//import resource.WebPageGen;


public class SingletonLookup extends Basic {

	
	
//	public static ApplicationContext getStyBean(){
//		return new ClassPathXmlApplicationContext("classpath:"+getStrategybean());
//	}
			
	
	
	private static WebApplicationContext wCtx = null;
	
	public static JdbcTemplate getJdbcTemplate(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		JdbcTemplate bean = wCtx.getBean("jdbcTemplate", JdbcTemplate.class);
		wCtx = null;
        return bean;
}
	
	
	
	
	
	
	
	public static TextContent getNoSqlTextDAO(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		TextContent bean = wCtx.getBean("textContentNoSql", TextContentNoSqlDAO.class);
		wCtx = null;
		return bean; 
	}
	
	public static IParamDAO getParamDAO(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		IParamDAO bean = wCtx.getBean("param", ParamDAO.class);
		wCtx = null;
		return bean; 
	}
	
	
	
	public static IAccount getAccountDAO(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		IAccount bean = wCtx.getBean("account", AccountDAO.class);
		wCtx = null;
		return bean; 
	}
	
	public static ISiteDAO getSiteDAO(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		ISiteDAO bean = wCtx.getBean("site", SiteDAO.class);
		wCtx = null;
		return bean; 
	}
	
	/*
	 * 
	 * Strategy
	 */
	
	
	public static TransformGenerator getTransformGenerator(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		TransformGenerator bean = wCtx.getBean("transfromStyGenerator",TransformGenerator.class);
		wCtx = null;
		return bean; 
	}
	
	
	
	
	
	
	/*
	 * Service
	 */

	public static SideBarX getSideBarX(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		SideBarX bean = wCtx.getBean("sidebar",SideBarBean.class);
		wCtx = null;
		return bean;
	}
	
	
	 
	public static XTag getXTag(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		XTag bean = wCtx.getBean("tagDao",TagDAOBean.class);
		wCtx = null;
		return bean;
	}
	
	
	public static UserSessionService getSimpleUserService(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		UserSessionService bean = wCtx.getBean("userService",UserFELayer.class);
		wCtx = null;
		return bean; 
	}
	
	public static UserPreferences getUserPreferences(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		UserPreferences bean = wCtx.getBean("userPreferences",UserPreferencesBean.class);
		wCtx = null;
		return bean; 
	}
	
	
	public static SdManage getSdManage(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		SdManage bean = wCtx.getBean("sdManage",SdManage.class);
		wCtx = null;
		return bean; 
	}
	
	
	public static WebContentManage getWebContentManage(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		WebContentManage bean = wCtx.getBean("webContentManage",WebContentManage.class);
		wCtx = null;
		return bean; 
	}
	
	public static AdminContentManage getAdminContentManage(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		AdminContentManage bean = wCtx.getBean("adminContentManage",AdminContentManage.class);
		wCtx = null;
		return bean; 
	}
	
	
	public static LanguageAccess getLanguageAccess(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		LanguageAccess bean = wCtx.getBean("genericScarabLang",LanguageGenericManageBean.class);
		wCtx = null;
		return bean; 
	}
	
	public static SiteAccess getSiteManagement(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		SiteAccess bean = wCtx.getBean("siteManagement",SiteManagement.class);
		wCtx = null;
		return bean; 
	}
	
	public static SidebarAccess getSideBarTAccess(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		SidebarAccess bean = wCtx.getBean("sidebartagmanage",SidebarTagManage.class);
		wCtx = null;
		return bean; 
	}
	
	public static SidebarControl getSideBarTControl(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		SidebarControl bean = wCtx.getBean("sidebar",SideBarBean.class);
		wCtx = null;
		return bean; 
	}
	

	public static GraphManage getGraphManage(ServletContext sc){
		wCtx = WebApplicationContextUtils.getWebApplicationContext(sc);
		GraphManage bean = wCtx.getBean("graphContentManage",GraphManage.class);
		wCtx = null;
		return bean; 
	}
}
