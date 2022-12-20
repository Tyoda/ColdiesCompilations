package org.coldie.wurmunlimited.mods.ceiling;

import java.util.Properties;
import java.util.logging.Logger;

import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

public class ceiling implements WurmServerMod, Configurable, ServerStartedListener, ItemTemplatesCreatedListener, Versioned {
	public static final Logger logger = Logger.getLogger(ceiling.class.getName());
	public static final String version = "ty1.0";
	public int itemtemplate1 = 8263;
	public String itemmodel1 = "model.structure.floor.wood.";

	private static ceiling instance;

	public ceiling(){
		instance = this;
	}

	@Override
	public void onServerStarted() {
		ModActions.registerAction(new raiseaction());
	}

	@Override
	public void configure(Properties properties) {
		itemtemplate1 = Integer.parseInt(properties.getProperty("itemtemplate1", Float.toString(itemtemplate1)));
		itemmodel1 = properties.getProperty("itemmodel1",itemmodel1);		
	}

	@Override
	public void onItemTemplatesCreated() {
		new ceilingitems();
	}

	@Override
	public String getVersion() {
		return version;
	}

	public static ceiling getInstance() {
		return instance;
	}
}