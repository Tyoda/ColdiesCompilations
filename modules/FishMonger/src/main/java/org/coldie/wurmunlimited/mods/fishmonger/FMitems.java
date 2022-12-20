package org.coldie.wurmunlimited.mods.fishmonger;

import com.wurmonline.server.MiscConstants;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modsupport.IdFactory;
import org.gotti.wurmunlimited.modsupport.IdType;

import java.io.IOException;

public class FMitems {
	
	public FMitems() {
		try {
			fishmonger.itemtemplate1 = IdFactory.getIdFor("fish monger", IdType.ITEMTEMPLATE);
			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(fishmonger.itemtemplate1, "fish monger", "fish mongers", "almost full", "somewhat occupied", "half-full", "emptyish",
					"Give me fish and you shall be rewarded.",
					new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 86, 178  },(short) 60,(short) 1, 0,
					2419200L, 100, 100, 100, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, fishmonger.itemmodel1, 1.0F,
					2000000,(byte) 15, 100, false);
		}catch(IOException e){ throw new HookException(e); }
	}
}