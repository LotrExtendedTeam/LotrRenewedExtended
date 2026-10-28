package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager.ProtectionInstance;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedConquestTableBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedBookshelfBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedWeaponRackBlock;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerPermissions;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBoatItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedFangornRiverWeedItem;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;

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
import net.minecraft.network.play.server.SChangeBlockPacket;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
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

public class ExtendedBannerEventHandlers {
	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBlockBreak$Extended(BlockEvent.BreakEvent event) {
		if (!event.getWorld().isClientSide()) {
			BlockState state = event.getWorld().getBlockState(event.getPos());
			if(state.getBlock() instanceof ExtendedMEBannerBlock) {
				// Get active banner position
				BlockPos eventPos = state.getValue(ExtendedMEBannerBlock.HALF)==DoubleBlockHalf.UPPER ? event.getPos().below() : event.getPos();
				TileEntity banner = event.getWorld().getBlockEntity(eventPos);
				// Allow breaking banner if it can't self protect
				if(banner instanceof ExtendedMEBannerTileEntity && !((ExtendedMEBannerTileEntity)banner).isSelfProtecting()) {
					return;// Allow other handles to override this one
				}
				//Prevent breaking active banner block unless having banner permission
				if (ExtendedBannerProtectionManager.getProtectionStatus(event.getWorld(), eventPos, new ProtectionInstance.PlayerProtection(event.getPlayer(), ExtendedBannerPermissions.BANNER)).isNotAllowed()) {
					event.setCanceled(true);
				}
			}
			// General block breaking stuff
			else if (ExtendedBannerProtectionManager.getProtectionStatus(event.getWorld(), event.getPos(), new ProtectionInstance.PlayerProtection(event.getPlayer())).isNotAllowed()) {
				event.setCanceled(true);
			}
			//Prevent breaking blocks under banner unless having banner permission
			else if(event.getWorld().getBlockState(event.getPos().above()).getBlock() instanceof ExtendedMEBannerBlock && event.getWorld().getBlockState(event.getPos().above()).getValue(ExtendedMEBannerBlock.HALF)==DoubleBlockHalf.LOWER) {
				if (ExtendedBannerProtectionManager.getProtectionStatus(event.getWorld(), event.getPos().above(), new ProtectionInstance.PlayerProtection(event.getPlayer(), ExtendedBannerPermissions.BANNER)).isNotAllowed()) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBlucketFill$Extended(FillBucketEvent event) {
		World world = event.getWorld();
		if (!world.isClientSide() && event.getTarget().getType() == Type.BLOCK) {
			BlockPos pos = ((BlockRayTraceResult) event.getTarget()).getBlockPos();
			if (ExtendedBannerProtectionManager.getProtectionStatus(world, pos, new ProtectionInstance.PlayerProtection(event.getPlayer())).isNotAllowed()) {
				event.setCanceled(true);
				if (event.getPlayer() instanceof ServerPlayerEntity) {
					ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.getPlayer();
					BlockState actualState = world.getBlockState(pos);
					serverPlayer.connection.send(new SChangeBlockPacket(pos, actualState));
					serverPlayer.refreshContainer(serverPlayer.inventoryMenu);
				}
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void fallOnBlockEvent$Extended(BlockEvent.FarmlandTrampleEvent event){
		if (event.getFallDistance() < 0.5) // Permission checks only for at least 1-block high fall events
			return;
		if (!event.getWorld().isClientSide() && ExtendedBannerProtectionManager.getProtectionStatus(event.getWorld(), event.getPos(), new ProtectionInstance.AnyProtection()).isNotAllowed()) {
			event.setCanceled(true);
		}
	}
	// Already handled by NPC checkSpawnRules for NPCs, regular creatures should always spawn
	//@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleEntitySpawn$Extended(LivingSpawnEvent.CheckSpawn event) {
		IWorld world = event.getWorld();
		LivingEntity entity = event.getEntityLiving();
		if (!world.isClientSide() && entity instanceof MobEntity) {
			if (ExtendedBannerProtectionManager.getProtectionStatus(world, entity.blockPosition(), new ProtectionInstance.AnyProtection()).isNotAllowed()) {
				event.setResult(Result.DENY);
				return;
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBlockLeftInteract$Extended(PlayerInteractEvent.LeftClickBlock event) {
		World world = event.getWorld();
		if (!world.isClientSide()) {
			if (world.getBlockState(event.getPos()).getBlock() instanceof FireBlock) {
				if (ExtendedBannerProtectionManager.getProtectionStatus(world, event.getPos(), new ProtectionInstance.PlayerProtection(event.getPlayer())).isNotAllowed()) {
					event.setCanceled(true);
					return;
				}
			}
		}
	}
	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBlockRightClick$Extended(PlayerInteractEvent.RightClickItem event) {
		World world = event.getWorld();
		if(!world.isClientSide()) {
			ItemStack itemstack = event.getPlayer().getItemInHand(event.getHand());
			if(!itemstack.isEmpty() && (itemstack.getItem() instanceof BoatItem 
				|| itemstack.getItem() instanceof ExtendedBoatItem 
				|| itemstack.getItem() instanceof LOTRBoatItem
				|| itemstack.getItem() instanceof LilyPadItem
				|| itemstack.getItem() instanceof ExtendedFangornRiverWeedItem)){
				if (ExtendedBannerProtectionManager.getProtectionStatus(event.getWorld(), event.getPos(), new ProtectionInstance.PlayerProtection(event.getPlayer(), ExtendedBannerPermissions.EVERYTHING)).isNotAllowed()) {
					event.setCanceled(true);
					if(itemstack.getItem() instanceof LilyPadItem || itemstack.getItem() instanceof ExtendedFangornRiverWeedItem) {
						BlockRayTraceResult blockraytraceresult = Item.getPlayerPOVHitResult(world, event.getPlayer(), RayTraceContext.FluidMode.SOURCE_ONLY);
						if (blockraytraceresult.getType() == BlockRayTraceResult.Type.BLOCK) {
							BlockPos waterPos = blockraytraceresult.getBlockPos();
							BlockPos lilyPos = waterPos.above();
							// Ensure the position is within build height and the chunk is loaded
							if (World.isInWorldBounds(lilyPos) && world.hasChunkAt(lilyPos) && event.getPlayer() instanceof ServerPlayerEntity) {
								ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.getPlayer();
								BlockState actualStateWater = world.getBlockState(waterPos);
								serverPlayer.connection.send(new SChangeBlockPacket(waterPos, actualStateWater));
								BlockState actualState = world.getBlockState(lilyPos);
								serverPlayer.connection.send(new SChangeBlockPacket(lilyPos, actualState));
							}
						}
					}
					if (event.getPlayer() instanceof ServerPlayerEntity) {
						((ServerPlayerEntity)event.getPlayer()).refreshContainer(event.getPlayer().inventoryMenu);
					}
				}
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleBlockRightInteract$Extended(PlayerInteractEvent.RightClickBlock event) {
		if(!event.getWorld().isClientSide()) {
			World world = event.getWorld();
			BlockPos eventPos = event.getPos();
			BlockState bState = world.getBlockState(eventPos);
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
				} else if (world.getBlockEntity(eventPos) instanceof IInventory) {
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
			if (ExtendedBannerProtectionManager.getProtectionStatus(world, eventPos, new ProtectionInstance.PlayerProtection(event.getPlayer(), perm)).isNotAllowed()) {
				event.setCanceled(true);
				if (event.getPlayer() instanceof ServerPlayerEntity) {
					ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.getPlayer();
					BlockState state = world.getBlockState(eventPos);
					BlockPos above = eventPos.above();
					BlockState stateAbove = world.getBlockState(above);
					BlockState stateTop = world.getBlockState(above.above());
					serverPlayer.connection.send(new SChangeBlockPacket(eventPos, state));
					serverPlayer.connection.send(new SChangeBlockPacket(above, stateAbove));
					serverPlayer.connection.send(new SChangeBlockPacket(above.above(), stateTop));
					serverPlayer.refreshContainer(serverPlayer.inventoryMenu);
				}
				return;
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void handleDetonateEvent$Extended(ExplosionEvent.Detonate event) {
		Explosion explosion = event.getExplosion();
		World world = event.getWorld();
		LivingEntity exploder = event.getExplosion().getSourceMob();
		ProtectionInstance protectionInstance = null;
		if (!world.isClientSide) {
			if(exploder instanceof NPCEntity || exploder instanceof MobEntity) {
				protectionInstance = new ProtectionInstance.AnyProtection();
			}else if(exploder instanceof PlayerEntity) {
				protectionInstance = new ProtectionInstance.PlayerProtection((PlayerEntity) exploder);
			}else if(explosion.getExploder() instanceof TNTEntity) {
				protectionInstance = new ProtectionInstance.TNTProtection((TNTEntity) explosion.getExploder());
			}
		}
		if(protectionInstance==null) {
			protectionInstance = new ProtectionInstance.AnyProtection();
		}
		saveBlocks(world, explosion.getToBlow(), protectionInstance);
	}

	private static void saveBlocks(World world, List<BlockPos> blocksToBlow, ProtectionInstance instance) {
		blocksToBlow.removeIf(pos -> ExtendedBannerProtectionManager.getProtectionStatus(world, pos, instance).isNotAllowed());
	}
}