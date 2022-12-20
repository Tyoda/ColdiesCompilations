package net.coldie.wurmunlimited.mods.dungeons;

import com.friya.wurmonline.server.loot.BeforeDropListener;
import com.friya.wurmonline.server.loot.LootResult;
import com.friya.wurmonline.server.loot.LootSystem;
import com.wurmonline.server.Server;
import com.wurmonline.server.bodys.Wound;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.deities.Deities;
import com.wurmonline.server.deities.Deity;
import com.wurmonline.server.economy.Economy;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.gotti.wurmunlimited.modsupport.ModSupportDb;

public class currencyadding
implements BeforeDropListener {
    private static currencyadding instance;

    currencyadding() {
    }

    public static currencyadding getInstance() {
        if (instance == null) { 
            instance = new currencyadding();
        }
        return instance;
    }

    @SuppressWarnings("unused")
	public boolean onBeforeDrop(LootResult lootResult) {
        Creature[] arrattackers = lootResult.getKillers();
        Creature thisCreature = lootResult.getCreature();
        String name = thisCreature.getName();
        
        int X = (int) thisCreature.getPosX()/4;
        int Y = (int) thisCreature.getPosY()/4;
        boolean pvp = false;
        boolean pvpevil = false;
        int n = arrattackers.length;
        int n2 = 0;
        while (n2 < n) {
        	Creature i = arrattackers[n2];
        	if (thisCreature.isPlayer()){
        		if (i.getKingdomId() != thisCreature.getKingdomId()){
            		pvp = true;       		
        		}else{
        			pvpevil = true;
        		}
        	};
        	
        	if (i.isPlayer()){
        		
		        Deity Loki = null; //Deities.getDeity(106);
		        
		        if(Loki != null){
		        	if(i.getDeity().getName().equals("Loki") && thisCreature.getFightingSkill().getKnowledge() >= 40){
		        		//10% chance at each.
		            switch (Server.rand.nextInt(11)) {
		            case 0: {
			            	//i.getCommunicator().sendSafeServerMessage("faith: "+i.getFaith() + "favor: "+i.getFavor());
	
			        		if (i.getFaith() - i.getFavor() > 40){
			        			try {
									i.setFavor(i.getFavor() + 10);
									i.getCommunicator().sendSafeServerMessage("Loki gave you 10 favor.");
								} catch (IOException e) {
									e.printStackTrace();
								}
			        		}else{
			        			i.getCommunicator().sendSafeServerMessage("Loki would have given you favor but she decided you have enough");	
			        		};
			        		break;
			        	}
		            case 1: {
						try {
							int payment = (int)Math.floor(500);//5 copper
							i.addMoney((long) payment);
							String bountymessage = Economy.getEconomy().getChangeFor((long) payment).getChangeString();
							i.getCommunicator().sendSafeServerMessage("Loki makes "+bountymessage+" get deposited into your bank.");		  					

						} catch (IOException e1) {
							e1.printStackTrace();
						}	
						break;

		            }
		            
		            case 2:{
		                Wound[] wounds2 = i.getBody().getWounds().getWounds();
		                int w2 = 0;
		                float worstwound = 0;
		                int worstpart = 0;
		                if (i.getBody().getWounds() != null && ( wounds2.length > 0)) {
		                    	while (w2 < wounds2.length){
		                        if (wounds2[w2].getSeverity() > worstwound ) {
		                        	worstwound = wounds2[w2].getSeverity();
		                        	worstpart = w2;
		                        }
		                        ++w2;}
		                        VolaTile t = Zones.getTileOrNull(i.getTileX(), i.getTileY(), i.isOnSurface());
		                        if (t != null) {
		                            t.sendAttachCreatureEffect(i, (byte)11, (byte)0, (byte)0, (byte)0, (byte)0);
		                        } 
		                        i.getCommunicator().sendSafeServerMessage("Loki says you shall be healed, slightly");
		                        if (wounds2[worstpart].getSeverity() / 1000.0f < 10) {
		                        	wounds2[worstpart].heal();
		                        }else{
		                        	wounds2[worstpart].modifySeverity((- 10) * 1000);
		                        }
		                	}	            	
		            }
		            case 10:{
		            	i.getCommunicator().sendSafeServerMessage("Luck was not on your side this time, you get nothing extra.");		            	
		            }
		            default:{
		            	i.getCommunicator().sendSafeServerMessage("Luck was not on your side this time, you get nothing extra.");
		            }
		       }

		        	};
		        }
                //dungeon1
                if (dungeonmain.currency1enabled){
	                if (dungeonmain.zone1minx <= X && X <= dungeonmain.zone1maxx && dungeonmain.zone1miny <= Y && Y <= dungeonmain.zone1maxy){ 
	                long bounty = dungeonmain.trashmobamount1;
	                String[] minibossmobsname1split = dungeonmain.minibossnames1.split(",");
	     	        int m = minibossmobsname1split.length;
	     	        int m2 = 0;
	     	        while (m2 < m) {
	     				 String minibossname = minibossmobsname1split[m2];
	     		        	if (name.contains(minibossname)){
	     		        		bounty = dungeonmain.minibossamount1;
	     		        	}
	     			 	++m2;
	     		 	}
	     	        
	     			 String[] bossmobsname1split = dungeonmain.bossmobnames1.split(",");
	     		        int p = bossmobsname1split.length;
	     		        int p2 = 0;
	     		        while (p2 < p) {
	     					 String bossname = bossmobsname1split[p2];
	     			        	if (name.contains(bossname)){
	     			        		bounty = dungeonmain.bossmobamount1;
	     			        	}
	     				 	++p2;
	     			 	} 
	                	Connection dbcon2 = null;
				      PreparedStatement ps2 = null;	
				      if (pvp){
				    	  Player player = (Player) thisCreature;
				    	  bounty = player.getRank() * 2;  
				      }
				      if (pvpevil)bounty = -dungeonmain.pvpevil;
				      try
				      {
					      dbcon2 = ModSupportDb.getModSupportDb();
					      ps2 = dbcon2.prepareStatement("UPDATE DungeonCurrency "
					      + "SET dungeon1 = dungeon1 + "+bounty+" WHERE name = \""+i.getName()+"\"");
					      ps2.executeUpdate();
					      ps2.close(); 
					      dbcon2.close();
					      //logger.log(Level.INFO, i.getName()+" is a player and gets "+bounty+" "+dungeonmain.currencyname1);
					      i.getCommunicator().sendSafeServerMessage("you got "+bounty+" "+dungeonmain.currencyname1);
				      }
			      catch (SQLException e) {
			          throw new RuntimeException(e);
			        }        		
	
	        		}
                }
                //end of dungeon1
                
                //dungeon2
                if (dungeonmain.currency2enabled){
	                if (dungeonmain.zone2minx <= X && X <= dungeonmain.zone2maxx && dungeonmain.zone2miny <= Y && Y <= dungeonmain.zone2maxy){ 
	                long bounty = dungeonmain.trashmobamount2;
	                String[] minibossmobsname2split = dungeonmain.minibossnames2.split(",");
	     	        int m = minibossmobsname2split.length;
	     	        int m2 = 0;
	     	        while (m2 < m) {
	     				 String minibossname = minibossmobsname2split[m2];
	     		        	if (name.contains(minibossname)){
	     		        		bounty = dungeonmain.minibossamount2;
	     		        	}
	     			 	++m2;
	     		 	}
	     	        
	     			 String[] bossmobsname2split = dungeonmain.bossmobnames2.split(",");
	     		        int p = bossmobsname2split.length;
	     		        int p2 = 0;
	     		        while (p2 < p) {
	     					 String bossname = bossmobsname2split[p2];
	     			        	if (name.contains(bossname)){
	     			        		bounty = dungeonmain.bossmobamount2;
	     			        	}
	     				 	++p2;
	     			 	} 
	                	Connection dbcon2 = null;
				      PreparedStatement ps2 = null;	  
				      if (pvp){
				    	  Player player = (Player) thisCreature;
				    	  bounty = player.getRank() * 2;  
				      }
				      if (pvpevil)bounty = -dungeonmain.pvpevil;
				      try
				      {
					      dbcon2 = ModSupportDb.getModSupportDb();
					      ps2 = dbcon2.prepareStatement("UPDATE DungeonCurrency "
					      + "SET dungeon2 = dungeon2 + "+bounty+" WHERE name = \""+i.getName()+"\"");
					      ps2.executeUpdate();
					      ps2.close(); 
					      dbcon2.close();
					      //logger.log(Level.INFO, i.getName()+" is a player and gets "+bounty+" "+dungeonmain.currencyname2);
					      i.getCommunicator().sendSafeServerMessage("you got "+bounty+" "+dungeonmain.currencyname2);
				      }
			      catch (SQLException e) {
			          throw new RuntimeException(e);
			        }        		
	
	        		}
                }
                //end of dungeon2
                
                //dungeon3
                if (dungeonmain.currency3enabled){
	                if (dungeonmain.zone3minx <= X && X <= dungeonmain.zone3maxx && dungeonmain.zone3miny <= Y && Y <= dungeonmain.zone3maxy){ 
	                long bounty = dungeonmain.trashmobamount3;
	                String[] minibossmobsname3split = dungeonmain.minibossnames3.split(",");
	     	        int m = minibossmobsname3split.length;
	     	        int m3 = 0;
	     	        while (m3 < m) {
	     				 String minibossname = minibossmobsname3split[m3];
	     		        	if (name.contains(minibossname)){
	     		        		bounty = dungeonmain.minibossamount3;
	     		        	}
	     			 	++m3;
	     		 	}
	     	        
	     			 String[] bossmobsname3split = dungeonmain.bossmobnames3.split(",");
	     		        int p = bossmobsname3split.length;
	     		        int p3 = 0;
	     		        while (p3 < p) {
	     					 String bossname = bossmobsname3split[p3];
	     			        	if (name.contains(bossname)){
	     			        		bounty = dungeonmain.bossmobamount3;
	     			        	}
	     				 	++p3;
	     			 	} 
	                	Connection dbcon3 = null;
				      PreparedStatement ps3 = null;	  
				      if (pvp){
				    	  Player player = (Player) thisCreature;
				    	  bounty = player.getRank() * 2;  
				      }
				      if (pvpevil)bounty = -dungeonmain.pvpevil;
				      try
				      {
					      dbcon3 = ModSupportDb.getModSupportDb();
					      ps3 = dbcon3.prepareStatement("UPDATE DungeonCurrency "
					      + "SET dungeon3 = dungeon3 + "+bounty+" WHERE name = \""+i.getName()+"\"");
					      ps3.executeUpdate();
					      ps3.close(); 
					      dbcon3.close();
					      //logger.log(Level.INFO, i.getName()+" is a player and gets "+bounty+" "+dungeonmain.currencyname3);
					      i.getCommunicator().sendSafeServerMessage("you got "+bounty+" "+dungeonmain.currencyname3);
				      }
			      catch (SQLException e) {
			          throw new RuntimeException(e);
			        }        		
	
	        		}
                }
                //end of dungeon3                
                
                //dungeon4
                if (dungeonmain.currency4enabled){
	                if (dungeonmain.zone4minx <= X && X <= dungeonmain.zone4maxx && dungeonmain.zone4miny <= Y && Y <= dungeonmain.zone4maxy){ 
	                long bounty = dungeonmain.trashmobamount4;
	                String[] minibossmobsname4split = dungeonmain.minibossnames4.split(",");
	     	        int m = minibossmobsname4split.length;
	     	        int m4 = 0;
	     	        while (m4 < m) {
	     				 String minibossname = minibossmobsname4split[m4];
	     		        	if (name.contains(minibossname)){
	     		        		bounty = dungeonmain.minibossamount4;
	     		        	}
	     			 	++m4;
	     		 	}
	     	        
	     			 String[] bossmobsname4split = dungeonmain.bossmobnames4.split(",");
	     		        int p = bossmobsname4split.length;
	     		        int p4 = 0;
	     		        while (p4 < p) {
	     					 String bossname = bossmobsname4split[p4];
	     			        	if (name.contains(bossname)){
	     			        		bounty = dungeonmain.bossmobamount4;
	     			        	}
	     				 	++p4;
	     			 	} 
	                	Connection dbcon4 = null;
				      PreparedStatement ps4 = null;	
				      if (pvp){
				    	  Player player = (Player) thisCreature;
				    	  bounty = player.getRank() * 2;  
				      }
				      if (pvpevil)bounty = -dungeonmain.pvpevil;
				      try
				      {
					      dbcon4 = ModSupportDb.getModSupportDb();
					      ps4 = dbcon4.prepareStatement("UPDATE DungeonCurrency "
					      + "SET dungeon4 = dungeon4 + "+bounty+" WHERE name = \""+i.getName()+"\"");
					      ps4.executeUpdate();
					      ps4.close(); 
					      dbcon4.close();
					      //logger.log(Level.INFO, i.getName()+" is a player and gets "+bounty+" "+dungeonmain.currencyname4);
					      i.getCommunicator().sendSafeServerMessage("you got "+bounty+" "+dungeonmain.currencyname4);
				      }
			      catch (SQLException e) {
			          throw new RuntimeException(e);
			        }        		
	
	        		}
                }
                //end of dungeon4
                
                //dungeon5
                if (dungeonmain.currency5enabled){
	                if (dungeonmain.zone5minx <= X && X <= dungeonmain.zone5maxx && dungeonmain.zone5miny <= Y && Y <= dungeonmain.zone5maxy){ 
	                long bounty = dungeonmain.trashmobamount5;
	                String[] minibossmobsname5split = dungeonmain.minibossnames5.split(",");
	     	        int m = minibossmobsname5split.length;
	     	        int m5 = 0;
	     	        while (m5 < m) {
	     				 String minibossname = minibossmobsname5split[m5];
	     		        	if (name.contains(minibossname)){
	     		        		bounty = dungeonmain.minibossamount5;
	     		        	}
	     			 	++m5;
	     		 	}
	     	        
	     			 String[] bossmobsname5split = dungeonmain.bossmobnames5.split(",");
	     		        int p = bossmobsname5split.length;
	     		        int p5 = 0;
	     		        while (p5 < p) {
	     					 String bossname = bossmobsname5split[p5];
	     			        	if (name.contains(bossname)){
	     			        		bounty = dungeonmain.bossmobamount5;
	     			        	}
	     				 	++p5;
	     			 	} 
	                	Connection dbcon5 = null;
				      PreparedStatement ps5 = null;	  
				      if (pvp){
				    	  Player player = (Player) thisCreature;
				    	  bounty = player.getRank() * 2;  
				      }
				      if (pvpevil)bounty = -dungeonmain.pvpevil;
				      try
				      {
					      dbcon5 = ModSupportDb.getModSupportDb();
					      ps5 = dbcon5.prepareStatement("UPDATE DungeonCurrency "
					      + "SET dungeon5 = dungeon5 + "+bounty+" WHERE name = \""+i.getName()+"\"");
					      ps5.executeUpdate();
					      ps5.close(); 
					      dbcon5.close();
					      //logger.log(Level.INFO, i.getName()+" is a player and gets "+bounty+" "+dungeonmain.currencyname5);
					      i.getCommunicator().sendSafeServerMessage("you got "+bounty+" "+dungeonmain.currencyname5);
				      }
			      catch (SQLException e) {
			          throw new RuntimeException(e);
			        }        		
	
	        		}
                }
                //end of dungeon5               
                
                //dungeon6
                if (dungeonmain.currency6enabled){
	                if (dungeonmain.zone6minx <= X && X <= dungeonmain.zone6maxx && dungeonmain.zone6miny <= Y && Y <= dungeonmain.zone6maxy){ 
	                long bounty = dungeonmain.trashmobamount6;
	                String[] minibossmobsname6split = dungeonmain.minibossnames6.split(",");
	     	        int m = minibossmobsname6split.length;
	     	        int m6 = 0;
	     	        while (m6 < m) {
	     				 String minibossname = minibossmobsname6split[m6];
	     		        	if (name.contains(minibossname)){
	     		        		bounty = dungeonmain.minibossamount6;
	     		        	}
	     			 	++m6;
	     		 	}
	     	        
	     			 String[] bossmobsname6split = dungeonmain.bossmobnames6.split(",");
	     		        int p = bossmobsname6split.length;
	     		        int p6 = 0;
	     		        while (p6 < p) {
	     					 String bossname = bossmobsname6split[p6];
	     			        	if (name.contains(bossname)){
	     			        		bounty = dungeonmain.bossmobamount6;
	     			        	}
	     				 	++p6;
	     			 	} 
	                	Connection dbcon6 = null;
				      PreparedStatement ps6 = null;	   
				      if (pvp){
				    	  Player player = (Player) thisCreature;
				    	  bounty = player.getRank() * 2;  
				      }
				      if (pvpevil)bounty = -dungeonmain.pvpevil;
				      try
				      {
					      dbcon6 = ModSupportDb.getModSupportDb();
					      ps6 = dbcon6.prepareStatement("UPDATE DungeonCurrency "
					      + "SET dungeon6 = dungeon6 + "+bounty+" WHERE name = \""+i.getName()+"\"");
					      ps6.executeUpdate();
					      ps6.close(); 
					      dbcon6.close();
					      //logger.log(Level.INFO, i.getName()+" is a player and gets "+bounty+" "+dungeonmain.currencyname6);
					      i.getCommunicator().sendSafeServerMessage("you got "+bounty+" "+dungeonmain.currencyname6);
				      }
			      catch (SQLException e) {
			          throw new RuntimeException(e);
			        }        		
	
	        		}
                }
                //end of dungeon6               
                
                //dungeon7
                if (dungeonmain.currency7enabled){
	                if (dungeonmain.zone7minx <= X && X <= dungeonmain.zone7maxx && dungeonmain.zone7miny <= Y && Y <= dungeonmain.zone7maxy){ 
	                long bounty = dungeonmain.trashmobamount7;
	                String[] minibossmobsname7split = dungeonmain.minibossnames7.split(",");
	     	        int m = minibossmobsname7split.length;
	     	        int m7 = 0;
	     	        while (m7 < m) {
	     				 String minibossname = minibossmobsname7split[m7];
	     		        	if (name.contains(minibossname)){
	     		        		bounty = dungeonmain.minibossamount7;
	     		        	}
	     			 	++m7;
	     		 	}
	     	        
	     			 String[] bossmobsname7split = dungeonmain.bossmobnames7.split(",");
	     		        int p = bossmobsname7split.length;
	     		        int p7 = 0;
	     		        while (p7 < p) {
	     					 String bossname = bossmobsname7split[p7];
	     			        	if (name.contains(bossname)){
	     			        		bounty = dungeonmain.bossmobamount7;
	     			        	}
	     				 	++p7;
	     			 	} 
	                	Connection dbcon7 = null;
				      PreparedStatement ps7 = null;	 
				      if (pvp){
				    	  Player player = (Player) thisCreature;
				    	  bounty = player.getRank() * 2;  
				      }
				      if (pvpevil)bounty = -dungeonmain.pvpevil;
				      try
				      {
					      dbcon7 = ModSupportDb.getModSupportDb();
					      ps7 = dbcon7.prepareStatement("UPDATE DungeonCurrency "
					      + "SET dungeon7 = dungeon7 + "+bounty+" WHERE name = \""+i.getName()+"\"");
					      ps7.executeUpdate();
					      ps7.close(); 
					      dbcon7.close();
					      //logger.log(Level.INFO, i.getName()+" is a player and gets "+bounty+" "+dungeonmain.currencyname7);
					      i.getCommunicator().sendSafeServerMessage("you got "+bounty+" "+dungeonmain.currencyname7);
				      }
			      catch (SQLException e) {
			          throw new RuntimeException(e);
			        }        		
	
	        		}
                }
                //end of dungeon7
                
                
                //dungeon8
                if (dungeonmain.currency8enabled){
	                if (dungeonmain.zone8minx <= X && X <= dungeonmain.zone8maxx && dungeonmain.zone8miny <= Y && Y <= dungeonmain.zone8maxy){ 
	                long bounty = dungeonmain.trashmobamount8;
	                String[] minibossmobsname8split = dungeonmain.minibossnames8.split(",");
	     	        int m = minibossmobsname8split.length;
	     	        int m8 = 0;
	     	        while (m8 < m) {
	     				 String minibossname = minibossmobsname8split[m8];
	     		        	if (name.contains(minibossname)){
	     		        		bounty = dungeonmain.minibossamount8;
	     		        	}
	     			 	++m8;
	     		 	}
	     	        
	     			 String[] bossmobsname8split = dungeonmain.bossmobnames8.split(",");
	     		        int p = bossmobsname8split.length;
	     		        int p8 = 0;
	     		        while (p8 < p) {
	     					 String bossname = bossmobsname8split[p8];
	     			        	if (name.contains(bossname)){
	     			        		bounty = dungeonmain.bossmobamount8;
	     			        	}
	     				 	++p8;
	     			 	} 
	                	Connection dbcon8 = null;
				      PreparedStatement ps8 = null;	 
				      if (pvp){
				    	  Player player = (Player) thisCreature;
				    	  bounty = player.getRank() * 2;  
				      }
				      if (pvpevil)bounty = -dungeonmain.pvpevil;
				      try
				      {
					      dbcon8 = ModSupportDb.getModSupportDb();
					      ps8 = dbcon8.prepareStatement("UPDATE DungeonCurrency "
					      + "SET dungeon8 = dungeon8 + "+bounty+" WHERE name = \""+i.getName()+"\"");
					      ps8.executeUpdate();
					      ps8.close(); 
					      dbcon8.close();
					      //logger.log(Level.INFO, i.getName()+" is a player and gets "+bounty+" "+dungeonmain.currencyname8);
					      i.getCommunicator().sendSafeServerMessage("you got "+bounty+" "+dungeonmain.currencyname8);
				      }
			      catch (SQLException e) {
			          throw new RuntimeException(e);
			        }        		
	
	        		}
                }
                //end of dungeon8               
                
                //dungeon9
                if (dungeonmain.currency9enabled){
	                if (dungeonmain.zone9minx <= X && X <= dungeonmain.zone9maxx && dungeonmain.zone9miny <= Y && Y <= dungeonmain.zone9maxy){ 
	                long bounty = dungeonmain.trashmobamount9;
	                String[] minibossmobsname9split = dungeonmain.minibossnames9.split(",");
	     	        int m = minibossmobsname9split.length;
	     	        int m9 = 0;
	     	        while (m9 < m) {
	     				 String minibossname = minibossmobsname9split[m9];
	     		        	if (name.contains(minibossname)){
	     		        		bounty = dungeonmain.minibossamount9;
	     		        	}
	     			 	++m9;
	     		 	}
	     	        
	     			 String[] bossmobsname9split = dungeonmain.bossmobnames9.split(",");
	     		        int p = bossmobsname9split.length;
	     		        int p9 = 0;
	     		        while (p9 < p) {
	     					 String bossname = bossmobsname9split[p9];
	     			        	if (name.contains(bossname)){
	     			        		bounty = dungeonmain.bossmobamount9;
	     			        	}
	     				 	++p9;
	     			 	} 
	                	Connection dbcon9 = null;
				      PreparedStatement ps9 = null;	
				      if (pvp){
				    	  Player player = (Player) thisCreature;
				    	  bounty = player.getRank() * 2;  
				      }
				      if (pvpevil)bounty = -dungeonmain.pvpevil;
				      try
				      {
					      dbcon9 = ModSupportDb.getModSupportDb();
					      ps9 = dbcon9.prepareStatement("UPDATE DungeonCurrency "
					      + "SET dungeon9 = dungeon9 + "+bounty+" WHERE name = \""+i.getName()+"\"");
					      ps9.executeUpdate();
					      ps9.close(); 
					      dbcon9.close();
					      //logger.log(Level.INFO, i.getName()+" is a player and gets "+bounty+" "+dungeonmain.currencyname9);
					      i.getCommunicator().sendSafeServerMessage("you got "+bounty+" "+dungeonmain.currencyname9);
				      }
			      catch (SQLException e) {
			          throw new RuntimeException(e);
			        }        		
	
	        		}
                }
                //end of dungeon9               
                
                //dungeon10
                if (dungeonmain.currency10enabled){
	                if (dungeonmain.zone10minx <= X && X <= dungeonmain.zone10maxx && dungeonmain.zone10miny <= Y && Y <= dungeonmain.zone10maxy){ 
	                long bounty = dungeonmain.trashmobamount10;
	                String[] minibossmobsname10split = dungeonmain.minibossnames10.split(",");
	     	        int m = minibossmobsname10split.length;
	     	        int m10 = 0;
	     	        while (m10 < m) {
	     				 String minibossname = minibossmobsname10split[m10];
	     		        	if (name.contains(minibossname)){
	     		        		bounty = dungeonmain.minibossamount10;
	     		        	}
	     			 	++m10;
	     		 	}
	     	        
	     			 String[] bossmobsname10split = dungeonmain.bossmobnames10.split(",");
	     		        int p = bossmobsname10split.length;
	     		        int p10 = 0;
	     		        while (p10 < p) {
	     					 String bossname = bossmobsname10split[p10];
	     			        	if (name.contains(bossname)){
	     			        		bounty = dungeonmain.bossmobamount10;
	     			        	}
	     				 	++p10;
	     			 	} 
	                	Connection dbcon10 = null;
				      PreparedStatement ps10 = null;
				      if (pvp){
				    	  Player player = (Player) thisCreature;
				    	  bounty = player.getRank() * 2;  
				      }
				      if (pvpevil)bounty = -dungeonmain.pvpevil;
				      try
				      {
					      dbcon10 = ModSupportDb.getModSupportDb();
					      ps10 = dbcon10.prepareStatement("UPDATE DungeonCurrency "
					      + "SET dungeon10 = dungeon10 + "+bounty+" WHERE name = \""+i.getName()+"\"");
					      ps10.executeUpdate();
					      ps10.close(); 
					      dbcon10.close();
					      //logger.log(Level.INFO, i.getName()+" is a player and gets "+bounty+" "+dungeonmain.currencyname10);
					      i.getCommunicator().sendSafeServerMessage("you got "+bounty+" "+dungeonmain.currencyname10);
				      }
			      catch (SQLException e) {
			          throw new RuntimeException(e);
			        }        		
	
	        		}
                }
                //end of dungeon10 
                
                
        	}
            ++n2;
        }
        return true;
    }

    public static void onServerStarted() {
        LootSystem.getInstance().listen((BeforeDropListener)currencyadding.getInstance());
    }

 
}