package net.coldie.wurmunlimited.mods.weapons;

import org.gotti.wurmunlimited.modloader.interfaces.*;

import java.util.Properties;
import java.util.logging.Logger;

public class Weapons implements WurmServerMod, Configurable, ServerStartedListener, ItemTemplatesCreatedListener, Versioned {
   public static final Logger logger = Logger.getLogger(Weapons.class.getName());
   public float headsplitterdamage = 18.0F;
   public float headsplitterspeed = 8.0F;
   public float javelindamage = 9.0F;
   public float javelinspeed = 5.0F;
   public float spikedclubdamage = 8.0F;
   public float spikedclubspeed = 3.0F;
   public float warpickdamage = 3.0F;
   public float warpickspeed = 2.0F;
   public float daggerdamage = 3.0F;
   public float daggerspeed = 1.0F;

   private static Weapons instance;

   public Weapons(){
      instance = this;
   }

   public void configure(Properties properties) {
      headsplitterdamage = Float.parseFloat(properties.getProperty("headsplitterdamage", Float.toString(headsplitterdamage)));
      headsplitterspeed = Float.parseFloat(properties.getProperty("headsplitterspeed", Float.toString(headsplitterspeed)));
      javelindamage = Float.parseFloat(properties.getProperty("javelindamage", Float.toString(javelindamage)));
      javelinspeed = Float.parseFloat(properties.getProperty("javelinspeed", Float.toString(javelinspeed)));
      spikedclubdamage = Float.parseFloat(properties.getProperty("spikedclubdamage", Float.toString(spikedclubdamage)));
      spikedclubspeed = Float.parseFloat(properties.getProperty("spikedclubspeed", Float.toString(spikedclubspeed)));
      warpickdamage = Float.parseFloat(properties.getProperty("warpickdamage", Float.toString(warpickdamage)));
      warpickspeed = Float.parseFloat(properties.getProperty("warpickspeed", Float.toString(warpickspeed)));
      daggerdamage = Float.parseFloat(properties.getProperty("daggerdamage", Float.toString(daggerdamage)));
      daggerspeed = Float.parseFloat(properties.getProperty("daggerspeed", Float.toString(daggerspeed)));
   }

   public void onServerStarted() {
      Items.onServerStarted();
   }

   public void onItemTemplatesCreated() {
      Items.onItemTemplatesCreated();
   }

   public static Weapons getInstance(){
      return instance;
   }

   @Override
   public String getVersion() {
      return "ty1.0";
   }
}
