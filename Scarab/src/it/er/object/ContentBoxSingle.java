package it.er.object;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="contentboxsingle")
public class ContentBoxSingle {
	
	private NovitaContainerSingle novita;
	
	private Pictures pictures;
	
	public ContentBoxSingle(){}
	
	public ContentBoxSingle(NovitaContainerSingle novita){
		this.novita = novita;
	}

	@XmlElement
	public NovitaContainerSingle getNovita() {
		return novita;
	}

	public void setNovita(NovitaContainerSingle novita) {
		this.novita = novita;
	}
	@XmlElement
	public Pictures getPictures() {
		return pictures;
	}

	public void setPictures(Pictures pictures) {
		this.pictures = pictures;
	}

	
	
}
