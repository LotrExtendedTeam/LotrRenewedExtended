package com.github.maximuslotro.lotrextended.mixins.lotr.common.network;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import lotr.common.network.CPacketMapTp;

@Mixin(CPacketMapTp.class)
public class MixinCPacketMapTp {

	@Redirect(method = "handle(Llotr/common/network/CPacketMapTp;Ljava/util/function/Supplier;)V", 
		at = @At(value = "INVOKE", target = "Ljava/lang/String;format"), remap=false)
	private static String injectedCommand(String command, Object... params) {
		return String.format(ExtendedServerConfig.alternateMapTeleportCommand.get(), params[0], params[1], params[2]);
	}
}
