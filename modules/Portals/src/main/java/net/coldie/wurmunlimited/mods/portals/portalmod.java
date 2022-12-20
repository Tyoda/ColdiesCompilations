package net.coldie.wurmunlimited.mods.portals;

import com.wurmonline.server.DbConnector;
import com.wurmonline.server.utils.DbUtilities;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.ItemTemplatesCreatedListener;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;
import org.gotti.wurmunlimited.modloader.interfaces.ServerPollListener;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;

import org.gotti.wurmunlimited.modsupport.ModSupportDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;


public class portalmod implements WurmServerMod, PreInitable, Configurable, ServerStartedListener, ItemTemplatesCreatedListener, ServerPollListener  {
	//private static Logger logger = Logger.getLogger(portalmod.class.getName());
	public static final String version = "ty1.0";
   
	public static HashMap<String, String> myMap = new HashMap<>();
	public static HashMap<String, String> myMapBank = new HashMap<>();
	public static HashMap<String, String> myMapGM1 = new HashMap<>();
	public static HashMap<String, String> myMapGM2 = new HashMap<>();
	public static HashMap<String, Long> myMapPortal = new HashMap<>();


	public static int costPerMin = 1; //1 iron per min default
	public static int costToActivate = 10000; //1s default
	public static int activateBankAmount = 5000; //50c default
	public static boolean newConcrete = false;
	public static boolean craftPortals = false;
	public static int newConcreteItem1 = 146;
	public static int newConcreteItem2 = 492;


	public static int portalActionId = 0;
	public static int portalAddLocationId = 0;
	public static int portalEditActionId = 0;
	public static int addUpkeepActionId = 0;
	public static int activateActionId = 0;

	@Override
	public void onItemTemplatesCreated() {
	new PortalItems();
	}

	public void configure(Properties properties) {
		costPerMin = Integer.parseInt(properties.getProperty("costpermin", String.valueOf(costPerMin)));
		costToActivate = Integer.parseInt(properties.getProperty("costtoactivate", String.valueOf(costToActivate)));
		activateBankAmount = Integer.parseInt(properties.getProperty("activatebankamount", String.valueOf(activateBankAmount)));
		newConcrete = Boolean.parseBoolean(properties.getProperty("newconcrete", Boolean.toString(newConcrete)));
		craftPortals = Boolean.parseBoolean(properties.getProperty("craftportals", Boolean.toString(craftPortals)));
		newConcreteItem1 = Integer.parseInt(properties.getProperty("newconcreteitem1", String.valueOf(newConcreteItem1)));
		newConcreteItem2 = Integer.parseInt(properties.getProperty("newconcreteitem2", String.valueOf(newConcreteItem2)));
	}

	public static boolean checkaction(Item target){
		int clicked = target.getTemplateId();
		return clicked == 4002 || clicked == 4003 || clicked == 4004 || clicked == 4010 || clicked == 4011; // TODO: Ids
	}

	public static boolean checkGM(Creature performer){
		return performer.getPower() >= 2;
	}

	@Override
	public void onServerStarted() {
		portalaction portalAction = new portalaction();
		portalActionId = portalAction.getActionId();
		ModActions.registerAction(portalAction);

		portaladdlocation portalAddLocationAction = new portaladdlocation();
		portalAddLocationId = portalAddLocationAction.getActionId();
		ModActions.registerAction(portalAddLocationAction);

		portaleditaction portalEditAction = new portaleditaction();
		portalEditActionId = portalEditAction.getActionId();
		ModActions.registerAction(portalEditAction);

		addupkeepaction addUpkeepAction = new addupkeepaction();
		addUpkeepActionId = addUpkeepAction.getActionId();
		ModActions.registerAction(addUpkeepAction);

		activateaction activateAction = new activateaction();
		activateActionId = activateAction.getActionId();
		ModActions.registerAction(activateAction);


		Connection dbcon = ModSupportDb.getModSupportDb();
		PreparedStatement ps = null;
		try {
			if (!ModSupportDb.hasTable(dbcon, "ColdiePortals")) {
				String sql = "CREATE TABLE ColdiePortals (name VARCHAR(20) NOT NULL DEFAULT 'Unknown',"
						+ " posx INT NOT NULL DEFAULT 100,"
						+ " posy INT NOT NULL DEFAULT 100,"
						+ " bank INT NOT NULL DEFAULT 0,"
						+ " itemid LONG NOT NULL DEFAULT 0)";
				ps = dbcon.prepareStatement(sql);
				ps.execute();
			}
		} catch (SQLException e) { throw new RuntimeException(e); }
		finally {
			DbUtilities.closeDatabaseObjects(ps, null);
			DbConnector.returnConnection(dbcon);
		}

		dbcon = ModSupportDb.getModSupportDb();
		ps = null;
		try {
			if (!ModSupportDb.hasTable(dbcon, "ColdieGMPortals")) {
				String sql2 = "CREATE TABLE ColdieGMPortals (name VARCHAR(20) NOT NULL DEFAULT 'Unknown',"
						+ " posx INT NOT NULL DEFAULT 100,"
						+ " posy INT NOT NULL DEFAULT 100,"
						+ " bank INT NOT NULL DEFAULT 0,"
						+ " itemid LONG NOT NULL DEFAULT 0)";
				ps = dbcon.prepareStatement(sql2);
				ps.execute();
			}
		}
		catch (SQLException e) { throw new RuntimeException(e); }
		finally {
			DbUtilities.closeDatabaseObjects(ps, null);
			DbConnector.returnConnection(dbcon);
		}
	}

	@Override
	public void preInit() {
	ModActions.init();
	}

	@Override
	public void onServerPoll()
	{
	pollportals.pollportal();
	}

	@Override
	public String getVersion() {
		return version;
	}
}