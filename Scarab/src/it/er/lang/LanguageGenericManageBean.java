package it.er.lang;

import it.er.generic.GenericTag;
import it.er.lang.object.Lang;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;


public class LanguageGenericManageBean extends DefaultHandler implements LanguageAccess,InitializingBean,LanguageAdmin{
	
	private static Logger logger = LogManager.getLogger(LanguageGenericManageBean.class);
	
	private List<Lang> allLang;

	private static final String genericFileName = "generic_scarab_lang_container.xml";
	
	public String genericFilePath;
	
	private static final String externalFileName = "external_scarab_lang_container.xml";
	
	public String externalFilePath;
	
	public String internalResourceScarabPath;
	
	public String externalResourceScarabPath;
	
	private static final String externalResourceScarabFolder = "lang";
	
	public String getGenericFilePath() {
		return genericFilePath;
	}
	public void setGenericFilePath(String genericFilePath) {
		this.genericFilePath = genericFilePath;
	}

	private File genericFile;
	
	private File externalFile;
	
	private SAXParserFactory factory = SAXParserFactory.newInstance();
	
	private SAXParser langParser = null;
	
	private SAXParser initParser = null;
	
	private SAXParser childParser = null;
	
	private String exitValue = new String();
	
	private List<GenericTag> exitListChilds = new LinkedList<GenericTag>();
	
	private static int currentposition = 0;

	private boolean langRightElement = false;
	
	private boolean langFoundElement = false;
	
	
	public String currentLang;
	
	public String currentKey;
	
	public String currentChildsLang;
	
	public String currentAncestor;
	
	public boolean startCurrentAncestor = false;
	
	public boolean langRightChildsElement = false;
	
	public boolean langFoundAncestorElement = false;
	
	public int langFoundChildsCount = 0;
	
	public String langFoundChildTagname = null;

	public boolean langFoundChild = false;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Context initCtx = null;
		Context envCtx = null;
				
		try {
			langParser = factory.newSAXParser();
			childParser = factory.newSAXParser();
			initParser = factory.newSAXParser();
			// Obtain our environment naming context
			initCtx = new InitialContext();
			envCtx = (Context) initCtx.lookup("java:comp/env");
			internalResourceScarabPath = String.valueOf(envCtx.lookup("internalResourceScarabPath"));
			String myclasses = ("WEB-INF,classes,it,er,resource,").replaceAll("\\,", new String(new char[]{File.separatorChar}));
			genericFile = new File(genericFilePath+File.separatorChar+internalResourceScarabPath+File.separatorChar+myclasses+LanguageGenericManageBean.genericFileName);
			allLang = new ArrayList<Lang>();
			initParser.parse(genericFile, allLanghandler);
		} catch (SAXParseException e){
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		/*
		 * external
		 */
		try {
			externalResourceScarabPath = String.valueOf(envCtx.lookup("externalResourceScarabPath"));
			externalFile = new File(externalResourceScarabPath+File.separatorChar+LanguageGenericManageBean.externalResourceScarabFolder+File.separator+LanguageGenericManageBean.externalFileName);
			if(externalFile != null) initParser.parse(externalFile, allLanghandler);
		} catch (Exception e) {
			logger.info("there is not external lang resource. Or error");
		}
	}
	
	DefaultHandler allLanghandler = new DefaultHandler(){
		@Override
		public void startElement(String uri, String localName,
				String qName, Attributes attributes) throws SAXException {
			
			if (qName.equalsIgnoreCase("lang") && attributes.getIndex("type") != -1 && attributes.getIndex("suffix") != -1){ 
				allLang.add(new Lang(attributes.getValue("type"),attributes.getValue("suffix")));
			} 
			
		}
	};
	
	
	DefaultHandler handler = new DefaultHandler(){
							
		@Override
		public void startElement(String uri, String localName,
				String qName, Attributes attributes) throws SAXException {
			
			if (qName.equalsIgnoreCase("lang") && attributes.getIndex("type") != -1 && 
					attributes.getValue("type").equals(currentLang)){
				langRightElement = true;
			} 
			
			if (qName.equalsIgnoreCase(currentKey) ){
				langFoundElement = true;
				
			} 
			
		}
		
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			
			if (langFoundElement){
				langFoundElement = false;
			}
		}
		
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			 
			if (langFoundElement && langRightElement){
				String tmp = new String(ch, start, length);
				exitValue = tmp;
				currentposition ++;
				langFoundElement = false;
				langRightElement = false;
			}
		}
	};

	@Override
	public synchronized String searchLangValue(String lang, String key) {
		String value = null;

		try {
				currentKey = key;
				currentLang = lang;
				langParser.parse(genericFile, handler);
				

				if (exitValue != null && !exitValue.isEmpty()){
					value =  exitValue;
					currentposition --;
				}else if (externalFile != null){
					langParser.parse(externalFile, handler);

					if (exitValue != null && !exitValue.isEmpty()){
						value =  exitValue;
						currentposition --;
					}
				}else{
						value =  "#error#";
				}
		} catch (SAXException | IOException e){
			logger.error(e.getMessage()+"lang Elements are"+currentposition, e);
		} catch (Exception e){
			logger.error(e.getMessage()+"lang Elements are"+currentposition, e);
		}
		
		return value;
	}
	
	DefaultHandler childsHandler = new DefaultHandler(){
		public void startDocument() throws SAXException {
			exitListChilds = new LinkedList<GenericTag>();
		};
		
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (qName.equalsIgnoreCase("lang") && attributes.getIndex("type") != -1 && 
					attributes.getValue("type").equals(currentChildsLang)){
				langRightChildsElement = true;
			} 
			
			if (qName.equalsIgnoreCase(currentAncestor) && langRightChildsElement){
				langFoundAncestorElement = true;
			} 
			if (langFoundAncestorElement && !qName.equalsIgnoreCase(currentAncestor)){
				langFoundChild = true;
			} else {
				langFoundChild = false;
			}
				
			if (langFoundChild){
				langFoundChildTagname = qName;
			}
		};
		
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (langFoundChild){
				langFoundChild = false;
			}
			
			
			if (langFoundAncestorElement && qName.equalsIgnoreCase(currentAncestor)){
				langFoundAncestorElement = false;
				langFoundChildTagname = null;
				langFoundChild = false;
				langRightChildsElement = false;
			}
			
			if (qName.equalsIgnoreCase("lang") && langRightChildsElement){
				langRightChildsElement = false;
			}
		}
		
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if (langFoundChild){
				String tmp = new String(ch, start, length); 
				GenericTag g = new GenericTag();
				g.setName(langFoundChildTagname);
				g.setValue(tmp);
				exitListChilds.add(g);
				langFoundChildsCount ++;
			}
		}
	};
	@Override
	public synchronized List<GenericTag> getChilds(String lang,String ancestorTag) {
		List<GenericTag> value = null;

		try {
				currentAncestor = ancestorTag;
				currentChildsLang = lang;
				childParser.parse(genericFile, childsHandler);
				

				if (exitListChilds != null && !exitListChilds.isEmpty()){
					value =  exitListChilds;
				}else if (externalFile != null){
					childParser.parse(externalFile, childsHandler);
					if (exitListChilds != null && !exitListChilds.isEmpty()){
						value =  exitListChilds;
					}
					
				} else{
						value =  null;
				}
		} catch (SAXException | IOException e){
			logger.error(e.getMessage()+"childs Elements are"+langFoundChildsCount, e);
		} catch (Exception e){
			logger.error(e.getMessage()+"childs Elements are"+langFoundChildsCount, e);
		}
		
		return value;
	}
	@Override
	public boolean reloadLang() {
		boolean flag = false;
		try	{
			afterPropertiesSet();
			flag = true;
			logger.info("reloaded Lang");	
		} catch ( Exception e){
			logger.error("Failed to Reload Lang",e);
			flag = false;
		}
		return flag;
	}
	
	@Override
	public List<String> getAllLangs() {
		List<String> felang = new ArrayList<String>();
		Iterator<Lang> l = allLang.iterator();
		while (l.hasNext()){
			Lang lc = l.next(); 
			if (lc.getType() != null && (lc.getType().equals("it") || lc.getType().equals("it_IT"))){
				felang.add(0, lc.getType());
			} else {
				felang.add(lc.getType());
			}
		}
		return felang;
	}
	
	@Override
	public List<Lang> getAllLangsObj() {
		List<Lang> felang = new ArrayList<Lang>();
		Iterator<Lang> l = allLang.iterator();
		while (l.hasNext()){
			Lang lc = l.next(); 
			if (lc.getType() != null && (lc.getType().equals("it") || lc.getType().equals("it_IT"))){
				felang.add(0, lc);
			} else {
				felang.add(lc);
			}
		}
		return felang;
	}
	
}
