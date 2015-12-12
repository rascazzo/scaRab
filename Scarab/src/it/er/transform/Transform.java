package it.er.transform;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.JdbcTemplate;


public interface Transform {
	public void setJdbcToParam(JdbcTemplate j); 
	public void doXMLProcessingJavax(HttpServletRequest req, 
			HttpServletResponse res, String XML, String XSL, Map<String,String> xmlParam,
			OutputStream out, Ofelia xml, Class<?> type) throws IOException;
	public void doXMLProcessing(HttpServletRequest req,String XSL, Map<String,String> xmlParam,
			 OutputStream out,Ofelia xml, Class<?> type) throws IOException;
	public void doXRabProcessing(String XRab, Map<String,String> xmlParam,
			OutputStream out,Ofelia xml, Class<?> type) throws IOException;
}
