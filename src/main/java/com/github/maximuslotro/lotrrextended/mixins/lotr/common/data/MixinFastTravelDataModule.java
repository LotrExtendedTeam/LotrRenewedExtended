package com.github.maximuslotro.lotrrextended.mixins.lotr.common.data;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.LOTRLog;
import lotr.common.data.FastTravelDataModule;
import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.event.LOTRFastTravelWaypointEvent;
import lotr.common.stat.LOTRStats;
import lotr.common.util.UsernameHelper;
import lotr.common.world.map.Waypoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;

@Mixin(FastTravelDataModule.class)
public abstract class MixinFastTravelDataModule {

//	@Inject(at = @At(value = "HEAD"), method="fastTravelTo", remap = false)
//	private void fastTravelTo(ServerPlayerEntity player, Waypoint waypoint, CallbackInfo info) {
//		ServerWorld world = player.getLevel();
//		BlockPos orig = new BlockPos(player.getX(), player.getY(), player.getZ());
//		BlockPos travelPos = waypoint.getTravelPosition(world, player);
//		if (travelPos != null) {
//			LOTRFastTravelWaypointEvent event = new LOTRFastTravelWaypointEvent(player, world, orig, travelPos);
//			MinecraftForge.EVENT_BUS.post(event);
//		}
//	}

	@Shadow(remap=false)
	private MobEntity fastTravelEntity(ServerWorld world, MobEntity entity, double x, double y, double z) {return null;}

	@Shadow(remap=false)
	private void setUUIDToMount(UUID uuid) {}

	@Shadow(remap=false)
	private void sendFTScreenPacket(ServerPlayerEntity player, Waypoint waypoint, int startX, int startZ) {}

	@Shadow(remap=false)
	public void setTimeSinceFTWithUpdate(int i) {}

	@Shadow(remap=false)
	public void incrementWPUseCount(Waypoint waypoint) {}

	/**
	 * @author maximuslotro
	 * @reason because injecting into a code tree is hard
	 * */
	@Overwrite(remap=false)
	private void fastTravelTo(ServerPlayerEntity player, Waypoint waypoint) {
		ServerWorld world = player.getLevel();
		BlockPos travelPos = waypoint.getTravelPosition(world, player);
		if (travelPos == null) {
			LOTRLog.warn("Player %s fast travel to %s was cancelled because the waypoint returned a null travel position.", UsernameHelper.getRawUsername(player), waypoint.getRawName());
			return;
		}
		double startXF = player.getX();
		double startYF = player.getY();
		double startZF = player.getZ();
		int startX = MathHelper.floor(startXF);
		int startZ = MathHelper.floor(startZF);
		// Extended Start
		BlockPos origin = new BlockPos(player.getX(), player.getY(), player.getZ());
		MinecraftForge.EVENT_BUS.post(new LOTRFastTravelWaypointEvent.Pre(player, world, origin, travelPos));
		// Extended End
		List<MobEntity> surroundingEntities = world.getEntitiesOfClass(MobEntity.class, player.getBoundingBox().inflate(256.0));
		HashSet<MobEntity> entitiesToTransport = new HashSet<>();
		for (MobEntity entity : surroundingEntities) {
			if (entity instanceof TameableEntity && ((TameableEntity) entity).getOwner() == player && !((TameableEntity) entity).isOrderedToSit()) {
				entitiesToTransport.add(entity);
				continue;
			}
			if (entity.isLeashed() && entity.getLeashHolder() == player) {
				entitiesToTransport.add(entity);
				continue;
			}
			// Extended Start
			if (entity instanceof ExtendedHirableEntity && ((ExtendedHirableEntity) entity).getOwner() == player && !((ExtendedHirableEntity) entity).isStationary()) {
				entitiesToTransport.add(entity);
				continue;
			}
			// Extended End
		}
		HashSet<Entity> transportExclusions = new HashSet<>();
		for (MobEntity entity : entitiesToTransport) {
			for (Entity rider : entity.getPassengers()) {
				if (!entitiesToTransport.contains(rider)) {
					continue;
				}
				transportExclusions.add(rider);
			}
		}
		entitiesToTransport.removeAll(transportExclusions);
		Entity playerMount = player.getVehicle();
		player.stopRiding();
		player.teleportTo(travelPos.getX() + 0.5D, travelPos.getY(), travelPos.getZ() + 0.5D);
		player.fallDistance = 0.0F;
		if (playerMount instanceof MobEntity) {
			playerMount = fastTravelEntity(world, (MobEntity) playerMount, travelPos.getX() + 0.5D, travelPos.getY(), travelPos.getZ() + 0.5D);
		}
		if (playerMount != null) {
			setUUIDToMount(playerMount.getUUID());
		}
		for (MobEntity entity : entitiesToTransport) {
			Entity mount = entity.getVehicle();
			entity.stopRiding();
			entity = fastTravelEntity(world, entity, travelPos.getX() + 0.5, travelPos.getY(), travelPos.getZ() + 0.5);
			if (!(mount instanceof MobEntity)) {
				continue;
			}
			mount = fastTravelEntity(world, (MobEntity) mount, travelPos.getX() + 0.5, travelPos.getY(), travelPos.getZ() + 0.5);
			entity.startRiding(mount);
		}
		sendFTScreenPacket(player, waypoint, startX, startZ);
		setTimeSinceFTWithUpdate(0);
		incrementWPUseCount(waypoint);
		player.awardStat(LOTRStats.FAST_TRAVEL);
		double dx = player.getX() - startXF;
		double dy = player.getY() - startYF;
		double dz = player.getZ() - startZF;
		int distanceInM = Math.round(MathHelper.sqrt(dx * dx + dy * dy + dz * dz));
		if (distanceInM > 0) {
			player.awardStat(LOTRStats.FAST_TRAVEL_ONE_M, distanceInM);
		}
		// Extended Start
		MinecraftForge.EVENT_BUS.post(new LOTRFastTravelWaypointEvent.Post(player, world, origin, travelPos));
		// Extended End
	}
}
