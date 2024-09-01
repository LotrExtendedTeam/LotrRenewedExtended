package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedrollBlock;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedTraderRespawnEntity;

import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.item.LOTRSpawnEggItem;
import lotr.common.util.ExtendedTraderHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class WorldEventHandlers {

	@SubscribeEvent
	public static void onEntityRightClick(PlayerInteractEvent.EntityInteract event) {
		if (event.getPlayer().getItemInHand(event.getHand()).getItem() instanceof SpawnEggItem ||
				event.getPlayer().getItemInHand(event.getHand()).getItem() instanceof LOTRSpawnEggItem) {
			if (event.getTarget() instanceof ExtendedTraderEntity) {
				ExtendedTraderEntity trader = (ExtendedTraderEntity) event.getTarget();

				ExtendedTraderHelper.openTraderGui((NPCEntity) event.getTarget(), event.getPlayer(), trader);
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void preventCloakedRangersFromBeingTargeted(LivingSetAttackTargetEvent event) {
		if (event.getTarget() instanceof ExtendedDunedainRangerEntity) {
			ExtendedDunedainRangerEntity ranger = (ExtendedDunedainRangerEntity) event.getTarget();
			if (ranger.isCloaked()) {
				// set target to null
				if (event.getEntityLiving() instanceof NPCEntity) {
					NPCEntity npc = (NPCEntity) event.getEntityLiving();
					npc.setTarget(null);
				}
			}
		}
	}

	@SubscribeEvent
	public static void handleBedRollCancelSpawn(PlayerSetSpawnEvent event) {
		if(event.isForced() || event.getNewSpawn()==null) {
			return;
		}
		if(event.getPlayer().level.getBlockState(event.getNewSpawn())!=null) {
			if(event.getPlayer().level.getBlockState(event.getNewSpawn()).getBlock() instanceof ExtendedBedrollBlock) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.LOWEST)
	public static void handleBlockBreakAdvancement(BlockEvent.BreakEvent event) {
		if(event.getPlayer() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity)event.getPlayer();
			ExtendedCriteriaTriggers.PLAYER_BREAK_BLOCK_TRIGGER.trigger(player, event.getPos(), player.getItemInHand(player.getUsedItemHand()));
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void handleWanderingTraderDeath(LivingDeathEvent event) {
		if (!(event.getEntity() instanceof ExtendedTraderEntity)) {
			return;
		}
		if (!(event.getEntity() instanceof NPCEntity)) {
			return;
		}

		ExtendedTraderEntity trader = (ExtendedTraderEntity) event.getEntity();
		if(trader.isWanderingTrader()) {
			if (trader.getEscortUUIDs() == null || trader.getEscortUUIDs().isEmpty()) {
				return;
			}

			AxisAlignedBB initial = new AxisAlignedBB(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
					event.getEntity().getX() + 1, event.getEntity().getY() + 1, event.getEntity().getZ() + 1);
			List<NPCEntity> potentialEscorts = event.getEntity().level.getEntitiesOfClass(NPCEntity.class, initial.inflate(256));

			for (NPCEntity entity : potentialEscorts) {
				if (trader.getEscortUUIDs().contains(entity.getUUID())) {
					entity.remove();
				}
			}

			// notify all players that the wandering trader has died
			for (PlayerEntity player : event.getEntity().level.players()) {
				ITextComponent text = event.getSource().getLocalizedDeathMessage(event.getEntityLiving());
				player.sendMessage(text, Util.NIL_UUID);
			}
		}else {
			NPCEntity npc = (NPCEntity)event.getEntity();
			ExtendedTraderRespawnEntity respawnerEntity = new ExtendedTraderRespawnEntity(npc.level);
			respawnerEntity.moveTo(npc.getX(), npc.getY()+(npc.getBbHeight()/2.0F), npc.getZ());
			
			//Set entity Type
			respawnerEntity.setTraderType(npc.getType());
			//Save trader trades
			CompoundNBT compound =  new CompoundNBT();
			trader.saveAllTrades(compound);
			respawnerEntity.setTraderType(compound);
			//Save Trader Home info
			if(npc.hasRestriction()){
				respawnerEntity.setHomeInfo(npc.getRestrictCenter(), (int)npc.getRestrictRadius());
			}
			npc.level.addFreshEntity(respawnerEntity);
		}
	}
}
