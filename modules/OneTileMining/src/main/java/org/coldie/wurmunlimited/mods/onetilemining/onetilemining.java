package org.coldie.wurmunlimited.mods.onetilemining;


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



public class onetilemining implements WurmServerMod, Configurable, Initable, ServerStartedListener, Versioned {
	public static final Logger logger = Logger.getLogger(onetilemining.class.getName());

	public static final String version = "ty1.0";
   
	// static int onetileminingline = 3186;

	// static int onetileminingline2 = 3451;
	static int count = 0;
	static int countskill = 0;
	static boolean done = false;
	static float levelfactor = 1.0f;
	static String minecode = "Math.max(0.5f, (float)mining.getKnowledge(0.0) / 100.0f);";
	static boolean onetilehook = false;
	static boolean loweraction = false;
	static boolean surfaceminechance = false;
	
	@Override
	public void init() {
		if(onetilehook) {
			replaceMethod();
			replaceMethod2();
			replaceMethod3();
		}
		if(surfaceminechance) {
			replaceMethod4();
		}
	}

	public static void doconfig(Properties properties){
		minecode = properties.getProperty("minecode",minecode);
		levelfactor = Float.parseFloat(properties.getProperty("levelfactor", Float.toString(levelfactor)));
		loweraction = Boolean.parseBoolean(properties.getProperty("loweraction", Boolean.toString(loweraction)));
		onetilehook = Boolean.parseBoolean(properties.getProperty("onetilehook", Boolean.toString(onetilehook)));
		surfaceminechance = Boolean.parseBoolean(properties.getProperty("surfaceminechance", Boolean.toString(surfaceminechance)));
		
	}
	@Override
	public void onServerStarted() {
		ModActions.registerAction(new onetilereloadaction());
		if(loweraction) ModActions.registerAction(new surfacelevelaction());
	}	
	
	public void configure(Properties properties) {
		doconfig(properties);
	}
	
	private void replaceMethod4(){
		countskill = 0;
		try {
			CtClass ctc = HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.TileRockBehaviour");
			ctc.getDeclaredMethod("mine").instrument(new ExprEditor(){
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("max")) {
						countskill = countskill +1;
						logger.info(countskill+" count");
						if (countskill == 3) {
							logger.log(Level.INFO, "Replacing Math.max(0.2f, (float)mining.getKnowledge(0.0) / 200.0f);");
							m.replace("$_ = "+minecode);
						}
					}
				}
			});
		}
		catch (CannotCompileException | NotFoundException e) {
			logger.log(Level.SEVERE, "Failed to apply onetilemining interception", e);
		}
	}

	private void replaceMethod(){
		count = 0;
		try {
			CtClass ctc = HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.TileRockBehaviour");
			ctc.getDeclaredMethod("mine").instrument(new ExprEditor(){
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("getTile")) {
						if (++count == 2) {
							m.replace("$_ = org.coldie.wurmunlimited.mods.onetilemining.onetilecheck.canmine(digTilex,digTiley);");
						}
					}
				}
			});
		}
		catch (CannotCompileException | NotFoundException e) {
			logger.log(Level.SEVERE, "Failed to apply onetilemining interception", e);
		}

		count = 0;
		try {
			CtClass ctc = HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.CaveTileBehaviour");
			ctc.getDeclaredMethod("raiseRockLevel").instrument(new ExprEditor(){
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("decodeType")) {
						if (++count == 3) {
							m.replace("$_ = 4;");
						}
					}
				}
			});
		}
		catch (CannotCompileException | NotFoundException e) {
			logger.log(Level.SEVERE, "Failed to apply concrete interception", e);
		}
	}

	private void replaceMethod2(){
		try {
			CtClass ctc = HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.TileRockBehaviour");
			ctc.getDeclaredMethod("mine").instrument(new ExprEditor(){
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("encode") && !done){
						done = true;
						m.replace("$_ = org.coldie.wurmunlimited.mods.onetilemining.onetilecheck.gettile(digTilex,digTiley,newHeight);");
					}
				}
			});
		}
		catch (CannotCompileException | NotFoundException e) {
			logger.log(Level.SEVERE, "Failed to apply onetilemining interception", e);
		}
	}

	private void replaceMethod3(){
		try {
			CtClass ctc = HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.CaveTileBehaviour");
			ctc.getDeclaredMethod("flatten").instrument(new ExprEditor(){
				int thiscount = 0;
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("getStandardActionTime")){
						++thiscount;
						if (thiscount == 1 ||  thiscount == 3  || thiscount == 5 ) {
							m.replace("$_ = org.coldie.wurmunlimited.mods.onetilemining.onetilecheck.getactiontime(performer, mining, source);");
						}
					}
				}
			});
		}
		catch (CannotCompileException | NotFoundException e) {
			logger.log(Level.SEVERE, "Failed to apply level interception", e);
		}
	}

	@Override
	public String getVersion() {
		return version;
	}
}

