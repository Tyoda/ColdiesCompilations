package net.coldie.wurmunlimited.mods.dungeons;

import com.wurmonline.server.creatures.Communicator;
import com.wurmonline.server.players.Player;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.ModSupportDb;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;


public class dungeonmain implements WurmServerMod, Configurable, PreInitable, ServerStartedListener, PlayerLoginListener, ItemTemplatesCreatedListener, PlayerMessageListener, Versioned {
	// public static final Logger logger = Logger.getLogger(dungeonmain.class.getName());
	public static final String version = "ty1.0";
	
	public static int pvpevil = 0;
	
	public static String currencyname1 = "defaultcurrency1";
	public static boolean currency1enabled = false;
	public static int zone1minx = 0;
	public static int zone1maxx = 1;
	public static int zone1miny = 0;
	public static int zone1maxy = 1;
	public static int itemtemplate1 = 4301;
	public static String itemmodel1 = "model.light.lamp.street.pole.";
	public static String minibossnames1 = "";
	public static String bossmobnames1 = "";
	public static String dungeon1items = "";
	public static int trashmobamount1 = 100;
	public static int bossmobamount1 = 1000;
	public static int minibossamount1 = 500;
	public static HashMap<Integer, String> items1 = new HashMap<Integer,String>();
	public static String dungeon1color = "";
	public static int statuettetemplate1 = 4311;
	public static int healpower1 = 3;
	public static int healcooldown1 = 30;
	
	public static String currencyname2 = "defaultcurrency2";
	public static boolean currency2enabled = false;
	public static int zone2minx = 0;
	public static int zone2maxx = 2;
	public static int zone2miny = 0;
	public static int zone2maxy = 2;
	public static int itemtemplate2 = 4302;
	public static String itemmodel2 = "model.light.lamp.street.pole.";
	public static String minibossnames2 = "";
	public static String bossmobnames2 = "";
	public static String dungeon2items = "";
	public static int trashmobamount2 = 200;
	public static int bossmobamount2 = 2000;
	public static int minibossamount2 = 200;
	public static HashMap<Integer, String> items2 = new HashMap<Integer,String>();
	public static String dungeon2color = "";
	public static int statuettetemplate2 = 4312;
	public static int healpower2 = 3;
	public static int healcooldown2 = 30;	
	
	public static String currencyname3 = "defaultcurrency3";
	public static boolean currency3enabled = false;
	public static int zone3minx = 0;
	public static int zone3maxx = 3;
	public static int zone3miny = 0;
	public static int zone3maxy = 3;
	public static int itemtemplate3 = 4303;
	public static String itemmodel3 = "model.light.lamp.street.pole.";
	public static String minibossnames3 = "";
	public static String bossmobnames3 = "";
	public static String dungeon3items = "";
	public static int trashmobamount3 = 300;
	public static int bossmobamount3 = 3000;
	public static int minibossamount3 = 300;
	public static HashMap<Integer, String> items3 = new HashMap<Integer,String>();
	public static String dungeon3color = "";	
	public static int statuettetemplate3 = 4313;
	public static int healpower3 = 3;
	public static int healcooldown3 = 30;
	
	public static String currencyname4 = "defaultcurrency4";
	public static boolean currency4enabled = false;
	public static int zone4minx = 0;
	public static int zone4maxx = 4;
	public static int zone4miny = 0;
	public static int zone4maxy = 4;
	public static int itemtemplate4 = 4304;
	public static String itemmodel4 = "model.light.lamp.street.pole.";
	public static String minibossnames4 = "";
	public static String bossmobnames4 = "";
	public static String dungeon4items = "";
	public static int trashmobamount4 = 400;
	public static int bossmobamount4 = 4000;
	public static int minibossamount4 = 400;
	public static HashMap<Integer, String> items4 = new HashMap<Integer,String>();
	public static String dungeon4color = "";
	public static int statuettetemplate4 = 4314;
	public static int healpower4 = 3;
	public static int healcooldown4 = 30;
	
	public static String currencyname5 = "defaultcurrency5";
	public static boolean currency5enabled = false;
	public static int zone5minx = 0;
	public static int zone5maxx = 5;
	public static int zone5miny = 0;
	public static int zone5maxy = 5;
	public static int itemtemplate5 = 4305;
	public static String itemmodel5 = "model.light.lamp.street.pole.";
	public static String minibossnames5 = "";
	public static String bossmobnames5 = "";
	public static String dungeon5items = "";
	public static int trashmobamount5 = 500;
	public static int bossmobamount5 = 5000;
	public static int minibossamount5 = 500;
	public static HashMap<Integer, String> items5 = new HashMap<Integer,String>();
	public static String dungeon5color = "";
	public static int statuettetemplate5 = 4315;
	public static int healpower5 = 3;
	public static int healcooldown5 = 30;
	
	public static String currencyname6 = "defaultcurrency6";
	public static boolean currency6enabled = false;
	public static int zone6minx = 0;
	public static int zone6maxx = 6;
	public static int zone6miny = 0;
	public static int zone6maxy = 6;
	public static int itemtemplate6 = 4306;
	public static String itemmodel6 = "model.light.lamp.street.pole.";
	public static String minibossnames6 = "";
	public static String bossmobnames6 = "";
	public static String dungeon6items = "";
	public static int trashmobamount6 = 600;
	public static int bossmobamount6 = 6000;
	public static int minibossamount6 = 600;
	public static HashMap<Integer, String> items6 = new HashMap<Integer,String>();
	public static String dungeon6color = "";	
	public static int statuettetemplate6 = 4316;
	public static int healpower6 = 3;
	public static int healcooldown6 = 30;
	
	public static String currencyname7 = "defaultcurrency7";
	public static boolean currency7enabled = false;
	public static int zone7minx = 0;
	public static int zone7maxx = 7;
	public static int zone7miny = 0;
	public static int zone7maxy = 7;
	public static int itemtemplate7 = 4307;
	public static String itemmodel7 = "model.light.lamp.street.pole.";
	public static String minibossnames7 = "";
	public static String bossmobnames7 = "";
	public static String dungeon7items = "";
	public static int trashmobamount7 = 700;
	public static int bossmobamount7 = 7000;
	public static int minibossamount7 = 700;
	public static HashMap<Integer, String> items7 = new HashMap<Integer,String>();
	public static String dungeon7color = "";
	public static int statuettetemplate7 = 4317;
	public static int healpower7 = 3;
	public static int healcooldown7 = 30;
	
	public static String currencyname8 = "defaultcurrency8";
	public static boolean currency8enabled = false;
	public static int zone8minx = 0;
	public static int zone8maxx = 8;
	public static int zone8miny = 0;
	public static int zone8maxy = 8;
	public static int itemtemplate8 = 4308;
	public static String itemmodel8 = "model.light.lamp.street.pole.";
	public static String minibossnames8 = "";
	public static String bossmobnames8 = "";
	public static String dungeon8items = "";
	public static int trashmobamount8 = 800;
	public static int bossmobamount8 = 8000;
	public static int minibossamount8 = 800;
	public static HashMap<Integer, String> items8 = new HashMap<Integer,String>();
	public static String dungeon8color = "";
	public static int statuettetemplate8 = 4318;
	public static int healpower8 = 3;
	public static int healcooldown8 = 30;
	
	public static String currencyname9 = "defaultcurrency9";
	public static boolean currency9enabled = false;
	public static int zone9minx = 0;
	public static int zone9maxx = 9;
	public static int zone9miny = 0;
	public static int zone9maxy = 9;
	public static int itemtemplate9 = 4309;
	public static String itemmodel9 = "model.light.lamp.street.pole.";
	public static String minibossnames9 = "";
	public static String bossmobnames9 = "";
	public static String dungeon9items = "";
	public static int trashmobamount9 = 900;
	public static int bossmobamount9 = 9000;
	public static int minibossamount9 = 900;
	public static HashMap<Integer, String> items9 = new HashMap<Integer,String>();
	public static String dungeon9color = "";	
	public static int statuettetemplate9 = 4319;
	public static int healpower9 = 3;
	public static int healcooldown9 = 30;
	
	public static String currencyname10 = "defaultcurrency10";
	public static boolean currency10enabled = false;
	public static int zone10minx = 0;
	public static int zone10maxx = 10;
	public static int zone10miny = 0;
	public static int zone10maxy = 10;
	public static int itemtemplate10 = 4310;
	public static String itemmodel10 = "model.light.lamp.street.pole.";
	public static String minibossnames10 = "";
	public static String bossmobnames10 = "";
	public static String dungeon10items = "";
	public static int trashmobamount10 = 100;
	public static int bossmobamount10 = 1000;
	public static int minibossamount10 = 100;
	public static HashMap<Integer, String> items10 = new HashMap<Integer,String>();
	public static String dungeon10color = "";
	public static int statuettetemplate10 = 4320;
	public static int healpower10 = 3;
	public static int healcooldown10 = 30;
	
	public static void doconfig(Properties properties){
		
		currency1enabled = Boolean.parseBoolean(properties.getProperty("currency1enabled", Boolean.toString(currency1enabled)));
		currency2enabled = Boolean.parseBoolean(properties.getProperty("currency2enabled", Boolean.toString(currency2enabled)));
		currency3enabled = Boolean.parseBoolean(properties.getProperty("currency3enabled", Boolean.toString(currency3enabled)));
		currency4enabled = Boolean.parseBoolean(properties.getProperty("currency4enabled", Boolean.toString(currency4enabled)));
		currency5enabled = Boolean.parseBoolean(properties.getProperty("currency5enabled", Boolean.toString(currency5enabled)));
		currency6enabled = Boolean.parseBoolean(properties.getProperty("currency6enabled", Boolean.toString(currency6enabled)));
		currency7enabled = Boolean.parseBoolean(properties.getProperty("currency7enabled", Boolean.toString(currency7enabled)));
		currency8enabled = Boolean.parseBoolean(properties.getProperty("currency8enabled", Boolean.toString(currency8enabled)));
		currency9enabled = Boolean.parseBoolean(properties.getProperty("currency9enabled", Boolean.toString(currency9enabled)));
		currency10enabled = Boolean.parseBoolean(properties.getProperty("currency10enabled", Boolean.toString(currency10enabled)));

		
		//pvpevil = Integer.parseInt(properties.getProperty("pvpevil", Float.toString(pvpevil)));
		
		
		// dungeon 1
		if (currency1enabled){
		minibossamount1 = Integer.parseInt(properties.getProperty("minibossamount1", Float.toString(minibossamount1)));
		minibossnames1 = properties.getProperty("minibossnames1",minibossnames1);
		zone1minx = Integer.parseInt(properties.getProperty("zone1minx", Float.toString(zone1minx)));
		zone1maxx = Integer.parseInt(properties.getProperty("zone1maxx", Float.toString(zone1maxx)));				
		zone1miny = Integer.parseInt(properties.getProperty("zone1miny", Float.toString(zone1miny)));		
		zone1maxy = Integer.parseInt(properties.getProperty("zone1maxy", Float.toString(zone1maxy)));
		currencyname1 = properties.getProperty("currencyname1",currencyname1);
		itemtemplate1 = Integer.parseInt(properties.getProperty("itemtemplate1", Float.toString(itemtemplate1)));
		itemmodel1 = properties.getProperty("itemmodel1",itemmodel1);
		trashmobamount1 = Integer.parseInt(properties.getProperty("trashmobamount1", Float.toString(trashmobamount1)));
		bossmobamount1 = Integer.parseInt(properties.getProperty("bossmobamount1", Float.toString(bossmobamount1)));
		bossmobnames1 = properties.getProperty("bossmobnames1",bossmobnames1);
	    dungeon1items = properties.getProperty("dungeon1items",dungeon1items); 
	    dungeon1color = properties.getProperty("dungeon1color",dungeon1color);
	    statuettetemplate1 = Integer.parseInt(properties.getProperty("statuettetemplate1", Float.toString(statuettetemplate1)));
	    healpower1 = Integer.parseInt(properties.getProperty("healpower1", Float.toString(healpower1)));
	    healcooldown1 = Integer.parseInt(properties.getProperty("healcooldown1", Float.toString(healcooldown1)));
		}
		
		// dungeon 2
		if (currency2enabled){
		minibossamount2 = Integer.parseInt(properties.getProperty("minibossamount2", Float.toString(minibossamount2)));
		minibossnames2 = properties.getProperty("minibossnames2",minibossnames2);
		zone2minx = Integer.parseInt(properties.getProperty("zone2minx", Float.toString(zone2minx)));
		zone2maxx = Integer.parseInt(properties.getProperty("zone2maxx", Float.toString(zone2maxx)));
		zone2miny = Integer.parseInt(properties.getProperty("zone2miny", Float.toString(zone2miny)));
		zone2maxy = Integer.parseInt(properties.getProperty("zone2maxy", Float.toString(zone2maxy)));
		currencyname2 = properties.getProperty("currencyname2",currencyname2);
		itemtemplate2 = Integer.parseInt(properties.getProperty("itemtemplate2", Float.toString(itemtemplate2)));
		itemmodel2 = properties.getProperty("itemmodel2",itemmodel2);
		trashmobamount2 = Integer.parseInt(properties.getProperty("trashmobamount2", Float.toString(trashmobamount2)));
		bossmobamount2 = Integer.parseInt(properties.getProperty("bossmobamount2", Float.toString(bossmobamount2)));
		bossmobnames2 = properties.getProperty("bossmobnames2",bossmobnames2);
	    dungeon2items = properties.getProperty("dungeon2items",dungeon2items); 
	    dungeon2color = properties.getProperty("dungeon2color",dungeon2color);
	    statuettetemplate2 = Integer.parseInt(properties.getProperty("statuettetemplate2", Float.toString(statuettetemplate2)));
	    healpower2 = Integer.parseInt(properties.getProperty("healpower2", Float.toString(healpower2)));
	    healcooldown2 = Integer.parseInt(properties.getProperty("healcooldown2", Float.toString(healcooldown2)));
		}
		// dungeon 3
		if (currency3enabled){
		minibossamount3 = Integer.parseInt(properties.getProperty("minibossamount3", Float.toString(minibossamount3)));
		minibossnames3 = properties.getProperty("minibossnames3",minibossnames3);
		zone3minx = Integer.parseInt(properties.getProperty("zone3minx", Float.toString(zone3minx)));
		zone3maxx = Integer.parseInt(properties.getProperty("zone3maxx", Float.toString(zone3maxx)));				
		zone3miny = Integer.parseInt(properties.getProperty("zone3miny", Float.toString(zone3miny)));		
		zone3maxy = Integer.parseInt(properties.getProperty("zone3maxy", Float.toString(zone3maxy)));
		currencyname3 = properties.getProperty("currencyname3",currencyname3);
		itemtemplate3 = Integer.parseInt(properties.getProperty("itemtemplate3", Float.toString(itemtemplate3)));
		itemmodel3 = properties.getProperty("itemmodel3",itemmodel3);
		trashmobamount3 = Integer.parseInt(properties.getProperty("trashmobamount3", Float.toString(trashmobamount3)));
		bossmobamount3 = Integer.parseInt(properties.getProperty("bossmobamount3", Float.toString(bossmobamount3)));
		bossmobnames3 = properties.getProperty("bossmobnames3",bossmobnames3);
	    dungeon3items = properties.getProperty("dungeon3items",dungeon3items); 
	    dungeon3color = properties.getProperty("dungeon3color",dungeon3color);	
	    statuettetemplate3 = Integer.parseInt(properties.getProperty("statuettetemplate3", Float.toString(statuettetemplate3)));
	    healpower3 = Integer.parseInt(properties.getProperty("healpower3", Float.toString(healpower3)));
	    healcooldown3 = Integer.parseInt(properties.getProperty("healcooldown3", Float.toString(healcooldown3)));
		}
		// dungeon 4
		if (currency4enabled){
		minibossamount4 = Integer.parseInt(properties.getProperty("minibossamount4", Float.toString(minibossamount4)));
		minibossnames4 = properties.getProperty("minibossnames4",minibossnames4);
		zone4minx = Integer.parseInt(properties.getProperty("zone4minx", Float.toString(zone4minx)));
		zone4maxx = Integer.parseInt(properties.getProperty("zone4maxx", Float.toString(zone4maxx)));				
		zone4miny = Integer.parseInt(properties.getProperty("zone4miny", Float.toString(zone4miny)));		
		zone4maxy = Integer.parseInt(properties.getProperty("zone4maxy", Float.toString(zone4maxy)));
		currencyname4 = properties.getProperty("currencyname4",currencyname4);
		itemtemplate4 = Integer.parseInt(properties.getProperty("itemtemplate4", Float.toString(itemtemplate4)));
		itemmodel4 = properties.getProperty("itemmodel4",itemmodel4);
		trashmobamount4 = Integer.parseInt(properties.getProperty("trashmobamount4", Float.toString(trashmobamount4)));
		bossmobamount4 = Integer.parseInt(properties.getProperty("bossmobamount4", Float.toString(bossmobamount4)));
		bossmobnames4 = properties.getProperty("bossmobnames4",bossmobnames4);
	    dungeon4items = properties.getProperty("dungeon4items",dungeon4items); 
	    dungeon4color = properties.getProperty("dungeon4color",dungeon4color);
	    statuettetemplate4 = Integer.parseInt(properties.getProperty("statuettetemplate4", Float.toString(statuettetemplate4)));
	    healpower4 = Integer.parseInt(properties.getProperty("healpower4", Float.toString(healpower4)));
	    healcooldown4 = Integer.parseInt(properties.getProperty("healcooldown4", Float.toString(healcooldown4)));
		}
		// dungeon 5
		if (currency5enabled){
		minibossamount5 = Integer.parseInt(properties.getProperty("minibossamount5", Float.toString(minibossamount5)));
		minibossnames5 = properties.getProperty("minibossnames5",minibossnames5);
		zone5minx = Integer.parseInt(properties.getProperty("zone5minx", Float.toString(zone5minx)));
		zone5maxx = Integer.parseInt(properties.getProperty("zone5maxx", Float.toString(zone5maxx)));				
		zone5miny = Integer.parseInt(properties.getProperty("zone5miny", Float.toString(zone5miny)));		
		zone5maxy = Integer.parseInt(properties.getProperty("zone5maxy", Float.toString(zone5maxy)));
		currencyname5 = properties.getProperty("currencyname5",currencyname5);
		itemtemplate5 = Integer.parseInt(properties.getProperty("itemtemplate5", Float.toString(itemtemplate5)));
		itemmodel5 = properties.getProperty("itemmodel5",itemmodel5);
		trashmobamount5 = Integer.parseInt(properties.getProperty("trashmobamount5", Float.toString(trashmobamount5)));
		bossmobamount5 = Integer.parseInt(properties.getProperty("bossmobamount5", Float.toString(bossmobamount5)));
		bossmobnames5 = properties.getProperty("bossmobnames5",bossmobnames5);
	    dungeon5items = properties.getProperty("dungeon5items",dungeon5items); 
	    dungeon5color = properties.getProperty("dungeon5color",dungeon5color);	
	    statuettetemplate5 = Integer.parseInt(properties.getProperty("statuettetemplate5", Float.toString(statuettetemplate5)));
	    healpower5 = Integer.parseInt(properties.getProperty("healpower5", Float.toString(healpower5)));
	    healcooldown5 = Integer.parseInt(properties.getProperty("healcooldown5", Float.toString(healcooldown5)));
		}
		
		// dungeon 6
		if (currency6enabled){
		minibossamount6 = Integer.parseInt(properties.getProperty("minibossamount6", Float.toString(minibossamount6)));
		minibossnames6 = properties.getProperty("minibossnames6",minibossnames6);
		zone6minx = Integer.parseInt(properties.getProperty("zone6minx", Float.toString(zone6minx)));
		zone6maxx = Integer.parseInt(properties.getProperty("zone6maxx", Float.toString(zone6maxx)));				
		zone6miny = Integer.parseInt(properties.getProperty("zone6miny", Float.toString(zone6miny)));		
		zone6maxy = Integer.parseInt(properties.getProperty("zone6maxy", Float.toString(zone6maxy)));
		currencyname6 = properties.getProperty("currencyname6",currencyname6);
		itemtemplate6 = Integer.parseInt(properties.getProperty("itemtemplate6", Float.toString(itemtemplate6)));
		itemmodel6 = properties.getProperty("itemmodel6",itemmodel6);
		trashmobamount6 = Integer.parseInt(properties.getProperty("trashmobamount6", Float.toString(trashmobamount6)));
		bossmobamount6 = Integer.parseInt(properties.getProperty("bossmobamount6", Float.toString(bossmobamount6)));
		bossmobnames6 = properties.getProperty("bossmobnames6",bossmobnames6);
	    dungeon6items = properties.getProperty("dungeon6items",dungeon6items); 
	    dungeon6color = properties.getProperty("dungeon6color",dungeon6color);
	    statuettetemplate6 = Integer.parseInt(properties.getProperty("statuettetemplate6", Float.toString(statuettetemplate6)));
	    healpower6 = Integer.parseInt(properties.getProperty("healpower6", Float.toString(healpower6)));
	    healcooldown6 = Integer.parseInt(properties.getProperty("healcooldown6", Float.toString(healcooldown6)));
		}
		// dungeon 7
		if (currency7enabled){
		minibossamount7 = Integer.parseInt(properties.getProperty("minibossamount7", Float.toString(minibossamount7)));
		minibossnames7 = properties.getProperty("minibossnames7",minibossnames7);
		zone7minx = Integer.parseInt(properties.getProperty("zone7minx", Float.toString(zone7minx)));
		zone7maxx = Integer.parseInt(properties.getProperty("zone7maxx", Float.toString(zone7maxx)));				
		zone7miny = Integer.parseInt(properties.getProperty("zone7miny", Float.toString(zone7miny)));		
		zone7maxy = Integer.parseInt(properties.getProperty("zone7maxy", Float.toString(zone7maxy)));
		currencyname7 = properties.getProperty("currencyname7",currencyname7);
		itemtemplate7 = Integer.parseInt(properties.getProperty("itemtemplate7", Float.toString(itemtemplate7)));
		itemmodel7 = properties.getProperty("itemmodel7",itemmodel7);
		trashmobamount7 = Integer.parseInt(properties.getProperty("trashmobamount7", Float.toString(trashmobamount7)));
		bossmobamount7 = Integer.parseInt(properties.getProperty("bossmobamount7", Float.toString(bossmobamount7)));
		bossmobnames7 = properties.getProperty("bossmobnames7",bossmobnames7);
	    dungeon7items = properties.getProperty("dungeon7items",dungeon7items); 
	    dungeon7color = properties.getProperty("dungeon7color",dungeon7color);	
	    statuettetemplate7 = Integer.parseInt(properties.getProperty("statuettetemplate7", Float.toString(statuettetemplate7)));
	    healpower7 = Integer.parseInt(properties.getProperty("healpower7", Float.toString(healpower7)));
	    healcooldown7 = Integer.parseInt(properties.getProperty("healcooldown7", Float.toString(healcooldown7)));
		}
		// dungeon 8
		if (currency8enabled){
		minibossamount8 = Integer.parseInt(properties.getProperty("minibossamount8", Float.toString(minibossamount8)));
		minibossnames8 = properties.getProperty("minibossnames8",minibossnames8);
		zone8minx = Integer.parseInt(properties.getProperty("zone8minx", Float.toString(zone8minx)));
		zone8maxx = Integer.parseInt(properties.getProperty("zone8maxx", Float.toString(zone8maxx)));				
		zone8miny = Integer.parseInt(properties.getProperty("zone8miny", Float.toString(zone8miny)));		
		zone8maxy = Integer.parseInt(properties.getProperty("zone8maxy", Float.toString(zone8maxy)));
		currencyname8 = properties.getProperty("currencyname8",currencyname8);
		itemtemplate8 = Integer.parseInt(properties.getProperty("itemtemplate8", Float.toString(itemtemplate8)));
		itemmodel8 = properties.getProperty("itemmodel8",itemmodel8);
		trashmobamount8 = Integer.parseInt(properties.getProperty("trashmobamount8", Float.toString(trashmobamount8)));
		bossmobamount8 = Integer.parseInt(properties.getProperty("bossmobamount8", Float.toString(bossmobamount8)));
		bossmobnames8 = properties.getProperty("bossmobnames8",bossmobnames8);
	    dungeon8items = properties.getProperty("dungeon8items",dungeon8items); 
	    dungeon8color = properties.getProperty("dungeon8color",dungeon8color);
	    statuettetemplate8 = Integer.parseInt(properties.getProperty("statuettetemplate8", Float.toString(statuettetemplate8)));
	    healpower8 = Integer.parseInt(properties.getProperty("healpower8", Float.toString(healpower8)));
	    healcooldown8 = Integer.parseInt(properties.getProperty("healcooldown8", Float.toString(healcooldown8)));
		}
		// dungeon 9
		if (currency9enabled){
		minibossamount9 = Integer.parseInt(properties.getProperty("minibossamount9", Float.toString(minibossamount9)));
		minibossnames9 = properties.getProperty("minibossnames9",minibossnames9);
		zone9minx = Integer.parseInt(properties.getProperty("zone9minx", Float.toString(zone9minx)));
		zone9maxx = Integer.parseInt(properties.getProperty("zone9maxx", Float.toString(zone9maxx)));				
		zone9miny = Integer.parseInt(properties.getProperty("zone9miny", Float.toString(zone9miny)));		
		zone9maxy = Integer.parseInt(properties.getProperty("zone9maxy", Float.toString(zone9maxy)));
		currencyname9 = properties.getProperty("currencyname9",currencyname9);
		itemtemplate9 = Integer.parseInt(properties.getProperty("itemtemplate9", Float.toString(itemtemplate9)));
		itemmodel9 = properties.getProperty("itemmodel9",itemmodel9);
		trashmobamount9 = Integer.parseInt(properties.getProperty("trashmobamount9", Float.toString(trashmobamount9)));
		bossmobamount9 = Integer.parseInt(properties.getProperty("bossmobamount9", Float.toString(bossmobamount9)));
		bossmobnames9 = properties.getProperty("bossmobnames9",bossmobnames9);
	    dungeon9items = properties.getProperty("dungeon9items",dungeon9items); 
	    dungeon9color = properties.getProperty("dungeon9color",dungeon9color);	 
	    statuettetemplate9 = Integer.parseInt(properties.getProperty("statuettetemplate9", Float.toString(statuettetemplate9)));
	    healpower9 = Integer.parseInt(properties.getProperty("healpower9", Float.toString(healpower9)));
	    healcooldown9 = Integer.parseInt(properties.getProperty("healcooldown9", Float.toString(healcooldown9)));
		}
		// dungeon 10
		if (currency10enabled){
		minibossamount10 = Integer.parseInt(properties.getProperty("minibossamount10", Float.toString(minibossamount10)));
		minibossnames10 = properties.getProperty("minibossnames10",minibossnames10);
		zone10minx = Integer.parseInt(properties.getProperty("zone10minx", Float.toString(zone10minx)));
		zone10maxx = Integer.parseInt(properties.getProperty("zone10maxx", Float.toString(zone10maxx)));				
		zone10miny = Integer.parseInt(properties.getProperty("zone10miny", Float.toString(zone10miny)));		
		zone10maxy = Integer.parseInt(properties.getProperty("zone10maxy", Float.toString(zone10maxy)));
		currencyname10 = properties.getProperty("currencyname10",currencyname10);
		itemtemplate10 = Integer.parseInt(properties.getProperty("itemtemplate10", Float.toString(itemtemplate10)));
		itemmodel10 = properties.getProperty("itemmodel10",itemmodel10);
		trashmobamount10 = Integer.parseInt(properties.getProperty("trashmobamount10", Float.toString(trashmobamount10)));
		bossmobamount10 = Integer.parseInt(properties.getProperty("bossmobamount10", Float.toString(bossmobamount10)));
		bossmobnames10 = properties.getProperty("bossmobnames10",bossmobnames10);
	    dungeon10items = properties.getProperty("dungeon10items",dungeon10items); 
	    dungeon10color = properties.getProperty("dungeon10color",dungeon10color);
	    statuettetemplate10 = Integer.parseInt(properties.getProperty("statuettetemplate10", Float.toString(statuettetemplate10)));
	    healpower10 = Integer.parseInt(properties.getProperty("healpower10", Float.toString(healpower10)));
	    healcooldown10 = Integer.parseInt(properties.getProperty("healcooldown10", Float.toString(healcooldown10)));
		}		
	}
	
	public void configure(Properties properties) {
		doconfig(properties);
	    
	}	
	
	public void onItemTemplatesCreated() {
		new dungeonitems();
	}

	  public void onPlayerLogin(Player p)
	  {
		  boolean founded = false;

		      try
		      {	      
		    	  Connection dbcon = ModSupportDb.getModSupportDb();
		    	  PreparedStatement ps = dbcon.prepareStatement("SELECT * FROM DungeonCurrency");
		    	  ResultSet rs = ps.executeQuery();

		      while (rs.next()) {
		    	  if (rs.getString("name").equals(p.getName())){
		    		  founded = true;
		    	  }		    	  
		      }
		      rs.close();
		      ps.close();
			    }
		      catch (SQLException e) {
		          throw new RuntimeException(e);
		        }
		  
		  
		  if (founded == false){
		    try
	    {
	        //create first record which will be event location
	        Connection dbcon = ModSupportDb.getModSupportDb();
	        PreparedStatement ps = dbcon.prepareStatement("INSERT INTO DungeonCurrency (name) VALUES(\""+p.getName()+"\")");
		    ps.executeUpdate();
		    ps.close();

	    }
	    catch (SQLException e)
	    {
	    	throw new RuntimeException(e);
	    }
		    }	

	  }	
		@Override
		public void onServerStarted() {
			currencyadding.onServerStarted();
			if (currency1enabled)ModActions.registerAction(new dungeon1action()); ModActions.registerAction(new heal1action());
			if (currency2enabled)ModActions.registerAction(new dungeon2action()); ModActions.registerAction(new heal2action());
			if (currency3enabled)ModActions.registerAction(new dungeon3action()); ModActions.registerAction(new heal3action());
			if (currency4enabled)ModActions.registerAction(new dungeon4action()); ModActions.registerAction(new heal4action());
			if (currency5enabled)ModActions.registerAction(new dungeon5action()); ModActions.registerAction(new heal5action());
			if (currency6enabled)ModActions.registerAction(new dungeon6action()); ModActions.registerAction(new heal6action());
			if (currency7enabled)ModActions.registerAction(new dungeon7action()); ModActions.registerAction(new heal7action());
			if (currency8enabled)ModActions.registerAction(new dungeon8action()); ModActions.registerAction(new heal8action());
			if (currency9enabled)ModActions.registerAction(new dungeon9action()); ModActions.registerAction(new heal9action());
			if (currency10enabled)ModActions.registerAction(new dungeon10action()); ModActions.registerAction(new heal10action());
			
			ModActions.registerAction(new redoconfigaction());

		    try
		    {
		      Connection con = ModSupportDb.getModSupportDb();
		      String sql = "";
		      
		      if (!ModSupportDb.hasTable(con, "DungeonCurrency")) {
		        sql = "CREATE TABLE DungeonCurrency (\t\tname\t\t\t\tVARCHAR(30)\t\t\tNOT NULL DEFAULT 'Unknown',"
		        		+ "\t\tdungeon1\t\t\t\t\tINT\t\tNOT NULL DEFAULT 0,"
		        		+ "\t\tdungeon2\t\t\t\t\tINT\t\tNOT NULL DEFAULT 0,"
		        		+ "\t\tdungeon3\t\t\t\t\tINT\t\tNOT NULL DEFAULT 0,"
		        		+ "\t\tdungeon4\t\t\t\t\tINT\t\tNOT NULL DEFAULT 0,"
		        		+ "\t\tdungeon5\t\t\t\t\tINT\t\tNOT NULL DEFAULT 0,"
		        		+ "\t\tdungeon6\t\t\t\t\tINT\t\tNOT NULL DEFAULT 0,"
		        		+ "\t\tdungeon7\t\t\t\t\tINT\t\tNOT NULL DEFAULT 0,"
		        		+ "\t\tdungeon8\t\t\t\t\tINT\t\tNOT NULL DEFAULT 0,"
		        		+ "\t\tdungeon9\t\t\t\t\tINT\t\tNOT NULL DEFAULT 0,"
		        		+ "\t\tdungeon10\t\t\t\t\tINT\t\tNOT NULL DEFAULT 0,"
		        		+ "\t\tLastHealed\t\t\t\t\tLONG\t\tNOT NULL DEFAULT 0)";
		        PreparedStatement ps = con.prepareStatement(sql);
		        ps.execute();
		        ps.close();       
		      }
		    }
		    catch (SQLException e)
		    {
		    	throw new RuntimeException(e);
		    }			
		}
		
		@Override
		public void preInit() {
			ModActions.init();
		}
		
		@Override
		  public boolean onPlayerMessage(Communicator communicator, String message)
		  {
		    if ((message != null) && (message.startsWith("/dungeons"))) {
		    	communicator.sendNormalServerMessage(communicator.getPlayer().getName()+"");
	        	Connection dbcon = null;
	  	      PreparedStatement ps = null;
	  	      ResultSet rs = null;
	  	      try
	  	      {	      
	  	      dbcon = ModSupportDb.getModSupportDb();
	  	      ps = dbcon.prepareStatement("SELECT * FROM DungeonCurrency WHERE name = \""+communicator.getPlayer().getName()+"\"");
	  	      rs = ps.executeQuery();
	  	      
				int dung1 = rs.getInt("dungeon1");
				int dung2 = rs.getInt("dungeon2");
				int dung3 = rs.getInt("dungeon3");
				int dung4 = rs.getInt("dungeon4");
				int dung5 = rs.getInt("dungeon5");
				int dung6 = rs.getInt("dungeon6");
				int dung7 = rs.getInt("dungeon7");
				int dung8 = rs.getInt("dungeon8");
				int dung9 = rs.getInt("dungeon9");
				int dung10 = rs.getInt("dungeon10");
	  	      rs.close();
	  	      ps.close();
	  	      dbcon.close();
				if (currency1enabled)communicator.sendNormalServerMessage("You have "+dung1+" of "+currencyname1);
				if (currency2enabled)communicator.sendNormalServerMessage("You have "+dung2+" of "+currencyname2);
				if (currency3enabled)communicator.sendNormalServerMessage("You have "+dung3+" of "+currencyname3);
				if (currency4enabled)communicator.sendNormalServerMessage("You have "+dung4+" of "+currencyname4);
				if (currency5enabled)communicator.sendNormalServerMessage("You have "+dung5+" of "+currencyname5);
				if (currency6enabled)communicator.sendNormalServerMessage("You have "+dung6+" of "+currencyname6);
				if (currency7enabled)communicator.sendNormalServerMessage("You have "+dung7+" of "+currencyname7);
				if (currency8enabled)communicator.sendNormalServerMessage("You have "+dung8+" of "+currencyname8);
				if (currency9enabled)communicator.sendNormalServerMessage("You have "+dung9+" of "+currencyname9);
				if (currency10enabled)communicator.sendNormalServerMessage("You have "+dung10+" of "+currencyname10);	  	      
	  	    }
	  	      catch (SQLException e) {
	  	          throw new RuntimeException(e);
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