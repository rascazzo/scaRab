package it.er.dao.sidebar;

public class SBTagname {

	private String tagSiteId;
	
	private Integer id;
	
	private String name;
	
	private String textValue;
	
	private Boolean active;
	
	
	public SBTagname(){}
	
	public SBTagname(Integer id){
		this.id = id;
	}
	
	public SBTagname(String name, String tagSiteId){
		this.name = name;
		this.tagSiteId = tagSiteId;
	}

	public String getTagSiteId() {
		return tagSiteId;
	}

	public void setTagSiteId(String tagSiteId) {
		this.tagSiteId = tagSiteId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}
