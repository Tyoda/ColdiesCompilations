package org.coldie.wurmunlimited.mods.creaturesdb;


import com.wurmonline.server.DbConnector;
import com.wurmonline.server.utils.DbUtilities;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.Versioned;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class creaturesdb implements WurmServerMod, ServerStartedListener, Versioned {
	public static final Logger logger = Logger.getLogger(creaturesdb.class.getName());
	public static final String version = "ty1.0";
	private ArrayList<Long> position = new ArrayList<>();
	private ArrayList<Long> creatures = new ArrayList<>();
	private ArrayList<Long> deleteIt = new ArrayList<>();

	@Override
	public void onServerStarted() {
		findAllIds();
		findMissingIds();
		deleteIds();

		position.clear(); position = null;
		creatures.clear(); creatures = null;
		deleteIt.clear(); deleteIt = null;
	}
	
	private void findAllIds() {
    	Connection db = null;
        PreparedStatement ps = null;
        ResultSet rs = null;	    
	    try {
			db = DbConnector.getCreatureDbCon();
			ps = db.prepareStatement("SELECT WURMID FROM POSITION");
			rs = ps.executeQuery();

			while (rs.next()) {
				position.add(rs.getLong("WURMID"));
			}
		} catch (SQLException e) { logger.warning(e.toString()); }
	    finally {
	    	DbUtilities.closeDatabaseObjects(ps, rs);
	    	DbConnector.returnConnection(db);
	    }
	}
	
	
	void findMissingIds() {
		logger.info("looking size: "+position.size());
    	Connection db = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
	    try {
			db = DbConnector.getCreatureDbCon();
			ps = db.prepareStatement("SELECT * FROM CREATURES");
			rs = ps.executeQuery();

			while (rs.next()) {
				creatures.add(rs.getLong("WURMID"));
			}
	    } catch (SQLException e) { logger.warning(e.toString()); }
		finally {
		  DbUtilities.closeDatabaseObjects(ps, rs);
		  DbConnector.returnConnection(db);
		}
	}
	
	void deleteIds() {
		for (Long wurmId : position ) {
			if(!creatures.contains(wurmId)) deleteIt.add(wurmId);
		}

		Connection db = null;
	    PreparedStatement ps = null;
		for (Long wurmId : deleteIt) {
			logger.log(Level.WARNING, "Removing creature with WURMID: "+ wurmId);
			try {
				db = DbConnector.getCreatureDbCon();

				ps = db.prepareStatement("DELETE FROM CREATURES WHERE WURMID=?");
				ps.setLong(1, wurmId);
				ps.executeUpdate();

				ps = db.prepareStatement("DELETE FROM BRANDS WHERE WURMID=?");
				ps.setLong(1, wurmId);
				ps.executeUpdate();

				ps = db.prepareStatement("DELETE FROM OFFSPRING WHERE FATHERID=?");
				ps.setLong(1, wurmId);
				ps.executeUpdate();

				ps = db.prepareStatement("DELETE FROM OFFSPRING WHERE MOTHERID=?");
				ps.setLong(1, wurmId);
				ps.executeUpdate();

				ps = db.prepareStatement("DELETE FROM POSITION WHERE WURMID=?");
				ps.setLong(1, wurmId);
				ps.executeUpdate();

				ps = db.prepareStatement("DELETE FROM PROTECTED WHERE WURMID=?");
				ps.setLong(1, wurmId);
				ps.executeUpdate();

				ps = db.prepareStatement("DELETE FROM SKILLS WHERE OWNER=?");
				ps.setLong(1, wurmId);
				ps.executeUpdate();

				ps = db.prepareStatement("DELETE FROM ANIMALSETTINGS WHERE WURMID=?");
				ps.setLong(1, wurmId);
				ps.executeUpdate();
		    } catch (SQLException e) { logger.warning(e.toString()); }
		    finally {
		      DbUtilities.closeDatabaseObjects(ps, null);
		      DbConnector.returnConnection(db);
		    }
		}
		logger.info("Removed total: "+deleteIt.size()+" from creatures.db");
	}

	@Override
	public String getVersion(){
		return version;
	}
}