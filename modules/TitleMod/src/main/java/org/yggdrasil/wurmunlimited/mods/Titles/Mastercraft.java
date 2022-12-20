package org.yggdrasil.wurmunlimited.mods.Titles;

import java.util.logging.Logger;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;

public class Mastercraft {
	private static Logger logger = Logger.getLogger(Mastercraft.class.getName());
	public static void addNewTitles(){
        try {
            ExtendTitleEnum.builder("com.wurmonline.server.players.Titles$Title"); 
            TitleMod.titleid = TitleMod.titleid -1;
    		for(String title : TitleMod.titlelist)
    		{
    			TitleMod.titleid = TitleMod.titleid +1;
                ExtendTitleEnum.getSingletonInstance().addExtendEntry(title, TitleMod.titleid, title, title, -1, "NORMAL");

    		}        	

            // Characteristic Titles
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("MindLogic_Legendary", 1003, "Mentalist", "Mentalist", 100, "LEGENDARY");
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("MindSpeed_Legendary", 1007, "Mind Over Matter", "Mind Over Matter", 101, "LEGENDARY");
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("BodyStrength_Legendary", 1011, "Force of Nature", "Force of Nature", 102, "LEGENDARY");
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("BodyStamina_Legendary", 1015, "Unstoppable", "Unstoppable", 103, "LEGENDARY");
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("BodyControl_Legendary", 1019, "Manipulator", "Manipulator", 104, "LEGENDARY");
             ExtendTitleEnum.getSingletonInstance().addExtendEntry("SoulStrength_Legendary", 1023, "Prophet", "Prophet", 105, "LEGENDARY");
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("SoulDepth_Legendary", 1027, "Planewalker", "Planewalker", 106, "LEGENDARY");

            // Skill Titles (Full)
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("Staff_Legendary", 1103, "Sensei", "Sensei", 10090, "LEGENDARY");
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("Scythe_Legendary", 1107, "Reaper", "Reaper", 10047, "LEGENDARY");
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("Defensive_Legendary", 1111, "Unbreakable", "Unbreakable", 10054, "LEGENDARY");
           ExtendTitleEnum.getSingletonInstance().addExtendEntry("Aggressive_Legendary", 1115, "Warmonger", "Warmonger", 10053, "LEGENDARY");
            
            // Skill Titles (100)
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("Archery_Legendary", 1500, "Legendary Marksman", "Legendary Marksman", 1030, "LEGENDARY");
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("Body_Legendary", 1501, "Hercules", "Hercules", 1, "LEGENDARY");
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("Axes_Legendary", 1502, "Viking", "Viking", 1003, "LEGENDARY");
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("Baking_Legendary", 1503, "Patissier", "Patissier", 10039, "LEGENDARY");
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("Archaeology_Legendary", 1504, "Curator", "Curator", 10069, "LEGENDARY");
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("CarvingKnife_Legendary", 1505, "Woodsculptor", "Woodsculptor", 10007, "LEGENDARY");
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("Taming_Legendary", 1506, "King of the Jungle", "Queen of the Jungle", 10078, "LEGENDARY");
            ExtendTitleEnum.getSingletonInstance().addExtendEntry("Climbing_Legendary", 1507, "Moonwalker", "Moonwalker", 10073, "LEGENDARY");
            ExtendTitleEnum.getSingletonInstance().ExtendEnumEntries();

        } catch (BadBytecode | ClassNotFoundException | NotFoundException | CannotCompileException e) {
            logger.warning(e.getMessage());
        }
    }
}
