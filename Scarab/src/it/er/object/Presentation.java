package it.er.object;

import it.er.transform.Ofelia;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "presentation")
public class Presentation  implements Ofelia{
	private String navigation;
	private String title;
	private Header header;
	private Metatag metatag;
	private Citazione citazione;
	private Logged logged;
	
	public Presentation(){}
	
	public Presentation(String nav, String title){
		this.navigation = nav;
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
	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}
	
	@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlElement
	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}
	
	@XmlElement
	public Metatag getMetatag() {
		return metatag;
	}

	public void setMetatag(Metatag metatag) {
		this.metatag = metatag;
	}



	@Override
	public ByteArrayOutputStream writeTo(Object target, Class<?> type)
			throws IOException {
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			JAXBContext ctx = JAXBContext.newInstance(type);
			ctx.createMarshaller().marshal(target, os);
			} catch (JAXBException ex) {
				throw new RuntimeException(ex);
			}
		return os;
	}
	
	
	@XmlElement
	public Logged getLogged() {
		return logged;
	}

	public void setLogged(Logged logged) {
		this.logged = logged;
	}

	@Override
	public Object getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
