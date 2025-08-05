package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import lotr.common.entity.npc.ExtendedBanditEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
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
		if(bandit.hasAlreadyStolen()) { // move simple check outside of getEntitiesOfClass for peformance reasons
			List<PlayerEntity> players = bandit.level.getEntitiesOfClass(PlayerEntity.class, bandit.getBoundingBox().inflate(20.0D));
			if (!players.isEmpty()) {
				this.targetPlayer = players.get(0);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canContinueToUse() {
		return targetPlayer != null && bandit.distanceTo(targetPlayer) < dangerRadius;
	}

	@Override
	public void start() {
	}

	@Override
	public void tick() {
		if (targetPlayer != null && bandit.hasAlreadyStolen()) {
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
		Vector3d direction = banditPos.subtract(playerPos).normalize().scale(35.0D);
		Vector3d fleeTarget = banditPos.add(direction);

		Path path = navigation.createPath(fleeTarget.x, fleeTarget.y, fleeTarget.z, 0);
		if (path != null) {
			navigation.moveTo(path, 2.0D);
		}
	}

	@Override
	public void stop() {
		this.targetPlayer = null;
		navigation.stop();
		bandit.setHasStolen(false);
	}
}
