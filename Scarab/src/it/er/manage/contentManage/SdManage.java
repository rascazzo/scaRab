package it.er.manage.contentManage;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;

import it.er.account.IAccount;
import it.er.basic.BaseNoSql;
import it.er.dao.IParamDAO;
import it.er.dao.ISiteDAO;
import it.er.dao.Site;
import it.er.dao.Text;
import it.er.dao.TextContent;
import it.er.dao.TextContentNoSqlDAO;
import it.er.lang.LanguageAccess;
import it.er.manage.BaseManage;
import it.er.object.Layer;
import it.er.object.Metatag;
import it.er.object.NovitaContainer;
import it.er.object.NovitaPos;
import it.er.object.Pictures;
import it.er.service.SideBarX;
import it.er.service.UserPreferences;
import it.er.tag.SiteAccess;
import it.er.tag.XTag;
import it.er.util.CustomException;
import it.er.util.SingletonLookup;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("sdManage")
public class SdManage extends BaseManage{


	private static Logger log = LogManager.getLogger(SdManage.class);

	
	private IParamDAO param;
	
	
	private IAccount account;
	
	private SiteAccess siteManagement;
	
	
	public SiteAccess getSiteManagement() {
		return siteManagement;
	}
	@Autowired
	public void setSiteManagement(SiteAccess siteManagement) {
		this.siteManagement = siteManagement;
	}

	private UserPreferences userPreferences;
	
	
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
	
	private SideBarX sidebar = null;
	
	private XTag tagDao;
	
	
	
	public XTag getTagDao() {
		return tagDao;
	}
	@Autowired
	public void setTagDao(XTag xtag) {
		this.tagDao = xtag;
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
	
	@SuppressWarnings("unchecked")
	private String selectDaoValue(Site s,String tagname,ServletContext cx) throws Exception{
		sidebar = SingletonLookup.getSideBarX(cx);
		Map<String,Map<String,String>> sideBarMapValue = sidebar.getSideBarMapValue();
		Map<Site,Map<String,Map<String,Map<String,String>>>> sideBarMapValueSite = sidebar.getSideBarMapValueSite();
		if (sideBarMapValueSite != null){
			sideBarMapValue = (Map<String,Map<String,String>>) siteManagement.containsAndGetObject(sideBarMapValueSite, s);
		}
		Map<String,String> secondLiv = null;
		Set<String> pr = sideBarMapValue.keySet();
		boolean flag = false;
		Iterator<String> i = pr.iterator(); 
		while(i.hasNext()){
			secondLiv = sideBarMapValue.get(i.next());
			Set<String> sec = secondLiv.keySet();
			Iterator<String> i2 = sec.iterator();
			while(i2.hasNext()){
				if (i2.next().equals(tagname))
					flag =  true;
			}
		}
		if (flag){
			Map<String,Map<String,String>> sideBarMapOtherAttr = sidebar.getSideBarMapOtherAttr();
			String daoValue = sideBarMapOtherAttr.get(tagname).get("dao");
			//xDao = SingletonLookup.getXDao(cx);
			return daoValue;
		} else
			return "novita";
	}
	
	
	private boolean containsId(List<Integer> l, Integer id){
		boolean flag = false;
		Iterator<Integer> i = l.iterator();
		while(i.hasNext()){
			if (id.intValue() == i.next().intValue()){
				flag = true;
				return flag;
			}
		}
		return flag;
	}
	
	public Layer getTagname(HttpServletRequest req,
			ServletContext cx,HttpServletResponse res,
			String lang,
			String tagname,
			String argument,
			int start,
			int limit,
			int page,
			final boolean succ,
			final boolean prec) throws SQLException, CustomException{
		String tit = "";//account.getTitleLetele();
		Site s = null;
		checkLang(userPreferences.getLogged(), lang);
		try {
			 s = siteManagement.getCurrentSite(req); 
			 tit = s.getTitle();
		
		} catch (Exception eS){
			log.error(eS);
		}
		Layer layer = SdManage.createAuthLayer(userPreferences, req,false);
		layer.setTitle(tit);
		layer.setNavigation(tagname);
		param = SingletonLookup.getParamDAO(cx);
		//xDao = SingletonLookup.getXDao(cx);
		int limitElement = 0;
		try {
			limitElement= Integer.parseInt(param.getParam(getNumbernovitainpage()).getValue());
		} catch (NumberFormatException e) {
			log.warn("Unable to parse "+getNumbernovitainpage());
		} catch (SQLException e) {
			log.warn("Unable to get "+getNumbernovitainpage());
		}
		
		if (limit > 0){
			limitElement = limit;
		}
		
		Pictures pictures = null;
		NovitaContainer novConteiner = null;
		String daoValue = null;
		try {
			daoValue = selectDaoValue(s,tagname, cx);
		} catch (Exception e2) {
			log.error("Unable to get dao",e2);
		}
				
		if (prec)
			page--;
		else if (succ)
			page++;
		
		novita  = SingletonLookup.getNoSqlTextDAO(cx);
		if (tagDao.getIdTagname(tagname, s) != null)
			if (daoValue!=null){
				
				
				 if (daoValue.equals("novita")){
					
						//int total = xDao.countXdao(tagname);
						//if (getOffset(page, limitElement)>total)
						//	return null;
						novConteiner = new NovitaContainer();
						novConteiner.setArchivio(false);
						try {
							Deque<Text> novList = null; 
							if (argument != null && !argument.isEmpty())		
								novList = novita.getTextListShort(limitElement, start, tagname, argument,lang);
							else
								novList = novita.getTextListShort(limitElement, start, tagname, null,lang);
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
						} catch (CustomException e){
							throw e;
						} catch (Exception e) {
							log.error("Unable to select Novita",e.getCause());
						}
						
						
					
				} 
			} else return null;
		else throw new CustomException(new WebApplicationException(404));
		
		layer.getContent().setText(novConteiner);
		layer.getContent().setPictures(pictures);
		
		/*Image ;meta */
		try {
			List<Metatag> lm = null;
			if (argument != null && !argument.isEmpty())	
				lm = novita.readMetatagByTitle(null, tagname, lang);
			else
				lm = novita.readMetatagByTitle(null, tagname, lang);
			if (lm != null && lm.size() > 0){
				this.addURLToMetaKey(lm.get(0), tagname, null, s , sidebar, lang, null, req.getQueryString());
				layer.setMetatag(lm.get(0));
			}	
		} catch (Exception e1) {
			log.error("Unable to read Metatag List",e1.getCause());
		}
		sidebar = SingletonLookup.getSideBarX(cx);
		if (s != null)
			try {
				layer.setSidebar(sidebar.getSidebar(s));
			} catch (Exception e) {
				log.warn(e.getMessage(),e);
			}
		setTextsAndLabels(layer, genericScarabLang ,lang);
		return layer;
	}
	
	public Layer getIdForTagname(HttpServletRequest req,
			ServletContext cx, HttpServletResponse res,
			String lang,
			String tagname,
			String argument,
			String id) throws SQLException, CustomException{
		
		
		String tit = "";
		Site s = null;
		checkLang(userPreferences.getLogged(), lang);
		try {
			 s = siteManagement.getCurrentSite(req);
			 tit = s.getTitle();
		
		} catch (Exception eS){
			log.error(eS);
		}
		Layer layer = SdManage.createAuthLayer(userPreferences, req,false);
		layer.setTitle(tit);
		layer.setNavigation(tagname+","+id);
		param = SingletonLookup.getParamDAO(cx);
		
		
		Pictures pictures = null;
		NovitaContainer novConteiner = null;
		
		String daoValue = null;
		try {
			daoValue = selectDaoValue(s,tagname, cx);
		} catch (Exception e2) {
			log.error(e2);
		}
		novita  = SingletonLookup.getNoSqlTextDAO(cx);
		if (tagDao.getIdTagname(tagname, s) != null)
			if (daoValue!=null){
				
				/*
				if (daoValue.equals("quadro")){
					quadro = SingletonLookup.getQuadroDao(cx);
					layer.setContextDao("quadro");
					try {
						List<String> ids = quadro.getIdsView(tagname);
						if (containsId(ids, id)){
							Quadro q = quadro.getQuadro(id);
							layer.setNavigation(layer.getNavigation()+" . "+q.getNome());
							String thumb = dinamicPath(q.getImage(), getQuadrithumbimagepath(), param);
							q.setImage(dinamicPath(q.getImage(), getQuadriimagepath(), param));
							pictures = new Pictures();
							pictures.setQuadro(q);
							metaTitle = q.getNome();
							//layer.getMetatag().setImage("http://" + host + "/" + thumb);
						
						} else {
							log.warn("id for "+tagname+" not exist");
							return null;
						}
						
					} catch (Exception e) {
						log.error("Unable to select Quadro List",e.getCause());
						return null;
					} finally{
						quadro = null;
					}
				} else*/ if (daoValue.equals("novita")){
				
						//int total = xDao.countXdao(tagname);
						//if (getOffset(page, limitElement)>total)
						//	return null;
						novConteiner = new NovitaContainer();
						novConteiner.setArchivio(false);
						try {
							List<Text> novList = null;
							if (argument != null && !argument.isEmpty())
								novList = novita.readTextList(TextContentNoSqlDAO.getNolimit(), 0, tagname, argument,id.toLowerCase(), lang);
							else
								novList = novita.readTextList(TextContentNoSqlDAO.getNolimit(), 0, tagname, null,id.toLowerCase(), lang);
							if (novList!=null&&novList.size()>0){
								if (novList.get(0) == null)
									new CustomException(new WebApplicationException(404));
								
							}
							novConteiner.setArticle(novList);
						} catch (CustomException e){
							throw e;
						} catch (Exception e) {
							log.error("Unable to select Novita",e.getCause());
						}
					
				} /*else if (daoValue.equals("cavalletto")){
					cavalletto = SingletonLookup.getCavallettoDAO(cx);
					layer.setContextDao("cavalletto");
					try {
						List<Integer> ids = cavalletto.getIdsView(tagname);
						if (containsId(ids, id)){
							Cavalletto c = cavalletto.getSingleSkizzo(id);
							String thumb = dinamicPath(c.getImage(), getSkizzithumbimagepath(), param);
							c.setImage(dinamicPath(c.getImage(), getSkizziimagepath(), param));
							pictures = new Pictures();
							pictures.setCavallettosing(c);
							
							//layer.getMetatag().setImage("http://" + host + "/" + thumb);
						} else {
							log.warn("id for "+tagname+" not exist");
							return null;
						}
					} catch (Exception e) {
						log.error("Unable to select Cavalletto List",e.getCause());
						return null;
					} finally{
						cavalletto = null;
					}
				} else if (daoValue.equals("acasatua")){
					acasatua = SingletonLookup.getAcasatuaDAO(cx);
					layer.setContextDao("acasatua");
					try {
						List<Integer> ids = acasatua.getIdsView(tagname);
						if (containsId(ids, id)){
							Acasatua a = acasatua.getSingleAcasatua(id);
							String thumb = dinamicPath(a.getImage(), getAcasathumbimagepath(), param);
							a.setImage(dinamicPath(a.getImage(), getAcasaimagepath(), param));
							pictures = new Pictures();
							pictures.setAcasatua(a);
						
							//layer.getMetatag().setImage("http://" + host + "/" + thumb);
						} else {
							log.warn("id for "+tagname+" not exist");
							return null;
						}
					} catch (Exception e) {
						log.error("Unable to select Skizzi List",e.getCause());
						return null;
					} finally{
						acasatua = null;
					}
				} else return null;*/
			} else return null;
		else throw new CustomException(new WebApplicationException(404));
		
		layer.getContent().setText(novConteiner);
		layer.getContent().setPictures(pictures);
		/*Image ;meta */
		try {
			List<Metatag> lm = null; 
			if (argument != null && !argument.isEmpty())		
				lm = novita.readMetatagByTitle(null, id.toLowerCase(), lang);
			else
				lm = novita.readMetatagByTitle(null, id.toLowerCase(), lang);
			if (lm != null && lm.size() > 0){
				this.addURLToMetaKey(lm.get(0), tagname, id, s , sidebar, lang, null, req.getQueryString());
				layer.setMetatag(lm.get(0));
			}	
		} catch (Exception e1) {
			log.error("Unable to read Metatag List",e1.getCause());
		}
		sidebar = SingletonLookup.getSideBarX(cx);
		if (s != null)
			try {
				layer.setSidebar(sidebar.getSidebar(s));
			} catch (Exception e) {
				log.warn(e.getMessage(),e);
			}
		
		setTextsAndLabels(layer, genericScarabLang ,lang);
		return layer;
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
