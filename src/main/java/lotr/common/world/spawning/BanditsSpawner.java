package lotr.common.world.spawning;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import lotr.common.entity.npc.ExtendedBanditEntity;
import lotr.common.init.ExtendedEntities;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

public class BanditsSpawner {

    /**
     * Attempt to spawn a bandit for the player and in the specified world.
     *
     * If alwaysSpawn is provided as false we will just attempt to spawn one and it could fail if there is no chance of
     * bandits in the biome the player is in, if the roll for spawning the bandit fails, or if all distance for the
     * bandit to spawn in is protected. Always spawn will override this and allow spawning bandits even in biomes where
     * no bandits spawn and will not do a roll.
     *
     * IgnoreProtection can be used to spawn a bandit even if the area is protected
     *
     * @param world
     * @param player
     * @param alwaysSpawn
     * @param ignoreProtection
     * @return
     */
    public static boolean spawn(ServerWorld world, PlayerEntity player, boolean alwaysSpawn, boolean ignoreProtection) {
        // get the biome the player is in and check if we should attempt to spawn a
        if (!alwaysSpawn) {
            BlockPos pos = new BlockPos(player.position());
            String biomeName = world.getBiomeName(pos).toString();
            int chance = 0;

            try {
                chance = BanditBiomeList.getChanceOfBanditFromBiomeName(biomeName);
            } catch (IllegalArgumentException ex) {
                ExtendedLog.error("Issue while determining LOTR biome when spawning a bandit", ex);
            }

            int roll = world.random.nextInt(100) + 1;
            if (roll >= chance) {
                ExtendedLog.debug("Roll for Bandit failed - Got " + roll + " and chance was " + chance + ".");
                return false;
            }
        }

        // if we have made it this point the roll succeeded - make 20 attempts to find a point to spawn them
        for (int attempts = 0; attempts < 20; attempts++) {
            // pick a random angle and distance from the player
            float angle = world.random.nextFloat() * 360.0F;
            int x = MathHelper.floor(player.getX()) + MathHelper.floor((MathHelper.sin(angle) * (24 + world.random.nextInt(20))));
            int z = MathHelper.floor(player.getZ()) + MathHelper.floor((MathHelper.cos(angle) * (24 + world.random.nextInt(20))));
            int y = world.getHeight(Heightmap.Type.WORLD_SURFACE, x, z);
            BlockPos pos = new BlockPos(x, y, z);

            // verify the position in within a biome that can have bandits in case they are at the border
            String biomeName = world.getBiomeName(pos).toString();
            int chance = 0;
            try {
                chance = BanditBiomeList.getChanceOfBanditFromBiomeName(biomeName);
            } catch (IllegalArgumentException ex) {
                ExtendedLog.error("Issue while determining LOTR biome when spawning a bandit", ex);
            }
            // try again as this point must have gone into a biome that there can be no bandits in
            if (chance == 0) continue;

            ExtendedBanditEntity bandit = ExtendedEntities.BANDIT.get().create(world, null, null, player, pos, SpawnReason.SPAWN_EGG, true, false);
            if (bandit != null) {
                // check protection
                if (!ignoreProtection) {
                    if (ExtendedBannerProtectionManager.getProtectionStatus(world, pos, new ExtendedBannerProtectionManager.SingleProtectionInstance.EntityProtection(bandit), false, "checkSpawnRules").isNotAllowed()) {
                        ExtendedLog.debug("Bandit could not be spawned at " + pos + " because it was inside protection banner.");
                        continue;
                    }
                }

                // actually attempt to spawn the bandit
                float yaw = world.random.nextFloat() * 360.0F; // set look angle
                bandit.setYBodyRot(yaw);
                bandit.setYHeadRot(yaw);
                bandit.setPersistenceRequired();
                world.addFreshEntityWithPassengers(bandit);
                return true;

                // TODO: should there be more than one bandit potentially?
                /*int escortCount = world.random.nextInt(3) + 2;
                for (int i = 0; i < escortCount; i++) {
                    NPCEntity escort = ((ExtendedWanderingTraderEntity) trader).createEscort();
                    if (escort != null) {
                        escort.setPersistenceRequired();
                        BlockPos escortPos = new BlockPos(trader.getX(), trader.getY(), trader.getZ());
                        escort.finalizeSpawn((IServerWorld) world, world.getCurrentDifficultyAt(escortPos), SpawnReason.EVENT, null, null);
                        escort.setPos(trader.getX(), trader.getY(), trader.getZ());
                        world.addFreshEntity(escort);
                        escort.restrictTo(escortPos, 8);
                        ((ExtendedWanderingTraderEntity) trader).addEscort(escort.getUUID());
                    }
                }*/
            }
        }

        return false;
    }
}
