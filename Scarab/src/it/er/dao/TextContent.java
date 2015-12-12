package it.er.dao;

import it.er.object.Metatag;

import java.util.Deque;
import java.util.List;

public interface TextContent {

	public int insert(Text t,String lang,Metatag meta) throws Exception;
	public int insertSeries(TextSeries s,String colname,String lang, Metatag meta) throws Exception;
	public int[] insert(List<Text> t,String lang) throws Exception;
	public List<String> getIdsView(String tagname,String lang) throws Exception;
	public List<String> getIdsSeriesView(String tagname,String colname,String lang) throws Exception;
	public List<String> getIdsViewByTitle(String tagname,String title,String lang) throws Exception;
	public List<String> getIdsSeriesViewByTitle(String tagname,String colname,String title,String lang) throws Exception;
	public Deque<Text> getTextListShort(int limit, int start,String tagname,String argument,String lang) throws Exception;
	public List<Text> readTextList(int limit, int start,String tagname,String argument,String lang) throws Exception;
	public List<Text> readTextList(int limit, int start,String tagname,String argument,String title,String lang) throws Exception;
	public List<Text> readTextSeriesList(int limit, int start,String tagname,String colname,String lang) throws Exception;
	public List<Metatag> readMetatagByTitle(String colname,String title,String lang) throws Exception;
}
