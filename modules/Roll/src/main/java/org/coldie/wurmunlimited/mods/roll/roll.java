package org.coldie.wurmunlimited.mods.roll;


import com.wurmonline.server.Server;
import com.wurmonline.server.creatures.Communicator;
import org.gotti.wurmunlimited.modloader.interfaces.PlayerMessageListener;
import org.gotti.wurmunlimited.modloader.interfaces.Versioned;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import java.util.HashMap;
import java.util.StringTokenizer;


public class roll implements WurmServerMod, PlayerMessageListener, Versioned {
	public static final String version = "ty1.0";
	public static HashMap<Integer, Integer> results = new HashMap<>();

	@Override
	public boolean onPlayerMessage(Communicator communicator, String message){
		if ((message != null) && (message.startsWith("/roll"))) {
			boolean working = true;
			int max = 0;
			int total = 0;
			StringTokenizer tokens = new StringTokenizer(message);
			tokens.nextToken();
			try{
				max = Integer.parseInt(tokens.nextToken());
				total = Integer.parseInt(tokens.nextToken());
			} catch (NumberFormatException numberFormatException) {
				//communicator.getPlayer().getCurrentTile().broadCast(communicator.getPlayer().getName()+" messed up, its /roll number number");
				working = false;
				// empty catch block
			}

			if (total == 0 || max == 0)working = false;
			if (total > 50)working = false;
			if (working){
				//communicator.getPlayer().getCurrentTile().broadCast(communicator.getPlayer().getName()+" max: "+max+" total:"+total);
				results.clear();

				if (max >= total){
					results.put(1, (Server.rand.nextInt(max))+1);
					while (results.size() < total){
						int nextresult = (Server.rand.nextInt(max))+1;
						if (!results.containsValue(nextresult)){
							results.put(results.size()+1,nextresult);
							//communicator.getPlayer().getCurrentTile().broadCast(communicator.getPlayer().getName() + " rolls " +nextresult);
						}
					}
					int i = 1;
					StringBuilder mess = new StringBuilder("(");
					while (i < results.size()){
						mess.append(results.get(i)).append(",");
						++i;
					}
					mess.append(results.get(results.size()));
					communicator.getPlayer().getCurrentTile().broadCast(communicator.getPlayer().getName() + " people: "+max+" rolls:"+total+" "+mess+")");
				}else{
					communicator.getPlayer().getCurrentTile().broadCast(communicator.getPlayer().getName() + " don't do rolls more than "+total+" players.");
				}
			}else{
				communicator.getPlayer().getCurrentTile().broadCast(communicator.getPlayer().getName() + " messed up.");
			}

			return true;
		}

		return false;
	}

	@Override
	public String getVersion(){
		return version;
	}
}