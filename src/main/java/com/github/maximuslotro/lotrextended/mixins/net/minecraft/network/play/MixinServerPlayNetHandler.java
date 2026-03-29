package com.github.maximuslotro.lotrextended.mixins.net.minecraft.network.play;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.entity.npc.ExtendedRidableEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CPlayerPacket;

/**
 * Mixin for {@link ServerPlayNetHandler} to modify player behavior on the server.
 * <p>
 * This mixin adds two main enhancements:
 * <ul>
 *   <li>Allows players riding {@link ExtendedRidableEntity} mounts to open their inventories via the
 *       {@link net.minecraft.network.play.client.CEntityActionPacket.Action#OPEN_INVENTORY} action.</li>
 *   <li>Prevents players from being kicked for floating when they are mounted, by resetting the
 *       {@code clientIsFloating} state and {@code aboveGroundTickCount} in {@link ServerPlayNetHandler#handleMovePlayer}.</li>
 * </ul>
 * </p>
 */
@Mixin(ServerPlayNetHandler.class)
public class MixinServerPlayNetHandler {
	/**
     * The player associated with this network handler.
     * Shadowed from {@link ServerPlayNetHandler}.
     */
	@Shadow
	public ServerPlayerEntity player;
	/**
     * Tracks whether the client is considered to be floating.
     * Shadowed from {@link ServerPlayNetHandler}.
     */
	@Shadow 
	private boolean clientIsFloating;
	/**
     * Counts the number of ticks the client has been considered floating.
     * Shadowed from {@link ServerPlayNetHandler}.
     */
	@Shadow 
	private int aboveGroundTickCount;
	/**
     * Intercepts {@link ServerPlayNetHandler#handlePlayerCommand} to allow mounted players
     * to open the inventory of an {@link ExtendedRidableEntity}.
     * <p>
     * If the action is {@link net.minecraft.network.play.client.CEntityActionPacket.Action#OPEN_INVENTORY} and the player is riding
     * an {@code ExtendedRidableEntity}, the inventory is opened and the packet handling is cancelled
     * to prevent default behavior.
     * </p>
     *
     * @param packet The entity action packet sent by the client.
     * @param ci     The callback info allowing cancellation of the original method.
     */
	@Inject(
		method = "handlePlayerCommand",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/network/PacketThreadUtil;ensureRunningOnSameThread(Lnet/minecraft/network/IPacket;Lnet/minecraft/network/INetHandler;Lnet/minecraft/world/server/ServerWorld;)V",
			shift = At.Shift.AFTER
			),
		cancellable = true
		)
	public void handlePlayerCommand$Extneded(CEntityActionPacket packet, CallbackInfo ci) {
		if (packet.getAction() != CEntityActionPacket.Action.OPEN_INVENTORY) {
			return;
		}
		//Allow alternate mounts to open inventories
		if (this.player.getVehicle() instanceof ExtendedRidableEntity) {
			((ExtendedRidableEntity)this.player.getVehicle()).openInventory(this.player);
			this.player.resetLastActionTime();
			ci.cancel();
		}
	}
	/**
	 * Fixes the MC-98727 bug where players are kicked for "floating too long" when mounting entities.
	 * <p>
	 * In vanilla, the {@link ServerPlayNetHandler#clientIsFloating} state is not reset when a player
	 * starts riding a vehicle. This can cause the server to incorrectly think the player is floating,
	 * eventually triggering a kick after 80 ticks. This injection resets the floating state and tick
	 * counter whenever the player is mounted.
	 * </p>
	 * <p>
	 * Injected into {@link ServerPlayNetHandler#handleMovePlayer} just before the first
	 * {@link ServerPlayerEntity#absMoveTo(double, double, double, float, float)} call.
	 * </p>
	 *
	 * @param packet The movement packet sent by the client.
	 * @param ci     The callback info for the injection.
	 */
	@SuppressWarnings("javadoc")
	@Inject(
        method = "handleMovePlayer",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/ServerPlayerEntity;absMoveTo(DDDFF)V",
            ordinal = 0
        )
    )
    private void resetFloatingOnMount(CPlayerPacket packet, CallbackInfo ci) {
		this.clientIsFloating = false;
        this.aboveGroundTickCount = 0;
    }
}
