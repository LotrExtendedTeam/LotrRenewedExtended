package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedrollBlock;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedTraderRespawnEntity;

import lotr.common.data.ExtendedLevelData;
import lotr.common.entity.npc.ExtendedBarrowWrightEntity;
import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.ExtendedEntities;
import lotr.common.item.LOTRSpawnEggItem;
import lotr.common.util.ExtendedTraderHelper;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
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
	public static void onContainerOpen(PlayerContainerEvent.Open event) {
		World world = event.getPlayer().level;
		if (!world.isClientSide) {
			ExtendedLevelData serverInstance = ExtendedLevelData.serverInstance();
			ServerWorld serverWorld = (ServerWorld) world;
			PlayerEntity player = event.getPlayer();
			if (serverInstance.isPlayerBarrowCursed(player)
					&& world.getBiome(player.blockPosition()).getRegistryName().toString().equals("lotr:barrow_downs")
					&& event.getContainer() instanceof ChestContainer) {
				ExtendedBarrowWrightEntity barrowWight = new ExtendedBarrowWrightEntity(ExtendedEntities.BARROW_WIGHT.get(), serverWorld);
				boolean spawned = false;
				int tries = 0;
				while (!spawned && tries < 49) {
					double x = player.getX() + (world.random.nextDouble() - world.random.nextDouble())*5 + 0.5D;
					double y = player.getY() + world.random.nextInt(3) - 1;
					double z = player.getZ() + (world.random.nextDouble() - world.random.nextDouble())*5 + 0.5D;
					if (world.getBlockState(new BlockPos((int)x, (int)y, (int)z)).is(Blocks.AIR) && world.getBlockState(new BlockPos((int)x, (int)y+1, (int)z)).is(Blocks.AIR)) {
						barrowWight.setPos(x, y, z);
						serverWorld.addFreshEntity(barrowWight);
						//maybe add a sound effect?
						serverInstance.removeCurse(player);
						spawned = true;
					}
					tries++;
				}
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
