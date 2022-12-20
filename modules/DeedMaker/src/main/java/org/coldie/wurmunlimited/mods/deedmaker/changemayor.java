package org.coldie.wurmunlimited.mods.deedmaker;


import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.villages.NoSuchRoleException;
import com.wurmonline.server.villages.NoSuchVillageException;
import com.wurmonline.server.villages.Village;
import com.wurmonline.server.villages.Villages;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class changemayor implements ModAction, BehaviourProvider, ActionPerformer {
	public static final Logger logger = Logger.getLogger(changemayor.class.getName());

	public final short actionId;
	public final ActionEntry actionEntry;

	public changemayor() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Make GM Mayor", "Making GM Mayor", new int[]{}); 
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		if (performer instanceof Player && performer.getPower() >= 5 && target.getTemplateId() == 663)
			return Collections.singletonList(actionEntry);

		return null;
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		return action(act, performer, target, action, counter);
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item target, short action, float counter) {
		if (performer instanceof Player && performer.getPower() >= 5 && target.getTemplateId() == 663)
			makeDeed(performer, target);

		return true;
	}

	private void makeDeed(Creature performer, Item target) {
		try {
			int data = target.getData2();
			Village village = Villages.getVillage(data);
			village.addCitizen(performer, village.getRoleForStatus((byte)2));
			village.setMayor(performer.getName());
			
		} catch (NoSuchVillageException | IOException | NoSuchRoleException e) {
			logger.log(Level.WARNING, "Failed to change mayor: " + e.getMessage(), e);
		}
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}