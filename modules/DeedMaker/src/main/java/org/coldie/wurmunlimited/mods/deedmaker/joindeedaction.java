package org.coldie.wurmunlimited.mods.deedmaker;

import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.villages.NoSuchRoleException;
import com.wurmonline.server.villages.Village;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class joindeedaction implements ModAction, BehaviourProvider, ActionPerformer {
	public static final Logger logger = Logger.getLogger(joindeedaction.class.getName());

	public final short actionId;
	public final ActionEntry actionEntry;

	public joindeedaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Join Deed", "Joining Deed", new int[]{}); 
		ModActions.registerAction(actionEntry);
	}

	/*@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {
		return null; // TODO
	}*/

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		if (performer instanceof Player && target.getTemplateId() == deedmaker.getInstance().getInvitorID())
			return Collections.singletonList(actionEntry);

		return null;
	}

	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		return action(act, performer, target, action, counter);
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item target, short action, float counter) {
		if (performer instanceof Player && target.getTemplateId() == deedmaker.getInstance().getInvitorID()) {
		      if (performer.mayChangeVillageInMillis() > 0L) {
		    	  performer.getCommunicator().sendSafeServerMessage("You may not change settlement for another " + 
		          				Server.getTimeFor(performer.mayChangeVillageInMillis()));
		      } else {
				int X = performer.getTileX();
				int Y = performer.getTileY();
				VolaTile vTile = Zones.getOrCreateTile(X, Y, true);
				if (vTile.getVillage() != null) {
					Village village = vTile.getVillage();
					try {
						village.addCitizen(performer, village.getRoleForStatus((byte)3));
					} catch (IOException | NoSuchRoleException e) {
						logger.log(Level.WARNING, "Failed to change mayor: " + e.getMessage(), e);
					}
				}else {
					performer.getCommunicator().sendSafeServerMessage("I am not on a deed at the moment, please contact an administrator.");
				}
		      }
		}

		return true;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}