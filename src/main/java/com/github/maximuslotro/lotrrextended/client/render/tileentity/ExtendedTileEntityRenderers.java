package com.github.maximuslotro.lotrrextended.client.render.tileentity;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedTileEntities;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ExtendedTileEntityRenderers {
	public static void registerTileEntityRenderers() {
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.STONE_CHEST.get(), ExtendedChestTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.BED.get(), ExtendedBedTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.WEAPON_RACK.get(), ExtendedWeaponRackItemRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.STONE_SIGN.get(), ExtendedStoneTextSignTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.STONE_GRINDER.get(), ExtendedStoneGrinderTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.BIRD_CAGE.get(), ExtendedBirdCageTileEntityRenderer::new);
	}
}
