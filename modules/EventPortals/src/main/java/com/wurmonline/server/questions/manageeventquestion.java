package com.wurmonline.server.questions;


import com.wurmonline.server.DbConnector;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.NoSuchActionException;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.utils.DbUtilities;
import net.coldie.tools.BmlForm;
import net.coldie.wurmunlimited.mods.eventportals.eventmod;
import org.gotti.wurmunlimited.modsupport.ModSupportDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class manageeventquestion extends Question {
	private boolean properlySent = false;

	manageeventquestion(Creature aResponder, String aTitle, String aQuestion, int aType, long aTarget) {
		super(aResponder, aTitle, aQuestion, aType, aTarget);
	}

	public manageeventquestion(Creature aResponder, String aTitle, String aQuestion, long aTarget) {
		super(aResponder, aTitle, aQuestion, 79, aTarget);
	}

	public void answer(Properties answer) {
		if (!properlySent) return;

		// check drop down and accepted
		boolean accepted = (answer.containsKey("accept")) && (answer.get("accept") == "true");

		if (accepted) {
			String val = answer.getProperty("event");
			if (val == null || val.equals("eventnzenny")){
				eventmod.activated = true;
				eventmod.zennyactivated = true;
				Connection dbcon = ModSupportDb.getModSupportDb();
				PreparedStatement ps = null;
				try {
					ps = dbcon.prepareStatement(
							"UPDATE ColdieEventPortals SET posx=?, posy=?, layer=?, floorlevel=? WHERE id=1"
					);
					ps.setFloat(1, getResponder().getPosX());
					ps.setFloat(2, getResponder().getPosY());
					ps.setInt(3, getResponder().getLayer());
					ps.setInt(4, getResponder().getFloorLevel());
					ps.executeUpdate();

					getResponder().getCommunicator().sendNormalServerMessage("Activating event portal");
					Server.getInstance().broadCastAlert("Event Portal Activated", false, (byte)1);
					eventmod.active = true;
				}
				catch (SQLException e) { throw new RuntimeException(e); }
				finally{
					DbUtilities.closeDatabaseObjects(ps, null);
					DbConnector.returnConnection(dbcon);
				}
			}else{
				eventmod.zennyactivated = true;
			}
		}else{
			eventmod.activated = false;
			eventmod.zennyactivated = false;
			Connection dbcon = ModSupportDb.getModSupportDb();
			PreparedStatement ps = null;
			try {
				ps = dbcon.prepareStatement("UPDATE ColdieEventPortals SET posx = 1 WHERE id = 1");
				ps.executeUpdate();

				getResponder().getCommunicator().sendNormalServerMessage("Deactivating event portal");
				Server.getInstance().broadCastAlert("Event Portal Deactivated", false, (byte)1);
				eventmod.active = false;
			}
			catch (SQLException e) { throw new RuntimeException(e); }
			finally{
				DbUtilities.closeDatabaseObjects(ps, null);
				DbConnector.returnConnection(dbcon);
			}
		}
	}


	public void sendQuestion() {
		boolean ok = false;

		try {
			Action act = getResponder().getCurrentAction();
			if (act.getNumber() == eventmod.getManageEventActionId()) {
				ok = true;
			}
		}
		catch (NoSuchActionException e) { throw new RuntimeException("No such action", e); }

		if (ok) {
			properlySent = true;

			BmlForm f = new BmlForm("");

			f.addHidden("id", id+"");
			f.addBoldText(getQuestion());
			f.addText("\n ");

			f.addRaw("radio{ group='event'; id='eventnzenny';text='Activate event'}");

			f.addText("\n\n\n\n ");
			f.beginHorizontalFlow();
			f.addButton("Start Event", "accept");
			f.addText("               ");
			f.addButton("End Event", "decline");


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