package org.coldie.wurmunlimited.mods.paintings;

import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Properties;


public class paintings implements WurmServerMod, ItemTemplatesCreatedListener, Configurable, ServerStartedListener, Versioned {
	// public static final Logger logger = Logger.getLogger(paintings.class.getName());
	public static final String version = "ty1.0";
	public static int portraitnumber = 8;
	public static int landscapenumber = 9;

	public static int targetid = 0;

	@Override
	public void configure(Properties properties) {
		targetid = Integer.parseInt(properties.getProperty("targetid", Integer.toString(targetid)));
		portraitnumber = Integer.parseInt(properties.getProperty("portraitnumber", Integer.toString(portraitnumber)));
		landscapenumber = Integer.parseInt(properties.getProperty("landscapenumber", Integer.toString(landscapenumber)));
	}

	@Override
	public void onItemTemplatesCreated() {
		new paintingitems();
	}

	@Override
	public void onServerStarted() {
		ModActions.registerAction(new changeportraitaction());
		ModActions.registerAction(new changelandscapeaction());
	}

	@Override
	public String getVersion() {
		return version;
	}
}