package it.er.presentation.admin.object.generic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import it.er.layerintercept.IdentifiedIntercept;
import it.er.layerintercept.OverX;
import it.er.manage.BaseManage.BaseManeError;
import it.er.object.Attribute;
import it.er.object.Logged;
import it.er.presentation.admin.BaseAdminContent;
import it.er.presentation.admin.object.fecomponent.SelectItem;
import it.er.service.UserPreferences;
import it.er.transform.Ofelia;
import it.er.util.CustomException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlRootElement(name = "response")
@XmlSeeAlso({SelectItem.class,Attribute.class})
public class GenericScarabResponse extends BaseAdminContent implements IdentifiedIntercept,Ofelia{
	
	private boolean success;
	
	private String message;
	
	private Integer status;
	
	private Object body;
	
	private List<?> bodyL;
	
	private String rely;
	
	public GenericScarabResponse(){
		super();
	}
	
	public GenericScarabResponse(Integer status){
		super();
		this.status = status;
	}
	
	public static void checkForResponse(GenericScarabResponse g) throws CustomException{
		if (g == null)
			throw new CustomException();
	}
	
	public GenericScarabResponse(UserPreferences u,Class <? extends BaseAdminContent> restservice,HttpServletRequest req, boolean requiredAuth) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, CustomException{
		super();
		this.setLogged((Logged) (restservice.getMethod("invokeServerIdentities", new Class[]{UserPreferences.class,HttpServletRequest.class,boolean.class}).invoke(this, new Object[]{u,req,requiredAuth})));
	}
	@XmlElement
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	@XmlElement
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@XmlElement
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	@Override
	public Logged getLogged() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getContent() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@XmlElement
	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}
	
	
	@XmlElementWrapper(name="list")
	public List<?> getBodyL() {
		return bodyL;
	}

	public void setBodyL(List<?> bodyL) {
		this.bodyL = bodyL;
	}
	
	
	@XmlElement
	public String getRely() {
		return rely;
	}

	public void setRely(String rely) {
		this.rely = rely;
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
			throw (CustomException) new CustomException(BaseManeError.LOGIN_REQUIRED.getDescription());

		}
		return l;
	}

	@Override
	public void invokeClientIdentities(HttpServletRequest req, OverX over)
			throws CustomException {
		// TODO Auto-generated method stub
		
	}

	

}
