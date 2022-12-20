package org.coldie.wurmunlimited.mods.fishmonger;

import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class fishmonger implements WurmServerMod, Configurable, ItemTemplatesCreatedListener, ServerStartedListener, Initable, Versioned {
	public static final Logger logger = Logger.getLogger(fishmonger.class.getName());

	public static final String version = "ty1.0";
	public static int itemtemplate1 = 6311;
	public static String itemmodel1 = "model.creature.humanoid.human.child.";
	public static float adjustment = 1.0f;
	public static float rare = 1.0f;
	public static float supreme = 1.0f;
	public static float fantastic = 1.0f;

	public static int maxinnet = 20;

	public static int  maxprice = 100000;

	public static int minnow=50;
	public static int loach=10;
	public static int wurmfish=10;

	public static int sardine=50;
	public static int roach=50;
	public static int perch=50;
	public static int clam=10;
	public static int brooktrout=10;
	public static int pike=10;
	public static int carp=10;
	public static int catfish=10;

	public static int herring=50;
	public static int smallmouthbass=10;
	public static int snook=10;
	public static int salmon=10;

	public static int octopus=10;
	public static int sailfish=10;
	public static int dorado=10;
	public static int blueshark=10;
	public static int tuna=10;
	public static int marlin=10;
	public static int whiteshark=10;


	@Override
	public void onServerStarted() {
		ModActions.registerAction(new fishmongeraction());
		ModActions.registerAction(new fishpriceaction());
	}

	@Override
	public void init() {
		try {
			CtClass ctc = HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.MethodsFishing");
			ctc.getDeclaredMethod("fish").instrument(new ExprEditor(){
				 public void edit(MethodCall m) throws CannotCompileException {
					 if (m.getMethodName().equals("isEmpty")) {
						 m.replace("if ($0.getItemCount() >"+maxinnet+"){$_ = false;}else{$_ = true;};");
						 logger.log(Level.INFO, "Applied fish net is empty hook");
					}
				}
			});
		} catch (CannotCompileException | NotFoundException e) {
			logger.log(Level.SEVERE, "Failed to apply fish net is empty hook", e);
		}
	}

	@Override
	public void configure(Properties properties) {
		maxprice = Integer.parseInt(properties.getProperty("maxprice", Float.toString(maxprice)));
		maxinnet = Integer.parseInt(properties.getProperty("maxinnet", Float.toString(maxinnet)));
		itemmodel1 = properties.getProperty("itemmodel1",itemmodel1);
		itemtemplate1 = Integer.parseInt(properties.getProperty("itemtemplate1", Float.toString(itemtemplate1)));
		adjustment = Float.parseFloat(properties.getProperty("adjustment", Float.toString(adjustment)));
		rare = Float.parseFloat(properties.getProperty("rare", Float.toString(rare)));
		supreme = Float.parseFloat(properties.getProperty("supreme", Float.toString(supreme)));
		fantastic = Float.parseFloat(properties.getProperty("fantastic", Float.toString(fantastic)));

		minnow = Integer.parseInt(properties.getProperty("minnow", Float.toString(minnow)));
		loach = Integer.parseInt(properties.getProperty("loach", Float.toString(loach)));
		wurmfish = Integer.parseInt(properties.getProperty("wurmfish", Float.toString(wurmfish)));
		sardine = Integer.parseInt(properties.getProperty("sardine", Float.toString(sardine)));
		roach = Integer.parseInt(properties.getProperty("roach", Float.toString(roach)));
		perch = Integer.parseInt(properties.getProperty("perch", Float.toString(perch)));
		clam = Integer.parseInt(properties.getProperty("clam", Float.toString(clam)));
		brooktrout = Integer.parseInt(properties.getProperty("brooktrout", Float.toString(brooktrout)));
		pike = Integer.parseInt(properties.getProperty("pike", Float.toString(pike)));
		carp = Integer.parseInt(properties.getProperty("carp", Float.toString(carp)));
		catfish = Integer.parseInt(properties.getProperty("catfish", Float.toString(catfish)));
		herring = Integer.parseInt(properties.getProperty("herring", Float.toString(herring)));
		smallmouthbass = Integer.parseInt(properties.getProperty("smallmouthbass", Float.toString(smallmouthbass)));
		snook = Integer.parseInt(properties.getProperty("snook", Float.toString(snook)));
		salmon = Integer.parseInt(properties.getProperty("salmon", Float.toString(salmon)));
		octopus = Integer.parseInt(properties.getProperty("octopus", Float.toString(octopus)));
		sailfish = Integer.parseInt(properties.getProperty("sailfish", Float.toString(sailfish)));
		dorado = Integer.parseInt(properties.getProperty("dorado", Float.toString(dorado)));
		blueshark = Integer.parseInt(properties.getProperty("blueshark", Float.toString(blueshark)));
		tuna = Integer.parseInt(properties.getProperty("tuna", Float.toString(tuna)));
		marlin = Integer.parseInt(properties.getProperty("marlin", Float.toString(marlin)));
		whiteshark = Integer.parseInt(properties.getProperty("whiteshark", Float.toString(whiteshark)));
	}


	public void onItemTemplatesCreated() {
	new FMitems();
	}

	public static int getfishvalue(Item Fish, Creature performer) {
		int base = getbase(Fish);
		float rareAdjust = 1.0f;
		switch (Fish.getRarity()) {
			case 1:
				rareAdjust = rare;
				break;
			case 2:
				rareAdjust = supreme;
				break;
			case 3:
				rareAdjust = fantastic;
				break;
		}

		if (base == 0) return 0;

		return Math.min(
				(int) (base * Fish.getWeightGrams() * adjustment * 0.01 * rareAdjust),
				maxprice
		);
	}

	private static int getbase(Item Fish) {
		switch (Fish.getTemplateId()) {
			case 162: 		// roach
				return roach;
			case 163:  		// perch
				return perch;
			case 165:  		// brooktrout
				return brooktrout;
			case 157:  		// pike
				return pike;
			case 160:  		// catfish
				return catfish;
			case 161:   	// snook
				return snook;
			case 159:  		// herring
				return herring;
			case 164:  		// carp
				return carp;
			case 158:  		// smallmouthbass
				return smallmouthbass;
			case 1335:  	// salmon
				return salmon;
			case 572:  		// octopus
				return octopus;
			case 569:  		// marlin
				return marlin;
			case 570:  		// blueshark
				return blueshark;
			case 574:  		// dorado
				return dorado;
			case 573:  		// sailfish
				return sailfish;
			case 571:  		// whiteshark
				return whiteshark;
			case 575:  		// tuna
				return tuna;
			case 1338:  	// minnow
				return minnow;
			case 1339:  	// loach
				return loach;
			case 1340:  	// wurmfish
				return wurmfish;
			case 1337:  	// sardine
				return sardine;
			case 1394:  	// clam
				return clam;
			default:
				return 0;
		}
	}

	@Override
	public String getVersion() {
		return version;
	}
}