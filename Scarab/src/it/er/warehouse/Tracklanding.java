package it.er.warehouse;

import java.sql.Timestamp;


public class Tracklanding {

	private Integer idlanding;
	private Timestamp time;
	private Integer idvisit;
	private Boolean socialactivity;
	
	public Tracklanding(){}
	
	public Tracklanding(Integer idlanding){
		this.idlanding = idlanding;
	}

	public Integer getIdlanding() {
		return idlanding;
	}

	public void setIdlanding(Integer idlanding) {
		this.idlanding = idlanding;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Integer getIdvisit() {
		return idvisit;
	}

	public void setIdvisit(Integer idvisit) {
		this.idvisit = idvisit;
	}
	
	public Boolean getSocialactivity() {
		return socialactivity;
	}

	public void setSocialactivity(Boolean socialactivity) {
		this.socialactivity = socialactivity;
	}
}
