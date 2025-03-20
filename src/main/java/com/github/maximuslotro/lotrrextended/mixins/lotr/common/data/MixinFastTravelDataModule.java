package com.github.maximuslotro.lotrrextended.mixins.lotr.common.data;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.github.maximuslotro.lotrrextended.api.events.world.ExtendedFastTravelWaypointEvent;

import lotr.common.LOTRLog;
import lotr.common.data.FastTravelDataModule;
import lotr.common.entity.npc.ExtendedHirableEntity;
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

/**
 * @reason Adds logic to Fast Travel teleporting to teleport Hired Units with the player
 * @Mixin (FastTravelDataModule.class)
 * */
@Mixin(FastTravelDataModule.class)
public abstract class MixinFastTravelDataModule {
	/**
	 * Helper method for teleporting entities
	 * @Shadow (remap=false)
	 * @param world The ServerWorld to teleport the entity to
	 * @param entity A MobEntity instance of the entity to teleport
	 * @param x X coordinate to teleport to
	 * @param y Y coordinate to teleport to
	 * @param z Z coordinate to teleport to
	 * @return An instance of MobEntity
	 * */
	@Shadow(remap=false)
	private MobEntity fastTravelEntity(ServerWorld world, MobEntity entity, double x, double y, double z) {return null;}

	/**
	 * Sets a UUID, of a mount, for the player to try mounting at the destination FastTravel point
	 * @Shadow (remap=false)
	 * @param uuid The UUID to set
	 * */
	@Shadow(remap=false)
	private void setUUIDToMount(UUID uuid) {}
	/**
	 * Send the FastTravel pack to the player
	 * @Shadow (remap=false)
	 * @param player The ServerPlayerEntity to teleport
	 * @param waypoint The Waypoint instance to teleport to
	 * @param startX The initial x coordinate teleporting from 
	 * @param startZ The initial z coordinate teleporting from 
	 * */
	@Shadow(remap=false)
	private void sendFTScreenPacket(ServerPlayerEntity player, Waypoint waypoint, int startX, int startZ) {}
	/**
	 * Sets the players FastTravel cooldown counter
	 * @Shadow (remap=false)
	 * @param i The time since player FastTraveled
	 * */
	@Shadow(remap=false)
	public void setTimeSinceFTWithUpdate(int i) {}
	/**
	 * Records the number of times the player has used the Waypoint
	 * @Shadow (remap=false)
	 * @param waypoint The waypoint to update count data
	 * */
	@Shadow(remap=false)
	public void incrementWPUseCount(Waypoint waypoint) {}

	/**
	 * @author maximuslotro
	 * @reason Needed to allow Hired Units to travel with the player, like leashed entities and mounts do
	 * @Overwrite (remap=false)
	 * @param player The ServerPlayerEntity to teleport
	 * @param waypoint The Waypoint to teleport to
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
		MinecraftForge.EVENT_BUS.post(new ExtendedFastTravelWaypointEvent.Pre(player, world, origin, travelPos));
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
			if (entity instanceof ExtendedHirableEntity && ((ExtendedHirableEntity) entity).getUnitOwner() == player && !((ExtendedHirableEntity) entity).getOrders().isFollowingPlayer()) {
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
		MinecraftForge.EVENT_BUS.post(new ExtendedFastTravelWaypointEvent.Post(player, world, origin, travelPos));
		// Extended End
	}
}
