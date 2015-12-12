package it.er.dao.sidebar;

public class SBTagnameAttr {

	private Integer idTagname;
	
	private Integer idAttribut;
	
	public SBTagnameAttr(){}
	
	public SBTagnameAttr(Integer idTagname, Integer idAttribut){
		this.idTagname = idTagname;
		this.idAttribut = idAttribut;
	}

	public Integer getIdTagname() {
		return idTagname;
	}

	public void setIdTagname(Integer idTagname) {
		this.idTagname = idTagname;
	}

	public Integer getIdAttribut() {
		return idAttribut;
	}

	public void setIdAttribut(Integer idAttribut) {
		this.idAttribut = idAttribut;
	}
	
	
}
