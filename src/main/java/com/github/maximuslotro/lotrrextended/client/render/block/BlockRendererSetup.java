package com.github.maximuslotro.lotrrextended.client.render.block;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.tileentity.ExtendedBedTileEneityRenderer;
import com.github.maximuslotro.lotrrextended.client.render.tileentity.ExtendedChestTileEntityRenderer;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedCarpetBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedHangingBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerWallBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedFullBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedReedBasketBlock;

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
	public static final ResourceLocation ORC_BED = createNewBedModel("orc_bed");
	public static final RenderMaterial ORC_BED_MATERIAL = new RenderMaterial(Atlases.BED_SHEET, ORC_BED);
	public static final ResourceLocation BLACK_FUR_BED = createNewBedModel("black_fur_bed");
	public static final RenderMaterial BLACK_FUR_BED_MATERIAL = new RenderMaterial(Atlases.BED_SHEET, BLACK_FUR_BED);
	public static final ResourceLocation FUR_BED = createNewBedModel("fur_bed");
	public static final RenderMaterial FUR_BED_MATERIAL = new RenderMaterial(Atlases.BED_SHEET, FUR_BED);
	public static final ResourceLocation GRAY_FUR_BED = createNewBedModel("gray_fur_bed");
	public static final RenderMaterial GRAY_FUR_BED_MATERIAL = new RenderMaterial(Atlases.BED_SHEET, GRAY_FUR_BED);
	public static final ResourceLocation SILVER_FUR_BED = createNewBedModel("silver_fur_bed");
	public static final RenderMaterial SILVER_FUR_BED_MATERIAL = new RenderMaterial(Atlases.BED_SHEET, SILVER_FUR_BED);
	public static final ResourceLocation WHITE_FUR_BED = createNewBedModel("white_fur_bed");
	public static final RenderMaterial WHITE_FUR_BED_MATERIAL = new RenderMaterial(Atlases.BED_SHEET, WHITE_FUR_BED);

	public static void regModels() {
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
			event.addSprite(ORC_BED);
			event.addSprite(BLACK_FUR_BED);
			event.addSprite(FUR_BED);
			event.addSprite(GRAY_FUR_BED);
			event.addSprite(SILVER_FUR_BED);
			event.addSprite(WHITE_FUR_BED);
		}
	}

	public static void setupBlockRenderers() {
		for(RegistryObject<Block> reg: LOTRBlocks.BLOCKS.getEntries()) {
			Block block = reg.get();
			if(block instanceof ExtendedBedBlock) {
				RenderTypeLookup.setRenderLayer(block, RenderType.cutout());
			}
			if(block instanceof ExtendedFullBedBlock) {
				RenderTypeLookup.setRenderLayer(block, RenderType.cutout());
			}
			if(block instanceof ExtendedReedBasketBlock) {
				RenderTypeLookup.setRenderLayer(block, RenderType.cutout());
			}
			if(block instanceof ExtendedHangingBlock) {
				RenderTypeLookup.setRenderLayer(block, RenderType.cutout());
			}
			if(block instanceof ExtendedMEBannerBlock) {
				RenderTypeLookup.setRenderLayer(block, RenderType.cutout());
			}
			if(block instanceof ExtendedMEBannerWallBlock) {
				RenderTypeLookup.setRenderLayer(block, RenderType.cutout());
			}
			if(block instanceof ExtendedCarpetBlock) {
				RenderTypeLookup.setRenderLayer(block, RenderType.cutout());
			}
		}
	}
}
