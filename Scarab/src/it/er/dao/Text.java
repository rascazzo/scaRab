package it.er.dao;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="text")
public class Text implements TextArgument {

	private String idText;
	private String body;
	private int createDate;
	private int last_update;
	private boolean archive;
	private String textIdUser;
	private String title;
	private String tagname;
	private String sitename;
	private String argument;
	private String order;
	
	
	public Text(){}
	
	public Text(String idtext){
		this.idText = idtext;
	}
	@XmlElement
	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
	}
	@XmlElement
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	@XmlElement
	public int getCreateDate() {
		return createDate;
	}

	public void setCreateDate(int createDate) {
		this.createDate = createDate;
	}
	@XmlElement
	public int getLast_update() {
		return last_update;
	}

	public void setLast_update(int last_update) {
		this.last_update = last_update;
	}
	@XmlElement
	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	@XmlElement
	public String getTextIdUser() {
		return textIdUser;
	}

	public void setTextIdUser(String textIdUser) {
		this.textIdUser = textIdUser;
	}
	@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlElement
	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	@XmlElement
	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	@XmlElement
	public String getArgument() {
		return argument;
	}

	public void setArgument(String argument) {
		this.argument = argument;
	}

	@Override
	public void cleanArgument() {
		this.argument = null;
	}
	@XmlElement
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

		
	
	
}
