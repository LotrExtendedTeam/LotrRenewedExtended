package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import lotr.common.entity.npc.ExtendedBanditEntity;
import lotr.common.entity.npc.NPCEntity;
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
    private final NPCEntity entity;
    private PlayerEntity targetPlayer;
    private final PathNavigator navigation;
    private final double dangerRadius = 45.0D;


    public ExtendedBanditEscapeGoal(NPCEntity entity, PathNavigator navigation) {
        this.entity = entity;
        this.navigation = navigation;
        this.setMutexFlags(EnumSet.of(Flag.MOVE));
    }

    private void setMutexFlags(EnumSet<Flag> move) {
    }


    @Override
    public boolean canUse() {
        List<PlayerEntity> players = entity.level.getEntitiesOfClass(PlayerEntity.class, entity.getBoundingBox().inflate(20.0D));
        if (!players.isEmpty() && ((ExtendedBanditEntity) entity).hasAlreadyStolen()) {
            this.targetPlayer = players.get(0);
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return targetPlayer != null && entity.distanceTo(targetPlayer) < dangerRadius;
    }

    @Override
    public void start() {
    }

    @Override
    public void tick() {
        if (targetPlayer != null && ((ExtendedBanditEntity) entity).hasAlreadyStolen()) {
            targetPlayer = entity.level.getNearestPlayer(entity, dangerRadius);
            runAwayFromPlayer();
        }

        Vector3d banditPos = entity.position();
        BlockPos banditBlockPos = new BlockPos(banditPos.x, banditPos.y, banditPos.z);
        if (entity.level instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) entity.level;
            if (!serverWorld.hasChunkAt(banditBlockPos)) {
                stop();
            }
        }
    }


    private void runAwayFromPlayer() {
        if (targetPlayer == null) return;

        Vector3d banditPos = entity.position();
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
        ((ExtendedBanditEntity) entity).setHasStolen(false);
    }

}
