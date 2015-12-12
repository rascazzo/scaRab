package it.er.manage.admin.navigation;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.rowset.spi.SyncResolver;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import it.er.generic.GenericTag;
import it.er.lang.LanguageAccess;
import it.er.presentation.admin.object.MenuElementMount;
import it.er.presentation.admin.object.MenuHTMLSingleList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class NavigationGenericManageBean extends DefaultHandler implements NavigationAccess,InitializingBean{


	private static Logger logger = LogManager.getLogger(NavigationGenericManageBean.class);

	public String genericFileName = "generic_adminMenu_scarab.xml";
	
	public String genericFilePath;
	
	private LanguageAccess languageAccess;
	
	public String getGenericFilePath() {
		return genericFilePath;
	}
	public void setGenericFilePath(String genericFilePath) {
		this.genericFilePath = genericFilePath;
	}
	
	public LanguageAccess getLanguageAccess() {
		return languageAccess;
	}
	@Autowired
	public void setLanguageAccess(LanguageAccess languageAccess) {
		this.languageAccess = languageAccess;
	}

	public String internalResourceScarabPath;
	
	private File genericFile;
	
	private SAXParserFactory factory = SAXParserFactory.newInstance();
	
	private SAXParser menuParser = null;
	
	private MenuHTMLSingleList lilist;
	
	private MenuElementMount mounted;
	
	private List<MenuElementMount> menu;
	
	private boolean loginRequired = false;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			menuParser = factory.newSAXParser();
			// Obtain our environment naming context
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			internalResourceScarabPath = String.valueOf(envCtx.lookup("internalResourceScarabPath"));
			String myclasses = ("WEB-INF,classes,it,er,resource,").replaceAll("\\,", new String(new char[]{File.separatorChar}));
			genericFile = new File(genericFilePath+File.separatorChar+internalResourceScarabPath+File.separatorChar+myclasses+genericFileName);
			
		} catch (SAXParseException e){
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	
	public String langNav = "";
	public String hrefPrefixNav = "";
	public boolean elementTag = false;
	public boolean rootnav = false;
	public String defaulttag = "";
	public String tagname = "";
	public boolean mountingElement = false;
	public String mountingName = "";
	public String currentqNameLi = "";
	public String currentqNameLiId = "";
	public Attributes currentAttributesLi;
	public String mountedQname = "";
	
	
	private DefaultHandler handler = new DefaultHandler(){
		@Override
		public void startDocument() throws SAXException {
			menu = new LinkedList<MenuElementMount>();
			lilist = new MenuHTMLSingleList();
			mounted = new MenuElementMount();
		};
		
		@Override
		public void startElement(String uri, String localName,
					String qName, Attributes attributes)
					throws SAXException {
			if (attributes.getIndex("id") != -1 && mountingElement){
				currentqNameLiId = attributes.getValue("id");
				currentqNameLi = qName;
				currentAttributesLi = attributes;
				elementTag = true;
			} else {
				elementTag = false;
			}
			
			if (qName.equalsIgnoreCase("rootnav") && !mountingElement){
				rootnav = true;
				if (attributes.getIndex("defaulttag") != -1){
					defaulttag = attributes.getValue("defaulttag");
					tagname = defaulttag;
				}
			} 
			
			if (attributes.getIndex("mounting") != -1 && attributes.getValue("mounting").equals("true") ){
				if ((attributes.getIndex("loginrequired") != -1 && attributes.getValue("loginrequired").equals("true") && loginRequired) 
						|| ((attributes.getIndex("loginrequired") != -1 && attributes.getValue("loginrequired").equals("false") && !loginRequired) || (attributes.getIndex("loginrequired") == -1))){
					mountingElement = true;
					mountedQname = qName;
					if (attributes.getIndex("name") != -1){
						mountingName =  attributes.getValue("name");
						mounted.setName(languageAccess.searchLangValue(langNav,mountingName));
						mounted.setTagname(mountedQname);
					}
					if (attributes.getIndex("defaulttag") != -1){
						tagname = attributes.getValue("defaulttag");
					} else {
						tagname = defaulttag;
					}
				} 
			}
			
		}
		
		@Override
		public void endElement(String uri, String localName,
					String qName) throws SAXException {
			if (mountingElement && qName.equals(mountedQname)){
				mounted.setTags(lilist);
				menu.add(mounted);
				mountingElement = false;
				mounted = new MenuElementMount();
				lilist = new MenuHTMLSingleList();
				tagname = defaulttag;
			}
			
			if (rootnav){
				rootnav = false;
				//tagname = defaulttag;
			}
		}
		
		@Override
		public void characters(char[] ch, int start, int length)
					throws SAXException {
			if (elementTag){
				String tmprefprefixnav = "";
				if (currentAttributesLi.getIndex("basepath") != -1){
					tmprefprefixnav = hrefPrefixNav;
					hrefPrefixNav = currentAttributesLi.getValue("basepath");
				}
				
				int i = 0;
				while (i<currentAttributesLi.getLength()){
					boolean flag = false;
					if (i == 0 && currentAttributesLi.getIndex("href") != -1 ){
						flag = true;
						lilist.offerToMapNewTagname(tagname,languageAccess.searchLangValue(langNav,new String(ch,start,length)),
								"href",hrefPrefixNav+"/"+langNav+"/"+currentAttributesLi.getValue("href"));
						
					} else if (i == 0 && currentAttributesLi.getIndex("action") != -1){
						flag = true;
						lilist.offerToMapNewTagname(tagname,languageAccess.searchLangValue(langNav,new String(ch,start,length)),
								"action",hrefPrefixNav+"/"+currentAttributesLi.getValue("action"));
					} else if (!flag && !(currentAttributesLi.getQName(i).equals("href") || currentAttributesLi.getQName(i).equals("action")) && i == 0){
						lilist.offerToMapNewTagname(tagname,languageAccess.searchLangValue(langNav,new String(ch,start,length)),
								currentAttributesLi.getQName(i),currentAttributesLi.getValue(i));		
					} else if (!(currentAttributesLi.getQName(i).equals("href") || currentAttributesLi.getQName(i).equals("action"))){
						lilist.offerToMapExistTagname(tagname,
								currentAttributesLi.getQName(i),currentAttributesLi.getValue(i));	
					}
					i++;
				}
				if (currentAttributesLi.getIndex("basepath") != -1){
					hrefPrefixNav = tmprefprefixnav;
				}
				elementTag = false;
			} 
			
		}
	};
	
	@Override
	public synchronized List<MenuElementMount> genericNavigationFetch(String lang, String hrefPrefix,boolean loginRequired) throws SAXException,IOException{
			langNav = lang;
			hrefPrefixNav = hrefPrefix;
			this.loginRequired = loginRequired;
		try {
			menuParser.parse(genericFile, handler);
			this.loginRequired = false;	
		} catch (SAXException | IOException e){
			
			logger.error(e.getMessage(), e);
		}
		
		return menu;
	}
	
	
}