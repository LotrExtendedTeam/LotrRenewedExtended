package com.github.maximuslotro.lotrrextended.client.render.block;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.tileentity.ExtendedBedTileEneityRenderer;
import com.github.maximuslotro.lotrrextended.client.render.tileentity.ExtendedChestTileEntityRenderer;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBedBlock;

import lotr.common.init.ExtendedTileEntities;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class BlockRendererSetup {
	public static final ResourceLocation STONE_CHEST = createNewChestModel("stone_chest");
	public static final RenderMaterial STONE_CHEST_MATERIAL = new RenderMaterial(Atlases.CHEST_SHEET, STONE_CHEST);
	public static final ResourceLocation STRAW_BED = createNewBedModel("straw_bed");
	public static final RenderMaterial STRAW_BED_MATERIAL = new RenderMaterial(Atlases.BED_SHEET, STRAW_BED);
//	public static Callable<ItemStackTileEntityRenderer> createStoneChestRenderer() {
//		return () -> new ExtendedItemStackRenderer(ExtendedStoneChestTileEntity::new);
//	}

	public static void regModels() {
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.STONE_CHEST.get(), ExtendedChestTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(ExtendedTileEntities.STRAW_BED.get(), ExtendedBedTileEneityRenderer::new);
	}

	public static ResourceLocation createNewChestModel(String name) {
		return new ResourceLocation(LotrExtendedMod.MODID, "entity/chest/"+name);
	}

	public static ResourceLocation createNewBedModel(String name) {
		return new ResourceLocation(LotrExtendedMod.MODID, "entity/bed/"+name);
	}

	public static void addStupidChestTexturesTookMeSeveralDaysToGetThisRight(TextureStitchEvent.Pre event) {
		if (event.getMap().location().equals(Atlases.CHEST_SHEET)) {
			event.addSprite(STONE_CHEST);
		}
		if (event.getMap().location().equals(Atlases.BED_SHEET)) {
			event.addSprite(STRAW_BED);
		}
	}

	public static void setupBlockRenderers() {
		for(RegistryObject<Block> reg: LOTRBlocks.BLOCKS.getEntries()) {
			Block block = reg.get();
			if(block instanceof ExtendedBedBlock) {
				RenderTypeLookup.setRenderLayer(block, RenderType.cutout());
			}
		}
	}
}
