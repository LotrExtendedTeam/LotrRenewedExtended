package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.util.LOTREnchantUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedModifiers;

public class LOTREnchantEventHandlers {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource() == null || event.getSource().getEntity() == null) {
            return;
        }

        Entity source = event.getSource().getEntity();
        if (!(source instanceof LivingEntity)) {
            return;
        }

        ItemStack weapon = ((LivingEntity) source).getMainHandItem();
        LivingEntity target = event.getEntityLiving();
        List<ExtendedModifiers> enchants = LOTREnchantUtils.getEnchantList(weapon);

        for (ExtendedModifiers enchant : enchants) {
            if (LOTREnchantUtils.isBaneEffectiveAgainst(enchant, target)) {
                // only increase damage if > 0 (prevents damage going through shields)
                if (event.getAmount() > 0F) {
                    event.setAmount(event.getAmount() + 4.0F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
    	// Only run bane info when NPC dies
    	if (!(event.getEntityLiving() instanceof NPCEntity)) {
    		return;
    	}

    	// only run on server
    	if (event.getEntityLiving().level.isClientSide()) {
    		return;
    	}
    	// Check for invalid and null death sources (null instanceof LivingEntity)==false
    	if (!(event.getSource().getEntity() instanceof LivingEntity)) { // Reworked logic to catch NPE
    		return;
    	}
    	// check the weapon that was used
    	LivingEntity source = (LivingEntity) event.getSource().getEntity();
    	ItemStack weapon = source.getItemInHand(Hand.MAIN_HAND);
    	if (source instanceof PlayerEntity) {
    		LOTREnchantUtils.incrementBaneProgress(weapon, event.getEntity(), (PlayerEntity) source);
    	} else {
    		LOTREnchantUtils.incrementBaneProgress(weapon, event.getEntity(), null);
    	}
    }
}
