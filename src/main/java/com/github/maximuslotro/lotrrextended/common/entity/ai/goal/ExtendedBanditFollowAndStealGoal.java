package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import lotr.common.entity.npc.ExtendedBanditEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.EntityPredicates;

import java.util.EnumSet;
import java.util.List;

public class ExtendedBanditFollowAndStealGoal extends Goal {
	private final ExtendedBanditEntity bandit;
	private PlayerEntity targetPlayer;
	private final PathNavigator navigation;

	public ExtendedBanditFollowAndStealGoal(ExtendedBanditEntity entity, PathNavigator navigation) {
		this.bandit = entity;
		this.navigation = navigation;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		if(!bandit.hasStolen() && !bandit.getNPCCombatUpdater().isCombatStance()) {
			List<PlayerEntity> players = bandit.level.getEntitiesOfClass(PlayerEntity.class, bandit.getBoundingBox().inflate(45.0D), EntityPredicates.NO_CREATIVE_OR_SPECTATOR);
			if (!players.isEmpty()) {
				this.targetPlayer = players.get(bandit.random.nextInt(players.size()));
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canContinueToUse() {
		return targetPlayer != null && targetPlayer.isAlive() && bandit.distanceTo(targetPlayer) < 30.0D && !bandit.hasStolen();
	}

	@Override
	public void start() {
	}

	@Override
	public void tick() {
		if (targetPlayer != null && !bandit.hasStolen()) {
			bandit.getLookControl().setLookAt(targetPlayer, 10.0F, bandit.getMaxHeadXRot());
			navigation.moveTo(targetPlayer, 1.8D);
			if (bandit.distanceTo(targetPlayer) < 2.0D) {
				ExtendedBanditStealHelper.stealItemsFromPlayer(targetPlayer, bandit.getInventory());
				bandit.setStolen();
				this.targetPlayer = null;
			}
		}
	}

	@Override
	public void stop() {
		this.targetPlayer = null;
	}
}