package net.coldie.wurmunlimited.mods.weapons;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.combat.Weapon;
import com.wurmonline.server.items.*;
import org.gotti.wurmunlimited.modsupport.IdFactory;
import org.gotti.wurmunlimited.modsupport.IdType;

import java.io.IOException;
import java.util.logging.Level;

public class Items {
   public static int headSplitterId = -10;
   public static int javelinId = -10;
   public static int spikedClubId = -10;
   public static int warpickId = -10;
   public static int daggerId = -10;
   public static void onItemTemplatesCreated() {
      try {
         headSplitterId = IdFactory.getIdFor("coldie.weapons.headsplitteraxe", IdType.ITEMTEMPLATE);
         javelinId = IdFactory.getIdFor("coldie.weapons.javelin", IdType.ITEMTEMPLATE);
         spikedClubId = IdFactory.getIdFor("coldie.spiked.club", IdType.ITEMTEMPLATE);
         warpickId = IdFactory.getIdFor("coldie.weapons.warpick", IdType.ITEMTEMPLATE);
         daggerId = IdFactory.getIdFor("coldie.weapons.dagger", IdType.ITEMTEMPLATE);
         ItemTemplateCreator.createItemTemplate(headSplitterId, "head splitter axe", "head splitter axes", "superb", "good", "ok", "poor", "A very large weapon.", new short[]{108, 44, 22, 2, 15, 37, 84, 147, 189}, (short)1227, (short)1, 60, 3024000L, 4, 15, 100, 10025, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.weapon.axe.headsplitter.", 30.0F, 3000, (byte)11, 10000, true);
         ItemTemplateCreator.createItemTemplate(javelinId, "javelin", "javelins", "superb", "good", "ok", "poor", "A long and slender weapon.", new short[]{108, 44, 22, 13, 37, 154, 84, 147, 189}, (short)1221, (short)1, 50, 3024000L, 3, 5, 205, 10088, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.weapon.polearm.javelin.", 20.0F, 2700, (byte)11, 10000, true);
         ItemTemplateCreator.createItemTemplate(spikedClubId, "spiked club", "spiked clubs", "superb", "good", "ok", "poor", "A club with metal spikes.", new short[]{108, 44, 22, 13, 15, 37, 147, 189}, (short)1239, (short)1, 50, 3024000L, 1, 20, 70, 10064, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.weapon.club.spiked club.", 20.0F, 3000, (byte)11, 10000, true);
         ItemTemplateCreator.createItemTemplate(warpickId, "warpick", "warpicks", "superb", "good", "ok", "poor", "A pick designed for battle.", new short[]{108, 44, 22, 13, 15, 37, 147, 189}, (short)743, (short)1, 30, 9072000L, 1, 30, 70, 10009, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.weapon.pick.warpick.", 30.0F, 3000, (byte)11, 10000, true);
         ItemTemplateCreator.createItemTemplate(daggerId, "dagger", "daggers", "superb", "good", "ok", "poor", "A small but deadly weapon.", new short[]{108, 44, 22, 2, 17, 37, 147, 189}, (short)735, (short)1, 30, 9072000L, 1, 30, 70, 10029, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.weapon.blade.dagger.", 30.0F, 1100, (byte)11, 10000, true);
      } catch (IOException e) {
         Weapons.logger.log(Level.SEVERE, "Failed while creating item templates.", e);
      }

      AdvancedCreationEntry headsplitter = CreationEntryCreator.createAdvancedEntry(1016, 23, 88, headSplitterId, true, true, 0.0F, false, false, CreationCategories.WEAPONS);
      headsplitter.addRequirement(new CreationRequirement(1, 1250, 2, true));
      headsplitter.addRequirement(new CreationRequirement(2, 88, 1, true));
      AdvancedCreationEntry javelin = CreationEntryCreator.createAdvancedEntry(1016, 23, 709, javelinId, true, true, 0.0F, false, false, CreationCategories.WEAPONS);
      javelin.addRequirement(new CreationRequirement(1, 100, 5, true));
      AdvancedCreationEntry spikedclub = CreationEntryCreator.createAdvancedEntry(1016, 314, 444, spikedClubId, true, true, 0.0F, false, false, CreationCategories.WEAPONS);
      spikedclub.addRequirement(new CreationRequirement(1, 218, 15, true));
      AdvancedCreationEntry warpick = CreationEntryCreator.createAdvancedEntry(1016, 23, 123, warpickId, true, true, 0.0F, false, false, CreationCategories.WEAPONS);
      warpick.addRequirement(new CreationRequirement(1, 376, 1, true));
      CreationEntryCreator.createSimpleEntry(1016, 64, 125, daggerId, false, true, 0.0F, false, false, CreationCategories.WEAPONS);
   }

   public static void onServerStarted(){
      final Weapons weapons = Weapons.getInstance();
      new Weapon(headSplitterId, weapons.headsplitterdamage, weapons.headsplitterspeed, 0.05F, 5, 5, 0.2F, 0.0);
      new Weapon(javelinId, weapons.javelindamage, weapons.javelinspeed, 0.06F, 7, 4, 1.0F, 0.0);
      new Weapon(spikedClubId, weapons.spikedclubdamage, weapons.spikedclubspeed, 0.01F, 4, 6, 1.0F, 0.0);
      new Weapon(warpickId, weapons.warpickdamage, weapons.warpickspeed, 0.02F, 3, 3, 0.1F, 0.0);
      new Weapon(daggerId, weapons.daggerdamage, weapons.daggerspeed, 0.05F, 2, 3, 0.1F, 0.0);
   }
}
