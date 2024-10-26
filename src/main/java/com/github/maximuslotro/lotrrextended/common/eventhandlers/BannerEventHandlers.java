package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import java.util.List;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager.SingleProtectionInstance;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBookshelfBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedConquestTableBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedWeaponRackBlock;
import com.github.maximuslotro.lotrrextended.common.enums.BannerPermission;

import lotr.common.block.FactionCraftingTableBlock;
import lotr.common.block.GateBlock;
import lotr.common.block.KegBlock;
import lotr.common.block.PlateBlock;
import lotr.common.block.VesselDrinkBlock;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.item.minecart.TNTMinecartEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
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
		ExtendedLog.info("BreakEvent");
		if (!event.getWorld().isClientSide() && ExtendedBannerProtectionManager.isBannerProtected(event.getWorld(), event.getPos(), new SingleProtectionInstance.PlayerProtection(event.getPlayer()), true)) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerBlucketFill(FillBucketEvent event) {
		World world = event.getWorld();
		if (!world.isClientSide() && event.getTarget().getType() == Type.BLOCK) {
			ExtendedLog.info("FillBucketEvent");
			BlockPos pos = ((BlockRayTraceResult) event.getTarget()).getBlockPos();
			if (ExtendedBannerProtectionManager.isBannerProtected(world, pos, new SingleProtectionInstance.PlayerProtection(event.getPlayer()), true)) {
				event.setResult(Result.DENY);
				BlockState state = world.getBlockState(pos);
				world.sendBlockUpdated(pos, state, state, 2);
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
    public static void fallOnBlockEvent(BlockEvent.FarmlandTrampleEvent event){
        if (event.getFallDistance() < 0.5) // Permission checks only for at least 1-block high fall events
            return;
		if (!event.getWorld().isClientSide() && ExtendedBannerProtectionManager.isBannerProtected(event.getWorld(), event.getPos(), new SingleProtectionInstance.EntityProtection(event.getEntity()), true)) {
			event.setCanceled(true);
		}
    }

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerEntitySpawn(LivingSpawnEvent.CheckSpawn event) {
		IWorld world = event.getWorld();
		LivingEntity entity = event.getEntityLiving();
		if (!world.isClientSide() && entity instanceof MobEntity) {
			if (ExtendedBannerProtectionManager.isBannerProtected(world, entity.blockPosition(), new SingleProtectionInstance.AnyProtection(), false)) {
				event.setResult(Result.DENY);
				return;
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerEntityInteract(PlayerInteractEvent.EntityInteractSpecific event) {
		World world = event.getWorld();
		Entity entity = event.getEntity();
		if (!world.isClientSide() && (entity instanceof HangingEntity)) {
			ExtendedLog.info("PlayerInteractEvent.EntityInteractSpecific");
			if (ExtendedBannerProtectionManager.isBannerProtected(world, entity.blockPosition(), new SingleProtectionInstance.PlayerProtection(event.getPlayer()), true)) {
				event.setCanceled(true);
				return;
			}
		}
		if (!world.isClientSide() && (entity instanceof ArmorStandEntity)) {
			ExtendedLog.info("PlayerInteractEvent.EntityInteractSpecific");
			if (ExtendedBannerProtectionManager.isBannerProtected(world, entity.blockPosition(), new SingleProtectionInstance.PlayerProtection(event.getPlayer(), BannerPermission.CONTAINERS), true)) {
				event.setCanceled(true);
				return;
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerEntityAttacked(AttackEntityEvent event) {
		World level = event.getEntity().level;
		Entity entity = event.getEntity();
		if (!level.isClientSide() && (entity instanceof HangingEntity)) {
			ExtendedLog.info("AttackEntityEvent");
			if (ExtendedBannerProtectionManager.isBannerProtected(level, entity.blockPosition(), new SingleProtectionInstance.PlayerProtection(event.getPlayer()), true)) {
				event.setCanceled(true);
				return;
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerBlockLeftInteract(PlayerInteractEvent.LeftClickBlock event) {
		World world = event.getWorld();
		ExtendedLog.info("PlayerInteractEvent.LeftClickBlock");
		if (!world.isClientSide() && ExtendedBannerProtectionManager.isBannerProtected(world, event.getPos(), new SingleProtectionInstance.PlayerProtection(event.getPlayer()), true)) {
			if (world.getBlockState(event.getPos()).getBlock() instanceof FireBlock) {
				event.setCanceled(true);
				return;
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerBlockRightInteract(PlayerInteractEvent.RightClickBlock event) {
		World world = event.getWorld();
		BlockState bState = world.getBlockState(event.getPos());
		Block block = bState.getBlock();
		BannerPermission perm = BannerPermission.EVERYTHING;
		ExtendedLog.info("PlayerInteractEvent.RightClickBlock");
		if(!(!world.isClientSide() && event.getPlayer().isShiftKeyDown() && !event.getPlayer().getItemInHand(event.getHand()).isEmpty())) {
			if (block == Blocks.BOOKSHELF || block instanceof ExtendedBookshelfBlock || block instanceof LecternBlock) {
				perm = BannerPermission.KNOWLEDGE;
			} else if (block instanceof DoorBlock || block instanceof TrapDoorBlock || block instanceof FenceGateBlock || block instanceof GateBlock) {
				perm = BannerPermission.DOORS;
			} else if (block instanceof CraftingTableBlock || block instanceof FactionCraftingTableBlock || block instanceof AnvilBlock || block instanceof ExtendedConquestTableBlock) {
				perm = BannerPermission.CRAFTING;
			} else if (world.getBlockEntity(event.getPos()) instanceof IInventory) {
				if (block instanceof KegBlock || block instanceof CampfireBlock) {
					perm = BannerPermission.EDITABLES;
				} else {
					perm = BannerPermission.CONTAINERS;
				}
			} else if (block instanceof EnderChestBlock || block instanceof ExtendedWeaponRackBlock || block instanceof JukeboxBlock) {
				perm = BannerPermission.CONTAINERS;
			} else if (block instanceof PlateBlock || block instanceof CakeBlock || block instanceof VesselDrinkBlock) {
				perm = BannerPermission.EDITABLES;
			} else if (block instanceof BedBlock) {
				perm = BannerPermission.BEDS;
			} else if (block instanceof AbstractButtonBlock || block instanceof LeverBlock || block instanceof DaylightDetectorBlock) {
				perm = BannerPermission.FLICKABLES;
			}
		}
		if (!world.isClientSide() && ExtendedBannerProtectionManager.isBannerProtected(world, event.getPos(), new SingleProtectionInstance.PlayerProtection(event.getPlayer(), perm), true)) {
			event.setCanceled(true);
			if (block instanceof DoorBlock) {
				world.setBlockAndUpdate(event.getPos().below(), bState);
				world.setBlockAndUpdate(event.getPos(), bState);
				world.setBlockAndUpdate(event.getPos().above(), bState);
			}
			return;
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
		Explosion explosion = event.getExplosion();
		World world = event.getWorld();
		Entity exploder = event.getExplosion().getExploder();
		if (!world.isClientSide && exploder != null) {
			ExtendedLog.info("ExplosionEvent.Detonate");
			SingleProtectionInstance protectionInstance = null;
			if(exploder instanceof NPCEntity || exploder instanceof MobEntity) {
				protectionInstance = new SingleProtectionInstance.AnyProtection();
			}else if(exploder instanceof ProjectileEntity) {
				protectionInstance = new SingleProtectionInstance.LaunchedProtection((ProjectileEntity)exploder);
			}else if(exploder instanceof TNTEntity) {
				protectionInstance = new SingleProtectionInstance.TNTProtection((TNTEntity)exploder);
			}else if(exploder instanceof TNTMinecartEntity) {
				protectionInstance = new SingleProtectionInstance.AnyProtection();
			}
			if(protectionInstance!=null) {
				saveBlocks(world, explosion.getToBlow(), protectionInstance);
//				List<BlockPos> blocksToBlow = explosion.getToBlow();
//				List<BlockPos> blocksToSave = new ArrayList<>();
//				for(BlockPos pos : blocksToBlow) {
//					if(ExtendedBannerProtectionManager.isBannerProtected(world, pos, protectionInstance)) {
//						blocksToSave.add(pos);
//					}
//				}
//				blocksToBlow.removeAll(blocksToSave);
			}
		}
	}

	private static void saveBlocks(World world, List<BlockPos> blocksToBlow, SingleProtectionInstance instance) {
		blocksToBlow.removeIf(pos -> ExtendedBannerProtectionManager.isBannerProtected(world, pos, instance, true));
	}
}