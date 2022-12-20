
package org.coldie.wurmunlimited.mods.VRmounts;

import com.wurmonline.server.creatures.CreatureTemplate;
import com.wurmonline.server.creatures.CreatureTemplateFactory;
import com.wurmonline.server.creatures.NoSuchCreatureTemplateException;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;

public class CorpseModifier {

    private static void setCorpseModel(int templateId, String model){
        try{
            CreatureTemplate template = CreatureTemplateFactory.getInstance().getTemplate(templateId);
            if(template != null){
                ReflectionUtil.setPrivateField(template, ReflectionUtil.getField(template.getClass(), "corpsename"), model);
            }
        } catch (NoSuchCreatureTemplateException | IllegalArgumentException | IllegalAccessException | ClassCastException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void setTemplateVariables() {
        try {
            // Set corpse models
            setCorpseModel(mount1.templateId, "horse.goldbuckskin.");
            //setCorpseModel(mount2.templateId, "horse.chestnut.");
        } catch ( IllegalArgumentException | ClassCastException e) {
            e.printStackTrace();
        }
    }

}
