package com.wurmonline.server.questions;


import com.wurmonline.server.Items;
import com.wurmonline.server.NoSuchItemException;
import com.wurmonline.server.Players;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.NoSuchActionException;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;


import java.util.Properties;

import org.coldie.wurmunlimited.mods.tentsleep.tent;

public class tentsleepquestion extends Question
{
	  private boolean properlySent = false;
	  private Item item ;
	  tentsleepquestion(Creature aResponder, String aTitle, String aQuestion, int aType, long aTarget)
	  {
	    super(aResponder, aTitle, aQuestion, aType, aTarget);
	  }
	  
	  public tentsleepquestion(Creature aResponder, String aTitle, String aQuestion, long aTarget)
	  {
	    super(aResponder, aTitle, aQuestion, 79, aTarget);
	  } 

@Override
    public void answer(Properties answers) {
    	if (!properlySent) {
	      return;
	    }
    	try {
			item = Items.getItem(target);
		} catch (NoSuchItemException e) {
			e.printStackTrace();
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
	    boolean ok = true;
	    

	      try {
	        ok = false;
	        Action act = getResponder().getCurrentAction();
	        if (act.getNumber() == tent.actionId) {
	          ok = true;
	        }
	      }
	      catch (NoSuchActionException act) {
	        throw new RuntimeException("No such action", act);
	      }
	    
	    if (ok) {
	      properlySent = true;   	
    	
        StringBuilder buf = new StringBuilder();
        buf.append(this.getBmlHeader());
        buf.append("text{text='Do you want to go to sleep? You will log off Wurm.'}text{text=''}");
        buf.append("radio{ group='sleep'; id='true';text='Yes';selected='true'}");
        buf.append("radio{ group='sleep'; id='false';text='No'}");
        buf.append(createAnswerButton2());
        getResponder().getCommunicator().sendBml(300, 300, true, true, buf.toString(), 200, 200, 200, this.title);
    }
	    }
	    
}