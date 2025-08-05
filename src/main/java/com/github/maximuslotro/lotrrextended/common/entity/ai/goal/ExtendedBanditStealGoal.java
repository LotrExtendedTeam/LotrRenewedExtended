package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import lotr.common.entity.npc.ExtendedBanditEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNavigator;


import java.util.EnumSet;
import java.util.List;

public class ExtendedBanditStealGoal extends Goal {
	private final ExtendedBanditEntity bandit;
	private PlayerEntity targetPlayer;
	private final PathNavigator navigation;

	public ExtendedBanditStealGoal(ExtendedBanditEntity entity, PathNavigator navigation) {
		this.bandit = entity;
		this.navigation = navigation;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		if(!bandit.hasAlreadyStolen()) {
			List<PlayerEntity> players = bandit.level.getEntitiesOfClass(PlayerEntity.class, bandit.getBoundingBox().inflate(2.0D));
			if (!players.isEmpty()) {
				this.targetPlayer = players.get(0);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canContinueToUse() {
		return targetPlayer != null && bandit.distanceTo(targetPlayer) < 1.0D;
	}

	@Override
	public void start() {
	}

	@Override
	public void tick() {
		if (targetPlayer != null && bandit.distanceTo(targetPlayer) < 1.0D) {
			ExtendedBanditStealHelper.stealItemsFromPlayer(targetPlayer, bandit.getInventory());
			bandit.setHasStolen(true);
			this.targetPlayer = null;
		}
	}

	@Override
	public void stop() {
		this.targetPlayer = null;
	}
}