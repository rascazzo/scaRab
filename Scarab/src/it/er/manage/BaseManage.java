package it.er.manage;

import it.er.basic.Basic;
import it.er.dao.IParamDAO;
import it.er.dao.Site;
import it.er.dinamic.ResolvPath;
import it.er.generic.GenericTag;
import it.er.lang.LanguageAccess;
import it.er.object.Layer;
import it.er.object.Logged;
import it.er.object.Metatag;
import it.er.service.SideBarBean;
import it.er.service.SideBarX;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseManage extends Basic implements ResolvPath{
	
	private static final String content = "content";
	
	private static final String sidebarContent = SideBarBean.getSidebarapp();
	
	public static String getContent() {
		return content;
	}

	public static String getSidebarcontent() {
		return sidebarContent;
	}

	public enum BaseManeError{
		LOGIN_REQUIRED("Required Login"),
		LOGIN_DONE("In Login");
		private BaseManeError(String des){
			this.des = des;
		}
		
		private String des;
		
		public String getDescription(){
			return this.des;
		}
	}
	
	@Override
	public String dinamicPath(String file, String nameParam, IParamDAO param) throws SQLException{
		return param.getParam(nameParam).getValue()+"/"+file;
	}

	protected List<Integer> loadSeqPage(int limit,int total,List<Integer> l){
		int i = 0;
		for (i=0;i<((int)total/limit);i++)
			l.add(new Integer(i+1));
		if (total % limit != 0)
			l.add(new Integer(i+1));
		return l;
	}
	
	/*
	protected String getRealNameSpace(boolean namespace, HttpServletRequest req, String otherURL){
		String nameSpaceinPage = "";
		if (namespace){
			nameSpaceinPage = otherURL;
		} else {
			nameSpaceinPage = req.getRequestURL().toString();
		}
		return nameSpaceinPage;
	}*/
	
	protected int getOffset(int page,int limit){
		if (page>1)
			return limit*(page-1)-1;
		else return 0;
	}
	
	protected void checkLang(Logged logged,String lang){
		if (!logged.getCurrLang().equals(lang)){
			logged.setCurrLang(lang);
		}
	}
	
	protected void setMetaKeys(String authorinfo,
			String titleinfo,
			String imageinfo,
			String descriptioninfo,
			String keysinfo,
			String lang,
			Metatag meta){
		boolean s = false;
		if (authorinfo != null && !authorinfo.isEmpty()){
			meta.setAuthor(authorinfo);
			s = true;
		}	
		if (titleinfo != null && !titleinfo.isEmpty()){
			meta.setTitle(titleinfo);
			s = true;
		}
		if (imageinfo != null && !imageinfo.isEmpty()){
			meta.setImage(imageinfo);
			s = true;
		}
		if (descriptioninfo != null && !descriptioninfo.isEmpty()){
			meta.setDescription(descriptioninfo);
			s = true;
		}	
		if (!s){
			meta = null;
			return;
		}
		meta.setLang(lang);
		String[] k = keysinfo.split("\\,");
		List<String> keys = new ArrayList<String>();
		if (k != null && k.length > 0){
			for (String i: k){
				keys.add(i);
			}
			
		} else if (keysinfo!=null && !keysinfo.isEmpty()){
			keys.add(keysinfo);
			
		}
		
		meta.setKey(keys);
	}
	
	protected void addURLToMetaKey(Metatag meta,String tagname,
			String id,
			Site s,
			SideBarX sd,
			String lang,
			String contentType,
			String query){
		if (sd != null){
			meta.setURL(sd.resolveHrefByTagName(s, contentType, tagname, lang, id, query));
		}
	}
	
	protected void setTextsAndLabels(Layer l,LanguageAccess genericScarabLang, String lang){
		if (genericScarabLang == null)
			return;
		List<GenericTag> label = genericScarabLang.getChilds(lang, "labels");
		List<GenericTag> text = genericScarabLang.getChilds(lang, "texts");
		l.setText(text);
		l.setLabel(label);
	}
}
