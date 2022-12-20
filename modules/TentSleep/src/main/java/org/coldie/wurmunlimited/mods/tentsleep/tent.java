package org.coldie.wurmunlimited.mods.tentsleep;


import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemTypes;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.questions.tentsleepquestion;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

public class tent implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer {
	public static short actionId;
	static ActionEntry actionEntry;
   
	public tent() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Sleep", "Sleeping", new int[]{ 
				}); 
		ModActions.registerAction(actionEntry);
	}

	@Override
	public short getActionId() {
		return actionId;
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		if (performer instanceof Player ) {
			if ((target.isOwner(performer) && target.getParentId() == -10) && ( target.isTent() || target.getTemplateId() == 1313)) {
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
		if (performer instanceof Player ) {
			if ((target.isOwner(performer) && target.getParentId() == -10) && ( target.isTent() || target.getTemplateId() == 1313)) {
				try {
					tentsleepquestion aq = new tentsleepquestion(
			                performer, 
			                "Sleep Activation", 
			                "You seriously want to sleep here??\n\n", 
			                target.getWurmId());
			              
			              aq.sendQuestion();
					return true;
				} catch (Exception e) {
					return false;
				}			
 
			}
			return true;
		}
		return false;
	}
}