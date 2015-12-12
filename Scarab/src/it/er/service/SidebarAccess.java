package it.er.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.er.dao.Site;
import it.er.object.Attribute;
import it.er.tag.Tag;
import it.er.util.CustomException;

public interface SidebarAccess {
	public List<Tag> getAllRootTag(Site s) throws CustomException;
	public List<Tag> getTagsSon(Site s,String tagnamerel) throws CustomException;
	public int[] insertTgn(HttpServletRequest req,HttpServletResponse res, String tagname, String tagvalue, String numorder,
				String sitename,String tgtypeFirst, String tgtypeSecond, String tgtypeGrp,
				String tagnamerel,String lang) throws CustomException;
	public List<Attribute> getAttributes(Site s,String tagname) throws CustomException;
	public int[] insertAttributes(final List<Attribute> l,String tagname,Site s) throws CustomException;
	
}
