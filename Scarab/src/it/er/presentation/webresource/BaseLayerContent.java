package it.er.presentation.webresource;


import it.er.account.IAccount;
import it.er.basic.Basic;
import it.er.dao.IParamDAO;
import it.er.dao.Site;
import it.er.dinamic.ResolvPath;
import it.er.layerintercept.OverX;
import it.er.object.Layer;
import it.er.object.Logged;
import it.er.object.Single;
import it.er.service.SideBarX;
import it.er.transform.Ofelia;
import it.er.transform.TransformGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.annotation.XmlElement;


public abstract class BaseLayerContent extends Basic implements ResolvPath{
	
	@Override
	public String dinamicPath(String file, String nameParam, IParamDAO param) throws SQLException{
		return param.getParam(nameParam).getValue()+"/"+file;
	}
	
	
	
	
	
	protected void setCommonLayer(String tit, HttpServletRequest req,ServletContext cx, Layer layer,
			IParamDAO param,IAccount account,SideBarX sidebar) throws Exception{
		//layer.getControlpanel().setStati(over.hereVerify(req,cx));
		//layer.setCitazione(navmenu.getCitAttiva(cx));
		//layer.getCitazione().setImage(layer.getCitazione().dinamicPath(layer.getCitazione().getImage(), getCitaimagepath(), param));
		layer.getMetatag().setTitle(layer.getTitle()+" | "+layer.getNavigation());
		layer.getMetatag().setType("Development");
		//layer.getMetatag().setURL(layer.getNamespace());
		//layer.getMetatag().setSitename(host);
		Site s = site.readSite(req.getServerName());
		if (s != null)
			layer.setSidebar(sidebar.getSidebar(s));
	}
	
	/**
	 * tagname
	 * @throws Exception 
	 */
	protected void setCommonLayer(String host, String tit, HttpServletRequest req,ServletContext cx, Layer layer, OverX over,
			IParamDAO param,IAccount account,SideBarX sidebar, Object navmenu,String nameSpaceinPagetagname, String metaTitle) throws Exception{
		layer.getHeader().setDomain(host);
		layer.getMetatag().setTitle(layer.getTitle()+" | "+layer.getNavigation()+" . "+metaTitle);
		layer.getMetatag().setType("Development");
		layer.getMetatag().setSitename(tit);
		Site s = site.readSite(req.getServerName());
		if (s != null)
			layer.setSidebar(sidebar.getSidebar(s));
		
		
	}
	
	protected void setCommonSingle(String host, String tit,HttpServletRequest req,ServletContext cx, Single single, 
			IParamDAO param,IAccount account,String nameSpaceinPagetagname, String metaTitle){
		single.getHeader().setDomain(host);
		single.getMetatag().setTitle(single.getTitle()+" | "+single.getNavigation()+" . "+metaTitle);
		single.getMetatag().setType("Development");
		single.getMetatag().setURL(single.getNamespace());
		single.getMetatag().setSitename(tit);
		single.setNamespacetagname(nameSpaceinPagetagname);
	}
	
	
	
	protected Logged logged;

	@XmlElement
	public Logged getLogged() {
		return logged;
	}

	public void setLogged(Logged logged) {
		this.logged = logged;
	}
	
	protected StreamingOutput getWSLayerStream(final TransformGenerator tg,
			final Ofelia off, 
			final HttpServletRequest req,
			final ServletContext cx){
		return new StreamingOutput() {
			
			@Override
			public void write(OutputStream out) throws IOException,
						WebApplicationException {
					if (off!=null){
						tg.doXMLProcessing(req,getXSLTPath("layer.xslt",req.getServerName(),cx), null, out, off,
								Layer.class);
					} else {
						throw new WebApplicationException(404);
					}
				}
			};
	}
}
