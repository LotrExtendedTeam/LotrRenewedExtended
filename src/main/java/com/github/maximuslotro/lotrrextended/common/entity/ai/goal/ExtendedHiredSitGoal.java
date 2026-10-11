package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.EnumSet;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitOrders;

import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;

/**
 * A custom goal for hired NPCs that enables stationary behavior.
 * 
 * Based on {@link net.minecraft.entity.ai.goal.SitGoal}.
 */
public class ExtendedHiredSitGoal extends Goal {
	/** The NPC executing this goal. */
	private final NPCEntity entity;
	/** Path navigator for movement control. */
	private final PathNavigator navigation;

	public ExtendedHiredSitGoal(NPCEntity npc) {
		this.entity = npc;
		this.navigation = entity.getNavigation();
		if (!(entity.getNavigation() instanceof GroundPathNavigator) && !(entity.getNavigation() instanceof FlyingPathNavigator)) {
			throw new IllegalArgumentException("Unsupported mob type for ExtendedHiredSitGoal");
		}
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		if(entity.isInWaterOrBubble() || !entity.isOnGround()) {
			return false;
		}
		if (!((ExtendedINPCEntity)entity).getUnitInfo().isNpcActiveDuty() || ((ExtendedINPCEntity)entity).getUnitInfo().getOrders()!=ExtendedUnitOrders.STATIONARY) {
			return false;
		}
		if(entity.getLastHurtByMob() != null && entity.getLastHurtByMob() != ((ExtendedINPCEntity)entity).getUnitInfo().getUnitOwner()) {
			return false;
		}
		return true;
	}

	@Override
	public void start() {
		this.entity.getNavigation().stop();
	}
}