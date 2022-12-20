package net.coldie.wurmunlimited.mods.bountycoldiestyle;

import java.util.Properties;
import java.util.logging.Logger;

import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import com.wurmonline.server.creatures.Creature;


public class bountymod implements WurmServerMod, Configurable, PreInitable, ServerStartedListener, Versioned {
	public static final Logger logger = Logger.getLogger(bountymod.class.getName());

	public static final String version = "ty1.0";
   
	static float bountyMultiplier = 0.7f;
	static float DRFactor = 1.4f;
	public static String[] group1ids;
	public static String[] group2ids;
	public static String[] group3ids;
	public static String[] group4ids;	
	public static String[] group5ids;
	public static String[] group6ids;	
	public static String[] group7ids;
	public static String[] group8ids;	
	public static String[] group9ids;
	
	static float group1factor = 1.0f;
	static float group2factor = 1.0f;
	static float group3factor = 1.0f;
	static float group4factor = 1.0f;
	static float group5factor = 1.0f;
	static float group6factor = 1.0f;
	static float group7factor = 1.0f;
	static float group8factor = 1.0f;
	static float group9factor = 1.0f;
	
	static float fierce = 1.0f;
	static float angry = 1.0f;
	static float raging = 1.0f;
	static float slow = 1.0f;
	static float alert = 1.0f;
	static float greenish = 1.0f;
	static float lurking = 1.0f;
	static float sly = 1.0f;
	static float hardened = 1.0f;
	static float scared = 1.0f;
	static float diseased = 1.0f;
	static float champion = 1.0f;
	
	@Override
	public void onServerStarted() {
		ModActions.registerAction(new bountyreloadaction());
		ModActions.registerAction(new getidaction());
		currencyadding.onServerStarted();
	}

	
	@Override
	public void configure(Properties properties) {
		doconfig(properties);
	}
		
	public static void doconfig(Properties properties){
		bountyMultiplier = Float.parseFloat(properties.getProperty("bountyMultiplier", Float.toString(bountyMultiplier)));
		DRFactor = Float.parseFloat(properties.getProperty("DRFactor", Float.toString(DRFactor)));
		group1ids = properties.getProperty("group1ids").split(";");
		group2ids = properties.getProperty("group2ids").split(";");
		group3ids = properties.getProperty("group3ids").split(";");
		group4ids = properties.getProperty("group4ids").split(";");
		group5ids = properties.getProperty("group5ids").split(";");
		group6ids = properties.getProperty("group6ids").split(";");
		group7ids = properties.getProperty("group7ids").split(";");
		group8ids = properties.getProperty("group8ids").split(";");
		group9ids = properties.getProperty("group9ids").split(";");

		
		group1factor = Float.parseFloat(properties.getProperty("group1factor", Float.toString(group1factor)));
		group2factor = Float.parseFloat(properties.getProperty("group2factor", Float.toString(group2factor)));
		group3factor = Float.parseFloat(properties.getProperty("group3factor", Float.toString(group3factor)));
		group4factor = Float.parseFloat(properties.getProperty("group4factor", Float.toString(group4factor)));
		group5factor = Float.parseFloat(properties.getProperty("group5factor", Float.toString(group5factor)));
		group6factor = Float.parseFloat(properties.getProperty("group6factor", Float.toString(group6factor)));
		group7factor = Float.parseFloat(properties.getProperty("group7factor", Float.toString(group7factor)));
		group8factor = Float.parseFloat(properties.getProperty("group8factor", Float.toString(group8factor)));
		group9factor = Float.parseFloat(properties.getProperty("group9factor", Float.toString(group9factor)));

		
		
		fierce = Float.parseFloat(properties.getProperty("fierce", Float.toString(fierce)));
		angry = Float.parseFloat(properties.getProperty("angry", Float.toString(angry)));
		raging = Float.parseFloat(properties.getProperty("raging", Float.toString(raging)));
		slow = Float.parseFloat(properties.getProperty("slow", Float.toString(slow)));
		alert = Float.parseFloat(properties.getProperty("alert", Float.toString(alert)));
		greenish = Float.parseFloat(properties.getProperty("greenish", Float.toString(greenish)));
		lurking = Float.parseFloat(properties.getProperty("lurking", Float.toString(lurking)));
		sly = Float.parseFloat(properties.getProperty("sly", Float.toString(sly)));
		hardened = Float.parseFloat(properties.getProperty("hardened", Float.toString(hardened)));
		scared = Float.parseFloat(properties.getProperty("scared", Float.toString(scared)));
		diseased = Float.parseFloat(properties.getProperty("diseased", Float.toString(diseased)));
		champion = Float.parseFloat(properties.getProperty("champion", Float.toString(champion)));

	}
	
	public static boolean isgroup1(Creature creature)
	{
		for(String boss : group1ids)
		{
			if(creature.getTemplate().getTemplateId() == Integer.parseInt(boss))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isgroup2(Creature creature)
	{
		for(String boss : group2ids)
		{
			if(creature.getTemplate().getTemplateId() == Integer.parseInt(boss))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isgroup3(Creature creature)
	{
		for(String boss : group3ids)
		{
			if(creature.getTemplate().getTemplateId() == Integer.parseInt(boss))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isgroup4(Creature creature)
	{
		for(String boss : group4ids)
		{
			if(creature.getTemplate().getTemplateId() == Integer.parseInt(boss))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isgroup5(Creature creature)
	{
		for(String boss : group5ids)
		{
			if(creature.getTemplate().getTemplateId() == Integer.parseInt(boss))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isgroup6(Creature creature)
	{
		for(String boss : group6ids)
		{
			if(creature.getTemplate().getTemplateId() == Integer.parseInt(boss))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isgroup7(Creature creature)
	{
		for(String boss : group7ids)
		{
			if(creature.getTemplate().getTemplateId() == Integer.parseInt(boss))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isgroup8(Creature creature)
	{
		for(String boss : group8ids)
		{
			if(creature.getTemplate().getTemplateId() == Integer.parseInt(boss))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isgroup9(Creature creature)
	{
		for(String boss : group9ids)
		{
			if(creature.getTemplate().getTemplateId() == Integer.parseInt(boss))
			{
				return true;
			}
		}
		return false;
	}
	@Override
	public void preInit() {
		ModActions.init();
	}

	public String getVersion() {
		return version;
	}
}