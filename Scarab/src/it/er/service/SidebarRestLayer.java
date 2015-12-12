package it.er.service;

import it.er.dao.Site;
import it.er.manage.BaseManage.BaseManeError;
import it.er.object.Attribute;
import it.er.presentation.admin.object.fecomponent.SelectItem;
import it.er.presentation.admin.object.generic.GenericScarabResponse;
import it.er.tag.SiteAccess;
import it.er.tag.Tag;
import it.er.util.CustomException;
import it.er.util.SingletonLookup;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ws.rs.*;

@Path("/sidebarws")
public class SidebarRestLayer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8096670809424449459L;

	private static final Logger log = LogManager.getLogger(SidebarRestLayer.class);
	
	private SiteAccess siteManagement;
	
	private UserPreferences userPreferences;
	
	private SidebarAccess sidebartagmanage;
	

	/**
	 * 
	 * @param req
	 * @param res
	 * @param cx
	 * @param sitenameid
	 * @param taganamerel
	 * @return
	 */
	@GET
	@Path("alltagsonbysite") 
	public Response getTagsSonbySite(@Context HttpServletRequest req,
			@Context HttpServletResponse res,
			@Context ServletContext cx,
			@QueryParam("sitenameid") String sitenameid,
			@QueryParam("tagnamerel") String taganamerel) {
		GenericScarabResponse g = null;
		userPreferences = SingletonLookup.getUserPreferences(cx);
		siteManagement = SingletonLookup.getSiteManagement(cx);
		sidebartagmanage = SingletonLookup.getSideBarTAccess(cx);
		try{
			g = SidebarRestLayer.createAuthScarabResponse(userPreferences, req);
			GenericScarabResponse.checkForResponse(g);
			List<Site> ls = siteManagement.getAllSite();
			Iterator<Site> i = ls.iterator();
			Site s = null;
			while (i.hasNext()){
				s = i.next();
				if (s.getIdsite().equals(sitenameid)){
					break;
				} 
				s = null;
			}
 			List<Tag> l = sidebartagmanage.getTagsSon(s,taganamerel);
 			Iterator<Tag> it = l.iterator();
 			List<SelectItem> si = new ArrayList<SelectItem>(); 
 			while (it.hasNext()){
 				Tag t = it.next();
 				si.add(new SelectItem(t.getTextvalue(), t.getTagname()));
 			}
 			
 			g.setBodyL(si);
 			g.setRely(taganamerel);
 			g.setSuccess(true);
		} catch (CustomException e){
			if (e.getMessage().indexOf(BaseManeError.LOGIN_REQUIRED.getDescription()) != -1)
				res.setStatus(Status.UNAUTHORIZED.getStatusCode());
			else 
				res.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
		} catch (Exception e) {
			res.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
		return Response.ok(g,"application/json").build();
	}
	
	/**
	 * 
	 * @param req
	 * @param res
	 * @param cx
	 * @param sitenameid
	 * @return
	 */
	
	@GET
	@Path("allrtagsbysite")
	public Response getAllRootTags(@Context HttpServletRequest req,
			@Context HttpServletResponse res,
			@Context ServletContext cx,
			@QueryParam("sitenameid") String sitenameid) {
		GenericScarabResponse g = null;
		userPreferences = SingletonLookup.getUserPreferences(cx);
		siteManagement = SingletonLookup.getSiteManagement(cx);
		sidebartagmanage = SingletonLookup.getSideBarTAccess(cx);
		try{
			
			g = SidebarRestLayer.createAuthScarabResponse(userPreferences, req);
			GenericScarabResponse.checkForResponse(g);
			List<Site> ls = siteManagement.getAllSite();
			Iterator<Site> i = ls.iterator();
			Site s = null;
			while (i.hasNext()){
				s = i.next();
				if (s.getIdsite().equals(sitenameid)){
					break;
				} 
				s = null;
			}
 			List<Tag> l = sidebartagmanage.getAllRootTag(s);
 			Iterator<Tag> it = l.iterator();
 			List<SelectItem> si = new ArrayList<SelectItem>(); 
 			while (it.hasNext()){
 				Tag t = it.next();
 				si.add(new SelectItem(t.getTextvalue(), t.getTagname()));
 			}
 			g.setBodyL(si);
 			g.setRely(sitenameid);
 			g.setSuccess(true);
		} catch (CustomException e){
			if (e.getMessage().indexOf(BaseManeError.LOGIN_REQUIRED.getDescription()) != -1)
				res.setStatus(Status.UNAUTHORIZED.getStatusCode());
			else 
				res.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			
		} catch (Exception e) {
			res.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
		return Response.ok(g,"application/json").build();
	}
	
	/**
	 * 
	 * @param req
	 * @param res
	 * @param cx
	 * @param tagname
	 * @param tagvalue
	 * @param numorder
	 * @param sitename
	 * @param tgtypeFirst
	 * @param tgtypeSecond
	 * @param tgtypeGrp
	 * @param tagnamerel
	 * @return
	 */
	@POST
	@Path("inserttgn")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	public Response  insertTgn(@Context HttpServletRequest req,
			@Context HttpServletResponse res,
			@Context ServletContext cx,
			@FormParam("tagname") String tagname, 
			@FormParam("tagvalue") String tagvalue,
			@FormParam("tagnumorder") String numorder,
			@FormParam("sitename") String sitename,
			@FormParam("tgtypeFirst") String tgtypeFirst,
			@FormParam("tgtypeSecond") String tgtypeSecond, 
			@FormParam("tgtypeGrp") String tgtypeGrp,
			@DefaultValue("") @FormParam("tagnamerel") String tagnamerel){
		GenericScarabResponse g = null;
		userPreferences = SingletonLookup.getUserPreferences(cx);
		siteManagement = SingletonLookup.getSiteManagement(cx);
		sidebartagmanage = SingletonLookup.getSideBarTAccess(cx);
		int[] it = null;
		if (tagnamerel != null && tagnamerel.isEmpty())
			tagnamerel = null;
		try{
			g = SidebarRestLayer.createAuthScarabResponse(userPreferences, req);
			GenericScarabResponse.checkForResponse(g);
			it = sidebartagmanage.insertTgn(req,res,tagname,tagvalue,numorder,sitename,tgtypeFirst,tgtypeSecond,tgtypeGrp,tagnamerel,userPreferences.getLogged().getCurrLang());
			if (it.length > 0){
				g.setSuccess(true);
				g.setMessage("insert #n:"+it.length);
				List<Object> r = new ArrayList<Object>();
				for (int i:it){
					r.add(i);
				}
				g.setBodyL(r);
			}
			
		} catch (CustomException e){
			if (e.getMessage().indexOf(BaseManeError.LOGIN_REQUIRED.getDescription()) != -1)
				res.setStatus(Status.UNAUTHORIZED.getStatusCode());
			else 
				res.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
		} catch (Exception e) {
			res.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
		return Response.ok(g,"application/json").build();
	}
	
	/**
	 * 
	 * @param req
	 * @param res
	 * @param cx
	 * @param sitenameid
	 * @param tagname
	 * @return
	 */
	@GET
	@Path("getattributes") 
	public Response getAttributes(@Context HttpServletRequest req,
			@Context HttpServletResponse res,
			@Context ServletContext cx,
			@QueryParam("sitenameid") String sitenameid,
			@QueryParam("tagname") String tagname) {
		GenericScarabResponse g = null;
		userPreferences = SingletonLookup.getUserPreferences(cx);
		siteManagement = SingletonLookup.getSiteManagement(cx);
		sidebartagmanage = SingletonLookup.getSideBarTAccess(cx);
		try{
			g = SidebarRestLayer.createAuthScarabResponse(userPreferences, req);
			GenericScarabResponse.checkForResponse(g);
			List<Site> ls = siteManagement.getAllSite();
			Iterator<Site> i = ls.iterator();
			Site s = null;
			while (i.hasNext()){
				s = i.next();
				if (s.getIdsite().equals(sitenameid)){
					break;
				} 
				s = null;
			}
 			List<Attribute> l = sidebartagmanage.getAttributes(s, tagname);
 			
 			g.setBodyL(l);
 			g.setRely(tagname);
 			g.setSuccess(true);
		} catch (CustomException e){
			if (e.getMessage().indexOf(BaseManeError.LOGIN_REQUIRED.getDescription()) != -1){
					return Response.status(Status.UNAUTHORIZED).build();
			} else 
				return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok(g,"application/json").build();
	}
	
	/**
	 * 
	 * @param req
	 * @param res
	 * @param cx
	 * @param sitenameid
	 * @param tagname
	 * @param key
	 * @param value
	 * @return
	 */
	@POST
	@Path("createattributes")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	public Response createAttributes(@Context HttpServletRequest req,
			@Context HttpServletResponse res,
			@Context ServletContext cx,
			@FormParam("sitenameid") String sitenameid,
			@FormParam("tagname") String tagname,
			@FormParam("key") String key,
			@FormParam("value") String value){
		int[] r = null;
		GenericScarabResponse g = null;
		userPreferences = SingletonLookup.getUserPreferences(cx);
		siteManagement = SingletonLookup.getSiteManagement(cx);
		sidebartagmanage = SingletonLookup.getSideBarTAccess(cx);
		try{
			g = SidebarRestLayer.createAuthScarabResponse(userPreferences, req);
			GenericScarabResponse.checkForResponse(g);
			List<Attribute> l = new ArrayList<Attribute>();
			Attribute a = new Attribute(key, value);
			l.add(a);
			r = sidebartagmanage.insertAttributes(l, tagname, siteManagement.getCurrentSite(sitenameid) );
			List<String> lr = new ArrayList<String>(r.length);
			for(int attr:r){
				lr.add(String.format("{%s:%d}",l.get(0).getName(),attr));
			}
			g.setBodyL(lr);
 			g.setSuccess(true);
		} catch (CustomException e){
			log.error("Error in Create attributes",e);
			if (e.getMessage().indexOf(BaseManeError.LOGIN_REQUIRED.getDescription()) != -1){
					return Response.status(Status.UNAUTHORIZED).build();
			} else 
				return Response.status(Status.BAD_REQUEST).build();
		} catch (Exception e) {
			log.error("Error in Create Response, maybe unable to find Site", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		
		}
		return Response.ok(g,"application/json").build();
	}
	
	public boolean reloadSB(ServletContext cx) throws CustomException{
		SidebarControl sidebartagctl = SingletonLookup.getSideBarTControl(cx);
		return sidebartagctl.reloadAllSidebar();		
	}
	
	
	@POST
	@Path("reloadsb")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	public Response reloadSB(@Context HttpServletRequest req,
			@Context HttpServletResponse res,
			@Context ServletContext cx){
		GenericScarabResponse g = null;
		userPreferences = SingletonLookup.getUserPreferences(cx);
		boolean r = false;
		try{
			g = SidebarRestLayer.createAuthScarabResponse(userPreferences, req);
			GenericScarabResponse.checkForResponse(g);
			log.info("try reloaded sidebar #"+userPreferences.getLogged().getAccount().getUsername()+"...");
			r = this.reloadSB(cx);
			g.setSuccess(r);
		}catch (Exception e) {
			log.error("Not able to reload sidebar",e);
			if (e.getMessage().indexOf(BaseManeError.LOGIN_REQUIRED.getDescription()) != -1){
				return Response.status(Status.UNAUTHORIZED).build();
			} else
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}	
		return Response.ok(g,"application/json").build();
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
}
