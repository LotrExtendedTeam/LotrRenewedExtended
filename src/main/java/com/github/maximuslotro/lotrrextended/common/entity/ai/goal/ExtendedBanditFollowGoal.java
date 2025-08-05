package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import lotr.common.entity.npc.ExtendedBanditEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNavigator;


import java.util.EnumSet;
import java.util.List;

public class ExtendedBanditFollowGoal extends Goal {
	private final ExtendedBanditEntity bandit;
	private PlayerEntity targetPlayer;
	private final PathNavigator navigation;


	public ExtendedBanditFollowGoal(ExtendedBanditEntity entity, PathNavigator navigation) {
		this.bandit = entity;
		this.navigation = navigation;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		if(!bandit.hasAlreadyStolen()) {
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
		return targetPlayer != null && bandit.distanceTo(targetPlayer) < 30.0D;
	}

	@Override
	public void start() {
	}

	@Override
	public void tick() {
		if (targetPlayer != null && !bandit.hasAlreadyStolen()) {
			bandit.getLookControl().setLookAt(targetPlayer, 10.0F, bandit.getMaxHeadXRot());
			navigation.moveTo(targetPlayer, 2.0D);
		}
	}

	@Override
	public void stop() {
		this.targetPlayer = null;
	}
}