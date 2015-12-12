package it.er.object;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="novitacontainersingle")
public class NovitaContainerSingle {

	private NovitaPos article = null;
	
	private boolean isArchivio;
	

	@XmlElement
	public NovitaPos getArticle() {
		return article;
	}

	public void setArticle(NovitaPos article) {
		this.article = article;
	}
	@XmlElement
	public boolean isArchivio() {
		return isArchivio;
	}

	public void setArchivio(boolean isArchivio) {
		this.isArchivio = isArchivio;
	}
	
	
	
	
	
}
