package org.coldie.wurmunlimited.mods.antimycelium;

import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.Versioned;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.logging.Logger;

public class antimycelium implements WurmServerMod, ServerStartedListener, Versioned {
	public static final Logger logger = Logger.getLogger(antimyceliumaction.class.getName());

	public static final String version = "ty1.0";

	@Override
	public void onServerStarted() {
		ModActions.registerAction(new antimyceliumaction());
	}

	@Override
	public String getVersion(){
		return version;
	}
	
}