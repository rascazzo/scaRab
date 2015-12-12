package it.er.presentation.admin.object;

import it.er.generic.GenericTag;

import java.util.List;

public interface TagMapSet {

	public List<GenericTag>  offerToMapNewTagname(String tagname,String tagvalue,String attrName,String attrValue);
	
	public List<GenericTag>  offerToMapExistTagname(String tagname,String attrName,String attrValue);
}
