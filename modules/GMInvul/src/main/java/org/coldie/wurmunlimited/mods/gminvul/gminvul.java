package org.coldie.wurmunlimited.mods.gminvul;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.Versioned;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import java.util.logging.Level;
import java.util.logging.Logger;

public class gminvul implements WurmServerMod, Initable, Versioned {
	public static final Logger logger = Logger.getLogger(gminvul.class.getName());
	public static final String version = "ty1.0";
	
	@Override
	public void init() {
		try {
			CtClass ctc = HookManager.getInstance().getClassPool().get("com.wurmonline.server.players.Player");
			ctc.getDeclaredMethod("isInvulnerable").instrument(new ExprEditor(){
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("getPower")) {
						logger.log(Level.INFO, "changing GM invul power from 1 to 3");
						m.replace("$_ = getPower() - 2;");
					}
				}
			});
		} catch (CannotCompileException | NotFoundException e) {
			logger.log(Level.SEVERE, "Failed to apply GM invulnerability interception", e);
		}
	}

	@Override
	public String getVersion(){
		return version;
	}
}