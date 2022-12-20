package org.coldie.wurmunlimited.mods.servertimemessage;

import com.wurmonline.server.Server;
import com.wurmonline.server.creatures.Communicator;
import com.wurmonline.server.players.Player;
import org.gotti.wurmunlimited.modloader.interfaces.*;

import java.text.SimpleDateFormat;
import java.util.Properties;

public class servertimemessage implements WurmServerMod, Configurable, ServerPollListener, PlayerMessageListener, Versioned {
	public static final String version = "ty1.0";
	private static long lastPoll = System.currentTimeMillis();
	private static final int pollFrequency = (5*1000); //5 seconds // TODO: configurable
	private final SimpleDateFormat dfh = new SimpleDateFormat("HH");
	private final SimpleDateFormat dfm = new SimpleDateFormat("mm");
	private final SimpleDateFormat dft = new SimpleDateFormat("HH:mm");
	private static boolean time30 = false;
	private static boolean time10 = false;
	private static boolean time5 = false;
	private static boolean time2 = false;
	private static boolean time1 = false;
	private static int alarmhour = 5;
	private static int alarmminute = 55;
	 
	 
	@Override
	public void configure(Properties properties) {
		alarmhour = Integer.parseInt(properties.getProperty("alarmhour", Integer.toString(alarmhour)));
		alarmminute = Integer.parseInt(properties.getProperty("alarmminute", Integer.toString(alarmminute)));
	}

    @Override
    public boolean onPlayerMessage(Communicator communicator, String msg) {
        if (msg.startsWith("/servertime")) {
        	Player player = communicator.player;
        	if(player.getPower() > 3) {
        		String DFH = dft.format(System.currentTimeMillis());
        		player.getCommunicator().sendNormalServerMessage("Server time is "+DFH);
        	}
        	return true;
        }
        return false;
    }
    
	@Override
	public void onServerPoll() {
		long currMillis = System.currentTimeMillis();
		if (lastPoll + pollFrequency > currMillis) return;

		lastPoll = currMillis;
		String DFH = dfh.format(currMillis);
		String DFM = dfm.format(currMillis);
		int DH = Integer.parseInt(DFH);
		int DM = Integer.parseInt(DFM);
		if(!time30 && DH == alarmhour && DM == alarmminute-30) {
			Server.getInstance().broadCastAlert("The server is rebooting in 30 minutes. ", true, (byte)1);
			time30 = true;
		}
		if(!time10 && DH == alarmhour && DM == alarmminute-10) {
			Server.getInstance().broadCastAlert("The server is rebooting in 10 minutes. ", true, (byte)1);
			time10 = true;
		}
		if(!time5 && DH == alarmhour && DM == alarmminute-5) {
			Server.getInstance().broadCastAlert("The server is rebooting in 5 minutes. ", true, (byte)1);
			time5 = true;
		}
		if(!time2 && DH == alarmhour && DM == alarmminute-2) {
			Server.getInstance().broadCastAlert("The server is rebooting in 2 minutes. ", true, (byte)1);
			time2 = true;
		}
		if(!time1 && DH == alarmhour && DM == alarmminute-1) {
			Server.getInstance().broadCastAlert("The server is rebooting in 1 minute. ", true, (byte)1);
			time1 = true;
		}
	}

	@Override
	public String getVersion(){
		return version;
	}
}