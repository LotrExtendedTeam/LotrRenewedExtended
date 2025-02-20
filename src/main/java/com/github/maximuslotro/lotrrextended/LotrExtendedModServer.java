package com.github.maximuslotro.lotrrextended;

import com.github.maximuslotro.lotrrextended.common.chairs.HackyChairDatabase;
import com.github.maximuslotro.lotrrextended.common.codex.ExtendedCodexServerPlayerList;
import com.github.maximuslotro.lotrrextended.common.eventhandlers.ExtendedTickHandlerServer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

public class LotrExtendedModServer {
	public static ExtendedTickHandlerServer serverTickEventHandler;
	public static HackyChairDatabase hackyChairManager;
	public static ExtendedCodexServerPlayerList codexPlayerList;

	public static void staticServerSetup() {
		new LotrExtendedModServer();
	}

	public static LotrExtendedModServer INSTANCE;

	public LotrExtendedModServer() {
		INSTANCE = this;
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(this::extendedServerStopping);
		forgeBus.addListener(this::extendedServerPlayerLoggedOut);
		forgeBus.addListener(this::extendedServerOnDatapackSync);
		serverTickEventHandler = new ExtendedTickHandlerServer();
		hackyChairManager = new HackyChairDatabase();
		codexPlayerList = new ExtendedCodexServerPlayerList();
	}

	public void extendedServerStopping(FMLServerStoppingEvent event) {
		hackyChairManager.handleServerStopping();
		codexPlayerList.saveAll();
	}

	public void extendedServerPlayerLoggedOut(PlayerLoggedOutEvent event) {
		codexPlayerList.codexPlayerLoggedOut(event);
	}

	public void extendedServerOnDatapackSync(OnDatapackSyncEvent event) {
		codexPlayerList.codexDatapackSync(event);
	}
}
