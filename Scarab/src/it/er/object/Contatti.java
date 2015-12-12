package it.er.object;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="contatti")
public class Contatti {
	private List<String> contatti = null;
	
	public Contatti(){
		this.contatti = new LinkedList<String>();
	}

	@XmlElement
	public List<String> getContatti() {
		return contatti;
	}

	public void setContatti(List<String> contatti) {
		this.contatti = contatti;
	}
	
	
}
