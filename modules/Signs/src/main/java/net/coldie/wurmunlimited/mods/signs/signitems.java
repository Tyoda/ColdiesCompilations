package net.coldie.wurmunlimited.mods.signs;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.items.*;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import java.io.IOException;

public class signitems implements WurmServerMod, ItemTypes, MiscConstants {
   public signitems() {
      for(int x = 0; x < signsmain.signnames.length; ++x) {
         try {
            ItemTemplateCreator.createItemTemplate(signsmain.startid + x, "Sign - " + signsmain.signnames[x], "Signs", "superb", "good", "ok", "poor", "A decorative sign.", new short[]{22, 44, 157, 52, 51, 195}, (short)252, (short)1, 0, 9072000L, 20, 20, 200, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.decoration.signs." + signsmain.signnames[x] + ".", 30.0F, 3000, (byte)11, 10000, true);
         } catch (IOException var3) {
         }

         AdvancedCreationEntry teddy = CreationEntryCreator.createAdvancedEntry(10015, 188, 46, signsmain.startid + x, true, true, 0.0F, false, false, CreationCategories.DECORATION);
         teddy.addRequirement(new CreationRequirement(1, 218, 5, true));
         teddy.addRequirement(new CreationRequirement(2, 438, 1, true));
         teddy.addRequirement(new CreationRequirement(3, 131, 3, true));
      }

   }
}
