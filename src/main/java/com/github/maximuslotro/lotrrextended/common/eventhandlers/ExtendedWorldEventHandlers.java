package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.ExtendedVanillaProtectionHandlers;
import com.github.maximuslotro.lotrextended.api.events.world.ExtendedFastTravelWaypointEvent;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedPlacedBooksBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedrollBlock;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;
import com.github.maximuslotro.lotrrextended.common.entity.npc.ExtendedLoreEntity;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedDuckEntity;
import com.github.maximuslotro.lotrrextended.common.entity.passive.ExtendedHorseBoarEntity;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedInvasionSpawnerEntity;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedRespawnerEntity;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedPlaceableBookTypes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedDaggerItem;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedBlockStateUtils;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedPlacementUtils;

import lotr.common.entity.npc.ExtendedBarrowWightEntity;
import lotr.common.entity.npc.ExtendedICaptainEntity;
import lotr.common.entity.npc.ExtendedDunedainRangerEntity;
import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.ExtendedIMerchantEntity;
import lotr.common.entity.npc.ExtendedIWanderingTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.NPCEntitySettings;
import lotr.common.entity.npc.data.NPCEntitySettingsManager;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRDimensions;
import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRMaterial;
import lotr.common.world.map.CustomWaypointStructureHandler;
import net.minecraft.block.BellBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

/**
 * Handles various world-related events for custom entities and gameplay mechanics.
 * 
 * <p>This class listens to Forge events using {@link SubscribeEvent} and modifies
 * entity behaviors, player spawn logic, block-breaking advancements, trader/captain
 * death handling, and custom entity size adjustments.</p>
 */
public class ExtendedWorldEventHandlers {
	private static final Map<ResourceLocation, Supplier<Block>> MUD_TRANSFORMS = new HashMap<>();

	static {
		MUD_TRANSFORMS.put(Blocks.DIRT.getRegistryName(), ExtendedBlocks.MUD);
		MUD_TRANSFORMS.put(ExtendedBlocks.DIRT_SLAB.get().getRegistryName(), ExtendedBlocks.MUD_SLAB);
		MUD_TRANSFORMS.put(Blocks.GRASS_BLOCK.getRegistryName(), ExtendedBlocks.MUD_GRASS);
		//MUD_TRANSFORMS.put(ExtendedBlocks.GRASS_BLOCK_SLAB.get().getRegistryName(), ExtendedBlocks.MUD_GRASS_SLAB);
		MUD_TRANSFORMS.put(Blocks.GRASS_PATH.getRegistryName(), ExtendedBlocks.MUD_PATH);
		MUD_TRANSFORMS.put(ExtendedBlocks.GRASS_PATH_SLAB.get().getRegistryName(), ExtendedBlocks.MUD_PATH_SLAB);
		MUD_TRANSFORMS.put(Blocks.FARMLAND.getRegistryName(), ExtendedBlocks.MUD_FARMLAND);
	}

	@SubscribeEvent
	public static void onServerStopping$Extended(FMLServerStoppingEvent event) {
		// Cache player list to prevent ConcurrentModificationException
		/* Still throws error, TODO rework code
		List<ServerPlayerEntity> players = event.getServer().getPlayerList().getPlayers();
		for (ServerPlayerEntity player : players) {
			player.connection.disconnect(new TranslationTextComponent("multiplayer.disconnect.server_shutdown"));
		}*/
	}

	@SubscribeEvent
	public static void onPlayerLogout$Extended(PlayerEvent.PlayerLoggedOutEvent event) {
		ExtendedVanillaProtectionHandlers.extendedWarnedPlayers.remove(event.getPlayer().getUUID());
		ExtendedVanillaProtectionHandlers.wasInProtection.remove(event.getPlayer().getUUID());
	}

	@SubscribeEvent
	public static void onPlayerTick$Extended(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END && event.player instanceof ServerPlayerEntity && ExtendedServerConfig.enableExtendedSpawnProtection.get()) {
			ServerPlayerEntity player = (ServerPlayerEntity) event.player;

			boolean isProtected = ExtendedVanillaProtectionHandlers.isUnderSpawnProtection(player.getLevel(), player.blockPosition(), player, false, false, false);
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
		if(ExtendedServerConfig.enableStaffServer.get() && event.getPlayer() instanceof ServerPlayerEntity) {
			if(ExtendedVanillaProtectionHandlers.isUnderSpawnProtection(((ServerPlayerEntity) event.getPlayer()).getLevel(), 
				event.getPos(), event.getPlayer(), true, true, false)) {
				event.setCanceled(true);
				return;
			}
		}
		if(event.getPlayer() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity)event.getPlayer();
			ExtendedCriteriaTriggers.PLAYER_BREAK_BLOCK_TRIGGER.trigger(player, event.getPos(), player.getItemInHand(player.getUsedItemHand()));
		}
	}

	@SubscribeEvent
	public static void handleEntityDeath$Extended(LivingDeathEvent event) {
		LivingEntity deadEntity = event.getEntityLiving();
		Entity killerEntity = event.getSource().getEntity();
		if(killerEntity!=null && !killerEntity.level.isClientSide()) {
			// Handle invasion kills
			if(deadEntity.getPersistentData().hasUUID("lotr:invasion_id")||deadEntity.getPersistentData().contains("ExtInvasion")) {
				//TODO remove old ID after a year or two from 1.11.0, by this point it should be out of use
				UUID invasionId = deadEntity.getPersistentData().contains("lotr:invasion_id") ? 
					event.getEntityLiving().getPersistentData().getUUID("lotr:invasion_id") : event.getEntityLiving().getPersistentData().getUUID("ExtInvasion");
				ExtendedLevelData.serverInstance().getInvasionData().decrementEnemiesLeft(invasionId);
				if (killerEntity instanceof ServerPlayerEntity) {
					ExtendedLevelData.serverInstance().getInvasionData().trackRecentPlayerKill(invasionId, killerEntity.getUUID());
				}
			}
			// Handle hired unit level ups
			if(killerEntity instanceof NPCEntity && ((ExtendedINPCEntity)killerEntity).getUnitInfo().isNpcActiveDuty()) {
				// base amount of xp off kill alignment bonus
				float xpPoints = 0;
				if(deadEntity instanceof NPCEntity) {
					NPCEntitySettings settings = NPCEntitySettingsManager.getEntityTypeSettings(deadEntity);
					xpPoints = (settings != null && settings.getKillAlignmentBonus() > 0) ? settings.getKillAlignmentBonus() : 1;
				} else {
					if(deadEntity instanceof PigEntity ||
						deadEntity instanceof ChickenEntity ||
						deadEntity instanceof CowEntity ||
						deadEntity instanceof SheepEntity ||
						deadEntity instanceof ExtendedHorseBoarEntity ||
						deadEntity instanceof ExtendedDuckEntity) {
						xpPoints = 0.25f;
					}
				}
				((ExtendedINPCEntity)killerEntity).getUnitInfo().giveExperiencePoints(xpPoints);
				((ExtendedINPCEntity)killerEntity).getUnitInfo().incrementMobKills();
				((ExtendedINPCEntity)killerEntity).getUnitInfo().markDirty();
			}
			// If an NPC is a wandering trader, no respawns, even it is a captain
			if(deadEntity instanceof ExtendedIMerchantEntity && ((ExtendedINPCEntity) deadEntity).getTraderInfo().isTraderCurrentlyWandering()) {
				ExtendedIWanderingTraderEntity trader = (ExtendedIWanderingTraderEntity) deadEntity;
				if (trader.getEscortUUIDs() == null || trader.getEscortUUIDs().isEmpty()) {
					return;
				}

				List<NPCEntity> escorts = deadEntity.level.getEntitiesOfClass(NPCEntity.class, 
					new AxisAlignedBB(deadEntity.blockPosition()).inflate(256), (e) -> trader.getEscortUUIDs().contains(e.getUUID()));
				escorts.forEach(NPCEntity::remove);

				// notify all players that the wandering trader has died
				for (PlayerEntity player : deadEntity.level.players()) {
					ITextComponent text = event.getSource().getLocalizedDeathMessage(event.getEntityLiving());
					player.sendMessage(text, Util.NIL_UUID);
				}
			}else if(deadEntity instanceof MobEntity) {
				// If not wandering, determine if it should respawn
				MobEntity mob = (MobEntity) deadEntity;
				if((mob.isPersistenceRequired() && (mob instanceof ExtendedIMerchantEntity || mob instanceof ExtendedICaptainEntity || mob instanceof ExtendedLoreEntity))
					|| (mob.getPersistentData().contains("ExtendedRespawn") && mob.getPersistentData().getBoolean("ExtendedRespawn"))) {
					// Make entity respawner
					ExtendedRespawnerEntity respawnerEntity = new ExtendedRespawnerEntity(mob.level);
					//respawnerEntity.moveTo(npc.getX(), npc.getY()+(npc.getBbHeight()/2.0F), npc.getZ());
					respawnerEntity.moveTo(mob.getX(), mob.getY()+mob.getBbHeight(), mob.getZ());
					//Set entity Type
					respawnerEntity.setEntityType(mob.getType());
					//Save trader trades
					CompoundNBT compound =  new CompoundNBT();
					if(mob instanceof ExtendedIMerchantEntity) {
						((ExtendedIMerchantEntity) mob).getTraderData().writeToNbt(compound);
					}
					if(mob instanceof ExtendedICaptainEntity) {
						// IDK maybe captains have data to save in the future
					}
					respawnerEntity.setEntityData(compound);
					//Save Trader Home info
					if(mob.hasRestriction()){
						respawnerEntity.setHomeInfo(mob.getRestrictCenter(), (int)mob.getRestrictRadius());
					}
					// Should be server world at this code since can only execute on server
					((ServerWorld)mob.level).addFreshEntityWithPassengers(respawnerEntity);
				}
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
	public static void onEntityLeave$Extended(EntityLeaveWorldEvent event) {
		if (!event.getWorld().isClientSide() && event.getEntity() instanceof ExtendedInvasionSpawnerEntity && ExtendedLevelData.serverInstance().getInvasionData().hasInvasion(event.getEntity().getUUID())) {
			// Un-register invasion when spawner is unloaded or deleted.
			ExtendedLevelData.serverInstance().getInvasionData().unregisterInvasion(event.getEntity().getUUID());
		}
	}

	@SubscribeEvent
	public static void handleBlockLeftInteract$Extended(PlayerInteractEvent.RightClickBlock event) {
		World level = event.getWorld();
		BlockState clickedState = level.getBlockState(event.getPos());
		PlayerEntity player = event.getPlayer();
		if(event.getItemStack().getItem().is(ExtendedTags.Items.PLACEABLE_BOOKS)) {
			if(event.getItemStack().getItem().equals(LOTRItems.RED_BOOK.get()) && CustomWaypointStructureHandler.INSTANCE.isProtectedByWaypointStructure(level, event.getPos(), player)){
				return; // Don't try placing red books in waypoint protection (regular waypoint protection has a red book bypass for editing)
			}
			ExtendedPlaceableBookTypes type = ExtendedPlaceableBookTypes.getByItem(event.getItemStack().getItem());
			if(type.notEmpty() && !(clickedState.getBlock() instanceof ExtendedPlacedBooksBlock)) {
				BlockItemUseContext blockusecontext = new BlockItemUseContext(new ItemUseContext(player, event.getHand(), event.getHitVec()));
				if(ExtendedPlacementUtils.place(ExtendedBlocks.PLACED_BOOKS.get(), new BlockItemUseContext(blockusecontext))) {
					player.swing(event.getHand(), true);
					event.setCanceled(true);
					return;
				}
			}
		}
		Block clickedBlock = clickedState.getBlock();
		if(PotionUtils.getPotion(event.getItemStack())==Potions.WATER && MUD_TRANSFORMS.containsKey(clickedBlock.getRegistryName())) {
			Block newBlock = MUD_TRANSFORMS.get(clickedBlock.getRegistryName()).get();
			BlockState newBlockState = newBlock.defaultBlockState();
			if(newBlock instanceof FarmlandBlock) {
				newBlockState.setValue(FarmlandBlock.MOISTURE, 7);
			}
			if(newBlock instanceof SlabBlock) {
				newBlockState = ExtendedBlockStateUtils.copyBlockStateToBlockStateOverwriteSlab(newBlockState, clickedState);
			}
			level.setBlock(event.getPos(), newBlockState, 3);
			player.swing(event.getHand(), true);
			ItemStack stack = event.getItemStack();
			if (!player.abilities.instabuild) {
				ItemStack emptyBottle = new ItemStack(Items.GLASS_BOTTLE);
				stack.shrink(1);
				if (stack.isEmpty()) {
					player.setItemInHand(event.getHand(), emptyBottle);
				} else if (!player.inventory.add(emptyBottle)) {
					player.drop(emptyBottle, false);
				} else if (player instanceof ServerPlayerEntity) {
					((ServerPlayerEntity)player).refreshContainer(player.inventoryMenu);
				}
			}
			level.playSound((PlayerEntity)null, event.getPos(), SoundEvents.BOTTLE_EMPTY, SoundCategory.BLOCKS, 0.5F, 7.0F);
			return;
		}
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
				player.swing(event.getHand(), true);
				event.setCanceled(true);
				return;
			}
		}
		if (clickedState.is(Blocks.BOOKSHELF)) {
			if (!level.isClientSide) {
				level.setBlock(event.getPos(), ExtendedBlocks.EXTENDED_BOOKSHELF.get().defaultBlockState(), 3);
				player.swing(event.getHand(), true);
			}
			event.setCanceled(true);
			return;
		}
		if(clickedState.is(Blocks.ENCHANTING_TABLE)) {
			if(!ExtendedServerConfig.enableVanillaEnchanting.get()) {
				event.setCanceled(true);
				return;
			}
		}
		if(clickedState.is(Blocks.CAULDRON) && clickedState.getValue(CauldronBlock.LEVEL)>0) {
			if(ExtendedDaggerItem.isPoisoned(event.getItemStack())) {
				if (!level.isClientSide) {
					((CauldronBlock) clickedState.getBlock()).setWaterLevel(level, event.getPos(), clickedState, clickedState.getValue(CauldronBlock.LEVEL) - 1);
					player.swing(event.getHand(), true);
				}
				ExtendedDaggerItem.removePoison(event.getItemStack());
				return;
			}
		}
	}

	@SubscribeEvent
	public static void handleExtendedFastTravelWaypointEventPost$Extended(ExtendedFastTravelWaypointEvent.Post pEvent) {
		ExtendedCriteriaTriggers.ABSTRACT_STRING_TRIGGER.trigger(pEvent.getPlayer(), "fastTraveled");
	}

	@SubscribeEvent
	public static void onLivingAttacked$Extended(LivingAttackEvent event) {
		if(ExtendedServerConfig.enableStaffServer.get() && event.getSource().getEntity() instanceof ServerPlayerEntity && event.getEntityLiving() instanceof ServerPlayerEntity) {
			if(ExtendedVanillaProtectionHandlers.isUnderSpawnProtection(((ServerPlayerEntity) event.getEntityLiving()).getLevel(), 
				event.getEntityLiving().blockPosition(), ((ServerPlayerEntity)event.getSource().getEntity()), true, true, false)) {
				event.setCanceled(true);
				return;
			}
		}
		if(event.getSource().isProjectile() && event.getSource() instanceof IndirectEntityDamageSource) {
			if(event.getSource().getDirectEntity() instanceof AbstractArrowEntity || event.getSource().getDirectEntity() instanceof FireworkRocketEntity) {
				boolean shouldNegateArrowDamage = true;
				for(ItemStack stack : event.getEntityLiving().getArmorSlots()) {
					if(!(stack.getItem() instanceof ArmorItem) || ((ArmorItem) stack.getItem()).getMaterial()!=LOTRMaterial.valueOf("GALVORN").asArmor()) {
						shouldNegateArrowDamage = false;
						break;
					}
				}
				// In lore arrows couldn't hurt them, only barrow blades?
				if(event.getEntityLiving() instanceof ExtendedBarrowWightEntity) {
					shouldNegateArrowDamage = true;
				}
				if(shouldNegateArrowDamage) {
					event.setCanceled(true);
					// Ensure armor is still damaged
					if(event.getEntityLiving() instanceof PlayerEntity && !event.getEntityLiving().level.isClientSide()) {
						((PlayerEntity)event.getEntityLiving()).inventory.hurtArmor(event.getSource(), event.getAmount());
					}
					return;
				}
			}
			// Players or NPCs riding
			if(event.getEntityLiving().isVehicle() && event.getSource().getDirectEntity() instanceof AbstractArrowEntity) {
				if(event.getSource().getEntity() instanceof PlayerEntity || event.getSource().getEntity() instanceof NPCEntity) {
					if(event.getEntityLiving().hasPassenger(event.getSource().getEntity())) {
						event.setCanceled(true);
						return;
					}
				}
			}
		}
	}
}