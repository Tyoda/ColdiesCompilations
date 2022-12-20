package org.coldie.wurmunlimited.mods.sitheil;

import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.villages.Village;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zone;
import com.wurmonline.server.zones.Zones;
import javassist.CtClass;
import javassist.CtPrimitiveType;
import javassist.bytecode.Descriptor;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.classhooks.InvocationHandlerFactory;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.Versioned;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class sitheil implements WurmServerMod, Initable, Versioned {
	public static final Logger logger = Logger.getLogger(sitheil.class.getName());
	public static final String version = "ty1.0";

	@Override
	public void init() {
		try {
			// public boolean action(Action act, Creature performer, Item source, int tilex, int tiley,
			// boolean onSurface, int heightOffset, int tile, int dir, short action, float counter)
			String descriptor = Descriptor.ofMethod(CtPrimitiveType.booleanType, new CtClass[] {
					HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.Action"),
					HookManager.getInstance().getClassPool().get("com.wurmonline.server.creatures.Creature"),
					HookManager.getInstance().getClassPool().get("com.wurmonline.server.items.Item"),
					CtPrimitiveType.intType,
					CtPrimitiveType.intType,
					CtPrimitiveType.booleanType,
					CtPrimitiveType.intType,
					CtPrimitiveType.intType,
					CtPrimitiveType.intType,
					CtPrimitiveType.shortType,
					CtPrimitiveType.floatType
			});
			HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.CaveWallBehaviour", "action", descriptor, new InvocationHandlerFactory(){
				@Override
				public InvocationHandler createInvocationHandler(){
					return new InvocationHandler(){
						@Override
						public Object invoke(Object object, Method method, Object[] args) throws Throwable {
							Creature performer = (Creature) args[1];
							short action = (short) args[9];
							float counter = (float) args[10];
							int x = (int) args[3];
							int y = (int) args[4];
							if (action == 145 || action == 146 || action == 147) {
								if (counter == 1.0F) {
									Zone zone = Zones.getZone(x, y, true);
									VolaTile vt = zone.getOrCreateTile(x, y);
									if (vt != null) {
										Village village = vt.getVillage();
										if (village == null) {
											performer.getCommunicator().sendAlertServerMessage("Mining off deed is against the rules on this server.");
											logger.log(Level.SEVERE, performer.getName()+": I am naughty and mining off deed at "+x+"/"+y+"."); // lol
										}else {
											//performer.getCommunicator().sendAlertServerMessage("on deed test print.");
											//performer.getCommunicator().sendMgmtMessage(System.currentTimeMillis(), performer.getName(),"on deed test print.");
										}
									}
								}
							}
							return method.invoke(object, args);
						}
					};
				}
			});
		} catch (Exception e) { throw new HookException(e); }
	}

	@Override
	public String getVersion(){
		return version;
	}
}