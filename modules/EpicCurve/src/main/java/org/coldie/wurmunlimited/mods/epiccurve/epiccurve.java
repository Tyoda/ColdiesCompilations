package org.coldie.wurmunlimited.mods.epiccurve;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.Versioned;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import java.util.logging.Logger;


public class epiccurve implements WurmServerMod, Initable, Versioned {
	static Logger logger = Logger.getLogger(epiccurve.class.getName());
    public static final String version = "ty1.0";
   
    @Override
    public void init() {
        enableEpicCurve();
    }

    private void enableEpicCurve() {
        ClassPool cp = HookManager.getInstance().getClassPool();
        try {
            CtClass ctClass = cp.get("com.wurmonline.server.skills.Skill");
            CtConstructor constructor = ctClass.getClassInitializer();
            constructor.instrument(new ExprEditor(){
                public void edit(FieldAccess m) throws CannotCompileException {
                    if (m.getClassName().equals("com.wurmonline.server.ServerEntry") && m.getFieldName().equals("EPIC")) {
                        logger.warning("Epic 1: modified skillMod");
                        m.replace("$_ = true;");
                    }
                }
            });

            ctClass = cp.get("com.wurmonline.server.Server");
            ctClass.getDeclaredMethods("getModifiedFloatEffect")[0].instrument(new ExprEditor(){
                public void edit(FieldAccess m) throws CannotCompileException {
                    if (m.getFieldName().equals("EpicServer")) {
                        logger.warning("Epic 3: modified float effect enabled (curve)");
                        m.replace("$_ = true;");
                    }
                }
            });

            ctClass.getDeclaredMethods("getModifiedPercentageEffect")[0].instrument(new ExprEditor(){
                public void edit(FieldAccess m) throws CannotCompileException {
                    if (m.getFieldName().equals("EpicServer")) {
                        logger.warning("Epic 4: modified percentage effect enabled (curve)");
                        m.replace("$_ = true;");
                    }
                }
            });
        }
        catch (CannotCompileException | NotFoundException e) {
            throw new HookException(e);
        }
        logger.warning("Epic 5: done modifying Epic concept");
    }

    @Override
    public String getVersion() {
        return version;
    }
}