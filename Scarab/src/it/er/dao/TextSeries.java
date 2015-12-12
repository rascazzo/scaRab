package it.er.dao;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="textseries")
public class TextSeries {

	private List<Text> serie;
	
	private String nosqlCollectionType = "text_"; 
	
	private String nosqlCollectionName;
	
	private Integer total;
	
	private Integer start;
	
	private Integer limit;
	
	private Integer n;
	
	private String title;
	
	private String subtitle;
	
	public TextSeries(){
		this.serie = new LinkedList<Text>();
	}

	@XmlElementWrapper(name="series")
	public List<Text> getSerie() {
		return serie;
	}

	public void setSerie(List<Text> serie) {
		this.serie = serie;
	}

	@XmlElement
	public String getNosqlCollectionType() {
		return nosqlCollectionType;
	}

	public void setNosqlCollectionType(String nosqlCollectionType) {
		this.nosqlCollectionType = nosqlCollectionType;
	}

	@XmlElement
	public String getNosqlCollectionName() {
		return nosqlCollectionName;
	}

	public void setNosqlCollectionName(String nosqlCollectionName) {
		this.nosqlCollectionName = nosqlCollectionName;
	}

	@XmlElement
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@XmlElement
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	@XmlElement
	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	@XmlElement
	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}
	@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@XmlElement
	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	
 }
