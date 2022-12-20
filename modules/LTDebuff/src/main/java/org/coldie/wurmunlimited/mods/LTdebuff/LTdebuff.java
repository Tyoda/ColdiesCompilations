package org.coldie.wurmunlimited.mods.LTdebuff;

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


public class LTdebuff implements Configurable, WurmServerMod, Initable, ServerStartedListener, Versioned {
	public static final Logger logger = Logger.getLogger(LTdebuff.class.getName());

	public static final String version = "ty1.0";
	static int LTpower = 4;
	static boolean debug = false;

	int Count = 0;

	@Override
	public void configure(Properties properties) {
		doconfig(properties);
	}
	
	public static void doconfig(Properties properties){
		LTpower = Integer.parseInt(properties.getProperty("LTpower", Float.toString(LTpower)));
		debug = Boolean.parseBoolean(properties.getProperty("debug", Boolean.toString(debug)));
		if (LTpower < 1) LTpower = 1;
	}

	@Override
	public void onServerStarted() {
		ModActions.registerAction(new LTreload());
	}


	@Override
	public void init() {
		try {
			CtClass ctc = HookManager.getInstance().getClassPool().get("com.wurmonline.server.creatures.CombatHandler");
			ctc.getDeclaredMethod("setDamage").instrument(new ExprEditor(){
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("min")) {
						Count = Count +1;
						if (Count == 4) {
							m.replace("$_ = org.coldie.wurmunlimited.mods.LTdebuff.LTdebuffReplace.DoStuffs(targetWound,toHeal);");
							logger.log(Level.INFO, "replaced LT debuff value to be 1");
						}
					}
				}
			});
		}
		catch (CannotCompileException | NotFoundException e) {
			logger.log(Level.SEVERE, "Failed to apply LTdebuff interception", e);
		}
	}

	@Override
	public String getVersion(){
		return version;
	}
}