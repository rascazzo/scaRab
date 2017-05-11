package it.er.presentation.admin;

import java.util.List;

import it.er.basic.Basic;
import it.er.object.Logged;
import java.io.File;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class BaseAdminContent {

	protected static final String appBasePath = "app";
	
	protected static final String userfePath = "userfe";
	
	protected static final String sidebarfePath = "sidebarfe";
	
	protected static final String restPath = "rest";
	
	protected Logged logged;
	
	protected List<String> lang;
	
	public BaseAdminContent(){}
	
	@XmlElement(name = "baseapppath")
	public String getAppBasePath() {
		return BaseAdminContent.appBasePath.concat("/").concat("admin");
	}
	
	@XmlElement(name = "userapppath")
	public String getUserfepath() {
		return  BaseAdminContent.appBasePath.concat("/").concat(BaseAdminContent.userfePath);
	}

	@XmlElement(name = "sidebarapppath")
	public String getSidebarfepath() {
		return BaseAdminContent.appBasePath.concat("/").concat(BaseAdminContent.sidebarfePath);
	}

	@XmlElement
	public Logged getLogged() {
		return logged;
	}

	public void setLogged(Logged logged) {
		this.logged = logged;
	}

	@XmlElementWrapper(name="langs")
	public List<String> getLang() {
		return lang;
	}

	public void setLang(List<String> lang) {
		this.lang = lang;
	}

	@XmlElement(name = "restfullpath")
	public String getRestpath() {
		return BaseAdminContent.restPath;
	}
	
public static String getXsltadminpath(String file){
                return Basic.XSLTAdminPath.replaceAll(Basic.scarabSeparatorFile, File.separatorChar+"")+file;
        }

        public static String getXRabadminPath(String file){
                return Basic.XRABAdminPath.replaceAll(Basic.scarabSeparatorFile, File.separatorChar+"")+file;
        }
 public static String getErrorpage() {
                return Basic.errorPage;
        }


        public static String getNotfoundpage() {
                return Basic.notFoundPage;
        }
	
}
