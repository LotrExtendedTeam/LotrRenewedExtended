package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNavigator;


import java.util.EnumSet;
import java.util.List;

public class ExtendedBanditFollowGoal extends Goal {
     private final NPCEntity entity;
    private PlayerEntity targetPlayer;
    private final PathNavigator navigation;


    public ExtendedBanditFollowGoal(NPCEntity entity, PathNavigator navigation) {
            this.entity = entity;
            this.navigation = navigation;
            this.setMutexFlags(EnumSet.of(Flag.MOVE));
        }

    private void setMutexFlags(EnumSet<Flag> move) {
    }


    @Override
    public boolean canUse() {
        List<PlayerEntity> players = entity.level.getEntitiesOfClass(PlayerEntity.class, entity.getBoundingBox().inflate(20.0D));
        if (!players.isEmpty()) {
            this.targetPlayer = players.get(0);
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return targetPlayer != null && entity.distanceTo(targetPlayer) < 30.0D;
    }

    @Override
        public void start() {
        }

        @Override
        public void tick() {
            if (targetPlayer != null) {
                entity.getLookControl().setLookAt(targetPlayer, 10.0F, entity.getMaxHeadXRot());
                navigation.moveTo(targetPlayer, 2.0D);
            }
        }

    @Override
    public void stop() {
        this.targetPlayer = null;
    }

}

