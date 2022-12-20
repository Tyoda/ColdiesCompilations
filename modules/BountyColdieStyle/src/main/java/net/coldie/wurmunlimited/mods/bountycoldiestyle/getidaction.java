package net.coldie.wurmunlimited.mods.bountycoldiestyle;

import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.players.Player;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

public class getidaction implements ModAction, BehaviourProvider, ActionPerformer {

	public final short actionId;
	public final ActionEntry actionEntry;

	public getidaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Get Creature ID", "Getting Creature ID", new int[]{ 
				}); 
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Creature target) {
		if (performer instanceof Player && performer.getPower() > 2 ) {
			return Collections.singletonList(actionEntry);
		} else {
			return null;
		}
	}

	@Override
	public boolean action(Action act, Creature performer, Creature target, short action, float counter) {
		if (performer instanceof Player && performer.getPower() > 2) {
			performer.getCommunicator().sendSafeServerMessage("Template ID of "+target.getName()+" is "+target.getTemplateId()+".");
		}

		return true;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}