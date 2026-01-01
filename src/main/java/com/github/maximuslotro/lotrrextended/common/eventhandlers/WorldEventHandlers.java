package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import java.util.List;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.ExtendedVanillaProtectionHandlers;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedPlacedBooksBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedrollBlock;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.entity.npc.ExtendedLoreEntity;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedDuckEntity;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedRespawnerEntity;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedPlaceableBookTypes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedPlacementUtils;

import lotr.common.entity.npc.ExtendedBarrowWightEntity;
import lotr.common.entity.npc.ExtendedCaptainEntity;
import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.ExtendedWanderingTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.NPCEntitySettings;
import lotr.common.entity.npc.data.NPCEntitySettingsManager;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRDimensions;
import net.minecraft.block.BellBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Handles various world-related events for custom entities and gameplay mechanics.
 * 
 * <p>This class listens to Forge events using {@link SubscribeEvent} and modifies
 * entity behaviors, player spawn logic, block-breaking advancements, trader/captain
 * death handling, and custom entity size adjustments.</p>
 */
public class WorldEventHandlers {

	@SubscribeEvent
	public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
		ExtendedVanillaProtectionHandlers.extendedWarnedPlayers.remove(event.getPlayer().getUUID());
		ExtendedVanillaProtectionHandlers.wasInProtection.remove(event.getPlayer().getUUID());
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END && event.player instanceof ServerPlayerEntity && ExtendedServerConfig.enableExtendedSpawnProtection.get()) {
			ServerPlayerEntity player = (ServerPlayerEntity) event.player;

			boolean isProtected = ExtendedVanillaProtectionHandlers.isUnderSpawnProtection(player.getLevel(), player.blockPosition(), player, false, true);
			UUID id = player.getUUID();
			boolean wasProtected = ExtendedVanillaProtectionHandlers.wasInProtection.getOrDefault(id, false);

			if (!wasProtected && isProtected) {
				player.sendMessage(new StringTextComponent("Entered spawn protection").withStyle(TextFormatting.RED), Util.NIL_UUID);
			} else if (wasProtected && !isProtected) {
				player.sendMessage(new StringTextComponent("Left spawn protection").withStyle(TextFormatting.GREEN), Util.NIL_UUID);
			}

			ExtendedVanillaProtectionHandlers.wasInProtection.put(id, isProtected);
		}
	}
	/**
	 * Prevents cloaked {@link ExtendedDunedainRangerEntity} from being targeted by NPCs.
	 *
	 * @param event the attack target event containing the potential target and attacker
	 */
	@SubscribeEvent
	public static void preventCloakedRangersFromBeingTargeted$Extended(LivingSetAttackTargetEvent event) {
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
	/**
	 * Cancels setting a spawn point if the bed used is an {@link ExtendedBedrollBlock}.
	 *
	 * @param event the spawn-setting event triggered when a player sets their spawn
	 */
	@SubscribeEvent
	public static void handleBedRollCancelSpawn$Extended(PlayerSetSpawnEvent event) {
		if(event.isForced() || event.getNewSpawn()==null) {
			return;
		}
		if(event.getPlayer().level.getBlockState(event.getNewSpawn())!=null) {
			if(event.getPlayer().level.getBlockState(event.getNewSpawn()).getBlock() instanceof ExtendedBedrollBlock) {
				event.setCanceled(true);
			}
		}
	}
	/**
	 * Triggers a custom advancement criterion when a block is broken.
	 *
	 * <p>This ensures that the player's block-breaking action is tracked for custom advancements.</p>
	 *
	 * @param event the block break event
	 */
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public static void handleBlockBreakAdvancement$Extended(BlockEvent.BreakEvent event) {
		if(event.getPlayer() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity)event.getPlayer();
			ExtendedCriteriaTriggers.PLAYER_BREAK_BLOCK_TRIGGER.trigger(player, event.getPos(), player.getItemInHand(player.getUsedItemHand()));
		}
	}
	/**
	 * Handles special logic when {@link ExtendedTraderEntity} or {@link ExtendedCaptainEntity} dies.
	 * <ul>
	 *   <li>If the entity is a wandering trader, escorts are removed and players are notified.</li>
	 *   <li>If the entity is persistent, it respawns via {@link ExtendedRespawnerEntity}.</li>
	 *   <li>Otherwise, the entity is allowed to die normally.</li>
	 * </ul>
	 *
	 * @param event the living entity death event
	 */
	@SubscribeEvent
	public static void handleCaptainTraderDeath$Extended(LivingDeathEvent event) {
		Entity sourceEntity = event.getSource().getEntity();
		if (sourceEntity instanceof NPCEntity && ((ExtendedNPCEntity)sourceEntity).getUnitInfo().isNpcActiveDuty()) {
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

			((ExtendedNPCEntity)sourceEntity).getUnitInfo().giveExperiencePoints(xpPoints);
			((ExtendedNPCEntity)sourceEntity).getUnitInfo().setMobKills(((ExtendedNPCEntity)sourceEntity).getUnitInfo().getMobKills() + 1);
			((ExtendedNPCEntity)sourceEntity).getUnitInfo().markDirty();
		}
		// If an NPC is a wandering trader, no respawns, even it is a captain
		if(event.getEntity() instanceof ExtendedTraderEntity && ((ExtendedNPCEntity) event.getEntity()).getTraderInfo().isTraderCurrentlyWandering()) {
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
		}
		if(event.getEntity() instanceof MobEntity) {
			MobEntity mob = (MobEntity) event.getEntity();
			if((mob.isPersistenceRequired() && (mob instanceof ExtendedTraderEntity || mob instanceof ExtendedCaptainEntity || mob instanceof ExtendedLoreEntity))
					|| (mob.getPersistentData().contains("ExtendedRespawn") && mob.getPersistentData().getBoolean("ExtendedRespawn")) 
					|| (mob.getPersistentData().contains("ExtendedRespawnOnDeath") && mob.getPersistentData().getBoolean("ExtendedRespawnOnDeath"))) {
				// Make entity respawner
				ExtendedRespawnerEntity respawnerEntity = new ExtendedRespawnerEntity(mob.level);
				//respawnerEntity.moveTo(npc.getX(), npc.getY()+(npc.getBbHeight()/2.0F), npc.getZ());
				respawnerEntity.moveTo(mob.getX(), mob.getY()+mob.getBbHeight(), mob.getZ());

				//Set entity Type
				respawnerEntity.setEntityType(mob.getType());

				//Save trader trades
				CompoundNBT compound =  new CompoundNBT();
				if(mob instanceof ExtendedTraderEntity) {
					((ExtendedTraderEntity) mob).getTraderData().writeToNbt(compound);
				}
				if(mob instanceof ExtendedCaptainEntity) {
					// IDK maybe captains have data to save in the future
				}
				respawnerEntity.setEntityData(compound);

				//Save Trader Home info
				if(mob.hasRestriction()){
					respawnerEntity.setHomeInfo(mob.getRestrictCenter(), (int)mob.getRestrictRadius());
				}
				mob.level.addFreshEntity(respawnerEntity);
			}
		}
	}
	/**
	 * Adjusts the size of {@link ExtendedBarrowWightEntity} on the client side.
	 *
	 * @param event the size-change event for an entity
	 */
	@SubscribeEvent
	public static void handleWightSizeChange$Extended(EntityEvent.Size event) {
		if(event.getEntity()!=null && event.getEntity().level!=null && event.getEntity().level.isClientSide() && event.getEntity() instanceof ExtendedBarrowWightEntity) {
			event.setNewSize(EntitySize.scalable(0.8f, 2.4f));
		}
	}
	/**
	 * Modifies entities as they spawn
	 *
	 * @param event the entity modifier event
	 */
	@SubscribeEvent
	public static void handleEntitySpawn$Extended(EntityJoinWorldEvent event) {
		if(LOTRDimensions.isModDimension(event.getWorld())) {
			if (!event.getWorld().isClientSide() && event.getEntity() instanceof ExperienceOrbEntity && !ExtendedServerConfig.enableVanillaXp.get()) {
				event.setCanceled(true);
				return;
			}
		}
	}

	@SubscribeEvent
	public static void handleBlockLeftInteract$Extended(PlayerInteractEvent.LeftClickBlock event) {
		if(LOTRDimensions.isModDimension(event.getWorld())) {
			if(event.getWorld().getBlockState(event.getPos()).getBlock() instanceof EnchantingTableBlock) {
				if(!ExtendedServerConfig.enableVanillaEnchanting.get()) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void handleBlockLeftInteract$Extended(PlayerInteractEvent.RightClickBlock event) {
		if(event.getPlayer().getMainHandItem().getItem().is(ExtendedTags.Items.PLACEABLE_BOOKS)) {
			ExtendedPlaceableBookTypes type = ExtendedPlaceableBookTypes.getByItem(event.getPlayer().getMainHandItem().getItem());
			if(type.notEmpty() && !(event.getWorld().getBlockState(event.getPos()).getBlock() instanceof ExtendedPlacedBooksBlock)) {
				BlockItemUseContext blockusecontext = new BlockItemUseContext(new ItemUseContext(event.getPlayer(), event.getHand(), event.getHitVec()));
				if(ExtendedPlacementUtils.place(ExtendedBlocks.PLACED_BOOKS.get(), new BlockItemUseContext(blockusecontext))) {
					event.getPlayer().swing(event.getHand(), true);
					event.setCanceled(true);
					return;
				}
			}
		}
		World level = event.getPlayer().level;
		BlockState clickedState = level.getBlockState(event.getPos());
		if (clickedState.is(LOTRBlocks.WICKER_FENCE.get())) {
			BlockPos checkPos = event.getPos().above();
			BlockState state = level.getBlockState(checkPos);
			int steps = 0;
			int maxSteps = ExtendedServerConfig.bellRingChainMaxLength.get();
			int worldTop = level.getMaxBuildHeight();  // get build limit
			while (state.getBlock() instanceof ChainBlock) {
				if (checkPos.getY() >= worldTop) {
					break;
				}
				if (steps >= maxSteps) {
					break;
				}
				checkPos = checkPos.above();
				state = level.getBlockState(checkPos);
				steps++;
			}
			if (state.getBlock() instanceof BellBlock) {
				BellBlock bell = (BellBlock) state.getBlock();
				bell.attemptToRing(level, checkPos, null);
				event.getPlayer().swing(event.getHand(), true);
				event.setCanceled(true);
				return;
			}
		}
		if (clickedState.is(Blocks.BOOKSHELF)) {
			if (!level.isClientSide) {
				PlayerEntity player = event.getPlayer();
				level.setBlock(event.getPos(), ExtendedBlocks.EXTENDED_BOOKSHELF.get().defaultBlockState(), 3);
				player.swing(event.getHand(), true);
			}
			event.setCanceled(true);
			return;
		}
	}

	@SubscribeEvent
	public static void handleEntityUpdate$Extended(LivingUpdateEvent pEvent) {
		if(pEvent.getEntity() instanceof ServerPlayerEntity && pEvent.getEntity().tickCount%20 ==0) {
			ServerPlayerEntity player = (ServerPlayerEntity)pEvent.getEntity();
			PlayerInventory inventory = player.inventory;
			for(int slotIndex=0; slotIndex < inventory.getContainerSize(); slotIndex++) {
				ItemStack stack = inventory.getItem(slotIndex);
				if(stack.isDamageableItem()) { 
					if(!ExtendedModifierUtils.hasRandomModifiers(stack)) {
						ExtendedModifierUtils.generateItemModifiersTagged(player.random, stack, false, false);
					}
				}
			}
			ItemStack carried = inventory.getCarried();
			if(carried.isDamageableItem() && !ExtendedModifierUtils.hasRandomModifiers(carried)) {
				ExtendedModifierUtils.generateItemModifiersTagged(player.random, carried, false, false);
				player.broadcastCarriedItem();
			}
		}
	}

	@SubscribeEvent
	public static void handleItemAttributeChanges$Extended(ItemAttributeModifierEvent pEvent) {
		ExtendedModifierUtils.processItemAttributes(pEvent);
	}

	@SubscribeEvent
	public static void onLivingAttacked$Extended(LivingAttackEvent event) {
		if(ExtendedServerConfig.enableStaffServer.get() && event.getSource().getEntity() instanceof ServerPlayerEntity && event.getEntityLiving() instanceof ServerPlayerEntity) {
			if(ExtendedVanillaProtectionHandlers.isUnderSpawnProtection(((ServerPlayerEntity) event.getEntityLiving()).getLevel(), 
					event.getEntityLiving().blockPosition(), ((ServerPlayerEntity)event.getSource().getEntity()), true, true)) {
				event.setCanceled(true);
			}
		}
	}
}