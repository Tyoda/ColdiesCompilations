package net.coldie.wurmunlimited.mods.eventportals;


import com.wurmonline.server.DbConnector;
import com.wurmonline.server.utils.DbUtilities;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.ItemTemplatesCreatedListener;
import org.gotti.wurmunlimited.modloader.interfaces.PlayerLoginListener;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import com.wurmonline.server.players.Player;

import org.gotti.wurmunlimited.modsupport.ModSupportDb;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;


public class eventmod implements WurmServerMod, Configurable, PreInitable, PlayerLoginListener, ServerStartedListener, ItemTemplatesCreatedListener, Initable  {
	public static final Logger logger = Logger.getLogger(eventmod.class.getName());
	public static final String version = "ty1.0";
   
	public static boolean activated = false;
	public static boolean zennyactivated = false;
	public static HashMap<Integer, Integer> eventlootenable = new HashMap<>();
	public static final String[] arrevent = new String[0];

	public static int freadeathminx = 0;
	public static int freadeathmaxx = 1;
	public static int freadeathminy = 0;
	public static int freadeathmaxy = 1;

	private static int eventActionId = 0;
	private static int manageEventActionId = 0;
	private static int eventReloadActionId = 0;
	private static int returnEventActionId = 0;

	private static int eventPortalTemplateId = 0;

	public static boolean active = false;

	@Override
	public void onItemTemplatesCreated() {
		new eventitems();
	}

	
	@Override
	public void onServerStarted() {
		eventaction eventAction = new eventaction();
		eventActionId = eventAction.getActionId();
		manageeventaction manageEventAction = new manageeventaction();
		manageEventActionId = manageEventAction.getActionId();
		returneventaction returnEventAction = new returneventaction();
		returnEventActionId = returnEventAction.getActionId();
		eventreloadaction eventReloadAction = new eventreloadaction();
		eventReloadActionId = eventReloadAction.getActionId();
		ModActions.registerAction(eventAction);
		ModActions.registerAction(manageEventAction);
		ModActions.registerAction(returnEventAction);
		ModActions.registerAction(eventReloadAction);
		Connection dbcon = ModSupportDb.getModSupportDb();
		PreparedStatement ps = null;
		try {

			if (!ModSupportDb.hasTable(dbcon, "ColdieEventPortals")) {
				String sql = "CREATE TABLE ColdieEventPortals (id LONG NOT NULL UNIQUE,"
													+ " posx FLOAT NOT NULL DEFAULT 100,"
													+ " posy FLOAT NOT NULL DEFAULT 100,"
													+ " layer INT NOT NULL DEFAULT 0,"
													+ " floorlevel INT NOT NULL DEFAULT 0)";
				ps = dbcon.prepareStatement(sql);

				ps.execute();
				DbUtilities.closeDatabaseObjects(ps, null);

				//create first record which will be event location
				ps = dbcon.prepareStatement("INSERT INTO ColdieEventPortals (id,posx,posy,layer,floorlevel) VALUES(1,1,1,0,0)");
				ps.executeUpdate();
			}
		}
		catch (SQLException e) { throw new RuntimeException(e);}
		finally {
			DbUtilities.closeDatabaseObjects(ps, null);
			DbConnector.returnConnection(dbcon);
		}
	}

	@Override
	public void configure(Properties properties) {
doconfig(properties);
}

	public static void doconfig(Properties properties){
		freadeathminx = Integer.parseInt(properties.getProperty("freadeathminx", Float.toString(freadeathminx)));
		freadeathmaxx = Integer.parseInt(properties.getProperty("freadeathmaxx", Float.toString(freadeathmaxx)));
		freadeathminy = Integer.parseInt(properties.getProperty("freadeathminy", Float.toString(freadeathminy)));
		freadeathmaxy = Integer.parseInt(properties.getProperty("freadeathmaxy", Float.toString(freadeathmaxy)));
	}

	@Override
	public void preInit() {
ModActions.init();
}

	@Override
	public void init() {
freedeathhook.setUpDeathEffectInterception();
}

	//free death check
	public static boolean checkfreedeath(int tilex, int tiley, String name){
		logger.info("Name: "+name);
		return tilex > freadeathminx && tilex < freadeathmaxx && tiley > freadeathminy && tiley < freadeathmaxy;
	}

	@Override
	public void onPlayerLogin(Player player) {
		if(active)
			player.getCommunicator().sendNormalServerMessage("Event Portal is active.");
	}

	public static int getManageEventActionId() {
		return manageEventActionId;
	}

	public static int getEventReloadActionId() {
		return eventReloadActionId;
	}

	public static int getReturnEventActionId() {
		return returnEventActionId;
	}

	public static int getEventActionId() {
		return eventActionId;
	}

	public static int getEventPortalTemplateId() {
		return eventPortalTemplateId;
	}

	public static void setEventPortalTemplateId(int eventPortalTemplateId) {
		eventmod.eventPortalTemplateId = eventPortalTemplateId;
	}

	public String getVersion() {
		return version;
	}
}