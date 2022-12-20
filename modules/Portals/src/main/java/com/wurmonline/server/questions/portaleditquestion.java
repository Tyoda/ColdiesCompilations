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
import java.util.logging.Logger;

public class portaleditquestion extends Question {
	private static final Logger logger = Logger.getLogger(portalmod.class.getName());
	private boolean properlySent = false;

	// int g = 0;

	public portaleditquestion(Creature aResponder, String aTitle, String aQuestion, long aTarget) {
		super(aResponder, aTitle, aQuestion, 79, aTarget);
	}

	public void answer(Properties answer) {
		if (!properlySent) return;

		String mynumberGM = answer.getProperty("portalchoiceGM");
		String mynumber = answer.getProperty("portalchoice");
		String name = "";
		String dbname = "";
		if 	(mynumberGM.equals("0") && mynumber.equals("0")){
			getResponder().getCommunicator().sendNormalServerMessage("you didn't pick a portal");
			return;
		}
		if 	(mynumberGM.equals("0")){
			name = portalmod.myMapGM1.get(mynumber);
			dbname = "ColdiePortals";
		}
		if 	(mynumber.equals("0")){
			name = portalmod.myMapGM2.get(mynumberGM);
			dbname = "ColdieGMPortals";
		}
		if (dbname.equals("")){
			getResponder().getCommunicator().sendNormalServerMessage("Just pick 1 portal, not 2");
			return;
		}

		// check drop down and accepted
		boolean accepted = (answer.containsKey("accept")) && (answer.get("accept") == "true");
		Connection dbcon = ModSupportDb.getModSupportDb();
		PreparedStatement ps = null;
		if (accepted) {
			//edit selection
			try {
				ps = dbcon.prepareStatement("UPDATE ? SET posx=? , posy=? WHERE name=?");
				ps.setString(1, dbname);
				ps.setInt(2, (int)answer.get("xcoords"));
				ps.setInt(3, (int)answer.get("ycoords"));
				ps.setString(4, name);
				ps.executeUpdate();
				getResponder().getCommunicator().sendNormalServerMessage("Edited portal "+ name);
				logger.info(getResponder().getName()+" edited portal "+ name);
			} catch (SQLException e) { throw new RuntimeException(e); }
			finally {
				DbUtilities.closeDatabaseObjects(ps, null);
				DbConnector.returnConnection(dbcon);
			}
		}else{
			//delete selection
			try {
				//getResponder().getCommunicator().sendNormalServerMessage("Deleted portal "+ name + "from db "+dbname);
				ps = dbcon.prepareStatement("DELETE FROM ? WHERE name=?");
				ps.setString(1, dbname);
				ps.setString(2, name);
				ps.executeUpdate();
				getResponder().getCommunicator().sendNormalServerMessage("Deleted portal "+ name);
				logger.info(getResponder().getName()+" deleted portal "+ name);
			}
			catch (SQLException e) { throw new RuntimeException(e); }
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
			if (act.getNumber() == portalmod.portalEditActionId)
				ok = true;
		} catch (NoSuchActionException act) { throw new RuntimeException("No such action", act); }

		if (ok) {
			properlySent = true;

			BmlForm f = new BmlForm("");

			f.addHidden("id", String.valueOf(id));
			f.addBoldText(getQuestion());
			f.addText("\n ");

			//drop down option
			f.addRaw("harray{label{text='Current GM portal location choices'}dropdown{id='portalchoiceGM';options=\"");
			// use table data

			Connection dbcon = ModSupportDb.getModSupportDb();
			PreparedStatement ps = null;
			ResultSet rs = null;
			portalmod.myMapGM2.clear();
			try {
				ps = dbcon.prepareStatement("SELECT * FROM ColdieGMPortals ORDER BY name");
				rs = ps.executeQuery();
				int i = 1;
				f.addRaw("--Choose Portal--,");
				while (rs.next()) {
					portalmod.myMapGM2.put(String.valueOf(i), rs.getString("name"));
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
			f.addText("\n");

			f.addRaw("harray{label{text='Current portal location choices'}dropdown{id='portalchoice';options=\"");
			// use table data

			dbcon = ModSupportDb.getModSupportDb();
			ps = null;
			rs = null;
			portalmod.myMapGM1.clear();
			try {
				ps = dbcon.prepareStatement("SELECT * FROM ColdiePortals ORDER BY name");
				rs = ps.executeQuery();
				int i = 1;
				f.addRaw("--Choose Portal--,");
				while (rs.next()) {
					portalmod.myMapGM1.put(String.valueOf(i), rs.getString("name"));
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
			f.addText("\n");
			f.addText("Insert new x coords here\t");
			f.addInput("xcoords", 10, String.valueOf(getResponder().getPosX()/4));
			f.addText("\n");
			f.addText("Insert new y coords here\t");
			f.addInput("ycoords", 10, String.valueOf(getResponder().getPosY()/4));
			f.addText("\n");

			f.addText("\n");
			f.beginHorizontalFlow();
			f.addButton("Edit Selection", "accept");
			f.addText("               ");
			f.addButton("Delete Selection", "decline");

			f.endHorizontalFlow();
			f.addText(" \n");
			f.addText(" \n");

			getResponder().getCommunicator().sendBml(
					250,
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