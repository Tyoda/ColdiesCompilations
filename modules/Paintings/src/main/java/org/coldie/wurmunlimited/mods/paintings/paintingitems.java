package org.coldie.wurmunlimited.mods.paintings;

import java.io.IOException;

import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modsupport.items.ModItems;
import org.gotti.wurmunlimited.modsupport.items.ModelNameProvider;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.items.AdvancedCreationEntry;
import com.wurmonline.server.items.CreationCategories;
import com.wurmonline.server.items.CreationEntryCreator;
import com.wurmonline.server.items.CreationRequirement;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.skills.SkillList;


public class paintingitems {
	
	public paintingitems() {
		landscape();
		portrait();
		window();
		newpaintings();
	}
	
	private void window() {
	    try {
	    	com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+16, "Window Portrait Painting 1", "Window Portrait Painting 1", "superb", "good", "ok", "poor",
	    			"A picture that fits nicely in a window.",
	    			new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
	    			MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.windowportrait1", 30.0F, 500, (byte)22, 10000, true);

	    	com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+17, "Window Portrait Painting 2", "Window Portrait Painting 2", "superb", "good", "ok", "poor",
	    			"A picture that fits nicely in a window.",
	    			new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
	    			MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.windowportrait2", 30.0F, 500, (byte)22, 10000, true);

	    	com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+18, "Window Portrait Painting 3", "Window Portrait Painting 3", "superb", "good", "ok", "poor",
	    			"A picture that fits nicely in a window.",
	    			new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
	    			MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.windowportrait3", 30.0F, 500, (byte)22, 10000, true);
	
	    	com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+19, "Window Portrait Painting 4", "Window Portrait Painting 4", "superb", "good", "ok", "poor",
	    			"A picture that fits nicely in a window.",
	    			new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
	    			MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.windowportrait4", 30.0F, 500, (byte)22, 10000, true);

	    	com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+20, "Window Portrait Painting 5", "Window Portrait Painting 5", "superb", "good", "ok", "poor",
	    			"A picture that fits nicely in a window.",
	    			new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
	    			MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.windowportrait5", 30.0F, 500, (byte)22, 10000, true);
	    	
	    	com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+21, "Window Portrait Painting 6", "Window Portrait Painting 6", "superb", "good", "ok", "poor",
	    			"A picture that fits nicely in a window.",
	    			new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
	    			MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.windowportrait6", 30.0F, 500, (byte)22, 10000, true);

	    	com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+22, "Window Portrait Painting 7", "Window Portrait Painting 7", "superb", "good", "ok", "poor",
	    			"A picture that fits nicely in a window.",
	    			new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
	    			MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.windowportrait7", 30.0F, 500, (byte)22, 10000, true);

	    	com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+23, "Window Portrait Painting 8", "Window Portrait Painting 8", "superb", "good", "ok", "poor",
	    			"A picture that fits nicely in a window.",
	    			new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
	    			MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.windowportrait8", 30.0F, 500, (byte)22, 10000, true);
	
	    	com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+24, "Window Portrait Painting 9", "Window Portrait Painting 9", "superb", "good", "ok", "poor",
	    			"A picture that fits nicely in a window.",
	    			new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
	    			MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.windowportrait9", 30.0F, 500, (byte)22, 10000, true);

	    	com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+25, "Window Portrait Painting 10", "Window Portrait Painting 10", "superb", "good", "ok", "poor",
	    			"A picture that fits nicely in a window.",
	    			new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
	    			MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.windowportrait10", 30.0F, 500, (byte)22, 10000, true);
	    	}catch(IOException e){
				throw new HookException(e);
	    	}

	    	AdvancedCreationEntry windowportrait1 = CreationEntryCreator.createAdvancedEntry(SkillList.CLOTHTAILORING, ItemList.needleIron, ItemList.clothString, paintings.targetid+16, false, true, 0.0f, false, false, CreationCategories.FURNITURE);
	    	windowportrait1.addRequirement(new CreationRequirement(1, ItemList.clothYard, 5, true));
	    	windowportrait1.addRequirement(new CreationRequirement(2, ItemList.dye, 1, true));

	    	AdvancedCreationEntry windowportrait2 = CreationEntryCreator.createAdvancedEntry(SkillList.CLOTHTAILORING, ItemList.needleIron, ItemList.clothString, paintings.targetid+17, false, true, 0.0f, false, false, CreationCategories.FURNITURE);
	    	windowportrait2.addRequirement(new CreationRequirement(1, ItemList.clothYard, 5, true));
	    	windowportrait2.addRequirement(new CreationRequirement(2, ItemList.dye, 1, true));

	    	AdvancedCreationEntry windowportrait3 = CreationEntryCreator.createAdvancedEntry(SkillList.CLOTHTAILORING, ItemList.needleIron, ItemList.clothString, paintings.targetid+18, false, true, 0.0f, false, false, CreationCategories.FURNITURE);
	    	windowportrait3.addRequirement(new CreationRequirement(1, ItemList.clothYard, 5, true));
	    	windowportrait3.addRequirement(new CreationRequirement(2, ItemList.dye, 1, true));

	    	AdvancedCreationEntry windowportrait4 = CreationEntryCreator.createAdvancedEntry(SkillList.CLOTHTAILORING, ItemList.needleIron, ItemList.clothString, paintings.targetid+19, false, true, 0.0f, false, false, CreationCategories.FURNITURE);
	    	windowportrait4.addRequirement(new CreationRequirement(1, ItemList.clothYard, 5, true));
	    	windowportrait4.addRequirement(new CreationRequirement(2, ItemList.dye, 1, true));

	    	AdvancedCreationEntry windowportrait5 = CreationEntryCreator.createAdvancedEntry(SkillList.CLOTHTAILORING, ItemList.needleIron, ItemList.clothString, paintings.targetid+20, false, true, 0.0f, false, false, CreationCategories.FURNITURE);
	    	windowportrait5.addRequirement(new CreationRequirement(1, ItemList.clothYard, 5, true));
	    	windowportrait5.addRequirement(new CreationRequirement(2, ItemList.dye, 1, true));

	    	AdvancedCreationEntry windowportrait6 = CreationEntryCreator.createAdvancedEntry(SkillList.CLOTHTAILORING, ItemList.needleIron, ItemList.clothString, paintings.targetid+21, false, true, 0.0f, false, false, CreationCategories.FURNITURE);
	    	windowportrait6.addRequirement(new CreationRequirement(1, ItemList.clothYard, 5, true));
	    	windowportrait6.addRequirement(new CreationRequirement(2, ItemList.dye, 1, true));

	    	AdvancedCreationEntry windowportrait7 = CreationEntryCreator.createAdvancedEntry(SkillList.CLOTHTAILORING, ItemList.needleIron, ItemList.clothString, paintings.targetid+22, false, true, 0.0f, false, false, CreationCategories.FURNITURE);
	    	windowportrait7.addRequirement(new CreationRequirement(1, ItemList.clothYard, 5, true));
	    	windowportrait7.addRequirement(new CreationRequirement(2, ItemList.dye, 1, true));

	    	AdvancedCreationEntry windowportrait8 = CreationEntryCreator.createAdvancedEntry(SkillList.CLOTHTAILORING, ItemList.needleIron, ItemList.clothString, paintings.targetid+23, false, true, 0.0f, false, false, CreationCategories.FURNITURE);
	    	windowportrait8.addRequirement(new CreationRequirement(1, ItemList.clothYard, 5, true));
	    	windowportrait8.addRequirement(new CreationRequirement(2, ItemList.dye, 1, true));

	    	AdvancedCreationEntry windowportrait9 = CreationEntryCreator.createAdvancedEntry(SkillList.CLOTHTAILORING, ItemList.needleIron, ItemList.clothString, paintings.targetid+24, false, true, 0.0f, false, false, CreationCategories.FURNITURE);
	    	windowportrait9.addRequirement(new CreationRequirement(1, ItemList.clothYard, 5, true));
	    	windowportrait9.addRequirement(new CreationRequirement(2, ItemList.dye, 1, true));

	    	AdvancedCreationEntry windowportrait10 = CreationEntryCreator.createAdvancedEntry(SkillList.CLOTHTAILORING, ItemList.needleIron, ItemList.clothString, paintings.targetid+25, false, true, 0.0f, false, false, CreationCategories.FURNITURE);
	    	windowportrait10.addRequirement(new CreationRequirement(1, ItemList.clothYard, 5, true));
	    	windowportrait10.addRequirement(new CreationRequirement(2, ItemList.dye, 1, true));
	 }

		private void portrait() {
			
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+11, "Portrait Painting 1", "Portrait Painting 1", "superb", "good", "ok", "poor",
						"A picture to put up against a wall.",
						new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.portrait1", 30.0F, 500, (byte)22, 10000, true);
		
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+12, "Portrait Painting 2", "Portrait Painting 2", "superb", "good", "ok", "poor",
						"A picture to put up against a wall.",
						new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.portrait2", 30.0F, 500, (byte)22, 10000, true);
		
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+13, "Portrait Painting 3", "Portrait Painting 3", "superb", "good", "ok", "poor",
						"A picture to put up against a wall.",
						new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.portrait3", 30.0F, 500, (byte)22, 10000, true);
		
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+14, "Portrait Painting 4", "Portrait Painting 4", "superb", "good", "ok", "poor",
						"A picture to put up against a wall.",
						new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.portrait4", 30.0F, 500, (byte)22, 10000, true);
		
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+15, "Portrait Painting 5", "Portrait Painting 5", "superb", "good", "ok", "poor",
						"A picture to put up against a wall.",
						new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.portrait5", 30.0F, 500, (byte)22, 10000, true);
		
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+26, "Portrait Painting 6", "Portrait Painting 6", "superb", "good", "ok", "poor",
						"A picture to put up against a wall.",
						new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.portrait6", 30.0F, 500, (byte)22, 10000, true);
		
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+27, "Portrait Painting 7", "Portrait Painting 7", "superb", "good", "ok", "poor",
						"A picture to put up against a wall.",
						new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.portrait7", 30.0F, 500, (byte)22, 10000, true);
		
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+28, "Portrait Painting 8", "Portrait Painting 8", "superb", "good", "ok", "poor",
						"A picture to put up against a wall.",
						new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.portrait8", 30.0F, 500, (byte)22, 10000, true);
		
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+29, "Portrait Painting 9", "Portrait Painting 9", "superb", "good", "ok", "poor",
						"A picture to put up against a wall.",
						new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.portrait9", 30.0F, 500, (byte)22, 10000, true);
		
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+30, "Portrait Painting 10", "Portrait Painting 10", "superb", "good", "ok", "poor",
						"A picture to put up against a wall.",
						new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.portrait10", 30.0F, 500, (byte)22, 10000, true);
			}catch(IOException e){
				throw new HookException(e);
			}
		
		}
	
	
	private void landscape() {
		try {
			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+1, "Landscape Painting 1", "Landscape Painting 1", "superb", "good", "ok", "poor",
					"A picture to put up against a wall.",
					new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.landscape1", 30.0F, 500, (byte)22, 10000, true);

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+2, "Landscape Painting 2", "Landscape Painting 2", "superb", "good", "ok", "poor",
					"A picture to put up against a wall.",
					new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.landscape2", 30.0F, 500, (byte)22, 10000, true);

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+3, "Landscape Painting 3", "Landscape Painting 3", "superb", "good", "ok", "poor",
					"A picture to put up against a wall.",
					new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.landscape3", 30.0F, 500, (byte)22, 10000, true);

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+4, "Landscape Painting 4", "Landscape Painting 4", "superb", "good", "ok", "poor",
					"A picture to put up against a wall.",
					new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.landscape4", 30.0F, 500, (byte)22, 10000, true);

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+5, "Landscape Painting 5", "Landscape Painting 5", "superb", "good", "ok", "poor",
					"A picture to put up against a wall.",
					new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.landscape5", 30.0F, 500, (byte)22, 10000, true);

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+6, "Landscape Painting 6", "Landscape Painting 6", "superb", "good", "ok", "poor",
					"A picture to put up against a wall.",
					new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.landscape6", 30.0F, 500, (byte)22, 10000, true);

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+7, "Landscape Painting 7", "Landscape Painting 7", "superb", "good", "ok", "poor",
					"A picture to put up against a wall.",
					new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.landscape7", 30.0F, 500, (byte)22, 10000, true);

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+8, "Landscape Painting 8", "Landscape Painting 8", "superb", "good", "ok", "poor",
					"A picture to put up against a wall.",
					new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.landscape8", 30.0F, 500, (byte)22, 10000, true);

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+9, "Landscape Painting 9", "Landscape Painting 9", "superb", "good", "ok", "poor",
					"A picture to put up against a wall.",
					new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.landscape9", 30.0F, 500, (byte)22, 10000, true);

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+10, "Landscape Painting 10", "Landscape Painting 10", "superb", "good", "ok", "poor",
					"A picture to put up against a wall.",
					new short[] { 24, 44, 157, 52 },(short)333, (short)1, 0, 9072000L, 10, 10, 30, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.landscape10", 30.0F, 500, (byte)22, 10000, true);
		}catch(IOException e){
			throw new HookException(e);
		}
	}


	private void newpaintings() {
		try {

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+40, "Framed Landscape", "Framed Landscapes", "superb", "good", "ok", "poor",
					"A framed picture to place against a wall.",
					new short[] { 21, 44, 157, 52, 92, 249, 48, 67 },(short)60, (short)1, 0, 9072000L, 150, 150, 150, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.framedlandscape.", 30.0F, 500, (byte)14, 10000, true).setSecondryItem("Image border");
		}catch(IOException e){
			throw new HookException(e);
		}

        AdvancedCreationEntry framedlandscape = CreationEntryCreator.createAdvancedEntry(SkillList.CARPENTRY_FINE, ItemList.knifeCarving, ItemList.plank, paintings.targetid+40, false, true, 0.0f, false, false, CreationCategories.DECORATION);
        framedlandscape.addRequirement(new CreationRequirement(1, ItemList.plank, 3, true));
        framedlandscape.addRequirement(new CreationRequirement(2, ItemList.nailsIronSmall, 4, true));
        framedlandscape.addRequirement(new CreationRequirement(3, ItemList.dye, 1, true));
        ModelNameProvider framedLandscapeProvider = new paintingsModelProvider("model.furniture.framedlandscape.");
        ModItems.addModelNameProvider(paintings.targetid+40, framedLandscapeProvider);	
		
        try {
	
			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(paintings.targetid+41, "Framed Portrait", "Framed Portraits", "superb", "good", "ok", "poor",
					"A framed picture to place against a wall.",
					new short[] { 21, 44, 157, 52, 92, 249, 48, 67 },(short)60, (short)1, 0, 9072000L, 150, 150, 150, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.framedportraits.", 30.0F, 500, (byte)14, 10000, true).setSecondryItem("Image border");
		}catch(IOException e){
			throw new HookException(e);
		}

	    AdvancedCreationEntry framedportraits = CreationEntryCreator.createAdvancedEntry(SkillList.CARPENTRY_FINE, ItemList.knifeCarving, ItemList.plank, paintings.targetid+41, false, true, 0.0f, false, false, CreationCategories.DECORATION);
	    framedportraits.addRequirement(new CreationRequirement(1, ItemList.plank, 3, true));
	    framedportraits.addRequirement(new CreationRequirement(2, ItemList.nailsIronSmall, 4, true));
	    framedportraits.addRequirement(new CreationRequirement(3, ItemList.dye, 1, true));
	    ModelNameProvider framedportraitProvider = new paintingsModelProvider("model.furniture.framedportraits.");
	    ModItems.addModelNameProvider(paintings.targetid+41, framedportraitProvider);
		}
}