package com.github.maximuslotro.lotrextended.mixins.net.minecraft.network.login;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.staffserver.ExtendedSpecialConfig;
import com.mojang.authlib.GameProfile;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.ServerLoginNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.network.FMLConnectionData;
import net.minecraftforge.fml.network.NetworkHooks;

@Mixin(ServerLoginNetHandler.class)
public class MixinServerLoginNetHandler {

	@Shadow
	@Final
	public NetworkManager connection;

	@Shadow
	@Final
	private MinecraftServer server;

	@Shadow
	private GameProfile gameProfile;

	@Shadow
	public void disconnect(ITextComponent pReason) {}

	@Shadow
	protected GameProfile createFakeProfile(GameProfile pOriginal) {return null;}

	@Inject(method = "handleAcceptedLogin", at = @At(value = "HEAD"), cancellable = true)
	public void handleAcceptedLogin$Extended(CallbackInfo ci) {
		if (!ExtendedServerConfig.enableStaffServer.get()) {
			return;
		}
		if (!this.gameProfile.isComplete()) {
			this.gameProfile = this.createFakeProfile(this.gameProfile);
		}
		FMLConnectionData data = NetworkHooks.getConnectionData(this.connection);
		if (data != null) {
			ITextComponent disconnectReason = ExtendedSpecialConfig.handleModsList(connection, server, gameProfile, data.getModList(), false);
			if(disconnectReason!=null) {
				disconnect(disconnectReason);
				ci.cancel();
				return;
			}
		}
	}
}
