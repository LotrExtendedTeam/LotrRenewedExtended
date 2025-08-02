package com.github.maximuslotro.lotrrextended.common.chairs;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedChairEntity;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.VerticalOnlySlabBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HackyChairDatabase {
	protected Map<ResourceLocation, Map<BlockPos, ExtendedChairEntity>> chairs = new HashMap<>();

	public HackyChairDatabase() {
		MinecraftForge.EVENT_BUS.addListener(this::extendedPlayerChairClick);
		MinecraftForge.EVENT_BUS.addListener(this::extendedBreakChairEvent);
		MinecraftForge.EVENT_BUS.addListener(this::extendedPlayerChairLoggedOut);
		MinecraftForge.EVENT_BUS.addListener(this::extendedPlayerChairAttackedEvent);
	}

	public void handleServerStopping() {
		// TODO see if we need to do some cleansing of old data
	}

	@SubscribeEvent(receiveCanceled = true)
	public void extendedPlayerChairClick(RightClickBlock event) {
		World world = event.getWorld();
		if(world.isClientSide) {
			return;
		}
		BlockPos pos = event.getPos();
		if(!ExtendedServerConfig.enableChairSlabSitting.get() || (event.isCanceled() && !ExtendedServerConfig.enableChairProtectionOverride.get())) {
			ExtendedLog.infoHideable(ExtendedServerConfig.enableSpecialLogging.get(), "ChairInterraction["+pos.toString()+", Status Disabled: [Config Disabled, or Protection Override]");
			return;
		}
		// Check for valid player
		if(event.getPlayer().getMainHandItem().isEmpty() && !event.getPlayer().isShiftKeyDown() && (!event.getPlayer().isPassenger() || (event.getPlayer().getVehicle() instanceof ExtendedChairEntity && ExtendedServerConfig.enableChairSwapping.get()))) {
			// Check for valid block location
			BlockState state = world.getBlockState(pos);
			if(getChairByPosition(pos, world)==null && world.isEmptyBlock(pos.above()) && world.isEmptyBlock(pos.above().above())) {
				// Check for valid block type
				if(state.getBlock() instanceof StairsBlock && state.hasProperty(StairsBlock.HALF)) {
					// Check valid orientation
					if(state.getValue(StairsBlock.HALF)==Half.BOTTOM) {
						addNewChair(world, pos, event.getPlayer(), state.hasProperty(StairsBlock.FACING) ? state.getValue(StairsBlock.FACING) : null);
						return;
					}
				}
				// Players can't sit on vertical slabs
				if(state.getBlock() instanceof VerticalOnlySlabBlock) {
					ExtendedLog.infoHideable(ExtendedServerConfig.enableSpecialLogging.get(), "ChairInterraction["+pos.toString()+", Status Denied: [No Vertial Slab Sitting]");
					return;
				}
				if(state.getBlock() instanceof SlabBlock && state.hasProperty(SlabBlock.TYPE)) {
					// Check valid orientation
					if(state.getValue(SlabBlock.TYPE)==SlabType.BOTTOM) {
						// Check if vertical slab, players can't sit on vertical slabs
						if(state.getBlock() instanceof AxialSlabBlock && state.hasProperty(BlockStateProperties.AXIS)) {
							if(state.getValue(BlockStateProperties.AXIS)!=Axis.Y) {
								ExtendedLog.infoHideable(ExtendedServerConfig.enableSpecialLogging.get(), "ChairInterraction["+pos.toString()+", Status Denied: [No Vertial Slab Sitting]");
								return;
							}
						}
						addNewChair(world, pos, event.getPlayer(), null);
						return;
					}else {
						ExtendedLog.infoHideable(ExtendedServerConfig.enableSpecialLogging.get(), "ChairInterraction["+pos.toString()+", Status Denied: [No Upper/Vertical Slab Sitting]");
					}
				}else {
					ExtendedLog.infoHideable(ExtendedServerConfig.enableSpecialLogging.get(), "ChairInterraction["+pos.toString()+", Status Denied: [Not a Slab or Stair Block]");
				}
			}else {
				ExtendedLog.infoHideable(ExtendedServerConfig.enableSpecialLogging.get(), "ChairInterraction["+pos.toString()+", Status Denied: [Invalid Chair Location]");
			}
		}else {
			ExtendedLog.infoHideable(ExtendedServerConfig.enableSpecialLogging.get(), "ChairInterraction["+pos.toString()+", Status Denied: [Hand Not Empty, Not Shifting, or Is Passanger]");
		}
	}

	public void addNewChair(World world, BlockPos pos, PlayerEntity rider, Direction dir) {
		ExtendedChairEntity chair = new ExtendedChairEntity(world);
		chair.setPos(pos.getX()+0.5D, pos.getY()+0.28D, pos.getZ()+0.5D);
		addChairToDatabase(world, pos, chair);
		world.addFreshEntity(chair);
		if(rider.getVehicle() instanceof ExtendedChairEntity) {
			// Not needed here as ExtendedChairEntity.getDismountLocationForPassenger
			// Will trigger ExtendedChairEntity.remove
			// Which will call removeChairFromDatabase with the proper blockpos
			//removeChairFromDatabase(world, rider.blockPosition());
			rider.getVehicle().ejectPassengers();
		}
		if(dir!=null) {
			if(rider instanceof ServerPlayerEntity) {
				 ((ServerPlayerEntity)rider).connection.teleport(rider.getX(), rider.getY(), rider.getZ(), dir.getOpposite().toYRot(), rider.xRot);
			}
		}
		rider.startRiding(chair);
		ExtendedLog.infoHideable(ExtendedServerConfig.enableSpecialLogging.get(), "ChairInterraction["+pos.toString()+", Status Allowed: [layer Sitting]");
	}

	@SubscribeEvent
	public void extendedBreakChairEvent(BreakEvent event) {
		ExtendedChairEntity chair = getChairByPosition(event.getPos(), event.getPlayer().level);
		if(chair != null) {
			if(event.getWorld() instanceof World) {
				removeChairFromDatabase((World)event.getWorld(), event.getPos());
			}
			chair.ejectPassengers();
		}
	}

	@SubscribeEvent
	public void extendedPlayerChairLoggedOut(PlayerLoggedOutEvent event) {
		ExtendedChairEntity chair = getChairByPlayer(event.getPlayer());
		if(chair != null) {
			removeChairFromDatabase(event.getEntity().level, event.getEntity().blockPosition());
			chair.ejectPassengers();
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void extendedPlayerChairAttackedEvent(LivingAttackEvent event) {
		if(event.getEntity() instanceof PlayerEntity) {
			ExtendedChairEntity chair = getChairByPlayer((PlayerEntity)event.getEntity());
			if(chair != null) {
				removeChairFromDatabase(event.getEntity().level, event.getEntity().blockPosition());
				chair.ejectPassengers();
			}
		}
	}

	public void removeChairFromDatabase(World uniqueLevel, BlockPos pos) {
		if(chairs.containsKey(uniqueLevel.dimension().location())) {
			chairs.get(uniqueLevel.dimension().location()).remove(pos);
		}
	}

	public boolean addChairToDatabase(World uniqueLevel, BlockPos pos, ExtendedChairEntity entity) {
		if(!chairs.containsKey(uniqueLevel.dimension().location())) {
			chairs.put(uniqueLevel.dimension().location(), new HashMap<>());
		}
		chairs.get(uniqueLevel.dimension().location()).put(pos, entity);
		return true;
	}

	@Nullable
	public ExtendedChairEntity getChairByPlayer(PlayerEntity player) {
		if(player.isPassenger() && player.getVehicle() instanceof ExtendedChairEntity) {
			return (ExtendedChairEntity) player.getVehicle();
		}
		return null;
	}

	@Nullable
	public ExtendedChairEntity getChairByPosition(BlockPos pos, World uniqueLevel) {
		if(chairs.containsKey(uniqueLevel.dimension().location())) {
			if(chairs.get(uniqueLevel.dimension().location()).containsKey(pos)) {
				return chairs.get(uniqueLevel.dimension().location()).get(pos);
			}
		}
		return null;
	}
}
