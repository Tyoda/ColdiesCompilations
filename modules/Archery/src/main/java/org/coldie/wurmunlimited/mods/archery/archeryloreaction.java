package org.coldie.wurmunlimited.mods.archery;

import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Actions;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

public class archeryloreaction implements ModAction, BehaviourProvider, ActionPerformer {

	public final short actionId;
	public final ActionEntry actionEntry;

	public archeryloreaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Get archery info", "Getting archery info", new int[]{Actions.ACTION_TYPE_IGNORERANGE});
		ModActions.registerAction(actionEntry);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer,Item source, Creature target) {
		if (performer instanceof Player && source.isWeaponBow())
			return Collections.singletonList(actionEntry);

		return null;
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item source, Creature target, short action, float counter) {
		if (performer instanceof Player && source.isWeaponBow()) {
			int dist = (int)Creature.getRange(performer, target.getPosX(), target.getPosY());
			int ideal = 0;
			switch (source.getTemplateId())
			{
			case 447:
				ideal = 20;
				break;
			case 448:
				ideal = 40;
				break;
			case 449:
				ideal = 80;
				break;
			}
			performer.getCommunicator().sendSafeServerMessage("Distance to mob: "+dist+" meters, ideal is "+ideal);
		}

		return true;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}