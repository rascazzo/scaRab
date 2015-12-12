package it.er.presentation.admin.object;

import it.er.generic.GenericTag;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="attributes")
public class AttributeTagHTML {

	private List<GenericTag> attributes;
	
	private Integer id;
	
	public AttributeTagHTML(){
		this.attributes = new LinkedList<GenericTag>();
	}

	@XmlElement
	public List<GenericTag> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<GenericTag> attributes) {
		this.attributes = attributes;
	}

	@XmlAttribute
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
