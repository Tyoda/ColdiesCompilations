package org.coldie.wurmunlimited.mods.onetilemining;


import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

public class onetilereloadaction implements ModAction, BehaviourProvider, ActionPerformer {
	public final short actionId;
	public final ActionEntry actionEntry;

	public onetilereloadaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Reload onetilemining properties", "Reloading onetilemining properties", new int[0]);
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		return getBehavioursFor(performer, target);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {
		if (performer instanceof Player && performer.getPower() > 4)
				return Collections.singletonList(actionEntry);

		return null;
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		return action(act, performer, target, action, counter);
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item target, short action, float counter) {
		if (performer instanceof Player && performer.getPower() > 4) {
			Path path = Paths.get("mods/onetilemining.properties");

			if (!Files.exists(path)) {
				performer.getCommunicator().sendAlertServerMessage("The config file seems to be missing.");
				return true;
			}
			InputStream stream = null;
			try {
				performer.getCommunicator().sendAlertServerMessage("Opening the config file.");
				stream = Files.newInputStream(path);
				Properties properties = new Properties();

				performer.getCommunicator().sendAlertServerMessage("Reading from the config file.");
				properties.load(stream);

				onetilemining.doconfig(properties);
				onetilemining.logger.info("Reloading configuration.");
				performer.getCommunicator().sendAlertServerMessage("Loading all options.");
			}
			catch (IOException ex) {
				onetilemining.logger.log(Level.SEVERE, "Error while reloading properties file.", ex);
				performer.getCommunicator().sendAlertServerMessage("Error reloading the config file, check the server log.");
			}
			finally {
				try {
					if (stream != null)
						stream.close();
				}
				catch (IOException ex) {
					onetilemining.logger.log(Level.SEVERE, "Properties file not closed, possible file lock.", ex);
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