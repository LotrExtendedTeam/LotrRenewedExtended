package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.util.LOTREnchantUtils;
import lotr.common.util.representation.LOTREnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class LOTREnchantEventHandlers {

    @SubscribeEvent
    public static void addTooltipsForLOTREnchants(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<ITextComponent> entries = new ArrayList<>();
        for (LOTREnchantment enchant : LOTREnchantUtils.getEnchantList(stack)) {
            entries.add(
                    new TranslationTextComponent("enchant.lotrextended." + enchant.getKey().toLowerCase()).append(": ")
                            .append(new TranslationTextComponent("enchant.lotrextended." + enchant.getKey().toLowerCase() + ".desc"))
                            .withStyle(TextFormatting.GRAY));
        }

        if (!entries.isEmpty()) {
            if (event.getToolTip().size() >= 1) {
                event.getToolTip().addAll(1, entries);
            } else {
                event.getToolTip().addAll(0, entries);
            }
        }
    }

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
        List<LOTREnchantment> enchants = LOTREnchantUtils.getEnchantList(weapon);

        for (LOTREnchantment enchant : enchants) {
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
        if (!(event.getEntity() instanceof NPCEntity)) {
           return;
        }

        // only run on server
        if (event.getEntityLiving().level.isClientSide()) return;

        NPCEntity entity = (NPCEntity) event.getEntity();

        // check the weapon that was used
        LivingEntity source = null;
        if (event.getSource().getEntity() != null &&
                !(event.getSource().getEntity() instanceof LivingEntity)) {
           return;
        }
        source = (LivingEntity) event.getSource().getEntity();

        ItemStack weapon = source.getItemInHand(Hand.MAIN_HAND);
        if (source instanceof PlayerEntity) {
            LOTREnchantUtils.incrementBaneProgress(weapon, entity, (PlayerEntity) source);
        } else {
            LOTREnchantUtils.incrementBaneProgress(weapon, entity, null);
        }
    }
}
