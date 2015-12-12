package it.er.presentation.admin;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.undo.CannotUndoException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import it.er.basic.Basic;
import it.er.dao.IParamDAO;
import it.er.layerintercept.AbstractIdentified;
import it.er.layerintercept.OverX;
import it.er.manage.BaseManage.BaseManeError;
import it.er.manage.admin.AdminContentManage;
import it.er.manage.sidebar.SidebarTagManage;
import it.er.object.Logged;
import it.er.presentation.admin.object.AdminLayer;
import it.er.presentation.admin.object.MainHTML;
import it.er.presentation.admin.object.generic.GenericScarabResponse;
import it.er.transform.Ofelia;
import it.er.transform.TransformGenerator;
import it.er.util.CustomException;
import it.er.util.SingletonLookup;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.tomcat.websocket.WsWebSocketContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/admin")
public class AdminContentOut extends BaseAdminContent {

	public static final Logger log = LogManager.getLogger(AdminContentOut.class);
	
	private IParamDAO param;
	
	
	@Autowired
	public void setParam(IParamDAO param) {
		this.param = param;
	}

	private AdminContentManage adminContentManage;
	
	
	@Autowired 
	public void setAdminContentManage(AdminContentManage adminContentManage) {
		this.adminContentManage = adminContentManage;
	}

	

	private Logged logged;
	
	public Logged getLogged() {
		return logged;
	}

	public void setLogged(Logged logged) {
		this.logged = logged;
	}
	
	@RequestMapping(value = "/{lang}/init", method = RequestMethod.GET, produces = {"text/html"})
	public @ResponseBody void initAdmin(HttpServletRequest req,
			HttpServletResponse res,@PathVariable String lang){
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(req.getServletContext());
		
		
		try {
			Ofelia contentManageing = adminContentManage.initAdmin(req, res, req.getServletContext(), true,lang);
			if (contentManageing.getContent() != null)
				((MainHTML) contentManageing.getContent()).setOutContext("init");
			final Ofelia off = contentManageing;
			
			this.processAdminLayer(tg, off,req, res, "adminBase.xslt");
		} catch (CustomException e){
			this.dorequiredLogin(e, res,lang);
		} catch (Exception e) {
			this.doOutError(e, res);
		}
		
	}
	
	
	private void internalAdminLogin(TransformGenerator tg,HttpServletRequest req,
			HttpServletResponse res,String lang) throws IOException, CustomException, Exception{
		final Ofelia contentManageing = adminContentManage.initAdminLogin(req, res, req.getServletContext(), false,lang);
		if (contentManageing.getContent() != null)
			((MainHTML) contentManageing.getContent()).setOutContext("loginAdmin");
		final Ofelia off = contentManageing;
			
		this.processAdminLayer(tg, off,req, res, "adminBase.xslt");
	}
	

	@RequestMapping(value = "/{lang}/loginAdmin", method = RequestMethod.GET, produces = {"text/html"})
	public @ResponseBody void initAdminLogin(HttpServletRequest req,
			HttpServletResponse res,@PathVariable String lang) {
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(req.getServletContext());
		try {
			this.internalAdminLogin(tg,req,res,lang);
		} catch (CustomException e){
			if (e.getMessage().indexOf(BaseManeError.LOGIN_DONE.getDescription()) != -1){
				try {
					res.sendRedirect(param.getParam(Basic.getParamadminappname()).getValue()+"/"+lang+"/administrator");
				} catch (IOException e1) {
					log.error("Custom Ex in transform #"+BaseManeError.LOGIN_DONE.getDescription(), e1);
				} catch (Exception e1){
					log.error(e1);
				}
			} else {
				log.error("Custom Ex in transform", e);
			}
		} catch (Exception e) {
			log.error("Error in transform", e);
			try {
				res.sendRedirect(getErrorpage());
			} catch (IOException e1) {
				log.error("Unable to redirect");
			}
		}
		
	}
	
	/*
	
	@RequestMapping(value = "/navigationLogout", method = RequestMethod.POST, produces = {"text/html"})
	public @ResponseBody void navigationLogout(HttpServletRequest req,
			HttpServletResponse res) {
		try {
			Ofelia off = adminContentManage.navigationLogut(req,res,req.getServletContext(),true);
		} catch (CustomException e){
			if (e.getMessage().indexOf(BaseManeError.LOGIN_REQUIRED.getDescription()) != -1){
				try {
					res.sendRedirect(param.getParam(Basic.getParamadminappname()).getValue()+"/"+adminContentManage.getUserPreferences().getLogged().getCurrLang()+"/loginAdmin");
				} catch (IOException e1) {
					log.error("Custom Ex in transform #"+BaseManeError.LOGIN_DONE.getDescription(), e1);
				} catch (Exception e1){
					log.error(e1);
				}
			} else {
				log.error("Custom Ex in transform", e);
			}
		} catch (Exception e) {
			log.error("Error in transform", e);
			try {
				res.sendRedirect(getErrorpage());
			} catch (IOException e1) {
				log.error("Unable to redirect");
			}
		}
		
	}*/
	
	@RequestMapping(value = "/initsite", method = RequestMethod.POST, produces = {"text/html"},
			consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody void initsite(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("confirmpassword") String confirmpassword,
			@RequestParam("email") String email,
			@RequestParam("title") String title,
			@RequestParam("subtitle") String subtitle,
			@RequestParam("domain") String domain,
			@RequestParam("xsltpath") String xsltpath,
			@RequestParam("adminxsltpath") String adminxsltpath,
			@RequestParam("webcontentpath") String webcontentpath,
			@RequestParam("mainlang") String mainlang,
			@RequestParam("mixedlang") String mixedlang){
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(req.getServletContext());
		
		
		try {
			final Ofelia off = adminContentManage.initsite(req, res, req.getServletContext(),
					username, password, confirmpassword, email, title, subtitle, domain, 
					xsltpath, adminxsltpath, webcontentpath, mainlang, mixedlang, true);
			this.processAdminLayer(tg, off,req, res, "adminBase.xslt");
		} catch (CustomException e){
			this.dorequiredLogin(e, res,adminContentManage.getUserPreferences().getLogged().getCurrLang());
		} catch (Exception e) {
			this.doOutError(e, res);
		}
	}

	@RequestMapping(value = "/{lang}/administrator", method = RequestMethod.GET, produces = {"text/html"})
	public @ResponseBody void adminBase(HttpServletRequest req,
			HttpServletResponse res,@PathVariable String lang){
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(req.getServletContext());
		try {
			
			Ofelia contentManageing = adminContentManage.adminBase(req, res, req.getServletContext(), true,lang);
			if (contentManageing.getContent() != null)
				((MainHTML) contentManageing.getContent()).setOutContext("administrator");
			final Ofelia off = contentManageing;
		
			this.processAdminLayer(tg, off,req,res, "adminBase.xslt");
		}catch (CustomException e){
			this.dorequiredLogin(e, res,lang);
		} catch (Exception e) {
			this.doOutError(e, res);
		}
	}
	
	@RequestMapping(value = "/{lang}/createsidebar", method = RequestMethod.GET, produces = {"text/html"})
	public @ResponseBody void getCreateDBSidebar(HttpServletRequest req,
			HttpServletResponse res,@PathVariable String lang){
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(req.getServletContext());
		try {
			
			Ofelia contentManageing = adminContentManage.getCreateDBSidebar(req, res, req.getServletContext(), true,lang);
			if (contentManageing.getContent() != null)
				((MainHTML) contentManageing.getContent()).setOutContext("createsidebar");
			final Ofelia off = contentManageing;
		
			this.processAdminLayer(tg, off, req,res, "adminBase.xslt");
		}catch (CustomException e){
			this.dorequiredLogin(e, res, lang);
		} catch (Exception e) {
			this.doOutError(e, res);
		}
	}
	
	@RequestMapping(value = "/{lang}/langoverview", method = RequestMethod.GET, produces = {"text/html"})
	public @ResponseBody void getLangOverView(HttpServletRequest req,
			HttpServletResponse res,@PathVariable String lang){
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(req.getServletContext());
		try {
			
			Ofelia contentManageing = adminContentManage.getLangOverView(req, res, req.getServletContext(), true,lang);
			if (contentManageing.getContent() != null)
				((MainHTML) contentManageing.getContent()).setOutContext("langoverview");
			final Ofelia off = contentManageing;
		
			this.processAdminLayer(tg, off, req,res, "adminBase.xslt");
		}catch (CustomException e){
			this.dorequiredLogin(e, res,lang);
		} catch (Exception e) {
			this.doOutError(e, res);
		}
	}
	
	@RequestMapping(value = "/langgeneralreload", method = RequestMethod.POST, produces = {"application/json"},
			consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody void langGeneralReload(HttpServletRequest req,
			HttpServletResponse res){
		try{
			final Ofelia contentManageing = adminContentManage.langGeneralReload(req);
			new StreamingOutput() {
				
				@Override
				public void write(OutputStream arg0) throws IOException,
						WebApplicationException {
					contentManageing.writeTo(contentManageing, GenericScarabResponse.class);
				}
			};
		} catch (CustomException e){
			this.dorequiredLogin(e, res,adminContentManage.getUserPreferences().getLogged().getCurrLang());
		} catch (Exception e) {
			this.doOutError(e, res);
		}
	}
	
	@RequestMapping(value = "/changelang", method = RequestMethod.POST, produces = {"text/html"},
			consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody void changeLang(HttpServletRequest req,
			HttpServletResponse res, @RequestParam String langchange){
		try{
			adminContentManage.changeLang(req,res,langchange);
			res.sendRedirect(param.getParam(Basic.getParamadminappname()).getValue()+"/"+adminContentManage.getUserPreferences().getLogged().getCurrLang()+"/administrator");
		} catch (CustomException e){
			this.dorequiredLogin(e, res,adminContentManage.getUserPreferences().getLogged().getCurrLang());
		} catch (Exception e) {
			this.doOutError(e, res);
		}
	}
	/*
	@RequestMapping(value = "/onChange", method = RequestMethod.POST, produces = {"application/json"},
			consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody void onChangeElement(HttpServletRequest req,
			HttpServletResponse res, @RequestParam String newValue, @RequestParam String element){
		try{
			adminContentManage.onChange(req,res,newValue,element);
		} catch (CustomException e){
			this.dorequiredLogin(e, res,adminContentManage.getUserPreferences().getLogged().getCurrLang());
		} catch (Exception e) {
			this.doOutError(e, res);
		}
	}*/
	
	
	
	
	
	@RequestMapping(value = "/{lang}/registration", method = RequestMethod.GET, produces = {"text/html"})
	public @ResponseBody void registration(HttpServletRequest req,
			HttpServletResponse res,@PathVariable String lang){
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(req.getServletContext());
		try {
			
			Ofelia contentManageing =  adminContentManage.userRegistration(req, res, req.getServletContext(), true,lang);
			if (contentManageing.getContent() != null)
				((MainHTML) contentManageing.getContent()).setOutContext("registration");
			final Ofelia off = contentManageing;
			this.processAdminLayer(tg, off, req,res, "adminBase.xslt");
		} catch (CustomException e){
			this.dorequiredLogin(e, res,lang);
		} catch (Exception e) {
			this.doOutError(e, res);
		}
		
	}
	
	@RequestMapping(value = "/{lang}/newtext", method = RequestMethod.GET, produces = {"text/html"})
	public @ResponseBody void newText(HttpServletRequest req,
			HttpServletResponse res,@PathVariable String lang){
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(req.getServletContext());
		try {
			
			Ofelia contentManageing =  adminContentManage.newText(req, res, req.getServletContext(), true,lang);
			if (contentManageing.getContent() != null)
				((MainHTML) contentManageing.getContent()).setOutContext("newtext");
			final Ofelia off = contentManageing;
			this.processAdminLayer(tg, off, req,res, "adminBase.xslt");
		} catch (CustomException e){
			this.dorequiredLogin(e, res,lang);
		} catch (Exception e) {
			this.doOutError(e, res);
		}
		
	}
	
	@RequestMapping(value = "/{lang}/newseries", method = RequestMethod.GET, produces = {"text/html"})
	public @ResponseBody void newTextSeries(HttpServletRequest req,
			HttpServletResponse res,@PathVariable String lang){
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(req.getServletContext());
		try {
			
			Ofelia contentManageing =  adminContentManage.newTextSeries(req, res, req.getServletContext(), true,lang);
			if (contentManageing.getContent() != null)
				((MainHTML) contentManageing.getContent()).setOutContext("newtextseries");
			final Ofelia off = contentManageing;
			this.processAdminLayer(tg, off, req,res, "adminBase.xslt");
		} catch (CustomException e){
			this.dorequiredLogin(e, res,lang);
		} catch (Exception e) {
			this.doOutError(e, res);
		}
		
	}
	
		
	@RequestMapping(value = "/newuser", method = RequestMethod.POST, produces = {"text/html"},
			consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public @ResponseBody void newUser(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("confirmpassword") String confirmpassword,
			@RequestParam("email") String email,
			@RequestParam("domain") String domain,
			@RequestParam("privacyconsent") String privacy) throws CustomException{
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(req.getServletContext());
		try{
			final Ofelia off = adminContentManage.newUser(req, res, req.getServletContext(),
					username, password, confirmpassword, email, domain,privacy, true);
			
				this.processAdminLayer(tg, off,req, res, "adminBase.xslt");
		} catch (CustomException e){
			this.dorequiredLogin(e, res, adminContentManage.getUserPreferences().getLogged().getCurrLang());
		} catch (Exception e) {
			this.doOutError(e, res);
		}
	}
	
	
	private void dorequiredLogin(CustomException e, HttpServletResponse res,String lang) {
		if (e.getCause() instanceof WebApplicationException){
			WebApplicationException ew = (WebApplicationException) e.getCause();
			if (ew.getResponse().getStatus() == Status.UNAUTHORIZED.getStatusCode()){
				try {
					res.sendRedirect(param.getParam(Basic.getParamadminappname()).getValue()+"/"+lang+"/loginAdmin");
				} catch (IOException e1) {
					log.error("Custom Ex in transform #"+BaseManeError.LOGIN_REQUIRED.getDescription(), e1);
				} catch (Exception e1) {
					log.error(e1);
					try {
						res.sendError(Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage());
					} catch (IOException e2) {
						log.error("in send error custom", e);
					}
				}
			}
		} else {
			log.error("Custom Ex in transform", e);
			try {
				res.sendError(Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage());
			} catch (IOException e2) {
				log.error("in send error custom", e2);
			}
		}
	}
	
	private void doOutError(Exception e,HttpServletResponse res){
		log.error("error in XSLT transform", e);
		try {
			res.sendError(Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage());
		} catch (IOException e1) {
			log.error("in send error custom",e1);
		}
	}
	
	private void processAdminLayer(TransformGenerator tg, Ofelia off, HttpServletRequest req, HttpServletResponse res, String xslt) throws IOException{
		tg.doXMLProcessing(req,getXsltadminpath(xslt), null,res.getOutputStream(), off,AdminLayer.class);
	}
	
	private void processXRabAdminLayer(TransformGenerator tg, Ofelia off, HttpServletResponse res, String xrab) throws IOException{
		tg.doXRabProcessing(getXRabadminPath(off.getLogged().isNew()?"login.rab":xrab), null, res.getOutputStream(), off, AdminLayer.class); 
		
	}
}
