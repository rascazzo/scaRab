package it.er.presentation.admin.object;

import it.er.generic.GenericTag;
import it.er.presentation.admin.object.fecomponent.SelectItem;
import it.er.tag.Tag;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "panel")
public class Panel {

	private String width;
	
	private String height;
	
	private String namePanel;
	
	private List<GenericTag> label;
	
	private List<GenericTag> text;
	
	private List<SelectItem> site;
	
	private List<SelectItem> father;
	
	private List<SelectItem> lang;
	
	public Panel(){
		
	}
	
	@XmlAttribute
	public String getNamePanel() {
		return namePanel;
	}


	public void setNamePanel(String namePanel) {
		this.namePanel = namePanel;
	}


	@XmlAttribute
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	@XmlAttribute
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	@XmlElementWrapper(name = "labels")
	public List<GenericTag> getLabel() {
		return label;
	}
	public void setLabel(List<GenericTag> label) {
		this.label = label;
	}
	@XmlElementWrapper(name = "texts")
	public List<GenericTag> getText() {
		return text;
	}

	public void setText(List<GenericTag> text) {
		this.text = text;
	}
	@XmlElementWrapper(name = "sites")
	public List<SelectItem> getSite() {
		return site;
	}

	public void setSite(List<SelectItem> site) {
		this.site = site;
	}
	@XmlElementWrapper(name = "fathers")
	public List<SelectItem> getFather() {
		return father;
	}

	public void setFather(List<SelectItem> father) {
		this.father = father;
	}

	@XmlElementWrapper(name = "langs")
	public List<SelectItem> getLang() {
		return lang;
	}

	public void setLang(List<SelectItem> lang) {
		this.lang = lang;
	}
	
	
	
	
}
