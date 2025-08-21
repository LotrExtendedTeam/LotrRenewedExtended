package com.github.maximuslotro.lotrextended.mixins.net.minecraft.server.dedicated;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import lotr.common.init.LOTRDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@Mixin(DedicatedServer.class)
public class MixinDedicatedServer {

	@Shadow
	public DedicatedPlayerList getPlayerList() {return null;}

	@Inject(method = "isUnderSpawnProtection(Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At(value = "HEAD"), cancellable = true)
	private void isUnderSpawnProtection$Extended(ServerWorld pLevel, BlockPos pPos, PlayerEntity pPlayer, CallbackInfoReturnable<Boolean> cir) {
		if(ExtendedServerConfig.enableExtendedSpawnProtection.get()) {
			if (pLevel.dimension() == World.OVERWORLD) {
				if (this.getPlayerList().getOps().isEmpty()) {
					cir.setReturnValue(false);
				} else if (this.getPlayerList().isOp(pPlayer.getGameProfile())) {
					cir.setReturnValue(false);
				} else if (ExtendedServerConfig.overworldSpawnProtectionRadius.get() <= 0) {
					cir.setReturnValue(false);
				} else {
					BlockPos blockpos = pLevel.getSharedSpawnPos();
					int i = MathHelper.abs(pPos.getX() - blockpos.getX());
					int j = MathHelper.abs(pPos.getZ() - blockpos.getZ());
					int k = Math.max(i, j);
					boolean isProtected = k <= ExtendedServerConfig.overworldSpawnProtectionRadius.get();
					if(isProtected) {
						pPlayer.sendMessage(new StringTextComponent("This land is under spawn protection").withStyle(TextFormatting.RED), Util.NIL_UUID);
					}
					cir.setReturnValue(isProtected);
				}
			}else if (pLevel.dimension() == LOTRDimensions.MIDDLE_EARTH_WORLD_KEY) {
				if (this.getPlayerList().getOps().isEmpty()) {
					cir.setReturnValue(false);
				} else if (this.getPlayerList().isOp(pPlayer.getGameProfile())) {
					cir.setReturnValue(false);
				} else if (ExtendedServerConfig.middleEarthSpawnProtectionRadius.get() <= 0) {
					cir.setReturnValue(false);
				} else {
					BlockPos blockpos = pLevel.getSharedSpawnPos();
					int i = MathHelper.abs(pPos.getX() - blockpos.getX());
					int j = MathHelper.abs(pPos.getZ() - blockpos.getZ());
					int k = Math.max(i, j);
					boolean isProtected = k <= ExtendedServerConfig.middleEarthSpawnProtectionRadius.get();
					if(isProtected) {
						pPlayer.sendMessage(new StringTextComponent("This land is under Middle Earth spawn protection").withStyle(TextFormatting.RED), Util.NIL_UUID);
					}
					cir.setReturnValue(isProtected);
				}
			}
		}
	}
}
