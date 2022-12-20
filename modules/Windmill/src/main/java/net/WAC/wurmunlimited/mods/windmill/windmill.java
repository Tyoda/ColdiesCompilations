package net.WAC.wurmunlimited.mods.windmill;

import com.wurmonline.server.FailedException;
import com.wurmonline.server.Items;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wurmonline.server.sounds.SoundPlayer;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.ItemTemplatesCreatedListener;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Properties;

public class windmill implements WurmServerMod, Configurable, ServerStartedListener, ItemTemplatesCreatedListener {
   static int windmilltemplateid = 5557;
   static int sawmilltemplateid = 5558;
   static int masonsmilltemplateid = 5559;
   static int maxnums = 20;
   static int windmillmaxnums = 20;
   static int sawmillinitialtime = 10;
   static int windmillinitialtime = 10;
   static int masonsmillinitialtime = 10;
   static boolean usewind = true;

   public windmill() {
   }

   public String getVersion() {
      return "v1.4";
   }

   public void configure(Properties properties) {
      usewind = Boolean.parseBoolean(properties.getProperty("usewind", Boolean.toString(usewind)));
      windmilltemplateid = Integer.parseInt(properties.getProperty("windmilltemplateid", Integer.toString(windmilltemplateid)));
      sawmilltemplateid = Integer.parseInt(properties.getProperty("sawmilltemplateid", Integer.toString(sawmilltemplateid)));
      masonsmilltemplateid = Integer.parseInt(properties.getProperty("masonsmilltemplateid", Integer.toString(masonsmilltemplateid)));
      maxnums = Integer.parseInt(properties.getProperty("maxnums", Integer.toString(maxnums)));
      windmillmaxnums = Integer.parseInt(properties.getProperty("windmillmaxnums", Integer.toString(windmillmaxnums)));
      sawmillinitialtime = Integer.parseInt(properties.getProperty("sawmillinitialtime", Integer.toString(sawmillinitialtime)));
      windmillinitialtime = Integer.parseInt(properties.getProperty("windmillinitialtime", Integer.toString(windmillinitialtime)));
      masonsmillinitialtime = Integer.parseInt(properties.getProperty("masonsmillinitialtime", Integer.toString(masonsmillinitialtime)));
   }

   public void onServerStarted() {
      ModActions.registerAction(new deckboardaction());
      ModActions.registerAction(new hullplankaction());
      ModActions.registerAction(new plankaction());
      ModActions.registerAction(new shafteaction());
      ModActions.registerAction(new woodbeamaction());
      ModActions.registerAction(new flouraction());
      ModActions.registerAction(new brickaction());
      ModActions.registerAction(new slabaction());
   }

   public void onItemTemplatesCreated() {
      new windmillitems();
   }

   static boolean sawmillItemCreate(Creature performer, Item item, int templateProduce, int templateConsume, int weightconsume, int maxNums, int maxItems, String SoundName) {
      Item[] currentItems = item.getAllItems(true);
      int produceTally = 0;
      int consumeTally = 0;
      Item[] var14 = currentItems;
      int var13 = currentItems.length;

      for(int var12 = 0; var12 < var13; ++var12) {
         Item i = var14[var12];
         if (templateProduce == i.getTemplateId()) {
            ++produceTally;
         } else if (templateConsume == i.getTemplateId()) {
            consumeTally += Math.abs(i.getFullWeight() / weightconsume);
         }
      }

      maxNums = Math.min(maxNums, consumeTally);
      if (produceTally >= maxItems) {
         return true;
      } else {
         int countcreated = 0;
         String createdname = "";
         if (templateConsume != 0) {
            consumeTally = Math.min(maxNums, consumeTally);
            boolean playsound = false;
            Item[] var17 = currentItems;
            int var16 = currentItems.length;

            for(int var15 = 0; var15 < var16; ++var15) {
               Item i = var17[var15];
               if (consumeTally > 0 && i.getTemplateId() == templateConsume && i.getWeightGrams() >= weightconsume) {
                  try {
                     byte material = i.getMaterial();
                     Item toInsert;
                     if (material != 0) {
                        toInsert = ItemFactory.createItem(templateProduce, i.getQualityLevel(), material, i.getRarity(), (String)null);
                     } else {
                        toInsert = ItemFactory.createItem(templateProduce, i.getQualityLevel(), i.getRarity(), (String)null);
                     }

                     if (i.getRarity() != 0) {
                        i.setRarity((byte)0);
                     }

                     playsound = true;
                     item.insertItem(toInsert, true);
                     ++countcreated;
                     createdname = toInsert.getName();
                     i.setWeight(i.getWeightGrams() - weightconsume, false);
                     if (100 >= i.getWeightGrams()) {
                        Items.destroyItem(i.getWurmId());
                     }

                     --consumeTally;
                  } catch (NoSuchTemplateException | FailedException var20) {
                     var20.printStackTrace();
                  }
               }
            }

            if (countcreated > 1) {
               createdname = createdname + "s";
            }

            if (playsound) {
               performer.getCommunicator().sendSafeServerMessage("Created " + countcreated + " " + createdname);
               SoundPlayer.playSound(SoundName, item, 0.0F);
               return false;
            }
         }

         return true;
      }
   }

   static boolean windmillItemCreate(Creature performer, Item item, int templateProduce, int templateConsume, int templateConsume2, int weightconsume, int weightconsume2, int maxNums, int maxItems, String SoundName) {
      Item[] currentItems = item.getAllItems(true);
      int produceTally = 0;
      int consumeTally = 0;
      int consumeTally2 = 0;
      Item[] var18 = currentItems;
      int var17 = currentItems.length;

      for(int var16 = 0; var16 < var17; ++var16) {
         Item i = var18[var16];
         if (templateProduce == i.getTemplateId()) {
            ++produceTally;
         } else if (templateConsume == i.getTemplateId()) {
            consumeTally += Math.abs(i.getFullWeight() / weightconsume);
         } else if (templateConsume2 == i.getTemplateId()) {
            consumeTally2 += Math.abs(i.getFullWeight() / weightconsume2);
         }
      }

      int totaltally = Math.min(consumeTally, consumeTally2);
      maxNums = Math.min(maxNums, totaltally);
      if (produceTally >= maxItems) {
         return true;
      } else {
         int countcreated = 0;
         String createdname = "";
         if (templateConsume != 0) {
            consumeTally = Math.min(maxNums, consumeTally);
            boolean playsound = false;
            Item[] var21 = currentItems;
            int var20 = currentItems.length;

            for(int var19 = 0; var19 < var20; ++var19) {
               Item i = var21[var19];
               if (consumeTally > 0 && i.getTemplateId() == templateConsume && i.getWeightGrams() >= weightconsume) {
                  try {
                     byte material = i.getMaterial();
                     Item toInsert;
                     if (material != 0) {
                        toInsert = ItemFactory.createItem(templateProduce, i.getQualityLevel(), material, i.getRarity(), (String)null);
                     } else {
                        toInsert = ItemFactory.createItem(templateProduce, i.getQualityLevel(), i.getRarity(), (String)null);
                     }

                     if (i.getRarity() != 0) {
                        i.setRarity((byte)0);
                     }

                     playsound = true;
                     item.insertItem(toInsert, true);
                     ++countcreated;
                     createdname = toInsert.getName();
                     i.setWeight(i.getWeightGrams() - weightconsume, false);
                     if (100 >= i.getWeightGrams()) {
                        Items.destroyItem(i.getWurmId());
                     }

                     --consumeTally;
                  } catch (NoSuchTemplateException | FailedException var24) {
                     var24.printStackTrace();
                  }
               }
            }

            if (countcreated > 1) {
               createdname = createdname + "s";
            }

            if (playsound) {
               performer.getCommunicator().sendSafeServerMessage("Created " + countcreated + " " + createdname);
               SoundPlayer.playSound(SoundName, item, 0.0F);
               return false;
            }
         }

         return true;
      }
   }

   static boolean masonsmillItemCreate(Creature performer, Item item, int createitem, int weightconsume, int maxNums, int maxItems, String SoundName) {
      Item[] currentItems = item.getAllItems(true);
      int consumeTally = maxNums;
      int countcreated = 0;
      String createdname = "";
      boolean playsound = false;
      Item[] var15 = currentItems;
      int var14 = currentItems.length;

      for(int var13 = 0; var13 < var14; ++var13) {
         Item i = var15[var13];
         int templateProduce = 0;
         if (consumeTally > 0 && i.getWeightGrams() >= weightconsume) {
            if (createitem == 0) {
               createdname = "brick";
               if (i.getTemplateId() == 770) {
                  templateProduce = 1123;
               }

               if (i.getTemplateId() == 785) {
                  templateProduce = 786;
               }

               if (i.getTemplateId() == 1116) {
                  templateProduce = 1121;
               }

               if (i.getTemplateId() == 146) {
                  templateProduce = 132;
               }
            } else if (createitem == 1) {
               createdname = "slab";
               if (i.getTemplateId() == 770) {
                  templateProduce = 771;
               }

               if (i.getTemplateId() == 785) {
                  templateProduce = 787;
               }

               if (i.getTemplateId() == 1116) {
                  templateProduce = 1124;
               }

               if (i.getTemplateId() == 146) {
                  templateProduce = 406;
               }
            }

            if (templateProduce != 0) {
               try {
                  byte material = i.getMaterial();
                  Item toInsert;
                  if (material != 0) {
                     toInsert = ItemFactory.createItem(templateProduce, i.getQualityLevel(), material, i.getRarity(), (String)null);
                  } else {
                     toInsert = ItemFactory.createItem(templateProduce, i.getQualityLevel(), i.getRarity(), (String)null);
                  }

                  if (i.getRarity() != 0) {
                     i.setRarity((byte)0);
                  }

                  playsound = true;
                  item.insertItem(toInsert, true);
                  ++countcreated;
                  i.setWeight(i.getWeightGrams() - weightconsume, false);
                  if (100 >= i.getWeightGrams()) {
                     Items.destroyItem(i.getWurmId());
                  }

                  --consumeTally;
               } catch (NoSuchTemplateException | FailedException var19) {
                  var19.printStackTrace();
               }
            }
         }
      }

      if (countcreated > 1) {
         createdname = createdname + "s";
      }

      if (playsound) {
         performer.getCommunicator().sendSafeServerMessage("Created " + countcreated + " " + createdname);
         SoundPlayer.playSound(SoundName, item, 0.0F);
         return false;
      } else {
         return true;
      }
   }
}
