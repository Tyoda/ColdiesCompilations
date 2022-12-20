package com.wurmonline.server.questions;


import com.wurmonline.server.DbConnector;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.NoSuchActionException;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.utils.DbUtilities;
import net.coldie.tools.BmlForm;
import net.coldie.wurmunlimited.mods.portals.portalmod;
import org.gotti.wurmunlimited.modsupport.ModSupportDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class portaladdlocationquestion extends Question {
	private boolean properlySent = false;

	// private int g = 0;

	public portaladdlocationquestion(Creature aResponder, String aTitle, String aQuestion, long aTarget) {
		super(aResponder, aTitle, aQuestion, 79, aTarget);
	}

	public void answer(Properties answer) {
		if (!properlySent) return;

		// check drop down and accepted
		boolean accepted = (answer.containsKey("accept")) && (answer.get("accept") == "true");
		if (accepted) {
			Connection dbcon = ModSupportDb.getModSupportDb();
			PreparedStatement ps = null;
			try {
				ps = dbcon.prepareStatement("INSERT INTO ColdieGMPortals (name,posx,posy,itemid,bank) VALUES(?,?,?,?,?)");
				ps.setString(1, answer.getProperty("name"));
				ps.setString(2, answer.getProperty("xcoords"));
				ps.setString(3, answer.getProperty("ycoords"));
				ps.setLong(4, portalmod.myMapPortal.get("0"));
				ps.setInt(5, 500);

				ps.executeUpdate();
			} catch (SQLException e) { throw new RuntimeException(e); }
			finally {
				DbUtilities.closeDatabaseObjects(ps, null);
				DbConnector.returnConnection(dbcon);
			}
		}
	}

	public void sendQuestion() {
		boolean ok = false;
		try {
			Action act = getResponder().getCurrentAction();
			if (act.getNumber() == portalmod.portalAddLocationId)
				ok = true;
		} catch (NoSuchActionException act) { throw new RuntimeException("No such action", act); }

		if (ok) {
			properlySent = true;

			BmlForm f = new BmlForm("");

			f.addHidden("id", id+"");
			f.addBoldText(getQuestion());
			f.addText("\n ");

			f.addText("Insert name here\t");
			f.addInput("name", 20, "");
			f.addText("\n");
			f.addText("Insert x coords here\t");
			f.addInput("xcoords", 10, (getResponder().getPosX()/4)+"");
			f.addText("\n");
			f.addText("Insert y coords here\t");
			f.addInput("ycoords", 10, (getResponder().getPosY()/4)+"");
			f.addText("\n");

			f.addText("\n");
			f.beginHorizontalFlow();
			f.addButton("Insert now", "accept");
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