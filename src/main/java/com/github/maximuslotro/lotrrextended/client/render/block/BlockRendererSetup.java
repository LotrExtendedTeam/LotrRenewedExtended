package com.github.maximuslotro.lotrrextended.client.render.block;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.tileentity.ExtendedChestTileEntityRenderer;

import lotr.common.init.ExtendedTileEntities;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class BlockRendererSetup {
	public static final ResourceLocation STONE_CHEST = createNewChestModel("stone_chest");
	public static final RenderMaterial STONE_CHEST_MATERIAL = new RenderMaterial(Atlases.CHEST_SHEET, STONE_CHEST);
//	public static Callable<ItemStackTileEntityRenderer> createStoneChestRenderer() {
//		return () -> new ExtendedItemStackRenderer(ExtendedStoneChestTileEntity::new);
//	}

	public static void regModels() {
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.STONE_CHEST.get(), ExtendedChestTileEntityRenderer::new);
	}

	public static ResourceLocation createNewChestModel(String name) {
		return new ResourceLocation(LotrExtendedMod.MODID, "entity/chest/"+name);
	}

	public static void addStupidChestTexturesTookMeSeveralDaysToGetThisRight(TextureStitchEvent.Pre event) {
		if (event.getMap().location().equals(Atlases.CHEST_SHEET)) {
			event.addSprite(STONE_CHEST);
		}
	}
}
