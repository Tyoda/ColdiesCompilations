package net.WAC.wurmunlimited.mods.windmill;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemTypes;
import com.wurmonline.server.players.Player;
import java.util.Arrays;
import java.util.List;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

public class plankaction implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer {
   public static short actionId;
   static ActionEntry actionEntry;

   public plankaction() {
      actionId = (short)ModActions.getNextActionId();
      actionEntry = ActionEntry.createEntry(actionId, "Create planks", "Creating planks", new int[0]);
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
      return performer instanceof Player && target.getTemplateId() == windmill.sawmilltemplateid ? Arrays.asList(actionEntry) : null;
   }

   public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
      return this.action(act, performer, target, action, counter);
   }

   public boolean action(Action act, Creature performer, Item target, short action, float counter) {
      String actionstring = act.getActionEntry().getActionString().toLowerCase();
      int actiontime = 1800;
      int tickTimes = windmill.sawmillinitialtime;
      int absolutewindpower = (int)(10.0F * Math.abs(Server.getWeather().getWindPower()));
      if (!windmill.usewind) {
         absolutewindpower = 0;
      }

      tickTimes -= absolutewindpower;
      if (target.getTemplateId() == windmill.sawmilltemplateid) {
         if (counter == 1.0F) {
            performer.sendActionControl(actionstring, true, actiontime);
            performer.getCommunicator().sendNormalServerMessage("You start to create planks.");
         }

         if (act.currentSecond() % tickTimes == 0) {
            if (performer.getStatus().getStamina() < 5000) {
               performer.getCommunicator().sendNormalServerMessage("You must rest.");
               return true;
            }

            performer.getStatus().modifyStamina(-4000.0F);
            return windmill.sawmillItemCreate(performer, target, 22, 9, 2000, windmill.maxnums, 1000, "sound.work.carpentry.saw");
         }
      }

      return false;
   }
}
