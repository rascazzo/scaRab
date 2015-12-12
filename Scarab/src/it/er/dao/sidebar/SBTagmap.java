package it.er.dao.sidebar;

public class SBTagmap {

	private Integer id;
	
	private String branch;
	
	private Integer idtagname;
	
	public SBTagmap(){}
	
	public SBTagmap(Integer id, String branch){
		this.id = id;
		this.branch = branch;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Integer getIdtagname() {
		return idtagname;
	}

	public void setIdtagname(Integer idtagname) {
		this.idtagname = idtagname;
	}
	
	
}
