package it.er.object;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import it.er.layerintercept.IdentifiedIntercept;
import it.er.layerintercept.OverX;
import it.er.manage.BaseManage.BaseManeError;
import it.er.object.content.GraphContent;
import it.er.presentation.webresource.BaseLayerContent;
import it.er.service.UserPreferences;
import it.er.transform.Ofelia;
import it.er.util.CustomException;

@XmlRootElement(name = "graphlayer")
public class GraphLayer extends BaseLayerContent implements Ofelia,IdentifiedIntercept{

	private String navigation;
	private String title;
	private Header header = new Header();
	private Metatag metatag = new Metatag();
	private List<GraphContent> content;
	private SideBar sidebar;
	
	
	public GraphLayer(){
		super();
		this.logged = new Logged();
		this.logged.setNew(true);
		this.content = new LinkedList<GraphContent>();
	}
	
	public GraphLayer(UserPreferences u,Class <? extends BaseLayerContent> restservice,HttpServletRequest req, boolean requiredAuth) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, CustomException{
		super();
		this.setLogged((Logged) (restservice.getMethod("invokeServerIdentities", new Class[]{UserPreferences.class,HttpServletRequest.class,boolean.class}).invoke(this, new Object[]{u,req,requiredAuth})));
		this.content = new LinkedList<GraphContent>();
	}
	
	
	/*
	public Layer(HttpServletRequest request, HttpServletResponse response, ServletContext context,String nav, String title, String namespace)
	{
		super(request,response,context);
		this.navigation = nav;
		this.title = title;
		this.namespace = namespace;
	}*/
	
	public GraphLayer(String nav, String title)
	{
		this.navigation = nav;
		this.title = title;
		
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

	
	@XmlElement(name = "graph")
	@XmlElementWrapper(name = "graphs")
	public List<GraphContent> getContent() {
		return content;
	}

	public void setContent(List<GraphContent> content) {
		this.content = content;
	}
	
	
	
}
