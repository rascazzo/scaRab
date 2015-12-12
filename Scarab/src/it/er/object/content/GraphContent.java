package it.er.object.content;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "graph")
@XmlSeeAlso(ScSvgWrap.class)
public class GraphContent {

	private List<?> svgContainer;
	
	public GraphContent(){}
	
	public GraphContent(List<?> l){
		this.svgContainer = l;
	}
	@XmlElement(name = "svg")
	@XmlElementWrapper(name = "lsvg")
	public List<?> getSvgContainer() {
		return svgContainer;
	}

	public void setSvgContainer(List<?> svgContainer) {
		this.svgContainer = svgContainer;
	}

	private String outContext;

	@XmlElement
	public String getOutContext() {
		return outContext;
	}

	public void setOutContext(String outContext) {
		this.outContext = outContext;
	}
	
	
	
	
	
}
