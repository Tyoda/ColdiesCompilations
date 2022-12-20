package org.coldie.wurmunlimited.mods.milkreset;

import com.wurmonline.server.creatures.Communicator;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.Creatures;
import com.wurmonline.server.players.Player;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.PlayerMessageListener;
import org.gotti.wurmunlimited.modloader.interfaces.Versioned;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class milkreset implements PlayerMessageListener, Configurable, WurmServerMod, Versioned {
	private static long lastDidMilkable = System.currentTimeMillis();
	private static int milkHours = 1;
	private static int GMLevel = 4;
	public static final Logger logger = Logger.getLogger(milkreset.class.getName());
	public static final String version = "ty1.0";
   
	@Override
	public void configure(Properties properties) {
		GMLevel = Integer.parseInt(properties.getProperty("GMlevel", Float.toString(GMLevel)));
		milkHours = Integer.parseInt(properties.getProperty("milkhours", Float.toString(milkHours)));
	}	
	
	public void resetmilk() {
		logger.log(Level.INFO, "Milking reset");
		Creature[] creatures = Creatures.getInstance().getCreatures();
		for (Creature creature : creatures) {
			if (creature.isMilkable())
				creature.setMilked(false);
			if (creature.isSheared())
				creature.setSheared(false);
		}
	}
	
	@Override
	public boolean onPlayerMessage(Communicator communicator, String msg) {
		String msgLower = msg.toLowerCase();

		Player p = communicator.getPlayer();

		long millis = System.currentTimeMillis();

		if (p.getPower() >= GMLevel && (msgLower.startsWith("/milk") || msgLower.startsWith("/shear"))) {
			float timeLeft = (1000L * 60L * 60L * milkHours) - (millis - lastDidMilkable);
			if(timeLeft <= 0f) {
				resetmilk();
				lastDidMilkable = millis;
				communicator.sendNormalServerMessage("Milking and shearing is possible again.");
			}else {
				if (timeLeft > 60000f) {
					communicator.sendNormalServerMessage("Time until next reset: "+(int)Math.floor(timeLeft/60000)+" minutes");
				}else {
					communicator.sendNormalServerMessage("Time until next reset: "+(int)Math.floor(timeLeft/1000)+" seconds");
				}
			}
			return true;
		}

		return false;
	}

	@Override
	public String getVersion() {
		return version;
	}
}