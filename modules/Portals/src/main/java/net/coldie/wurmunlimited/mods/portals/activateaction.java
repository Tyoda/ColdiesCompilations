package net.coldie.wurmunlimited.mods.portals;

import com.wurmonline.server.DbConnector;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.questions.activatequestion;
import com.wurmonline.server.utils.DbUtilities;
import org.gotti.wurmunlimited.modsupport.ModSupportDb;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class activateaction implements ModAction, BehaviourProvider, ActionPerformer {
	public final short actionId;
	public final ActionEntry actionEntry;

	public activateaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Activate Portal", "Activating Portal", new int[0]);
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		return getBehavioursFor(performer, target);
	}

	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {
		if (performer instanceof Player) {
			if (portalmod.checkaction(target)) {
				Connection dbcon = ModSupportDb.getModSupportDb();
				PreparedStatement ps = null;
				ResultSet rs = null;
				boolean found = false;
				try {
					ps = dbcon.prepareStatement("SELECT * FROM ColdiePortals");
					rs = ps.executeQuery();
					while (rs.next()) {
						if (target.getWurmId() ==  rs.getLong("itemid"))
							found = true;
					}
				} catch (SQLException e) { throw new RuntimeException(e); }
				finally {
					DbUtilities.closeDatabaseObjects(ps, rs);
					DbConnector.returnConnection(dbcon);
				}

				dbcon = ModSupportDb.getModSupportDb();
				ps = null;
				rs = null;
				try {
					ps = dbcon.prepareStatement("SELECT * FROM ColdieGMPortals");
					rs = ps.executeQuery();
					while (rs.next()) {
						if (target.getWurmId() ==  rs.getLong("itemid"))
							found = true;
					}
				}
				catch (SQLException e) { throw new RuntimeException(e); }
				finally {
					DbUtilities.closeDatabaseObjects(ps, rs);
					DbConnector.returnConnection(dbcon);
				}

				if (!found){
					portalmod.myMapPortal.clear();
					portalmod.myMapPortal.put("0", target.getWurmId());
					return Collections.singletonList(actionEntry);
				}
			}
		}

		return null;
	}

	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		return action(act, performer, target, action, counter);
	}

	@Override
	public boolean action(Action act, Creature performer, Item target, short action, float counter) {
		if (!portalmod.checkaction(target)) return false;

		activatequestion aq = new activatequestion(
			performer,
			"Portal Activation",
			"Would you like to activate this portal??\n\n",
			performer.getWurmId()
		);
		aq.sendQuestion();

		return true;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}