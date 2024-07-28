package lotr.common.world.spawning;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.ExtendedEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

public class WanderingTradersSpawner {

    public static void spawn(World world, PlayerEntity player) {
        // only make a max of 16 attempts to spawn the traveling trader
        for (int attempts = 0; attempts < 16; attempts++) {

            float angle = world.random.nextFloat() * 360.0F;
            int x = MathHelper.floor(player.getX()) + MathHelper.floor((MathHelper.sin(angle) * (24 + world.random.nextInt(20))));
            int z = MathHelper.floor(player.getZ()) + MathHelper.floor((MathHelper.cos(angle) * (24 + world.random.nextInt(20))));
            int y = world.getHeight(Heightmap.Type.WORLD_SURFACE, x, z);
            BlockPos pos = new BlockPos(x, y, z);

            String biomeName = world.getBiomeName(pos).toString();
            String entityName = null;
            try {
                entityName = WanderingTradersList.pickTraderFromBiomeName(world, biomeName);
            } catch (IllegalArgumentException ex) {
                ExtendedLog.error("Issue while determining LOTR when spawning a wandering trader", ex);
            }

            if (entityName != null) {
                EntityType type = getTraderFromName(entityName);
                if (type == null) {
                    ExtendedLog.error("Got null EntityType when attempting to parse " + entityName);
                    continue;
                }

                BlockPos plusOneY = new BlockPos(x, y + 1, z);
                if (y > 62 && !world.getBlockState(pos).isSuffocating(world, pos) && !world.getBlockState(plusOneY).isSuffocating(world, plusOneY)) {
                    BlockPos spawnPos = pos.offset(new Vector3i(0.5D, 0, 0.5D));
                    Entity trader = type.spawn((ServerWorld) world, null, null, spawnPos, SpawnReason.SPAWN_EGG, true, true);
                    if (trader instanceof ExtendedTraderEntity) {
                        // set look angle
                        float yaw = world.random.nextFloat() * 360.0F;
                        trader.setYBodyRot(yaw);
                        trader.setYHeadRot(yaw);

                        // TODO: Set to actual time when done with testing
                        System.out.println("Setting wandering trader status and they should be removed in a minute");
                        ((ExtendedTraderEntity) trader).setWanderingTraderTicksLeft(18000);
                        ((ExtendedTraderEntity) trader).setWanderingTrader(true);
                        ((NPCEntity) trader).restrictTo(pos, 5); // restrict within 5 blocks of spawn point

                        // TODO: Add support for spawning guards

                        // send message to the original player
                        player.sendMessage(new TranslationTextComponent("chat.wandering_trader.arrive_near_you",((NPCEntity) trader).getName(), new StringTextComponent("X: " + pos.getX() + " Z: " + pos.getZ())), Util.NIL_UUID);
                        for (PlayerEntity otherPlayer : world.players()) {
                            if (otherPlayer.getUUID().equals(player.getUUID())) continue;

                            otherPlayer.sendMessage(new TranslationTextComponent("chat.wandering_trader.arrive_near_player",((NPCEntity) trader).getName(), player.getName()), Util.NIL_UUID);
                        }
                        break;
                    } else {
                        ExtendedLog.error("Error attempting to set trader options when spawning wandering trader. Does " + type + " extend ExtendedTraderEntity?");
                        break;
                    }

                }
            } else {
                player.sendMessage(new StringTextComponent("The picked trader was null"), player.getUUID());
            }
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
