package org.coldie.wurmunlimited.mods.combiner;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.Versioned;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;


import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTemplateFactory;
import com.wurmonline.server.items.NoSuchTemplateException;

public class combiner implements WurmServerMod, ServerStartedListener, Configurable, Versioned {
	public static final Logger logger = Logger.getLogger(combiner.class.getName());

	public static final String version = "ty1.0";

	public static String[] combinelistid;

	@Override
	public void configure(Properties properties) {
		combinelistid = properties.getProperty("combinelistid").split(";");
	}

	@Override
	public void onServerStarted() {
		for (String s : combinelistid) {
			ItemTemplate Combined;
			try {
				Combined = ItemTemplateFactory.getInstance().getTemplate(Integer.parseInt(s));
				logger.log(Level.INFO, "ID: " + s + " Name:" + Combined.getName());
				ReflectionUtil.setPrivateField(Combined, ReflectionUtil.getField(Combined.getClass(), "combineCold"), true);
				ReflectionUtil.setPrivateField(Combined, ReflectionUtil.getField(Combined.getClass(), "combine"), true);
			} catch (IllegalArgumentException | IllegalAccessException | ClassCastException | NoSuchFieldException |
					 NoSuchTemplateException e) {
				logger.info(e.toString());
			}
		}
	}

	@Override
	public String getVersion() {
		return version;
	}
}