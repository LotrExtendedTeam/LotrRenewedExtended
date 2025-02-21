package com.github.maximuslotro.lotrrextended;

import com.github.maximuslotro.lotrrextended.common.chairs.HackyChairDatabase;
import com.github.maximuslotro.lotrrextended.common.codex.ExtendedCodexManager;
import com.github.maximuslotro.lotrrextended.common.codex.ExtendedCodexServerPlayerList;
import com.github.maximuslotro.lotrrextended.common.eventhandlers.ExtendedTickHandlerServer;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedHiredUnitProfileManager;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawnManager;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class LotrExtendedModServer {
	private static ExtendedTickHandlerServer serverTickEventHandler;
	private static HackyChairDatabase hackyChairManager;
	private static ExtendedCodexServerPlayerList codexPlayerList;
	private static ExtendedCodexManager codexManager;
	
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
		forgeBus.addListener(this::extendedRegisterDataPackListeners);
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

	public void extendedRegisterDataPackListeners(AddReloadListenerEvent event) {
		event.addListener(ExtendedStructureEntitySpawnManager.INSTANCE);
		event.addListener(ExtendedHiredUnitProfileManager.INSTANCE);
		codexManager = new ExtendedCodexManager(ServerLifecycleHooks.getCurrentServer().getDataPackRegistries().getPredicateManager());
		event.addListener(codexManager);
	}

	public static ExtendedCodexServerPlayerList getCodexPlayerList() {
		return codexPlayerList;
	}

	public static ExtendedCodexManager getCodexManager() {
		return codexManager;
	}

	public static HackyChairDatabase getHackyChairManager() {
		return hackyChairManager;
	}
}
