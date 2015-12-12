package it.er.transform;

import it.er.basic.Basic;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;




public class TransformGenerator extends Basic{

	private Transform transform;
	
	private static Logger log = LogManager.getLogger(TransformGenerator.class);
	
	public void close(){
		log.info("Destroy transfromStyGenerator bean");
		transform = null;
	}
	
	public void start(){
		log.info("Init transfromStyGenerator bean");
	}
	
	public void setTransform(Transform transform){
		this.transform = transform;
	}
	
	public Transform getTransform(){
		return this.transform;
	}
	
	public void doXMLProcessing(HttpServletRequest req,
			HttpServletResponse res, String XML, String XSL, Map<String,String> xmlParam,
			OutputStream out, Ofelia xml, Class<?> type) throws IOException{
		transform.doXMLProcessingJavax(req,res,XML,XSL,xmlParam,out,xml,type);
	}
	
	public void doXMLProcessing(HttpServletRequest req,String XSL, Map<String,String> xmlParam,
			OutputStream out,Ofelia xml, Class<?> type) throws IOException{
		transform.doXMLProcessing(req,XSL,xmlParam,out,xml,type);
	}
	
	public void doXRabProcessing(String XRab, Map<String,String> xmlParam,
			OutputStream out,Ofelia xml, Class<?> type) throws IOException{
		transform.doXRabProcessing(XRab,xmlParam,out,xml,type);
	}
	
}
