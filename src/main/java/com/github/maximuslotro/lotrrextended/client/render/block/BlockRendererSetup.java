package com.github.maximuslotro.lotrrextended.client.render.block;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.tileentity.ExtendedChestTileEntityRenderer;

import lotr.common.init.ExtendedTileEntities;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class BlockRendererSetup {
	public static final RenderMaterial STONE_CHEST_LOCATION = chestMaterial("stone_chest");
	
	private static RenderMaterial chestMaterial(String p_228774_0_) {
		return new RenderMaterial(new ResourceLocation(LotrExtendedMod.MODID,"textures/atlas/chest.png"), new ResourceLocation(LotrExtendedMod.MODID,"entity/chest/" + p_228774_0_));
	}

	public static void regModels() {
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.STONE_CHEST.get(), ExtendedChestTileEntityRenderer::new);
	}
}
