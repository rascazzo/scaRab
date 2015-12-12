package it.er.object;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sitemap")
public class SiteMap {
	/*Home*/
	private String home;
	/*News*/
	private List<String> articoli;
	private Map<String,List<String>> archivio;
	/*Contenuti*/
	private String galleria;
	private String cavalletto;
	private String acasatua;
	/*Iniziative*/
	private List<String> iniziative;
	/*Links*/
	private List<String> links;
	/*Contatti*/
	private String contatti;
	
	
	@XmlElement
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	@XmlElement
	public List<String> getArticoli() {
		return articoli;
	}
	public void setArticoli(List<String> articoli) {
		this.articoli = articoli;
	}
	@XmlElement
	public Map<String, List<String>> getArchivio() {
		return archivio;
	}
	public void setArchivio(Map<String, List<String>> archivio) {
		this.archivio = archivio;
	}
	@XmlElement
	public String getGalleria() {
		return galleria;
	}
	public void setGalleria(String galleria) {
		this.galleria = galleria;
	}
	@XmlElement
	public String getCavalletto() {
		return cavalletto;
	}
	public void setCavalletto(String cavalletto) {
		this.cavalletto = cavalletto;
	}
	@XmlElement
	public String getAcasatua() {
		return acasatua;
	}
	public void setAcasatua(String acasatua) {
		this.acasatua = acasatua;
	}
	@XmlElement
	public List<String> getIniziative() {
		return iniziative;
	}
	public void setIniziative(List<String> iniziative) {
		this.iniziative = iniziative;
	}
	@XmlElement
	public List<String> getLinks() {
		return links;
	}
	public void setLinks(List<String> links) {
		this.links = links;
	}
	@XmlElement
	public String getContatti() {
		return contatti;
	}
	public void setContatti(String contatti) {
		this.contatti = contatti;
	}
}
