package net.coldie.wurmunlimited.mods.decorations;

import java.util.Properties;
import java.util.logging.Logger;


import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.vehicles.ModVehicleBehaviour;
import org.gotti.wurmunlimited.modsupport.vehicles.ModVehicleBehaviours;
import org.gotti.wurmunlimited.modsupport.vehicles.VehicleFacade;

import com.wurmonline.server.behaviours.Vehicle;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;



public class decorations implements WurmServerMod, Configurable, ItemTemplatesCreatedListener, ServerStartedListener, Initable, Versioned {
	public static final Logger logger = Logger.getLogger(decorations.class.getName());
    public static final String version = "ty1.0";

    private int chessChairId = 0;

    private static decorations instance;

    public decorations(){
        instance = this;
    }

    @Override
	public void onItemTemplatesCreated() {
		new decorationitems();
		
	}

	@Override
	public void configure(Properties arg0) {
		
	}

	@Override
	public void onServerStarted() {
		
	}

	@Override
	public void init() {

        ModVehicleBehaviours.addItemVehicle(chessChairId, new ModVehicleBehaviour() {
        	
            @Override
            public void setSettingsForVehicle(Item item, Vehicle vehicle) {
                VehicleFacade facade = wrap(vehicle);
                facade.setCreature(false);
                vehicle.setMaxHeight(2500f);
                facade.createOnlyPassengerSeats(1);
                vehicle.setSeatFightMod(0, 1.0F, 0.4F);
                vehicle.setSeatOffset(0, 0.0F, 0.0F, 0.63F);
                vehicle.setChair(true);
                facade.setCommandType((byte) 2);
                facade.setName(item.getName());
                facade.setMaxDepth(-0.7F);
                facade.setMaxHeightDiff(0.04F);
            }

			@Override
			public void setSettingsForVehicle(Creature arg0, Vehicle arg1) {
				
			}

        });		
	}

    public String getVersion() {
        return version;
    }

    public int getChessChairId() {
        return chessChairId;
    }

    public void setChessChairId(int chessChairId) {
        this.chessChairId = chessChairId;
    }

    public static decorations getInstance() {
        return instance;
    }
}