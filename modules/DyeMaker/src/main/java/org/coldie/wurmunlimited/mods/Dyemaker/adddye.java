package org.coldie.wurmunlimited.mods.Dyemaker;


import com.wurmonline.server.Items;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.WurmColor;
import com.wurmonline.server.players.Player;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

public class adddye implements ModAction, BehaviourProvider, ActionPerformer {
	// public static final Logger logger = Logger.getLogger(adddye.class.getName());

	public final short actionId;
	public final ActionEntry actionEntry;

	public adddye() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Add dye", "Adding dye", new int[]{}); 
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		if (performer instanceof Player && target.getTemplateId() == Dyemaker.targetid && source.isDye())
			return Collections.singletonList(actionEntry);

		return null;
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		if (performer instanceof Player && target.getTemplateId() == Dyemaker.targetid && source.isDye() ) {
			final float sourceQl = source.getQualityLevel()/100;
			final int redSource = WurmColor.getColorRed(source.getColor());
			final int greenSource = WurmColor.getColorGreen(source.getColor());
			final int blueSource = WurmColor.getColorBlue(source.getColor());
			final int volumeSource = source.getVolume();
			final int newRed = (int)((redSource*volumeSource/255)*Dyemaker.losspercent*sourceQl)/100;
			final int newGreen = (int)((greenSource*volumeSource/255*Dyemaker.losspercent*sourceQl)/100);
			final int newBlue = (int)((blueSource*volumeSource/255*Dyemaker.losspercent*sourceQl)/100);

			if(redSource+greenSource+blueSource >= Dyemaker.maxrgb) {
				performer.getCommunicator().sendSafeServerMessage("That dye is to powerful for the device to process, R+G+B must be less than "+Dyemaker.maxrgb);
			}
			else {
				if (target.getData1() == -1) {
					Dyemaker.getdata(target);
				}
				target.setAllData(newRed + target.getData1(), newGreen + target.getData2(), newBlue + target.getExtra1(), 1);
				Items.destroyItem(source.getWurmId());
				performer.getCommunicator().sendSafeServerMessage(
						String.format("You added red:%f green:%f blue:%f", (float) newRed / 1000, (float) newGreen / 1000, (float) newBlue / 1000)
				);
			}
		}

		return true;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}