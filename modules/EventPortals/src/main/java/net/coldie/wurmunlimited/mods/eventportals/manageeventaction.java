package net.coldie.wurmunlimited.mods.eventportals;

import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.questions.manageeventquestion;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

public class manageeventaction implements ModAction, BehaviourProvider, ActionPerformer {

	public final short actionId;
	static ActionEntry actionEntry;

	public manageeventaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Manage Event Teleport", "Manage Event Teleporting", new int[]{ 
				}); 
		ModActions.registerAction(actionEntry);
	}

	@Override
	public short getActionId() {
		return actionId;
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		return getBehavioursFor(performer, target);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {
		int clicked = target.getTemplateId();
		// add check for if event active
		if (performer instanceof Player && performer.getPower() >= 4 && (clicked == 236 || clicked == eventmod.getEventPortalTemplateId()))
				return Collections.singletonList(actionEntry);

		return null;
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		return action(act, performer, target, action, counter);
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item target, short action, float counter) {
		if (performer.getPower() >= 4){
			try {
				manageeventquestion aq = new manageeventquestion(
						performer,
						"Manage Event Portal Menu",
						"Activate or deactivate event here\n\n",
						performer.getWurmId()
				);

				aq.sendQuestion();
			} catch (Exception e) { eventmod.logger.warning(e.toString()); }
		}

		return true;
	}
}