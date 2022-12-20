package net.coldie.wurmunlimited.mods.eventportals;

//import com.coldie.tools.BmlForm;

import com.wurmonline.server.DbConnector;
import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemTypes;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.questions.playerreturnquestion;
import com.wurmonline.server.utils.DbUtilities;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
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

public class returneventaction implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer {
	public final short actionId;
	public final ActionEntry actionEntry;

	public returneventaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Return Event Teleport", "Return Event Teleporting", new int[0]);
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		return getBehavioursFor(performer, target);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {
		int clicked = target.getTemplateId();
		// add check for if event active
		if (performer instanceof Player && (clicked == 236 || clicked == eventmod.getEventPortalTemplateId())) {
			boolean found = false;

			Connection dbcon = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				dbcon = ModSupportDb.getModSupportDb();
				ps = dbcon.prepareStatement("SELECT id FROM ColdieEventPortals");
				rs = ps.executeQuery();

				while (rs.next()) {
					if (rs.getLong("id") == performer.getWurmId())
						found = true;
				}
			} catch (SQLException e) { throw new RuntimeException(e); }
			finally {
				DbUtilities.closeDatabaseObjects(ps, rs);
				DbConnector.returnConnection(dbcon);
			}

			if (!found){
				dbcon = ModSupportDb.getModSupportDb();
				ps = null;
				try {
					ps = dbcon.prepareStatement("INSERT INTO ColdieEventPortals (id,posx,posy) VALUES(?,?,?)");

					ps.setLong(1, performer.getWurmId());
					ps.setFloat(2, 1f);
					ps.setFloat(3, 1f);

					ps.executeUpdate();
					return null;
				}
				catch (SQLException e) { throw new RuntimeException(e); }
				finally {
					DbUtilities.closeDatabaseObjects(ps, null);
					DbConnector.returnConnection(dbcon);
				}
			}

			dbcon = ModSupportDb.getModSupportDb();
			ps = null;
			rs = null;
			try {
				ps = dbcon.prepareStatement("SELECT posx FROM ColdieEventPortals WHERE id=?");
				ps.setLong(1, performer.getWurmId());
				rs = ps.executeQuery();
				if (rs.getFloat("posx") == 1){
					return null;
				}
			}
			catch (SQLException e) { throw new RuntimeException(e); }
			finally {
				DbUtilities.closeDatabaseObjects(ps, rs);
				DbConnector.returnConnection(dbcon);
			}

			return Collections.singletonList(actionEntry);
		}

		return null;
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		return action(act, performer, target, action, counter);
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item target, short action, float counter) {
		try {
			playerreturnquestion aq = new playerreturnquestion(
					performer,
					"Return from event",
					"Would you like to return from event?\n\n",
					performer.getWurmId()
			);

			aq.sendQuestion();
		} catch (Exception e) { eventmod.logger.info(e.toString()); }

		return true;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}