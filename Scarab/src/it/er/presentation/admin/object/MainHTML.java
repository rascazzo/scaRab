package it.er.presentation.admin.object;

import it.er.generic.GenericTag;
import it.er.presentation.admin.object.fecomponent.SelectItem;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mainpanel")
public class MainHTML {

	private List<Panel>  panel = new LinkedList<Panel>();
	
	private String outContext;
	
	private List<GenericTag> label;
	
	private List<GenericTag> text;
	
	private List<GenericTag> site;
	
	private List<GenericTag> father;
	
	private List<GenericTag> son;
	
	public MainHTML(){
		this.father = new LinkedList<GenericTag>();
		this.son = new LinkedList<GenericTag>();
	}


	@XmlElementWrapper(name = "panels")
	public List<Panel> getPanel() {
		return panel;
	}


	public void setPanel(List<Panel> panel) {
		this.panel = panel;
	}

	@XmlAttribute
	public String getOutContext() {
		return outContext;
	}


	public void setOutContext(String outContext) {
		this.outContext = outContext;
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
	
	@XmlElementWrapper(name = "mainsites")
	public List<GenericTag> getSite() {
		return site;
	}

	public void setSite(List<GenericTag> site) {
		this.site = site;
	}

	@XmlElementWrapper(name = "mainfathers")
	public List<GenericTag> getFather() {
		return father;
	}


	public void setFather(List<GenericTag> father) {
		this.father = father;
	}

	@XmlElementWrapper(name = "mainsons")
	public List<GenericTag> getSon() {
		return son;
	}


	public void setSon(List<GenericTag> son) {
		this.son = son;
	}
	
	
}
