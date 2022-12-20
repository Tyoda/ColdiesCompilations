package net.coldie.wurmunlimited.mods.bountycoldiestyle;


import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

public class bountyreloadaction implements ModAction, BehaviourProvider, ActionPerformer {
	public static short actionId;
	static ActionEntry actionEntry;

	public static final Path propertiesPath = Paths.get("mods/BountyColdieStyle.properties");

	public bountyreloadaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Reload bounty properties", "Reloading bounty properties", new int[]{ 
				}); 
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		return getBehavioursFor(performer, target);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {
		if (performer instanceof Player) {
			
			if (performer.getPower() > 4) {
					return Collections.singletonList(actionEntry);
			} else {
				return null;
			}
		}else {
			return null;
		}
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		return action(act, performer, target, action, counter);
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item target, short action, float counter) {
		if (performer.getPower() > 4) {
			
	        if (!Files.exists(propertiesPath)) {
				bountymod.logger.log(Level.SEVERE, "Could not find properties file.");
	            performer.getCommunicator().sendAlertServerMessage("The config file seems to be missing.");
	            return true;
	        }

	        InputStream stream = null;
	        try {
	            performer.getCommunicator().sendAlertServerMessage("Opening the config file.");
	            stream = Files.newInputStream(propertiesPath);
	            Properties properties = new Properties();
	            
	            performer.getCommunicator().sendAlertServerMessage("Reading from the config file.");
	            properties.load(stream);
	            
	            bountymod.doconfig(properties);
				bountymod.logger.info("Reloading configuration.");
	            performer.getCommunicator().sendAlertServerMessage("Loading all options.");

	        }
	        catch (Exception ex) {
	            bountymod.logger.log(Level.SEVERE, "Error while reloading properties file.", ex);
	            performer.getCommunicator().sendAlertServerMessage("Error reloading the config file, check the server log.");
	        }
	        finally {
	            try {
	                if (stream != null)
	                    stream.close();
	            }
	            catch (Exception ex) {
					bountymod.logger.log(Level.SEVERE, "Properties file not closed, possible file lock.", ex);
	                performer.getCommunicator().sendAlertServerMessage("Error closing the config file, possible file lock.");
	            }
	        }			
		}

		return true;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}