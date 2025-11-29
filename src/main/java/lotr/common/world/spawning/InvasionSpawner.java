package lotr.common.world.spawning;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedInvasionSpawnerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

public class InvasionSpawner {

    public static void spawn(ServerWorld world, PlayerEntity player) {
        // TODO: Add check for protection

        // only make a max of 16 attempts to spawn the traveling trader
        /*for (int attempts = 0; attempts < 20; attempts++) {*/
            float angle = world.random.nextFloat() * 360.0F;
            int x = MathHelper.floor(player.getX()) + MathHelper.floor((MathHelper.sin(angle) * (24 + world.random.nextInt(20))));
            int z = MathHelper.floor(player.getZ()) + MathHelper.floor((MathHelper.cos(angle) * (24 + world.random.nextInt(20))));
            int y = world.getHeight(Heightmap.Type.WORLD_SURFACE, x, z) + 3;

            // TODO: Add lotr:high_elves,lindon as an option later
            ExtendedInvasionSpawnerEntity spawner = new ExtendedInvasionSpawnerEntity(world);
            int roll = world.random.nextInt(3);
            switch (roll) {
                case 0:
                    spawner.setFaction("lotr:high_elves,rivendell");
                    break;
                case 1:
                    spawner.setFaction("lotr:northern_orcs");
                    break;
                case 2:
                    spawner.setFaction("lotr:dunedain_north");
                    break;
            }
            spawner.setPos(x, y, z);
            world.addFreshEntity(spawner);
        //}
    }
}
