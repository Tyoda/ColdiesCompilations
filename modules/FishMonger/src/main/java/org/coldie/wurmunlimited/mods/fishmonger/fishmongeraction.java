package org.coldie.wurmunlimited.mods.fishmonger;


import com.wurmonline.server.Items;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.economy.Economy;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class fishmongeraction implements ModAction, BehaviourProvider, ActionPerformer {

	public final short actionId;
	public final ActionEntry actionEntry;

	public fishmongeraction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Sell Fish", "Selling Fish", new int[0]);
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		int clicked = target.getTemplateId();
		if (performer instanceof Player && clicked == fishmonger.itemtemplate1 && (source.isFish() || source.isHollow())) {
			return Collections.singletonList(actionEntry);
		}

		return null;
	}

	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		int clicked = target.getTemplateId();
		if (performer instanceof Player && (clicked == fishmonger.itemtemplate1 && (source.isFish() || source.isHollow()))) {
			if (source.isHollow()) {
				Item[] keepNetItems = source.getAllItems(false);
				int fishPayment = 0;
				int fishCount = 0;
				for (Item keepNetItem : keepNetItems) {
					if (keepNetItem.isFish() && fishmonger.getfishvalue(keepNetItem, performer) != 0) {
						fishPayment = fishPayment + fishmonger.getfishvalue(keepNetItem, performer);
						Items.destroyItem(keepNetItem.getWurmId());
						++fishCount;
					}
				}
				if (fishPayment > 0) {
					try {
						performer.addMoney(fishPayment);
						String bountyMessage = Economy.getEconomy().getChangeFor(fishPayment).getChangeString();
						performer.getCommunicator().sendSafeServerMessage("The Fishmonger puts "+bountyMessage+" into your bank for selling "+fishCount+" fish.");
					} catch (IOException e) {
						fishmonger.logger.warning(e.toString());
					}
				}
			} else {
				sellfish(source,performer);
			}
		}

		return true;
	}
	
	public static void sellfish(Item fish, Creature performer) {
		try {
			if (fishmonger.getfishvalue(fish,performer) != 0) {
				performer.addMoney(fishmonger.getfishvalue(fish,performer));
				String bountyMessage = Economy.getEconomy().getChangeFor(fishmonger.getfishvalue(fish,performer)).getChangeString();
				performer.getCommunicator().sendSafeServerMessage("The Fishmonger puts "+bountyMessage+" into your bank.");
				Items.destroyItem(fish.getWurmId());
			}else {
				performer.getCommunicator().sendSafeServerMessage("That is worthless to me");
			}
		} catch (IOException e) {
			fishmonger.logger.warning(e.toString());
		}
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}