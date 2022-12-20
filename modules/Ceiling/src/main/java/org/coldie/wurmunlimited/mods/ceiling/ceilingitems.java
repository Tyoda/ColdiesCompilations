package org.coldie.wurmunlimited.mods.ceiling;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.SkillList;

import java.io.IOException;



public class ceilingitems {
	
	public ceilingitems() {
		ceiling ceil = ceiling.getInstance();
		try {
			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(ceil.itemtemplate1, "Ceiling", "Ceiling", "almost full", "somewhat occupied", "half-full", "emptyish",
					"A level covering above your head inside a building",
					new short[] { 21, 44, 51, 86, 108, 111, 123, 157  },(short)293, (short)1, 0, 9072000L, 2, 5, 100, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, ceil.itemmodel1, 1.0F, 200,(byte) 15, 100, false);
           
			AdvancedCreationEntry AOT = CreationEntryCreator.createAdvancedEntry(SkillList.CARPENTRY, ItemList.plank, ItemList.nailsIronLarge, ceil.itemtemplate1, true, true, 0.0f, true, false, CreationCategories.FURNITURE);
            AOT.addRequirement(new CreationRequirement(1, ItemList.plank, 10, true));
            AOT.addRequirement(new CreationRequirement(2, ItemList.nailsIronLarge, 5, true));	
            
		
		
		}catch(IOException e){
			ceiling.logger.severe("Could not create ceiling item. "+e);
		}
	}
}