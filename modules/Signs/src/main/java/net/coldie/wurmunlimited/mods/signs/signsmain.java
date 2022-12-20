package net.coldie.wurmunlimited.mods.signs;

import org.gotti.wurmunlimited.modloader.interfaces.*;

import java.util.Properties;
import java.util.logging.Logger;

public class signsmain implements WurmServerMod, Configurable, ItemTemplatesCreatedListener, ServerStartedListener, Initable {
   static Logger logger = Logger.getLogger(signsmain.class.getName());
   static int startid = 6100;
   static String[] signnames = new String[]{"Armour Smith", "Bank", "Berries", "Blacksmith", "Bushes", "Carpentry", "Digging", "Dyes", "Fruits", "Grains", "Grapes", "Hops", "Inn Food", "Inn Sleep", "Leather Working", "Maple", "Mining", "Magic Mushrooms", "Masonry", "Mushrooms", "Nuts", "Empty Frame", "Pottery", "Rope Making", "Shipbuilding", "Sorcery", "Stone Cutting", "Tailoring Cotton", "Tailoring Wool", "Tavern", "Vegetables", "Weaponsmith", "Woodcutting"};

   public signsmain() {
   }

   public String getVersion() {
      return "v1.5";
   }

   public void onItemTemplatesCreated() {
      new signitems();
   }

   public void configure(Properties arg0) {
   }

   public void onServerStarted() {
   }

   public void init() {
   }
}
