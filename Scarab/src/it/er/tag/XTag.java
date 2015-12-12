package it.er.tag;

import it.er.dao.Site;
import it.er.dao.sidebar.SBTagOrder;
import it.er.dao.sidebar.SBTagname;
import it.er.object.Attribute;
import it.er.object.Branch;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;

public interface XTag {
	public Integer getIdTagname(String tagname,Site s) throws SQLException;
	public List<Attribute> getAttributes(String tagname,Site s) throws SQLException,Exception;
	public Map<Site,List<Attribute>> getAttributes(String tagname) throws SQLException,Exception;
	public Integer getOrder(String tagname,Site s) throws SQLException,Exception;
	public List<SBTagOrder> getAllOrder(Site s,String tagnamerel) throws SQLException,Exception;
	public Boolean hasAttributes(String tagname, Attribute attribute,Site s) throws SQLException,Exception;
	public Branch getBranch(String tagname,Site s)throws SQLException,Exception;
	public SBTagname getTagname(String tagname,String lang,Site s)throws SQLException,Exception;
	public List<Tag> getTags(String tagnamefather, Boolean active,Site s) throws SQLException,Exception;
	public Map<Site,List<Tag>> getTags(String tagnamefather, Boolean active) throws SQLException,Exception;
	public List<Tag> getTags(String tagnamefather, Boolean active, String key, String value,String lang,Site s) throws SQLException,Exception;
	public Boolean hasSons(String tagname,Site s) throws SQLException,Exception;
	public Tag getRootTag(Integer id,Site s)throws SQLException,Exception;
	public List<Tag> getAllRootTag(Site s) throws SQLException,Exception;
	public Map<Site,List<Tag>> getAllRootTag() throws SQLException,Exception;
	public Boolean isAttribute(Attribute a,Site s)throws SQLException,Exception;
	public int[] insertAttributes(final List<Attribute> l,String tagname,Site s) throws SQLException;
	public Integer insertOrder(Integer numorder, String tagname,Site s) throws SQLException;
	public int[] updateOrder(List<SBTagOrder> sb) throws SQLException;
	public int[] insertBranch(final List<String> branch,String tagname,Site s) throws SQLException;
	public int[] insertTags(final Map<String,String> tag, String tagnamefather,final Map<String,Integer> numorder,String lang,Site s) throws SQLException;
	public Integer deleteTagname(String tagname,Site s) throws SQLException;
	public Integer deleteTagname(Integer id,Site s) throws SQLException;
	public List<String> getxTagFromEntity(String container) throws SQLException;
	public List<Tag> getxTagFromTagNameEnt() throws SQLException;
	public Integer simpleUpdateTagname(Integer id, boolean active, String textvalue, String lang,Site s) throws SQLException;
	public String getTagnameValueByLang(String tagname,String lang,Site s) throws SQLException;
	public int insertTagnameValueByLang(String tagname,String inLangvalue,String lang,Site s) throws SQLException;
	public PlatformTransactionManager getTransactionManager();
}
