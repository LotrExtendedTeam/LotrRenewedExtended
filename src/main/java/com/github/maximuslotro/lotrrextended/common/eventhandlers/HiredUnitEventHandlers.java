package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedDuckEntity;
import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedHiredUnitContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedHiredUnitEquipmentContainer;
import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.entity.npc.ExtendedHiredBreeGuard;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.NPCEntitySettings;
import lotr.common.entity.npc.data.NPCEntitySettingsManager;
import lotr.common.util.CoinUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class HiredUnitEventHandlers {

    @SubscribeEvent
    public static void playerCloseInventory(final PlayerContainerEvent.Close event) {
        if (!((event.getContainer() instanceof ExtendedHiredUnitContainer) ||
                (event.getContainer() instanceof ExtendedHiredUnitEquipmentContainer))) {
            return;
        }

        Entity entity;
        if (event.getContainer() instanceof ExtendedHiredUnitContainer) {
            entity = event.getPlayer().level.getEntity(((ExtendedHiredUnitContainer) event.getContainer()).getEntityId());
        } else {
            entity = event.getPlayer().level.getEntity(((ExtendedHiredUnitEquipmentContainer) event.getContainer()).getEntityId());
        }

        // make companion no longer stationary
        if (event.getContainer() instanceof ExtendedHiredUnitContainer) {
            if (entity instanceof ExtendedHiredBreeGuard) {
                ((ExtendedHiredBreeGuard) entity).setInventoryOpen(false);
            }
        } else if (event.getContainer() instanceof ExtendedHiredUnitEquipmentContainer) {
            if (entity instanceof ExtendedHiredBreeGuard) {
                ((ExtendedHiredBreeGuard) entity).setEquipmentOpen(false);
            }
        }
    }

    @SubscribeEvent
    public static void giveExperience(final LivingDeathEvent event) {
        Entity companion = event.getSource().getEntity();
        if (companion instanceof ExtendedHiredBreeGuard) {
            // base amount of xp off kill alignment bonus
            float xpPoints = 0;
            if (event.getEntity() instanceof NPCEntity) {
                NPCEntitySettings settings = NPCEntitySettingsManager.getEntityTypeSettings(event.getEntity());
                if (settings != null && settings.getKillAlignmentBonus() > 0) {
                    xpPoints = (int) settings.getKillAlignmentBonus();
                } else {
                    xpPoints = 1;
                }
            } else {
               if (event.getEntity() instanceof PigEntity ||
                       event.getEntity() instanceof ChickenEntity ||
                       event.getEntity() instanceof CowEntity ||
                       event.getEntity() instanceof ExtendedDuckEntity) {
                    xpPoints = 0.25f;
               }
            }

            ((ExtendedHirableEntity) companion).giveExperiencePoints(xpPoints);
            ((ExtendedHirableEntity) companion).setMobKills(((ExtendedHirableEntity) companion).getMobKills() + 1);
        }
    }

}
