package it.er.object;

import it.er.layerintercept.AbstractIdentified;
import it.er.layerintercept.OverX;
import it.er.transform.Ofelia;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "single")
public class Single implements Ofelia{

	private String navigation;
	private String title;
	private Header header = new Header();
	private Metatag metatag = new Metatag();
	private ContentBoxSingle content = new ContentBoxSingle();
	private String namespace;
	private String namespacetagname;
	private Logged logged;
	private SideBar sidebar;
	
	public Single(){}
	
	public Single(String nav, String title, String namespace){
		this.navigation = nav;
		this.title = title;
		this.namespace = namespace;
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
	@XmlElement
	public ContentBoxSingle getContent() {
		return content;
	}
	
	public void setContent(ContentBoxSingle content) {
		this.content = content;
	}
	@XmlElement
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	
	@XmlElement
	public String getNamespacetagname() {
		return namespacetagname;
	}

	public void setNamespacetagname(String namespacetagname) {
		this.namespacetagname = namespacetagname;
	}
	
	@XmlElement
	public Logged getLogged() {
		return logged;
	}

	public void setLogged(Logged logged) {
		this.logged = logged;
	}
	
	@XmlElement
	public SideBar getSidebar() {
		return sidebar;
	}

	public void setSidebar(SideBar sidebar) {
		this.sidebar = sidebar;
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

	
}
