package it.er.basic;


import it.er.dao.ISiteDAO;
import it.er.dao.SiteDAO;
import it.er.util.SingletonLookup;

import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public abstract class Basic{
	
	protected ISiteDAO site;
	
	@Autowired
	public void setSite(ISiteDAO site){
		this.site = site;
	}
	
	
	public ISiteDAO getSite() {
		return this.site;
	}

	public Basic(){}
	
	private static final String scarabSeparatorFile = "#f";

	private static final String paramAdminAppName = "adminApp";
	
	private static final String paramAdminErName = "frontEndEr";
	
	private static final String applicationName = "scarab";
	
	private static final String contentNameSp = "/rest/contentxmlsource/";
	
	private static final String XSLTPath = Basic.scarabSeparatorFile+"Xslt"+Basic.scarabSeparatorFile;
	
	private static final String XSLTAdminPath = Basic.scarabSeparatorFile+"admin"+Basic.scarabSeparatorFile+"Xslt"+Basic.scarabSeparatorFile;
	
	private static final String XRABAdminPath = Basic.scarabSeparatorFile+"admin"+Basic.scarabSeparatorFile+"XProc"+Basic.scarabSeparatorFile;
	
	private static final String webContentPath = "WebContentPath";
	
	private static final String baseUpload = Basic.scarabSeparatorFile+"upload"+Basic.scarabSeparatorFile;
	
	private static final String limitNovHom = "LimitTextHome";
	
	private static final String logoImagePath = "LogoImagePath";
	
	private static final String dbContext = "dbContext.xml";
	
	private static final String strategyBean = "strategyBean.xml";
	
	private static final String jdbcTemplate = "jdbcTemplate.xml";
	
	private static final String layerServiceBean = "layerservice.xml";
	
	private static final String errorPage = "/er/contentstk/error/error";
	
	private static final String notFoundPage = "/er/contentstk/error/notfound";

	public static String getLayerServiceBeanFinal() {
		return layerServiceBean;
	}
	
	public static String getJdbcTemplateFinal() {
		return jdbcTemplate;
	}
	
	public static String getDbcontext() {
		return dbContext;
	}

	public static String getStrategybean() {
		return strategyBean;
	}

	public static String getApplicationname() {
		return applicationName;
	}
	
	public static String getWCP() {
		return webContentPath;
	}
	
	public static String getBUpload() {
		return baseUpload.replaceAll(Basic.scarabSeparatorFile, File.separatorChar+"");
	}
	
	public static String getErrorpage() {
		return errorPage;
	}


	public static String getNotfoundpage() {
		return notFoundPage;
	}
	
	


	public static String getParamadminappname() {
		return paramAdminAppName;
	}


	public static String getParamadminername() {
		return paramAdminErName;
	}


	public String paramFromMap(Map<String,String> xmlParam){
		if (!(xmlParam==null)) {
			StringBuffer xmlQuery = new StringBuffer();
			xmlQuery.append("?");
			Set<String> xmlSet = xmlParam.keySet();
			Iterator<String> i = xmlSet.iterator();
			boolean init = true;
			while(i.hasNext()){
				if (!init)
					xmlQuery.append("&");
				String name = i.next();
				xmlQuery.append(name+"=");
				xmlQuery.append(xmlParam.get(name));
				init = false;
			}
			return xmlQuery.toString();
		}
		return null;
	}
	
	public static String getContentNameSp(String path){
		return contentNameSp+path;
	}
	
	public static String getXSLTPath(String file){
		return XSLTPath.replaceAll(Basic.scarabSeparatorFile, File.separatorChar+"")+file;
	}
	
	
	public String getXSLTPath(String file,String domain,ServletContext sc){
		String r = null;
		if (site == null)
			site = SingletonLookup.getSiteDAO(sc);
			try {
				String xsl = site.readSite(domain).getXsltpath();
				if (xsl != null && !xsl.isEmpty())
					r = xsl+file;
				r = getXSLTPath(file);
			} catch (SQLException e) {
				r = getXSLTPath(file);
			}
		return r;
	}
	
	public static String getLimitNovHom() {
		return limitNovHom;
	}
	
	public static String getLogoImagePath(){
		return logoImagePath;
	}
	
	public String appendLimitStart(String sql, int limit, int start){
		if (limit!=-1 && start!=-1)
			sql = sql.concat("limit "+limit+" offset "+start);
		else if (limit!=-1 && start==-1)
			sql = sql.concat("limit "+limit);
		return sql;
	}
	
	public String appendRealHostInWhere(String sql,boolean and,String termOne, String termTwo){
		if (and)
			sql = sql.concat(" AND "+termOne+"="+termTwo+" ");
		else
			sql = sql.concat(" WHERE "+termOne+"="+termTwo+" ");
		return sql;
	}
	
	public ApplicationContext getUnSpecificApplicationContext(String bean){
		return new ClassPathXmlApplicationContext("classpath:"+bean);
	}
	
	private static final String visitCookieName = "LSIDRESOR";
	
	protected static String getVisitCookieName(){
		return visitCookieName;
	}
	
	private static final String quadriImagePath = "QuadriImagePath";
	
	private static final String quadriThumbImagePath = "QuadriThumbImagePath";
	
	private static final String citaImagePath = "CitaImagePath";
	
	private static final String motiviImagePath = "MotiviImagePath";
	
	private static final String skizziImagePath = "SkizziImagePath";
	
	private static final String skizziThumbImagePath = "SkizziThumbImagePath";
	
	private static final String acasaImagePath = "AcasaImagePath";
	
	private static final String acasaThumbImagePath = "AcasaThumbImagePath";
	
	
	
	public static String getAcasaimagepath() {
		return acasaImagePath;
	}

	public static String getAcasathumbimagepath() {
		return acasaThumbImagePath;
	}

	public static String getSkizziimagepath() {
		return skizziImagePath;
	}

	public static String getSkizzithumbimagepath() {
		return skizziThumbImagePath;
	}

	public static String getQuadriimagepath() {
		return quadriImagePath;
	}

	public static String getQuadrithumbimagepath() {
		return quadriThumbImagePath;
	}

	public static String getCitaimagepath() {
		return citaImagePath;
	}

	public static String getMotiviimagepath() {
		return motiviImagePath;
	}
	
//	private static final String numberNovitaBeforeArk = "NumberNovitaBeforeArk";
//	
//
//	public static String getNumbernovitabeforeark() {
//		return numberNovitaBeforeArk;
//	}
	
	private static final String No2LivelSideMap = "NomeosDeNoS3c";

	public static String getNo2livelsidemap() {
		return No2LivelSideMap;
	}
	
	private static final String numberNovitaInPage = "NumberNovitaInPage";

	public static String getNumbernovitainpage() {
		return numberNovitaInPage;
	}
	
	private static final int numberSpaceThumbnail = 142;

	public static int getNumberspacethumbnail() {
		return numberSpaceThumbnail;
	}
	
	private static final String lastUpdateSidebar = "LastUpdateSidebar";

	public static String getLastupdatesidebar() {
		return lastUpdateSidebar;
	}

	public static String getXsltadminpath(String file){
		return XSLTAdminPath.replaceAll(Basic.scarabSeparatorFile, File.separatorChar+"")+file;
	}
	
	public static String getXRabadminPath(String file){
		return XRABAdminPath.replaceAll(Basic.scarabSeparatorFile, File.separatorChar+"")+file;
	}
	
}
