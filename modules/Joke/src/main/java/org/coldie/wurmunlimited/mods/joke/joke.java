package org.coldie.wurmunlimited.mods.joke;

import org.gotti.wurmunlimited.modloader.interfaces.PlayerLoginListener;
import org.gotti.wurmunlimited.modloader.interfaces.PlayerMessageListener;
import org.gotti.wurmunlimited.modloader.interfaces.Versioned;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import com.wurmonline.server.creatures.Communicator;
import com.wurmonline.server.players.Player;

public class joke implements WurmServerMod, PlayerMessageListener, PlayerLoginListener, Versioned {
	public static final String version = "ty1.0"; // Yep I just slapped my version number on this one
	@Override
	public boolean onPlayerMessage(Communicator communicator, String message) {
		if(message != null && message.startsWith("/joke")) {
			communicator.sendSafeServerMessage("Why did the turkey cross the road....");
			communicator.sendSafeServerMessage("To prove it wasn't chicken.");
			return true;
		}
		return false;
	}

	@Override
	public void onPlayerLogin(Player player) {
		player.getCommunicator().sendSafeServerMessage("Hi welcome to the server, stay a while and listen.");
	}

	@Override
	public String getVersion(){
		return version;
	}
}