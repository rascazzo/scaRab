package it.er.generic;

import it.er.presentation.admin.object.AttributeTagHTML;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;

public abstract class BasicDinamicHTMLContent {
	protected Integer tag_idx = 0;
	
	
	
	protected List<AttributeTagHTML> tagAttr;

	
	public BasicDinamicHTMLContent() {
		super();
		
		this.tagAttr = new LinkedList<AttributeTagHTML>();
	}
	
	public Integer getTag_idx() {
		return tag_idx;
	}


	@XmlElementWrapper(name="attrs")
	public List<AttributeTagHTML> getTagAttr() {
		return tagAttr;
	}

	public void setTagAttr(List<AttributeTagHTML> tagAttr) {
		this.tagAttr = tagAttr;
	}

	
}
