package org.coldie.wurmunlimited.mods.steamid;

import com.wurmonline.server.DbConnector;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.steam.SteamId;
import com.wurmonline.server.utils.DbUtilities;
import org.gotti.wurmunlimited.modloader.interfaces.PlayerLoginListener;
import org.gotti.wurmunlimited.modloader.interfaces.Versioned;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class steamid implements WurmServerMod, PlayerLoginListener, Versioned {
	// public static final Logger logger = Logger.getLogger(steamid.class.getName());
	public static final String version = "ty1.0";

	@Override
	public void onPlayerLogin(Player player) {
		SteamId steamid = player.getSteamId();
		boolean found = false;
		boolean first = true;
		long firstUsed = System.currentTimeMillis();
		Connection dbcon = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// sql 1
			dbcon = DbConnector.getPlayerDbCon();
			ps = dbcon.prepareStatement("SELECT * FROM STEAM_IDS WHERE PLAYER_ID=?");
			ps.setLong(1, player.getWurmId());
			rs = ps.executeQuery();
			while (rs.next()) {
				if(first) {
					firstUsed = rs.getLong("FIRST_USED");
					first = false;
				}
				found = true;
			}

			DbUtilities.closeDatabaseObjects(ps, rs);
			DbConnector.returnConnection(dbcon);

			// sql 2
			if(found) {
				DbUtilities.closeDatabaseObjects(ps, null);
				dbcon = DbConnector.getPlayerDbCon();
				ps = dbcon.prepareStatement("DELETE FROM STEAM_IDS WHERE PLAYER_ID=?");
				ps.setLong(1, player.getWurmId());
				ps.executeUpdate();
			}

			DbUtilities.closeDatabaseObjects(ps, null);
			DbConnector.returnConnection(dbcon);

			// sql 3
			long now = System.currentTimeMillis();
			ps = dbcon.prepareStatement("INSERT INTO STEAM_IDS(PLAYER_ID,STEAM_ID,FIRST_USED,LAST_USED) VALUES(?,?,?,?)");
			ps.setLong(1, player.getWurmId());
			ps.setLong(2, steamid.getSteamID64());
			ps.setLong(3, firstUsed);
			ps.setLong(4, now);
			ps.executeUpdate();

			DbUtilities.closeDatabaseObjects(ps, null);
			DbConnector.returnConnection(dbcon);

			// sql 4
			DbUtilities.closeDatabaseObjects(ps, null);
			dbcon = DbConnector.getPlayerDbCon();
			ps = dbcon.prepareStatement("SELECT * FROM STEAM_IDS WHERE PLAYER_ID=?");
			ps.setLong(1, player.getWurmId());
			rs = ps.executeQuery();

			//while (rs.next()) found = true;
		} catch (SQLException e) { throw new RuntimeException(e); }
		finally {
			DbUtilities.closeDatabaseObjects(ps, rs);
			DbConnector.returnConnection(dbcon);
		}
	}

	@Override
	public String getVersion(){
		return version;
	}
}
