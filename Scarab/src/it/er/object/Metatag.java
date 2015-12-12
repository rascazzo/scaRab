package it.er.object;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="metatag")
public class Metatag {

	private String title;
	private String type;
	private String URL;
	private String image;
	private String sitename;
	private String admin;
	private List<String> key;
	private String description;
	private String author;
	private String lang;
	private String id;
	
	public Metatag(){}
	
	public Metatag(String id){
		this.id = id;
	}

	@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@XmlElement
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@XmlElement
	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}
	@XmlElement
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	@XmlElement
	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	@XmlElement
	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}
	@XmlElementWrapper(name = "keys")
	public List<String> getKey() {
		return key;
	}

	public void setKey(List<String> key) {
		this.key = key;
	}
	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@XmlElement
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	@XmlElement
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	@XmlElement
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public static Map<String,String> checkExistMetatags(Metatag meta){
		Map<String,String> m = new HashMap<String, String>();
		if (meta == null)
			return null;
		Method[] mds = Metatag.class.getMethods();
		try{
				for (Method md:mds){
						if (md.getName().equals("getKey") || md.getName().equals("getClass"))
							continue;
						else if (md.getName().startsWith("get", 0))
							m.put(md.getName().substring(3).toLowerCase(),((String) String.valueOf(md.invoke(meta, new Object[]{}))));
				}
		} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
			return null;
		}
		return m;
	}
	
	
	
}
