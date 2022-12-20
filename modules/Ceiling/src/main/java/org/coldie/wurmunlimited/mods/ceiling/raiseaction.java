package org.coldie.wurmunlimited.mods.ceiling;

import com.wurmonline.server.FailedException;
import com.wurmonline.server.Items;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.structures.Floor;
import com.wurmonline.server.zones.NoSuchZoneException;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zone;
import com.wurmonline.server.zones.Zones;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

public class raiseaction implements ModAction, BehaviourProvider, ActionPerformer {

	public final short actionId;
	public final ActionEntry actionEntry;

	public raiseaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Place Ceiling", "Placing Ceiling", new int[0]);
		ModActions.registerAction(actionEntry);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		return getBehavioursFor(performer, target);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {

		if (performer instanceof Player) {
			if (target.getTemplateId() == ceiling.getInstance().itemtemplate1) {
				return Collections.singletonList(actionEntry);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		return action(act, performer, target, action, counter);
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item target, short action, float counter) {
		VolaTile tile = performer.getCurrentTile();
		ceiling ceil = ceiling.getInstance();
		if (target.getTemplateId() == ceil.itemtemplate1) {
				if(hasRoof(performer.getCurrentTile(),performer) 
				&& target.getOwnerId() == performer.getWurmId()
				&& !ceilingExists(tile, performer)) {
					Zone zone;
					Item newItem;
					try {
						newItem = ItemFactory.createItem(ceil.itemtemplate1,
								target.getQualityLevel(), fixCoords(tile.getPosX()), fixCoords(tile.getPosY()), 0.0f, performer
						        .isOnSurface(), (byte) 0, -10L, null);
						newItem.setLastOwnerId(performer.getWurmId());
						zone = Zones.getZone(target.getTileX(), target.getTileY(), target.isOnSurface());
			            zone.removeItem(newItem, true, true);
						newItem.setPos(fixCoords(tile.getPosX()), fixCoords(tile.getPosY()),performer.getPositionZ()+3f,0.0f,target.onBridge());
			            newItem.setMaterial(target.getMaterial());
						zone.addItem(newItem, true, false, false);
			            performer.getCommunicator().sendSafeServerMessage("You put the ceiling in place above your head.");
						Items.destroyItem(target.getWurmId());
					} catch (NoSuchTemplateException | FailedException | NoSuchZoneException e) {
						e.printStackTrace();
					}
				}
				return true;
			}
			return false;
	}
	
    private boolean hasRoof(VolaTile tile,Creature performer)
    {
        Floor[] floors = tile.getFloors();
        for (Floor floor : floors) {
            if (floor.isRoof()) {
            	if(floor.getFloorLevel()-1 != performer.getFloorLevel()) {
            		performer.getCommunicator().sendSafeServerMessage("You are not on the right level to put in a ceiling");
            		return false;
            	}
                return true;
            }
        }
        performer.getCommunicator().sendSafeServerMessage("You need a roof to be able to place a ceiling.");
        return false;
    }
    
    private float fixCoords(float coord) {
    	coord = (float) (Math.ceil(coord/4)*4)-0.01f;
    	return coord;
    }
    
    private boolean ceilingExists(VolaTile tile, Creature performer) {
    	Item[] items = tile.getItems();
		for (Item item : items) {
			if ((item.getTemplateId() == ceiling.getInstance().itemtemplate1)) {
				performer.getCommunicator().sendSafeServerMessage("Already a ceiling here.");
				return true;
			}
		}
    	return false;
    }

	@Override
	public short getActionId() {
		return actionId;
	}
}