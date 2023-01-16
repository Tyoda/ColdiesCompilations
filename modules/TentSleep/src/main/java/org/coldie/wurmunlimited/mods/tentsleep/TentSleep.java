package org.coldie.wurmunlimited.mods.tentsleep;


import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.Server;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.villages.Villages;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import javassist.*;
import javassist.bytecode.Descriptor;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Properties;
import java.util.logging.Logger;

public class TentSleep implements WurmServerMod, ServerStartedListener, PreInitable, Configurable {

    public static final String version = "ty1.1";

    public static final Logger logger = Logger.getLogger(TentSleep.class.getName());

    private float sBModifier = 0.75f;

    @Override
    public void configure(Properties properties) {
        sBModifier = Float.parseFloat(properties.getProperty("tentSBModifier", String.valueOf(sBModifier)));
        logger.info("Configured tent sleep bonus modifier: "+sBModifier);
    }

    @Override
    public void preInit() {
        ModActions.init();
        try {
            ClassPool classPool = HookManager.getInstance().getClassPool();
            // edit mayDropTentOnTile
            String descriptor = Descriptor.ofMethod(CtPrimitiveType.booleanType, new CtClass[] {
                HookManager.getInstance().getClassPool().get("com.wurmonline.server.creatures.Creature")
            });
            HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.MethodsItems", "mayDropTentOnTile", descriptor,
                () -> (proxy, method, args) -> {
                    Creature performer = (Creature) args[0];

                    VolaTile t = Zones.getTileOrNull(Zones.safeTileX(performer.getTileX()), Zones.safeTileY(performer.getTileY()), performer.isOnSurface());
                    if (t != null
                            && t.getVillage() != null
                            && t.getVillage() == performer.getCitizenVillage()){
                        return true;
                    }

                    for (int x = performer.getTileX() - 1; x <= performer.getTileX() + 1; x++) {
                        for (int y = performer.getTileY() - 1; y <= performer.getTileY() + 1; y++) {
                            if (Villages.getVillageWithPerimeterAt(Zones.safeTileX(x), Zones.safeTileY(y), true) != null) {
                                return false;
                            }
                            if (performer.isOnSurface()) {
                                int tile = Server.surfaceMesh.getTile(Zones.safeTileX(x), Zones.safeTileY(y));
                                if (Tiles.decodeHeight(tile) < 1) {
                                    return false;
                                }
                            }
                        }
                    }
                    return true;
                }
            );

            // add sleep bonus modifier
            CtClass ctPlayerInfo = classPool.getCtClass("com.wurmonline.server.players.PlayerInfo");
            ctPlayerInfo.getMethod("calculateSleep", Descriptor.ofMethod(CtPrimitiveType.voidType, new CtClass[0])).instrument(
                new ExprEditor(){
                    @Override
                    public void edit(MethodCall m) throws CannotCompileException {
                        if (m.getMethodName().equals("setSleep")){
                            m.replace(""+
                                "if(this.bed != -10L && this.bed != 0L){"+
                                    "try{"+
                                        "com.wurmonline.server.items.Item bedItem = com.wurmonline.server.Items.getItem(this.bed);"+
                                        "float quality = bedItem.getCurrentQualityLevel();"+
                                        "logger.info(this.getName()+\" just woke up. Base SB: \"+secs+\"s, slept in: \"+bedItem.getName()+\" of quality: \"+bedItem.getCurrentQualityLevel());"+
                                        "secs = (long)((double)secs * (0.9 + 0.33/(1+Math.exp(-((double)quality/15-4)))));"+
                                        "if(bedItem.isTent()){"+
                                            "logger.info(this.getName()+\" slept in a tent.\");"+
                                            "secs = (long)((double)secs * (double)"+sBModifier+");"+
                                        "}else if(bedItem.isBed()){"+
                                            "logger.info(this.getName()+\" slept in a bed.\");"+
                                        "}else{" +
                                            "logger.info(this.getName()+\" slept in something else. \");"+
                                            "secs = 0L;"+
                                        "}"+
                                    "} catch(com.wurmonline.server.NoSuchItemException itemException){"+
                                        "secs = 0L;"+
                                        "org.coldie.wurmunlimited.mods.tentsleep.TentSleep.logger.log(java.util.logging.Level.SEVERE, \"Failed to get bed item: \", itemException);"+
                                    "}"+
                                "}"+
                                "org.coldie.wurmunlimited.mods.tentsleep.TentSleep.logger.info(this.getName()+\" got SB \"+secs+'s');"+
                                "$_ = $proceed(this.sleep + (int)secs);"
                            );
                        }
                    }
                }
            );
        }
        catch (NotFoundException | CannotCompileException e) {
            logger.severe("Could not hook.");
            throw new HookException(e);
        }
    }

    @Override
    public void onServerStarted() {
        ModActions.registerAction(new SleepAction());
    }

    public String getVersion() {
        return version;
    }
}