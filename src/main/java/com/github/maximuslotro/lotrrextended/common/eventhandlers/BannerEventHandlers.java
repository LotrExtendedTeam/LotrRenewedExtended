package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionDetector;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager.SingleProtectionInstance;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBookshelfBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedConquestTableBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedWeaponRackBlock;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerPermissions;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerProtectionLevels;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerTypes;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
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
		//ExtendedLog.info("BreakEvent");
		if (!event.getWorld().isClientSide() && ExtendedBannerProtectionManager.getProtectionStatus(event.getWorld(), event.getPos(), new SingleProtectionInstance.PlayerProtection(event.getPlayer()), true, "handleBannerBlockBreak").isNotAllowed()) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerBlucketFill(FillBucketEvent event) {
		World world = event.getWorld();
		if (!world.isClientSide() && event.getTarget().getType() == Type.BLOCK) {
			//ExtendedLog.info("FillBucketEvent");
			BlockPos pos = ((BlockRayTraceResult) event.getTarget()).getBlockPos();
			if (ExtendedBannerProtectionManager.getProtectionStatus(world, pos, new SingleProtectionInstance.PlayerProtection(event.getPlayer()), true, "handleBannerBlucketFill").isNotAllowed()) {
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
		if (!event.getWorld().isClientSide() && ExtendedBannerProtectionManager.getProtectionStatus(event.getWorld(), event.getPos(), new SingleProtectionInstance.EntityProtection(event.getEntity()), true, "fallOnBlockEvent").isNotAllowed()) {
			event.setCanceled(true);
		}
    }

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerEntitySpawn(LivingSpawnEvent.CheckSpawn event) {
		IWorld world = event.getWorld();
		LivingEntity entity = event.getEntityLiving();
		if (!world.isClientSide() && entity instanceof MobEntity) {
			if (ExtendedBannerProtectionManager.getProtectionStatus(world, entity.blockPosition(), new SingleProtectionInstance.AnyProtection(), false, "handleBannerEntitySpawn").isNotAllowed()) {
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
			if (ExtendedBannerProtectionManager.getProtectionStatus(world, entity.blockPosition(), new SingleProtectionInstance.PlayerProtection(event.getPlayer()), true, "handleBannerEntityInteractHangingEntity").isNotAllowed()) {
				event.setCanceled(true);
				return;
			}
		}
		if (!world.isClientSide() && (entity instanceof ArmorStandEntity)) {
			if (ExtendedBannerProtectionManager.getProtectionStatus(world, entity.blockPosition(), new SingleProtectionInstance.PlayerProtection(event.getPlayer(), ExtendedBannerPermissions.CONTAINERS), true, "handleBannerEntityInteractArmorStand").isNotAllowed()) {
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
			//ExtendedLog.info("AttackEntityEvent");
			if (ExtendedBannerProtectionManager.getProtectionStatus(level, entity.blockPosition(), new SingleProtectionInstance.PlayerProtection(event.getPlayer()), true, "handleBannerEntityAttacked").isNotAllowed()) {
				event.setCanceled(true);
				return;
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerBlockLeftInteract(PlayerInteractEvent.LeftClickBlock event) {
		World world = event.getWorld();
		if (!world.isClientSide()) {
			if (world.getBlockState(event.getPos()).getBlock() instanceof FireBlock) {
				if (ExtendedBannerProtectionManager.getProtectionStatus(world, event.getPos(), new SingleProtectionInstance.PlayerProtection(event.getPlayer()), false, "handleBannerBlockLeftInteract").isNotAllowed()) {
					event.setCanceled(true);
					return;
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
			if(!(event.getPlayer().isShiftKeyDown() && !event.getPlayer().getItemInHand(event.getHand()).isEmpty())) {
				if (block == Blocks.BOOKSHELF || block instanceof ExtendedBookshelfBlock || block instanceof LecternBlock) {
					perm = ExtendedBannerPermissions.KNOWLEDGE;
				} else if (block instanceof DoorBlock || block instanceof TrapDoorBlock || block instanceof FenceGateBlock || block instanceof GateBlock) {
					perm = ExtendedBannerPermissions.DOORS;
				} else if (block instanceof CraftingTableBlock || block instanceof FactionCraftingTableBlock || block instanceof AnvilBlock || block instanceof ExtendedConquestTableBlock) {
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
				return;
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBannerDetonateEvent(ExplosionEvent.Detonate event) {
		Explosion explosion = event.getExplosion();
		World world = event.getWorld();
		Entity exploder = event.getExplosion().getSourceMob();
		if (!world.isClientSide && exploder != null) {
			SingleProtectionInstance protectionInstance = null;
			if(exploder instanceof NPCEntity || exploder instanceof MobEntity) {
				protectionInstance = new SingleProtectionInstance.AnyProtection();
			}else if(exploder instanceof ProjectileEntity) {
				protectionInstance = new SingleProtectionInstance.LaunchedProtection((ProjectileEntity)exploder);
			}else if(exploder instanceof TNTEntity) {
				if(((TNTEntity)exploder).getOwner()!=null) {
					LivingEntity owner = ((TNTEntity)exploder).getOwner();
					if(owner instanceof NPCEntity || owner instanceof MobEntity) {
						protectionInstance = new SingleProtectionInstance.AnyProtection();
					}else if(owner instanceof PlayerEntity) {
						protectionInstance = new SingleProtectionInstance.PlayerProtection((PlayerEntity) owner);
					}else {
						protectionInstance = new SingleProtectionInstance.AnyProtection();
					}
				}else {
					protectionInstance = new SingleProtectionInstance.TNTProtection((TNTEntity)exploder);
				}
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

    public static ExtendedBannerProtectionLevels getProtectionLevelNear(PlayerEntity player) {
        for (ExtendedBannerProtectionLevels level : ExtendedBannerProtectionLevels.values()) {
            if (ExtendedBannerProtectionDetector.isPlayerNearAnyBannerProtection(player, level)) {
                return level;
            }
        }
        return null;
    }

    @SubscribeEvent
    public static void onPlayerTickCheckBannerType(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            PlayerEntity player = event.player;

            ExtendedBannerProtectionLevels level = BannerEventHandlers.getProtectionLevelNear(player);

            if (level != null) {
                ExtendedBannerTypes type = ExtendedBannerProtectionDetector.getNearbyBannerType(player, level);

                if (type != null) {
                    player.sendMessage(
                            new StringTextComponent("You are in the banner zone: " + type.name() + ", level: " + level.name()),
                            player.getUUID()
                    );
                } else {
                    player.sendMessage(
                            new StringTextComponent("You are in the banner zone (unknown type), level: " + level.name()),
                            player.getUUID()
                    );
                }
            }
        }
    }



	private static void saveBlocks(World world, List<BlockPos> blocksToBlow, SingleProtectionInstance instance) {
		blocksToBlow.removeIf(pos -> ExtendedBannerProtectionManager.getProtectionStatus(world, pos, instance, true, "handleBannerDetonateEvent").isNotAllowed());
	}
}