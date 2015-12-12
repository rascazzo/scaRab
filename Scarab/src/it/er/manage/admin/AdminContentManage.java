package it.er.manage.admin;

import it.er.account.Account;
import it.er.account.AccountBreve;
import it.er.account.IAccount;
import it.er.account.Role;
import it.er.basic.Basic;
import it.er.dao.IParamDAO;
import it.er.dao.ISiteDAO;
import it.er.dao.Site;
import it.er.dao.Text;
import it.er.dao.TextContent;
import it.er.dao.TextSeries;
import it.er.generic.GenericTag;
import it.er.lang.LanguageAccess;
import it.er.lang.LanguageAdmin;
import it.er.lang.object.Lang;
import it.er.manage.BaseManage;
import it.er.manage.BaseManage.BaseManeError;
import it.er.manage.admin.navigation.NavigationAccess;
import it.er.object.Logged;
import it.er.object.Metatag;
import it.er.presentation.admin.object.AdminLayer;
import it.er.presentation.admin.object.HeadHTML;
import it.er.presentation.admin.object.MainHTML;
import it.er.presentation.admin.object.Panel;
import it.er.presentation.admin.object.fecomponent.SelectItem;
import it.er.presentation.admin.object.generic.GenericScarabResponse;
import it.er.service.UserPreferences;
import it.er.tag.SiteAccess;
import it.er.tag.Tag;
import it.er.tag.XTag;
import it.er.util.CustomException;
import it.er.util.MD5;
import it.er.util.SHA;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;

import com.mysql.jdbc.log.Log;

@Component("adminContentManage")
public class AdminContentManage extends BaseManage{
	
	private static Logger log = LogManager.getLogger(AdminContentManage.class);

	private SiteAccess siteManagement;
	
	private IParamDAO param;
	
	public SiteAccess getSiteManagement() {
		return siteManagement;
	}
	
	@Autowired
	public void setSiteManagement(SiteAccess siteManagement) {
		this.siteManagement = siteManagement;
	}

	private IAccount account;
	
	
	private UserPreferences userPreferences;
	protected void checkLang(Logged logged,String lang){
		if (!logged.getCurrLang().equals(lang)){
			logged.setCurrLang(lang);
		}
	}
	private LanguageAccess genericScarabLang;

	private LanguageAdmin languageAdmin;
	
	private XTag tagDao;
	
	@Autowired
	public void setTagDao(XTag tagDao) {
		this.tagDao = tagDao;
	}
	
	public LanguageAdmin getLanguageAdmin() {
		return languageAdmin;
	}
	@Autowired
	public void setLanguageAdmin(LanguageAdmin languageAdmin) {
		this.languageAdmin = languageAdmin;
	}
	public LanguageAccess getGenericScarabLang() {
		return genericScarabLang;
	}
	@Autowired
	public void setGenericScarabLang(LanguageAccess genericScarabLang) {
		this.genericScarabLang = genericScarabLang;
	}
	
	private NavigationAccess scarabNavigationAdmin;
	
	
	public NavigationAccess getScarabNavigationAdmin() {
		return scarabNavigationAdmin;
	}
	@Autowired
	public void setScarabNavigationAdmin(
			NavigationAccess scarabNavigationAdmin) {
		this.scarabNavigationAdmin = scarabNavigationAdmin;
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

	public IAccount getAccount() {
		return account;
	}

	public ISiteDAO getSite() {
		return site;
	}

	private TextContent textContentNoSql;
	
	

	public TextContent getTextContentNoSql() {
		return textContentNoSql;
	}
	@Autowired
	public void setTextContentNoSql(TextContent textContentNoSql) {
		this.textContentNoSql = textContentNoSql;
	}

	public AdminLayer initAdmin(HttpServletRequest req,
			HttpServletResponse res, ServletContext cx, 
			boolean isOut,String lang) throws CustomException{
		/*
		 * caso speciale init all site
		 */
		AdminLayer al = null; 
		boolean initFirst = false;
		try {		
			al = AdminContentManage.createAuthAdminLayer(userPreferences, req);
		} catch (CustomException e){
			try {
				List<Site> ls = siteManagement.getAllSite();
				if (ls != null && ls.size() > 0){
					throw e;
				}else {
					initFirst = true;
					al = new AdminLayer();
				}
			} catch (Exception e1) {
				log.warn("Not able to read sites",e1);
				throw e;
			}
		}
		checkLang(userPreferences.getLogged(), lang);
		al.setLang(genericScarabLang.getAllLangs());
		HeadHTML hd = new HeadHTML();
		hd.offerToMapNewTagname("script", "src", "/admin/libjs/scarab_admin.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("link", "href", "/admin/css/scarab_admin.css");
		hd.offerToMapExistTagname("link", "rel", "stylesheet");
		al.setHeads(hd);
		if (!initFirst)
			processAdminMenu(al,req,true,lang);
		MainHTML admin = new MainHTML();
		Panel p = new Panel();
		p.setWidth("100%");
		p.setLabel(genericScarabLang.getChilds(userPreferences.getLogged().getCurrLang(), "labels"));
		List<Panel> panels = new LinkedList<Panel>();
		panels.add(p);
		admin.setPanel(panels);
		al.setContent(admin);
		return al;
	}
	
	/*
	public AdminLayer navigationLogut(HttpServletRequest req,
			HttpServletResponse res, ServletContext cx, 
			boolean isOut) throws CustomException{
		AdminLayer al = null;
		try {
			if (!userPreferences.getLogged().isNew() && userPreferences.getLogged().getAccount() != null){
				userPreferences.logout(req);
				throw new CustomException(BaseManeError.LOGIN_REQUIRED.getDescription());
			} else {
				al = new AdminLayer();
			}
		} catch (CustomException e){
			log.info("login check...");
			if (e.getMessage().indexOf(BaseManeError.LOGIN_REQUIRED.getDescription()) != -1) {
				log.info(BaseManeError.LOGIN_REQUIRED.getDescription());
				throw e;
			} else {
				log.error(e.getMessage(),e);
				throw e;
			}
		} catch ( Exception e){
			log.error(e.getMessage(),e);
			throw e;
		}
		return al;
	}*/
	
	public AdminLayer initAdminLogin(HttpServletRequest req,
			HttpServletResponse res, ServletContext cx, 
			boolean isOut,String lang) throws CustomException{
		AdminLayer al = null;
		try {
			if (!userPreferences.getLogged().isNew() && userPreferences.getLogged().getAccount() != null){
				throw new CustomException(BaseManeError.LOGIN_DONE.getDescription());
			} else {
				al = new AdminLayer();
			}
		} catch (CustomException e){
			log.info("login check...");
			if (e.getMessage().indexOf(BaseManeError.LOGIN_DONE.getDescription()) != -1) {
				log.info(BaseManeError.LOGIN_DONE.getDescription());
				throw e;
			} else {
				log.error(e.getMessage(),e);
				throw e;
			}
		} catch ( Exception e){
			log.error(e.getMessage(),e);
			throw e;
		}
		checkLang(userPreferences.getLogged(), lang);
		al.setLang(genericScarabLang.getAllLangs());
		HeadHTML hd = new HeadHTML();
		hd.offerToMapNewTagname("script", "src", "/jquery/jquery-2.1.4.min.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/admin/libjs/scarab_admin.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/js/mobile.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("link", "href", "/admin/css/scarab_admin.css");
		hd.offerToMapExistTagname("link", "rel", "stylesheet");
		al.setHeads(hd);
		
		processAdminMenu(al,req,false,lang);
		MainHTML admin = new MainHTML();
		Panel p = new Panel();
		p.setHeight("70em");
		p.setWidth("100%");
		List<Panel> panels = new LinkedList<Panel>();
		panels.add(p);
		admin.setPanel(panels);
		al.setContent(admin);
		
		return al;
	}

	public AdminLayer userRegistration(HttpServletRequest req,
			HttpServletResponse res, ServletContext cx, 
			boolean isOut,String lang) throws CustomException{
		AdminLayer al = AdminContentManage.createAuthAdminLayer(userPreferences, req);
		
		HeadHTML hd = new HeadHTML();
		hd.offerToMapNewTagname("script", "src", "/admin/libjs/scarab_admin.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("link", "href", "/admin/css/scarab_admin.css");
		hd.offerToMapExistTagname("link", "rel", "stylesheet");
		hd.offerToMapNewTagname("script", "src", "/js/mobile.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		al.setHeads(hd);
		//String lang = userPreferences.getLogged().getCurrLang();
		
		processAdminMenu(al,req,true,lang);
		MainHTML admin = new MainHTML();
		Panel p = new Panel();
		p.setWidth("100%");
		List<Panel> panels = new LinkedList<Panel>();
		panels.add(p);
		admin.setPanel(panels);
		al.setContent(admin);
		return al;
	}
	
	public AdminLayer newText(HttpServletRequest req,
			HttpServletResponse res, ServletContext cx, 
			boolean isOut,String lang) throws CustomException{
		AdminLayer al = AdminContentManage.createAuthAdminLayer(userPreferences, req);
		checkLang(userPreferences.getLogged(), lang);
		al.setLang(genericScarabLang.getAllLangs());
		HeadHTML hd = new HeadHTML();
		hd.offerToMapNewTagname("link", "href", "/admin/css/scarab_admin.css");
		hd.offerToMapExistTagname("link", "rel", "stylesheet");
		hd.offerToMapNewTagname("link", "href", "/css/cmp.css");
		hd.offerToMapExistTagname("link", "rel", "stylesheet");
		hd.offerToMapNewTagname("script", "src", "/jquery/jquery-2.1.4.min.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/admin/libjs/scarab_admin.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/js/mobile.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/admin/libjs/scarabSt.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/js/loader.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/js/tree.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		al.setHeads(hd);
		//String lang = userPreferences.getLogged().getCurrLang();
		
		List<Site> lsite = null;
		List<Lang> langs = null;
		try {
			langs = genericScarabLang.getAllLangsObj();
		} catch (Exception e) {
			log.error("error in create object, lang, ",e);
			throw new CustomException(e);
		}
		
		processAdminMenu(al,req,true,lang);
		List<GenericTag> label = genericScarabLang.getChilds(lang, "labels");
		List<GenericTag> text = genericScarabLang.getChilds(lang, "texts");
		/*
		MainHTML admin = new MainHTML();
		Panel p = new Panel();
		p.setWidth("100%");
		p.setLabel(label);
		p.setText(text);
		p.setNamePanel("main");
		if (lsite != null) {
			Iterator<Site> i = lsite.iterator();
			List<SelectItem> lselectSite = new ArrayList<SelectItem>(); 
			while (i.hasNext()) {
				Site st = i.next();
				lselectSite.add(new SelectItem(st.getDomain(), st.getIdsite()));
			}
			SelectItem.insertDefaultItem(lselectSite);
			p.setSite(lselectSite);
		}*/
		MainHTML admin = new MainHTML();
		Panel p = new Panel();
		p.setHeight("70em");
		p.setWidth("100%");
		p.setLabel(label);
		p.setText(text);
		p.setNamePanel("main");
		List<GenericTag> mainSites = new ArrayList<GenericTag>();
		try {
			lsite = siteManagement.getAllSite();
			if (lsite != null) {
				Iterator<Site> i = lsite.iterator();
				List<SelectItem> lselectSite = new ArrayList<SelectItem>(); 
				while (i.hasNext()) {
					Site st = i.next();
					lselectSite.add(new SelectItem(st.getDomain(), st.getIdsite()));
					GenericTag mSite = new GenericTag();
					mSite.setName(st.getDomain());
					mSite.setValue(st.getIdsite());
					if (st.getIdsite().equals(siteManagement.getCurrentSite(req).getIdsite()))
						mSite.setMeta("main");
					mainSites.add(mSite);
				}
				SelectItem.insertDefaultItem(lselectSite);
				p.setSite(lselectSite);
			}
		} catch (Exception e) {
			log.error("error in list sites; main/current",e);
			throw new CustomException(e);
		}
		List<Panel> panels = new LinkedList<Panel>();
		panels.add(p);
		admin.setPanel(panels);
		admin.setSite(mainSites);
		admin.setLabel(label);
		admin.setText(text);
		al.setContent(admin);
		if (langs != null) {
			Iterator<Lang> i = langs.iterator();
			List<SelectItem> lselectLang = new ArrayList<SelectItem>(); 
			while (i.hasNext()) {
				Lang st = i.next();
				lselectLang.add(new SelectItem(st.getSuffix(),st.getType()));
			}
			SelectItem.insertDefaultItem(lselectLang);
			p.setLang(lselectLang);
		}
		
		return al;
	}
	
	public AdminLayer newTextSeries(HttpServletRequest req,
			HttpServletResponse res, ServletContext cx, 
			boolean isOut,String lang) throws CustomException{
		return this.newText(req, res, cx, isOut, lang);
	}
	
	public AdminLayer initsite(HttpServletRequest req,
			HttpServletResponse res, ServletContext cx,
			String username,
			String password,
			String confirmpassword,
			String email,
			String title,
			String subtitle,
			String domain,
			String xsltpath,
			String adminxsltpath,
			String webcontentpath,
			String mainlang,
			String mixedlang,
			boolean isOut) throws CustomException{
		AdminLayer al = new AdminLayer();
		if (username != null && !username.isEmpty() &&
				password != null && !password.isEmpty() &&
						confirmpassword != null && !confirmpassword.isEmpty() &&
								email != null && !email.isEmpty() &&
										title != null && !title.isEmpty() &&
												subtitle != null && !subtitle.isEmpty() &&
														domain != null && !domain.isEmpty() &&
																xsltpath != null && !xsltpath.isEmpty() &&
																		adminxsltpath != null && !adminxsltpath.isEmpty() &&
																				webcontentpath != null && !webcontentpath.isEmpty() &&
																						mainlang != null && !mainlang.isEmpty() &&
																						mixedlang != null && !mixedlang.isEmpty()){
			if (!password.equals(confirmpassword)){
				throw new CustomException("password not valid");
			}
		} else {
			throw new CustomException("form not valid");
		}
		String md5Domain = null;
		String md5Password = null;
		String md5User = null;
		String shaUserId = null;
		try {
			md5Domain = MD5.getHash(domain);
			md5Password = MD5.getHash(password);
			md5User = MD5.getHash(email);
			//Logic to move
			shaUserId = SHA.getHash1(email+password+username);
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
			throw new CustomException(e.getCause(), "Digest error");
		}
		
		Site s = new Site(domain);
		s.setAdminxsltpath(adminxsltpath);
		s.setIdsite(md5Domain);
		s.setMainlang(mainlang);
		s.setMixedlang(mixedlang.equals("on") || mixedlang.equals("true")?true:false);
		s.setSubtitle(subtitle);
		s.setTitle(title);
		s.setWebcontentpath(webcontentpath);
		s.setXsltpath(xsltpath);
		
		Account a = new Account();
		a.setUsername(md5User);
		a.setPassword(md5Password);
		a.seteMail(email);
		a.setMainsuperusersite(s.getIdsite());
		a.setUser_id(shaUserId);
		
		Role r = new Role();
		r.setRuolo("superadmin");//to do Constant
		
		AccountBreve testExistAcc= null;
		TransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus status = site.getTransactionManager().getTransaction(definition); 
		
		try {
			
			
			testExistAcc = account.loadAccountbreve(a.getUser_id());
			if (testExistAcc == null){
				site.saveSite(s);
				site.saveSuperAccount(a, md5Password);
				r.setUtenteId(a.getUser_id());
				account.saveSuperRoleAfterSuperAccout(r);
			} else {
				site.saveSite(s);
			}
				
			site.getTransactionManager().commit(status);
		} catch (Exception e){
			log.error("error in create site and superUser",e);
			site.getTransactionManager().rollback(status);
		}
		
		
		
		return al;
		
	}
	
	public int insertText(HttpServletRequest req,
			HttpServletResponse res, ServletContext cx,
			String tagname,
			String sitename,
			String title,
			String order,
			String archive,
			String lang,
			String body,
			String authorinfo,
			String titleinfo,
			String imageinfo,
			String keysinfo,
			String descriptioninfo,
			boolean isOut) throws CustomException{
		AdminLayer al = AdminContentManage.createAuthAdminLayer(userPreferences, req);
		checkLang(userPreferences.getLogged(), lang);
		Metatag meta = new Metatag();
		setMetaKeys(authorinfo, titleinfo, imageinfo, descriptioninfo, keysinfo, lang, meta);
		al.setLang(genericScarabLang.getAllLangs());
		Text t = new Text();
		if (archive != null && archive.equals("on"))
			t.setArchive(true);
		else
			t.setArchive(false);
		t.setBody(body);
		t.setSitename(sitename);
		t.setTagname(tagname);
		t.setTitle(title);
		t.setOrder(order);
		t.setTextIdUser(userPreferences.getLogged().getAccount().getUser_id());
		int r = 0;
		try {
			t.cleanArgument();
			r = textContentNoSql.insert(t,lang,meta);
		} catch (Exception e){
			log.error(e.getMessage(),e);
			r = 0;
		}
		return r;
		
	}
	
	public int insertTextSeries(HttpServletRequest req,
			HttpServletResponse res, ServletContext cx,
			String tagname,
			String sitename,
			String title,
			String order,
			String archive,
			String lang,
			String body,
			String colName,
			String serieTitle,
			String serieSubtitle,
			String authorinfo,
			String titleinfo,
			String imageinfo,
			String keysinfo,
			String descriptioninfo,
			boolean isOut) throws CustomException{
		AdminLayer al = AdminContentManage.createAuthAdminLayer(userPreferences, req);
		checkLang(userPreferences.getLogged(), lang);
		al.setLang(genericScarabLang.getAllLangs());
		Metatag meta = new Metatag();
		setMetaKeys(authorinfo, titleinfo, imageinfo, descriptioninfo, keysinfo, lang, meta);
		TextSeries ts = new TextSeries();
		Text t = new Text();
		if (archive != null && archive.equals("on"))
			t.setArchive(true);
		else
			t.setArchive(false);
		t.setBody(body);
		t.setSitename(sitename);
		t.setTagname(tagname);
		t.setTitle(title);
		t.setOrder(order);
		t.setTextIdUser(userPreferences.getLogged().getAccount().getUser_id());
		ts.getSerie().add(t);
		ts.setSubtitle(serieSubtitle);
		ts.setTitle(serieTitle);
		int r = 0;
		
		try {
			r = textContentNoSql.insertSeries(ts,colName,lang,meta);
		} catch (Exception e){
			log.error(e.getMessage(),e);
			r = 0;
		}
		return r;
		
	}
	
	public AdminLayer newUser(HttpServletRequest req,
			HttpServletResponse res, ServletContext cx,
			String username,
			String password,
			String confirmpassword,
			String email,
			String domain,
			String privacy,
			boolean isOut) throws CustomException{
		AdminLayer al = AdminContentManage.createAuthAdminLayer(userPreferences, req);
		
		if (username != null && !username.isEmpty() &&
				password != null && !password.isEmpty() &&
						confirmpassword != null && !confirmpassword.isEmpty() &&
								email != null && !email.isEmpty() &&
										domain != null && !domain.isEmpty() &&
												privacy != null && !privacy.isEmpty()){
			if (!password.equals(confirmpassword)){
				throw new CustomException("password not valid");
			}
		} else {
			throw new CustomException("form not valid");
		}
		String md5Domain = null;
		String md5Password = null;
		String md5User = null;
		String shaUserId = null;
		try {
			md5Domain = MD5.getHash(domain);
			md5Password = MD5.getHash(password);
			md5User = MD5.getHash(email);
			//Logic to move
			shaUserId = SHA.getHash1(email+password+username);
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
			throw new CustomException(e.getCause(), "Digest error");
		}
		
		Site s = new Site(domain);
		
		
		Account a = new Account();
		a.setUsername(md5User);
		a.setPassword(md5Password);
		a.seteMail(email);
		a.setUser_id(shaUserId);
		a.setPrivacy(privacy.equals("on")?true:false);
		Role r = new Role();
		r.setRuolo("user");//to do Constant
		
		TransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus status = account.getTransactionManager().getTransaction(definition); 
		
		try {
			
			s = site.readSite(s.getDomain());
			if (s.getIdsite() != null && !s.getIdsite().isEmpty()){
				account.saveAccount(a, md5Password);
				r.setUtenteId(a.getUser_id());
				account.saveRoleAfterAccout(r);
				account.getTransactionManager().commit(status);
			} else {
				new Exception("no site");
			}
		} catch (Exception e){
			log.error("error in create user",e);
			site.getTransactionManager().rollback(status);
		}
		
		
		
		
		
		
		
		
		return al;
		
	}
	
	@Autowired
	public void setParam(IParamDAO param) {
		this.param = param;
	}
	@Autowired
	public void setAccount(IAccount account) {
		this.account = account;
	}
	@Autowired
	public void setSite(ISiteDAO site) {
		this.site = site;
	}

	public AdminLayer adminBase(HttpServletRequest req,
			HttpServletResponse res, ServletContext cx, 
			boolean isOut,String lang) throws CustomException{
		AdminLayer al = AdminContentManage.createAuthAdminLayer(userPreferences, req);
		checkLang(userPreferences.getLogged(), lang);
		al.setLang(genericScarabLang.getAllLangs());
		HeadHTML hd = new HeadHTML();
		hd.offerToMapNewTagname("script", "src", "/jquery/jquery-2.1.4.min.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/admin/libjs/scarab_admin.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/js/mobile.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("link", "href", "/admin/css/scarab_admin.css");
		hd.offerToMapExistTagname("link", "rel", "stylesheet");
		al.setHeads(hd);
		//String lang = userPreferences.getLogged().getCurrLang();
		
		processAdminMenu(al,req,true,lang);
		List<GenericTag> label = genericScarabLang.getChilds(lang, "labels");
		List<GenericTag> text = genericScarabLang.getChilds(lang, "texts");
		MainHTML admin = new MainHTML();
		Panel p = new Panel();
		p.setHeight("70em");
		p.setWidth("100%");
		p.setLabel(label);
		p.setText(text);
		p.setNamePanel("main");
		List<Site> lsite = null;
		List<GenericTag> mainSites = new ArrayList<GenericTag>();
		try {
			lsite = siteManagement.getAllSite();
			if (lsite != null) {
				Iterator<Site> i = lsite.iterator();
				List<SelectItem> lselectSite = new ArrayList<SelectItem>(); 
				while (i.hasNext()) {
					Site st = i.next();
					lselectSite.add(new SelectItem(st.getDomain(), st.getIdsite()));
					GenericTag mSite = new GenericTag();
					mSite.setName(st.getDomain());
					mSite.setValue(st.getIdsite());
					if (st.getIdsite().equals(siteManagement.getCurrentSite(req).getIdsite()))
						mSite.setMeta("main");
					mainSites.add(mSite);
				}
				SelectItem.insertDefaultItem(lselectSite);
				p.setSite(lselectSite);
			}
		} catch (Exception e) {
			log.error("error in list sites; main/current",e);
			throw new CustomException(e);
		}
		List<Panel> panels = new LinkedList<Panel>();
		panels.add(p);
		admin.setPanel(panels);
		admin.setSite(mainSites);
		admin.setLabel(label);
		admin.setText(text);
		al.setContent(admin);
		
		return al;
	}
	
	@SuppressWarnings("unchecked")
	public AdminLayer getCreateDBSidebar(HttpServletRequest req,
			HttpServletResponse res, ServletContext cx, 
			boolean isOut,String lang) throws CustomException{
		AdminLayer al = AdminContentManage.createAuthAdminLayer(userPreferences, req);
		checkLang(userPreferences.getLogged(), lang);
		al.setLang(genericScarabLang.getAllLangs());
		HeadHTML hd = new HeadHTML();
		hd.offerToMapNewTagname("link", "href", "/admin/css/scarab_admin.css");
		hd.offerToMapExistTagname("link", "rel", "stylesheet");
		hd.offerToMapNewTagname("link", "href", "/css/cmp.css");
		hd.offerToMapExistTagname("link", "rel", "stylesheet");
		hd.offerToMapNewTagname("script", "src", "/jquery/jquery-2.1.4.min.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/admin/libjs/scarab_admin.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/js/mobile.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/admin/libjs/scarabSt.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/js/loader.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/js/tree.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		
		
		al.setHeads(hd);
		//String lang = userPreferences.getLogged().getCurrLang();
		List<Site> lsite = null;
		List<Tag> lfath = null;
		List<Tag> lson = null;
		Map<Site,List<Tag>> mapSon = null;
		Map<Site,List<Tag>> map = null;
		Set<Site> setSite =null;
		Set<Site> setSiteSon = null;
		try {
			lsite = siteManagement.getAllSite();
			map = tagDao.getAllRootTag();
			if (map != null)
				setSite = map.keySet();
			for (Site s:setSite){
				List<Tag> sFather = (List<Tag>) siteManagement.containsAndGetObject(map, s);
				for (Tag sf:sFather){
					if (mapSon == null){
						mapSon = tagDao.getTags(sf.getTagname(), true);
					} else {
						((List<Tag>)siteManagement.containsAndGetObject(mapSon, s)).addAll((List<Tag>) siteManagement.containsAndGetObject(tagDao.getTags(sf.getTagname(), true),s));
					}
				}
			}
			if (mapSon != null)
				setSiteSon = mapSon.keySet(); 
			
			lfath = (List<Tag>) siteManagement.containsAndGetObject(map, siteManagement.getCurrentSite(req));
			lson = (List<Tag>) siteManagement.containsAndGetObject(mapSon, siteManagement.getCurrentSite(req));
		} catch (Exception e) {
			log.error("error in create object, site, father",e);
			throw new CustomException(e);
		}
		
		processAdminMenu(al,req,true,lang);
		List<GenericTag> label = genericScarabLang.getChilds(lang, "labels");
		List<GenericTag> text = genericScarabLang.getChilds(lang, "texts");
		MainHTML admin = new MainHTML();
		Panel p = new Panel();
		p.setWidth("100%");
		p.setLabel(label);
		p.setText(text);
		p.setNamePanel("main");
		List<GenericTag> mainSites = new ArrayList<GenericTag>();
		try {
			if (lsite != null) {
				Iterator<Site> i = lsite.iterator();
				List<SelectItem> lselectSite = new ArrayList<SelectItem>(); 
				while (i.hasNext()) {
					Site st = i.next();
					lselectSite.add(new SelectItem(st.getDomain(), st.getIdsite()));
					GenericTag mSite = new GenericTag();
					mSite.setName(st.getDomain());
					mSite.setValue(st.getIdsite());
					if (st.getIdsite().equals(siteManagement.getCurrentSite(req).getIdsite()))
						mSite.setMeta("main");
					mainSites.add(mSite);
				}
				SelectItem.insertDefaultItem(lselectSite);
				p.setSite(lselectSite);
			}
		} catch (Exception e) {
			log.error("error in list sites; main/current",e);
			throw new CustomException(e);
		}
		List<SelectItem> lselectFather = null;
		try {
			lselectFather = new ArrayList<SelectItem>(); 
			if (lfath != null){
				Iterator<Tag> t = lfath.iterator();
				while (t.hasNext()) {
					Tag ta = t.next();
					lselectFather.add(new SelectItem(ta.getTagname(), ta.getTagname()));
					GenericTag g = new GenericTag();
					g.setMeta(siteManagement.getCurrentSite(req).getIdsite());
					g.setName(ta.getTagname());
					g.setValue(ta.getTextvalue());
					admin.getFather().add(g); 
				}
				SelectItem.insertDefaultItem(lselectFather);
				p.setFather(lselectFather);
			}
			if (lson != null){
				Iterator<Tag> t = lson.iterator();
				while (t.hasNext()) {
					Tag ta = t.next();
					GenericTag g = new GenericTag();
					g.setMeta(siteManagement.getCurrentSite(req).getIdsite());
					g.setName(ta.getTagname());
					g.setValue(ta.getTextvalue());
					admin.getSon().add(g); 
				}
			}
		} catch (Exception e) {
			log.error("error in create object, site, father; main tagsite s for current",e);
			throw new CustomException(e);
		}
		List<Panel> panels = new LinkedList<Panel>();
		panels.add(p);
		admin.setPanel(panels);
		admin.setLabel(label);
		admin.setText(text);
		admin.setSite(mainSites);
		al.setContent(admin);
		
		return al;
	}
	
	public AdminLayer getLangOverView(HttpServletRequest req,
			HttpServletResponse res, ServletContext cx, 
			boolean isOut,String lang) throws CustomException{
		AdminLayer al = AdminContentManage.createAuthAdminLayer(userPreferences, req);
		checkLang(userPreferences.getLogged(), lang);
		al.setLang(genericScarabLang.getAllLangs());
		HeadHTML hd = new HeadHTML();
		hd.offerToMapNewTagname("link", "href", "/admin/css/scarab_admin.css");
		hd.offerToMapExistTagname("link", "rel", "stylesheet");
		hd.offerToMapNewTagname("link", "href", "/css/cmp.css");
		hd.offerToMapExistTagname("link", "rel", "stylesheet");
		hd.offerToMapNewTagname("script", "src", "/jquery/jquery-2.1.4.min.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/admin/libjs/scarab_admin.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/js/mobile.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/admin/libjs/scarabSt.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/js/loader.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		hd.offerToMapNewTagname("script", "src", "/js/tree.js");
		hd.offerToMapExistTagname("script", "type", "text/javascript");
		al.setHeads(hd);
		//String lang = userPreferences.getLogged().getCurrLang();
		
		processAdminMenu(al,req,true,lang);
		List<GenericTag> label = genericScarabLang.getChilds(lang, "labels");
		List<GenericTag> text = genericScarabLang.getChilds(lang, "texts");
		MainHTML admin = new MainHTML();
		Panel p = new Panel();
		p.setWidth("100%");
		p.setLabel(label);
		p.setText(text);
		p.setNamePanel("main");
		List<Panel> panels = new LinkedList<Panel>();
		panels.add(p);
		admin.setPanel(panels);
		al.setContent(admin);
		
		return al;
	}
	
	public GenericScarabResponse langGeneralReload(HttpServletRequest req) throws CustomException{
		GenericScarabResponse r = null;
		try{
			r = AdminContentManage.createAuthScarabResponse(userPreferences, req);
		
			if (languageAdmin.reloadLang()){
				r.setSuccess(true);
				r.setStatus(200);
				return r;
			}
			else{
				r.setSuccess(false);
				r.setStatus(500);
				return r;
			}
		} catch (CustomException e){
			r = new GenericScarabResponse();
			r.setStatus(403);
			r.setSuccess(false);
		}
		return r;
	}
	
	public void changeLang(HttpServletRequest req,HttpServletResponse res,String lang) throws CustomException{
		Logged user =  userPreferences.getLogged();
		if (genericScarabLang.getAllLangs().contains(lang))
			user.setCurrLang(lang);
		else {
			log.warn("Try to set a wrong lang");
		}
	}
	
	
	
	private static AdminLayer createAuthAdminLayer(UserPreferences userPreferences, HttpServletRequest req) throws CustomException{
		AdminLayer al = null;
		try {
			al = new AdminLayer(userPreferences,AdminLayer.class,req,true);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e ) {
			log.info("User not in session #"+userPreferences.getLogged().isNew()+" error");
			if (e.getCause().getMessage().indexOf(BaseManeError.LOGIN_REQUIRED.getDescription()) != -1)
				throw new CustomException(new WebApplicationException(Status.UNAUTHORIZED));
			else
				throw new CustomException(new WebApplicationException(Status.BAD_REQUEST));
		} catch (Exception e){
			log.error("Error in Create Layer", e);
		}
		
		
		return al;
	}
	
	private static GenericScarabResponse createAuthScarabResponse(UserPreferences userPreferences, HttpServletRequest req) throws CustomException{
		GenericScarabResponse al = null;
		try {
			al = new GenericScarabResponse(userPreferences,GenericScarabResponse.class,req,true);
			
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e ) {
			log.info("User not in session #"+userPreferences.getLogged().isNew()+" error",e);
			throw new CustomException(e.getCause());
		} catch (Exception e){
			log.error("Error in Create Response", e);
			
		}
		
		
		return al;
	}

	private void processAdminMenu(AdminLayer l,HttpServletRequest req, boolean loginRequired, String lang) throws CustomException{
		try {
			l.setAdminMenus(scarabNavigationAdmin.genericNavigationFetch(lang, param.getParam(Basic.getParamadminappname()).getValue(),loginRequired));
		} catch (SQLException e) {
			log.error("error in create menu, lang",e);
			throw new CustomException(e);
		} catch (SAXException e) {
			log.error("error in create menu, parse",e);
			throw new CustomException(e);
		} catch (IOException e) {
			log.error("error in create menu, file xml",e);
			throw new CustomException(e);
		}
	}
}
