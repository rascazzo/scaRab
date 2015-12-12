package it.er.transform;


import it.er.basic.Basic;
import it.er.dao.IParamDAO;
import it.er.dao.ISiteDAO;
import it.er.dao.Param;
import it.er.dao.Site;
import it.er.xrab.ResolverRab;
import it.er.xrab.XRabProcExec;
import it.er.xrab.XRabResolver;
import it.er.xrab.XRabTransformer;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.xalan.processor.TransformerFactoryImpl;
import org.apache.xalan.xsltc.compiler.Template;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.xml.sax.SAXParseException;



public class TransformBean extends Basic implements Transform,InitializingBean{

	@Autowired
	private IParamDAO param;
	
	@Autowired
	private ISiteDAO site;
	
	private String webContentPath;
	
	private Map<String,String> siteWebContentPathMap = new HashMap<String, String>();
	
	
	public void setParam(IParamDAO param) {
		this.param = param;
	}
	
	public void setSite(ISiteDAO site) {
		this.site = site;
	}

	private static Logger log = LogManager.getLogger(TransformBean.class);
	
	public String getWebContentPath() {
		return webContentPath;
	}

	public void setWebContentPath(String webContentPath) {
		this.webContentPath = webContentPath;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		List<Param> p = param.getParamList(Basic.getWCP());
		setWebContentPath(p.get(0).getValue());
		List<Site> siteWebContentPathList = site.readAllSites();
		Iterator<Site> i = siteWebContentPathList.iterator();
		siteWebContentPathMap.clear();
		while (i.hasNext()){
			Site n = i.next();
			siteWebContentPathMap.put(n.getDomain(), n.getWebcontentpath());
		}
		site = null;
		param = null;
	}
	
	public void close(){
		log.info("Destroy transfromSty bean");
	}
	
	public void start(){
		log.info("Init transfromSty bean");
	}
	@Override
	public void doXMLProcessingJavax(HttpServletRequest req, HttpServletResponse res, String XML, String XSL, Map<String,String> xmlParam,
			OutputStream out, Ofelia xml, Class<?> type) throws IOException{
		res.setContentType("text/html");
		
		String xmlQuery = this.paramFromMap(xmlParam);
		if (!(xmlQuery==null))
			if (!xmlQuery.toString().equals("?"))
				XML += xmlQuery;
		InputStream xslt = null;
		Resolver r = null;
		InputStream xmlSource = null;
		try {
			
			if (siteWebContentPathMap.containsKey(req.getServerName())){
				String siteWebContentPath = null;
				siteWebContentPath = siteWebContentPathMap.get(req.getServerName());
				if (siteWebContentPath != null && siteWebContentPath.length() > 0){
					xslt = new FileInputStream(siteWebContentPath+XSL);
					r = new Resolver(siteWebContentPath);
				}
			} else {
				xslt = new FileInputStream(webContentPath+XSL);
				r = new Resolver(webContentPath);
			}
				xmlSource = new ByteArrayInputStream(xml.writeTo(xml, type).toByteArray());
				
				TransformerFactory factory = TransformerFactory.newInstance();
				factory.setURIResolver(r);
				Transformer transformer = factory.newTransformer(new StreamSource(xslt));
				transformer.transform(new StreamSource(xmlSource), new StreamResult(out));
			
		} catch (Exception e){
			res.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			out.write(res.getBufferSize());
			log.error("Error in transform", e);
		} finally {
			if (xslt != null)
				xslt.close();
			if (xmlSource != null)
				xmlSource.close();
		}
	}

	@Override
	public void setJdbcToParam(JdbcTemplate j) {
		this.param.setJdbcTemplate(j);		
	}

	@Override
	public void doXMLProcessing(HttpServletRequest req, String XSL, Map<String, String> xmlParam,
			 OutputStream out,Ofelia xml, Class<?> type) throws IOException {
		/*
		String xmlQuery = this.paramFromMap(xmlParam);
		if (!(xmlQuery==null))
			if (!xmlQuery.toString().equals("?"))
				XML += xmlQuery;
		*/
		InputStream xslt = null;
		Resolver r = null;
		InputStream xmlSource = null;
		try {
			String doctype = "<!DOCTYPE html>\n";
			out.write(doctype.getBytes());
			if (siteWebContentPathMap.containsKey(req.getServerName())){
				String siteWebContentPath = null;
				siteWebContentPath = siteWebContentPathMap.get(req.getServerName());
				if (siteWebContentPath != null && siteWebContentPath.length() > 0){
					xslt = new FileInputStream(siteWebContentPath+XSL);
					r = new Resolver(siteWebContentPath);
				}
			} else {
				xslt = new FileInputStream(webContentPath+XSL);
				r = new Resolver(webContentPath);
			}
			
			xmlSource = new ByteArrayInputStream(xml.writeTo(xml, type).toByteArray());
			
			TransformerFactory factory =  TransformerFactoryImpl.newInstance();       
			factory.setURIResolver(r);
			Templates template = factory.newTemplates(new StreamSource(xslt));
			Transformer transformer = template.newTransformer();
			transformer.transform(new StreamSource(xmlSource), new StreamResult(out));
			out.flush();
		} catch (Exception e){
			log.error("Error in transform", e.getCause());
		} finally {
			xslt.close();
			xmlSource.close();
		}
	}

	@Override
	public void doXRabProcessing(String XRab, Map<String, String> xmlParam,
			OutputStream out, Ofelia xml, Class<?> type) throws IOException {
		InputStream xrab = new FileInputStream(webContentPath+XRab);
		InputStream xmlSource = new ByteArrayInputStream(xml.writeTo(xml, type).toByteArray());
		try {
			XRabResolver r = new ResolverRab(webContentPath);
			XRabTransformer xrabElab = it.er.xrab.XRab.newIstance(); 
			xrabElab.setURIResolver(r);
			XRabProcExec proc = it.er.xrab.XRab.newTrasform(xrabElab, new StreamSource(xrab));
			proc.transform(new StreamSource(xmlSource), new StreamResult(out));
			} catch (Exception e){
			out.write(e.getMessage().getBytes());
			log.error("Error in XRAB transform", e.getCause());
		} finally {
			xrab.close();
			xmlSource.close();
		}
	}
	
	
}
