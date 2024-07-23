package lotr.common.world.spawning;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.init.ExtendedEntities;
import lotr.common.util.ExtendedTraderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WanderingTradersSpawner {

    public static void spawn(World world, PlayerEntity player) {
        BlockPos pos = new BlockPos(player.position().x, player.position().y, player.position().z);

        String entityName = null;
        try {
            String biomeName = world.getBiomeName(pos).toString();
            entityName = WanderingTradersList.pickTraderFromBiomeName(world, biomeName);
        } catch (IllegalArgumentException ex) {
            ExtendedLog.error("Issue while determing LOTR when spawning a wandering trader", ex);
        }

        if (entityName != null) {
            EntityType type = getTraderFromName(entityName);
            if (type == null) {
                ExtendedLog.error("Got null EntityType when attempting to parse " + entityName);
                return;
            }

            // TODO: Finish getting spawn stuff setup
            Entity trader = type.spawn((ServerWorld) world, null, null, pos, SpawnReason.SPAWN_EGG, true, true);
            if (trader instanceof ExtendedTraderEntity) {
                // TODO: Set to actual time when done with testing
                System.out.println("Setting wandering trader status and they should be removed in a minute");
                ((ExtendedTraderEntity) trader).setWanderingTraderTicksLeft(1200);
                ((ExtendedTraderEntity) trader).setWanderingTrader(true);
            } else {
                ExtendedLog.error("Error attempting to set trader options when spawning wandering trader. Does " + type + " extend ExtendedTraderEntity?");
            }

            player.sendMessage(new StringTextComponent("An " + entityName + " should spawn near you"), player.getUUID());
        } else {
            player.sendMessage(new StringTextComponent("The picked trader was null"), player.getUUID());

        }
    }

    private static EntityType getTraderFromName(String name) {
        if (name.equals("lotr:galadhrim_wanderer")) {
            return ExtendedEntities.GALADHRIM_WANDERER.get();
        } else if (name.equals("lotr:rivendell_wanderer")) {
            return ExtendedEntities.RIVENDELL_WANDERER.get();
        } else if (name.equals("lotr:oddment_collector")) {
            return ExtendedEntities.ODDMENT_COLLECTOR.get();
        } else {
            return null;
        }
    }

}
