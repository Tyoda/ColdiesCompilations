package org.coldie.wurmunlimited.mods.unleash;

import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import com.wurmonline.server.zones.Dens;

public class unleash implements WurmServerMod, ServerStartedListener {

	@Override
	public void onServerStarted() {
		Dens.deleteDen(16);
        Dens.deleteDen(89);
        Dens.deleteDen(91);
        Dens.deleteDen(90);
        Dens.deleteDen(92);
        Dens.deleteDen(17);
        Dens.deleteDen(18);
        Dens.deleteDen(19);
        Dens.deleteDen(104);
        Dens.deleteDen(103);
        Dens.deleteDen(20);
        Dens.deleteDen(22);
        Dens.deleteDen(27);
        Dens.deleteDen(11);
        Dens.deleteDen(26);
        Dens.deleteDen(23);		
	}	
}