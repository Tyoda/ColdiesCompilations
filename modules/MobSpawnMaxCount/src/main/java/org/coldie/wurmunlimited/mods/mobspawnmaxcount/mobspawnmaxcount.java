package org.coldie.wurmunlimited.mods.mobspawnmaxcount;

import com.wurmonline.server.creatures.CreatureTemplate;
import com.wurmonline.server.creatures.CreatureTemplateFactory;
import com.wurmonline.server.creatures.NoSuchCreatureTemplateException;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.Versioned;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mobspawnmaxcount implements WurmServerMod, ServerStartedListener, Configurable, Versioned {
	public static final Logger logger = Logger.getLogger(mobspawnmaxcount.class.getName());
	public static final String version = "ty1.0";
	public String mobMaxes = "";
	
	@Override
	public void onServerStarted() {
		try {
			changemax();
		} catch (NoSuchCreatureTemplateException | IllegalArgumentException | IllegalAccessException |
				 ClassCastException | NoSuchFieldException e) {
			logger.warning(e.toString());
		}
	}
	
	public void changemax() throws NoSuchCreatureTemplateException, IllegalArgumentException, IllegalAccessException, ClassCastException, NoSuchFieldException {
		logger.log(Level.INFO, "Creature max changes");
		String[] mobMaxesString = mobMaxes.split(";");
		for (String s : mobMaxesString) {
			String[] mobsmaxesstring = s.split(",");
			try {
				logger.log(Level.INFO, "mob id: " + mobsmaxesstring[0]);
				ReflectionUtil.setPrivateField(CreatureTemplateFactory.getInstance().getTemplate(Integer.parseInt(mobsmaxesstring[0])),
						ReflectionUtil.getField(CreatureTemplate.class, "maxPopulationOfCreatures"), Integer.parseInt(mobsmaxesstring[1]));
				ReflectionUtil.setPrivateField(CreatureTemplateFactory.getInstance().getTemplate(Integer.parseInt(mobsmaxesstring[0])),
						ReflectionUtil.getField(CreatureTemplate.class, "usesMaxPopulation"), true);
			} catch (Exception e) {
				logger.warning(e.toString());
			}
		}
	}

	@Override
	public void configure(Properties properties) {
		mobMaxes = properties.getProperty("mobmaxes", mobMaxes);
	}

	@Override
	public String getVersion(){
		return version;
	}
}