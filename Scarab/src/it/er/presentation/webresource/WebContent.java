package it.er.presentation.webresource;
import it.er.manage.contentManage.WebContentManage;
import it.er.manage.graph.GraphManage;
import it.er.object.GraphLayer;
import it.er.object.Layer;
import it.er.object.Presentation;
import it.er.object.SiteMapXML;
import it.er.util.SingletonLookup;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Path("/contentxmlsource")
public class WebContent extends BaseLayerContent {

	
	private static Logger log = LogManager.getLogger(WebContent.class);
	
	private GraphManage graphmanage;
	
	private WebContentManage webContentManage;
	
	public WebContentManage getWebContentManage() {
		return webContentManage;
	}

	public void setWebContentManage(WebContentManage webContentManage) {
		this.webContentManage = webContentManage;
	}


	@GET
	@Path("home")
	@Produces("application/xml")
	public Layer getHom(@Context HttpServletRequest req,
			@Context ServletContext cx, @Context HttpServletResponse res,
			@DefaultValue("en") @QueryParam("lang") String lang){
		webContentManage = SingletonLookup.getWebContentManage(cx);
		return webContentManage.getHom(req, cx, res, lang);
		
	}
	
	@GET
	@Path("welcome")
	@Produces("application/xml")
	public Presentation getPresentation(@Context HttpServletRequest req,
			@Context ServletContext cx, @Context HttpServletResponse res,
			@DefaultValue("en") @QueryParam("lang") String lang){
		webContentManage = SingletonLookup.getWebContentManage(cx);
		return webContentManage.getPresentation(req, cx, res, lang);
				
	}
	
	@GET
	@Path("sitemap")
	@Produces("application/xml")
	public SiteMapXML getSitemap(@Context HttpServletRequest req,@Context ServletContext cx){
		webContentManage = SingletonLookup.getWebContentManage(cx);
		return webContentManage.getSitemap(req, cx);
	}
	
	
	
	@GET
	@Path("custom/{contentstatic}")
	@Produces("application/xml")
	public Layer getStaticContent(@Context HttpServletRequest req,
			@Context ServletContext cx, @Context HttpServletResponse res,
			@PathParam("contentstatic") String contentstatic,
			@DefaultValue("en") @QueryParam("lang") String lang){
		
		webContentManage = SingletonLookup.getWebContentManage(cx);
		return webContentManage.getStaticContent(req, cx, res,  lang, contentstatic);
	}

	@GET
	@Path("customgraph/{contentstatic}")
	@Produces("application/xml")
	public GraphLayer getStaticGraphContent(@Context HttpServletRequest req,
			@Context ServletContext cx, @Context HttpServletResponse res,
			@PathParam("contentstatic") String contentstatic,
			@DefaultValue("en") @QueryParam("lang") String lang){
		
		graphmanage = SingletonLookup.getGraphManage(cx);
		return graphmanage.getGraphStaticContent(req, cx, res, lang, contentstatic);
	}

	
	
}
