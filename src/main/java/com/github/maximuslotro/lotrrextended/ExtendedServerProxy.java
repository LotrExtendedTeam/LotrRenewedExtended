package com.github.maximuslotro.lotrrextended;

import com.github.maximuslotro.lotrrextended.common.chairs.HackyChairDatabase;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

public class ExtendedServerProxy {
	public static HackyChairDatabase hackyChairManager;

	public static void staticServerSetup() {
		new ExtendedServerProxy();
	}

	public static ExtendedServerProxy INSTANCE;

	public ExtendedServerProxy() {
		INSTANCE = this;
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(this::extendedServerStopping);
		hackyChairManager = new HackyChairDatabase();
	}

	public void extendedServerStopping(FMLServerStoppingEvent event) {
		hackyChairManager.handleServerStopping();
	}
}
