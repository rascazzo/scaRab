package it.er.warehouse;

import java.sql.Timestamp;

public class Walking {

	private Integer idwalking;
	private Timestamp time;
	private Boolean socialactivity;
	
	public Walking(){}
	
	public Walking(Integer idwalking){
		this.idwalking = idwalking;
	}

	public Integer getIdwalking() {
		return idwalking;
	}

	public void setIdwalking(Integer idwalking) {
		this.idwalking = idwalking;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Boolean getSocialactivity() {
		return socialactivity;
	}

	public void setSocialactivity(Boolean socialactivity) {
		this.socialactivity = socialactivity;
	}
	
	
}
