package org.coldie.wurmunlimited.mods.leadmore;

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


public class leadmore implements WurmServerMod, Initable, Versioned {
	public static final Logger logger = Logger.getLogger(leadmore.class.getName());
    public static final String version = "ty1.0";
   
    @Override
    public void init() {
        mayleadmore();
    }

    public void mayleadmore() {
        try {
            CtClass ctc = HookManager.getInstance().getClassPool().get("com.wurmonline.server.players.Player");
            ctc.getDeclaredMethod("mayLeadMoreCreatures").instrument(new ExprEditor(){
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("size")) {
                        logger.log(Level.INFO, "lead more hook");
                        m.replace("$_ = ($0.size() - 2);");
                    }
                }
            });
        } catch (CannotCompileException | NotFoundException e) {
            logger.log(Level.SEVERE, "Failed to apply hook", e);
        }
    }

    public String getVersion() {
        return version;
    }
}