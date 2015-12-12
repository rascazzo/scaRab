package it.er.dao.sidebar;

public class SBTagSon {

	private Integer idtagname;
	
	private Integer idtagson;
	
	public SBTagSon() {}
	
	public SBTagSon(Integer idtagname, Integer idtagson) {
		this.idtagname = idtagname;
		this.idtagson = idtagson;
	}

	public Integer getIdtagname() {
		return idtagname;
	}

	public void setIdtagname(Integer idtagname) {
		this.idtagname = idtagname;
	}

	public Integer getIdtagson() {
		return idtagson;
	}

	public void setIdtagson(Integer idtagson) {
		this.idtagson = idtagson;
	}
	
	
}
