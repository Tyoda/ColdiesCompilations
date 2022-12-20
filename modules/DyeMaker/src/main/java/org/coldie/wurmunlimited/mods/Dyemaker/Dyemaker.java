package org.coldie.wurmunlimited.mods.Dyemaker;

import com.wurmonline.server.DbConnector;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Actions;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTemplateFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wurmonline.server.utils.DbUtilities;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Dyemaker implements WurmServerMod, Configurable, ServerStartedListener, ItemTemplatesCreatedListener, Versioned {
	// public static final Logger logger = Logger.getLogger(Dyemaker.class.getName());
	public static final String version = "ty1.0";

	public static Action act = null;
	public static int removeActionId = 0;
	public static Creature performer = null; 
	public static Item source = null;
	public static Item target = null;
	public static Item item = null;
	static int targetid = 1117;
	static int losspercent = 0;
	public static int minimumextraction = 50;
	static int maxrgb = 450;
    
	public void onItemTemplatesCreated() {
		new dyemakeritems();
	}
	
	@Override
	public void onServerStarted() {
		
        ItemTemplate hasData;
		try {
			hasData = ItemTemplateFactory.getInstance().getTemplate(targetid);
	        ReflectionUtil.setPrivateField(hasData, ReflectionUtil.getField(hasData.getClass(), "hasdata"), true);
		} catch (IllegalArgumentException | IllegalAccessException | ClassCastException | NoSuchFieldException | NoSuchTemplateException e) {
			e.printStackTrace();
		}		

		adddye adddyeAction = new adddye();
		removedye removedyeAction = new removedye();
		removeActionId = removedyeAction.getActionId();
		checkdye checkdyeAction = new checkdye();
		ModActions.registerAction(adddyeAction);
		ModActions.registerAction(removedyeAction);
		ModActions.registerAction(checkdyeAction);
		
		try {
			ReflectionUtil.setPrivateField(Actions.actionEntrys[adddyeAction.getActionId()],
				ReflectionUtil.getField(ActionEntry.class, "maxRange"), 8);
			ReflectionUtil.setPrivateField(Actions.actionEntrys[removedyeAction.getActionId()],
				ReflectionUtil.getField(ActionEntry.class, "maxRange"), 8);
			ReflectionUtil.setPrivateField(Actions.actionEntrys[checkdyeAction.getActionId()],
				ReflectionUtil.getField(ActionEntry.class, "maxRange"), 8);
		} catch (IllegalArgumentException | IllegalAccessException | ClassCastException | NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void configure(Properties properties) {
			// targetid = Integer.parseInt(properties.getProperty("targetid", Integer.toString(targetid)));
			losspercent = Integer.parseInt(properties.getProperty("losspercent", Integer.toString(losspercent)));
			// convert to amount instead of loss.
			losspercent = 100 - losspercent;
			if (0 > losspercent)losspercent = 1;
			if (losspercent > 100 )losspercent = 100;
			minimumextraction = Integer.parseInt(properties.getProperty("minimumextraction", Integer.toString(minimumextraction)));
			maxrgb = Integer.parseInt(properties.getProperty("maxrgb", Integer.toString(maxrgb)));
	}
	
	public static void getdata(Item target) {
		Connection dbcon = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean done = false;
		try {
			dbcon = DbConnector.getItemDbCon();
			ps = dbcon.prepareStatement("SELECT * FROM ITEMDATA WHERE WURMID=?");
			ps.setLong(1, target.getWurmId());
			rs = ps.executeQuery();

			while (rs.next()){
				done = true;
				target.setData1(rs.getInt("DATA1"));
				target.setData2(rs.getInt("DATA2"));
				target.setExtra1(rs.getInt("EXTRA1"));
			}
		} catch (SQLException e) { e.printStackTrace(); }
		finally {
			DbUtilities.closeDatabaseObjects(ps, rs);
			DbConnector.returnConnection(dbcon);
		}

		if(!done) {
			target.setData1(0);
			target.setData2(0);
			target.setExtra1(0);
		}
	}

	public String getVersion() {
		return version;
	}
}