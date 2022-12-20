package org.coldie.wurmunlimited.mods.deedmaker;

import java.util.Properties;

import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;


public class deedmaker implements WurmServerMod, Configurable, ServerStartedListener, ItemTemplatesCreatedListener, Versioned {
	public static final String version = "ty1.0";
	private float goldUpkeep = 1.0f;
	private int invitorID = 131322;
	private String InvitorModel = "model.creature.humanoid.human.guard.male";

	private static deedmaker instance;

	public deedmaker(){
		instance = this;
	}
	
    public String getVersion() {
        return version;
    }
   	
	@Override
	public void configure(Properties properties) {
		InvitorModel = properties.getProperty("InvitorModel",InvitorModel);
		invitorID = Integer.parseInt(properties.getProperty("InvitorID", Float.toString(invitorID)));
		doconfig(properties);
	}
	
	public void doconfig(Properties properties){
		goldUpkeep = Float.parseFloat(properties.getProperty("goldupkeep", Float.toString(goldUpkeep)));
	}
	
	public void onItemTemplatesCreated() {
		new deedmakeritems();
	}	
	
	@Override
	public void onServerStarted() {
		ModActions.registerAction(new deedmakeraction());
		ModActions.registerAction(new changemayor());
		ModActions.registerAction(new joindeedaction());
	}

	public static deedmaker getInstance(){
		return instance;
	}
	public int getInvitorID(){
		return invitorID;
	}
	public void setInvitorID(int newId){
		invitorID = newId;
	}
	public float getGoldUpkeep(){
		return goldUpkeep;
	}
	public String getInvitorModel(){
		return InvitorModel;
	}
}
