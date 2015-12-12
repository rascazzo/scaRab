package it.er.presentation.admin;

import it.er.layerintercept.AbstractIdentified;
import it.er.manage.admin.AdminContentManage;
import it.er.presentation.admin.object.AdminLayer;
import it.er.presentation.admin.object.generic.GenericScarabResponse;
import it.er.util.CustomException;
import it.er.util.SingletonLookup;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@Path("/admincontentxmlsource")
public class AdminContent extends AbstractIdentified {
	
	private static Logger log = LogManager.getLogger(AdminContent.class);

	private AdminContentManage adminContentManage;
	
	
	public AdminContentManage getAdminContentManage() {
		return adminContentManage;
	}

	public void setAdminContentManage(AdminContentManage adminContentManage) {
		this.adminContentManage = adminContentManage;
	}

	private static final String wsInit = "init";
	
	@GET
	@Path(AdminContent.wsInit)
	@Produces("application/xml")
	public AdminLayer initAdmin(@Context HttpServletRequest req,
			@Context HttpServletResponse res, @Context ServletContext cx, 
			@DefaultValue("0") @QueryParam("isOut") boolean isOut,
			@DefaultValue("it") @QueryParam("lang") String lang) throws WebApplicationException, CustomException{
		adminContentManage = SingletonLookup.getAdminContentManage(cx);
		AdminLayer al = null;
		try {
			al = adminContentManage.initAdmin(req, res, cx, isOut,lang);
		} catch (CustomException e) {
			AdminContent.manageWSCustomException(e,AdminContent.wsInit);
		}
		return al;
	}
	
	private static final String wsLoginAdmin = "loginAdmin";
	
	@GET
	@Path(AdminContent.wsLoginAdmin)
	@Produces("application/xml")
	public AdminLayer initAdminLogin(@Context HttpServletRequest req,
			@Context HttpServletResponse res, @Context ServletContext cx, 
			@DefaultValue("0") @QueryParam("isOut") boolean isOut,
			@DefaultValue("it") @QueryParam("lang") String lang) throws WebApplicationException, CustomException{
		adminContentManage = SingletonLookup.getAdminContentManage(cx);
		AdminLayer al = null;
		try {
			al =  adminContentManage.initAdminLogin(req, res, cx, isOut,lang);
		} catch (CustomException e) {
			AdminContent.manageWSCustomException(e,AdminContent.wsLoginAdmin);
		}
		return al;
	}
	
	private static final String wsRegistration = "registration";
	
	@GET
	@Path(AdminContent.wsRegistration)
	@Produces("application/xml")
	public AdminLayer userRegistration(@Context HttpServletRequest req,
			@Context HttpServletResponse res, @Context ServletContext cx, 
			@DefaultValue("0") @QueryParam("isOut") boolean isOut,
			@DefaultValue("it") @QueryParam("lang") String lang) throws WebApplicationException, CustomException  {
		adminContentManage = SingletonLookup.getAdminContentManage(cx);
		AdminLayer al = null;
		try {
			al =  adminContentManage.userRegistration(req, res, cx, isOut,lang);
		} catch (CustomException e) {
			AdminContent.manageWSCustomException(e,AdminContent.wsRegistration);
		}
		return al;
	}
	
	private static final String wsInitsite = "initsite";
	
	@POST
	@Path(AdminContent.wsInitsite)
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	@Produces("application/xml")
	public AdminLayer initsite(@Context HttpServletRequest req,
			@Context HttpServletResponse res, @Context ServletContext cx,
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("confirmpassword") String confirmpassword,
			@FormParam("email") String email,
			@FormParam("title") String title,
			@FormParam("subtitle") String subtitle,
			@FormParam("domain") String domain,
			@FormParam("xsltpath") String xsltpath,
			@FormParam("adminxsltpath") String adminxsltpath,
			@FormParam("webcontentpath") String webcontentpath,
			@FormParam("mainlang") String mainlang,
			@FormParam("mixedlang") String mixedlang,
			boolean isOut) throws CustomException{
		adminContentManage = SingletonLookup.getAdminContentManage(cx);
		return adminContentManage.initsite(req, res, cx, username, password, confirmpassword, email, 
				title, subtitle, domain, xsltpath, adminxsltpath, webcontentpath, mainlang, mixedlang, isOut);
		
	}
	
	private static final String wsNewuser = "newuser";
	
	@POST
	@Path(AdminContent.wsNewuser)
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	@Produces("application/xml")
	public AdminLayer newUser(@Context HttpServletRequest req,
			@Context HttpServletResponse res, @Context ServletContext cx,
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("confirmpassword") String confirmpassword,
			@FormParam("email") String email,
			@FormParam("domain") String domain,
			@FormParam("privacyconsent") String privacy,
			boolean isOut) throws CustomException{
		adminContentManage = SingletonLookup.getAdminContentManage(cx);
		return adminContentManage.newUser(req, res, cx, username, password, confirmpassword, email, domain, privacy,isOut);
		
	}
	
	private static final String wsAdminBase = "adminBase";
	
	
	@GET
	@Path(AdminContent.wsAdminBase)
	@Produces("application/xml")
	public AdminLayer adminBase(@Context HttpServletRequest req,
			@Context HttpServletResponse res, @Context ServletContext cx, 
			@DefaultValue("0") @QueryParam("isOut") boolean isOut,
			@DefaultValue("it") @QueryParam("lang") String lang) throws WebApplicationException, CustomException {
		adminContentManage = SingletonLookup.getAdminContentManage(cx);
		AdminLayer al = null;
		try {
			al = adminContentManage.adminBase(req, res, cx, isOut,lang);
		} catch (CustomException e) {
			AdminContent.manageWSCustomException(e,AdminContent.wsAdminBase);
		}
		return al;
	}

	private static final String wsCreatesidebar = "createsidebar";
	
	@GET
	@Path(AdminContent.wsCreatesidebar)
	@Produces("application/xml")
	public AdminLayer getCreateDBSidebar(@Context HttpServletRequest req,
			@Context HttpServletResponse res, @Context ServletContext cx, 
			@DefaultValue("0") @QueryParam("isOut") boolean isOut,
			@DefaultValue("it") @QueryParam("lang") String lang) throws WebApplicationException, CustomException {
		adminContentManage = SingletonLookup.getAdminContentManage(cx);
		AdminLayer al = null;
		try {
			al = adminContentManage.getCreateDBSidebar(req, res, cx, isOut,lang);
		} catch (CustomException e) {
			AdminContent.manageWSCustomException(e,AdminContent.wsCreatesidebar);
		}
		return al;
	}
	
	private static final String wsNewtext = "newtext";
	
	@GET
	@Path(AdminContent.wsNewtext)
	@Produces("application/xml")
	public AdminLayer getNewText(@Context HttpServletRequest req,
			@Context HttpServletResponse res, @Context ServletContext cx, 
			@DefaultValue("0") @QueryParam("isOut") boolean isOut,
			@DefaultValue("it") @QueryParam("lang") String lang) throws WebApplicationException, CustomException {
		adminContentManage = SingletonLookup.getAdminContentManage(cx);
		AdminLayer al = null;
		try {
			al = adminContentManage.newText(req, res, cx, isOut, lang);
		} catch (CustomException e) {
			AdminContent.manageWSCustomException(e,AdminContent.wsNewtext);
		}
		return al;
	}
	
	private static final String wsLanggeneralreload = "langgeneralreload";
	
	@POST
	@Path(AdminContent.wsLanggeneralreload)
	@Produces("application/json")
	public GenericScarabResponse langGeneralReload(@Context ServletContext cx,
					@Context HttpServletRequest req){
		adminContentManage = SingletonLookup.getAdminContentManage(cx);
		GenericScarabResponse g = null;
		try {
			g = adminContentManage.langGeneralReload(req);
		} catch (CustomException e) {
			log.error("Failed to generate response");
			g.setSuccess(false);
			g.setStatus(403);
		}
		
		return g;
	}
	
	
	private static void manageWSCustomException(Object e,String calledPath) throws CustomException,WebApplicationException{
		if (e instanceof CustomException){
			CustomException c = (CustomException) e;
			if (c.getCause() instanceof WebApplicationException){
				WebApplicationException w = (WebApplicationException) c.getCause();
				if (w.getResponse() != null && w.getResponse().getStatus() == Status.UNAUTHORIZED.getStatusCode()){
					log.warn(String.format("%s - %s - %s",w.getMessage()!=null?w.getMessage():"called",AdminContent.class.getName(),calledPath));
					throw w;
				}
			} else {
				
				log.warn(c.getMessage(),c.getCause());
				throw c;
			}
		} else {
			log.error("WS error");
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
	}
}
