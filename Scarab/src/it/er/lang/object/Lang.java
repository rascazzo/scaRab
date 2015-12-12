package it.er.lang.object;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="lang")
public class Lang {
	
	private String type;
	
	private String suffix;
	
	public Lang(){}
	
	public Lang(String type,String suffix){
		this.type = type;
		this.suffix = suffix;
	}


	@XmlElement
	public String getSuffix() {
		return suffix;
	}
	@XmlElement
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	

}
