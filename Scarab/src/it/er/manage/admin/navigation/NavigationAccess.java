package it.er.manage.admin.navigation;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import it.er.presentation.admin.object.MenuElementMount;

public interface NavigationAccess {

	public  List<MenuElementMount> genericNavigationFetch(String lang, String hrefPrefix, boolean loginRequired) throws SAXException,IOException;
}
