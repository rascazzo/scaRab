package it.er.object;


import it.er.dao.Text;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="novitacontainer")
public class NovitaContainer {

	private List<Text> article = null;
	
	private boolean isArchivio;
	
	private boolean isHome;
	
	private int page = 1;
	
	private List<Integer> pageseq = new LinkedList<Integer>();
	
	
	
	public NovitaContainer(){}

	
	@XmlElementWrapper(name="texts")
	public List<Text> getArticle() {
		return article;
	}

	public void setArticle(List<Text> novita) {
		this.article = novita;
	}

	@XmlElement
	public boolean isArchivio() {
		return isArchivio;
	}


	public void setArchivio(boolean isArchivio) {
		this.isArchivio = isArchivio;
	}
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
