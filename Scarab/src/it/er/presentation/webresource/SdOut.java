package it.er.presentation.webresource;
import it.er.manage.contentManage.SdManage;
import it.er.object.ContentBox;
import it.er.object.Layer;
import it.er.transform.Ofelia;
import it.er.transform.TransformGenerator;
import it.er.util.CustomException;
import it.er.util.SingletonLookup;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.StreamingOutput;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Path("/sd")
public class SdOut extends BaseLayerContent{

	private static Logger log = LogManager.getLogger(SdOut.class);
	
	private SdManage sdManage;
	
	
	
	public SdManage getSdManage() {
		return sdManage;
	}
	public void setSdManage(SdManage sdManage) {
		this.sdManage = sdManage;
	}
	
	
	@GET
	@Path("{tagname}")
	@Produces("text/html")
	public StreamingOutput getTagname(@Context final ServletContext cx, 
			@Context final HttpServletRequest req,
			@Context final HttpServletResponse res,
			@DefaultValue("en") @QueryParam("lang") final String lang,
			@PathParam("tagname") final String tagname,
			@DefaultValue("") @QueryParam("argument") final String argument,
			@DefaultValue("0") @QueryParam("start") final int start,
			@QueryParam("limit") final int limit,
			@DefaultValue("1") @QueryParam("page") final int page,
			@DefaultValue("0") @QueryParam("succ") final boolean succ,
			@DefaultValue("0") @QueryParam("prec") final boolean prec) throws Exception{
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(cx);
		sdManage = SingletonLookup.getSdManage(cx);
		return new StreamingOutput() {
		
		@Override
		public void write(OutputStream out) throws IOException,
					WebApplicationException {
			
				Ofelia off = null;
				try{		
					off = sdManage.getTagname(req, cx, res, lang, tagname, argument, start, limit,page, succ, prec);
					if (off!=null){
						if (off.getContent()!=null){
							((ContentBox)off.getContent()).setOutContext(tagname);
						}
						/*tg.doXMLProcessing(req,res, getContentNameSp(tagname), 
								getXSLTPath("layer.xslt",req.getServerName(),cx), null, out, off,
								Layer.class);*/
						tg.doXMLProcessing(req, getXSLTPath("layer.xslt",req.getServerName(),cx), null, out, off,
								Layer.class);
					} else {
						throw new WebApplicationException(404);
					}
				} catch (CustomException e) {
					log.error(e);
					if (e.getCause() instanceof WebApplicationException)
						throw (WebApplicationException) e.getCause();
				} catch (SQLException e){
					log.error(e);
					throw new WebApplicationException(500);
				}
			}
		};
	}
	
	@GET
	@Path("{tagname}/{id}")
	@Produces("text/html")
	public StreamingOutput getIdForTagname(@Context final ServletContext cx, 
			@Context final HttpServletRequest req,
			@Context final HttpServletResponse res,
			@DefaultValue("en") @QueryParam("lang") final String lang,
			@PathParam("tagname") final String tagname,
			@DefaultValue("") @QueryParam("argument") final String argument,
			@PathParam("id") final String id) throws Exception{
		final TransformGenerator tg = SingletonLookup.getTransformGenerator(cx);
		
		sdManage = SingletonLookup.getSdManage(cx);
		return new StreamingOutput() {
		
		@Override
		public void write(OutputStream out) throws IOException,
					WebApplicationException {
				Ofelia off = null; 
				try{		
					off = sdManage.getIdForTagname(req, cx, res, lang, tagname,argument, id);
					if (off!=null){
						if (off.getContent()!=null){
							((ContentBox)off.getContent()).setOutContext(tagname+"#"+id);
						}
						/*tg.doXMLProcessing(req,res, getContentNameSp(tagname), 
								getXSLTPath("layer.xslt",req.getServerName(),cx), null, out, off,
								Layer.class);*/
						tg.doXMLProcessing(req,getXSLTPath("layer.xslt",req.getServerName(),cx), null, out, off,
								Layer.class);
					} else {
						throw new WebApplicationException(404);
					}
				} catch (CustomException e) {
					log.error(e);
					if (e.getCause() instanceof WebApplicationException)
						throw (WebApplicationException) e.getCause();
				} catch (SQLException e){
					log.error(e);
					throw new WebApplicationException(500);
				}	
				
			}
		};
		
	}
	
	
}
