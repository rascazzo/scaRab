package it.er.presentation.admin.object;

import it.er.generic.BasicDinamicHTMLContent;
import it.er.generic.GenericTag;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="heads")
public class HeadHTML extends BasicDinamicHTMLContent implements AttributeTagMapSet{

	protected List<String> head;
	
	@XmlElementWrapper(name="heads")
	public List<String> getHead() {
		return head;
	}

	public void setHead(List<String> head) {
		this.head = head;
	}
	
	public HeadHTML(){
		super();
		this.head = new LinkedList<String>();
	}

	private GenericTag setGenericTag(String value,String name){
		GenericTag g = new GenericTag();
		g.setName(name);
		g.setValue(value);
		return g;
	}
	
	@Override
	public List<GenericTag> offerToMapNewTagname(String tagname,
			String attrName, String attrValue) {
		GenericTag g = this.setGenericTag(BasicDinamicHTMLContent.outw+attrValue, attrName);
		AttributeTagHTML lg = null;
		lg = new AttributeTagHTML();
		lg.getAttributes().add(g);
		lg.setId(tag_idx);
		this.tagAttr.add(tag_idx, lg);
		this.head.add(tag_idx, tagname);
		tag_idx++;
		return lg.getAttributes();
	}

	@Override
	public List<GenericTag> offerToMapExistTagname(String tagname,
			String attrName, String attrValue) {
		GenericTag g = this.setGenericTag(BasicDinamicHTMLContent.outw+attrValue, attrName);
		List<GenericTag> lg = null;
		Iterator<String> i = this.head.iterator();
		Integer lastIdx = null;
		Integer currIdx = 0;
		while(i.hasNext()){
			String tag = i.next();
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

	@Override
	public String getCompleteTagname(String tagname) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

	
}
