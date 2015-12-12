package it.er.object.content;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "svg")
public class ScSvgWrap {

	protected PaletteBox box;
	
	private String styleClassName = "scSwsvg";
	
	public ScSvgWrap(){
		this.box = new PaletteBox();
	}

	@XmlElement(name = "rect")
	public PaletteBox getBox() {
		return box;
	}

	public void setBox(PaletteBox box) {
		this.box = box;
	}

	@XmlAttribute(name= "class")
	public String getStyleClassName() {
		return styleClassName;
	}

	public void setStyleClassName(String styleClassName) {
		this.styleClassName = styleClassName;
	}
	
	
}
