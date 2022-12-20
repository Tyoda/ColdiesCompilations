package net.coldie.wurmunlimited.mods.dungeons;

import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.items.ItemTypes;

import java.io.IOException;

public class dungeonitems implements WurmServerMod, ItemTypes, MiscConstants {
	
	public dungeonitems() {
		try {

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.itemtemplate1, "Dungeon Merchant1", "Dungeon Merchant1", "almost full", "somewhat occupied", "half-full", "emptyish",
					"This will let you spend your hard earned "+dungeonmain.currencyname1,
					new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 86, 178  },(short) 60,(short) 1, 0, 2419200L, 100, 100, 100, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, dungeonmain.itemmodel1, 1.0F, 2000000,(byte) 15, 100, false);
		}catch(IOException e){
			
		}
		
		try {

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.itemtemplate2, "Dungeon Merchant2", "Dungeon Merchant2", "almost full", "somewhat occupied", "half-full", "emptyish",
					"This will let you spend your hard earned "+dungeonmain.currencyname2,
					new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 86, 178  },(short) 60,(short) 1, 0, 2419200L, 100, 100, 100, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, dungeonmain.itemmodel2, 1.0F, 2000000,(byte) 15, 100, false);
		}catch(IOException e){
			
		}		
		
		try {

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.itemtemplate3, "Dungeon Merchant3", "Dungeon Merchant3", "almost full", "somewhat occupied", "half-full", "emptyish",
					"This will let you spend your hard earned "+dungeonmain.currencyname3,
					new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 86, 178  },(short) 60,(short) 1, 0, 2419200L, 100, 100, 100, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, dungeonmain.itemmodel3, 1.0F, 2000000,(byte) 15, 100, false);
		}catch(IOException e){
			
		}		
		
		try {

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.itemtemplate4, "Dungeon Merchant4", "Dungeon Merchant4", "almost full", "somewhat occupied", "half-full", "emptyish",
					"This will let you spend your hard earned "+dungeonmain.currencyname4,
					new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 86, 178  },(short) 60,(short) 1, 0, 2419200L, 100, 100, 100, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, dungeonmain.itemmodel4, 1.0F, 2000000,(byte) 15, 100, false);
		}catch(IOException e){
			
		}
		
		try {

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.itemtemplate5, "Dungeon Merchant5", "Dungeon Merchant5", "almost full", "somewhat occupied", "half-full", "emptyish",
					"This will let you spend your hard earned "+dungeonmain.currencyname5,
					new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 86, 178  },(short) 60,(short) 1, 0, 2419200L, 100, 100, 100, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, dungeonmain.itemmodel5, 1.0F, 2000000,(byte) 15, 100, false);
		}catch(IOException e){
			
		}
		
		try {

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.itemtemplate6, "Dungeon Merchant6", "Dungeon Merchant6", "almost full", "somewhat occupied", "half-full", "emptyish",
					"This will let you spend your hard earned "+dungeonmain.currencyname6,
					new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 86, 178  },(short) 60,(short) 1, 0, 2419200L, 100, 100, 100, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, dungeonmain.itemmodel6, 1.0F, 2000000,(byte) 15, 100, false);
		}catch(IOException e){
			
		}
		
		try {

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.itemtemplate7, "Dungeon Merchant7", "Dungeon Merchant7", "almost full", "somewhat occupied", "half-full", "emptyish",
					"This will let you spend your hard earned "+dungeonmain.currencyname7,
					new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 86, 178  },(short) 60,(short) 1, 0, 2419200L, 100, 100, 100, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, dungeonmain.itemmodel7, 1.0F, 2000000,(byte) 15, 100, false);
		}catch(IOException e){
			
		}
		
		try {

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.itemtemplate8, "Dungeon Merchant8", "Dungeon Merchant8", "almost full", "somewhat occupied", "half-full", "emptyish",
					"This will let you spend your hard earned "+dungeonmain.currencyname8,
					new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 86, 178  },(short) 60,(short) 1, 0, 2419200L, 100, 100, 100, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, dungeonmain.itemmodel8, 1.0F, 2000000,(byte) 15, 100, false);
		}catch(IOException e){
			
		}
		
		try {

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.itemtemplate9, "Dungeon Merchant9", "Dungeon Merchant9", "almost full", "somewhat occupied", "half-full", "emptyish",
					"This will let you spend your hard earned "+dungeonmain.currencyname9,
					new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 86, 178  },(short) 60,(short) 1, 0, 2419200L, 100, 100, 100, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, dungeonmain.itemmodel9, 1.0F, 2000000,(byte) 15, 100, false);
		}catch(IOException e){
			
		}
		
		try {

			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.itemtemplate10, "Dungeon Merchant10", "Dungeon Merchant10", "almost full", "somewhat occupied", "half-full", "emptyish",
					"This will let you spend your hard earned "+dungeonmain.currencyname10,
					new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 86, 178  },(short) 60,(short) 1, 0, 2419200L, 100, 100, 100, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, dungeonmain.itemmodel10, 1.0F, 2000000,(byte) 15, 100, false);
		}catch(IOException e){
			
		}
		
		try {
		com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.statuettetemplate1, "statuette of "+dungeonmain.currencyname1, "statuettes of "+dungeonmain.currencyname1, "superb", "good", "ok", "poor", "A finely carved statuette.", new short[]{108, 52, 22, 44, 87, 92, 157},(short) 282,(short) 1, 0, 2419200, 3, 5, 20, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.decoration.statuette.", 20.0f, 1000, (byte) 0, 10000, true);
	}catch(IOException e){
		
	}      		
		
		try {
		com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.statuettetemplate2, "statuette of "+dungeonmain.currencyname2, "statuettes of "+dungeonmain.currencyname2, "superb", "good", "ok", "poor", "A finely carved statuette.", new short[]{108, 52, 22, 44, 87, 92, 157},(short) 282,(short) 1, 0, 2419200, 3, 5, 20, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.decoration.statuette.", 20.0f, 1000, (byte) 0, 10000, true);
	}catch(IOException e){
		
	} 
		try {
		com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.statuettetemplate3, "statuette of "+dungeonmain.currencyname3, "statuettes of "+dungeonmain.currencyname3, "superb", "good", "ok", "poor", "A finely carved statuette.", new short[]{108, 52, 22, 44, 87, 92, 157},(short) 282,(short) 1, 0, 2419200, 3, 5, 20, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.decoration.statuette.", 20.0f, 1000, (byte) 0, 10000, true);
	}catch(IOException e){
		
	} 
		try {
		com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.statuettetemplate4, "statuette of "+dungeonmain.currencyname4, "statuettes of "+dungeonmain.currencyname4, "superb", "good", "ok", "poor", "A finely carved statuette.", new short[]{108, 52, 22, 44, 87, 92, 157},(short) 282,(short) 1, 0, 2419200, 3, 5, 20, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.decoration.statuette.", 20.0f, 1000, (byte) 0, 10000, true);
	}catch(IOException e){
		
	} 
		try {
		com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.statuettetemplate5, "statuette of "+dungeonmain.currencyname5, "statuettes of "+dungeonmain.currencyname5, "superb", "good", "ok", "poor", "A finely carved statuette.", new short[]{108, 52, 22, 44, 87, 92, 157},(short) 282,(short) 1, 0, 2419200, 3, 5, 20, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.decoration.statuette.", 20.0f, 1000, (byte) 0, 10000, true);
	}catch(IOException e){
		
	} 
		try {
		com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.statuettetemplate6, "statuette of "+dungeonmain.currencyname6, "statuettes of "+dungeonmain.currencyname6, "superb", "good", "ok", "poor", "A finely carved statuette.", new short[]{108, 52, 22, 44, 87, 92, 157},(short) 282,(short) 1, 0, 2419200, 3, 5, 20, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.decoration.statuette.", 20.0f, 1000, (byte) 0, 10000, true);
	}catch(IOException e){
		
	} 
		try {
		com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.statuettetemplate7, "statuette of "+dungeonmain.currencyname7, "statuettes of "+dungeonmain.currencyname7, "superb", "good", "ok", "poor", "A finely carved statuette.", new short[]{108, 52, 22, 44, 87, 92, 157},(short) 282,(short) 1, 0, 2419200, 3, 5, 20, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.decoration.statuette.", 20.0f, 1000, (byte) 0, 10000, true);
	}catch(IOException e){
		
	} 
		try {
		com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.statuettetemplate8, "statuette of "+dungeonmain.currencyname8, "statuettes of "+dungeonmain.currencyname8, "superb", "good", "ok", "poor", "A finely carved statuette.", new short[]{108, 52, 22, 44, 87, 92, 157},(short) 282,(short) 1, 0, 2419200, 3, 5, 20, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.decoration.statuette.", 20.0f, 1000, (byte) 0, 10000, true);
	}catch(IOException e){
		
	} 
		try {
		com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.statuettetemplate9, "statuette of "+dungeonmain.currencyname9, "statuettes of "+dungeonmain.currencyname9, "superb", "good", "ok", "poor", "A finely carved statuette.", new short[]{108, 52, 22, 44, 87, 92, 157},(short) 282,(short) 1, 0, 2419200, 3, 5, 20, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.decoration.statuette.", 20.0f, 1000, (byte) 0, 10000, true);
	}catch(IOException e){
		
	} 
		try {
		com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dungeonmain.statuettetemplate10, "statuette of "+dungeonmain.currencyname10, "statuettes of "+dungeonmain.currencyname10, "superb", "good", "ok", "poor", "A finely carved statuette.", new short[]{108, 52, 22, 44, 87, 92, 157},(short) 282,(short) 1, 0, 2419200, 3, 5, 20, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.decoration.statuette.", 20.0f, 1000, (byte) 0, 10000, true);
	}catch(IOException e){
		
	} 
 
	}	
}