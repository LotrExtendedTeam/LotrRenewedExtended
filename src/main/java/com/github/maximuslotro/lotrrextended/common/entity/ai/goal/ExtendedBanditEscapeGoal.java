package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import lotr.common.entity.npc.ExtendedBanditEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;


import java.util.EnumSet;
import java.util.List;

public class ExtendedBanditEscapeGoal extends Goal {
	private final ExtendedBanditEntity bandit;
	private PlayerEntity targetPlayer;
	private final PathNavigator navigation;
	private final double dangerRadius = 45.0D;


	public ExtendedBanditEscapeGoal(ExtendedBanditEntity entity, PathNavigator navigation) {
		this.bandit = entity;
		this.navigation = navigation;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		if(bandit.hasStolen()) { // move simple check outside of getEntitiesOfClass for performance reasons
			if (bandit.getLastDamageSource() != null) {
				DamageSource last = bandit.getLastDamageSource();
				if (last.getEntity() != null && last.getEntity() instanceof PlayerEntity) {
					return false;
				}
			}

			List<PlayerEntity> players = bandit.level.getEntitiesOfClass(PlayerEntity.class, bandit.getBoundingBox().inflate(dangerRadius), EntityPredicates.NO_CREATIVE_OR_SPECTATOR);
			if (!players.isEmpty()) {
				// Instead of picking the first player in the closest list, pick the closest player to run away from
				double minDistance = dangerRadius;
				PlayerEntity closestTarget = players.get(0);
				for(PlayerEntity player : players) {
					double playerDistance = bandit.distanceTo(player);
					if(playerDistance < minDistance) {
						minDistance = playerDistance;
						closestTarget = player;
					}
				}
				this.targetPlayer = closestTarget;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canContinueToUse() {
		if (targetPlayer != null && targetPlayer.isAlive() && bandit.distanceTo(targetPlayer) < dangerRadius) {
			if (bandit.getLastDamageSource() != null) {
				DamageSource last = bandit.getLastDamageSource();
				return last.getEntity() == null || !(last.getEntity() instanceof PlayerEntity);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void tick() {
		if (targetPlayer != null && bandit.hasStolen()) {
			targetPlayer = bandit.level.getNearestPlayer(bandit, dangerRadius);
			runAwayFromPlayer();
		}

		Vector3d banditPos = bandit.position();
		BlockPos banditBlockPos = new BlockPos(banditPos.x, banditPos.y, banditPos.z);
		if (bandit.level instanceof ServerWorld) {
			ServerWorld serverWorld = (ServerWorld) bandit.level;
			if (!serverWorld.hasChunkAt(banditBlockPos)) {
				stop();
			}
		}
	}

	private void runAwayFromPlayer() {
		if (targetPlayer == null) {
			return;
		}

		Vector3d banditPos = bandit.position();
		Vector3d playerPos = targetPlayer.position();
		Vector3d direction = banditPos.subtract(playerPos).normalize().scale(dangerRadius);
		Vector3d fleeTarget = banditPos.add(direction);

		Path path = navigation.createPath(fleeTarget.x, fleeTarget.y, fleeTarget.z, 0);
		if (path != null) {
			navigation.moveTo(path, 1.5D);
		}
	}

	@Override
	public void stop() {
		this.targetPlayer = null;
		navigation.stop();
	}
}
