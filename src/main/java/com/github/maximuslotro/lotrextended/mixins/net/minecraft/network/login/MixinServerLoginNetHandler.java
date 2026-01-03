package com.github.maximuslotro.lotrextended.mixins.net.minecraft.network.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.staffserver.ExtendedSpecialConfig;
import com.mojang.authlib.GameProfile;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.ServerLoginNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ProfileBanEntry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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

	@Inject(method = "handleAcceptedLogin", at = @At(value = "INVOKE_ASSIGN",
					target = "Lnet/minecraft/server/management/PlayerList;canPlayerLogin(Ljava/net/SocketAddress;Lcom/mojang/authlib/GameProfile;)Lnet/minecraft/util/text/ITextComponent;"),
			locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	public void handleAcceptedLogin$Extended(CallbackInfo ci, ITextComponent itextcomponent) {
		if (itextcomponent != null || !ExtendedServerConfig.enableStaffServer.get()) return;

		FMLConnectionData data = NetworkHooks.getConnectionData(this.connection);
		if (data != null) {
			List<String> disallowedMods = new ArrayList<>();
			for (String mod : data.getModList()) {
				String modId = mod.toLowerCase(Locale.ROOT);

				if (ExtendedSpecialConfig.isAutoBan(modId)) {
					String msg = ExtendedSpecialConfig.getAutoBanMessage(modId);
					server.getPlayerList().getBans().add(new ProfileBanEntry(gameProfile, null, "AntiCheat", null, msg));
					disconnect(new StringTextComponent(msg));
					ci.cancel();
					return;
				}

				// Collect disallowed mods instead of kicking immediately
		        if (!ExtendedSpecialConfig.isAllowed(modId)) {
		            disallowedMods.add(modId);
		        }
			}
			// Kick once, listing all disallowed mods
		    if (!disallowedMods.isEmpty()) {
		        String msg = ExtendedSpecialConfig.getKickMessage(disallowedMods);
		        disconnect(new StringTextComponent(msg));
		        ci.cancel();
		    }
		}
	}
}
