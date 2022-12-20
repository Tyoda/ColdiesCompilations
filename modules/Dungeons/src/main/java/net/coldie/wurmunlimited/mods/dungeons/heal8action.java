package net.coldie.wurmunlimited.mods.dungeons;


import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.ModSupportDb;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;
import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.bodys.Wound;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemTypes;
import com.wurmonline.server.players.Player;


import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class heal8action implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer {

	public static short actionId;
	static ActionEntry actionEntry;

	public heal8action() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, dungeonmain.currencyname8+" Heal", dungeonmain.currencyname8+" Healing", new int[]{ 
				}); 
		ModActions.registerAction(actionEntry);
	}

	@Override
	public BehaviourProvider getBehaviourProvider() {
		return this;
	}

	@Override
	public ActionPerformer getActionPerformer() {
		return this;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Creature target) {
		int used = source.getTemplateId();
		if (dungeonmain.currency8enabled && used == dungeonmain.statuettetemplate8 && target instanceof Player) {
				return (List<ActionEntry>) Arrays.asList(actionEntry);
			} else {
				return null;
			}
	}
	
	
	@Override
	public boolean action(Action act, Creature performer, Item source, Creature target, short action, float counter) {
		int used = source.getTemplateId();
		Float X = target.getPosX()/4;
		Float Y = target.getPosY()/4;
        if (dungeonmain.currency8enabled){
            if (dungeonmain.zone8minx <= X && X <= dungeonmain.zone8maxx && dungeonmain.zone8miny <= Y && Y <= dungeonmain.zone8maxy){
				if (used == dungeonmain.statuettetemplate8 && target instanceof Player) {
					if (target.getBody().getWounds() != null && target.getBody().getWounds().getWounds().length > 0) {
			          Connection dbcon = null;
			  	      PreparedStatement ps = null;
			  	      ResultSet rs = null;
			  	      long lastheal = 0;
			  	      try
			  	      {	      
			  	      dbcon = ModSupportDb.getModSupportDb();
			  	      ps = dbcon.prepareStatement("SELECT * FROM DungeonCurrency WHERE name = \""+target.getName()+"\"");
			  	      rs = ps.executeQuery();
			  	      lastheal = rs.getLong("LastHealed");
			  	      rs.close();
			  	      ps.close();
			  	      dbcon.close();
			  	    }
			  	      catch (SQLException e) {
			  	          throw new RuntimeException(e);
			  	        }
			  	    if (System.currentTimeMillis()-lastheal > dungeonmain.healcooldown8*1000){
						int power = dungeonmain.healpower8;
						int w8 = 0;
						int numhealed = 0;
						int numfullhealed = 0;
				        Wound[] wounds8 = target.getBody().getWounds().getWounds();
				        if (target.getBody().getWounds() != null && ( wounds8.length > 0)) {
					        	while (w8 < wounds8.length){
					            if (wounds8[w8].getSeverity() / 1000.0f < (float)power) {
					                wounds8[w8].heal();
					                numfullhealed = numfullhealed+1;
					                }else{
					            wounds8[w8].modifySeverity((- power) * 1000);
					            numhealed = numhealed + 1;
					            }
					            ++w8;}
				        	
				        		//add time to char healed in db
			                	Connection dbcon8 = null;
							      PreparedStatement ps8 = null;	    	
							      try
							      {
								      dbcon8 = ModSupportDb.getModSupportDb();
								      ps8 = dbcon8.prepareStatement("UPDATE DungeonCurrency "
								      + "SET LastHealed = "+System.currentTimeMillis()+" WHERE name = \""+target.getName()+"\"");
								      ps8.executeUpdate();
								      ps8.close(); 
								      dbcon8.close();
							      }
						      catch (SQLException e) {
						          throw new RuntimeException(e);
						        }
							      performer.getCommunicator().sendSafeServerMessage("Target "+target.getName()+": "+numfullhealed+" fully healed, "+numhealed+" partially healed wounds.");  
				        	}else{
				        		performer.getCommunicator().sendSafeServerMessage(target.getName()+" has no wounds to heal.");
				        	}
				        	
			  	    }else{
			  	    long timeleft = (System.currentTimeMillis()-lastheal)/1000;
			  	    timeleft = dungeonmain.healcooldown8 - timeleft;
			  	    performer.getCommunicator().sendSafeServerMessage(target.getName()+" cant be healed like this for another "+timeleft+" seconds.");	
			  	    }
					}
		
				}
            }else{
            performer.getCommunicator().sendSafeServerMessage("Nice try, you need to be in "+dungeonmain.currencyname8+" in order to use this item.");
            }
        }
		return true;
	}
	
}