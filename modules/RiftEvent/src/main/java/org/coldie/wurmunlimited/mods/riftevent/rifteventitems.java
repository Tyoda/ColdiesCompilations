package org.coldie.wurmunlimited.mods.riftevent;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.SkillList;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.IdFactory;
import org.gotti.wurmunlimited.modsupport.IdType;

import java.io.IOException;


public class rifteventitems implements WurmServerMod, ItemTypes, MiscConstants {
	
	public rifteventitems() {
		try {
			riftevent.itemtemplate1 = IdFactory.getIdFor("Altar of Anubis", IdType.ITEMTEMPLATE);
			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(riftevent.itemtemplate1, "Altar of Anubis", "Altars of Anubis", "almost full", "somewhat occupied", "half-full", "emptyish",
					"Used to summon Rifts to your world, be warned they aren't friendly.",
					new short[] { 1, 22, 31, 44, 48, 51, 52, 67, 92, 108, 109, 135, 178, 180, 199, 225, 229, 259 }, 
					(short)60, (short)1, 0, 12096000L, 41, 41, 210, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, 
					"model.structure.rift.altar.1.", 62.0F, 5000, (byte)7, 10000, false).setContainerSize(300, 600, 600);
		}catch(IOException e){ throw new HookException(e); }

		if(riftevent.craftable) {
	        AdvancedCreationEntry AOA = CreationEntryCreator.createAdvancedEntry(SkillList.MASONRY, ItemList.stoneBrick, ItemList.concrete, riftevent.itemtemplate1, true, true, 0.0f, false, true, CreationCategories.EPIC);
	        AOA.addRequirement(new CreationRequirement(1, ItemList.stoneBrick, 9, true));
	        AOA.addRequirement(new CreationRequirement(2, ItemList.concrete, 9, true));
	        AOA.addRequirement(new CreationRequirement(3, ItemList.sheetGold, 1, true));
		}
	}
}