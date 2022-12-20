package net.WAC.wurmunlimited.mods.windmill;

import com.wurmonline.server.FailedException;
import com.wurmonline.server.Items;
import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.ItemTypes;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.sounds.SoundPlayer;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Arrays;
import java.util.List;

public class flouraction implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer {
   public static short actionId;
   static ActionEntry actionEntry;

   public flouraction() {
      actionId = (short)ModActions.getNextActionId();
      actionEntry = ActionEntry.createEntry(actionId, "Create flour", "Creating flour", new int[0]);
      ModActions.registerAction(actionEntry);
   }

   public BehaviourProvider getBehaviourProvider() {
      return this;
   }

   public ActionPerformer getActionPerformer() {
      return this;
   }

   public short getActionId() {
      return actionId;
   }

   public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
      return this.getBehavioursFor(performer, target);
   }

   public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {
      return performer instanceof Player && target.getTemplateId() == windmill.windmilltemplateid ? Arrays.asList(actionEntry) : null;
   }

   public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
      return this.action(act, performer, target, action, counter);
   }

   public boolean action(Action act, Creature performer, Item target, short action, float counter) {
      String actionstring = act.getActionEntry().getActionString().toLowerCase();
      int actiontime = 1800;
      int tickTimes = windmill.windmillinitialtime;
      int absolutewindpower = (int)(10.0F * Math.abs(Server.getWeather().getWindPower()));
      if (!windmill.usewind) {
         absolutewindpower = 0;
      }

      tickTimes -= absolutewindpower;
      if (target.getTemplateId() == windmill.windmilltemplateid) {
         if (counter == 1.0F) {
            performer.sendActionControl(actionstring, true, actiontime);
            performer.getCommunicator().sendNormalServerMessage("You start to create flour.");
         }

         if (act.currentSecond() % tickTimes == 0) {
            if (performer.getStatus().getStamina() < 5000) {
               performer.getCommunicator().sendNormalServerMessage("You must rest.");
               return true;
            }

            performer.getStatus().modifyStamina(-2000.0F);
            Item[] currentItems = target.getAllItems(true);
            int consumeTally = 0;
            int produceTally = 0;
            Item[] var16 = currentItems;
            int var15 = currentItems.length;

            int cornflourcount;
            for(cornflourcount = 0; cornflourcount < var15; ++cornflourcount) {
               Item i = var16[cornflourcount];
               if (28 <= i.getTemplateId() && i.getTemplateId() <= 32) {
                  if (201 != i.getTemplateId() && 1220 != i.getTemplateId()) {
                     if (28 <= i.getTemplateId() && i.getTemplateId() <= 32) {
                        ++consumeTally;
                     }
                  } else {
                     ++produceTally;
                  }
               }
            }

            if (produceTally >= 1000) {
               return true;
            }

            int countcreated = 0;
            cornflourcount = 0;
            String createdname = "";
            String cornflourcreatedname = "";
            consumeTally = Math.min(windmill.windmillmaxnums, consumeTally);
            if (consumeTally != 0) {
               boolean playsound = false;
               Item[] var21 = currentItems;
               int var20 = currentItems.length;

               for(int var19 = 0; var19 < var20; ++var19) {
                  Item i = var21[var19];
                  if (consumeTally > 0) {
                     Item toInsert;
                     byte material;
                     if (28 <= i.getTemplateId() && i.getTemplateId() <= 31 && i.getWeightGrams() >= 300) {
                        try {
                           material = i.getMaterial();
                           if (material != 0) {
                              toInsert = ItemFactory.createItem(201, i.getQualityLevel(), material, i.getRarity(), (String)null);
                           } else {
                              toInsert = ItemFactory.createItem(201, i.getQualityLevel(), i.getRarity(), (String)null);
                           }

                           if (i.getRarity() != 0) {
                              i.setRarity((byte)0);
                           }

                           playsound = true;
                           target.insertItem(toInsert, true);
                           ++countcreated;
                           createdname = toInsert.getName();
                           i.setWeight(i.getWeightGrams() - 300, false);
                           if (100 > i.getWeightGrams()) {
                              Items.destroyItem(i.getWurmId());
                           }

                           --consumeTally;
                        } catch (NoSuchTemplateException | FailedException var25) {
                           var25.printStackTrace();
                        }
                     }

                     if (i.getTemplateId() == 32 && i.getWeightGrams() >= 100) {
                        try {
                           material = i.getMaterial();
                           if (material != 0) {
                              toInsert = ItemFactory.createItem(1220, i.getQualityLevel(), material, i.getRarity(), (String)null);
                           } else {
                              toInsert = ItemFactory.createItem(1220, i.getQualityLevel(), i.getRarity(), (String)null);
                           }

                           if (i.getRarity() != 0) {
                              i.setRarity((byte)0);
                           }

                           playsound = true;
                           target.insertItem(toInsert, true);
                           ++cornflourcount;
                           cornflourcreatedname = toInsert.getName();
                           i.setWeight(i.getWeightGrams() - 100, false);
                           if (100 > i.getWeightGrams()) {
                              Items.destroyItem(i.getWurmId());
                           }

                           --consumeTally;
                        } catch (NoSuchTemplateException | FailedException var24) {
                           var24.printStackTrace();
                        }
                     }
                  }
               }

               if (playsound) {
                  if (countcreated != 0) {
                     performer.getCommunicator().sendSafeServerMessage("Created " + countcreated + " " + createdname);
                  }

                  if (cornflourcount != 0) {
                     performer.getCommunicator().sendSafeServerMessage("Created " + cornflourcount + " " + cornflourcreatedname);
                  }

                  SoundPlayer.playSound("sound.grindstone", target, 0.0F);
                  return false;
               }
            }

            return true;
         }
      }

      return false;
   }
}
