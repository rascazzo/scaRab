package it.er.presentation.admin.object;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="nav")
public class MenuHTML {

	private List<MenuHTMLSingleList> uls = new LinkedList<MenuHTMLSingleList>();
	
	public MenuHTML(){
		
	}

	@XmlElementWrapper(name="li")
	public List<MenuHTMLSingleList> getUls() {
		return uls;
	}

	public void setUls(List<MenuHTMLSingleList> uls) {
		this.uls = uls;
	}
	
	
}
