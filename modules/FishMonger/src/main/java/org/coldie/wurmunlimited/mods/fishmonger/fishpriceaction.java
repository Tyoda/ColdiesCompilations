package org.coldie.wurmunlimited.mods.fishmonger;


import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.economy.Economy;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

public class fishpriceaction implements ModAction, BehaviourProvider, ActionPerformer {

	public final short actionId;
	public final ActionEntry actionEntry;

	public fishpriceaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Get Price", "Getting Price", new int[0]);
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		if (performer instanceof Player && target.isFish() && fishmonger.getfishvalue(target,performer) != 0)
			return Collections.singletonList(actionEntry);

		return null;
	}

	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		if (performer instanceof Player && target.isFish() && fishmonger.getfishvalue(target,performer) != 0) {
			String bountyMessage = Economy.getEconomy().getChangeFor(fishmonger.getfishvalue(target,performer)).getChangeString();
			performer.getCommunicator().sendSafeServerMessage("The Fishmonger would pay you "+bountyMessage+".");
		}

		return true;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}