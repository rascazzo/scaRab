package it.er.object;


import it.er.dao.Text;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="text")
public class NovitaPos {
	
	private Text text;
	
	private int pos;
	
	public NovitaPos(){}
	
	public NovitaPos(Text n,int pos){
		this.text = n;
		this.pos = pos;
	}

	@XmlElement
	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
	@XmlAttribute
	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}
	
	

}
