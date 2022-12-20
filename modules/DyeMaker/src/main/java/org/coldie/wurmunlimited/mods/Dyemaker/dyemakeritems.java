package org.coldie.wurmunlimited.mods.Dyemaker;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.SkillList;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modsupport.IdFactory;
import org.gotti.wurmunlimited.modsupport.IdType;

import java.io.IOException;


public class dyemakeritems {
	
	public dyemakeritems() {
		try {
			Dyemaker.targetid = IdFactory.getIdFor("dye maker", IdType.ITEMTEMPLATE);
			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(Dyemaker.targetid, "dye maker", "dye makers", "almost full", "somewhat occupied", "half-full", "emptyish",
					"A device for storing and extracting Dye.",
					new short[] { 108, 135, 31, 25, 51, 86, 52, 44, 199  },(short)288, (short)1, 0,
					2419200L, 300, 300, 300, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.dyemaker", 12.0F,
					70000,(byte) 15, 100, false);
		}catch(IOException e){ throw new HookException(e); }

        AdvancedCreationEntry dyemaker = CreationEntryCreator.createAdvancedEntry(SkillList.MASONRY, ItemList.stoneBrick, ItemList.clay, Dyemaker.targetid, true, true, 0.0f, false, true, CreationCategories.FURNITURE);
        dyemaker.addRequirement(new CreationRequirement(1, ItemList.stoneBrick, 50, true));
        dyemaker.addRequirement(new CreationRequirement(2, ItemList.clay, 30, true));
        dyemaker.addRequirement(new CreationRequirement(3, ItemList.sapphire, 1, true));
        dyemaker.addRequirement(new CreationRequirement(4, ItemList.ruby, 1, true));
        dyemaker.addRequirement(new CreationRequirement(5, ItemList.emerald, 1, true));
        dyemaker.addRequirement(new CreationRequirement(6, ItemList.diamond, 1, true));
	}	
}