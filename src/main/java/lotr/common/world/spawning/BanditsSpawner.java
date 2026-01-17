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
     * @param world            The {@link ServerWorld} instance where the bandit should be spawned.
     * @param player           The {@link PlayerEntity} for whom the bandit is being spawned. Used as the spawn center.
     * @param alwaysSpawn      Whether to force spawning regardless of biome restrictions or spawn chance.
     * @param ignoreProtection Whether to bypass area protection checks (e.g., banner protection) when spawning.
     * @return {@code true} if a bandit was successfully spawned; {@code false} otherwise.
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

        // check protection against where the player is standing - this prevent a bandit from being generated for the player
        // at all if they are inside a protected area - can be reverted if we decided to but it might be nice to prevent them
        // from getting ambushed outside the wall of their base by a bunch bandits that were spawned while they were in their base
        if (!ignoreProtection) {
            BlockPos pos = new BlockPos(player.position());
            if (ExtendedBannerProtectionManager.getProtectionStatus(world, pos, new ExtendedBannerProtectionManager.SingleProtectionInstance.AnyProtection()).isNotAllowed()) {
                ExtendedLog.info("In Banner Area");
                return false;
            } else {
                ExtendedLog.info("Not In Banner Area");
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

            // all possible conditions that should make us quit and pick a new location
            BlockPos plusOneY = new BlockPos(x, y + 1, z);
            if (y < 62 || world.getBlockState(pos).isSuffocating(world, pos) || world.getBlockState(plusOneY).isSuffocating(world, plusOneY)
                    || (chance == 0 && !alwaysSpawn)) {
                continue;
            }

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

                // determine if there should be additional bandits (only for uncommon and common)
                if (chance > 10) {
                    int additionalBandits = 0;
                    if (chance == 30) {
                        // 20% chance for additional bandit in uncommon
                        int roll = world.random.nextInt(100) + 1;
                        if (roll <= 20) additionalBandits = 1;
                    } else if (chance == 70) {
                        // 20% chance for additional 2 bandits in common
                        int roll = world.random.nextInt(100) + 1;
                        if (roll <= 20) {
                            additionalBandits = 2;
                        } else if (roll >= 40) {
                            additionalBandits = 1; // 60% chance for additional bandit
                        }
                    }

                    for (int i = 0; i < additionalBandits; i++) {
                        ExtendedBanditEntity additional = ExtendedEntities.BANDIT.get().create(world, null, null, player, pos, SpawnReason.SPAWN_EGG, true, false);
                        yaw = world.random.nextFloat() * 360.0F; // set look angle
                        bandit.setYBodyRot(yaw);
                        bandit.setYHeadRot(yaw);
                        bandit.setPersistenceRequired();
                        world.addFreshEntityWithPassengers(additional);
                    }
                }

                return true;
            }
        }

        return false;
    }
}
