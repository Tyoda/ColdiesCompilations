package org.coldie.wurmunlimited.mods.Dyemaker;


import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

public class checkdye implements ModAction, BehaviourProvider, ActionPerformer {
	// public static final Logger logger = Logger.getLogger(checkdye.class.getName());

	public final short actionId;
	public final ActionEntry actionEntry;

	public checkdye() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Check dye", "Checking dye", new int[]{}); 
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		if (performer instanceof Player && target.getTemplateId() == Dyemaker.targetid)
			return Collections.singletonList(actionEntry);

		return null;
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		if (performer instanceof Player && target.getTemplateId() == Dyemaker.targetid) {
            if (target.getData1() == -1) {
				Dyemaker.getdata(target);
			}
			final float red = (float)(target.getData1());
			final float green = (float)target.getData2();
			final float blue = (float)target.getExtra1();
			performer.getCommunicator().sendSafeServerMessage("Red Volume:"+red/1000);
			performer.getCommunicator().sendSafeServerMessage("Green Volume:"+green/1000);
			performer.getCommunicator().sendSafeServerMessage("Blue Volume:"+blue/1000);
		}

		return true;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}