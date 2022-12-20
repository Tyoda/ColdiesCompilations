package net.coldie.wurmunlimited.mods.buildingskillmod;


import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.skills.NoSuchSkillException;
import com.wurmonline.server.skills.Skill;
import com.wurmonline.server.structures.Structure;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.Versioned;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modloader.classhooks.InvocationHandlerFactory;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtPrimitiveType;
import javassist.NotFoundException;
import javassist.bytecode.Descriptor;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;



public class buildingskillmod implements WurmServerMod, Configurable, Initable, Versioned {
    public static final Logger logger = Logger.getLogger(buildingskillmod.class.getName());

    public static final String version = "ty1.0";

    private static buildingskillmod instance;

    public buildingskillmod(){
        instance = this;
    }
    
    public static int skillfactor = 1;
    public static int bridgeskillfactor = 2;
 
    public static boolean hasLowSkill(Skill requiredSkill, int minSkill, int maxLength, boolean slidingScale, int length) {
    	minSkill = minSkill / bridgeskillfactor;
      if (requiredSkill.getKnowledge(0.0D) < minSkill)
        return true;
      if (slidingScale) {
        float as = (float)Math.min(requiredSkill.getKnowledge(0.0D), 99.0D);
        float k = 90.0F / (99.0F - minSkill);
        float a = 90.0F - (as - minSkill) * k;
        float b = (float)Math.toRadians(a);
        float c = (float)Math.sin(b);
        float d = 1.0F - c;
        float r = d * (maxLength - 5) + 5.0F;



        if (r * bridgeskillfactor < length)
          return true;
      }
      return false;
    }
    
    @Override
    public void configure(Properties properties) {

    	skillfactor = Integer.parseInt(properties.getProperty("skillfactor", String.valueOf(skillfactor)));
    	bridgeskillfactor = Integer.parseInt(properties.getProperty("bridgeskillfactor", String.valueOf(bridgeskillfactor)));
        try {

            String descriptor = Descriptor.ofMethod(CtPrimitiveType.booleanType, new CtClass[] {
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.creatures.Creature"), 
                    CtPrimitiveType.intType, CtPrimitiveType.intType,
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.structures.Structure")});       	
                HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.MethodsStructure", "hasEnoughSkillToExpandStructure", descriptor, new InvocationHandlerFactory(){
 
            	@Override 
                public InvocationHandler createInvocationHandler(){
                    return new InvocationHandler(){

                                
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) {
                    //Boolean result = (Boolean) method.invoke(proxy, args);

                    Creature performer = (Creature) args[0];
                    int tilex = (int) args[1];
                    int tiley = (int) args[2];
                    Structure plannedStructure = (Structure) args[3];

                    Skill carpentry;
                    try {
                        carpentry = performer.getSkills().getSkill(1005);
                    }
                    catch (NoSuchSkillException nss) {
                        performer.getCommunicator().sendNormalServerMessage("You have no idea how you would build a house.");
                        return false;
                    }
                    if (carpentry == null) {
                        performer.getCommunicator().sendNormalServerMessage("You have no idea how you would build a house.");
                        return false;
                    }
                    int limit = plannedStructure != null ? plannedStructure.getLimitFor(tilex, tiley, performer.isOnSurface(), true) : 5;
                    if (limit == 0) {
                        performer.getCommunicator().sendNormalServerMessage("The house seems to have no walls. Please replan.");
                        return false;
                    }
                    if ((double)limit > (carpentry.getKnowledge(0.0) * skillfactor)) {
                        performer.getCommunicator().sendNormalServerMessage("You are not skilled enough in Carpentry to build this size of structure.");
                        return false;
                    }
                    return true;
                        }};}});
            }
        catch (Exception e) {
            throw new HookException(e);
        }
        
        
        
        
        try {

            String descriptor = Descriptor.ofMethod(CtPrimitiveType.booleanType, new CtClass[] {
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.creatures.Creature"), 
                    CtPrimitiveType.intType, CtPrimitiveType.intType,
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.structures.Structure")});       	
               HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.MethodsStructure", "hasEnoughSkillToContractStructure", descriptor, new InvocationHandlerFactory(){
 
            	@Override 
                        public InvocationHandler createInvocationHandler(){
                            return new InvocationHandler(){

                                
                                @Override
                                public Object invoke(Object proxy, Method method, Object[] args) {
                                    Creature performer = (Creature) args[0];
                                    int tilex = (int) args[1];
                                    int tiley = (int) args[2];
                                    Structure plannedStructure = (Structure) args[3];
                                    Skill carpentry;
                                    try {
                                        carpentry = performer.getSkills().getSkill(1005);
                                    }
                                    catch (NoSuchSkillException nss) {
                                        performer.getCommunicator().sendNormalServerMessage("You have no idea how you would build a house.");
                                        return false;
                                    }
                                    if (carpentry == null) {
                                        performer.getCommunicator().sendNormalServerMessage("You have no idea how you would build a house.");
                                        return false;
                                    }
                                    int limit = plannedStructure != null ? plannedStructure.getLimitFor(tilex, tiley, performer.isOnSurface(), true) : 5;
                                    if (limit == 0) {
                                        performer.getCommunicator().sendNormalServerMessage("The house seems to have no walls. Please replan.");
                                        return false;
                                    }
                                    if ((double)limit > (carpentry.getKnowledge(0.0) * skillfactor)) {
                                        performer.getCommunicator().sendNormalServerMessage("You are not skilled enough in Carpentry to build this size of structure.");
                                        return false;
                                    }
                                    return true;                                   
                         
                        }};}});
            }
        catch (Exception e) {
            throw new HookException(e);
        }     
        
      


        
}

	@Override
	public void init() {
        try {
            CtClass ctc = HookManager.getInstance().getClassPool().get("com.wurmonline.server.questions.PlanBridgeQuestion");
            ctc.getDeclaredMethod("sendQuestion").instrument(new ExprEditor(){
                 public void edit(MethodCall m) throws CannotCompileException {
                	 
                	 if (m.getMethodName().equals("hasLowSkill")) {
                		 logger.log(Level.INFO, "bridge hasLowSkill hook");
                         m.replace("$_ =  net.coldie.wurmunlimited.mods.buildingskillmod.buildingskillmod.hasLowSkill(requiredSkill, $2, maxLength, $4, length);");
                	}
                }
               
            });
        }
        catch (CannotCompileException | NotFoundException e) {
            logger.log(Level.SEVERE, "Failed to apply bridge hasLowSkill interception", e);
        }
		
	}

    public String getVersion() {
        return version;
    }

    public static buildingskillmod getInstance(){
        return instance;
    }
}