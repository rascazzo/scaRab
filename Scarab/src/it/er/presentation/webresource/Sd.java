package it.er.presentation.webresource;
import java.sql.SQLException;

import it.er.layerintercept.AbstractIdentified;
import it.er.manage.contentManage.SdManage;
import it.er.object.Layer;
import it.er.util.CustomException;
import it.er.util.SingletonLookup;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Path("/sdxmlsource")
public class Sd extends AbstractIdentified{
	
	private static Logger log = LogManager.getLogger(Sd.class);
	
	
	
	
//	private String addBlankToJoin(String join){
//		StringBuffer buf = new StringBuffer();
//		buf.append(" ");
//		buf.append(join);
//		buf.append(" ");
//		return buf.toString();
//	}

	
	private SdManage sdManage;
	
	
	
	public SdManage getSdManage() {
		return sdManage;
	}
	public void setSdManage(SdManage sdManage) {
		this.sdManage = sdManage;
	}

	@GET
	@Path("{tagname}")
	@Produces("application/xml")
	public Layer getTagname(@Context HttpServletRequest req,
			@Context ServletContext cx, @Context HttpServletResponse res,
			@PathParam("tagname") String tagname,
			@DefaultValue("en") @QueryParam("lang") String lang,
			@DefaultValue("") @QueryParam("argument") String argument,
			@DefaultValue("0") @QueryParam("start") int start,
			@QueryParam("limit")int limit,
			@DefaultValue("1") @QueryParam("page") int page,
			@DefaultValue("0") @QueryParam("succ") final boolean succ,
			@DefaultValue("0") @QueryParam("prec") final boolean prec) throws WebApplicationException{
		sdManage = SingletonLookup.getSdManage(cx);
		try{
			return sdManage.getTagname(req, cx, res, lang,tagname, argument, start,limit,page, succ, prec);
		} catch (CustomException e) {
			log.error(e);
			if (e.getCause() instanceof WebApplicationException)
				throw (WebApplicationException) e.getCause();
		} catch (SQLException e){
			log.error(e);
			throw new WebApplicationException(500);
		}
		return null;
	}
	
	@GET
	@Path("{tagname}/{id}")
	@Produces("application/xml")
	public Layer getIdForTagname(@Context HttpServletRequest req,
			@Context ServletContext cx, @Context HttpServletResponse res,
			@DefaultValue("en") @QueryParam("lang") String lang,
			@DefaultValue("") @QueryParam("argument") String argument,
			@PathParam("tagname") String tagname,
			@PathParam("id") String id) throws WebApplicationException{
		sdManage = SingletonLookup.getSdManage(cx);
		try{
			return sdManage.getIdForTagname(req, cx, res, lang,tagname,argument, id);
		} catch (CustomException e) {
			log.error(e);
			if (e.getCause() instanceof WebApplicationException)
				throw (WebApplicationException) e.getCause();
		} catch (SQLException e){
			log.error(e);
			throw new WebApplicationException(500);
		}
	return null;
		
	}

	
}
