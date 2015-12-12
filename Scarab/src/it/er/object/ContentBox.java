package it.er.object;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="contentbox")
public class ContentBox {
	
	private NovitaContainer text = null;
	
	private Pictures pictures = null;
	
	private Contatti contatti = null;
	
	private String outContext;
	
	public ContentBox(){}
	
	public ContentBox(NovitaContainer n){
		this.text = n;
	}

	@XmlElement
	public NovitaContainer getText() {
		return text;
	}

	public void setText(NovitaContainer text) {
		this.text = text;
	}

	@XmlElement
	public Pictures getPictures() {
		return pictures;
	}

	public void setPictures(Pictures pictures) {
		this.pictures = pictures;
	}
	@XmlElement
	public Contatti getContatti() {
		return contatti;
	}

	public void setContatti(Contatti contatti) {
		this.contatti = contatti;
	}
	@XmlAttribute
	public String getOutContext() {
		return outContext;
	}

	public void setOutContext(String outContext) {
		this.outContext = outContext;
	}
	
	

}
