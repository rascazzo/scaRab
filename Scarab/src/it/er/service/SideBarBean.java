package it.er.service;

import it.er.basic.Basic;
import it.er.dao.IParamDAO;
import it.er.dao.Param;
import it.er.dao.Site;
import it.er.dinamic.DinamicLink;
import it.er.dinamic.MetaRelation;
import it.er.generic.GenericTag;
import it.er.lang.LanguageAccess;
import it.er.manage.BaseManage;
import it.er.object.Attribute;
import it.er.object.MetaXmlNode;
import it.er.object.SideBar;
import it.er.tag.SiteAccess;
import it.er.tag.Tag;
import it.er.tag.XTag;
import it.er.util.CustomException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SideBarBean extends Basic implements SideBarX,SidebarControl,InitializingBean,MetaRelation,DinamicLink{
	private JdbcTemplate jdbcTemplate = null;
	@Autowired
	private IParamDAO param;
	private File sidebarxml;
	private String sidebarxmlpath;
	private Map<Site,SideBar> sidebarMap;
	private Map<String,Map<String,String>> sideBarMapValue;
	private Map<String,Integer> sideBarMapOrder;
	private Map<String,Map<String,String>> sideBarMapOtherAttr;
	private Map<Site,Map<String,Map<String,Map<String,String>>>> sideBarMapValueSite;
	private Map<Site,Map<String,Integer>> sideBarMapOrderSite;
	private Map<Site,Map<String,Map<String,String>>> sideBarMapOtherAttrSite;
	private String friendsxmlpath;
	private File friendsxml;
	private static final String sidebarapp = "sd";
	private XTag xtag;
	
	public static String getSidebarapp() {
		return sidebarapp;
	}

	private LanguageAccess genericScarabLang;
	
	
	public LanguageAccess getGenericScarabLang() {
		return genericScarabLang;
	}
	
	@Autowired
	public void setGenericScarabLang(LanguageAccess genericScarabLang) {
		this.genericScarabLang = genericScarabLang;
	}

	private SiteAccess siteManagement;
	
	public SiteAccess getSiteManagement() {
		return siteManagement;
	}
	@Autowired
	public void setSiteManagement(SiteAccess siteManagement) {
		this.siteManagement = siteManagement;
	}

	private UserPreferences userpreferences;
	
	
	public UserPreferences getUserpreferences() {
		return userpreferences;
	}
	
	@Autowired
	public void setUserpreferences(UserPreferences userpreferences) {
		this.userpreferences = userpreferences;
	}

	private static Logger log = LogManager.getLogger(SideBarBean.class);
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}
	
	public void setParam(IParamDAO param) {
		this.param = param;
	}


	public void setSidebarxmlpath(String sidebarxmlpath) {
		this.sidebarxmlpath = sidebarxmlpath;
	}
	
	
	public void setFriendsxmlpath(String friendsxmlpath) {
		this.friendsxmlpath = friendsxmlpath;
	}

	public void setXtag(XTag xtag) {
		this.xtag = xtag;
	}

	public void close(){
		log.info("Destroy SideBarBean bean");
		this.sidebarxml = null;
		this.param = null;
		this.sidebarxmlpath = null;
		this.sidebarMap = null;
		this.jdbcTemplate = null;
		this.sideBarMapValue = null;
		this.sideBarMapOrder = null;
		this.sideBarMapOtherAttr = null;
		this.sideBarMapValueSite = null;
		this.sideBarMapOrderSite = null;
		this.sideBarMapOtherAttrSite = null;
		this.friendsxml = null;
		this.friendsxmlpath = null;
		
	}
	
	public void start(){
		log.info("Init SideBarBean bean");
	}
	@Override
	public SideBar getSidebar() {
		return sidebarMap.size()>0?sidebarMap.get(sidebarMap.keySet().iterator().next()):null;
	}
	public void setSidebar(Map<Site, SideBar> sidebarMap) {
		this.sidebarMap = sidebarMap;
	}
	@Override
	public Map<String, Map<String, String>> getSideBarMapValue() {
		return this.sideBarMapValue;
	}

	public void setSideBarMapValue(Map<String, Map<String, String>> sideBarMapValue) {
		this.sideBarMapValue = sideBarMapValue;
	}
	@Override
	public Map<String, Integer> getSideBarMapOrder() {
		return this.sideBarMapOrder;
	}

	public void setSideBarMapOrder(Map<String, Integer> sideBarMapOrder) {
		this.sideBarMapOrder = sideBarMapOrder;
	}
	@Override
	public Map<String, Map<String, String>> getSideBarMapOtherAttr() {
		return this.sideBarMapOtherAttr;
	}

	public void setSideBarMapOtherAttr(
			Map<String, Map<String, String>> sideBarMapOtherAttr) {
		this.sideBarMapOtherAttr = sideBarMapOtherAttr;
	}
	
	

	@Override
	public Map<Site, Map<String, Map<String, Map<String,String>>>> getSideBarMapValueSite() {
		return sideBarMapValueSite;
	}

	public void setSideBarMapValueSite(
			Map<Site, Map<String, Map<String,  Map<String,String>>>> sideBarMapValueSite) {
		this.sideBarMapValueSite = sideBarMapValueSite;
	}

	@Override
	public Map<Site, Map<String, Integer>> getSideBarMapOrderSite() {
		return sideBarMapOrderSite;
	}

	public void setSideBarMapOrderSite(
			Map<Site, Map<String, Integer>> sideBarMapOrderSite) {
		this.sideBarMapOrderSite = sideBarMapOrderSite;
	}

	@Override
	public Map<Site, Map<String, Map<String, String>>> getSideBarMapOtherAttrSite() {
		return sideBarMapOtherAttrSite;
	}

	public void setSideBarMapOtherAttrSite(
			Map<Site, Map<String, Map<String, String>>> sideBarMapOtherAttrSite) {
		this.sideBarMapOtherAttrSite = sideBarMapOtherAttrSite;
	}

	@Override
	public String resolvLink(String servername,String plus,String lang,String method){
		return "http://"+servername+plus+"/"+sidebarapp+lang+method;
	}
	
	@Override
	public String resolvLink(String servername,String plus,String method){
		return "http://"+servername+plus+"/"+sidebarapp+method;
	}
	
	@Override
	public String resolvLink(String plus,String method){
		return plus+"/"+sidebarapp+method;
	}
	
	public static String resolvStaticLink(String servername,String plus,String method){
		return "http://"+servername+plus+"/custom"+method;
	}
	
	public static String resolvStaticLinkLeft(String servername,String plus){
		return "http://".concat(servername).concat(plus).concat("/custom");
	}
	
	public static String resolvStaticLinkLeft(String plus){
		return plus.concat("/custom");
	}
	
	public static String resolvStaticLinkRight(String method){
		return "/".concat(method);
	}
	
	private Map<String,String> inspectOtherAttr(NamedNodeMap nodeAttr, String nameliv, boolean flagputinmap,List<Attribute> moreattr){
		Map<String,String> servTagAttr1 = null;
		if (flagputinmap)
			servTagAttr1 = new HashMap<String, String>();
		int c = 0;
		
		
		while (c<nodeAttr.getLength()){
			if (!nodeAttr.item(c).isEqualNode(nodeAttr.getNamedItem("id"))){
				Attribute attr1 = new Attribute();
				String name = new String(nodeAttr.item(c).getNodeName());
				String value = new String(nodeAttr.item(c).getNodeValue());
				if (flagputinmap)
					servTagAttr1.put(name, value);
				attr1.setName(name);
				attr1.setValue(value);
				moreattr.add(attr1);
			}
			c++;								
		}
		return servTagAttr1;
	}
	
	@Override
	public List<Attribute> inspectOtherAttribute(NamedNodeMap nodeAttr, String nameliv, boolean flagputinmap){
		List<Attribute> moreattr = new LinkedList<Attribute>();
		Map<String,String> servTagAttr1 = this.inspectOtherAttr(nodeAttr, nameliv, flagputinmap,moreattr);
		if (flagputinmap)
			sideBarMapOtherAttr.put(nameliv, servTagAttr1);
		return moreattr;
	}
	
	@Override
	public List<Attribute> inspectOtherAttributeDB(Site s,List<Attribute> attr, String nameliv, boolean flagputinmap){
		Map<String,String> servTagAttr1 = null;
		if (flagputinmap)
			servTagAttr1 = new HashMap<String, String>();
		int c = 0;
		while (c<attr.size()){
			if (!attr.get(c).getName().equals("id")){
				Attribute attr1 = attr.get(c);
				if (flagputinmap)
					servTagAttr1.put(attr1.getName(), attr1.getValue());
			}
			c++;								
		}
		if (flagputinmap){
			Map<String,Map<String,String>> other = new HashMap<String, Map<String,String>>();
			other.put(nameliv, servTagAttr1);
			sideBarMapOtherAttrSite.put(s, other);
		}
			
		return attr;
	}
	
	@Override
	public Map<String,String> listToMap(List<String> l,String prelink){
		Map<String,String> m = new HashMap<String, String>();
		Iterator<String> i = l.iterator();
		while (i.hasNext()){
			String s = i.next();
			m.put(s, prelink+"/"+s);
		}
		return m;
	}
	
	@Override
	public Map<String,String> detectMap(String maptype,String prelink){
		return null;
	}
	/* con novita
	@Override
	public Map<String,String> detectMap(String maptype,String prelink){
		List<String> r = null;
		if (maptype.equals("anno") || maptype.equals("year")){
			try {
				r = novita.getMapByValue(NovitaDAO.MapNovitaValue.YEAR, 2); /* 2 */ /*
			} catch (Exception e){
				log.error("Error in getMap", e.getCause());
			}
			return listToMap(r, prelink);
		}
		return null;
	}
	
	*/
	
	@Override
	public void loadMap(String tagname, Object metanode) throws SQLException{
			
			if (metanode instanceof MetaXmlNode){
				List<String> langs = genericScarabLang.getAllLangs();
				MetaXmlNode liv = (MetaXmlNode) metanode;
				liv.setLangWithList(langs);
				if (sideBarMapOtherAttr.get(tagname).containsKey("mapped") &&
						sideBarMapOtherAttr.get(tagname).get("mapped").equals("true")){
					liv.mapOn();
					if (sideBarMapOtherAttr.get(tagname).containsKey("maptype"))
						liv.setMap(detectMap(sideBarMapOtherAttr.get(tagname).get("maptype"),
								resolvLink(param.getParam(Basic.getParamadminername()).getValue(),"/"+BaseManage.getSidebarcontent()+"/"+"lang"+"/"+tagname)));
					
				}
				else {
					if (sideBarMapOtherAttr.get(tagname).containsKey("type")){
						if (sideBarMapOtherAttr.get(tagname).get("type").equals("staticlink")){
							liv.setLefthref(SideBarBean.resolvStaticLinkLeft((param.getParam(Basic.getParamadminername()).getValue()+"/"+BaseManage.getSidebarcontent())));
							liv.setRighthref(SideBarBean.resolvStaticLinkRight(tagname));
						}
					} else{
						liv.setLefthref(this.resolvLeftLink(param.getParam(Basic.getParamadminername()).getValue()+"/"+BaseManage.getSidebarcontent()));
						liv.setRighthref(this.resolvRightLink(tagname));
					}
					liv.mapOff();
				}
				
			}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void loadMapDB(Site s,String tagname, Object metanode) throws Exception{
			
			if (metanode instanceof MetaXmlNode){
				List<String> langs = genericScarabLang.getAllLangs();
				MetaXmlNode liv = (MetaXmlNode) metanode;
				liv.setLangWithList(langs);
				Map<String,Map<String,String>> oatt = new HashMap<String, Map<String,String>>();
				oatt = (Map<String,Map<String,String>>) siteManagement.containsAndGetObject(sideBarMapOtherAttrSite, s);
				if (oatt != null && oatt.get(tagname).containsKey("mapped") &&
						oatt.get(tagname).get("mapped").equals("true")){
					liv.mapOn();
					if (oatt.get(tagname).containsKey("maptype"))
						liv.setMap(detectMap(oatt.get(tagname).get("maptype"),
								resolvLink(param.getParam(Basic.getParamadminername()).getValue(),"/"+"lang"+"/"+tagname)));
					
				}
				else {
					if (oatt.get(tagname).containsKey("type")){
						if (oatt.get(tagname).get("type").equals("staticlink")){
							liv.setLefthref(SideBarBean.resolvStaticLinkLeft((param.getParam(Basic.getParamadminername()).getValue()+"/"+BaseManage.getSidebarcontent())));
							liv.setRighthref(SideBarBean.resolvStaticLinkRight(tagname));
						}
					} else{
						liv.setLefthref(this.resolvLeftLink(param.getParam(Basic.getParamadminername()).getValue()+"/"+BaseManage.getSidebarcontent()));
						liv.setRighthref(this.resolvRightLink(tagname));
					}
					liv.mapOff();
				}
				
			}
	}
	
		
	@Override
	public Object fetchMetaRelationXML(NodeList nodelist, Object first) throws Exception{
		SideBar currentSidebar = new SideBar();
		
		if (first instanceof MetaXmlNode){
			MetaXmlNode firstLiv = (MetaXmlNode) first;
			MetaXmlNode secondLiv = null;
			List<MetaXmlNode> amici = null;
			boolean flagamici = false;
			for (int i=0;i<nodelist.getLength();i++){
				Map<String,String> servTag2eValuelivello = new HashMap<String, String>();
				
				Node node = nodelist.item(i);
				if (node.getNodeType() != Node.COMMENT_NODE && node.getNodeType() == Node.ELEMENT_NODE){
					NamedNodeMap nodeAttr = null;
					firstLiv = new MetaXmlNode();
					String namePrimolivello = new String();
					namePrimolivello = node.getNodeName();
					if (node.hasAttributes()){
						nodeAttr = node.getAttributes();
						Integer idPrimolivello = new Integer(nodeAttr.getNamedItem("id").getNodeValue());
						sideBarMapOrder.put(namePrimolivello, idPrimolivello);
						firstLiv.setId(idPrimolivello);
						firstLiv.setAttributes(inspectOtherAttribute(nodeAttr, namePrimolivello,true));
					}
				
					firstLiv.setNodename(namePrimolivello);
					if (node.hasChildNodes() && node.getChildNodes().getLength()>1){
					
					/*secondo livello*/
					NodeList nodelist2 = node.getChildNodes();
							for (int j=0;j<nodelist2.getLength();j++){
								Node node2 = nodelist2.item(j);
								if (node2.hasChildNodes()){
									secondLiv = new MetaXmlNode();
									String nameSecondolivello = new String();
									nameSecondolivello = node2.getNodeName();
									secondLiv.setNodename(nameSecondolivello);
									if (node2.hasAttributes()){
										NamedNodeMap nodeAttr2 = node2.getAttributes();
										Integer idSecondolivello = new Integer(nodeAttr2.getNamedItem("id").getNodeValue());
										secondLiv.setId(idSecondolivello);
										sideBarMapOrder.put(nameSecondolivello, idSecondolivello);
										secondLiv.setAttributes(inspectOtherAttribute(nodeAttr2, nameSecondolivello,true));
									}
									String valueSecondolivello = new String();
									valueSecondolivello = node2.getTextContent();
									List<GenericTag> textlang = new ArrayList<GenericTag>();
									GenericTag gS = new GenericTag();
									gS.setName(valueSecondolivello);
									textlang.add(gS);
									secondLiv.setText(textlang);
									
									loadMap(nameSecondolivello, secondLiv);
									servTag2eValuelivello.put(nameSecondolivello, valueSecondolivello);
									sideBarMapValue.put(namePrimolivello, servTag2eValuelivello);
									firstLiv.getSecond().add(nameSecondolivello);
									secondLiv.noChilds();
									List<GenericTag> textlangname = new ArrayList<GenericTag>();
									GenericTag g = new GenericTag();
									g.setName(namePrimolivello);
									textlangname.add(g);
									firstLiv.setText(textlangname);
									firstLiv.hasChilds();
									currentSidebar.getSecond().add(secondLiv);
									}
								 
							}
					} else {
						/* nodo di primo livello con solo text node figlio */
						String valuePrimolivello = new String();
						valuePrimolivello = node.getTextContent();
						List<GenericTag> textlang = new ArrayList<GenericTag>();
						GenericTag gS = new GenericTag();
						gS.setName(valuePrimolivello);
						textlang.add(gS);
						firstLiv.setText(textlang);
						firstLiv.noChilds();
						loadMap(namePrimolivello, firstLiv);
						servTag2eValuelivello.put(getNo2livelsidemap(), valuePrimolivello);
						sideBarMapValue.put(namePrimolivello, servTag2eValuelivello);
						
						
						if (!flagamici && sideBarMapValue.containsKey("amici")){
							//amici = fetchFriends();
							currentSidebar.setFriends(amici);
							flagamici = true;
						}					
					}
					currentSidebar.getFirst().add(firstLiv);
				} 
				
			} 
		}else 
			currentSidebar = null;
				
		
		return currentSidebar;
	}
	
	
	
	private List<MetaXmlNode> fetchFriends() throws Exception{
		List<MetaXmlNode> friends = new LinkedList<MetaXmlNode>();
		this.friendsxml = new File(param.getParam(getWCP()).getValue()+this.friendsxmlpath);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder build = factory.newDocumentBuilder();
			Document doc = build.parse(friendsxml);
			NodeList firendselement = doc.getElementsByTagName("amici");
			if (firendselement!=null){
								
				NodeList nodelist = firendselement.item(0).getChildNodes();
				MetaXmlNode firstLiv = new MetaXmlNode();
				for (int i=0;i<nodelist.getLength();i++){
					Node node = nodelist.item(i);
					if (node.getNodeType() != Node.COMMENT_NODE && node.getNodeType() == Node.ELEMENT_NODE){
						NamedNodeMap nodeAttr = null;
						firstLiv = new MetaXmlNode();
						String namePrimolivello = new String();
						namePrimolivello = node.getNodeName();
						if (node.hasAttributes()){
							nodeAttr = node.getAttributes();
							Integer idPrimolivello = new Integer(nodeAttr.getNamedItem("id").getNodeValue());
							firstLiv.setId(idPrimolivello);
							firstLiv.setAttributes(inspectOtherAttribute(nodeAttr, namePrimolivello,false));
						}
					
						firstLiv.setNodename(namePrimolivello);
						String valuePrimolivello = new String();
						valuePrimolivello = node.getTextContent();
						List<GenericTag> textlang = new ArrayList<GenericTag>();
						GenericTag gS = new GenericTag();
						gS.setName(valuePrimolivello);
						textlang.add(gS);
						firstLiv.setText(textlang);
						firstLiv.noChilds();
						friends.add(firstLiv);
					}
				}
				log.info("Set new Friends");
			} 
			else {
				friends = null;
				new CustomException("xml file not well formed in root");
			}
		} catch (ParserConfigurationException e) {
			friends = null;
			e.printStackTrace();
		} catch (SAXException e) {
			friends = null;
			e.printStackTrace();
		} catch (IOException e) {
			friends = null;
			e.printStackTrace();
		} finally {
			
			this.friendsxml = null;
			this.friendsxmlpath = null;
			
		}
		
		return friends;
	}
	
	@Override
	public void XMLSetSidebar() throws Exception{
		this.sideBarMapValue = null;
		this.sideBarMapOrder = null;
		this.sideBarMapOtherAttr = null;
		//this.lastDateModSidebarxml = null;
		try {
			List<Param> p = param.getParamList(Basic.getWCP());
			String sdFile = p.get(0).getValue()+this.sidebarxmlpath;
			if (log.isDebugEnabled())
				log.debug(sdFile+"#filePathSidebar");
			this.sidebarxml = new File(sdFile);
			
			
			/* Set sidebar */
			
			/*
			Map<String,Map<String,String>> mapValue;  		tagnameIliv , (tagnameIIliv,TagValue)
			Map<String,Integer> mapOrder;					tagname 	, id
			Map<String,Map<String,String>> mapOtherAttr;	tagname 	, (attrName, attrValue)
			*/
			
			sideBarMapOrder = new HashMap<String, Integer>();
			sideBarMapOtherAttr = new HashMap<String, Map<String,String>>();
			sideBarMapValue = new HashMap<String, Map<String,String>>();
			
			Object ob = null;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
			DocumentBuilder build = factory.newDocumentBuilder();
			Document doc = build.parse(this.sidebarxml);
			NodeList sidebarelement = doc.getElementsByTagName("sidebar");
			if (sidebarelement!=null){
								
				NodeList nodelist = sidebarelement.item(0).getChildNodes();
				MetaXmlNode firstLiv = new MetaXmlNode();
				ob = fetchMetaRelationXML(nodelist,firstLiv);
				if (ob instanceof SideBar){
					Map<Site,SideBar> sd = new HashMap<Site, SideBar>();
					Site s = site.readSite(param.getParam("domain").getValue());
					sd.put(s, (SideBar)ob);
					setSidebar(sd);
					log.info("Set new XML SideBar");
				}
			} else {
				setSidebar(null);
				new CustomException("xml file not well formed in root");
			}
		} catch (ParserConfigurationException e) {
			setSidebar(null);
			log.error(e);
		} catch (SAXException e) {
			setSidebar(null);
			log.error(e);
		} catch (SQLException e) {
			setSidebar(null);
			log.error(e);
		} catch (FileNotFoundException e) {
			setSidebar(null);
			log.error(e);
		} catch (IOException e) {
			setSidebar(null);
			log.error(e);
		} catch (Exception e) {
			setSidebar(null);
			log.error(e);
		} 
	}
	
	@Override
	public boolean reloadAllSidebar() throws CustomException{
		boolean r = false;
		try {
			this.afterPropertiesSet();
			r = true;
		} catch (Exception e){
			log.error(e.getMessage());
			throw new CustomException(e);
		}
		return r;
	}
	
	@Override
	public void DBSetSidebar() throws Exception{
		this.sideBarMapValueSite = null;
		this.sideBarMapOrderSite = null;
		this.sideBarMapOtherAttrSite = null;
		
		
		
		/* Set Domain */
		//domain = param.getParam("Domain").getValue();
		/* Set Domain */
		List<Site> st = siteManagement.getAllSite();
		Object ob = null;
		
		try {
			Iterator<Site> i = st.iterator();
			Map<Site,SideBar> sdMap = new HashMap<Site, SideBar>();
			sideBarMapOrderSite = new HashMap<Site,Map<String, Integer>>();
			sideBarMapOtherAttrSite = new HashMap<Site,Map<String, Map<String,String>>>();
			sideBarMapValueSite = new HashMap<Site,Map<String, Map<String,Map<String,String>>>>();
			Map<Site,List<Tag>> tagsfirstmap = xtag.getAllRootTag();
			log.info("start with cicle sites...");
			while (i.hasNext()){
				
				Site s = i.next();
				
				@SuppressWarnings("unchecked")
				List<Tag> tagsFirst = (List<Tag>) siteManagement.containsAndGetObject(tagsfirstmap,s);
				if (tagsFirst != null)
					ob = fetchMetaRelationDB(tagsFirst,s);
				if (ob != null && ob instanceof SideBar){
					SideBar sd = (SideBar)ob;
					sdMap.put(s, sd);
					log.info("Set new DB SideBar");
				}
				
			}
			setSidebar(sdMap);
			log.info("Set SideBar Map");
		} catch (SQLException e) {
			log.error(e);
		} catch (Exception e){
			log.error(e);
		} 
	}
	
	@Override
	public Object fetchMetaRelationDB(List<Tag> tagsfirst, Site s) throws Exception{
		SideBar currentSidebar = new SideBar();
		
			if (tagsfirst != null){
				try {
					List<String> langs = genericScarabLang.getAllLangs();
					Iterator<Tag> onFirst = tagsfirst.iterator();
					Map<String,Integer> idsrgtmap = new HashMap<String, Integer>();
					Map<String,Map<String,Map<String,String>>> valsrgtmap = new HashMap<String, Map<String,Map<String,String>>>();
					
					while (onFirst.hasNext()){
						MetaXmlNode firstLiv = new MetaXmlNode();
						MetaXmlNode secondLiv = null;
						List<MetaXmlNode> amici = null;
						boolean flagamici = false;
						Map<String,Map<String,String>> servTag2eValuelivello = new HashMap<String, Map<String,String>>();
								Tag tag = onFirst.next();
								firstLiv.setNodename(tag.getTagname());
								Map<Site,List<Attribute>> attrOth = xtag.getAttributes(tag.getTagname());
								@SuppressWarnings("unchecked")
								List<Attribute> atother = (List<Attribute>) siteManagement.containsAndGetObject(attrOth,s);
								if (atother != null){
									firstLiv.setAttributes(inspectOtherAttributeDB(s,atother, tag.getTagname(),true));
								}
								Integer idS = null;
								idS = xtag.getOrder(tag.getTagname(),s);
								if (idS!=null){
									firstLiv.setId(idS);
									idsrgtmap.put(tag.getTagname(), idS);
								}
								Boolean hasson = xtag.hasSons(tag.getTagname(),s);
								boolean sons = false;
								if (hasson.booleanValue()){
									sons = hasson.booleanValue();
								}
									
								if (sons){
									Map<Site,List<Tag>> mapTagsSon = xtag.getTags(tag.getTagname(),true);
									@SuppressWarnings("unchecked")
									List<Tag> tagsSecond = (List<Tag>) siteManagement.containsAndGetObject(mapTagsSon,s);
									if (tagsSecond != null){
										Iterator<Tag> onSecond = tagsSecond.iterator();
										while(onSecond.hasNext()){
											Tag tag2 = onSecond.next();
											
											secondLiv = new MetaXmlNode();
											secondLiv.setNodename(tag2.getTagname());
											Integer id2 = null;
											id2 =  xtag.getOrder(tag2.getTagname(),s);
											Map<Site, List<Attribute>> attrOthS = xtag.getAttributes(tag2.getTagname());
											@SuppressWarnings("unchecked")
											List<Attribute> attrOthStmp = (List<Attribute>) siteManagement.containsAndGetObject(attrOthS,s);
											if (attrOthStmp != null){
												secondLiv.setAttributes(inspectOtherAttributeDB(s,attrOthStmp, tag2.getTagname(),true));
											}
											if (id2!=null){
												secondLiv.setId(id2);
												idsrgtmap.put(tag2.getTagname(), id2);
												Map<String,String> valueSecondolivello = new HashMap<String, String>();
												List<GenericTag> textlang = new ArrayList<GenericTag>();
												List<GenericTag> textlangFirst = new ArrayList<GenericTag>();
												
												for (String lang : langs) {
													String valueinLang = xtag.getTagnameValueByLang(tag2.getTagname(), lang, s);
													String valueinLangFirst = xtag.getTagnameValueByLang(tag.getTagname(), lang, s);
													valueSecondolivello.put(lang, valueinLang);
													GenericTag gS = new GenericTag();
													gS.setName(lang);
													gS.setValue(valueinLang);
													textlang.add(gS);
													GenericTag g = new GenericTag();
													g.setName(lang);
													g.setValue(valueinLangFirst);
													textlangFirst.add(g);
												}
												secondLiv.setText(textlang);
												loadMapDB(s,tag2.getTagname(), secondLiv);
												servTag2eValuelivello.put(tag2.getTagname(), valueSecondolivello);
												valsrgtmap.put(tag.getTagname(), servTag2eValuelivello);
												
												firstLiv.getSecond().add(tag2.getTagname());
												secondLiv.noChilds();
												
												firstLiv.setText(textlangFirst);
												firstLiv.hasChilds();
												currentSidebar.getSecond().add(secondLiv);
												
											}
										}
									}
									sideBarMapOrderSite.put(s, idsrgtmap);
									sideBarMapValueSite.put(s, valsrgtmap);
								} else {
									/* nodo di primo livello con solo text node figlio */
									Map<String,String> valuePrimolivello = new HashMap<String, String>();
									List<GenericTag> textlangFirst = new ArrayList<GenericTag>();
									
									for (String lang : langs) {
										String valueinLangFirst = xtag.getTagnameValueByLang(tag.getTagname(), lang, s);
										valuePrimolivello.put(lang, valueinLangFirst);
										GenericTag g = new GenericTag();
										g.setName(lang);
										g.setValue(valueinLangFirst);
										textlangFirst.add(g);
									}
									firstLiv.setText(textlangFirst);
									firstLiv.noChilds();
									loadMapDB(s,tag.getTagname(), firstLiv);
									servTag2eValuelivello.put(getNo2livelsidemap(), valuePrimolivello);
									valsrgtmap.put(tag.getTagname(), servTag2eValuelivello);
									if (!flagamici && valsrgtmap.containsKey("amici")){
										amici = fetchFriends();
										currentSidebar.setFriends(amici);
										flagamici = true;
									}	
									sideBarMapValueSite.put(s,valsrgtmap);
								}
								currentSidebar.getFirst().add(firstLiv);
							} 
				} catch (EmptyResultDataAccessException em){
					log.error("Error by Empty",em);
					currentSidebar = null;
				}
			} else {
				new CustomException(new Throwable("There is not first levels sidebar"), "Check init or create one");
			}	
		return currentSidebar;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (param.getParam("sidebarType").getValue().equals("XML"))
			XMLSetSidebar();
		else if (param.getParam("sidebarType").getValue().equals("DB"))
			DBSetSidebar();
	}
	@Override
	public Map<String, Map<String, String>> getSideBarMapValue(Site s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, Integer> getSideBarMapOrder(Site s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, Map<String, String>> getSideBarMapOtherAttr(Site s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SideBar getSidebar(Site s) throws Exception {
		return (SideBar) siteManagement.containsAndGetObject(sidebarMap, s);
	}

	@Override
	public String resolvLeftLink(String servername, String plus) {
		return "http://".concat(servername).concat("/").concat(plus).concat("/");
	}

	@Override
	public String resolvRightLink(String realname) {
		return "/".concat(realname);
	}

	@Override
	public String resolvLeftLink(String plus) {
		return "/".concat(plus).concat("/");
	}

	@Override
	public String resolveHrefByTagName(Site s, String contentType,String nodename, String lang,
			String id, String query) {
		String content = BaseManage.getSidebarcontent();
		if (contentType != null && contentType.equals(BaseManage.getContent())){
			content = BaseManage.getContent();
		} else if (contentType != null && !contentType.isEmpty() && !contentType.equals(BaseManage.getSidebarcontent())){
			content = contentType;
		}
		String r;
		try {
			r = "http://".concat(s.getDomain()).concat("/").concat(param.getParam("frontEndEr").getValue()).concat("/")
					.concat(content).concat("/").concat(nodename).concat(id != null?"/".concat(id):"").concat(query!=null?"?".concat(query):"");
		} catch (SQLException e) {
			r = "#";
		}
		return r;
	}
	
	

	
}
