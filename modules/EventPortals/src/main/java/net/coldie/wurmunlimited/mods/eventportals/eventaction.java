package net.coldie.wurmunlimited.mods.eventportals;


import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.questions.playerteleportquestion;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

public class eventaction implements ModAction, BehaviourProvider, ActionPerformer {

	public final short actionId;
	public final ActionEntry actionEntry;

	public eventaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Event Teleport", "Event Teleporting", new int[0]);
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		return getBehavioursFor(performer, target);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {
		int clicked = target.getTemplateId();
		// add check for if event active
		if (performer instanceof Player && clicked == 236 && eventmod.activated)
			return Collections.singletonList(actionEntry);

		return null;
	}

	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		return action(act, performer, target, action, counter);
	}

	@Override
	public boolean action(Action act, Creature performer, Item target, short action, float counter) {
		if (eventmod.activated) {
			try {
				playerteleportquestion aq = new playerteleportquestion(
						performer,
						"Event Portal Menu",
						"Would you like to teleport to the event?\n\n",
						performer.getWurmId()
				);

				aq.sendQuestion();
			} catch (Exception e) { eventmod.logger.warning(e.toString()); }
		}

		return true;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}