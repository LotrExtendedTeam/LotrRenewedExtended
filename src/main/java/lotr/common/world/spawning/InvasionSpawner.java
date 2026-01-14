package lotr.common.world.spawning;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedInvasionSpawnerEntity;
import lotr.common.world.ExtendedInvasions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

public class InvasionSpawner {

    /**
     * This is for when the invasion event is directly run as a command. It should spawn an
     * invasion if it is possible for one to form in that biome. If no invasion can be spawned
     * in that biome the method returns false. This method will attempt to spawn an invasion up
     * to 32 times depending on if there is a protection in the points it picks. If 32 times are
     * exceeded the method will also return false.
     *
     * @param world
     * @param player
     * @return boolean if an invasion was spawned
     */
    public static boolean requireInvasionSpawn(ServerWorld world, PlayerEntity player) {
        boolean spawnedInvasion = false;

        // only make a max of 32 attempts to spawn the invasion checking protection boundaries
        for (int attempts = 0; attempts < 32; attempts++) {
            // pick a location near the player
            float angle = world.random.nextFloat() * 360.0F;
            int x = MathHelper.floor(player.getX()) + MathHelper.floor((MathHelper.sin(angle) * (24 + world.random.nextInt(20))));
            int z = MathHelper.floor(player.getZ()) + MathHelper.floor((MathHelper.cos(angle) * (24 + world.random.nextInt(20))));
            int y = world.getHeight(Heightmap.Type.WORLD_SURFACE, x, z) + 3;

            // check for protection
            BlockPos pos = new BlockPos(player.position());
            if (ExtendedBannerProtectionManager.getProtectionStatus(world, pos, new ExtendedBannerProtectionManager.SingleProtectionInstance.AnyProtection(), false, "checkSpawnRules").isNotAllowed()) {
                continue;
            }

            // determine possible invasions
            String biomeName = world.getBiomeName(pos).toString();
            ExtendedInvasions invasion = null;
            try {
                invasion = InvasionBiomeList.pickInvasionFromBiomeName(world, biomeName);
            } catch (IllegalArgumentException ex) {
                ExtendedLog.error("Issue while determining LOTR when spawning an invasion.", ex);
            }

            if (invasion != null) {
                ExtendedInvasionSpawnerEntity spawner = new ExtendedInvasionSpawnerEntity(world);
                spawner.setFaction(invasion.getKey());
                spawner.setPos(x, y, z);
                world.addFreshEntity(spawner);
                spawnedInvasion = true;
                break;
            }
        }

        return spawnedInvasion;
    }
}
