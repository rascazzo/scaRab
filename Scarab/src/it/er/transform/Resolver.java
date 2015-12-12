package it.er.transform;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

public class Resolver implements URIResolver{

	/*
	private String hostname;
	
	public Resolver(String host){
		this.hostname = host;
	}
	
	@Override
	public Source resolve(String href, String base) throws TransformerException {
		Source s = null;
		try {
			if (href.startsWith("/")){
				s = new StreamSource("http://"+ hostname + href);
			} else{
				s = new StreamSource(href);
			}
				
		} catch (Exception ex){
			throw new TransformerException(ex);
		}
		return s;
	}*/
	
	private String root;
	
	public Resolver(String root){
		this.root = root;
	}
	
	@Override
	public Source resolve(String filename, String base) throws TransformerException {
		Source s = null;
		try {
			if (filename.startsWith("/")){
				s = new StreamSource(root + filename);
			} else{
				s = new StreamSource("./"+filename);
			}
				
		} catch (Exception ex){
			throw new TransformerException(ex);
		}
		return s;
	}
}
