package net.coldie.wurmunlimited.mods.portals;

import com.wurmonline.server.DbConnector;
import com.wurmonline.server.utils.DbUtilities;
import org.gotti.wurmunlimited.modsupport.ModSupportDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class pollportals  {
	public static final Logger logger = Logger.getLogger(portalmod.class.getName());
	private static long lastPoll = 0L;
	private static final int pollFrequency = (60*60*1000); //1 hour TODO: add config
	public static void pollportal(){
		if (lastPoll + pollFrequency > System.currentTimeMillis()) return;

		lastPoll = System.currentTimeMillis();
		Connection dbcon = ModSupportDb.getModSupportDb();
		PreparedStatement ps = null;
		try {
			ps = dbcon.prepareStatement("UPDATE ColdiePortals SET bank = bank - ? WHERE bank >= ?");
			ps.setInt(1, portalmod.costPerMin *60);//updates every hour
			ps.setInt(2, portalmod.costPerMin *60);
			ps.executeUpdate();
			logger.info("Updated player portal upkeeps");
		} catch (SQLException e) { throw new RuntimeException(e); }
		finally {
			DbUtilities.closeDatabaseObjects(ps, null);
			DbConnector.returnConnection(dbcon);
		}
	}
}