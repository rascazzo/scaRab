package it.er.presentation.admin.object;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import it.er.generic.BasicDinamicHTMLContent;
import it.er.generic.GenericTag;

@XmlRootElement(name="li")
public class MenuHTMLSingleList extends BasicDinamicHTMLContent implements AttributeTagMapSet,TagMapSet{

	protected List<GenericTag> navTag;
	
	@XmlElementWrapper(name="li")
	public List<GenericTag> getNavTag() {
		return navTag;
	}

	public void setNavTag(List<GenericTag> navTag) {
		this.navTag = navTag;
	}
	

	private GenericTag setGenericTag(String value,String name){
		GenericTag g = new GenericTag();
		g.setName(name);
		g.setValue(value);
		return g;
	}
	
	public MenuHTMLSingleList(){
		super();
		this.navTag = new LinkedList<GenericTag>();

	}
	
	@Override
	public List<GenericTag> offerToMapNewTagname(String tagname,
			String attrName, String attrValue) {
		GenericTag g = this.setGenericTag(attrValue, attrName);
		AttributeTagHTML lg = null;
		lg = new AttributeTagHTML();
		lg.getAttributes().add(g);
		lg.setId(tag_idx);
		this.tagAttr.add(tag_idx, lg);
		GenericTag gt = new GenericTag();
		gt.setName(tagname);
		this.navTag.add(tag_idx,gt);
		tag_idx++;
		return lg.getAttributes();
	}

	

	@Override
	public String getCompleteTagname(String tagname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GenericTag> offerToMapNewTagname(String tagname,
			String tagvalue, String attrName, String attrValue) {
		GenericTag g = this.setGenericTag(attrValue, attrName);
		AttributeTagHTML lg = null;
		lg = new AttributeTagHTML();
		lg.getAttributes().add(g);
		lg.setId(tag_idx);
		this.tagAttr.add(tag_idx, lg);
		GenericTag gt = new GenericTag();
		gt.setName(tagname);
		gt.setValue(tagvalue);
		this.navTag.add(tag_idx,gt);
		tag_idx++;
		return lg.getAttributes();
	}

	@Override
	public List<GenericTag> offerToMapExistTagname(String tagname,
			 String attrName, String attrValue) {
		GenericTag g = this.setGenericTag(attrValue, attrName);
		List<GenericTag> lg = null;
		Iterator<GenericTag> i = this.navTag.iterator();
		Integer lastIdx = null;
		Integer currIdx = 0;
		while(i.hasNext()){
			String tag = i.next().getName();
			if(tag.equals(tagname)){
				lastIdx = currIdx;
			}
			currIdx++;
		}
		if (lastIdx != null){
			if (this.tagAttr.get(lastIdx) != null){
				lg = this.tagAttr.get(lastIdx).getAttributes();
				lg.add(g);
			}
		}
		return lg;
	}
	
	

}
