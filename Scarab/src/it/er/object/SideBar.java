package it.er.object;


import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="sidebar")
public class SideBar {
	private List<MetaXmlNode> first;
	private List<MetaXmlNode> second;
	private List<MetaXmlNode> friends;
	
	public SideBar(){
		this.first = new LinkedList<MetaXmlNode>();
		this.second = new LinkedList<MetaXmlNode>();
		this.friends = new LinkedList<MetaXmlNode>();
	}
	@XmlElement
	public List<MetaXmlNode> getFirst() {
		return first;
	}
	public void setFirst(List<MetaXmlNode> first) {
		this.first = first;
	}
	@XmlElement
	public List<MetaXmlNode> getSecond() {
		return second;
	}
	public void setSecond(List<MetaXmlNode> second) {
		this.second = second;
	}
	@XmlElement
	public List<MetaXmlNode> getFriends() {
		return friends;
	}
	public void setFriends(List<MetaXmlNode> friends) {
		this.friends = friends;
	}
	
	
	
	
	
}
