package lotr.common.world.spawning;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedInvasionSpawnerEntity;

import lotr.common.data.LOTRLevelData;
import lotr.common.fac.AlignmentPredicates;
import lotr.common.world.ExtendedInvasions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

import java.util.Map;

public class InvasionSpawner {

	/**
	 * Attempts to forcibly spawn an invasion near the given player.
	 *
	 * <p>This is primarily used by commands. The method will:</p>
	 * <ul>
	 *   <li>Try up to 32 nearby positions</li>
	 *   <li>Respect banner protection rules</li>
	 *   <li>Use biome-based weighted selection</li>
	 * </ul>
	 *
	 * <p>If no valid spawn location is found within 32 attempts,
	 * the method returns {@code false}.</p>
	 *
	 * @param world the world to spawn the invasion in
	 * @param player the player near whom the invasion should spawn
	 * @return {@code true} if an invasion was successfully spawned
	 */
    public static boolean requireInvasionSpawn(ServerWorld world, PlayerEntity player) {
        boolean spawnedInvasion = false;

        // only make a max of 32 attempts to spawn the invasion checking protection boundaries
        for (int attempts = 0; attempts < 32; attempts++) {
            // pick a location near the player
            float angle = world.random.nextFloat() * 360.0F;
            int x = MathHelper.floor(player.getX()) + MathHelper.floor((MathHelper.sin(angle) * (24 + world.random.nextInt(20))));
            int z = MathHelper.floor(player.getZ()) + MathHelper.floor((MathHelper.cos(angle) * (24 + world.random.nextInt(20))));
            int y = world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z) + 3;

            // check for protection
            BlockPos pos = new BlockPos(player.position());
            if (ExtendedBannerProtectionManager.getProtectionStatus(world, pos, new ExtendedBannerProtectionManager.ProtectionInstance.AnyProtection()).isNotAllowed()) {
                continue;
            }

            try {
                ExtendedInvasions invasion = InvasionBiomeList.pickInvasionFromBiomeName(world, world.getBiomeName(pos));
                if (invasion != null) {
                    ExtendedInvasionSpawnerEntity spawner = new ExtendedInvasionSpawnerEntity(world);
                    spawner.setTypeKey(invasion.getTypeKey());
                    spawner.setPos(x, y, z);
                    world.addFreshEntity(spawner);
                    spawner.startInvasion();
                    spawnedInvasion = true;
                    break;
                }
            } catch (IllegalArgumentException ex) {
                ExtendedLog.error("Issue while determining LOTR when spawning an invasion.");
                ex.printStackTrace();
            }
        }

        return spawnedInvasion;
    }

    /**
     * Performs natural invasion spawn checks near the player.
     *
     * <p>This method is intended for periodic/random spawning. It:</p>
     * <ul>
     *   <li>Gets all invasions valid for the player's current biome</li>
     *   <li>Rolls each invasion using {@link InvasionChance#getChancePerSecond()}</li>
     *   <li>Attempts up to 8 placement checks per successful roll</li>
     * </ul>
     *
     * <p>Banner protection and biome validation are enforced before spawning.</p>
     *
     * @param world the world to spawn the invasion in
     * @param player the player near whom invasions may spawn
     * @return {@code true} if any invasion was successfully spawned
     */
    public static boolean attemptInvasionSpawns(ServerWorld world, PlayerEntity player) {
        boolean spawnedInvasion = false;

        // get all possible invasions that could spawn where the player is standing
        Map<ExtendedInvasions, InvasionChance> invasions = InvasionBiomeList.getInvasionsPerBiome(world.getBiomeName(new BlockPos(player.position())));
        if (invasions.isEmpty()) {
        	return false;
        }

        for (Map.Entry<ExtendedInvasions, InvasionChance> entry : invasions.entrySet()) {
            double pickedNum = world.random.nextDouble();
            if (pickedNum <= entry.getValue().getChancePerSecond()) {
            	boolean isNegative = entry.getKey().getFactionPointer().resolveFaction(world).map(faction -> LOTRLevelData.getSidedData(player).getAlignmentData().hasAlignment(faction, AlignmentPredicates.NEGATIVE)).orElse(false);
            	if (!isNegative) {
                    continue;
                }
                // only make a max of 8 attempts to spawn the invasion checking protection boundaries
                // while verifying that invasion is possible in that biome
                for (int attempts = 0; attempts < 8; attempts++) {
                    // pick a location near the player
                    float angle = world.random.nextFloat() * 360.0F;
                    int x = MathHelper.floor(player.getX()) + MathHelper.floor((MathHelper.sin(angle) * (24 + world.random.nextInt(20))));
                    int z = MathHelper.floor(player.getZ()) + MathHelper.floor((MathHelper.cos(angle) * (24 + world.random.nextInt(20))));
                    int y = world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z) + 3;

                    // check for protection here (rather than check spawn rules as the entity needs a faction, which invasion entities do not)
                    BlockPos spawnPos = new BlockPos(x, y, z);
                    if (ExtendedBannerProtectionManager.getProtectionStatus(world, spawnPos, new ExtendedBannerProtectionManager.ProtectionInstance.AnyProtection()).isNotAllowed()) {
                        continue;
                    }
                    // Check again if position can have invasion, in the case of a border with a different biome
                    if (!InvasionBiomeList.getInvasionsPerBiome(world.getBiomeName(spawnPos)).containsKey(entry.getKey())) {
                    	continue;
                    }

                    ExtendedInvasionSpawnerEntity spawner = new ExtendedInvasionSpawnerEntity(world);
                    spawner.setTypeKey(entry.getKey().getTypeKey());
                    spawner.setPos(x, y, z);
                    world.addFreshEntity(spawner);
                    spawner.startInvasion();
                    spawnedInvasion = true;
                    break;
                }
            }
        }
        return spawnedInvasion;
    }
}
