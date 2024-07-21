package lotr.common.world.spawning;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class WanderingTradersSpawner {

    public static void spawn(World world, PlayerEntity player) {
        BlockPos pos = new BlockPos(player.position().x, player.position().y, player.position().z);

        String entityName = null;
        try {
            entityName = WanderingTradersList.pickTraderFromBiomeName(world, world.getBiomeName(pos).get().getRegistryName().getPath());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            player.sendMessage(new StringTextComponent("An exception occurred while trying to pick the trader"), player.getUUID());
        }

        if (entityName != null) {
            player.sendMessage(new StringTextComponent("An " + entityName + " should spawn near you"), player.getUUID());
        } else {
            player.sendMessage(new StringTextComponent("The picked trader was null"), player.getUUID());
        }
    }

}
