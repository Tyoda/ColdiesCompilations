package net.WAC.wurmunlimited.mods.windmill;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.items.AdvancedCreationEntry;
import com.wurmonline.server.items.CreationCategories;
import com.wurmonline.server.items.CreationEntryCreator;
import com.wurmonline.server.items.CreationRequirement;
import com.wurmonline.server.items.ItemTemplateCreator;
import com.wurmonline.server.items.ItemTypes;
import java.io.IOException;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

public class windmillitems implements WurmServerMod, ItemTypes, MiscConstants {
   public windmillitems() {
      try {
         ItemTemplateCreator.createItemTemplate(windmill.sawmilltemplateid, "Sawmill", "Sawmills", "superb", "good", "ok", "poor", "A structure that creates wooden items.", new short[]{21, 44, 157, 1, 31, 178, 86, 52, 194, 195}, (short)1008, (short)1, 0, 9072000L, 5000, 5000, 5000, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.structure.sawmill.", 30.0F, 5000, (byte)14, 10000, true).setContainerSize(5000, 5000, 5000);
      } catch (IOException var6) {
      }

      AdvancedCreationEntry Sawmill = CreationEntryCreator.createAdvancedEntry(1005, 787, 860, windmill.sawmilltemplateid, true, true, 0.0F, true, true, CreationCategories.PRODUCTION);
      Sawmill.addRequirement(new CreationRequirement(1, 860, 10, true));
      Sawmill.addRequirement(new CreationRequirement(2, 773, 4, true));
      Sawmill.addRequirement(new CreationRequirement(3, 9, 4, true));
      Sawmill.addRequirement(new CreationRequirement(4, 217, 40, true));
      Sawmill.addRequirement(new CreationRequirement(5, 22, 60, true));
      Sawmill.addRequirement(new CreationRequirement(6, 24, 1, true));
      Sawmill.addRequirement(new CreationRequirement(7, 188, 8, true));
      Sawmill.addRequirement(new CreationRequirement(8, 131, 20, true));

      try {
         ItemTemplateCreator.createItemTemplate(windmill.windmilltemplateid, "Windmill", "Windmills", "superb", "good", "ok", "poor", "A structure powered by the wind.", new short[]{21, 44, 157, 1, 31, 178, 86, 52, 194, 195}, (short)247, (short)1, 0, 9072000L, 5000, 5000, 5000, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.structure.windmill.", 30.0F, 5000, (byte)14, 10000, true).setContainerSize(5000, 5000, 5000);
      } catch (IOException var5) {
      }

      AdvancedCreationEntry Windmill = CreationEntryCreator.createAdvancedEntry(1005, 23, 860, windmill.windmilltemplateid, true, true, 0.0F, true, true, CreationCategories.PRODUCTION);
      Windmill.addRequirement(new CreationRequirement(1, 860, 3, true));
      Windmill.addRequirement(new CreationRequirement(2, 23, 10, true));
      Windmill.addRequirement(new CreationRequirement(3, 9, 4, true));
      Windmill.addRequirement(new CreationRequirement(4, 217, 20, true));
      Windmill.addRequirement(new CreationRequirement(5, 22, 10, true));
      Windmill.addRequirement(new CreationRequirement(6, 188, 4, true));
      Windmill.addRequirement(new CreationRequirement(7, 132, 10, true));

      try {
         ItemTemplateCreator.createItemTemplate(windmill.masonsmilltemplateid, "Masonry Mill", "Masonry Mills", "superb", "good", "ok", "poor", "A structure with a diamond tipped blade for cutting rocks.", new short[]{25, 44, 157, 1, 31, 178, 86, 52, 194, 195}, (short)1008, (short)1, 0, 9072000L, 5000, 5000, 5000, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.structure.masonsmill.", 30.0F, 5000, (byte)15, 10000, true).setContainerSize(5000, 5000, 5000);
      } catch (IOException var4) {
      }

      AdvancedCreationEntry masonsmill = CreationEntryCreator.createAdvancedEntry(1013, 132, 492, windmill.masonsmilltemplateid, true, true, 0.0F, true, true, CreationCategories.PRODUCTION);
      masonsmill.addRequirement(new CreationRequirement(1, 860, 10, true));
      masonsmill.addRequirement(new CreationRequirement(2, 132, 40, true));
      masonsmill.addRequirement(new CreationRequirement(3, 9, 4, true));
      masonsmill.addRequirement(new CreationRequirement(4, 492, 10, true));
      masonsmill.addRequirement(new CreationRequirement(5, 380, 5, true));
      masonsmill.addRequirement(new CreationRequirement(6, 24, 1, true));
      masonsmill.addRequirement(new CreationRequirement(7, 188, 8, true));
      masonsmill.addRequirement(new CreationRequirement(8, 72, 10, true));
   }
}
