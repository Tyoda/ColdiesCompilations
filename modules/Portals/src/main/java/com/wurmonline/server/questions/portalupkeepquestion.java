package com.wurmonline.server.questions;


import com.wurmonline.server.DbConnector;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.NoSuchActionException;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.economy.Change;
import com.wurmonline.server.economy.Economy;
import com.wurmonline.server.utils.DbUtilities;
import net.coldie.tools.BmlForm;
import net.coldie.wurmunlimited.mods.portals.portalmod;
import org.gotti.wurmunlimited.modsupport.ModSupportDb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;



public class portalupkeepquestion extends Question {
	private boolean properlySent = false;

	public portalupkeepquestion(Creature aResponder, String aTitle, String aQuestion, long aTarget) {
		super(aResponder, aTitle, aQuestion, 79, aTarget);
	}

	public void answer(Properties answer) {
		int coins = 0;
		if (!properlySent || Integer.parseInt(answer.getProperty("portalchoice")) == 0) return;

		if (answer.getProperty("gold") != null && Integer.parseInt(answer.getProperty("gold")) >= 0)
			coins = Integer.parseInt(answer.getProperty("gold"))*1000000;

		if (answer.getProperty("silver") != null && Integer.parseInt(answer.getProperty("silver")) >= 0)
			coins = coins + (Integer.parseInt(answer.getProperty("silver"))*10000);

		if (coins == 0) {
			getResponder().getCommunicator().sendNormalServerMessage("You either had 0 coins or didn't do the numbers properly");
			return;
		}

		// check drop down and accepted
		boolean accepted = (answer.containsKey("accept")) && (answer.get("accept") == "true");

		if (accepted) {
			String myNumber = answer.getProperty("portalchoice");
			if (coins > getResponder().getMoney()){
				getResponder().getCommunicator().sendNormalServerMessage("you don't have enough coins in bank for that");
				return;
			}

			try {
				getResponder().chargeMoney(coins);
				getResponder().getCommunicator().sendNormalServerMessage("Removed "+coins+" from your account");
				logger.info("Removing "+coins+" iron from "+getResponder().getName()
									+" for adding upkeep to portal. portal wurmid "
									+portalmod.myMapBank.get(myNumber));
			} catch (IOException e) { logger.warning(e.toString()); }

			Connection dbcon = null;
			PreparedStatement ps = null;
			try {
				dbcon = ModSupportDb.getModSupportDb();
				ps = dbcon.prepareStatement("UPDATE ColdiePortals  SET bank = bank + ? WHERE itemid=?");
				ps.setInt(1, coins);
				ps.setString(2, portalmod.myMapBank.get(myNumber));
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
			if (act.getNumber() == portalmod.addUpkeepActionId)
				ok = true;
		}
		catch (NoSuchActionException act) { throw new RuntimeException("No such action", act); }

		if (ok) {
			properlySent = true;
			BmlForm f = new BmlForm("");

			f.addHidden("id", String.valueOf(id));
			f.addBoldText(getQuestion());
			f.addText("\nIt costs "+portalmod.costPerMin +" iron per minute \n ");

			f.addRaw("harray{label{text='Portal choices you can add upkeep to'}dropdown{id='portalchoice';options=\"");
			// use table data

			Connection dbcon = ModSupportDb.getModSupportDb();
			PreparedStatement ps = null;
			ResultSet rs = null;
			portalmod.myMapBank.clear();
			try {
				ps = dbcon.prepareStatement("SELECT * FROM ColdiePortals ORDER BY name");
				rs = ps.executeQuery();
				f.addRaw("--Choose Portal--,");
				int i = 1;
				while (rs.next()) {
					portalmod.myMapBank.put(String.valueOf(i), String.valueOf(rs.getLong("itemid")));
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
			long money = getResponder().getMoney();
			Change change = Economy.getEconomy().getChangeFor(money);
			long gold = change.getGoldCoins();
			long silver = change.getSilverCoins();

			if (gold > 0L)
				f.addRaw("harray{input{maxchars=\"10\";id=\"gold\";text=\"0\"};label{text=\"(" + gold + ") Gold coins\"}}");
			if ((silver > 0L) || (gold > 0L))
				f.addRaw("harray{input{maxchars=\"10\";id=\"silver\";text=\"0\"};label{text=\"(" + silver + ") Silver coins\"}}");

			f.addText("\n\n\n\n ");
			f.beginHorizontalFlow();
			f.addButton("Add upkeep now", "accept");
			f.addText("               ");
			f.addButton("Cancel", "decline");
			f.endHorizontalFlow();
			f.addText("\n\nDeed name: portal bank in irons\n");

			dbcon = null;
			ps = null;
			rs = null;
			try {
				dbcon = ModSupportDb.getModSupportDb();
				ps = dbcon.prepareStatement("SELECT * FROM ColdiePortals ORDER BY name");
				rs = ps.executeQuery();
				while (rs.next()) {
					f.addText(rs.getString("name")+" : "+rs.getInt("bank")+"\n");
				}
			}
			catch (SQLException e) { throw new RuntimeException(e); }
			finally {
				DbUtilities.closeDatabaseObjects(ps, rs);
				DbConnector.returnConnection(dbcon);
			}

			f.addText(" \n");
			f.addText(" \n");

			getResponder().getCommunicator().sendBml(
					350,
					450,
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