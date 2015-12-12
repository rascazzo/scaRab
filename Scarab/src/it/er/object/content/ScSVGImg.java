package it.er.object.content;


import javax.xml.bind.annotation.XmlAttribute;

public abstract class ScSVGImg {

	
	
	public ScSVGImg(){}
	
	
	protected String stroke;
	
	protected String strokeWidth;

	protected String fill;
	

	@XmlAttribute
	public String getStroke() {
		return stroke;
	}

	public void setStroke(String stroke) {
		this.stroke = stroke;
	}

	@XmlAttribute(name = "stroke-width")
	public String getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(String strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	
	@XmlAttribute
	public String getFill() {
		return fill;
	}

	public void setFill(String fill) {
		this.fill = fill;
	}
}
