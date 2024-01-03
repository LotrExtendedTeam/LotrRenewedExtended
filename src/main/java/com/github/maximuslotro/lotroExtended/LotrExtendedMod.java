package com.github.maximuslotro.lotroExtended;

import com.github.maximuslotro.lotroExtended.eventhandlers.ForgeInventoryEvents;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = LotrExtendedMod.MODID)
public class LotrExtendedMod {

	public static final String MODID = "lotrextended";

	public LotrExtendedMod() {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(ForgeInventoryEvents.class);

		ExtendedLog.make();
	}

	@SubscribeEvent
	public void registerCommands(RegisterCommandsEvent event) {
		InfoCommand.register(event.getDispatcher());
	}

	@SubscribeEvent
	public void onClientSetup(final FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(ExtendedBlocks.BLACKBERRY_BUSH_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ExtendedBlocks.BLUEBERRY_BUSH_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ExtendedBlocks.CRANBERRY_BUSH_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ExtendedBlocks.ELDERBERRY_BUSH_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ExtendedBlocks.RASPBERRY_BUSH_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ExtendedBlocks.WILDBERRY_BUSH_PLANT.get(), RenderType.cutout());
	}
}
