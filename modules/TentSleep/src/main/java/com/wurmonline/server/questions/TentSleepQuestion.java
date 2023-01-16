package com.wurmonline.server.questions;


import com.wurmonline.server.Players;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.NoSuchActionException;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import org.coldie.wurmunlimited.mods.tentsleep.SleepAction;

import java.util.Properties;

public class TentSleepQuestion extends Question {
		private boolean properlySent = false;
		private final Item item;

		public TentSleepQuestion(Creature aResponder, String aTitle, String aQuestion, Item aTarget) {
			super(aResponder, aTitle, aQuestion, 79, aTarget.getWurmId());
			item = aTarget;
		}

		@Override
		public void answer(Properties answers) {
			if (!properlySent) {
				return;
			}

			String val = answers.getProperty("sleep");
			if (val != null && val.equals("true")) {
				try {
					getResponder().getCurrentAction();
					getResponder().getCommunicator().sendNormalServerMessage("You are too busy to sleep right now.");
				}
				catch (NoSuchActionException nsa) {
					getResponder().getCommunicator().sendShutDown("You went to sleep. Sweet dreams.", true);
					((Player)getResponder()).getSaveFile().setBed(item.getWurmId());
					//((Player)getResponder()).setLogout();
					Server.getInstance().broadCastAction(getResponder().getName() + " goes to sleep in " + item.getNameWithGenus() + ".", getResponder(), 5);
					if (getResponder().hasLink()) {
						getResponder().setSecondsToLogout(2);
					} else {
						Players.getInstance().logoutPlayer((Player) getResponder());
					}
				}
			} else {
				getResponder().getCommunicator().sendNormalServerMessage("You decide not to go to sleep right now.");
			}
		}

		@Override
		public void sendQuestion() {
			boolean ok = false;
			try {
				Action act = getResponder().getCurrentAction();
				if (act.getNumber() == SleepAction.actionId) {
					ok = true;
				}
			} catch (NoSuchActionException act) {
				logger.severe("Action not found: ");
				throw new RuntimeException(act);
			}

			if (ok) {
				properlySent = true;

				StringBuilder buf = new StringBuilder()
				   .append(this.getBmlHeader())
				   .append("text{text='Do you want to go to sleep? You will log off Wurm.'}text{text=''}")
				   .append("text{text='You get less sleep bonus from a tent than a bed.'}text{text=''}")
				   .append("radio{ group='sleep'; id='true';text='Yes';selected='true'}")
				   .append("radio{ group='sleep'; id='false';text='No'}")
				   .append(createAnswerButton2());
				getResponder().getCommunicator().sendBml(300, 300, true, true, buf.toString(), 200, 200, 200, this.title);
			}
		}
}