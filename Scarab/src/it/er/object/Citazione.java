package it.er.object;

import it.er.dao.IParamDAO;
import it.er.dinamic.ResolvPath;

import java.sql.SQLException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="citazione")
public class Citazione implements ResolvPath {
	private int idCitazione;
	private String testo;
	private String autore;
	private int citIdUtente;
	private String image;
	private Boolean attiva;
	private static final String thumbPrefix = "thumb_";
	private String thumb = null;
	
	public Citazione(){
		
	}
	
	
	@XmlElement(name="thumbprefix")
	public String getThumb() {
		if (this.thumb==null)
			return thumbPrefix;
		else
			return thumb;
	}


	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	@XmlAttribute
	public int getIdCitazione() {
		return idCitazione;
	}

	public void setIdCitazione(int idCitazione) {
		this.idCitazione = idCitazione;
	}
	@XmlElement
	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}
	@XmlElement
	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}
	@XmlElement
	public int getCitIdUtente() {
		return citIdUtente;
	}

	public void setCitIdUtente(int citIdUtente) {
		this.citIdUtente = citIdUtente;
	}
	@XmlElement
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	@XmlElement
	public Boolean getAttiva() {
		return attiva;
	}

	public void setAttiva(Boolean attiva) {
		this.attiva = attiva;
	}

	@Override
	public String dinamicPath(String file, String nameParam, IParamDAO param) throws SQLException{
		String dinamic = "/"+param.getParam(nameParam).getValue()+"/"+file;
		return dinamic;
	}
}
