package it.er.dinamic;

import it.er.dao.Site;
import it.er.object.Attribute;
import it.er.object.MetaXmlNode;
import it.er.tag.Tag;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public interface MetaRelation {
	public List<Attribute> inspectOtherAttribute(NamedNodeMap nodeAttr, String nameliv,boolean flagputinmap);
	public Map<String,String> listToMap(List<String> l,String prelink);
	public Map<String,String> detectMap(String maptype,String prelink);
	public Object fetchMetaRelationXML(NodeList nodelist,Object first) throws Exception;
	public void XMLSetSidebar() throws Exception;
	public void DBSetSidebar() throws Exception;
	public void loadMap(String tagname, Object metanode) throws SQLException;
	public void loadMapDB(Site s,String tagname, Object metanode) throws Exception;
	public Object fetchMetaRelationDB(List<Tag> tagFirst, Site s) throws Exception;
	public List<Attribute> inspectOtherAttributeDB(Site s,List<Attribute> attr, String nameliv, boolean flagputinmap);
}
