package lotr.common.world.spawning;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.entity.npc.ExtendedWanderingTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.ExtendedEntities;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

public class WanderingTradersSpawner {

    public static void spawn(ServerWorld world, PlayerEntity player) {
        // only make a max of 16 attempts to spawn the traveling trader
        for (int attempts = 0; attempts < 20; attempts++) {
            // pick a random angle and distance from the player
            float angle = world.random.nextFloat() * 360.0F;
            int x = MathHelper.floor(player.getX()) + MathHelper.floor((MathHelper.sin(angle) * (24 + world.random.nextInt(20))));
            int z = MathHelper.floor(player.getZ()) + MathHelper.floor((MathHelper.cos(angle) * (24 + world.random.nextInt(20))));
            int y = world.getHeight(Heightmap.Type.WORLD_SURFACE, x, z);
            BlockPos pos = new BlockPos(x, y, z);

            // get the biome and pick a wandering trader for the location that was selected
            String biomeName = world.getBiomeName(pos).toString();
            String entityName = null;
            try {
                entityName = WanderingTradersList.pickTraderFromBiomeName(world, biomeName);
            } catch (IllegalArgumentException ex) {
                ExtendedLog.error("Issue while determining LOTR when spawning a wandering trader", ex);
            }

            // if entity is null skip some attempts as they are probably in a biome that just doesn't
            // support wandering traders, and we should not try all 16 attempts
            if (entityName == null) {
                ExtendedLog.warn("Got null entity when determining if a wandering trader could spawn at " + pos + ". This biome likely doesn't support wandering traders.");
                attempts += 2;
                continue;
            }

            // if the type is invalid try again but add a few attempts
            EntityType<?> type = getTraderFromName(entityName);
            if (type == null) {
                ExtendedLog.error("Got null EntityType when attempting to parse " + entityName);
                break;
            }

            BlockPos plusOneY = new BlockPos(x, y + 1, z);
            if (y > 62 && !world.getBlockState(pos).isSuffocating(world, pos) && !world.getBlockState(plusOneY).isSuffocating(world, plusOneY)) {
                // avoid spawning on slabs/stairs/leaves
                if (world.getBlockState(pos).getBlock() instanceof StairsBlock ||
                        world.getBlockState(pos).getBlock() instanceof SlabBlock ||
                        world.getBlockState(pos).getBlock() instanceof LeavesBlock) {
                    continue;
                }

                BlockPos spawnPos = pos.offset(new Vector3i(0.5D, 0, 0.5D));
                Entity trader = type.create((ServerWorld) world, null, null, player, spawnPos, SpawnReason.SPAWN_EGG, true, false);
                if (trader instanceof ExtendedWanderingTraderEntity && trader instanceof MobEntity) {
                    // set look angle
                    float yaw = world.random.nextFloat() * 360.0F;
                    trader.setYBodyRot(yaw);
                    trader.setYHeadRot(yaw);

                    ((ExtendedWanderingTraderEntity) trader).getTraderData().setWanderingTraderAliveTime(24000);
                    ((ExtendedWanderingTraderEntity) trader).getTraderData().setWanderingTrader(true);
                    ((MobEntity) trader).restrictTo(pos, 5); // restrict within 5 blocks of spawn point
                    ((MobEntity)trader).setPersistenceRequired();
                    world.addFreshEntityWithPassengers(trader);

                    int escortCount = world.random.nextInt(3) + 2;
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
                    }

                    // send message to the original player
                    player.sendMessage(new TranslationTextComponent("chat.wandering_trader.arrive_near_you", trader.getName().copy().withStyle(TextFormatting.YELLOW)), Util.NIL_UUID);
                    for (PlayerEntity otherPlayer : world.players()) {
                        if (otherPlayer.getUUID().equals(player.getUUID())) continue;
                        otherPlayer.sendMessage(new TranslationTextComponent("chat.wandering_trader.arrive_near_player", trader.getName().copy().withStyle(TextFormatting.YELLOW), player.getName()), Util.NIL_UUID);
                    }
                } else if(trader==null) {
                	ExtendedLog.error("Error attempting to spawn unknown trader. Does " + type + " extend ExtendedTraderEntity?");
                } else {
                    ExtendedLog.error("Error attempting to set trader options when spawning wandering trader. Does " + type + " extend ExtendedTraderEntity and MobEntity?");
                }
                break;
            }
        }
    }

    private static EntityType<?> getTraderFromName(String name) {
        switch (name) {
            case "lotr:galadhrim_wanderer":
                return ExtendedEntities.GALADHRIM_WANDERER.get();
            case "lotr:rivendell_wanderer":
                return ExtendedEntities.RIVENDELL_WANDERER.get();
            case "lotr:oddment_collector":
                return ExtendedEntities.ODDMENT_COLLECTOR.get();
            case "lotr:blue_mountains_merchant":
                return ExtendedEntities.BLUE_MOUNTAINS_MERCHANT.get();
            case "lotr:bree_merchant":
                return ExtendedEntities.BREE_MERCHANT.get();
            default:
                return null;
        }
    }

}
