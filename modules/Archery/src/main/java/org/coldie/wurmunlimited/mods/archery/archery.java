package org.coldie.wurmunlimited.mods.archery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.classhooks.InvocationHandlerFactory;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import com.wurmonline.server.Items;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;


import javassist.CtClass;
import javassist.CtPrimitiveType;
import javassist.bytecode.Descriptor;
import javassist.ClassPool;

public class archery implements WurmServerMod, Configurable, ServerStartedListener, Initable, Versioned {
	public static final Logger logger = Logger.getLogger(archery.class.getName());

    public static final String version = "ty1.0";

	public float uniquefactor = 1.0f;
	public float damagefactor = 1.0f;
	public float firingspeedadjustment = 2;

	@Override
	public void onServerStarted() {
		HookManager hm = HookManager.getInstance();
        ClassPool classPool = hm.getClassPool();
        
		ModActions.registerAction(new archeryloreaction());
	       try {
	            String descriptor = Descriptor.ofMethod(CtPrimitiveType.booleanType, new CtClass[] {
	            		classPool.get("com.wurmonline.server.creatures.Creature"), 
	            		classPool.get("com.wurmonline.server.creatures.Creature"), 
	            		classPool.get("com.wurmonline.server.items.Item"), 
	            		CtPrimitiveType.floatType,
	            		classPool.get("com.wurmonline.server.behaviours.Action")
	            		});
	               hm.registerHook("com.wurmonline.server.combat.Archery", "attack", descriptor, new InvocationHandlerFactory(){
	 
	            	@Override 
	                        public InvocationHandler createInvocationHandler(){
	                            return new InvocationHandler(){
	                                @Override
	                                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	                                	float count = (float)args[3]; 
	                                	if (count != 1.0) args[3] = (float)args[3] * firingspeedadjustment;
	                                    return method.invoke(proxy, args);
	                                }
	                            };
	                        }
	            	});
	            }
	        	catch (Exception e) {
	            throw new HookException(e);
	        	}		
	}

	@Override
	public void configure(Properties properties) {
		damagefactor = Float.parseFloat(properties.getProperty("damagefactor", String.valueOf(damagefactor)));
		uniquefactor = Float.parseFloat(properties.getProperty("uniquefactor", String.valueOf(uniquefactor)));
		firingspeedadjustment = Float.parseFloat(properties.getProperty("firingspeedadjustment", String.valueOf(firingspeedadjustment)));
	}
	
	@Override
	public void init() {
		HookManager hm = HookManager.getInstance();
        ClassPool classPool = hm.getClassPool();
        
        try {

            String descriptor = Descriptor.ofMethod(classPool.get("com.wurmonline.server.items.Item"), new CtClass[] {
            classPool.get("com.wurmonline.server.creatures.Creature")});       	
            hm.registerHook("com.wurmonline.server.combat.Archery", "getArrow", descriptor, new InvocationHandlerFactory(){

                @Override 
                public InvocationHandler createInvocationHandler(){
                       return new InvocationHandler(){
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) {
                                Creature performer = (Creature) args[0];
                                Item arrow; 
                                Item[] inventoryItems = performer.getInventory().getAllItems(false);
                                for (Item inventoryItem : inventoryItems) {
                                    if (inventoryItem.getTemplateId() == 462) {
                                        arrow = inventoryItem.findFirstContainedItem(456);
                                        if (arrow == null)
                                            arrow = inventoryItem.findFirstContainedItem(455);
                                        if (arrow == null) {
                                            arrow = inventoryItem.findFirstContainedItem(454);
                                        }
                                        if (arrow != null) return arrow;
                                    }
                                }
                                arrow = performer.getBody().getBodyItem().findFirstContainedItem(456);
                                if (arrow == null)
                                    arrow = performer.getBody().getBodyItem().findFirstContainedItem(455);
                                if (arrow == null)
                                    arrow = performer.getBody().getBodyItem().findFirstContainedItem(454);
                                if (arrow == null)
                                    arrow = performer.getInventory().findItem(456, true);
                                if (arrow == null)
                                    arrow = performer.getInventory().findItem(455, true);
                                if (arrow == null)
                                    arrow = performer.getInventory().findItem(454, true);                                   	 
                                return arrow;
                       }
                   };
                }
            });
        }
        catch (Exception e) {
           logger.log(Level.SEVERE, "Failed to apply Archery Find arrow interception", e);
           throw new HookException(e);
        }	   
        
        try {
           String descriptor = Descriptor.ofMethod(CtPrimitiveType.booleanType, new CtClass[] {
                   classPool.get("com.wurmonline.server.behaviours.Action"),
                   classPool.get("com.wurmonline.server.creatures.Creature"),
                   classPool.get("com.wurmonline.server.items.Item"),
                   classPool.get("com.wurmonline.server.items.Item"),
                   CtPrimitiveType.shortType,
                   CtPrimitiveType.floatType
           });       	
          hm.registerHook("com.wurmonline.server.behaviours.ItemBehaviour", "action", descriptor, new InvocationHandlerFactory(){
                @Override 
                public InvocationHandler createInvocationHandler(){
                    return new InvocationHandler(){
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Creature performer = (Creature) args[1];
                            Item source = (Item) args[2];
                            Item target = (Item) args[3];

                     	   if(source.getTemplateId() == 454 && target.getTemplateId() == 452  ) {
                     	   Item arrow = ItemFactory.createItem(456, source.getQualityLevel(), performer.getName());
                     	   arrow.setMaterial(source.getMaterial());
                     	   performer.getInventory().insertItem(arrow, true);
                     	   performer.getCommunicator().sendActionResult(true);
                     	   Items.destroyItem(target.getWurmId());
                     	   Items.destroyItem(source.getWurmId());
                     	   return true;
                     	   }
                     	   
                     	   if(target.getTemplateId() == 454 && source.getTemplateId() == 452  ) {
                     	   Item arrow = ItemFactory.createItem(456, target.getQualityLevel(), performer.getName());
                     	   arrow.setMaterial(target.getMaterial());
                     	   performer.getInventory().insertItem(arrow, true);
                     	   performer.getCommunicator().sendActionResult(true);
                     	   Items.destroyItem(target.getWurmId());
                     	   Items.destroyItem(source.getWurmId());
                     	   return true;
                     	   }
                            
                        return method.invoke(proxy, args);
                        }
                    };
            }});
        }
       catch (Exception e) {
    	   logger.log(Level.SEVERE, "Failed to apply Archery Craft interception", e);
           throw new HookException(e);
       }
       
      try {
           String descriptor = Descriptor.ofMethod(CtPrimitiveType.booleanType, new CtClass[] {
        		   classPool.get("com.wurmonline.server.creatures.Creature"),
                   classPool.get("com.wurmonline.server.creatures.Creature"),
                   classPool.get("com.wurmonline.server.items.Item"),
                   classPool.get("com.wurmonline.server.items.Item"),
                   CtPrimitiveType.doubleType,
                   CtPrimitiveType.floatType,
                   CtPrimitiveType.floatType,
                   CtPrimitiveType.byteType,
                   CtPrimitiveType.booleanType,
                   CtPrimitiveType.booleanType,
                   CtPrimitiveType.doubleType,
                   CtPrimitiveType.doubleType
           });       	
              hm.registerHook("com.wurmonline.server.combat.Archery", "hit", descriptor, new InvocationHandlerFactory(){

           	@Override 
                       public InvocationHandler createInvocationHandler(){
                           return new InvocationHandler(){

                               @Override
                               public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                   Creature defender = (Creature) args[0];
                                   Item bow = (Item) args[3];
                                   double damage = (double) args[4];
                                   if (bow != null) {
                                	   damage *= damagefactor;
	                                   if (defender.isUnique()) {
	                                	   damage *= uniquefactor;
	                                   }
	                                   args[4] = damage;
                                   }
                               return method.invoke(proxy, args);
                               }};}});
           }
       catch (Exception e) {
    	   logger.log(Level.SEVERE, "Failed to apply Archery Damage interception", e);
           throw new HookException(e);
       }   

	}

    @Override
    public String getVersion() {
        return version;
    }
}