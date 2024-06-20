package lotr.common.util;

import lotr.common.entity.npc.ExtendedHirableEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class ExtendedTeleportHelper {

    public static void teleportUnitsToPlayer(BlockPos originalPos, BlockPos target, ServerWorld world, PlayerEntity player) {
        AxisAlignedBB initial = new AxisAlignedBB(originalPos.getX(), originalPos.getY(), originalPos.getZ(),
                originalPos.getX() + 1, originalPos.getY() + 1, originalPos.getZ() + 1);
        List<NPCEntity> lotrEntities = world.getEntitiesOfClass(NPCEntity.class, initial.inflate(256));

        for (NPCEntity npc : lotrEntities) {
            if (!(npc instanceof ExtendedHirableEntity)) continue;

            ExtendedHirableEntity hireling = (ExtendedHirableEntity) npc;

            if (!hireling.isStationary()) {
                fastTravelEntity(world, npc, target.getX() + 0.5D, target.getY() + 0.5D, target.getZ() + 0.5D);
            }
        }

    }
    private static <T extends MobEntity> T fastTravelEntity(ServerWorld world, T entity, double x, double y, double z) {
        entity.moveTo(x, y, z, entity.yRot, entity.xRot);
        entity.fallDistance = 0.0F;
        entity.getNavigation().stop();
        entity.setTarget((LivingEntity) null);
        ServerChunkProvider scp = world.getChunkSource();
        scp.removeEntity(entity);
        scp.addEntity(entity);
        return entity;
    }

}
