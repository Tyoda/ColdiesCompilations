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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;



public class portalquestion extends Question {
	//public HashMap<String, String> myMap = new HashMap<String,String>();

	private boolean properlySent = false;

	public portalquestion(Creature aResponder, String aTitle, String aQuestion, long aTarget) {
		super(aResponder, aTitle, aQuestion, 79, aTarget);
	}

	public void answer(Properties answer) {
		if (!properlySent || Integer.parseInt(answer.getProperty("portalchoice")) == 0) return;

		// check drop down and accepted
		boolean accepted = (answer.containsKey("accept")) && (answer.get("accept") == "true");
		float tx = 500;
		float ty = 500;
		String name = "";

		if (accepted) {
			String mynumber = answer.getProperty("portalchoice");
			//getResponder().getCommunicator().sendNormalServerMessage(mynumber+" "+myMap.get(mynumber));

			Connection dbcon = ModSupportDb.getModSupportDb();
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = dbcon.prepareStatement("SELECT * FROM ColdieGMPortals");
				rs = ps.executeQuery();

				while (rs.next()){
					if (rs.getString("name").equals(portalmod.myMap.get(mynumber))){
						name = portalmod.myMap.get(mynumber);
						tx = rs.getFloat("posx");
						ty = rs.getFloat("posy");
					}
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
				ps = dbcon.prepareStatement("SELECT * FROM ColdiePortals");
				rs = ps.executeQuery();
				while (rs.next()){
					if (rs.getString("name").equals(portalmod.myMap.get(mynumber))){
						name = portalmod.myMap.get(mynumber);
						tx = rs.getFloat("posx");
						ty = rs.getFloat("posy");
					}
				}
			}
			catch (SQLException e) { throw new RuntimeException(e); }
			finally {
				DbUtilities.closeDatabaseObjects(ps, rs);
				DbConnector.returnConnection(dbcon);
			}

			if (name.equals("")) return;

			tx = (tx * 4);
			ty = (ty * 4);
			int layer = 0;
			int floorLevel = getResponder().getFloorLevel();
			getResponder().getCommunicator().sendNormalServerMessage("Teleporting to: "+name+" "+(tx/4)+", "+(ty/4));
			getResponder().setTeleportPoints(tx, ty, layer, floorLevel);
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
			if (act.getNumber() == portalmod.portalActionId)
				ok = true;
		}
		catch (NoSuchActionException act) { throw new RuntimeException("No such action", act); }

		if (ok) {
			properlySent = true;

			BmlForm f = new BmlForm("");

			f.addHidden("id", String.valueOf(id));
			f.addBoldText(getQuestion());
			f.addText("\n ");
			f.addRaw("harray{label{text='Current portal location choices'}dropdown{id='portalchoice';options=\"");
			// use table data

			Connection dbcon = ModSupportDb.getModSupportDb();
			PreparedStatement ps = null;
			ResultSet rs = null;
			portalmod.myMap.clear();
			int i = 1;
			try {
				ps = dbcon.prepareStatement("SELECT * FROM ColdieGMPortals ORDER BY name");
				rs = ps.executeQuery();
				f.addRaw("--Choose Destination--,");

				while (rs.next()) {
					portalmod.myMap.put(String.valueOf(i), rs.getString("name"));
					f.addRaw("*"+rs.getString("name")+"*");
					f.addRaw(",");
					++i;
				}
			}
			catch (SQLException e) { throw new RuntimeException(e); }
			finally {
				DbUtilities.closeDatabaseObjects(ps, rs);
				DbConnector.returnConnection(dbcon);
			}



			dbcon = ModSupportDb.getModSupportDb();
			ps = null;
			rs = null;
			try {
				ps = dbcon.prepareStatement("SELECT * FROM ColdiePortals WHERE bank >= ? ORDER BY name");
				ps.setInt(1, portalmod.costPerMin *60); // updates every hour
				rs = ps.executeQuery();
				while (rs.next()) {
					portalmod.myMap.put(String.valueOf(i), rs.getString("name"));
					f.addRaw(rs.getString("name"));
					f.addRaw(",");
					++i;
				}
			} catch (SQLException e) { throw new RuntimeException(e); }
			finally {
				DbUtilities.closeDatabaseObjects(ps, rs);
				DbConnector.returnConnection(dbcon);
			}


			f.addRaw("\"}}");

			f.addText("\n\n\n\n ");
			f.beginHorizontalFlow();
			f.addButton("Portal now", "accept");
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