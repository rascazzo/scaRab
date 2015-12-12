package it.er.object;


import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="picturesroot")
public class Pictures {

	private boolean isHome;
	
	private int page = 1;
	
	private List<Integer> pageseq = new LinkedList<Integer>();
	
	
	public Pictures(){}
	
	@XmlElement
	public boolean isHome() {
		return isHome;
	}

	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}

	

	@XmlElement
	public int getPage() {
		return page;
	}

	
	public void setPage(int page) {
		this.page = page;
	}

	@XmlElement
	public List<Integer> getPageseq() {
		return pageseq;
	}


	public void setPageseq(List<Integer> pageseq) {
		this.pageseq = pageseq;
	}
	
	
	
}
