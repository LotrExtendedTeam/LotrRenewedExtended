package com.github.maximuslotro.lotrrextended;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import lotr.common.init.LOTRDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExtendedVanillaProtectionHandlers {
	public static Map<UUID, Long> extendedWarnedPlayers = new HashMap<>();
	public static final Map<UUID, Boolean> wasInProtection = new HashMap<>();

	public static boolean isUnderSpawnProtection(ServerWorld pLevel, BlockPos pPos, PlayerEntity pPlayer, boolean canSendWarning, boolean bypassOpCheck) {
		return isUnderSpawnProtection(pLevel, pPos, pPlayer, pLevel.getServer().getPlayerList(), canSendWarning, bypassOpCheck);
	}
	public static boolean isUnderSpawnProtection(ServerWorld pLevel, BlockPos pPos, PlayerEntity pPlayer, PlayerList playerList, 
			boolean canSendWarning, boolean bypassOpCheck) {
		if(ExtendedServerConfig.enableExtendedSpawnProtection.get()) {
			if (pLevel.dimension() == World.OVERWORLD) {
				if (playerList.getOps().isEmpty()) {
					return false;
				} else if (ExtendedServerConfig.overworldSpawnProtectionRadius.get() <= 0) {
					return false;
				} else if (!bypassOpCheck && playerList.isOp(pPlayer.getGameProfile())) {
					return false;
				}  else {
					BlockPos blockpos = pLevel.getSharedSpawnPos();
					int i = MathHelper.abs(pPos.getX() - blockpos.getX());
					int j = MathHelper.abs(pPos.getZ() - blockpos.getZ());
					int k = Math.max(i, j);
					boolean isProtected = k <= ExtendedServerConfig.overworldSpawnProtectionRadius.get();
					if(isProtected && canSendWarning && canSendWarning(pPlayer)) {
						pPlayer.sendMessage(new StringTextComponent("This land is under spawn protection").withStyle(TextFormatting.RED), Util.NIL_UUID);
					}
					return isProtected;
				}
			}else if (pLevel.dimension() == LOTRDimensions.MIDDLE_EARTH_WORLD_KEY) {
				if (playerList.getOps().isEmpty()) {
					return false;
				} else if (ExtendedServerConfig.middleEarthSpawnProtectionRadius.get() <= 0) {
					return false;
				} else if (playerList.isOp(pPlayer.getGameProfile()) && !bypassOpCheck) {
					return false;
				}  else {
					BlockPos blockpos = pLevel.getSharedSpawnPos();
					int i = MathHelper.abs(pPos.getX() - blockpos.getX());
					int j = MathHelper.abs(pPos.getZ() - blockpos.getZ());
					int k = Math.max(i, j);
					boolean isProtected = k <= ExtendedServerConfig.middleEarthSpawnProtectionRadius.get();
					if(isProtected && canSendWarning && canSendWarning(pPlayer)) {
						pPlayer.sendMessage(new StringTextComponent("This land is under Middle Earth spawn protection").withStyle(TextFormatting.RED), Util.NIL_UUID);
					}
					return isProtected;
				}
			}
		}
		return false;
	}

	private static boolean canSendWarning(PlayerEntity player) {
	    long now = System.currentTimeMillis();
	    UUID id = player.getUUID();

	    long last = extendedWarnedPlayers.getOrDefault(id, 0L);
	    if (now - last >= 5000) { // 5 seconds
	    	extendedWarnedPlayers.put(id, now);
	        return true;
	    }
	    return false;
	}
}
