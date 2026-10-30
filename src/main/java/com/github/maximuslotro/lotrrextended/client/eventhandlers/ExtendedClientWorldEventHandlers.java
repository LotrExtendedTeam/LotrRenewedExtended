package com.github.maximuslotro.lotrrextended.client.eventhandlers;

import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;

import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ExtendedClientWorldEventHandlers {
	@SubscribeEvent
    public static void onClientPlayerLogin(ClientPlayerNetworkEvent.LoggedInEvent event) {
		ExtendedLevelData.clientInstance().resetOnServerExit();
		ExtendedLevelData.clientInstance().loadClientSafe();
    }
	@SubscribeEvent
    public static void onClientPlayerLogout(ClientPlayerNetworkEvent.LoggedInEvent event) {
		ExtendedLevelData.clientInstance().resetOnServerExit();
	}
}
