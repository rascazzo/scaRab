package it.er.dao.sidebar;

public class SBTagAttr {

	private Integer id;
	
	private String name;
	
	private String value;
	
	public SBTagAttr(){}
	
	public SBTagAttr(Integer id){
		this.id = id;
	}
	
	public SBTagAttr(String name,String value){
		this.name = name;
		this.value = value;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
