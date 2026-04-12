package com.github.maximuslotro.lotrextended.mixins.lotr.common.event;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import lotr.common.event.LOTREventHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;

/**
 * Mixin targeting {@code LOTREventHandler} to prevent login logic from continuing
 * when a {@code ServerPlayerEntity} has already disconnected.
 *
 * <p>This avoids unnecessary data processing and potential issues caused by
 * interacting with invalid player instances during the login event.</p>
 * @Mixin (LOTREventHandler.class)
 */
@Mixin(LOTREventHandler.class)
public class MixinLOTREventHandler {
	/**
	 * Injects just before {@code ServerPlayerEntity#getLevel()} is invoked inside
	 * {@code LOTREventHandler#onPlayerLogin}.
	 *
	 * <p>If the player has already disconnected at this point, the method execution
	 * is cancelled to prevent further processing using an invalid player reference.</p>
	 *
	 * @param event        the login event being processed
	 * @param ci           the callback info used to cancel execution
	 * @param player       the player associated with the event
	 * @param world        the player's current world
	 * @param serverPlayer the server-side player instance
	 */
	@Inject(
		method = "onPlayerLogin(Lnet/minecraftforge/event/entity/player/PlayerEvent$PlayerLoggedInEvent;)V",
		at = @At(
			value = "INVOKE",
			target = "Llotr/common/data/LOTRLevelData;serverInstance()Llotr/common/data/LOTRLevelData;",
			remap = false
			),
		cancellable = true,
		remap = false,
		locals = LocalCapture.CAPTURE_FAILHARD
		)
	private void beforeGetLevel(PlayerLoggedInEvent event, CallbackInfo ci, PlayerEntity player, World world, ServerPlayerEntity serverPlayer) {
		if (serverPlayer.hasDisconnected()) {
			// ExtendedLog.info("Prevented before getLevel: " + player.getStringUUID());
			ci.cancel();
		}
	}
	/**
	 * Injects just before {@code LOTRLevelData#sendLoginPacket(ServerPlayerEntity)}
	 * is invoked inside {@code LOTREventHandler#onPlayerLogin}.
	 *
	 * <p>Cancels execution if the player has disconnected, preventing login packets
	 * and related data from being sent to a non-existent or invalid connection.</p>
	 *
	 * @param event        the login event being processed
	 * @param ci           the callback info used to cancel execution
	 * @param player       the player associated with the event
	 * @param world        the player's current world
	 * @param serverPlayer the server-side player instance
	 */
	@Inject(
		method = "onPlayerLogin(Lnet/minecraftforge/event/entity/player/PlayerEvent$PlayerLoggedInEvent;)V",
		at = @At(
			value = "INVOKE",
			target = "Llotr/common/data/LOTRLevelData;sendLoginPacket(Lnet/minecraft/entity/player/ServerPlayerEntity;)V",
			remap = false
			),
		cancellable = true,
		remap = false,
		locals = LocalCapture.CAPTURE_FAILHARD
		)
	private void beforeSendLoginPacket(PlayerLoggedInEvent event, CallbackInfo ci, PlayerEntity player, World world, ServerPlayerEntity serverPlayer) {
		if (serverPlayer.hasDisconnected()) {
			// ExtendedLog.info("Prevented before sendLoginPacket: " + player.getStringUUID());
			ci.cancel();
		}
	}
}