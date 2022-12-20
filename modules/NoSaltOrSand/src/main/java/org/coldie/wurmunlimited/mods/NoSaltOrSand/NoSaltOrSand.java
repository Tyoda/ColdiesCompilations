package org.coldie.wurmunlimited.mods.NoSaltOrSand;

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

public class NoSaltOrSand implements WurmServerMod, Initable, Versioned {
	public static final Logger logger = Logger.getLogger(NoSaltOrSand.class.getName());

	public static final String version = "ty1.0";

	@Override
	public void init() {
		 replacemethod();
	}	
    
	private void replacemethod(){
       try {
            CtClass ctc = HookManager.getInstance().getClassPool().get("com.wurmonline.server.zones.TilePoller");
            ctc.getDeclaredMethod("pollNextTile").instrument(new ExprEditor(){
                 public void edit(MethodCall m) throws CannotCompileException {   
                	 if (m.getMethodName().equals("nextInt")) {
                		 logger.log(Level.INFO, "replacing random int with 5.");
		                 m.replace("$_ = 5;");
                	}
                }
            });
        }
        catch (CannotCompileException | NotFoundException e) {
            logger.log(Level.SEVERE, "Failed to apply No salt or sand interception", e);
        }
	}
	
	@Override
	public String getVersion(){
		return version;
	}
}