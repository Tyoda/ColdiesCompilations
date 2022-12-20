package org.coldie.wurmunlimited.mods.deedmaker;

import com.wurmonline.server.MiscConstants;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modsupport.IdFactory;
import org.gotti.wurmunlimited.modsupport.IdType;

import java.io.IOException;

public class deedmakeritems {
	
	public deedmakeritems() {
		try {
			deedmaker.getInstance().setInvitorID(IdFactory.getIdFor("invitor", IdType.ITEMTEMPLATE));
			com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(deedmaker.getInstance().getInvitorID(),
					"Village Welcomer", "Village Welcomers", "superb", "good", "ok", "poor",
					"Activate any item, right click me and click Join Deed to join this deed.", // TODO: don't have to activate
					new short[]{108, 52, 22, 92, 157, 31, 67, 40, 123},(short) 282,(short) 1, 0,
					2419200, 3, 5, 20, -10,
					MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, deedmaker.getInstance().getInvitorModel(),
					20.0f, 1000, (byte) 0, 10000, true);
		}catch(IOException e){ throw new HookException(e); }
	}
	
}