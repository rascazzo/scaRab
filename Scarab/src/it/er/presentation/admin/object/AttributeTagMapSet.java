package it.er.presentation.admin.object;

import it.er.generic.GenericTag;

import java.util.List;

public interface AttributeTagMapSet {

	public List<GenericTag>  offerToMapNewTagname(String tagname,String attrName,String attrValue);
	
	public List<GenericTag>  offerToMapExistTagname(String tagname,String attrName,String attrValue);
	
	public String getCompleteTagname(String tagname);
	
}
