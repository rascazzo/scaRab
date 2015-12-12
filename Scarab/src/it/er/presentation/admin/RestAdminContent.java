package it.er.presentation.admin;


import it.er.layerintercept.AbstractIdentified;
import it.er.manage.admin.AdminContentManage;
import it.er.util.CustomException;
import it.er.util.SingletonLookup;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@Path("/text")
public class RestAdminContent extends AbstractIdentified{

	private static Logger log = LogManager.getLogger(RestAdminContent.class);

	private AdminContentManage adminContentManage;
	
	
	public AdminContentManage getAdminContentManage() {
		return adminContentManage;
	}

	public void setAdminContentManage(AdminContentManage adminContentManage) {
		this.adminContentManage = adminContentManage;
	}
	
	@POST
	@Path("inserttext")
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	public Response insertText(@Context HttpServletRequest req,
			@Context HttpServletResponse res,
			@Context ServletContext cx,
			@FormParam("tagname") String tagname,
			@FormParam("sitename") String sitename,
			@FormParam("title") String title,
			@FormParam("order") String order,
			@DefaultValue("off") @FormParam("archive") String archive,
			@FormParam("lang") String lang,
			@FormParam("body") String body,
			@DefaultValue("") @FormParam("authorinfo") String authorinfo,
			@DefaultValue("") @FormParam("titleinfo") String titleinfo,
			@DefaultValue("") @FormParam("imageinfo") String imageinfo,
			@DefaultValue("") @FormParam("keysinfo") String keysinfo,
			@DefaultValue("") @FormParam("descriptioninfo") String descriptioninfo) throws CustomException{
		adminContentManage = SingletonLookup.getAdminContentManage(cx);
		int r = 0;
		String js = "{\"success\":";
		try{
			r = adminContentManage.insertText(req, res, cx,
					tagname, sitename, title, order,archive,lang, body,
					authorinfo,titleinfo,imageinfo,keysinfo,descriptioninfo,
					true);
			js = js + "true,\"insert\":"+r+"}";
		} catch (CustomException e){
			log.error(e.getMessage());
			js = js + "false,\"insert\":"+r+"}";
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		
		return Response.ok(js, "application/json").build();
	}
	
	@POST
	@Path("inserttextseries")
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	public Response insertTextSeries(@Context HttpServletRequest req,
			@Context HttpServletResponse res,
			@Context ServletContext cx,
			@FormParam("tagname") String tagname,
			@FormParam("sitename") String sitename,
			@FormParam("title") String title,
			@FormParam("order") String order,
			@DefaultValue("off") @FormParam("archive") String archive,
			@FormParam("lang") String lang,
			@FormParam("body") String body,
			@FormParam("collectionname") String colName,
			@FormParam("serietitle") String serietitle,
			@FormParam("seriesubtitle") String seriesubtitle,
			@DefaultValue("") @FormParam("authorinfo") String authorinfo,
			@DefaultValue("") @FormParam("titleinfo") String titleinfo,
			@DefaultValue("") @FormParam("imageinfo") String imageinfo,
			@DefaultValue("") @FormParam("keysinfo") String keysinfo,
			@DefaultValue("") @FormParam("descriptioninfo") String descriptioninfo) throws CustomException{
		adminContentManage = SingletonLookup.getAdminContentManage(cx);
		int r = 0;
		String js = "{\"success\":";
		try{
			r = adminContentManage.insertTextSeries(req, res, cx,
					tagname, sitename, title,order, archive,lang, body, colName,serietitle,seriesubtitle, 
					authorinfo,titleinfo,imageinfo,keysinfo,descriptioninfo,
					true);
			js = js + "true,\"insert\":"+r+"}";
		} catch (CustomException e){
			log.error(e.getMessage());
			js = js + "false,\"insert\":"+r+"}";
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		
		return Response.ok(js, "application/json").build();
	}
	
	
}
