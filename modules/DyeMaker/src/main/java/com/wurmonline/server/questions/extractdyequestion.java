package com.wurmonline.server.questions;


import com.wurmonline.server.*;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.MethodsItems;
import com.wurmonline.server.behaviours.NoSuchActionException;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.NoSuchCreatureException;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wurmonline.server.items.WurmColor;
import net.coldie.tools.BmlForm;
import org.coldie.wurmunlimited.mods.Dyemaker.Dyemaker;

import java.util.Properties;



public class extractdyequestion extends Question {
	//public static final Logger logger = Logger.getLogger(extractdyequestion.class.getName());
	private boolean properlySent = false;

	private int availableWeight;

	extractdyequestion(Creature aResponder, String aTitle, String aQuestion, int aType, long aTarget) {
		super(aResponder, aTitle, aQuestion, aType, aTarget);
	}

	public extractdyequestion(Creature aResponder, String aTitle, String aQuestion, long aTarget) {
		super(aResponder, aTitle, aQuestion, 79, aTarget);
	}

	@SuppressWarnings("null")
	public void answer(Properties answer) {
		if (!properlySent) return;

		// check drop down and accepted
		boolean accepted = (answer.containsKey("accept")) && (answer.get("accept") == "true");

		if (accepted) {
			int red = Integer.parseInt((String) answer.get("red"));
			int green = Integer.parseInt((String) answer.get("green"));
			int blue = Integer.parseInt((String) answer.get("blue"));
			int volume = Integer.parseInt((String) answer.get("volume"));
			float quality = Float.parseFloat((String) answer.get("quality"));

			float redNeeded = (quality * volume * red * 10)/255;
			float greenNeeded = (quality * volume * green * 10)/255;
			float blueNeeded = (quality * volume * blue * 10)/255;


			long itemWurmid = Long.parseLong((String) answer.get("itemid"));
			long sourceWurmid = Long.parseLong((String) answer.get("sourceid"));
			long playerId = Long.parseLong((String) answer.get("playerid"));

			Item target;
			Item source;
			Creature performer;
			Item item;
			try {
				target = Items.getItem(itemWurmid);
				source = Items.getItem(sourceWurmid);
				performer = Server.getInstance().getCreature(playerId);

				if (red > 255 || Dyemaker.minimumextraction > red || green > 255 || Dyemaker.minimumextraction > green ||
				blue > 255 || Dyemaker.minimumextraction > blue || quality > 100 || 1 > quality || 1 > volume) {
					performer.getCommunicator().sendSafeServerMessage("Incorrect values.");
					return;
				}
				if(target.getData1() < redNeeded ||  target.getData2() < greenNeeded || target.getExtra1() < blueNeeded) {
					performer.getCommunicator().sendSafeServerMessage("Not enough Dye for what you asked for.");
				}else if(volume*1000 > availableWeight) {
					performer.getCommunicator().sendSafeServerMessage("You can't carry that much weight.");
				}
				else{
					item = ItemFactory.createItem(438, quality, null);
					int c = WurmColor.createColor(red, green, blue);
					item.setColor(c);
					item.setWeight(volume*1000, true);
					MethodsItems.fillContainer(Dyemaker.act, source, item, performer, false);
					performer.getCommunicator().sendSafeServerMessage(
						String.format("You extracted %f dye with R,G,B (%d, %d, %d).", (float)volume, red, green, blue)
					);

					target.setData1((int) (target.getData1()-redNeeded));
					target.setData2((int) (target.getData2()-greenNeeded));
					target.setExtra1((int) (target.getExtra1()-blueNeeded));
				}
			} catch (NoSuchItemException | NoSuchPlayerException | NoSuchCreatureException | FailedException |
					NoSuchTemplateException e) { throw new RuntimeException(e); }
		}
	}

	public void sendQuestion() {
		boolean ok;
		try {
			ok = false;
			Action act = getResponder().getCurrentAction();
			if (act.getNumber() == Dyemaker.removeActionId) {
				ok = true;
			}
		} catch (NoSuchActionException act) { throw new RuntimeException("No such action", act); }

		if (ok) {
			properlySent = true;
			int maxVolume = Dyemaker.source.getContainerVolume();
			availableWeight = Dyemaker.performer.getCarryingCapacityLeft();


			BmlForm f = new BmlForm("");

			f.addHidden("id", id+"");
			f.addBoldText(getQuestion());

			f.addText("The container can carry maximum "+ maxVolume /1000);
			f.addText("The can carry "+ availableWeight /1000+" more weight");

			f.addText("Your device has "+Dyemaker.item.getData1()/1000+" maximum red dye ");
			f.addText("Your device has "+Dyemaker.item.getData2()/1000+" maximum green dye ");
			f.addText("Your device has "+Dyemaker.item.getExtra1()/1000+" maximum blue dye ");
			f.addText("This Device isn't perfect and can only extract dye with minimum values of "+Dyemaker.minimumextraction+" on each RGB.");
			f.addText("Red value");
			f.addInput("red", 3, ""+Dyemaker.minimumextraction);
			f.addText("Green value");
			f.addInput("green", 3, ""+Dyemaker.minimumextraction);
			f.addText("Blue value");
			f.addInput("blue", 3, ""+Dyemaker.minimumextraction);
			f.addText("Quality value, minimum of 1.");
			f.addInput("quality", 3, "1");
			f.addText("Volume value, minimum of 1.");
			f.addInput("volume", 3, "1");


			f.beginHorizontalFlow();
			f.addButton("Extract Dye", "accept");
			f.addText("               ");
			f.addButton("Cancel", "decline");

			f.addHidden("itemid", ""+Dyemaker.item.getWurmId());
			f.addHidden("sourceid", ""+Dyemaker.source.getWurmId());
			f.addHidden("playerid", ""+Dyemaker.performer.getWurmId());


			f.endHorizontalFlow();
			f.addText(" \n");
			f.addText(" \n");

			getResponder().getCommunicator().sendBml(
					300,
					350,
					true,
					true,
					f.toString(),
					150,
					150,
					200,
					title
			);
		}
	}

}