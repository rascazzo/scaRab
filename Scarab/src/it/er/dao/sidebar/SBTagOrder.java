package it.er.dao.sidebar;

public class SBTagOrder {
	private Integer id;
	
	private Integer idtagname;
	
	private Integer numorder;
	
	public SBTagOrder(){}
	
	public SBTagOrder(Integer idTagname){
		this.idtagname = idTagname;
	}
	
	public SBTagOrder(Integer id,Integer idTagname){
		this.id = id;
		this.idtagname = idTagname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdtagname() {
		return idtagname;
	}

	public void setIdtagname(Integer idtagname) {
		this.idtagname = idtagname;
	}

	public Integer getNumorder() {
		return numorder;
	}

	public void setNumorder(Integer numorder) {
		this.numorder = numorder;
	}
	
	
}
