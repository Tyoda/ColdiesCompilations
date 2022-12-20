package org.coldie.wurmunlimited.mods.deedmaker;


import com.wurmonline.server.*;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.NoSuchCreatureException;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.villages.NoSuchRoleException;
import com.wurmonline.server.villages.Village;
import com.wurmonline.server.villages.Villages;
import com.wurmonline.server.zones.Zones;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class deedmakeraction implements ModAction, BehaviourProvider, ActionPerformer {
	public static final Logger logger = Logger.getLogger(deedmakeraction.class.getName());

	public final short actionId;
	public final ActionEntry actionEntry;

	public deedmakeraction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Make GM Deed", "Making GM Deed", new int[0]);
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		if (performer instanceof Player && performer.getPower() >= 5 && target.getTemplateId() == 862)
			return Collections.singletonList(actionEntry);

		return null;
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		return action(act, performer, target, action, counter);
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item target, short action, float counter) {
		if (performer instanceof Player && performer.getPower() >= 5 && target.getTemplateId() == 862)
			makeDeed(performer, target);

		return true;
	}

	private void makeDeed(Creature performer, Item target) {
		try {
        	final Random recipeRandom = new Random();
        	int deedNum = recipeRandom.nextInt(89);
        	int playerX = (int) (performer.getPosX()/4);
        	int playerY = (int) (performer.getPosY()/4);
            Village v = Villages.createVillage(Zones.safeTileX(playerX - 5), Zones.safeTileX(playerX + 5), Zones.safeTileY(playerY - 5), Zones.safeTileY(playerY + 5), playerX, playerY, "GM Deed " + deedNum, performer, target.getWurmId(), true, false, "Loves ya baby", false, (byte)0, 0);
            deedmakeraction.logger.log(Level.INFO, performer.getName() + " founded GM Deed");
            v.addCitizen(performer, v.getRoleForStatus((byte)2));
            Server.getInstance().broadCastSafe(WurmCalendar.getTime(), false);
            Server.getInstance().broadCastSafe("The settlement of GM Deed " + deedNum + " has just been founded by " + performer.getName() + ".");
            performer.getCommunicator().sendSafeServerMessage("The settlement of GM Deed " + deedNum +" has been founded according to your specifications!");
            v.setIsHighwayAllowed(v.hasHighway());
            v.plan.updateGuardPlan(0, (long)(deedmaker.getInstance().getGoldUpkeep() * 1000000L), 0);
		} catch (FailedException fe) {
		   logger.log(Level.WARNING, "Failed to create settlement: " + fe.getMessage(), fe);
			performer.getCommunicator().sendSafeServerMessage(fe.getMessage());
		}
		catch (NoSuchPlayerException | NoSuchCreatureException | IOException e) {
			logger.log(Level.WARNING, "Failed to create settlement: " + e.getMessage(), e);
			performer.getCommunicator().sendNormalServerMessage("Failed to locate a resource needed for that request. Please contact administrator.");
		}
		catch (NoSuchRoleException nsr) {
			logger.log(Level.WARNING, "Failed to create settlement:" + nsr.getMessage(), nsr);
			performer.getCommunicator().sendNormalServerMessage("Failed to locate a role needed for that request. Please contact administrator.");
		}
		catch (NoSuchItemException nsi) {
			logger.log(Level.WARNING, "Failed to create settlement:" + nsi.getMessage(), nsi);
			performer.getCommunicator().sendNormalServerMessage("Failed to locate the deed. The operation was aborted.");
		}
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}