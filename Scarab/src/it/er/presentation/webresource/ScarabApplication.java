package it.er.presentation.webresource;

import it.er.presentation.RestGraphContent;
import it.er.presentation.admin.AdminContent;
import it.er.presentation.admin.RestAdminContent;
import it.er.service.SidebarRestLayer;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class ScarabApplication extends Application{
	
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(WebContent.class);
        classes.add(WebContentOut.class);
		classes.add(AdminContent.class);
        classes.add(Sd.class);
        classes.add(SdOut.class);
		classes.add(User.class);
		
		classes.add(RestAdminContent.class);
		classes.add(SidebarRestLayer.class);
		//classes.add(RestGraphContent.class);
		return classes;
	}

}
