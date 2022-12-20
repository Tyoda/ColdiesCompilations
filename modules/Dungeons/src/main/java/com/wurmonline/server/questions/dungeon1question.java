package com.wurmonline.server.questions;


import com.wurmonline.server.FailedException;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.NoSuchActionException;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wurmonline.server.items.WurmColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.gotti.wurmunlimited.modsupport.ModSupportDb;

import net.coldie.tools.BmlForm;
import net.coldie.wurmunlimited.mods.dungeons.dungeon1action;
import net.coldie.wurmunlimited.mods.dungeons.dungeonmain;


public class dungeon1question extends Question
{
	  private boolean properlySent = false;
	  
	  dungeon1question(Creature aResponder, String aTitle, String aQuestion, int aType, long aTarget)
	  {
	    super(aResponder, aTitle, aQuestion, aType, aTarget);
	  }
	  
	  public dungeon1question(Creature aResponder, String aTitle, String aQuestion, long aTarget)
	  {
	    super(aResponder, aTitle, aQuestion, 79, aTarget);
	  }
	  private static Logger logger = Logger.getLogger(dungeon1question.class.getName());	  
	  boolean bought = false;	
	  
	  
public void buyitem(String itemname, int zennycost, int itemid, float ql, byte rarity, int multibuy, byte material){
	String charname = getResponder().getName();	
    	Item item;
        if (getResponder().getInventory().getNumItemsNotCoins() <= 100-multibuy)
        {
        	
        	zennycost = zennycost * multibuy;
	Connection dbcon2 = null;
    PreparedStatement ps2 = null;	
	int starter = 0;
	String[] dungeon1colorsplit = dungeonmain.dungeon1color.split(",");	        
    int R = Integer.parseInt(dungeon1colorsplit[0]);
    int G = Integer.parseInt(dungeon1colorsplit[1]);
    int B = Integer.parseInt(dungeon1colorsplit[2]);
    
	while (starter < multibuy){   
			try {
				item = ItemFactory.createItem(itemid, ql, getResponder().getName());
				if (itemid == 4200){
					item.setDataXY(555, 243); //1100, 3530
				}
				item.setRarity((byte) rarity);
				item.setName(itemname);
				if(R!=256) {item.setColor(WurmColor.createColor(R, G, B));}
				item.setMaterial(material);
				getResponder().getInventory().insertItem(item, true);
				
			} catch (FailedException e) {
		
				e.printStackTrace();
			} catch (NoSuchTemplateException e) {
		
				e.printStackTrace();
			}
	starter=starter+1;}
      try
      {	      
	      dbcon2 = ModSupportDb.getModSupportDb();
	      ps2 = dbcon2.prepareStatement("UPDATE DungeonCurrency SET dungeon1 = dungeon1 - "+zennycost+" WHERE name = \""+charname+"\"");
	      ps2.executeUpdate();
	      ps2.close();
	      dbcon2.close();
	      bought = true;
	      logger.log(Level.INFO, charname+" spent "+zennycost+" "+dungeonmain.currencyname1+" on "+multibuy+"x "+ql+"ql "+itemname);
	      getResponder().getCommunicator().sendNormalServerMessage("You spent "+zennycost+" "+dungeonmain.currencyname1+" on "+multibuy+"x "+ql+"ql "+itemname);
    }
  catch (SQLException e) {
      throw new RuntimeException(e);
    }
        }else{
        	getResponder().getCommunicator().sendNormalServerMessage("you dont have enough room");
        	//purely to stop message saying not enough zenny
        	bought = true;
        	
        }
}	


	  public void answer(Properties answer)
	  {
	    if (!properlySent) {
	      return;
	    }

	    // check drop down and accepted
	    boolean accepted = (answer.containsKey("accept")) && (answer.get("accept") == "true");

	    if (accepted)
	    {
	    	bought = false;
	        String val = answer.getProperty("currency");
	        
	        if (val != null && !val.equals("nothing")){
//getResponder().getCommunicator().sendNormalServerMessage(val);
	          int zenny = 0;
	        	Connection dbcon = null;
	  	      PreparedStatement ps = null;
	  	      ResultSet rs = null;
	  	      try
	  	      {	      
	  	      dbcon = ModSupportDb.getModSupportDb();
	  	      ps = dbcon.prepareStatement("SELECT * FROM DungeonCurrency WHERE name = \""+getResponder().getName()+"\"");
	  	      rs = ps.executeQuery();
	  	      zenny = rs.getInt("dungeon1");

	  	      rs.close();
	  	      ps.close();
	  	      dbcon.close();
	  	    }
	  	      catch (SQLException e) {
	  	          throw new RuntimeException(e);
	  	        }	
	  	   int temonum = Integer.parseInt(answer.getProperty("multibuy"));
	  	   if (temonum == 9)temonum = 29;
	  	   int numbertobuy = temonum+1;
			 String[] dungeon1itemssplit = dungeonmain.dungeon1items.split(",");
		        int dungeon1itemssplitlength = dungeon1itemssplit.length;
		        int n2 = 0;
		        //name,id,ql,price,rarity
		        while (n2 < dungeon1itemssplitlength) {
		        	int id = Integer.parseInt(dungeon1itemssplit[n2+1]);
		        	float ql = Float.parseFloat(dungeon1itemssplit[n2+2]);
		        	int price = Integer.parseInt(dungeon1itemssplit[n2+3]);
		        	byte rarity = (byte) Integer.parseInt(dungeon1itemssplit[n2+4]);
		        	byte material = (byte) Integer.parseInt(dungeon1itemssplit[n2+5]);
		        	if (val.equals(dungeon1itemssplit[n2]) && zenny >= price*numbertobuy){
		        		buyitem(dungeon1itemssplit[n2],price,id, ql,rarity,numbertobuy,material);
		        	}
		        	n2=n2+6;
			 	}	        
			
//Treasure map	        	
			/**if (val.equals("treasuremap") && zenny >= 1000){
			buyitem("Treasure Map",1000,4200, 90.0F);	        	
			}	**/		
 			if (bought == false){
 				getResponder().getCommunicator().sendNormalServerMessage("You don't have enough for that.");
 			}
	        }
	        else {
	          getResponder().getCommunicator().sendNormalServerMessage("You didn't pick anything.");
	        }
	    }
	  }

	  public void sendQuestion()
	  {
	    boolean ok = true;
	    

	      try {
	        ok = false;
	        Action act = getResponder().getCurrentAction();
	        if (act.getNumber() == dungeon1action.actionId) {
	          ok = true;
	        }
	      }
	      catch (NoSuchActionException act) {
	        throw new RuntimeException("No such action", act);
	      }
	      
	    if (ok) {
	      properlySent = true;
 
	      BmlForm f = new BmlForm("");
	      
	      f.addHidden("id", id+"");
	      f.addBoldText(getQuestion(), new String[0]);
	      f.addText("\n ", new String[0]);
          int zenny = 0;
          Connection dbcon = null;
  	      PreparedStatement ps = null;
  	      ResultSet rs = null;
  	      try
  	      {	      
  	      dbcon = ModSupportDb.getModSupportDb();
  	      ps = dbcon.prepareStatement("SELECT * FROM DungeonCurrency WHERE name = \""+getResponder().getName()+"\"");
  	      rs = ps.executeQuery();
  	      zenny = rs.getInt("dungeon1");
  	      rs.close();
  	      ps.close();
  	      dbcon.close();
  	    }
  	      catch (SQLException e) {
  	          throw new RuntimeException(e);
  	        }	      
	      f.addBoldText("You have "+zenny+" "+dungeonmain.currencyname1+" in your account\n\n",new String[0]);
// add here
	      f.addRaw("radio{ group='currency'; id='nothing';text='buy nothing'}");
	      f.addText("\n ", new String[0]);
	     f.addRaw("harray{label{text='Number of items you want to buy.'}dropdown{id='multibuy';options='1,2,3,4,5,6,7,8,9,30'}}");
	     f.addBoldText("\n -- Rarity: 0 = normal, 1 = Rare, 2 = Supreme, 3 = Fantastic --\n", new String[0]);
			 String[] dungeon1itemssplit = dungeonmain.dungeon1items.split(",");
		        int dungeon1itemssplitlength = dungeon1itemssplit.length;
		        int n2 = 0;
		        //name,id,ql,price,rarity
		        while (n2 < dungeon1itemssplitlength) {
		        	int chars = dungeon1itemssplit[n2].length();
		        	String spacer = dungeon1itemssplit[n2];
		        	int counter = 0;
		        	while (counter < 30 - chars){
		        		spacer = spacer+" ";
		        	++counter;
		        	}
		        	f.beginHorizontalFlow();
		        	//hover='Test this"+dungeon1itemssplit[n2]+"';
					f.addRaw("radio{  group='currency'; id='"+dungeon1itemssplit[n2]+"';	text='"+spacer+"  QL:"+dungeon1itemssplit[n2+2]+"   Price:"+dungeon1itemssplit[n2+3]+"   Rarity:"+dungeon1itemssplit[n2+4]+"'}");
				 	n2=n2+6;
				 	f.endHorizontalFlow();
			 	}
            
	      
//stop here
	      f.addText("\n ", new String[0]);
	      f.beginHorizontalFlow();
	 	  f.addButton("Buy Now", "accept");
	      f.addText("               ", new String[0]);
	      f.addButton("Cancel", "decline");
	      f.endHorizontalFlow();
	      f.addText(" \n", new String[0]);
	      
	      getResponder().getCommunicator().sendBml(
	        400, 
	        500, 
	        true, 
	        true, 
	        f.toString(), 
	        150, 
	        150, 
	        200, 
	        title);
	    }
	  }

}