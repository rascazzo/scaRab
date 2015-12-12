package it.er.lang;

import java.util.List;

import it.er.generic.GenericTag;
import it.er.lang.object.Lang;

public interface LanguageAccess {

	public String searchLangValue(String lang,String key);
	
	public List<GenericTag> getChilds(String lang,String ancestorTag);
	
	public List<String> getAllLangs();
	
	public List<Lang> getAllLangsObj();
}

