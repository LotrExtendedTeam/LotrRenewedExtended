package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager.SingleProtectionInstance;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedConquestTableBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedBookshelfBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedWeaponRackBlock;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerPermissions;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBoatItem;

import lotr.common.block.FactionCraftingTableBlock;
import lotr.common.block.GateBlock;
import lotr.common.block.KegBlock;
import lotr.common.block.PlateBlock;
import lotr.common.block.VesselDrinkBlock;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.item.LOTRBoatItem;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CartographyTableBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.GrindstoneBlock;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.LoomBlock;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.LilyPadItem;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BannerEventHandlers {
	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerBlockBreak(BlockEvent.BreakEvent event) {
		//ExtendedLog.info("BreakEvent");
		if (!event.getWorld().isClientSide()) {
			if (ExtendedBannerProtectionManager.getProtectionStatus(event.getWorld(), event.getPos(), new SingleProtectionInstance.PlayerProtection(event.getPlayer())).isNotAllowed()) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerBlucketFill(FillBucketEvent event) {
		World world = event.getWorld();
		if (!world.isClientSide() && event.getTarget().getType() == Type.BLOCK) {
			//ExtendedLog.info("FillBucketEvent");
			BlockPos pos = ((BlockRayTraceResult) event.getTarget()).getBlockPos();
			if (ExtendedBannerProtectionManager.getProtectionStatus(world, pos, new SingleProtectionInstance.PlayerProtection(event.getPlayer())).isNotAllowed()) {
				event.setCanceled(true);
				BlockState state = world.getBlockState(pos);
				world.sendBlockUpdated(pos, state, state, 2);
				if (event.getPlayer() instanceof ServerPlayerEntity) {
					((ServerPlayerEntity)event.getPlayer()).refreshContainer(event.getPlayer().inventoryMenu);
				}
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void fallOnBlockEvent(BlockEvent.FarmlandTrampleEvent event){
		if (event.getFallDistance() < 0.5) // Permission checks only for at least 1-block high fall events
			return;
		if (!event.getWorld().isClientSide() && ExtendedBannerProtectionManager.getProtectionStatus(event.getWorld(), event.getPos(), new SingleProtectionInstance.EntityProtection(event.getEntity())).isNotAllowed()) {
			event.setCanceled(true);
		}
	}
	// Already handled by NPC checkSpawnRules for NPCs, regular creatures should always spawn
	//@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerEntitySpawn(LivingSpawnEvent.CheckSpawn event) {
		IWorld world = event.getWorld();
		LivingEntity entity = event.getEntityLiving();
		if (!world.isClientSide() && entity instanceof MobEntity) {
			if (ExtendedBannerProtectionManager.getProtectionStatus(world, entity.blockPosition(), new SingleProtectionInstance.AnyProtection()).isNotAllowed()) {
				event.setResult(Result.DENY);
				return;
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerBlockLeftInteract(PlayerInteractEvent.LeftClickBlock event) {
		World world = event.getWorld();
		if (!world.isClientSide()) {
			if (world.getBlockState(event.getPos()).getBlock() instanceof FireBlock) {
				if (ExtendedBannerProtectionManager.getProtectionStatus(world, event.getPos(), new SingleProtectionInstance.PlayerProtection(event.getPlayer())).isNotAllowed()) {
					event.setCanceled(true);
					return;
				}
			}
		}
	}
	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerBlockRightClick(PlayerInteractEvent.RightClickItem event) {
		World world = event.getWorld();
		if(!world.isClientSide()) {
			ItemStack itemstack = event.getPlayer().getItemInHand(event.getHand());
			if(!itemstack.isEmpty() && (itemstack.getItem() instanceof BoatItem 
				|| itemstack.getItem() instanceof ExtendedBoatItem 
				|| itemstack.getItem() instanceof LOTRBoatItem
				|| itemstack.getItem() instanceof LilyPadItem)){
				if (ExtendedBannerProtectionManager.getProtectionStatus(event.getWorld(), event.getPos(), new SingleProtectionInstance.PlayerProtection(event.getPlayer(), ExtendedBannerPermissions.EVERYTHING), true, "handleBannerBlockRightClick").isNotAllowed()) {
					event.setCanceled(true);
					if(itemstack.getItem() instanceof LilyPadItem) {
						BlockRayTraceResult blockraytraceresult = Item.getPlayerPOVHitResult(world, event.getPlayer(), RayTraceContext.FluidMode.SOURCE_ONLY);
						BlockPos lilyPos = blockraytraceresult.getBlockPos().above();
						BlockState state = world.getBlockState(lilyPos);
						world.sendBlockUpdated(lilyPos, state, state, 2);
					}
					if (event.getPlayer() instanceof ServerPlayerEntity) {
						((ServerPlayerEntity)event.getPlayer()).refreshContainer(event.getPlayer().inventoryMenu);
					}
				}
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerBlockRightInteract(PlayerInteractEvent.RightClickBlock event) {
		if(!event.getWorld().isClientSide()) {
			World world = event.getWorld();
			BlockState bState = world.getBlockState(event.getPos());
			Block block = bState.getBlock();
			ExtendedBannerPermissions perm = ExtendedBannerPermissions.EVERYTHING;
			if(!event.getPlayer().isShiftKeyDown() || event.getPlayer().getItemInHand(event.getHand()).isEmpty()) {
				if (block == Blocks.BOOKSHELF || block instanceof ExtendedBookshelfBlock || block instanceof LecternBlock) {
					perm = ExtendedBannerPermissions.KNOWLEDGE;
				} else if (block instanceof DoorBlock || block instanceof TrapDoorBlock || block instanceof FenceGateBlock || block instanceof GateBlock) {
					perm = ExtendedBannerPermissions.DOORS;
				} else if (block instanceof CraftingTableBlock || block instanceof FactionCraftingTableBlock || block instanceof AnvilBlock 
					|| block instanceof ExtendedConquestTableBlock || block instanceof GrindstoneBlock || block instanceof CartographyTableBlock
					|| block instanceof EnchantingTableBlock || block instanceof LoomBlock || block instanceof StonecutterBlock) {
					perm = ExtendedBannerPermissions.CRAFTING;
				} else if (world.getBlockEntity(event.getPos()) instanceof IInventory) {
					if (block instanceof KegBlock || block instanceof CampfireBlock) {
						perm = ExtendedBannerPermissions.EDITABLES;
					} else {
						perm = ExtendedBannerPermissions.CONTAINERS;
					}
				} else if (block instanceof EnderChestBlock || block instanceof ExtendedWeaponRackBlock || block instanceof JukeboxBlock) {
					perm = ExtendedBannerPermissions.CONTAINERS;
				} else if (block instanceof PlateBlock || block instanceof CakeBlock || block instanceof VesselDrinkBlock) {
					perm = ExtendedBannerPermissions.EDITABLES;
				} else if (block instanceof BedBlock) {
					perm = ExtendedBannerPermissions.BEDS;
				} else if (block instanceof AbstractButtonBlock || block instanceof LeverBlock || block instanceof DaylightDetectorBlock) {
					perm = ExtendedBannerPermissions.FLICKABLES;
				}else if (block instanceof ExtendedMEBannerBlock) {
					perm = ExtendedBannerPermissions.BANNER;
				}
			}
			if (ExtendedBannerProtectionManager.getProtectionStatus(world, event.getPos(), new SingleProtectionInstance.PlayerProtection(event.getPlayer(), perm), true, "handleBannerBlockRightInteract").isNotAllowed()) {
				event.setCanceled(true);
				if (block instanceof DoorBlock) {
					if(bState.getValue(DoorBlock.HALF)==DoubleBlockHalf.UPPER) {
						BlockState belowBlock = world.getBlockState(event.getPos().below());
						if(belowBlock.hasProperty(DoorBlock.HALF) && belowBlock.hasProperty(DoorBlock.OPEN)) {
							world.setBlockAndUpdate(event.getPos().below(), belowBlock.setValue(DoorBlock.OPEN, !bState.getValue(DoorBlock.OPEN)));
						}
						world.setBlockAndUpdate(event.getPos(), bState);
					}else {
						BlockState aboveBlock = world.getBlockState(event.getPos().above());
						if(aboveBlock.hasProperty(DoorBlock.HALF) && aboveBlock.hasProperty(DoorBlock.OPEN)) {
							world.setBlockAndUpdate(event.getPos().above(), aboveBlock.setValue(DoorBlock.OPEN, !bState.getValue(DoorBlock.OPEN)));
						}
						world.setBlockAndUpdate(event.getPos(), bState);
					}
				}
				BlockState state = world.getBlockState(event.getPos());
				world.sendBlockUpdated(event.getPos(), state, state, 2);
				if (event.getPlayer() instanceof ServerPlayerEntity) {
					((ServerPlayerEntity)event.getPlayer()).refreshContainer(event.getPlayer().inventoryMenu);
				}
				return;
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerDetonateEvent(ExplosionEvent.Detonate event) {
		Explosion explosion = event.getExplosion();
		World world = event.getWorld();
		LivingEntity exploder = event.getExplosion().getSourceMob();
		SingleProtectionInstance protectionInstance = null;
		if (!world.isClientSide) {
			if(exploder instanceof NPCEntity || exploder instanceof MobEntity) {
				protectionInstance = new SingleProtectionInstance.AnyProtection();
			}else if(exploder instanceof PlayerEntity) {
				protectionInstance = new SingleProtectionInstance.PlayerProtection((PlayerEntity) exploder);
			}else if(explosion.getExploder() instanceof TNTEntity) {
				protectionInstance = new SingleProtectionInstance.TNTProtection((TNTEntity) explosion.getExploder());
			}
		}
		if(protectionInstance==null) {
			protectionInstance = new SingleProtectionInstance.AnyProtection();
		}
		saveBlocks(world, explosion.getToBlow(), protectionInstance);

	}

	private static void saveBlocks(World world, List<BlockPos> blocksToBlow, SingleProtectionInstance instance) {
		blocksToBlow.removeIf(pos -> ExtendedBannerProtectionManager.getProtectionStatus(world, pos, instance).isNotAllowed());
	}
}