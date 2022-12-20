package org.coldie.wurmunlimited.mods.riftevent;

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

public class riftspawnaction implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer {
	// public static final Logger logger = riftevent.logger;
	public final short actionId;
	public final ActionEntry actionEntry;

	public riftspawnaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Spawn Mobs", "Spawning Mobs", new int[0]);
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
		if(performer instanceof Player && target.getTemplateId() == riftevent.itemtemplate1) {
			if (riftevent.spawnCreature(performer, target)) {
				target.setData2(target.getData2() - riftevent.powerused);
				target.setName("Altar of Anubis: "+target.getData2());
				target.updateName();
			}
		}

		return true;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}