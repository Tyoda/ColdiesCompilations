package org.coldie.wurmunlimited.mods.VRmounts;

import static org.gotti.wurmunlimited.modsupport.creatures.ModCreatures.addCreature;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.creatures.ModCreatures;

public class VRmounts implements WurmServerMod, ServerStartedListener, Configurable, Initable {
	 private static Logger logger = Logger.getLogger(VRmounts.class.getName());
	
	 public static float mountedspeed = 38.0F;
	 public static int sizeadjust = 40;
	 
	 public static float BODY_STRENGTH = 40.0F;	 
	 public static float BODY_CONTROL= 50.0F;
	 public static float BODY_STAMINA= 50.0F;
	 public static float MIND_LOGICAL= 40.0F;
	 public static float MIND_SPEED= 40.0F;
	 public static float SOUL_STRENGTH= 60.0F;
	 public static float SOUL_DEPTH= 40.0F;
	 public static float WEAPONLESS_FIGHTING= 60.0F;
	 public static float GROUP_FIGHTING= 40.0F;
	 public static float baseCombatRating=40.0F;	 
	 
	 public static int maxPopulationOfCreatures = 40;
	 
	 public static String examinemessage ="I am awesome.";

     public String getVersion() {
        return "v2.0";
     }

	@Override
	public void configure(Properties properties) {
		examinemessage = properties.getProperty("examinemessage",examinemessage);
		BODY_STRENGTH = Float.parseFloat(properties.getProperty("BODY_STRENGTH", Float.toString(BODY_STRENGTH)));		
		mountedspeed = Float.parseFloat(properties.getProperty("mountedspeed", Float.toString(mountedspeed)));
		sizeadjust = Integer.parseInt(properties.getProperty("sizeadjust", Float.toString(sizeadjust)));
		BODY_CONTROL = Float.parseFloat(properties.getProperty("BODY_CONTROL", Float.toString(BODY_CONTROL)));
		BODY_STAMINA = Float.parseFloat(properties.getProperty("BODY_STAMINA", Float.toString(BODY_STAMINA)));
		MIND_LOGICAL = Float.parseFloat(properties.getProperty("MIND_LOGICAL", Float.toString(MIND_LOGICAL)));
		MIND_SPEED = Float.parseFloat(properties.getProperty("MIND_SPEED", Float.toString(MIND_SPEED)));
		SOUL_STRENGTH = Float.parseFloat(properties.getProperty("SOUL_STRENGTH", Float.toString(SOUL_STRENGTH)));
		SOUL_DEPTH = Float.parseFloat(properties.getProperty("SOUL_DEPTH", Float.toString(SOUL_DEPTH)));
		WEAPONLESS_FIGHTING = Float.parseFloat(properties.getProperty("WEAPONLESS_FIGHTING", Float.toString(WEAPONLESS_FIGHTING)));
		GROUP_FIGHTING = Float.parseFloat(properties.getProperty("GROUP_FIGHTING", Float.toString(GROUP_FIGHTING)));
		baseCombatRating = Float.parseFloat(properties.getProperty("baseCombatRating", Float.toString(baseCombatRating)));
		maxPopulationOfCreatures = Integer.parseInt(properties.getProperty("maxPopulationOfCreatures", Float.toString(maxPopulationOfCreatures)));
	
	}

	@Override
	public void onServerStarted() {
	
        try {
            logger.info("Setting corpse models...");
            CorpseModifier.setTemplateVariables();
        } catch (Throwable throwable) {
            logger.log(Level.SEVERE, "Error In onServerStarted", throwable);
        }		
	}

	@Override
	public void init() {
        ModCreatures.init();
        addCreature(new mount1());
	}
}

