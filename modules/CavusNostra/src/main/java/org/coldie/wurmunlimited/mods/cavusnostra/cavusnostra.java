package org.coldie.wurmunlimited.mods.cavusnostra;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.classhooks.InvocationHandlerFactory;
import org.gotti.wurmunlimited.modloader.interfaces.*;

import com.wurmonline.server.behaviours.MethodsItems;
import com.wurmonline.server.creatures.CreatureTemplate;
import com.wurmonline.server.creatures.CreatureTemplateFactory;
import com.wurmonline.server.creatures.CreatureTemplateIds;
import com.wurmonline.server.creatures.NoSuchCreatureTemplateException;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.skills.SkillSystem;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtPrimitiveType;
import javassist.bytecode.Descriptor;


public class cavusnostra implements WurmServerMod, Configurable, Initable, ServerStartedListener, Versioned {
	public static final Logger logger = Logger.getLogger(cavusnostra.class.getName());
	public static final String version = "ty1.0";
	static boolean moonmetalimp = false;
	static boolean groomall = true;
	static boolean leadunicorn = true;
	static boolean Traitremoval = false;
	static int[] traitIDs;
	public static String[] traitIds;
	
	public void init() {
		if(moonmetalimp) mooonmetalimp();
	}
	
	@Override
	public void onServerStarted() {
	
		try {
			Setfightskillgain();
		} catch (IllegalArgumentException | IllegalAccessException
				| ClassCastException | NoSuchFieldException e1) {
			e1.printStackTrace();
		}
		
		if(leadunicorn)
			try {
				Setunicorncanlead();
			} catch (NoSuchCreatureTemplateException | IllegalArgumentException | IllegalAccessException |
					 ClassCastException | NoSuchFieldException e) {
				e.printStackTrace();
			}
		if(groomall)
			try {
				groomallhook();
			} catch (NoSuchCreatureTemplateException | IllegalArgumentException | IllegalAccessException |
					 ClassCastException | NoSuchFieldException e) {
				e.printStackTrace();
			}
		if(Traitremoval){	
			changeTraits();
		}
	}
	public void configure(Properties properties) {
		moonmetalimp = Boolean.parseBoolean(properties.getProperty("moonmetalimp", Boolean.toString(moonmetalimp)));
		groomall = Boolean.parseBoolean(properties.getProperty("groomall", Boolean.toString(groomall)));
		leadunicorn = Boolean.parseBoolean(properties.getProperty("leadunicorn", Boolean.toString(leadunicorn)));
		Traitremoval = Boolean.parseBoolean(properties.getProperty("Traitremoval", Boolean.toString(Traitremoval)));
		traitIds = properties.getProperty("traitIds").split(";");

	
	}
	
	
	private void mooonmetalimp(){

        try {
        	ClassPool classPool = HookManager.getInstance().getClassPool();
            String descriptor = Descriptor.ofMethod(CtPrimitiveType.intType, new CtClass[] {
            		classPool.get("com.wurmonline.server.items.Item")});       	
               HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.MethodsItems", "getImproveTemplateId", descriptor, new InvocationHandlerFactory(){
 
            	@Override 
                        public InvocationHandler createInvocationHandler(){
                            return new InvocationHandler(){
                                @Override
                                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
									//logger.log(Level.INFO, "Moon metal imp using steel hook");
                                	byte material = MethodsItems.getImproveMaterial((Item) args[0]);
                                    switch (material) {
                                        case 56:
                                        case 57:
                                        case 67:
                                            return 205;
                                    }
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

		static public void Setunicorncanlead() throws NoSuchCreatureTemplateException, IllegalArgumentException, IllegalAccessException, ClassCastException, NoSuchFieldException {
				logger.log(Level.INFO, "Unicorn lead Hook");
				boolean canlead = true;
				ReflectionUtil.setPrivateField(CreatureTemplateFactory.getInstance().getTemplate(CreatureTemplateIds.UNICORN_CID),
				ReflectionUtil.getField(CreatureTemplate.class, "leadable"), canlead);
		}
	
		static public void groomallhook() throws NoSuchCreatureTemplateException, IllegalArgumentException, IllegalAccessException, ClassCastException, NoSuchFieldException {
				logger.log(Level.INFO, "Hell horse and unicorn grooming hook");
				boolean canlead = true;
				ReflectionUtil.setPrivateField(CreatureTemplateFactory.getInstance().getTemplate(CreatureTemplateIds.HELL_HORSE_CID),
                ReflectionUtil.getField(CreatureTemplate.class, "domestic"), canlead);	
		
				//ReflectionUtil.setPrivateField(SkillTemplate.class   ,
                //ReflectionUtil.getField(SkillTemplate.class, "difficulty"), canlead);		
		}

		static public void Setfightskillgain() throws IllegalArgumentException, IllegalAccessException, ClassCastException, NoSuchFieldException {
			for (int x = 1; x < 100; x++) {
				logger.log(Level.INFO, "x:"+x+" name: "+SkillSystem.getSkillTemplateByIndex(x).getName()+" difficulty: "+SkillSystem.getSkillTemplateByIndex(x).getDifficulty());
				if(SkillSystem.getSkillTemplateByIndex(x).getName().equalsIgnoreCase("Fighting")) {
					SkillSystem.getSkillTemplateByIndex(x).setDifficulty((float)(20000 * 0.1));
					logger.log(Level.INFO, "fight difficulty: "+SkillSystem.getSkillTemplateByIndex(x).getDifficulty());
				    return;
				}
			}
	}
		
		static void changeTraits(){
	       try {
	            String descriptor = Descriptor.ofMethod(CtPrimitiveType.booleanType, new CtClass[] {
	            		CtPrimitiveType.intType});
	               HookManager.getInstance().registerHook("com.wurmonline.server.creatures.Traits", "isTraitNegative", descriptor, new InvocationHandlerFactory(){
	 
	            	@Override 
	                        public InvocationHandler createInvocationHandler(){
	                            return new InvocationHandler(){
	                                @Override
	                                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	                        			for(String id : traitIds)
	                        			{
	                        				logger.log(Level.INFO, "Negative Trait Hook for ID: "+id);
	                                		if((int)args[0] == Integer.parseInt(id)){
	                                			return true;
	                                        }	                        			
	                                	}                            	

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
	public String getVersion(){
		return version;
	}
}