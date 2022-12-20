package net.coldie.wurmunlimited.mods.portals;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.SkillList;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;

import java.io.IOException;

public class PortalItems {
	
	public PortalItems() {
		try { // TODO: IdFactory whatever
			ItemTemplateCreator.createItemTemplate(4002, "Server Portal", "portals", "almost full", "somewhat occupied", "half-full", "emptyish",
			"This rudimentary structure is rumoured to lead to far away lands.",
			new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 86, 178, 92 },(short) 60,(short) 1, 0, 2419200L, 100, 100, 200, -10,
			MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.structure.portal.8.", 90.0F, 1400,(byte) 15, 100, false);

			ItemTemplateCreator.createItemTemplate(4003, "Huge Server Portal", "portals", "almost full", "somewhat occupied", "half-full",
			"emptyish",
			"This impressive structure leads to far away lands.",
			new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 178, 92 },(short) 60,(short) 1, 0, Long.MAX_VALUE, 100, 100,
			200, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.structure.portal.7.", 90.0F, 1400,(byte) 15,
			100, false);

			ItemTemplateCreator.createItemTemplate(4004, 3, "Steel Server Portal", "portals", "superb", "good", "ok", "poor", "",
			new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 178, 92 },(short) 60,(short) 1, 0, Long.MAX_VALUE, 100, 100, 200, -10,
			MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.structure.portal.9.", 90.0F, 1400,(byte) 57, 10000, false,
			-1);

			ItemTemplateCreator.createItemTemplate(4010, 3, "Dark Crystal Server Portal", "portals", "superb", "good", "ok", "poor", "",
			new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 178, 92 },(short) 60,(short) 1, 0, Long.MAX_VALUE, 100, 100, 200, -10,
			MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.structure.portal.3.", 90.0F, 1400,(byte) 57, 10000, false,
			-1);

			ItemTemplateCreator.createItemTemplate(4011, 3, "Crystal Server Portal", "portals", "superb", "good", "ok", "poor", "",
			new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 178, 92 },(short) 60,(short) 1, 0, Long.MAX_VALUE, 100, 100, 200, -10,
			MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.structure.portal.4.", 90.0F, 1400,(byte) 57, 10000, false,
			-1);
			
			if (portalmod.craftPortals){
				AdvancedCreationEntry AOT = CreationEntryCreator.createAdvancedEntry(SkillList.MASONRY,
						ItemList.marbleBrick, ItemList.concrete, 4002, true, true,
						0.0f, false, true, CreationCategories.EPIC);
				AOT.addRequirement(new CreationRequirement(1, ItemList.marbleBrick, 1000, true));
				AOT.addRequirement(new CreationRequirement(2, ItemList.concrete, 1000, true));

				AdvancedCreationEntry AOT2 = CreationEntryCreator.createAdvancedEntry(SkillList.MASONRY, ItemList.marbleBrick,
						ItemList.concrete, 4003, true, true, 0.0f,
						false, true, CreationCategories.EPIC);
				AOT2.addRequirement(new CreationRequirement(1, ItemList.marbleBrick, 1500, true));
				AOT2.addRequirement(new CreationRequirement(2, ItemList.concrete, 1500, true));

				AdvancedCreationEntry AOT3 = CreationEntryCreator.createAdvancedEntry(SkillList.SMITHING_BLACKSMITHING,
						ItemList.sheetSteel, ItemList.concrete, 4004, true, true,
						0.0f, false, true, CreationCategories.EPIC);
				AOT3.addRequirement(new CreationRequirement(1, ItemList.sheetSteel, 100, true));
				AOT3.addRequirement(new CreationRequirement(2, ItemList.marbleBrick, 1000, true));
				AOT3.addRequirement(new CreationRequirement(3, ItemList.concrete, 1000, true));

				AdvancedCreationEntry AOT4 = CreationEntryCreator.createAdvancedEntry(SkillList.SMITHING_GOLDSMITHING,
						ItemList.riftCrystal, ItemList.concrete, 4010, true, true,
						0.0f, false, true, CreationCategories.EPIC);
				AOT4.addRequirement(new CreationRequirement(1, ItemList.riftCrystal, 100, true));
				AOT4.addRequirement(new CreationRequirement(2, ItemList.concrete, 1000, true));

				AdvancedCreationEntry AOT5 = CreationEntryCreator.createAdvancedEntry(SkillList.SMITHING_GOLDSMITHING,
						ItemList.riftCrystal, ItemList.concrete, 4011, true, true,
						0.0f, false, true, CreationCategories.EPIC);
				AOT5.addRequirement(new CreationRequirement(1, ItemList.riftCrystal, 100, true));
				AOT5.addRequirement(new CreationRequirement(2, ItemList.concrete, 1000, true));
			}

			if (portalmod.newConcrete){
				// new way to make concrete
				CreationEntryCreator.createSimpleEntry(10042, portalmod.newConcreteItem1,
						portalmod.newConcreteItem2, ItemList.concrete, true, true,
						0.0F, true, false, CreationCategories.RESOURCES);
				// 10042,782
			}
		}catch(IOException e){ throw new HookException(e); }
	}
}