package com.github.maximuslotro.lotrrextended.client.render.tileentity;

import com.github.maximuslotro.lotrrextended.client.render.block.ExtendedWeaponRackItemRenderer;

import lotr.common.init.ExtendedTileEntities;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ExtendedTileEntityRenderers {
	public static void registerTileEntityRenderers() {
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.STONE_CHEST.get(), ExtendedChestTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.STRAW_BED.get(), ExtendedBedTileEneityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.ORC_BED.get(), ExtendedBedTileEneityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.BLACK_FUR_BED.get(), ExtendedBedTileEneityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.FUR_BED.get(), ExtendedBedTileEneityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.GRAY_FUR_BED.get(), ExtendedBedTileEneityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.SILVER_FUR_BED.get(), ExtendedBedTileEneityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.WHITE_FUR_BED.get(), ExtendedBedTileEneityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.WEAPON_RACK.get(), ExtendedWeaponRackItemRenderer::new);
	}
}
