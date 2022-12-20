package com.wurmonline.server.questions;


import com.wurmonline.server.DbConnector;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.NoSuchActionException;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.utils.DbUtilities;
import com.wurmonline.server.villages.Village;
import com.wurmonline.server.zones.Zones;
import net.coldie.tools.BmlForm;
import net.coldie.wurmunlimited.mods.portals.portalmod;
import org.gotti.wurmunlimited.modsupport.ModSupportDb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;


public class activatequestion extends Question {
	private static final Logger logger = Logger.getLogger(portalmod.class.getName());
	private boolean properlySent = false;

	public activatequestion(Creature aResponder, String aTitle, String aQuestion, long aTarget) {
		super(aResponder, aTitle, aQuestion, 79, aTarget);
	}

	public void answer(Properties answer) {
		int coins = portalmod.costToActivate;
		if (!properlySent) return;

		// check drop down and accepted
		boolean accepted = (answer.containsKey("accept")) && (answer.get("accept") == "true");

		if (accepted) {
			if (coins > getResponder().getMoney()){
				getResponder().getCommunicator().sendNormalServerMessage("You don't have enough coins in the bank for that");
				return;
			}

			Village v = Zones.getVillage( getResponder().getTileX(), getResponder().getTileY(), true);
			if (v != null){
				String duplicateVillage = "";

				try {
					getResponder().chargeMoney(coins);
					logger.info(
						String.format("Removing %d iron from %s for activating portal. portal wurmid %d", coins, getResponder().getName(), portalmod.myMapPortal.get("0")));
					getResponder().getCommunicator().sendNormalServerMessage(
						String.format("Removed %d iron from your bank to activate portal at %s", coins, v.getName()));
				} catch (IOException e) { logger.warning(e.toString()); }

				Connection dbcon = ModSupportDb.getModSupportDb();
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					ps = dbcon.prepareStatement("SELECT * FROM ColdieGMPortals ORDER BY name");
					rs = ps.executeQuery();

					while (rs.next()) {
						if (rs.getString("name").equals(v.getName()))
							duplicateVillage = " 2";
						if (rs.getString("name").equals(v.getName()+" 2"))
							duplicateVillage = " 3";
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
					ps = dbcon.prepareStatement("SELECT * FROM ColdiePortals ORDER BY name");
					rs = ps.executeQuery();

					while (rs.next()) {
						if (rs.getString("name").equals(v.getName()))
							duplicateVillage = " 2";
						if (rs.getString("name").equals(v.getName()+" 2"))
							duplicateVillage = " 3";
					}
				} catch (SQLException e) { throw new RuntimeException(e); }
				finally {
					DbUtilities.closeDatabaseObjects(ps, rs);
					DbConnector.returnConnection(dbcon);
				}


				dbcon = ModSupportDb.getModSupportDb();
				ps = null;
				try {
					ps = dbcon.prepareStatement("INSERT INTO ColdiePortals (name,posx,posy,itemid,bank) VALUES(?,?,?,?,?)");
					ps.setString(1, v.getName()+duplicateVillage);
					ps.setFloat(2,  getResponder().getPosX()/4);
					ps.setFloat(3,  getResponder().getPosY()/4);
					ps.setLong(4,  portalmod.myMapPortal.get("0"));
					ps.setInt(5, portalmod.activateBankAmount);
					ps.executeUpdate();
				} catch (SQLException e) { throw new RuntimeException(e); }
				finally {
					DbUtilities.closeDatabaseObjects(ps, null);
					DbConnector.returnConnection(dbcon);
				}

			}else{
				getResponder().getCommunicator().sendNormalServerMessage("The portal must be on a deed");
			}
		}
	}

	public void sendQuestion() {
		boolean ok = false;
		try {
			Action act = getResponder().getCurrentAction();
			if (act.getNumber() == portalmod.activateActionId)
				ok = true;
		} catch (NoSuchActionException act) { throw new RuntimeException("No such action", act); }

		if (ok) {
			properlySent = true;

			BmlForm f = new BmlForm("");

			f.addHidden("id", id+"");
			f.addBoldText(getQuestion());
			f.addText("\nIt costs "+portalmod.costPerMin +" iron per minute to keep portals active\n ");
			f.addText("\n Activating this portal will cost you "+portalmod.costToActivate +" iron from your bank, this will pay for activation and also "+portalmod.activateBankAmount +" iron into this portals bank");

			f.addText("\n\n\n\n ");
			f.beginHorizontalFlow();
			f.addButton("Activate portal now", "accept");
			f.addText("               ");
			f.addButton("Cancel", "decline");

			f.endHorizontalFlow();
			f.addText(" \n");
			f.addText(" \n");

			getResponder().getCommunicator().sendBml(
					300,
					350,
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