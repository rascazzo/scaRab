package it.er.warehouse;

public class Visit {

	private Integer idvisit;
	private Integer objectid;
	private String objectype;
	private String objectname;
	private String tagname;
	private Boolean nobject;
	
	public Visit(){}
	
	public Visit(Integer idvisit){
		this.idvisit = idvisit;
	}

	public Integer getIdvisit() {
		return idvisit;
	}

	public void setIdvisit(Integer idvisit) {
		this.idvisit = idvisit;
	}

	public Integer getObjectid() {
		return objectid;
	}

	public void setObjectid(Integer objectid) {
		this.objectid = objectid;
	}

	public String getObjectype() {
		return objectype;
	}

	public void setObjectype(String objectype) {
		this.objectype = objectype;
	}

	public String getObjectname() {
		return objectname;
	}

	public void setObjectname(String objectname) {
		this.objectname = objectname;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	public Boolean getNobject() {
		return nobject;
	}

	public void setNobject(Boolean nobject) {
		this.nobject = nobject;
	}
	
	
}
