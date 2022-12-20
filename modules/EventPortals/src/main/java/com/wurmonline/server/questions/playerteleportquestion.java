package com.wurmonline.server.questions;


import com.wurmonline.server.DbConnector;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.NoSuchActionException;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.utils.DbUtilities;
import net.coldie.tools.BmlForm;
import net.coldie.wurmunlimited.mods.eventportals.eventmod;
import org.gotti.wurmunlimited.modsupport.ModSupportDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class playerteleportquestion extends Question {
	private boolean properlySent = false;

	playerteleportquestion(Creature aResponder, String aTitle, String aQuestion, int aType, long aTarget) {
		super(aResponder, aTitle, aQuestion, aType, aTarget);
	}

	public playerteleportquestion(Creature aResponder, String aTitle, String aQuestion, long aTarget) {
		super(aResponder, aTitle, aQuestion, 79, aTarget);
	}

	public void answer(Properties answer) {
		if (!properlySent) return;

		// check drop down and accepted
		boolean accepted = (answer.containsKey("accept")) && (answer.get("accept") == "true");
		float tx;
		float ty;
		int floorlevel;
		int layer;
		if (accepted) {
			Connection dbcon = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				dbcon = ModSupportDb.getModSupportDb();
				ps = dbcon.prepareStatement("SELECT * FROM ColdieEventPortals WHERE id = 1");
				rs = ps.executeQuery();
				tx = (rs.getFloat("posx"));
				ty = (rs.getFloat("posy"));
				floorlevel = (rs.getInt("floorlevel"));
				layer = (rs.getInt("layer"));
			}
			catch (SQLException e){ throw new RuntimeException(e); }
			finally{
				DbUtilities.closeDatabaseObjects(ps, rs);
				DbConnector.returnConnection(dbcon);
			}

			dbcon = ModSupportDb.getModSupportDb();
			ps = null;
			try {
				ps = dbcon.prepareStatement("UPDATE ColdieEventPortals  SET posx=?, posy=?, layer=?, floorlevel=? WHERE id=?");
				ps.setFloat(1, getResponder().getPosX());
				ps.setFloat(2, getResponder().getPosY());
				ps.setInt(3, getResponder().getLayer());
				ps.setInt(4, getResponder().getFloorLevel());
				ps.setLong(5, getResponder().getWurmId());
				ps.executeUpdate();
			}
			catch (SQLException e) { throw new RuntimeException(e); }
			finally{
				DbUtilities.closeDatabaseObjects(ps, null);
				DbConnector.returnConnection(dbcon);
			}

			getResponder().setTeleportPoints(tx, ty, layer, floorlevel);
			getResponder().startTeleporting();
			getResponder().getCommunicator().sendNormalServerMessage("You feel a slight tingle in your spine.");
			getResponder().getCommunicator().sendTeleport(false);
			getResponder().teleport(true);
			getResponder().stopTeleporting();
		}
	}

	public void sendQuestion() {
		boolean ok = false;


		try {
			Action act = getResponder().getCurrentAction();
			if (act.getNumber() == eventmod.getEventActionId())
				ok = true;
		}
		catch (NoSuchActionException act) { throw new RuntimeException("No such action", act); }

		if (ok) {
			properlySent = true;

			BmlForm f = new BmlForm("");

			f.addHidden("id", id+"");
			f.addBoldText(getQuestion());
			f.addText("\n ");

			f.addText("\n\n\n\n ");
			f.beginHorizontalFlow();
			f.addButton("Teleport now", "accept");
			f.addText("               ");
			f.addButton("Cancel", "decline");


			f.endHorizontalFlow();
			f.addText(" \n");
			f.addText(" \n");

			getResponder().getCommunicator().sendBml(
					300,
					250,
					true,
					true,
					f.toString(),
					150,
					150,
					200,
					title
			);
		}
	}
}