package lotr.common.world.spawning;

import java.util.Optional;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.ExtendedIWanderingTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.world.spawning.WanderingTradersList.ExtendedWanderingTraderData;
import net.minecraft.block.BlockState;
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
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

public class WanderingTradersSpawner {

	public static boolean attemptSpawn(ServerWorld world, PlayerEntity player, boolean disableBannerChecks, boolean disableAlignmentChecks) {
		// only make a max of 16 attempts to spawn the traveling trader
		for (int attempts = 0; attempts < 20; attempts++) {
			// pick a random angle and distance from the player
			float angle = world.random.nextFloat() * 360.0F;
			int x = MathHelper.floor(player.getX()) + MathHelper.floor((MathHelper.sin(angle) * (24 + world.random.nextInt(20))));
			int z = MathHelper.floor(player.getZ()) + MathHelper.floor((MathHelper.cos(angle) * (24 + world.random.nextInt(20))));
			int y = world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
			BlockPos position = new BlockPos(x, y, z);

			// get the biome and pick a wandering trader for the location that was selected
			Optional<ExtendedWanderingTraderData> entityData = WanderingTradersList.pickTraderFromBiomeId(world, world.getBiomeName(position));
			// probably in a biome that just doesn't support wandering traders
			if (!entityData.isPresent()) {
				return false;
			}
			// Disable spawning for players with opposing alignment
			if (LOTRLevelData.serverInstance().getData(player).getAlignmentData().getAlignment(entityData.get().traderFaction)<0 && !disableAlignmentChecks) {
				continue;
			}
			EntityType<?> type = entityData.get().traderType;
			BlockPos plusOneY = position.above();
			if (y > 62 && !world.getBlockState(position).isSuffocating(world, position) && !world.getBlockState(plusOneY).isSuffocating(world, plusOneY)) {
				BlockState state = world.getBlockState(position.below());
				// avoid spawning on slabs/stairs/leaves
				if (!world.getFluidState(position.below()).isEmpty() ||
					state.getBlock() instanceof StairsBlock ||
					state.getBlock() instanceof SlabBlock ||
					state.getBlock() instanceof LeavesBlock) {
					continue;
				}

				Entity trader = type.create((ServerWorld) world, null, null, player, position, SpawnReason.EVENT, true, false);
				if (trader instanceof ExtendedIWanderingTraderEntity && trader instanceof MobEntity) {
					// check spawn rules (banner protection for EVENT types)
					if (!((MobEntity)trader).checkSpawnRules(world, SpawnReason.EVENT) && !disableBannerChecks) {
						// Disable spawning under opposing banner protection
	                	continue;
	                }
					// set look angle
					float yaw = world.random.nextFloat() * 360.0F;
					trader.setYBodyRot(yaw);
					trader.setYHeadRot(yaw);

					((ExtendedIWanderingTraderEntity) trader).getTraderData().setWanderingTraderAliveTime(24000);
					((ExtendedIWanderingTraderEntity) trader).getTraderData().setWanderingTrader(true);
					((MobEntity) trader).restrictTo(position, 5); // restrict within 5 blocks of spawn point
					((MobEntity)trader).setPersistenceRequired();
					world.addFreshEntityWithPassengers(trader);

					int escortCount = world.random.nextInt(3) + 2;
					for (int i = 0; i < escortCount; i++) {
						NPCEntity escort = ((ExtendedIWanderingTraderEntity) trader).createEscort();
						if (escort != null) {
							escort.setPersistenceRequired();
							BlockPos escortPos = new BlockPos(trader.getX(), trader.getY(), trader.getZ());
							escort.finalizeSpawn((IServerWorld) world, world.getCurrentDifficultyAt(escortPos), SpawnReason.EVENT, null, null);
							escort.setPos(trader.getX(), trader.getY(), trader.getZ());
							world.addFreshEntityWithPassengers(escort);
							escort.restrictTo(escortPos, 8);
							((ExtendedIWanderingTraderEntity) trader).addEscort(escort.getUUID());
						}
					}

					Style npcNameStyle = Style.EMPTY.withColor(Color.fromRgb(((ExtendedIWanderingTraderEntity) trader).getFactionColor()));
					if(((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData(player)).getExtendedMiscData().doHighContrastMessages()) {
						player.sendMessage(new TranslationTextComponent("chat.wandering_trader.arrive_near_you", trader.getName().copy().withStyle(npcNameStyle)), Util.NIL_UUID);
					}else {
						player.sendMessage(new TranslationTextComponent("chat.wandering_trader.arrive_near_you", trader.getName().copy().withStyle(TextFormatting.YELLOW)), Util.NIL_UUID);
					}
					for (PlayerEntity otherPlayer : world.players()) {
						if (otherPlayer.getUUID().equals(player.getUUID())) {
							continue;
						}
						if(((ExtendedLOTRPlayerData)LOTRLevelData.serverInstance().getData(otherPlayer)).getExtendedMiscData().doHighContrastMessages()) {
							otherPlayer.sendMessage(new TranslationTextComponent("chat.wandering_trader.arrive_near_player", trader.getName().copy().withStyle(npcNameStyle), player.getName()), Util.NIL_UUID);
						}else {
							otherPlayer.sendMessage(new TranslationTextComponent("chat.wandering_trader.arrive_near_player", trader.getName().copy().withStyle(TextFormatting.YELLOW), player.getName()), Util.NIL_UUID);
						}
					}
					return true;
				} else if(trader==null) {
					// Entity was denied spawning
					//ExtendedLog.error("Error attempting to spawn unknown trader. Does " + type + " extend ExtendedTraderEntity?");
					return false;
				} else {
					ExtendedLog.error("Error attempting to set trader options when spawning wandering trader. Does " + type + " extend ExtendedTraderEntity and MobEntity?");
					return false;
				}
			}
		}
		return false;
	}
}
