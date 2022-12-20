package org.coldie.wurmunlimited.mods.VRmounts;



import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.Features;
import com.wurmonline.server.behaviours.Vehicle;
import com.wurmonline.server.combat.ArmourTemplate;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.shared.constants.CreatureTypes;
import org.gotti.wurmunlimited.modsupport.CreatureTemplateBuilder;
import org.gotti.wurmunlimited.modsupport.creatures.EncounterBuilder;
import org.gotti.wurmunlimited.modsupport.creatures.ModCreature;
import org.gotti.wurmunlimited.modsupport.vehicles.ModVehicleBehaviour;
import org.gotti.wurmunlimited.modsupport.vehicles.VehicleFacade;

import static com.wurmonline.server.skills.SkillList.*;

public class mount2 implements ModCreature, CreatureTypes {
    public static int templateId;
    
    @Override
    public CreatureTemplateBuilder createCreateTemplateBuilder() {
        final int[] types = new int[] {0,2,3,9,12,14,27,30,32,38,41,42,43,62};
        final CreatureTemplateBuilder builder = new CreatureTemplateBuilder(
                "mod.creature.quadraped.horse.chestnut", "Mr Edd", "Insert even better stuffs here.",
                "model.creature.quadraped.horse.chestnut",
                types, (byte) 1,
                (short) 3,
                (byte) 0,
                (short) 180, (short) 50, (short) 250,
                "sound.death.horse", "sound.death.horse", "sound.combat.hit.horse", "sound.combat.hit.horse",
                1.0F, 1.0F, 2.5F, 1.5F, 2.0F, 0.0F, 1.5F, 100,
                new int[]{71, 306, 307, 308, 309},
                5,
                0,
                (byte)79
        );
        templateId = builder.getTemplateId();

        builder.skill(BODY_STRENGTH, VRmounts.BODY_STRENGTH);
        builder.skill(BODY_CONTROL, VRmounts.BODY_CONTROL);
        builder.skill(BODY_STAMINA, VRmounts.BODY_STAMINA);
        builder.skill(MIND_LOGICAL, VRmounts.MIND_LOGICAL);
        builder.skill(MIND_SPEED, VRmounts.MIND_SPEED);
        builder.skill(SOUL_STRENGTH, VRmounts.SOUL_STRENGTH);
        builder.skill(SOUL_DEPTH, VRmounts.SOUL_DEPTH);
        builder.skill(WEAPONLESS_FIGHTING, VRmounts.WEAPONLESS_FIGHTING);
        builder.skill(GROUP_FIGHTING, VRmounts.GROUP_FIGHTING);

        builder.baseCombatRating(VRmounts.baseCombatRating);
        builder.combatDamageType((byte) 2);
        builder.alignment(100.0f);
        builder.sizeModifier(VRmounts.sizeadjust, VRmounts.sizeadjust, VRmounts.sizeadjust);
        builder.maxAge(700);
        builder.kickDamString("kick");
        builder.headbuttDamString("bite");
        builder.armourType(ArmourTemplate.ARMOUR_TYPE_CLOTH);
       // builder.setCombatMoves( new int[] { 1,2,3 });
        builder.maxPopulationOfCreatures(VRmounts.maxPopulationOfCreatures);
        if (Features.Feature.CREATURE_MOVEMENT_CHANGES.isEnabled()) {
        	builder.vision((short)4);
        }
        return builder;
    }

    @Override
    public ModVehicleBehaviour getVehicleBehaviour() {
        return new ModVehicleBehaviour() {

            @Override
            public void setSettingsForVehicle(final Item item, final Vehicle vehicle) {
            }

            @Override
            public void setSettingsForVehicle(final Creature creature, final Vehicle v) {
                final VehicleFacade vehicle = this.wrap(v);
                vehicle.createPassengerSeats(0);
                vehicle.setSeatFightMod(0, 0.6F, 1.3F);
                vehicle.setSeatOffset(0, 0.0F, 0.0F, -0.01F);
                vehicle.setCreature(true);
                vehicle.setSkillNeeded(26.0F);
                vehicle.setName(creature.getName());
                vehicle.setMaxDepth(-0.7F);
                vehicle.setMaxHeightDiff(0.1f);
                vehicle.setMaxSpeed(VRmounts.mountedspeed);
                vehicle.setCommandType((byte) 3);
                vehicle.setCanHaveEquipment(true);
            }
        };
    }

    @Override
    public void addEncounters() {
        if (templateId == 0) {
            return;
        }

                new EncounterBuilder(Tiles.Tile.TILE_STEPPE.id)
                        .addCreatures(templateId, 1)
                        .build(1);

                new EncounterBuilder(Tiles.Tile.TILE_TUNDRA.id)
                        .addCreatures(templateId, 1)
                        .build(1);

    }
}