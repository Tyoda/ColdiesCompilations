package net.coldie.wurmunlimited.mods.portals;

import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.questions.portaleditquestion;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

public class portaleditaction implements ModAction, BehaviourProvider, ActionPerformer {
	public final short actionId;
	public final ActionEntry actionEntry;

	public portaleditaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Edit Portal", "Editing Portal", new int[0]);
		ModActions.registerAction(actionEntry);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		return getBehavioursFor(performer, target);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {
		if (performer instanceof Player && portalmod.checkGM(performer) && portalmod.checkaction(target))
			return Collections.singletonList(actionEntry);

		return null;
	}

	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		return action(act, performer, target, action, counter);
	}

	@Override
	public boolean action(Action act, Creature performer, Item target, short action, float counter) {
		if (portalmod.checkaction(target) && portalmod.checkGM(performer)) {
			portaleditquestion aq = new portaleditquestion(
					performer,
					"Edit Portal Menu",
					"How would you like to edit portals?\n\n",
					performer.getWurmId()
			);

			aq.sendQuestion();
		}

		return true;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}