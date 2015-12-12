package it.er.presentation.admin.object;

import it.er.generic.GenericTag;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "ul")
public class MenuElementMount{

	
	private String name;
	
	private String tagname;
	
	
	
	
	@XmlAttribute
	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public MenuElementMount(){
		super();
		this.tags = new MenuHTMLSingleList();	
	}

	@XmlElement(name = "tagAttr")
	public MenuHTMLSingleList getTags() {
		return this.tags;
	}

	public void setTags(MenuHTMLSingleList tags) {
		this.tags = tags;
	}

	protected MenuHTMLSingleList tags;

	public void clear(){
		this.tags = null;
		this.name = null;
	}
	
}
