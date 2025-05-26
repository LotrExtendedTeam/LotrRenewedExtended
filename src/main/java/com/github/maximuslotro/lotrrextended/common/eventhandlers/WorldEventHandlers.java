package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedrollBlock;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedTraderRespawnEntity;

import lotr.common.entity.npc.ExtendedBarrowWightEntity;
import lotr.common.entity.npc.ExtendedCaptainEntity;
import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.ExtendedWanderingTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.item.LOTRSpawnEggItem;
import lotr.common.util.ExtendedTraderHelper;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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

	@SubscribeEvent
	public static void handleCaptainTraderDeath(LivingDeathEvent event) {
		if (!(event.getEntity() instanceof ExtendedTraderEntity || event.getEntity() instanceof ExtendedCaptainEntity)) {
			return;
		}

		// If an NPC is a wandering trader, no respawns, even it is a captain
		if(event.getEntity() instanceof ExtendedTraderEntity && ((ExtendedTraderEntity) event.getEntity()).isTraderWandering()) {
			ExtendedWanderingTraderEntity trader = (ExtendedWanderingTraderEntity) event.getEntity();
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
			return;
		}else {
			NPCEntity npc = (NPCEntity)event.getEntity();
			// Check if entity has flag to respawn, also handle older versions where flag is not present
			if(npc.getPersistentData().contains("ExtendedRespawnOnDeath") && !npc.getPersistentData().getBoolean("ExtendedRespawnOnDeath")) {
				return; //Let entity die
			}
			// Update older entities to have the flag
			npc.getPersistentData().putBoolean("ExtendedRespawnOnDeath", true);
			// Make entity respawner
			ExtendedTraderRespawnEntity respawnerEntity = new ExtendedTraderRespawnEntity(npc.level);
			respawnerEntity.moveTo(npc.getX(), npc.getY()+(npc.getBbHeight()/2.0F), npc.getZ());

			//Set entity Type
			respawnerEntity.setEntityType(npc.getType());

			//Save trader trades
			CompoundNBT compound =  new CompoundNBT();
			if(event.getEntity() instanceof ExtendedTraderEntity) {
				((ExtendedTraderEntity) event.getEntity()).getTraderData().writeToNbt(compound);
			}
			if(event.getEntity() instanceof ExtendedCaptainEntity) {
				// IDK maybe captains have data to save in the future
			}
			respawnerEntity.setEntityData(compound);

			//Save Trader Home info
			if(npc.hasRestriction()){
				respawnerEntity.setHomeInfo(npc.getRestrictCenter(), (int)npc.getRestrictRadius());
			}
			npc.level.addFreshEntity(respawnerEntity);
		}
	}

	@SubscribeEvent
	public static void handleWightSizeChange(EntityEvent.Size event) {
		if(event.getEntity()!=null && event.getEntity().level!=null && event.getEntity().level.isClientSide() && event.getEntity() instanceof ExtendedBarrowWightEntity) {
			event.setNewSize(EntitySize.scalable(0.8f, 2.4f));
		}
	}
}
