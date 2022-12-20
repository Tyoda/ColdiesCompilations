package org.coldie.wurmunlimited.mods.fishmonger;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemTypes;
import com.wurmonline.server.players.Player;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

public class loadrodaction implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer {

	public final short actionId;
	public final ActionEntry actionEntry;

	public loadrodaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Load Rod", "Loading Rod", new int[0]);
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		int clicked = target.getTemplateId();
		if (performer instanceof Player && clicked == 1346)
			return Collections.singletonList(actionEntry);

		return null;
	}

	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		int clicked = target.getTemplateId();
		if (performer instanceof Player ) {
			if (clicked == 1346) {
				Item[] fishingItems = target.getFishingItems();
				Item fishingReel = fishingItems[0];
				Item fishingLine = fishingItems[1];
				Item fishingFloat = fishingItems[2];
				Item fishingHook = fishingItems[3];
				Item fishingBait = fishingItems[4];	

				if (fishingReel == null) {
					fishmonger.logger.warning("fishing reel was null");
		        }				
				if (fishingLine == null) {
					fishmonger.logger.warning("fishing line was null");
		        }
		        if (fishingFloat == null) {
					fishmonger.logger.warning("fishing float was null");
		        }
		        if (fishingHook == null) {
					fishmonger.logger.warning("fishing hook was null");
		        }
		        if (fishingBait == null) {
					fishmonger.logger.warning("fishing bait was null");
		        }
			}
		}

		return true;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}