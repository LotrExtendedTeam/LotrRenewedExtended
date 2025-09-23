package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierType;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

public class ExtendedServerModifierEventHandlers {

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
		List<ExtendedModifier> enchants = ExtendedModifierUtils.getModifierList(weapon);

		for (ExtendedModifier enchant : enchants) {
			if (ExtendedModifierUtils.isBaneEffectiveAgainst(enchant, target)) {
				// only increase damage if > 0 (prevents damage going through shields)
				if (event.getAmount() > 0F) {
					event.setAmount(event.getAmount() + 4.0F);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event) {
		if (event.getEntityLiving() instanceof NPCEntity && !event.getEntityLiving().level.isClientSide()) {
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
				ExtendedModifierUtils.incrementBaneProgress(weapon, event.getEntity(), (PlayerEntity) source);
			} else {
				ExtendedModifierUtils.incrementBaneProgress(weapon, event.getEntity(), null);
			}
		}else if (event.getEntityLiving() instanceof PlayerEntity && !event.getEntityLiving().level.isClientSide()) {
			if (event.getSource().getEntity() instanceof LivingEntity) {
				if(!ExtendedModifierUtils.getModifierByTypeStack(((LivingEntity)event.getSource().getEntity()).getMainHandItem(), ExtendedModifierType.HEADHUNTING).isUnknown()) {
					ItemStack head = new ItemStack(Items.PLAYER_HEAD);
					head.getOrCreateTag().put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), ((PlayerEntity)event.getEntityLiving()).getGameProfile()));
					event.getEntityLiving().spawnAtLocation(head);
				}
			}
		}
	}
}
