package org.coldie.wurmunlimited.mods.tentsleep;


import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.Server;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.villages.Villages;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import javassist.CtClass;
import javassist.CtPrimitiveType;
import javassist.bytecode.Descriptor;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Properties;

public class tentsleep implements WurmServerMod, ServerStartedListener, Configurable {

    public static final String version = "ty1.0";
	
    @Override
    public void configure(Properties properties) {
    	
        try {

            String descriptor = Descriptor.ofMethod(CtPrimitiveType.booleanType, new CtClass[] {
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.creatures.Creature")});       	
                HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.MethodsItems", "mayDropTentOnTile", descriptor,
                () -> (proxy, method, args) -> {
                    Creature performer = (Creature) args[0];

                    VolaTile t = Zones.getTileOrNull(Zones.safeTileX(performer.getTileX()), Zones.safeTileY(performer.getTileY()), performer.isOnSurface());
                    if (t != null) {
                        if ((t.getVillage() != null) && (t.getVillage() == performer.getCitizenVillage()))
                        {
                          return true;
                        }
                    }

                    for (int x = performer.getTileX() - 1; x <= performer.getTileX() + 1; x++) {
                        for (int y = performer.getTileY() - 1; y <= performer.getTileY() + 1; y++)
                        {
                            if (Villages.getVillageWithPerimeterAt(Zones.safeTileX(x), Zones.safeTileY(y), true) != null)
                            {
                                return false;
                            }
                            if (performer.isOnSurface())
                            {
                              int tile = Server.surfaceMesh.getTile(Zones.safeTileX(x), Zones.safeTileY(y));
                              if (Tiles.decodeHeight(tile) < 1)
                              {
                                return false;
                              }
                            }
                        }
                      }
                      return true;
                        });
            }
        catch (Exception e) {
            throw new HookException(e);
        }   	
    	
    }

    @Override
    public void onServerStarted() {
        ModActions.registerAction(new tent());
    }

    public String getVersion() {
        return version;
    }
}