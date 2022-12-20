package org.coldie.wurmunlimited.mods.Dyemaker;


import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.MethodsItems;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.questions.extractdyequestion;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

public class removedye implements ModAction, BehaviourProvider, ActionPerformer {
	// public static final Logger logger = Logger.getLogger(removedye.class.getName());

	public final short actionId;
	public final ActionEntry actionEntry;

	public removedye() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Extract dye", "Extracting dye", new int[]{}); 
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		if (performer instanceof Player && target.getTemplateId() == Dyemaker.targetid && source.isContainerLiquid() &&
				!MethodsItems.checkIfStealing(target, performer, null) && target.mayAccessHold(performer))
			return Collections.singletonList(actionEntry);

		return null;
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		if (performer instanceof Player && target.getTemplateId() == Dyemaker.targetid && source.isContainerLiquid() &&
			!MethodsItems.checkIfStealing(target, performer, null) && target.mayAccessHold(performer)) {
			if(!source.isEmpty(true)) {
				performer.getCommunicator().sendSafeServerMessage("Container needs to be completely empty.");
			}else {

				if (target.getData1() == -1) {
					Dyemaker.getdata(target);
				}
				try {
					extractdyequestion aq = new extractdyequestion(
							performer,
							"Extract Dye",
							"What dye combination would you like to Extract??\n\n",
							performer.getWurmId()
					);

					Dyemaker.act = act;
					Dyemaker.performer = performer;
					Dyemaker.source = source;
					Dyemaker.item = target;
					aq.sendQuestion();
				} catch (Exception e) { throw new RuntimeException(e); }
			}
		}

		return true;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}