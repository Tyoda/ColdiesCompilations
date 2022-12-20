package org.coldie.wurmunlimited.mods.riftevent;

import com.wurmonline.server.FailedException;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Actions;
import com.wurmonline.server.creatures.*;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class riftevent implements WurmServerMod, Configurable, ServerStartedListener, ItemTemplatesCreatedListener, Versioned {
	static Logger logger = Logger.getLogger(riftevent.class.getName());
	public static final String version = "ty1.0";
	public static int itemtemplate1 = 6815;
	public static boolean craftable = true;
	public static int highpowerneeded = 400000;
	public static int highspawns = 5;
	public static String highmobids = "106";
	public static String highlootids = "1102";
	public static String[] highmobs;
	public static String[] highloots;
	public static int highrarity = 2;
	
	public static int midpowerneeded = 200000;
	public static int midspawns = 5;
	public static String midmobids = "106";
	public static String midlootids = "1102";
	public static String[] midmobs;
	public static String[] midloots;
	public static int midrarity = 1;
	
	public static int lowpowerneeded = 100000;
	public static int lowspawns = 5;
		public static String lowmobids = "106";
	public static String lowlootids = "1102";
	public static String[] lowmobs;
	public static String[] lowloots;
	public static int lowrarity = 0;
	
	public static int North = 10;
	public static int South = 10;
	public static int East = 10;
	public static int West = 10;
	public static int powerused = 0;
    
	@Override
	public void onItemTemplatesCreated() {
		new rifteventitems();
	}
	
	@Override
	public void onServerStarted() {
		riftsacrificeaction riftSacrificeAction = new riftsacrificeaction();
		ModActions.registerAction(riftSacrificeAction);

		riftspawnaction riftSpawnAction = new riftspawnaction();
		ModActions.registerAction(riftSpawnAction);
		
		try {
			ReflectionUtil.setPrivateField(Actions.actionEntrys[riftSacrificeAction.getActionId()],
			ReflectionUtil.getField(ActionEntry.class, "maxRange"), 8);
			ReflectionUtil.setPrivateField(Actions.actionEntrys[riftSpawnAction.getActionId()],
			ReflectionUtil.getField(ActionEntry.class, "maxRange"), 8);
		} catch (IllegalArgumentException | IllegalAccessException | ClassCastException | NoSuchFieldException e) {
			throw new HookException(e);
		}
	}

	@Override
	public void configure(Properties properties) {
		highpowerneeded = Integer.parseInt(properties.getProperty("highpowerneeded", Float.toString(highpowerneeded)));
		midpowerneeded = Integer.parseInt(properties.getProperty("midpowerneeded", Float.toString(midpowerneeded)));
		lowpowerneeded = Integer.parseInt(properties.getProperty("lowpowerneeded", Float.toString(lowpowerneeded)));
		
		
		craftable = Boolean.parseBoolean(properties.getProperty("craftable", Boolean.toString(craftable)));	
		highmobids = properties.getProperty("highmobids",highmobids);
		highlootids = properties.getProperty("lootids",highlootids);
		highmobs = highmobids.split(",");
		highloots = highlootids.split(",");
		highspawns = Integer.parseInt(properties.getProperty("highspawns", Float.toString(highspawns)));
		highrarity = Integer.parseInt(properties.getProperty("highrarity", Float.toString(highrarity)));
		
		midmobids = properties.getProperty("midmobids",midmobids);
		midlootids = properties.getProperty("midlootids",midlootids);
		midmobs = midmobids.split(",");
		midloots = midlootids.split(",");
		midspawns = Integer.parseInt(properties.getProperty("midspawns", Float.toString(midspawns)));
		midrarity = Integer.parseInt(properties.getProperty("midrarity", Float.toString(midrarity)));
		
		lowmobids = properties.getProperty("lowmobids",lowmobids);
		lowlootids = properties.getProperty("lowlootids",lowlootids);
		lowmobs = lowmobids.split(",");
		lowloots = lowlootids.split(",");
		lowspawns = Integer.parseInt(properties.getProperty("lowspawns", Float.toString(lowspawns)));
		lowrarity = Integer.parseInt(properties.getProperty("lowrarity", Float.toString(lowrarity)));
	
		North = Integer.parseInt(properties.getProperty("North", Float.toString(North)));
		South = Integer.parseInt(properties.getProperty("South", Float.toString(South)));
		East = Integer.parseInt(properties.getProperty("East", Float.toString(East)));
		West = Integer.parseInt(properties.getProperty("West", Float.toString(West)));
		if(North == 0) North = 1;
		if(South == 0) South = 1;
		if(East == 0) East = 1;
		if(West == 0) West = 1;
	}

    public static boolean spawnCreature(Creature Performer, Item target) {
    	int numberToSpawn = 1;
    	int rarity = 0;
    	String[] mobs = null;
    	String[] loots = null;
    	powerused = 0;
    	if(target.getData2() < lowpowerneeded) {
    		Performer.getCommunicator().sendSafeServerMessage("Anubis requires more power, the altar needs at least "+lowpowerneeded+" power it only has "+target.getData2());
    		return false;
    	}
    	
		if(target.getData2()>= lowpowerneeded) {
			mobs = lowmobs;
			loots = lowloots;
			numberToSpawn =  lowspawns;
			rarity = lowrarity;
			powerused = lowpowerneeded;
		}
		
		if(target.getData2()>= midpowerneeded) {
			mobs = midmobs;
			loots = midloots;
			rarity = midrarity;
			numberToSpawn = midspawns;
			powerused = midpowerneeded;
		}
		
		if(target.getData2()>= highpowerneeded) {
			mobs = highmobs;
			loots = highloots;
			rarity = highrarity;
			numberToSpawn = highspawns;
			powerused = highpowerneeded;
		}
		
    	for (int x=0; x<numberToSpawn; ++x) {
	        try {
	            CreatureTemplate ct = CreatureTemplateFactory.getInstance().getTemplate(Integer.parseInt(mobs[Server.rand.nextInt(mobs.length)]));
	            byte sex = 0;
	            boolean reborn = false;
	            if (Server.rand.nextInt(2) == 0) {
	                sex = 1;
	            }
	            byte ctype = (byte)Server.rand.nextInt(12);
	            if (Server.rand.nextInt(20) == 0) {
	                ctype = 99;
	            }
	            float spawnX = target.getPosX() - (West*4) + Server.rand.nextInt((West + East+1)*4);
	            float spawnY = target.getPosY() - (North*4) + Server.rand.nextInt((North + South+1)*4);
	            Creature creature = Creature.doNew(ct.getTemplateId(), true, spawnX, spawnY, Server.rand.nextFloat() * 360.0f, 0, ct.getName(), sex, (byte)0, ctype, reborn);
				Item item;
	            try {
					item = ItemFactory.createItem(Integer.parseInt(loots[Server.rand.nextInt(loots.length)]) , 50 + Server.rand.nextInt(41), "Anubis");
					item.setRarity((byte) rarity);
					creature.getInventory().insertItem(item, true);
				} catch (FailedException | NoSuchTemplateException e) {
					logger.warning(e.toString());
				}
			} catch (Exception e) {
	            logger.log(Level.WARNING, e.getMessage(), e);
	        	return false;
	        }
    	}
        return true;
    }

	@Override
	public String getVersion(){
		return version;
	}
}