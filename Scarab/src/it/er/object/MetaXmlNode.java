package it.er.object;

import it.er.dinamic.SetPropertyForChilds;
import it.er.generic.GenericTag;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class MetaXmlNode implements SetPropertyForChilds{
	private String nodename;
	private Integer id;
	private String lang;
	private List<Attribute> attributes;
	private List<String> second;
	private List<GenericTag> text;
	private String lefthref;
	private String righthref;
	private Map<String,String> map;
	
	public MetaXmlNode(){
		this.attributes = new LinkedList<Attribute>();
		this.second = new LinkedList<String>();
		this.lang = "en";
	}
	
	public MetaXmlNode(int id){
		this.id = new Integer(id);
		this.attributes = new LinkedList<Attribute>();
		this.second = new LinkedList<String>();
		this.lang = "en";
	}
	
	
	
	@XmlAttribute
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public void setLangWithList(List<String> lang) {
		this.lang = "";
		Iterator<String> ln = lang.iterator();
		int i = 0;
		while (ln.hasNext()){
			if (i < (lang.size() -1))
				this.lang = this.lang.concat(ln.next()).concat(";");
			else
				this.lang = this.lang.concat(ln.next());
			i++;
		}
	}

	@XmlAttribute
	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	
	@XmlAttribute
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@XmlElement
	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	@XmlElement
	public List<String> getSecond() {
		return second;
	}

	public void setSecond(List<String> second) {
		this.second = second;
	}
	@XmlElement
	@XmlElementWrapper(name = "texts")
	public List<GenericTag> getText() {
		return text;
	}

	public void setText(List<GenericTag> text) {
		this.text = text;
	}
	

	@Override
	public void hasChilds() {
		setLefthref(null);
		setRighthref(null);
	}

	@XmlElement
	public String getLefthref() {
		return lefthref;
	}

	public void setLefthref(String lefthref) {
		this.lefthref = lefthref;
	}
	@XmlElement
	public String getRighthref() {
		return righthref;
	}

	public void setRighthref(String righthref) {
		this.righthref = righthref;
	}

	@Override
	public void noChilds() {
		this.setSecond(null);
	}

	@Override
	public void mapOn() {
		this.map = new HashMap<String, String>();
	}

	@Override
	public void mapOff(){
		this.map = null;
	}
	
	@XmlElementWrapper(name = "mapping")
	public Map<String,String> getMap() {
		return map;
	}

	public void setMap(Map<String,String> map) {
		this.map = map;
	}
	
}
