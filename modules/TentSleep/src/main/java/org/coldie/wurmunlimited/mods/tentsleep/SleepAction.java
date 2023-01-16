package org.coldie.wurmunlimited.mods.tentsleep;


import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemTypes;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.questions.TentSleepQuestion;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class SleepAction implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer {
	public static short actionId;
	static ActionEntry actionEntry;
   
	public SleepAction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Sleep", "sleeping", new int[0]);
		ModActions.registerAction(actionEntry);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		return getBehavioursFor(performer, target);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {
		if (performer instanceof Player
				&& target.isOwner(performer)
				&& target.getParentId() == -10
				&& (target.isTent() || target.getTemplateId() == 1313)) {
			return Collections.singletonList(actionEntry);
		}
		return null;
	}

	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		return action(act, performer, target, action, counter);
	}

	@Override
	public boolean action(Action act, Creature performer, Item target, short action, float counter) {
		return performAction(performer, target);
	}


	public boolean performAction(Creature performer, Item target){
		if (performer instanceof Player
				&& target.isOwner(performer)
				&& target.getParentId() == -10
				&& (target.isTent() || target.getTemplateId() == 1313)) {
			try {
				TentSleepQuestion aq = new TentSleepQuestion(
						performer,
						"Sleep Activation",
						"You seriously want to sleep here??\n\n",
						target
				);
				aq.sendQuestion();
			} catch (Exception e) {
				TentSleep.logger.log(Level.SEVERE, "Exception while sleep question: ", e);
			}
		}
		return true;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}