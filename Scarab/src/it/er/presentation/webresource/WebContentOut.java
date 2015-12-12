package it.er.presentation.webresource;
import it.er.basic.Basic;
import it.er.manage.contentManage.WebContentManage;
import it.er.manage.graph.GraphManage;
import it.er.object.ContentBox;
import it.er.object.GraphLayer;
import it.er.object.Layer;
import it.er.object.Presentation;
import it.er.object.content.GraphContent;
import it.er.transform.Ofelia;
import it.er.transform.TransformGenerator;
import it.er.util.SingletonLookup;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.StreamingOutput;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@Path("/content")
public class WebContentOut extends Basic{
	
	private static Logger log = LogManager.getLogger(WebContentOut.class);
	
	private WebContentManage webContentManage;
	
	private GraphManage graphmanage;
	
	public WebContentManage getWebContentManage() {
		return webContentManage;
	}

	public void setWebContentManage(WebContentManage webContentManage) {
		this.webContentManage = webContentManage;
	}

	@GET
	@Path("{lang}/welcome")
	@Produces("text/html")
	public StreamingOutput getWelcome(@Context final ServletContext cx, 
			@Context final HttpServletRequest req,
			@Context final HttpServletResponse res,
			@DefaultValue("en") @PathParam("lang") final String lang) throws Exception{
		webContentManage = SingletonLookup.getWebContentManage(cx);
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(cx);
			return new StreamingOutput() {
			
		@Override
		public void write(OutputStream out) throws IOException,
					WebApplicationException {
				Ofelia off = webContentManage.getPresentation(req, cx, res,lang);
				if (off != null){
					
				/*tg.doXMLProcessing(req,res, getContentNameSp("welcome"), 
						getXSLTPath("presentation.xslt",req.getServerName(),cx), null, out, off,
						Presentation.class);*/
				tg.doXMLProcessing(req,getXSLTPath("presentation.xslt",req.getServerName(),cx), null, out, off,
						Presentation.class);
				} else {
					throw new WebApplicationException(404);
				}
			}
		};
	}
	
	@GET
	@Path("{lang}/home")
	@Produces("text/html")
	public StreamingOutput getHome(@Context final ServletContext cx, 
			@Context final HttpServletRequest req,
			@Context final HttpServletResponse res,
			@DefaultValue("en") @PathParam("lang") final String lang) throws Exception{
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(cx);
		webContentManage = SingletonLookup.getWebContentManage(cx);
		
		return new StreamingOutput() {
		
		@Override
		public void write(OutputStream out) throws IOException,
					WebApplicationException {
				Ofelia off = webContentManage.getHom(req, cx, res, lang);
				if (off!=null){
					if (off.getContent()!=null){
						((ContentBox)off.getContent()).setOutContext("home");
					}
				/*tg.doXMLProcessing(req,res, getContentNameSp("home"), 
						getXSLTPath("layer.xslt",req.getServerName(),cx), null, out, off,
						Layer.class);*/
					tg.doXMLProcessing(req, getXSLTPath("layer.xslt",req.getServerName(),cx), null, out, off,
						Layer.class);
				} else {
					throw new WebApplicationException(404);
				}
			}
		};
	}
	

	
	
	@GET
	@Path("{lang}/custom/{contentstatic}")
	@Produces("text/html")
	public StreamingOutput getStatic(@Context final ServletContext cx, 
			@Context final HttpServletRequest req,
			@Context final HttpServletResponse res,
			@DefaultValue("en") @PathParam("lang") final String lang,
			@PathParam("contentstatic") final String contentstatic) throws Exception{
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(cx);
		webContentManage = SingletonLookup.getWebContentManage(cx);
		return new StreamingOutput() {
		
		@Override
		public void write(OutputStream out) throws IOException,
					WebApplicationException {
				Ofelia off = webContentManage.getStaticContent(req, cx, res, lang,contentstatic);
				if (off!=null){
					if (off.getContent()!=null){
						((ContentBox)off.getContent()).setOutContext("static@"+contentstatic);
					}
				/*tg.doXMLProcessing(req,res, getContentNameSp(contentstatic), 
						getXSLTPath(contentstatic+".xslt",req.getServerName(),cx), null, out, off,
						Layer.class);*/
					tg.doXMLProcessing(req, getXSLTPath(contentstatic+".xslt",req.getServerName(),cx), null, out, off,
						Layer.class);
				} else {
					throw new WebApplicationException(404);
				}
			}
		};
	}
	
	@GET
	@Path("{lang}/customgraph/{contentstatic}")
	@Produces("text/html")
	public StreamingOutput getGraphStatic(@Context final ServletContext cx, 
			@Context final HttpServletRequest req,
			@Context final HttpServletResponse res,
			@DefaultValue("en") @PathParam("lang") final String lang,
			@PathParam("contentstatic") final String contentstatic) throws Exception{
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(cx);
		graphmanage = SingletonLookup.getGraphManage(cx);
		return new StreamingOutput() {
		
		@SuppressWarnings("unchecked")
		@Override
		public void write(OutputStream out) throws IOException,
					WebApplicationException {
				Ofelia off = graphmanage.getGraphStaticContent(req, cx, res, lang,contentstatic);
				if (off!=null){
					if (off.getContent()!=null){
						for (GraphContent gc:((List<GraphContent>)off.getContent())){
							gc.setOutContext("static@"+contentstatic);
						};
					}
				/*tg.doXMLProcessing(req,res, getContentNameSp(contentstatic), 
						getXSLTPath(contentstatic+".xslt",req.getServerName(),cx), null, out, off,
						Layer.class);*/
					tg.doXMLProcessing(req, getXSLTPath(contentstatic+".xslt",req.getServerName(),cx), null, out, off,
						GraphLayer.class);
				} else {
					throw new WebApplicationException(404);
				}
			}
		};
	}
}
