package org.coldie.wurmunlimited.mods.riftevent;

import com.wurmonline.server.Items;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.MethodsReligion;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class riftsacrificeaction implements ModAction, BehaviourProvider, ActionPerformer {
	public static final Logger logger = Logger.getLogger(riftevent.class.getName());
	public final short actionId;
	public final ActionEntry actionEntry;

	public riftsacrificeaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Sacrifice", "Sacrificing", new int[0]);
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		return getBehavioursFor(performer, target);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {
		if (performer instanceof Player && target.getTemplateId() == riftevent.itemtemplate1)
			return Collections.singletonList(actionEntry);

		return null;
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		return action(act, performer, target, action, counter);
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item target, short action, float counter) {
		if(performer instanceof Player && target.getTemplateId() == riftevent.itemtemplate1)
			doAction(performer, target);

		return true;
	}
	
	private void doAction(Creature performer, Item target) {
		Item[] items = target.getAllItems(false);
		if (items.length > 0){
			int value = 0;
			for (Item item : items) {
				if (!MethodsReligion.canBeSacrificed(item)) {
					performer.getCommunicator().sendNormalServerMessage("Anubis does not accept " + item.getNameWithGenus() + ".");
				} else if (item.isBanked()) {
					performer.getCommunicator().sendNormalServerMessage("Anubis does not accept " + item.getNameWithGenus() + ".");
					logger.log(Level.INFO, performer.getName() + " tried to sac banked item!");
				} else if ((!item.isPlacedOnParent()) && (!item.isInsidePlacedContainer())) {
					float newVal = MethodsReligion.getFavorValue(null, item);
					float mod = MethodsReligion.getFavorModifier(null, item);
					value = (int) (value + Math.max(10.0F, newVal * mod));
					Items.destroyItem(item.getWurmId());
				}
			}
			performer.getCommunicator().sendNormalServerMessage("Anubis accepts your offering and increases power by "+value);
			performer.getCommunicator().sendNormalServerMessage("Power needed to spawn are:"+riftevent.lowpowerneeded+":"+riftevent.midpowerneeded+":"+riftevent.highpowerneeded);
			target.setData2(target.getData2() + value);
			target.setName("Altar of Anubis: "+target.getData2());
			target.updateName();
		}else{
			// TODO
		}
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}