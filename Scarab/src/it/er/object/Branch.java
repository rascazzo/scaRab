package it.er.object;

import java.util.LinkedList;
import java.util.List;


public class Branch {
	
	private Integer id;
	private Integer idtagname;
	private List<String> branches;
	
	public Branch(){
		this.branches = new LinkedList<String>();
	}
	
	public Branch(Integer id){
		this.branches = new LinkedList<String>();
		this.id = id;
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

	public List<String> getBranches() {
		return branches;
	}

	public void setBranches(List<String> branches) {
		this.branches = branches;
	}


	
	
	
}
