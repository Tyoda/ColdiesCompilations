package net.coldie.wurmunlimited.mods.eventportals;

import com.wurmonline.server.MiscConstants;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modsupport.IdFactory;
import org.gotti.wurmunlimited.modsupport.IdType;

import java.io.IOException;

public class eventitems {
	public eventitems() {
		try {
			eventmod.setEventPortalTemplateId(IdFactory.getIdFor("event portal", IdType.ITEMTEMPLATE));
			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(eventmod.getEventPortalTemplateId(), "Event Portal", "portals", "almost full", "somewhat occupied", "half-full", "emptyish",
					"This portal will send you home from the event.",
					new short[] { 108, 25, 49, 31, 52, 44, 48, 67, 51, 86, 178 },(short) 60,(short) 1, 0,
					2419200L, 500, 500, 1000, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.structure.portal.8.", 1.0F,
					2000000,(byte) 15, 100, false);
		}catch(IOException e){ throw new HookException(e); }
	}	
}