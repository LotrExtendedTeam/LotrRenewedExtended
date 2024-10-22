package com.github.maximuslotro.lotrrextended.client.render.entity;

import lotr.client.render.entity.*;
import lotr.common.init.ExtendedEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ExtendedEntityRenderers {
	public static void registerEntityRenderers() {
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DUNEDAIN_RANGER.get(), ExtendedDunedainRangerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.GALADHRIM_WANDERER.get(), ExtendedGaladhrimWandererRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HOBBIT_BARTENDER.get(), ExtendedHobbitBartenderRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.HOBBIT_FARMER.get(), ExtendedHobbitFarmerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.ODDMENT_COLLECTOR.get(), ExtendedOddmentCollectorRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.RIVENDELL_WANDERER.get(), ExtendedRivendellWandererRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.THROWN_CONKER.get(), manager -> new SpriteRenderer<>(manager, itemRenderer));
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DUCK.get(), ExtendedDuckRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.DUCK_EGG_ENTITY.get(), manager -> new SpriteRenderer<>(manager, itemRenderer));
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.STRUCTURE_ENTITY_SPAWNER.get(), ExtendedStructureSpawnerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.TRADER_RESPAWNER_ENTITY.get(), ExtendedTraderRespawnerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BLUE_MOUNTAINS_MERCHANT.get(), ExtendedBlueMountainsMerchantRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.GUNDABAD_CAMP_TRADER.get(), ExtendedGundabadOrcScroungerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.GUNDABAD_URUK.get(), ExtendedGundabadUrukRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.GUNDABAD_URUK_ARCHER.get(), ExtendedGundabadUrukRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ExtendedEntities.BARROW_WIGHT.get(), ExtendedBarrowWightRenderer::new);
	}
}
