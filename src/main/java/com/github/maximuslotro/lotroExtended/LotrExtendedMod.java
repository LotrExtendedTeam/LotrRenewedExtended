package com.github.maximuslotro.lotroExtended;

import com.github.maximuslotro.lotroExtended.common.loot.ExtendedLootModifiers;
import com.github.maximuslotro.lotroExtended.eventhandlers.ForgeInventoryEvents;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(value = LotrExtendedMod.MODID)
public class LotrExtendedMod {

	public static final String MODID = "lotrextended";

	public LotrExtendedMod() {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(ForgeInventoryEvents.class);

		ExtendedLog.make();
		ExtendedLootModifiers.register();
	}

	@SubscribeEvent
	public void registerCommands(RegisterCommandsEvent event) {
		InfoCommand.register(event.getDispatcher());
	}
}
