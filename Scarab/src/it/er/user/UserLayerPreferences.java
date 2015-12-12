package it.er.user;

import it.er.service.SideBarX;
import it.er.service.UserPreferences;

public interface UserLayerPreferences {

	public void loadLayer(Class<?> layerType,Object layer, UserPreferences u , SideBarX s);
}
