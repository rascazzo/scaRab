package it.er.object;



import it.er.layerintercept.AbstractIdentified;
import it.er.layerintercept.OverX;
import it.er.presentation.webresource.BaseLayerContent;
import it.er.transform.Ofelia;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="page")
public class BaseWebObj extends BaseLayerContent implements Ofelia{

	
	private Citazione citazione;
	
	private String nav;
	
	private Header header;
	
	private String title;
	
	private ContentBox content;
	
	private String URLPage;
	
	private Metatag metatag;
	
	public BaseWebObj(){}
	
	public BaseWebObj(String nav, String title){
		this.nav = nav;
		this.title = title;
	}

	
	@XmlElement
	public Citazione getCitazione() {
		return citazione;
	}

	public void setCitazione(Citazione citazione) {
		this.citazione = citazione;
	}
	@XmlElement
	public String getNav() {
		return nav;
	}

	public void setNav(String nav) {
		this.nav = nav;
	}
	@XmlElement
	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}
	
	@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@XmlElement
	public ContentBox getContent() {
		return content;
	}

	public void setContent(ContentBox content) {
		this.content = content;
	}
	@XmlElement
	public String getURLPage() {
		return URLPage;
	}

	public void setURLPage(String uRLPage) {
		URLPage = uRLPage;
	}

	@XmlElement
	public Metatag getMetatag() {
		return metatag;
	}

	public void setMetatag(Metatag metatag) {
		this.metatag = metatag;
	}

	
	public ByteArrayOutputStream writeTo(Object target, Class<?> type) throws IOException{
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			JAXBContext ctx = JAXBContext.newInstance(type);
			ctx.createMarshaller().marshal(target, os);
			} catch (JAXBException ex) {
				throw new RuntimeException(ex);
			}
		return os;
	}

	
	
	
	
	
	
	
}
