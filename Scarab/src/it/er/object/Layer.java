package it.er.object;

import it.er.generic.GenericTag;
import it.er.layerintercept.IdentifiedIntercept;
import it.er.layerintercept.OverX;
import it.er.manage.BaseManage.BaseManeError;
import it.er.presentation.webresource.BaseLayerContent;
import it.er.service.UserPreferences;
import it.er.transform.Ofelia;
import it.er.util.CustomException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "layer")
public class Layer extends BaseLayerContent implements Ofelia,IdentifiedIntercept{
	
	private String navigation;
	private String title;
	private Header header = new Header();
	private Metatag metatag = new Metatag();
	private ContentBox content = new ContentBox();
	private SideBar sidebar;
	private Citazione citazione;
	private List<GenericTag> label;
	private List<GenericTag> text;
	
	public Layer(){
		super();
		this.logged = new Logged();
		this.logged.setNew(true);
	}
	
	public Layer(UserPreferences u,Class <? extends BaseLayerContent> restservice,HttpServletRequest req, boolean requiredAuth) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, CustomException{
		super();
		this.setLogged((Logged) (restservice.getMethod("invokeServerIdentities", new Class[]{UserPreferences.class,HttpServletRequest.class,boolean.class}).invoke(this, new Object[]{u,req,requiredAuth})));
	}
	
	
	/*
	public Layer(HttpServletRequest request, HttpServletResponse response, ServletContext context,String nav, String title, String namespace)
	{
		super(request,response,context);
		this.navigation = nav;
		this.title = title;
		this.namespace = namespace;
	}*/
	
	public Layer(String nav, String title)
	{
		this.navigation = nav;
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
	public Citazione getCitazione() {
		return citazione;
	}

	public void setCitazione(Citazione citazione) {
		this.citazione = citazione;
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
	public SideBar getSidebar() {
		return sidebar;
	}

	public void setSidebar(SideBar sidebar) {
		this.sidebar = sidebar;
	}

	@Override
	public Logged invokeServerIdentities(UserPreferences u,
			HttpServletRequest req, boolean requiredAuth)
			throws CustomException {
		Logged l = null;
		try{
			l = u.getLogged();		
		} catch (Exception e){
			if (l == null){
				l = new Logged();
				l.setNew(true);
			}
			
		}
		if (u.getLogged().isNew() && requiredAuth){
			throw new CustomException(BaseManeError.LOGIN_REQUIRED.getDescription());
		}
		return l;
	}

	@Override
	public void invokeClientIdentities(HttpServletRequest req, OverX over)
			throws CustomException {
		// TODO Auto-generated method stub
		
	}

	@XmlElementWrapper(name = "mainlabels")
	public List<GenericTag> getLabel() {
		return label;
	}
	public void setLabel(List<GenericTag> label) {
		this.label = label;
	}
	@XmlElementWrapper(name = "maintexts")
	public List<GenericTag> getText() {
		return text;
	}

	public void setText(List<GenericTag> text) {
		this.text = text;
	}

	
	
	
}
